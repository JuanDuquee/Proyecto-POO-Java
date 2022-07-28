
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan David Duque, Jose Daniel Cruz Perdomo, Juan Camilo Ceron
 */
public class UsaPaciente extends javax.swing.JFrame {
    private ArrayList<Paciente> losPacientes = new ArrayList<>();
    private ArrayList<Cotizante> losCotizantes = new ArrayList<>();
    private int consecutivo = -2; //inicia desde -2 para que el numero de registro comience desde cero

    /**
     * Creates new form UsaPaciente
     */
    public UsaPaciente() {
        initComponents();
        
        iniciarDatos(losPacientes);
    }
    
    private int obtenerNuevoConsecutivo(){
        consecutivo++;
        return consecutivo;
    }
    
    public void iniciarDatos(ArrayList<Paciente> info){
        LocalDate fechaAhora= LocalDate.now();
        obtenerNuevoConsecutivo();
//        Iniciar Pacientes
        losPacientes.add(new Cotizante(1000, 322, "Alberto Posada", 10, obtenerNuevoConsecutivo()));
        losPacientes.add(new Cotizante(2000, 310, "Andres Rodriguez", 20, obtenerNuevoConsecutivo()));
        losPacientes.add(new Cotizante(3000, 315, "Jaime Ortiz", 30, obtenerNuevoConsecutivo()));
    
        // Ingresa Servicios
        losPacientes.get(0).agregarServicio(new ServicioMedico("Hospitalización", fechaAhora, 100000));
        losPacientes.get(1).agregarServicio(new ServicioMedico("Cirugia", fechaAhora, 1500000));
        losPacientes.get(2).agregarServicio(new ServicioMedico("Consulta médica con especialista", fechaAhora, 5000));  
    }
  
    public String buscarIdentificacion(ArrayList<Paciente> info, int buscarID){
        String res = "El paciente buscado con el id es: \n\n";
        for (int i = 0; i < info.size(); i++) {
            if(buscarID == info.get(i).getNumID()){
                if(info.get(i) instanceof Cotizante){
                    res += "Cotizante: \n\n" + "nombre: " + info.get(i).nombre + "\n" + "numero de identificacion: " + info.get(i).numID + 
                            "\n" + "numero registro: " + info.get(i).numRegistro + "\n" + "Celular: " + ((Cotizante) info.get(i)).getCelular() + 
                            "\n" + "Salario: " + ((Cotizante) info.get(i)).getSalario() + "\n\n";
                }else{
                    res += "Beneficiario: \n\n" + "nombre: " + info.get(i).nombre + "\n" + "numero de identificacion: " + info.get(i).numID + 
                            "\n" + "numero registro: " + info.get(i).numRegistro + "\n" + "Tipo Identificacion: " + 
                            ((Beneficiario) info.get(i)).getTipoID() + "\n\n";
                }
            } 
        }
        String res1 = "";
        for (int i = 0; i < info.size(); i++) {
          for (int j = 0; j < info.get(i).suServicioMedico.size(); j++) {
            if(buscarID == info.get(i).getNumID()){ 
              res1 += "Servicio Medico: " + "\n" + "Tipo de servicios: " + info.get(i).suServicioMedico.get(j).getSuTipoServicio() + 
                            "\n" + "Fecha del servicio: " + info.get(i).suServicioMedico.get(j).getFechaValida() + "\n"
                            + "Costo Total: " + info.get(i).suServicioMedico.get(j).getCostoTotal() + "\n\n";
            }  
        }
        }
        return res + res1;
    }
    
    public int buscarPaciente(ArrayList<Paciente> info, int idPaciente)
    {
        int pos = -1;
        for (int i = 0; i < info.size(); i++) {
            if (idPaciente ==(info.get(i).getNumID())){
                pos = i;
                break;
            }            
        }
        return pos;
    }
    
    public int buscarCotizante(ArrayList<Cotizante> info, int idCotizante)
    {
        int pos = -1;
        for (int i = 0; i < info.size(); i++) {
            if (idCotizante ==(info.get(i).getNumID())){
                pos = i;
                break;
            }            
        }
        return pos;
    }
    
    public String listarPaciente(ArrayList<Paciente> info){
        String res = "Los pacientes listados son:\n\n";
//        Ordenamiento ascendente por el numero de identificación
        info.sort(
            (x,y) -> Integer.compare(x.getNumID(), y.getNumID())
        );
        
//        Para ordenar strings
//        info.sort(
//            (x,y) -> x.getNombre().compareToIgnoreCase(y.getNombre())
//        );
            for (int i = 0; i < info.size(); i++) {
//            el toString esta modificado para reportar la informacion del paciente que se pide y la fecha + su tipo de servicio medico
              res += info.get(i).toString();
        }
        return res;
    }

    public void almacenarInformacion(ArrayList<Paciente> info) {
        String nombreArchivo = "paciente.obj";
        ObjectOutputStream salida = null;
        try {
            // almacenamos infor
            salida = new ObjectOutputStream(
                    new FileOutputStream(nombreArchivo, true));
            salida.writeObject(info);
            
            JOptionPane.showMessageDialog(null, "Se han almacenado los datos exitosamente");
        } catch (Exception e) {
            System.out.println("Error almacenando los datos "+e.getMessage()) ;
        } finally {
            try {
                salida.close();
            } catch (Exception e) {
                System.out.println("Error cerrando ");
            }
        }
    }
    
