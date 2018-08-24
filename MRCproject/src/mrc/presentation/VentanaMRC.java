package mrc.presentation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import static java.lang.Math.abs;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1069, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 513, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
//        try {
//            if (evt.getClickCount() == 2) {
//                model.agregarActividad(new Actividad("" + cont++, 2, evt.getX(), evt.getY()));
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(VentanaMRC.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_formMouseClicked

    public VentanaMRC() {
        initComponents();
        initListeners();
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }
    
    public void setController(Controller controller){
        this.controller=controller;
    }

    public void initListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                if (ev.getClickCount() == 2) {
                    preAgregarActividad(ev.getX(), ev.getY());
                } else {
                    Actividad seleccionada = seleccionar(ev.getX(), ev.getY());
                    if (seleccionada != null) {
                        setTitle(seleccionada.getName());
                    }
                }
            }
        });
    }

    public Actividad seleccionar(int x, int y) {
        for (Actividad a : model.getPoryect().getActividades().values()) {
            if (new Ellipse2D.Double(a.getX() - R, a.getY() - R, D, D).contains(x, y)) {
                return a;
            }
        }
        return null;
    }

    public void preAgregarActividad(int x, int y){
        JTextField id = new JTextField();
        JTextField duracion = new JTextField();
        Object[] message = {"Id:",id,"Duracion:",duracion};
        int option = JOptionPane.showConfirmDialog(null, message,"Actividad",JOptionPane.OK_CANCEL_OPTION);
        if(option == JOptionPane.OK_OPTION){
            try{
                controller.agregarActividad(id.getText(),Integer.parseInt(duracion.getText()),x,y);
            } catch (Exception ex) {
                
            }
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
    // End of variables declaration//GEN-END:variables
}
