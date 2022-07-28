/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan David
 */
public class Beneficiario extends Paciente {

    private String tipoID;
    private Cotizante suCotizante;

    public Beneficiario(String tipoID, Cotizante suCotizante, String nombre, int numID, int numRegistro) {
        super(nombre, numID, numRegistro);
        this.tipoID = tipoID;
        this.suCotizante = suCotizante;
    }
    
    public double determinarCuotaCopago(double costo) {
        double res=0;
        int aux = getSuCotizante().calcularRangoSalarial();
        if (aux == 1) {
            
            res = costo * 0.115;
            if (res > 260747) {
                res = 260747;
            }
        }else{
           if (aux == 2){
             res = costo * 0.173;
             if (res > 1044805) {
                 res = 1044805;
             }
           }else{
               if(aux == 3){
                 res = costo * 0.23;
                 if(res > 2089610){
                    res = 2089610; 
                 }
               }
           }
        }

        return res;
    }

    public String getTipoID() {
        return tipoID;
    }

    public void setTipoID(String tipoID) {
        this.tipoID = tipoID;
    }

    public Cotizante getSuCotizante() {
        return suCotizante;
    }

    public void setSuCotizante(Cotizante suCotizante) {
        this.suCotizante = suCotizante;
    }

    @Override
    public String toString() {
        return "Su cotizante: " + suCotizante.nombre + "\n" + "Beneficiario: " + "\n" + super.toString();
//                "Beneficiario{" + "tipoID=" + tipoID + ", suCotizante=" + suCotizante + '}';
    }

}
