import java.sql.*;
import javax.swing.table.DefaultTableModel;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Victor
 */
public class VistaAlumnos extends javax.swing.JFrame {

    /**
     * Creates new form VistaAlumnos
     */
    DefaultTableModel modelo;
    Controlador control;
    public VistaAlumnos() {
        setLocationRelativeTo(null);
        control = new Controlador();
        modelo = new DefaultTableModel();
        initComponents();
        rellenarCurso();
        rellenarEvaluacion();
        modelo.addColumn(" ");        
        modelo.addRow(new Object[]{"Insuficiente"});
        modelo.addRow(new Object[]{"Suficiente"});
        modelo.addRow(new Object[]{"Bien"});
        modelo.addRow(new Object[]{"Notable"});
        modelo.addRow(new Object[]{"Sobresaliente"});
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cCurso = new java.awt.Choice();
        cEvaluacion = new java.awt.Choice();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tResultado = new javax.swing.JTable();
        bConsultar = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Estadisticas Notas");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Seleccion Curso/Evaluacion");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 21, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 730, -1));

        jLabel2.setText("Curso:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, -1, -1));

        jLabel3.setText("Evaluacion:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));
        getContentPane().add(cCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 140, -1));

        cEvaluacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rellenarAsignatura(evt);
            }
        });
        getContentPane().add(cEvaluacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 140, -1));

        jLabel4.setText("Estadisticas notas");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 162, 720, 10));

        tResultado.setModel(modelo);
        jScrollPane1.setViewportView(tResultado);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 590, 190));

        bConsultar.setText("Consultar");
        bConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rellenarNotas(evt);
            }
        });
        getContentPane().add(bConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 240, -1, -1));

        bSalir.setText("Salir");
        bSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSalirActionPerformed(evt);
            }
        });
        getContentPane().add(bSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 290, 90, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_bSalirActionPerformed

    private void rellenarAsignatura(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rellenarAsignatura
        ResultSet resultado = control.obtenerAsignaturas(cEvaluacion.getSelectedItem(), cCurso.getSelectedItem());
        
        try {
            if (resultado != null) {
                modelo.setColumnCount(1);
                while (resultado.next()) {                    
                    modelo.addColumn( resultado.getString("asignatura") );
                }
            }else{
                System.out.println("Objeto Vacio");
            }
        } catch (SQLException e) {
            System.out.println("Fallo rellenarAsignatura()");
        }
    }//GEN-LAST:event_rellenarAsignatura

    private void rellenarNotas(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rellenarNotas
        ResultSet resultado;
        int insuficiente,suficiente,bien,notable,sobresaliente;
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            insuficiente = 0;
            suficiente = 0;
            bien = 0;
            notable = 0;
            sobresaliente = 0;
            resultado  = control.obtenerNotas(cEvaluacion.getSelectedItem(), cCurso.getSelectedItem(), modelo.getColumnName(i));
            for (int j = 0; j < modelo.getRowCount(); j++) {
                try {
                    if (resultado != null) {
                        if (resultado.getInt("nota") < 5 ) {
                            insuficiente++;
                            modelo.setValueAt(insuficiente, i, j);
                        }else if (resultado.getInt("nota") == 5) {
                            suficiente++;
                        } else if(resultado.getInt("nota") == 6){
                            bien++;
                        }else if (resultado.getInt("nota") >= 7 && resultado.getInt("nota") <= 8) {
                            notable++;
                        } else {
                            sobresaliente++;
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Fallo rellenarNotas");
                }
            }
        }
    }//GEN-LAST:event_rellenarNotas
         
    private void rellenarCurso(){
        ResultSet resultado = control.obtenerRegistros("cursos", "curso");
                
        try {
            if (resultado != null) {
                while (resultado.next()) {                    
                    cCurso.addItem( resultado.getString("curso") );
                }
            }else{
                System.out.println("Objeto Vacio");
            }
        } catch (Exception e) {
            System.out.println("Fallo rellenarCurso()");
        }
    }
    
    private void rellenarEvaluacion(){
        ResultSet resultado = control.obtenerRegistros("notas", "evaluacion");
                
        try {
            if (resultado != null) {
                while (resultado.next()) {                    
                    cEvaluacion.addItem( resultado.getString("evaluacion") );
                }
            }else{
                System.out.println("Objeto Vacio");
            }
        } catch (Exception e) {
            System.out.println("Fallo rellenarCurso()");
        }
    }
    
    
    
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
            java.util.logging.Logger.getLogger(VistaAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaAlumnos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bConsultar;
    private javax.swing.JButton bSalir;
    private java.awt.Choice cCurso;
    private java.awt.Choice cEvaluacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tResultado;
    // End of variables declaration//GEN-END:variables
}
