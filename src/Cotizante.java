/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan David
 */
public class Cotizante extends Paciente{
    private double salario;
    private int celular;

    public Cotizante(double salario, int celular, String nombre, int numID, int numRegistro) {
        super(nombre, numID, numRegistro);
        this.salario = salario;
        this.celular = celular;
    }
    
    public int determinarCuotaModeradora(){
        int res;
        int aux = calcularRangoSalarial();
        if(aux == 1){
            res = 3500;
        }else{
            if(aux == 2){
                res = 14000;
            }else{
                res = 36800;
            }
        }
        
        return res;
    }
    
    public int calcularRangoSalarial(){
        int res;
        if(salario <= 1817051){
        res = 1;       
        }else{
            if(salario <= 4542631){
                res = 2;
            }else{
               res = 3; 
            }
        }       
        return res;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    @Override
    public String toString() {
        return "Cotizante: " + "\n" + super.toString(); 
//                + "Cotizante{" + "salario=" + salario + ", celular=" + celular + '}';
    }
    
    
    
}
