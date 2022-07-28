
import java.io.Serializable;
import java.time.LocalDate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan David
 */
public class ServicioMedico implements Serializable{
    private String suTipoServicio;
    private LocalDate fechaValida;
    private double costoTotal;

    public ServicioMedico(String suTipoServicio, LocalDate fechaValida, double costoTotal) {
        this.suTipoServicio = suTipoServicio;
        this.fechaValida = fechaValida;
        this.costoTotal = costoTotal;
    }

    public String getSuTipoServicio() {
        return suTipoServicio;
    }

    public void setSuTipoServicio(String suTipoServicio) {
        this.suTipoServicio = suTipoServicio;
    }

    public LocalDate getFechaValida() {
        return fechaValida;
    }

    public void setFechaValida(LocalDate fechaValida) {
        this.fechaValida = fechaValida;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    @Override
    public String toString() {
        return "Servicio Medico: " + "\n" + "Tipo de servicios: " + suTipoServicio + "\n" + "Fecha del servicio: " + fechaValida + "\n\n";
//                ", costoTotal=" + costoTotal + '}';
    } 
}
