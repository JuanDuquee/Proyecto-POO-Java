
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan David
 */
public class Paciente implements Serializable{
    protected String nombre;
    protected int numID;
    protected int numRegistro;
    protected ArrayList<ServicioMedico> suServicioMedico;

    public Paciente(String nombre, int numID, int numRegistro) {
        this.nombre = nombre;
        this.numID = numID;
        this.numRegistro = numRegistro;
        suServicioMedico = new ArrayList<>();
    }
    
    public void agregarServicio(ServicioMedico nuevoS){
        suServicioMedico.add(nuevoS);
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumID() {
        return numID;
    }

    public void setNumID(int numID) {
        this.numID = numID;
    }

    public int getNumRegistro() {
        return numRegistro;
    }

    public void setNumRegistro(int numRegistro) {
        this.numRegistro = numRegistro;
    }

    @Override
    public String toString() {
        return "nombre: " + nombre + "\n" + "numero de identificacion: " + numID + "\n" + "numero registro: " + numRegistro 
                + "\n\n" + suServicioMedico;
    }
       
}
