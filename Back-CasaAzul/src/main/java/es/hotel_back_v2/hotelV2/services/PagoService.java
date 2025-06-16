package es.hotel_back_v2.hotelV2.services;

import es.hotel_back_v2.hotelV2.model.Pago;
import es.hotel_back_v2.hotelV2.model.Reserva;
import es.hotel_back_v2.hotelV2.repositories.PagoRepository;
import es.hotel_back_v2.hotelV2.repositories.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaService reservaService;

    @Transactional
    public Pago registrarPago(Long reservaId, float monto, Pago.MetodoPago metodo) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(reservaId);
        if (reservaOpt.isEmpty()) {
            throw new RuntimeException("Reserva no encontrada");
        }

        Reserva reserva = reservaOpt.get();
        Pago pago = new Pago();
        pago.setReserva(reserva);
        pago.setMonto(monto);
        pago.setMetodo(metodo);
        pago.setFecha(LocalDate.now());
        pago.setFactura(reservaService.generarFactura(reservaId));

        Pago pagoGuardado = pagoRepository.save(pago);
        System.out.println("🟢 Pago registrado con ID: " + pagoGuardado.getId());

        return pagoGuardado;
    }


    public String esPagoRealizado(Long idReserva) {
        boolean pagoExiste = pagoRepository.existsByReservaId(idReserva);
        return pagoExiste ? "Pagado" : "No pagado";
    }

}
