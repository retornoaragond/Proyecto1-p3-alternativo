package mrc.presentation;

import com.sun.webkit.graphics.GraphicsDecoder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import static java.lang.Math.abs;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import mrc.logic.Actividad;

/**
 * @author Esteban Espinoza Fallas 402290345
 * @author Carlos Vargas Alfaro 402170927
 */
public class VentanaMRC extends javax.swing.JFrame implements Observer {

    Model model;
    Controller controller;
    int R = 20;
    int D = 40;
    int cont = 1;
    Actividad seleccionada = null;
    Actividad movida = null;
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

    public void initListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    preAgregarActividad(evt.getX(), evt.getY());
                } else {
                    if (seleccionada == null) {
                        seleccionada = seleccionar(evt.getX(), evt.getY());
                        if (seleccionada != null) {
                            System.out.println("Actividad " + seleccionada.getName());
                            setTitle("salida " + seleccionada.getName());
                        } else {
                            setTitle("MRC");
                        }
                    } else {
                        Actividad temp = seleccionar(evt.getX(), evt.getY());
                        if (temp != null) {
                            try {
                                System.out.println("Actividad " + temp.getName());
                                controller.Relacionar(seleccionada.getName(), temp.getName());
                                setTitle("entrada " + temp.getName());
                                seleccionada = null;
                                repaint();
                            } catch (Exception ex) {
                                //generar ventanita del error
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                                System.out.println(ex.getMessage());
                                seleccionada = null;
                                repaint();
                            }
                        } else {
                            seleccionada = null;
                            setTitle("MRC");
                            repaint();
                        }
                    }
                }
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent evt) {
                if (seleccionada != null) {
                    repaint();
                }

            }

            @Override
            public void mouseDragged(MouseEvent evt) {
                movida = seleccionar(evt.getX(), evt.getY());
                if (movida != null) {
                    setTitle("Moviendo: " + movida.getName());
                    movida.setX(evt.getX());
                    movida.setY(evt.getY());
                }
                repaint();
            }
        });
    }

    public Actividad seleccionar(int x, int y) {
        if (!model.getPoryect().getActividades().isEmpty()) {
            for (Actividad a : model.getPoryect().getActividades().values()) {
                if (new Ellipse2D.Double(a.getX() - R, a.getY() - R, D, D).contains(x, y)) {
                    return a;
                }
            }
        }
        return null;
    }

    public void preAgregarActividad(int x, int y) {
        JTextField id = new JTextField();
        JTextField duracion = new JTextField();
        Object[] message = {"Id:", id, "Duracion:", duracion};
        boolean er = false;
        do {
            int option = JOptionPane.showConfirmDialog(null, message, "Actividad", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    Integer.parseInt(duracion.getText());
                } catch (NumberFormatException e) {
                    er = true;
                    JOptionPane.showMessageDialog(null,
                            "La duracion debe ser un numero.");
                    System.out.println("error la duracion debe ser un numero");
                }
                try {
                    controller.agregarActividad(id.getText(), Integer.parseInt(duracion.getText()), x, y);
                    er = false;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "No se puede crear la activadad.");
                    System.out.println("No se puede crear la activadad");
                }
            } else {
                er = false;
            }
        } while (er);
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
                            "No se puede crear la activadad.");  
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
            System.out.println("ruta guardar: " + ruta_Archivo_save);
            controller.guardarArchivo(ruta_Archivo_save);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Actividad a : model.proyecto.getActividades().values()) {
            if (a.getHolgura() == 0) {
                g.setColor(Color.red);
            } else {
                g.setColor(Color.black);
            }
            g.drawOval(a.getX() - R, a.getY() - R, D, D);
            g.drawString(a.getName() + "(" + a.getDtime() + ")",
                    a.getX() - R + 5, a.getY() + 5);
        }
        for (Actividad a : model.proyecto.getActividades().values()) {
            for (Actividad act : a.getSalidas()) {
                if (!("n_f".equals(act.getName()))) {
                    if (a.getHolgura() == 0 && act.getHolgura() == 0) {
                        g.setColor(Color.red);
                    } else {
                        g.setColor(Color.black);
                    }
                    relaciones(a, act, g);
                }
            }
        }
        prerelacion(g);
    }

    public void relaciones(Actividad a, Actividad b, Graphics g) {
        int BX = a.getX() - b.getX();
        int BY = a.getY() - b.getY();
        int BH = (int) Math.sqrt(Math.pow(abs(BX), 2) + Math.pow(abs(BY), 2));
        int abx = R * abs(BX) / BH;
        int aby = R * abs(BY) / BH;
        int c = 3;
        if (BY < 0) {
            if (BX < 0) {
                g.drawLine(a.getX() + abx, a.getY() + aby, b.getX() - abx, b.getY() - aby);
                g.fillOval(b.getX() - abx - c, b.getY() - aby - c, 6, 6);
            } else {
                g.drawLine(a.getX() - abx, a.getY() + aby, b.getX() + abx, b.getY() - aby);
                g.fillOval(b.getX() + abx - c, b.getY() - aby - c, 6, 6);
            }
        } else {
            if (BX < 0) {
                g.drawLine(a.getX() + abx, a.getY() - aby, b.getX() - abx, b.getY() + aby);
                g.fillOval(b.getX() - abx - c, b.getY() + aby - c, 6, 6);
            } else {
                g.drawLine(a.getX() - abx, a.getY() - aby, b.getX() + abx, b.getY() + aby);
                g.fillOval(b.getX() + abx - c, b.getY() + aby - c, 6, 6);
            }
        }
    }

    public void prerelacion(Graphics g) {
        if (seleccionada != null) {
            g.setColor(Color.green);
            g.drawLine(seleccionada.getX(), seleccionada.getY(), this.getMousePosition().x, this.getMousePosition().y);
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
