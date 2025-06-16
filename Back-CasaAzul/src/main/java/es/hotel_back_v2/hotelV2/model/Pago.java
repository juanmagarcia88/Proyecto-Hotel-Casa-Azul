package es.hotel_back_v2.hotelV2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

@Entity
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que 0")
    private float monto;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El método de pago no puede estar vacío")
    private MetodoPago metodo;

    private String factura;

    @NotNull(message = "La fecha de pago no puede ser nula")
    @PastOrPresent(message = "La fecha de pago no puede ser futura")
    private LocalDate fecha;

    //relaciones

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    @JsonBackReference
    private Reserva reserva;

    //constructores

    public Pago() {
    }

    public enum MetodoPago {
        TARJETA,
        PAYPAL,
        PAYSAFECARD
    }

    public Pago(Long id, float monto, MetodoPago metodo, String factura, LocalDate fecha) {
        this.id = id;
        this.monto = monto;
        this.metodo = metodo;
        this.factura = factura;
        this.fecha = fecha;
    }

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public MetodoPago getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPago metodo) {
        this.metodo = metodo;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}
