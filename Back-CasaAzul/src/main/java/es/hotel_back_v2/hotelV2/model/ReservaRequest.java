package es.hotel_back_v2.hotelV2.model;

import java.util.Date;
import java.util.List;

public class ReservaRequest {
    private Date fechaInicio;
    private Date fechaFin;
    private String dniCliente;
    private List<Long> numerosHabitaciones;

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public List<Long> getNumerosHabitaciones() {
        return numerosHabitaciones;
    }

    public void setNumerosHabitaciones(List<Long> numerosHabitaciones) {
        this.numerosHabitaciones = numerosHabitaciones;
    }
}
