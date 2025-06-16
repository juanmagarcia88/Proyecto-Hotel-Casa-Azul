package es.hotel_back_v2.hotelV2.controllers;

import es.hotel_back_v2.hotelV2.model.Reserva;
import es.hotel_back_v2.hotelV2.model.ReservaRequest;
import es.hotel_back_v2.hotelV2.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    //crear reserva
    @PostMapping("/crear")
    public ResponseEntity<?> crearReserva(@RequestBody ReservaRequest reservaRequest) {
        try {
            Reserva nuevaReserva = reservaService.crearReserva(
                    reservaRequest.getFechaInicio(),
                    reservaRequest.getFechaFin(),
                    reservaRequest.getDniCliente(),
                    reservaRequest.getNumerosHabitaciones()
            );
            return ResponseEntity.ok(Map.of("id", nuevaReserva.getId())); // Devuelve JSON con el ID
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }


    //mostrar todas las reservas
    @GetMapping("/mostrartodas")
    public ResponseEntity<List<Reserva>> buscarTodasReservas() {
        return ResponseEntity.ok(reservaService.buscarTodas());
    }

    //mostrar reserva por id
    @GetMapping("/buscarporid/{id}")
    public ResponseEntity<Optional<Reserva>> buscarReservaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarReservaPorId(id));
    }

    //eliminar reserva por id
    @DeleteMapping("/eliminar/{id}")
    public void eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
    }

    //modificar reserva por id
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Reserva> modificarReserva(@PathVariable Long id, @RequestBody Reserva reservaActualizada) {
        return ResponseEntity.ok(reservaService.modificarReserva(id, reservaActualizada));
    }

    //crear factura apartir de una reserva
    @GetMapping("/generarFactura/{idReserva}")
    public ResponseEntity<String> generarFactura(@PathVariable Long idReserva) {
        try {
            String factura = reservaService.generarFactura(idReserva);
            return ResponseEntity.ok(factura);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al generar factura");
        }
    }

    //listar reservas por fecha
    @GetMapping("/listarPorFecha")
    public Map<Date, List<Reserva>> listarReservasPorFecha() {
        return reservaService.listarReservasAgrupadasPorFecha();
    }

    @GetMapping("/buscarporDni/{dni}")
    public ResponseEntity<List<Reserva>> buscarReservasPorDni(@PathVariable String dni) {
        List<Reserva> reservas = reservaService.buscarReservasPorDni(dni);
        return ResponseEntity.ok(reservas);
    }


}
