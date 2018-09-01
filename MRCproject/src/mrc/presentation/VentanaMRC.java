package mrc.presentation;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Esteban Espinoza Fallas 402290345
 * @author Carlos Vargas Alfaro 402170927
 */
public class VentanaMRC extends javax.swing.JFrame implements Observer {

    Model model;
    Controller controller;
    Lienzo lienzo;
    private final FileNameExtensionFilter filter
            = new FileNameExtensionFilter(
                    "Archivos .xml", "xml"
            );

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        Archivo = new javax.swing.JMenu();
        Guardar = new javax.swing.JMenuItem();
        Recuperar = new javax.swing.JMenuItem();
        Limpiar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Archivo.setText("Archivo");

        Guardar.setText("Guardar");
        Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarActionPerformed(evt);
            }
        });
        Archivo.add(Guardar);

        Recuperar.setText("Recuperar");
        Recuperar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecuperarActionPerformed(evt);
            }
        });
        Archivo.add(Recuperar);

        Limpiar.setText("Limpiar");
        Limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LimpiarActionPerformed(evt);
            }
        });
        Archivo.add(Limpiar);

        jMenuBar1.add(Archivo);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1069, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LimpiarActionPerformed
        controller.limpiarProyecto();
    }//GEN-LAST:event_LimpiarActionPerformed

    private void RecuperarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecuperarActionPerformed
        abrir_proyecto();
    }//GEN-LAST:event_RecuperarActionPerformed

    private void GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarActionPerformed
        guardar_proyecto();
    }//GEN-LAST:event_GuardarActionPerformed

    public VentanaMRC() {
        initComponents();
        initListeners();
        setTitle("MRC");
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
        model.addObserver(lienzo);
        ajustarComponentes(getContentPane());
    }

    private void ajustarComponentes(Container c) {
        c.setLayout(new BorderLayout());
        c.add(BorderLayout.CENTER, lienzo);
    }

    public void initListeners() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == VK_ESCAPE) {
                    lienzo.seleccionada = null;
                    lienzo.repaint();
                }
            }
        });
    }

    public void abrir_proyecto() {
        JFileChooser file = new JFileChooser();
        file.setFileFilter(filter);
        int opcion = file.showOpenDialog(this);
        String ruta_Archivo_open = null;
        if (opcion == JFileChooser.APPROVE_OPTION) {
            ruta_Archivo_open = file.getSelectedFile().toString();
        }
        if (ruta_Archivo_open != null) {
            try {
                System.out.println("ruta abrir: " + ruta_Archivo_open);
                controller.limpiarProyecto();
                controller.abrirarchivo(ruta_Archivo_open);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "No se puede cargar el proyecto");
            }
        }
    }

    public void guardar_proyecto() {
        String ruta_Archivo_save = null;
        JFileChooser file_open = new JFileChooser();
        file_open.setFileFilter(filter);
        int opcion = file_open.showSaveDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            ruta_Archivo_save = file_open.getSelectedFile().toString();
        }
        if (ruta_Archivo_save != null) {
            if (".xml".equals(ruta_Archivo_save.substring(ruta_Archivo_save.length() - 4))) {
                System.out.println("ruta guardar: " + ruta_Archivo_save);
                controller.guardarArchivo(ruta_Archivo_save);
            } else {
                ruta_Archivo_save = ruta_Archivo_save + ".xml";
                System.out.println("ruta guardar: " + ruta_Archivo_save);
                controller.guardarArchivo(ruta_Archivo_save);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        lienzo.repaint();
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
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//
//                }
//            }
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            JFrame.setDefaultLookAndFeelDecorated(true);

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaMRC.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaMRC.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaMRC.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaMRC.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaMRC().setVisible(true);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Archivo;
    private javax.swing.JMenuItem Guardar;
    private javax.swing.JMenuItem Limpiar;
    private javax.swing.JMenuItem Recuperar;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
