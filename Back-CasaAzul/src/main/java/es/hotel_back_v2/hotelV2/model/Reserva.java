package es.hotel_back_v2.hotelV2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    @FutureOrPresent(message = "La fecha de inicio no puede ser pasada")
    private Date fecha_inicio;

    @NotNull(message = "La fecha de fin no puede ser nula")
    @Future(message = "La fecha de fin debe ser futura")
    private Date fecha_fin;

    private Double total; // Asegúrate de que el campo 'total' está presente

    //relaciones

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference //evita que se serialice la relación inversa
    private Cliente cliente;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Pago> pagos;

    @ManyToMany
    @JoinTable(
            name = "reserva_habitacion",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "habitacion_num")
    )
    private List<Habitacion> habitaciones;

    //constructores

    public Reserva(Long id, Date fecha_inicio, Date fecha_fin) {
        this.id = id;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public Reserva() {

    }

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fecha_inicio=" + fecha_inicio +
                ", fecha_fin=" + fecha_fin +
                '}';
    }

}