    public void recuperarArchivoObjetos(ArrayList<Paciente> bd) 
    {
        
        // Actualizar el nombre del archivo
        String nombreArchivo = "paciente.obj";
        ArrayList<Paciente> laBD=new ArrayList();
        ObjectInputStream  entrada=null;
        try{
            // lectura de datos
            entrada  =   new  ObjectInputStream( 
                    new  FileInputStream(nombreArchivo)) ;

            // leer flujo y almacenarlo en laBD. Usar casting        
            laBD=(ArrayList<Paciente>) entrada.readObject();
            // opcional el borrar los datos existentes en bd
            bd.clear();
            JOptionPane.showMessageDialog(null,
                    "La información de los pacientes fue eliminada");
            // pasar los datos de laBD a bd
                    bd.addAll(laBD);
                    
            JOptionPane.showMessageDialog(null, "Se han recuperado los datos exitosamente");
        } catch( Exception  e ) { 
            JOptionPane.showMessageDialog(null,"Error recuperando los datos "+e.getMessage()+" -- "+e.toString()) ;
        } finally
        {
            try{
                entrada.close();
            } catch(Exception e){
               JOptionPane.showMessageDialog(null,"Error cerrando ") ;             
            }
        }                              
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        InsertarPaciente = new javax.swing.JButton();
        ServicioMedico = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        ReportePacientes = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        ReportexIdentificacion = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        Salida = new javax.swing.JButton();
        AlmacenarDatos = new javax.swing.JButton();
        RecuperarDatos = new javax.swing.JButton();
        Limpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        InsertarPaciente.setText("Insertar Paciente");
        InsertarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertarPacienteActionPerformed(evt);
            }
        });

        ServicioMedico.setText("Ingresar Servicio");
        ServicioMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ServicioMedicoActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Cotizante");

        jLabel12.setText("Salario");

        jLabel13.setText("Celular");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Beneficiario");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Registro de Nacimiento", "Tarjeta de identidad", "Cedula" }));

        jLabel5.setText("Tipo Id:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, 184, Short.MAX_VALUE))
                    .addComponent(jLabel11)
                    .addComponent(jLabel14)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(jTextField7))))
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Paciente");

        jLabel2.setText("Nombre");

        jLabel3.setText("numID");

        jLabel9.setText("Tipo Paciente");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cotizante", "Beneficiario" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)))
                    .addComponent(jLabel1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(53, 53, 53))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 0, 0));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Servicios Medicos");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Consulta médica general", "Consulta médica especialista", "Cirugía", "Hospitalización" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel6.setText("Servicio Medico");

        jLabel8.setText("Costo Servicio:");

        jLabel15.setText("Tipo Servicio:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(3, 3, 3)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 10, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel16.setText("MED SALUD");

        ReportePacientes.setText("Reportar Pacientes1");
        ReportePacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportePacientesActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Reportar por id:");

        ReportexIdentificacion.setText("Reportar Pacientes2");
        ReportexIdentificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportexIdentificacionActionPerformed(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Reportar todos:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(772, 772, 772))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(303, 303, 303)
                        .addComponent(jLabel16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(194, 194, 194)
                                .addComponent(InsertarPaciente)
                                .addGap(340, 340, 340))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(ReportePacientes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ReportexIdentificacion)
                                .addGap(5, 5, 5)))
                        .addComponent(ServicioMedico)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ServicioMedico)
                    .addComponent(InsertarPaciente))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ReportePacientes)
                    .addComponent(jLabel10)
                    .addComponent(ReportexIdentificacion)
                    .addComponent(jLabel17))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(153, 255, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addContainerGap())
        );

        Salida.setText("Salir");
        Salida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalidaActionPerformed(evt);
            }
        });

        AlmacenarDatos.setText("Almacenar Datos");
        AlmacenarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlmacenarDatosActionPerformed(evt);
            }
        });

        RecuperarDatos.setText("Recuperar Datos");
        RecuperarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecuperarDatosActionPerformed(evt);
            }
        });

        Limpiar.setText("Limpiar");
        Limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(AlmacenarDatos)
                .addGap(59, 59, 59)
                .addComponent(RecuperarDatos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Salida)
                .addGap(79, 79, 79)
                .addComponent(Limpiar)
                .addGap(53, 53, 53))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Salida)
                    .addComponent(AlmacenarDatos)
                    .addComponent(RecuperarDatos)
                    .addComponent(Limpiar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void InsertarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertarPacienteActionPerformed
        try {
            String tipoPaciente = jComboBox3.getSelectedItem().toString();

            String elNombre = jTextField1.getText();
            int elNumID = Integer.parseInt(jTextField2.getText());
            int numRegistro = obtenerNuevoConsecutivo();

            if (tipoPaciente.equalsIgnoreCase("Cotizante")) {
                double elSalario = Double.parseDouble(jTextField6.getText());
                int elCelular = Integer.parseInt(jTextField7.getText());
 
                Cotizante obj = new Cotizante(elSalario, elCelular, elNombre, elNumID, numRegistro);
                losPacientes.add(obj);
                losCotizantes.add(obj);
                JOptionPane.showMessageDialog(null, "Se ha ingresado el paciente satisfactoriamente");
                
            } else {
                String suTipoID = jComboBox1.getSelectedItem().toString();
                int idCotizante = Integer.parseInt(JOptionPane.showInputDialog("Digite el id de su cotizante"));
                int pos = buscarCotizante(losCotizantes, idCotizante);
                if (pos == -1) {
                    JOptionPane.showMessageDialog(null, "Cotizante inexistente");
                } else {
                    losPacientes.add(new Beneficiario(suTipoID, losCotizantes.get(pos), elNombre, elNumID, numRegistro));
                    JOptionPane.showMessageDialog(null, "Se ha ingresado el paciente satisfactoriamente");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se dió un error al ejecutar estas instrucciones"
                    + "\n info Mesagge:" + e.getMessage());
        }
    }//GEN-LAST:event_InsertarPacienteActionPerformed

    private void SalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalidaActionPerformed
        System.exit(0);
    }//GEN-LAST:event_SalidaActionPerformed

    private void ReportePacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportePacientesActionPerformed
        try{
        jTextArea1.setText(listarPaciente(losPacientes));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Se dio un error al ejecutar estas instrucciones"+
                    "\n info Message:" +e.getMessage()+
                    "\n info ToString:"+e.toString());
        }
    }//GEN-LAST:event_ReportePacientesActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void ServicioMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ServicioMedicoActionPerformed
        try{
        int elNumID =  Integer.parseInt(JOptionPane.showInputDialog("Digite el id del paciente al que"+
                    "\nasignara el servicio")); 
        
        int pos = buscarPaciente(losPacientes, elNumID);
        
        if(pos == -1){
           JOptionPane.showMessageDialog(null,"Paciente inexistente"); 
        }else{
            String tipoServicio = jComboBox2.getSelectedItem().toString();
            LocalDate fechaValida = LocalDate.parse(
                            JOptionPane.showInputDialog("Digite la fecha valida en formato(AAAA-MM-DD)"));
            double elCosto = Double.parseDouble(jTextField4.getText());
            losPacientes.get(pos).agregarServicio(new ServicioMedico(tipoServicio, fechaValida, elCosto));

            double res=0.0;
            if(losPacientes.get(pos) instanceof Cotizante){
                res = ((Cotizante) losPacientes.get(pos)).determinarCuotaModeradora();
                    JOptionPane.showMessageDialog(null,"Valor total a pagar: " + res);
            }else{
               if(tipoServicio.equalsIgnoreCase ("Cirugía") || 
                        tipoServicio.equalsIgnoreCase("Hospitalización")){
                   res = Math.round(((Beneficiario) losPacientes.get(pos)).determinarCuotaCopago(elCosto));
                   JOptionPane.showMessageDialog(null,"Valor total a pagar: " + res);
               }else{
                   res = ((Beneficiario) losPacientes.get(pos)).getSuCotizante().determinarCuotaModeradora();
                   JOptionPane.showMessageDialog(null,"Valor total a pagar: " + res);
               } 
            }
            
           
        }
        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Se dio un error al ejecutar estas instrucciones"+
                    "\n info Message:" +e.getMessage()+
                    "\n info ToString:"+e.toString());
        }
    }//GEN-LAST:event_ServicioMedicoActionPerformed

    private void RecuperarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecuperarDatosActionPerformed
        recuperarArchivoObjetos(losPacientes);
    }//GEN-LAST:event_RecuperarDatosActionPerformed

    private void AlmacenarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlmacenarDatosActionPerformed
        almacenarInformacion(losPacientes);
    }//GEN-LAST:event_AlmacenarDatosActionPerformed

    private void LimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpiarActionPerformed
        jTextArea1.setText("");
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField4.setText("");
        jTextField6.setText("");
        jTextField7.setText("");   
    }//GEN-LAST:event_LimpiarActionPerformed

    private void ReportexIdentificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportexIdentificacionActionPerformed
        try {
        int buscarID = Integer.parseInt(JOptionPane.showInputDialog("Digite el id del paciente que desea mostrar"));
        jTextArea1.setText(buscarIdentificacion(losPacientes, buscarID));
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se dió un error al ejecutar estas instrucciones"
                    + "\n info Mesagge:" + e.getMessage());
        }
    }//GEN-LAST:event_ReportexIdentificacionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UsaPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsaPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsaPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsaPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UsaPaciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AlmacenarDatos;
    private javax.swing.JButton InsertarPaciente;
    private javax.swing.JButton Limpiar;
    private javax.swing.JButton RecuperarDatos;
    private javax.swing.JButton ReportePacientes;
    private javax.swing.JButton ReportexIdentificacion;
    private javax.swing.JButton Salida;
    private javax.swing.JButton ServicioMedico;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
