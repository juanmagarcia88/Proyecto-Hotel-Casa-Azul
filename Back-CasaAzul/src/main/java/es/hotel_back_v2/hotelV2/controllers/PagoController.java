package es.hotel_back_v2.hotelV2.controllers;

import es.hotel_back_v2.hotelV2.model.Pago;
import es.hotel_back_v2.hotelV2.services.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pago")
public class PagoController {

    private final PagoService pagoService;

    // Inyección de dependencias por constructor
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping("/{reservaId}")
    public ResponseEntity<Pago> registrarPago(
            @PathVariable Long reservaId,
            @RequestBody Map<String, String> body) {

        System.out.println("🟢 Recibida solicitud para registrar pago en reservaId: " + reservaId);
        try {
            float monto = Float.parseFloat(body.get("monto"));
            Pago.MetodoPago metodo = Pago.MetodoPago.valueOf(body.get("metodo").toUpperCase());

            // Verificar los datos recibidos
            System.out.println("Monto recibido: " + monto);
            System.out.println("Método de pago recibido: " + metodo);

            Pago pago = pagoService.registrarPago(reservaId, monto, metodo);
            return new ResponseEntity<>(pago, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.out.println("🔴 Error: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Verificar si un pago ha sido realizado para una reserva
    @GetMapping("/verificar/{reservaId}")
    public ResponseEntity<String> verificarPago(@PathVariable Long reservaId) {
        String pagoRealizado = pagoService.esPagoRealizado(reservaId);
        return new ResponseEntity<>(pagoRealizado, HttpStatus.OK);
    }
}

