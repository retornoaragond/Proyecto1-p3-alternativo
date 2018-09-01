package mrc.presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.BUTTON1;
import java.awt.geom.Ellipse2D;
import static java.lang.Math.abs;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import mrc.logic.Actividad;

/**
 * @author Esteban Espinoza Fallas 402290345
 * @author Carlos Vargas Alfaro 402170927
 */
public class VentanaMRC extends javax.swing.JFrame implements Observer {

    Model model;
    Controller controller;
    int R = 25;
    int D = 50;
    int Dx = 0;
    int Dy = 0;
    int cont = 1;
    int dibujado = 0;
    Actividad seleccionada = null;
    Actividad act_movida = null;
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
                if (evt.getButton() == BUTTON1) {
                    if (evt.getClickCount() == 2) {
                        agregarActividad(evt.getX(), evt.getY());
                    } else {
                        if (seleccionada == null) {
                            selec_actividad(evt.getX(), evt.getY());
                        } else {
                            selec_relacion(evt.getX(), evt.getY());
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                act_movida = seleccionar(evt.getX(), evt.getY());
                if (act_movida != null) {
                    Dx = act_movida.getX() - evt.getX();
                    Dy = act_movida.getY() - evt.getY();
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
                if (act_movida != null) {
                    setTitle("Moviendo: " + act_movida.getName());
                    moveCmouse(act_movida, evt.getX(), evt.getY());
                    repaint();
                }
            }
        });
    }

    public void selec_actividad(int x, int y) {
        seleccionada = seleccionar(x, y);
        if (seleccionada != null) {
            System.out.println("Actividad " + seleccionada.getName());
            setTitle("salida " + seleccionada.getName());
            repaint();
        } else {
            setTitle("MRC");
            repaint();
        }
    }

    public void selec_relacion(int x, int y) {
        Actividad temp = seleccionar(x, y);
        if (temp != null) {
            try {
                System.out.println("Actividad " + temp.getName());
                controller.relacionar(seleccionada.getName(), temp.getName());
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

    public void agregarActividad(int x, int y) {//
        JTextField id = new JTextField();
        JTextField duracion = new JTextField();
        Object[] message = {"Id:", id, "Duracion:", duracion};
        boolean er = false;
        do {
            int option = JOptionPane.showConfirmDialog(null, message, "Actividad", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    Integer.parseInt(duracion.getText());
                    controller.agregarActividad(id.getText(), Integer.parseInt(duracion.getText()), x, y);
                    er = false;
                } catch (NumberFormatException e) {
                    er = true;
                    JOptionPane.showMessageDialog(null,
                            "La duracion debe ser un numero.");
                    System.out.println("Error al tratar de agregar una actividad:\n La duracion debe ser un numero");
                } catch (Exception ex) {
                    er = true;
                    if ("1".equals(ex.getMessage())) {
                        JOptionPane.showMessageDialog(null,
                                "Error al tratar de agregar una actividad:\n Ya existe una actividad con el mismo ID");
                        System.out.println("Error al tratar de agregar una actividad: Ya existe una actividad con el mismo ID");
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Error al tratar de agregar una actividad:\n La duracion debe ser mayor que 0");
                        System.out.println("Error al tratar de agregar una actividad: La duracion debe ser mayor que cero");
                    }
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
        Graphics2D g2 = (Graphics2D) g;
        //para manipular el renderizado 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(3));
        g2.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 16));
        ///////////////////////////////////////////////////////////////
        model.proyecto.getActividades().values().stream().map((a) -> {
            if (a != seleccionada) {
                if (a.getHolgura() == 0) {
                    g2.setColor(Color.red);
                } else {
                    g2.setColor(Color.black);
                }
            } else {
                g2.setColor(Color.green);
            }
            return a;
        }).map((a) -> {
            g2.drawOval(a.getX() - R, a.getY() - R, D, D);
            return a;
        }).forEachOrdered((a) -> {
            g2.drawString(a.getName() + "(" + a.getDtime() + ")",
                    a.getX() - R + 5, a.getY() + 5);
        });
        model.proyecto.getActividades().values().forEach((a) -> {
            a.getSalidas().stream().filter((act) -> (!("n_f".equals(act.getName())))).map((act) -> {
                if (a.getHolgura() == 0 && act.getHolgura() == 0) {
                    g2.setColor(Color.red);
                } else {
                    g2.setColor(Color.blue);
                }
                return act;
            }).forEachOrdered((act) -> {
                relaciones(a, act, g2);
            });
        });
        prerelacion(g2);
    }

    public void relaciones(Actividad a, Actividad b, Graphics2D g2) {
        double x1 = a.getX();
        double y1 = a.getY();
        double x2 = b.getX();
        double y2 = b.getY();
        int c = 5;
        x1 = (x1 + (((x2 - x1) * R) / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))));
        y1 = (y1 + (((y2 - y1) * R) / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))));
        x2 = (x2 - (((x2 - x1) * R) / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))));
        y2 = (y2 - (((y2 - y1) * R) / Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))));
        g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        g2.fillOval((int) (x2 - c), (int) (y2 - c), c * 2, c * 2);
    }

    public void moveCmouse(Actividad a, int mx, int my) {
        if (Dy < 0) {
            if (Dx < 0) {
                controller.moveractividad(a.getName(), mx - abs(Dx), my - abs(Dy));
            } else {
                controller.moveractividad(a.getName(), mx + abs(Dx), my - abs(Dy));
            }
        } else {
            if (Dx < 0) {
                controller.moveractividad(a.getName(), mx - abs(Dx), my + abs(Dy));
            } else {
                controller.moveractividad(a.getName(), mx + Dx, my + abs(Dy));
            }
        }
    }

    public void prerelacion(Graphics2D g2) {
        if (seleccionada != null) {
            g2.setColor(Color.green);
            g2.drawLine(seleccionada.getX(), seleccionada.getY(), this.getMousePosition().x, this.getMousePosition().y);
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
