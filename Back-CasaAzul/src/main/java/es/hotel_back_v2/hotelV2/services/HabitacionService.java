package es.hotel_back_v2.hotelV2.services;

import es.hotel_back_v2.hotelV2.model.Habitacion;
import es.hotel_back_v2.hotelV2.model.Reserva;
import es.hotel_back_v2.hotelV2.repositories.HabitacionRepository;
import es.hotel_back_v2.hotelV2.repositories.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    // Método para asignar el precio y la capacidad según el tipo de habitación
    private void asignarPrecioYCapacidad(Habitacion habitacion) {
        switch (habitacion.getTipo().toLowerCase()) {  // Utilizamos toLowerCase para evitar problemas con mayúsculas
            case "individual":
                habitacion.setPrecio(60.0);  // Precio predeterminado para habitación individual
                habitacion.setCapacidad(1);  // Capacidad predeterminada para habitación individual
                break;
            case "doble":
                habitacion.setPrecio(80.0);  // Precio predeterminado para habitación doble
                habitacion.setCapacidad(2);  // Capacidad predeterminada para habitación doble
                break;
            case "suite":
                habitacion.setPrecio(150.0);  // Precio predeterminado para suite
                habitacion.setCapacidad(4);  // Capacidad predeterminada para suite
                break;
            default:
                throw new IllegalArgumentException("Tipo de habitación desconocido");
        }
    }

    // Crear habitación
    @Transactional
    public Habitacion crearHabitacion(Habitacion habitacion) {
        asignarPrecioYCapacidad(habitacion);  // Asignamos precio y capacidad según el tipo
        habitacion.setEstado(Habitacion.Estado.DISPONIBLE); // Aseguramos que el estado inicial sea "DISPONIBLE"
        return habitacionRepository.save(habitacion);  // Guardamos la habitación en la base de datos
    }

    // Eliminar habitación por número
    public void eliminarHabitacion(Long numero) {
        habitacionRepository.deleteById(numero);  // Eliminamos la habitación por su número
    }

    // Modificar el estado de la habitación
    @Transactional
    public void actualizarEstadoHabitacion(Long numeroHabitacion, Habitacion.Estado estado) {
        Optional<Habitacion> habitacion = habitacionRepository.findById(numeroHabitacion);
        if (habitacion.isPresent()) {
            Habitacion h = habitacion.get();
            h.setEstado(estado);  // Establecemos el estado de la habitación
            habitacionRepository.save(h);  // Guardamos la habitación actualizada
        } else {
            throw new RuntimeException("Habitación no encontrada");
        }
    }

    // Mostrar todas las habitaciones
    public List<Habitacion> buscarHabitaciones() {
        return habitacionRepository.findAll();  // Retorna todas las habitaciones
    }

    // Mostrar habitación por número
    public Habitacion buscarPorNumero(Long numero) {
        return habitacionRepository.findById(numero)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));  // Retorna la habitación por su número
    }

    // Obtener habitaciones ocupadas
    public List<Habitacion> obtenerHabitacionesOcupadas(Date fecha) {
        List<Habitacion> habitacionesOcupadas = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findAll();  // Obtenemos todas las reservas

        for (Reserva reserva : reservas) {
            // Comprobamos si la fecha está dentro del rango de la reserva
            if (fecha.after(reserva.getFecha_inicio()) && fecha.before(reserva.getFecha_fin())) {
                habitacionesOcupadas.addAll(reserva.getHabitaciones());  // Añadimos las habitaciones ocupadas
            } else if (fecha.after(reserva.getFecha_fin())) {
                // Liberamos habitaciones de reservas pasadas
                for (Habitacion habitacion : reserva.getHabitaciones()) {
                    actualizarEstadoHabitacion(habitacion.getNumero(), Habitacion.Estado.DISPONIBLE);  // Cambiamos a disponible
                }
            }
        }

        return habitacionesOcupadas;  // Retornamos la lista de habitaciones ocupadas
    }
}
