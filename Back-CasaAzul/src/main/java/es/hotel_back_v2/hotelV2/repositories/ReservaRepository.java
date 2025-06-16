package es.hotel_back_v2.hotelV2.repositories;

import es.hotel_back_v2.hotelV2.model.Cliente;
import es.hotel_back_v2.hotelV2.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByCliente(Cliente cliente);
}