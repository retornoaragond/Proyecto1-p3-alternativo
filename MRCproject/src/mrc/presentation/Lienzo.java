package mrc.presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.event.MouseEvent.BUTTON1;
import java.awt.geom.Ellipse2D;
import static java.lang.Math.abs;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mrc.logic.Actividad;

/**
 *
 * @author Esteban Espinoza Fallas 402290345
 */
public final class Lienzo extends JPanel implements Observer {

    Controller controller;
    Model model;
    int R = 25;
    int D = 50;
    int Dx = 0;
    int Dy = 0;
    int cont = 1;
    Actividad seleccionada = null;
    Actividad act_movida = null;

    public Lienzo(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
        initListeners();
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
            repaint();
        } else {
            repaint();
        }
    }

    public void selec_relacion(int x, int y) {
        Actividad temp = seleccionar(x, y);
        if (temp != null) {
            try {
                System.out.println("Actividad " + temp.getName());
                controller.relacionar(seleccionada.getName(), temp.getName());
                seleccionada = null;
                repaint();
            } catch (Exception ex) {
                if (null == ex.getMessage()) {
//                    JOptionPane.showMessageDialog(null, "Error: Al relacionar "+seleccionada.getName()+" y "+temp.getName()+" genera un ciclo.");
//                    System.out.printf("Error: Al relacionar %s y %s genera un ciclo.",seleccionada.getName(),temp.getName());
//                    seleccionada = null;
//                    repaint();
                } else //generar ventanita del error
                switch (ex.getMessage()) {
                    case "1":
                        JOptionPane.showMessageDialog(null, "Error: no puede relacionar una actividad con ella misma");
                        System.out.println("Error: no puede relacionar una actividad con ella misma");
                        seleccionada = null;
                        repaint();
                        break;
                    case "2":
                        JOptionPane.showMessageDialog(null, "Error: Ya existe una relacion entre "+seleccionada.getName()+" y "+temp.getName()+".");
                        System.out.printf("Error: Ya existe una relacion %s y %s",seleccionada.getName(),temp.getName());
                        seleccionada = null;
                        repaint();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error: Al relacionar "+seleccionada.getName()+" y "+temp.getName()+" genera un ciclo.");
                        System.out.printf("Error: Al relacionar %s y %s genera un ciclo.",seleccionada.getName(),temp.getName());
                        seleccionada = null;
                        repaint();
                        break;
                }
            }
        } else {
            seleccionada = null;
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

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
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
        //g2.fillOval((int) (x2 - c), (int) (y2 - c), c * 2, c * 2);
        dibujaflecha(new Point((int) x1, (int) y1), new Point((int) x2, (int) y2), g2);
    }

    public void dibujaflecha(Point punto1, Point punto2, Graphics2D g2) {
        double ang, angSep = 45.0;
        double tx, ty;
        int dist = 10;
        ty = -(punto1.y - punto2.y) * 1.0;
        tx = (punto1.x - punto2.x) * 1.0;
        ang = Math.atan(ty / tx);
        if (tx < 0) {
            ang += Math.PI;
        }
        Point p1 = new Point(), p2 = new Point(), punto = punto2;
        p1.x = (int) (punto.x + dist * Math.cos(ang - Math.toRadians(angSep)));
        p1.y = (int) (punto.y - dist * Math.sin(ang - Math.toRadians(angSep)));
        p2.x = (int) (punto.x + dist * Math.cos(ang + Math.toRadians(angSep)));
        p2.y = (int) (punto.y - dist * Math.sin(ang + Math.toRadians(angSep)));
        g2.drawLine(p1.x, p1.y, punto.x, punto.y);
        g2.drawLine(p2.x, p2.y, punto.x, punto.y);
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
        try {
            if (seleccionada != null) {
                g2.setColor(Color.green);
                g2.drawLine(seleccionada.getX(), seleccionada.getY(), this.getMousePosition().x, this.getMousePosition().y);
            }
        } catch (Exception e) {
            System.out.println("el mouse salio de la pantalla.");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
}
