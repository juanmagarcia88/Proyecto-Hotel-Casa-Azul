package es.hotel_back_v2.hotelV2.services;

import es.hotel_back_v2.hotelV2.model.Cliente;
import es.hotel_back_v2.hotelV2.model.Habitacion;
import es.hotel_back_v2.hotelV2.model.Reserva;
import es.hotel_back_v2.hotelV2.repositories.ClienteRepository;
import es.hotel_back_v2.hotelV2.repositories.HabitacionRepository;
import es.hotel_back_v2.hotelV2.repositories.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HabitacionService habitacionService;

    @Autowired
    private HabitacionRepository habitacionRepository;

    //crear reserva
    @Transactional
    public Reserva crearReserva(
            @RequestParam Date fechaInicio,
            @RequestParam Date fechaFin,
            @RequestParam String dniCliente,
            @RequestParam List<Long> numerosHabitaciones) {

        // Validar fechas
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas.");
        }
        if (fechaInicio.after(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        // Buscar cliente por DNI
        Cliente cliente = clienteRepository.findByDni(dniCliente)
                .orElseThrow(() -> new IllegalArgumentException("El cliente con DNI " + dniCliente + " no existe."));

        // Buscar habitaciones y validar existencia
        List<Habitacion> habitaciones = habitacionRepository.findAllById(numerosHabitaciones);
        if (habitaciones.size() != numerosHabitaciones.size()) {
            throw new IllegalArgumentException("Una o más habitaciones no existen.");
        }

        // Validar disponibilidad de habitaciones
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getEstado() != Habitacion.Estado.DISPONIBLE) {
                throw new IllegalArgumentException("La habitación " + habitacion.getNumero() + " no está disponible.");
            }
        }

        long diffInMillies = fechaFin.getTime() - fechaInicio.getTime();
        // Calcular noches de manera que sea final para lambda
        final long noches = (TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) == 0) ? 1 : TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        double total = habitaciones.stream()
                .mapToDouble(h -> h.getPrecio() * noches) // noches ahora es final
                .sum();


        // Crear y guardar la reserva
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setFecha_inicio(fechaInicio);
        nuevaReserva.setFecha_fin(fechaFin);
        nuevaReserva.setCliente(cliente);
        nuevaReserva.setHabitaciones(habitaciones);
        nuevaReserva.setTotal(total);

        reservaRepository.save(nuevaReserva);

        // Actualizar estado de habitaciones
        habitaciones.forEach(habitacion -> {
            habitacionService.actualizarEstadoHabitacion(habitacion.getNumero(), Habitacion.Estado.OCUPADA);
            habitacionRepository.save(habitacion);
        });

        return nuevaReserva;
    }

    //comprobar si las habitaciones están disponibles
    public boolean hayHabitacionesDisponibles(List<Habitacion> habitacionesSolicitadas, Date fechaInicio, Date fechaFin) {
        //obtener todas las reservas existentes
        List<Reserva> todasReservas = reservaRepository.findAll();

        //recorremos todas las reservas existentes
        for (Reserva reserva : todasReservas) {
            //recorremos las habitaciones de la reserva existente
            for (Habitacion habitacion : reserva.getHabitaciones()) {
                //verificamos si alguna de las habitaciones solicitadas ya está ocupada
                for (Habitacion habitacionSolicitada : habitacionesSolicitadas) {
                    if (habitacion.getNumero().equals(habitacionSolicitada.getNumero())) {
                        //si las fechas de la nueva reserva se solapan con las fechas de la reserva existente
                        long inicioExistente = reserva.getFecha_inicio().getTime();
                        long finExistente = reserva.getFecha_fin().getTime();
                        long nuevoInicio = fechaInicio.getTime();
                        long nuevoFin = fechaFin.getTime();

                        //verificamos si las fechas se solapan
                        if (nuevoInicio < finExistente && nuevoFin > inicioExistente) {
                            return false; //la habitación está ocupada en las fechas solicitadas
                        }
                    }
                }
            }
        }
        //si no hay solapamiento de fechas, la habitación está disponible
        return true;
    }

    //eliminar reserva por id
    @Transactional
    public void eliminarReserva(Long id) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(id);
        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            for (Habitacion habitacion : reserva.getHabitaciones()) {
                habitacionService.actualizarEstadoHabitacion(habitacion.getNumero(), Habitacion.Estado.DISPONIBLE); // Liberar habitación
            }
            reservaRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Reserva no encontrada");
        }
    }

    //mostrar todas las reservas
    public List<Reserva> buscarTodas() {
        return reservaRepository.findAll();
    }

    //buscar reserva por id
    public Optional<Reserva> buscarReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    //modificar reserva
    @Transactional
    public Reserva modificarReserva(Long id, Reserva reservaActualizada) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isPresent()) {
            reserva.get().setFecha_inicio(reservaActualizada.getFecha_inicio());
            reserva.get().setFecha_fin(reservaActualizada.getFecha_fin());
            return reservaRepository.save(reserva.get());
        }else{
            throw new NoSuchElementException("Reserva no encontrada");
        }
    }

    //crear factura
    public String generarFactura(Long idReserva) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(idReserva);
        if (reservaOpt.isEmpty()) {
            throw new RuntimeException("Reserva no encontrada");
        }
        Reserva reserva = reservaOpt.get();

        long diffInMillies = reserva.getFecha_fin().getTime() - reserva.getFecha_inicio().getTime();
        final long noches = (TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) == 0) ? 1 : TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        double total = reserva.getHabitaciones().stream()
                .mapToDouble(h -> h.getPrecio() * noches)
                .sum();

        return "Factura de la Reserva:\n" +
                "Cliente: " + reserva.getCliente().getNombre() + " " + reserva.getCliente().getApellido() + "\n" +
                "Fecha de Entrada: " + reserva.getFecha_inicio() + "\n" +
                "Fecha de Salida: " + reserva.getFecha_fin() + "\n" +
                "Total a Pagar: $" + total;
    }

    //listar reservas por fecha
    public Map<Date, List<Reserva>> listarReservasAgrupadasPorFecha() {
        //obtener todas las reservas
        List<Reserva> reservas = reservaRepository.findAll();

        //agrupar las reservas por su fecha de inicio
        return reservas.stream()
                .collect(Collectors.groupingBy(Reserva::getFecha_inicio));
    }

    // Buscar reservas por DNI del cliente
    public List<Reserva> buscarReservasPorDni(String dni) {
        Cliente cliente = clienteRepository.findByDni(dni)
                .orElseThrow(() -> new IllegalArgumentException("El cliente con DNI " + dni + " no existe."));

        return reservaRepository.findByCliente(cliente);
    }
}