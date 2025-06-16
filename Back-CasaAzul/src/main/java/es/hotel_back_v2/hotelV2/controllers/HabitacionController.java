package es.hotel_back_v2.hotelV2.controllers;

import es.hotel_back_v2.hotelV2.model.Habitacion;
import es.hotel_back_v2.hotelV2.repositories.HabitacionRepository;
import es.hotel_back_v2.hotelV2.services.HabitacionService;
import es.hotel_back_v2.hotelV2.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/habitacion")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private HabitacionRepository habitacionRepository;

    // Añadir habitación
    @PostMapping
    public ResponseEntity<Habitacion> agregarHabitacion(@RequestBody Habitacion habitacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(habitacionService.crearHabitacion(habitacion));
    }

    // Mostrar todas las habitaciones
    @GetMapping("/mostrarTodas")
    public ResponseEntity<List<Habitacion>> buscarTodasHabitaciones() {
        return ResponseEntity.ok(habitacionRepository.findAll());
    }

    // Mostrar solo las habitaciones disponibles
    @GetMapping("/mostrarDisponibles")
    public ResponseEntity<List<Habitacion>> buscarHabitacionesDisponibles() {
        List<Habitacion> habitacionesDisponibles = habitacionRepository.findAll().stream()
                .filter(habitacion -> habitacion.getEstado() == Habitacion.Estado.DISPONIBLE)
                .collect(Collectors.toList());
        return ResponseEntity.ok(habitacionesDisponibles);
    }

    // Mostrar habitación por número
    @GetMapping("/buscarporNumero/{numero}")
    public ResponseEntity<Optional<Habitacion>> buscarPorNumero(@PathVariable Long numero) {
        return ResponseEntity.ok(habitacionRepository.findById(numero));
    }

    // Eliminar habitación por número
    @DeleteMapping("/eliminarHabitacion/{numero}")
    public void eliminarHabitacion(@PathVariable Long numero) {
        habitacionService.eliminarHabitacion(numero);
    }

    // Obtener las habitaciones ocupadas en una fecha específica
    @GetMapping("/habitaciones-ocupadas/{fecha}")
    public ResponseEntity<List<Habitacion>> obtenerHabitacionesOcupadas(@PathVariable("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        return ResponseEntity.ok(habitacionService.obtenerHabitacionesOcupadas(fecha));
    }
}
