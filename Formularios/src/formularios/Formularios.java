
package formularios;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class Formularios {
//atributos
    JFrame f = new JFrame();
    JPanel p1 = new JPanel();
    Object filas[][] = new Object[50][3];
    int x = 0;
    JTable table = new JTable();
    JScrollPane sp = new JScrollPane();

    public void save() {

        try {
            ObjectOutputStream tabla = new ObjectOutputStream(new FileOutputStream("registros.dat"));
            tabla.writeObject(filas);
            tabla.close();
        } catch (IOException s) {
        }

    }

    public void load() throws ClassNotFoundException {

        try {

            ObjectInputStream recuperar = new ObjectInputStream(new FileInputStream("registros.dat"));
                
            filas = (Object[][]) recuperar.readObject();
            recuperar.close();

        } catch (IOException e) {
        }

    }

    public void form() throws ClassNotFoundException {
        load();
        f.setTitle("Ejemplo");
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setBounds(50, 175, 800, 400);
        f.setVisible(true);

        p1.setBackground(Color.YELLOW);
        p1.setLayout(null);
        f.add(p1);

        JLabel l1 = new JLabel("Codigo:");
        l1.setBounds(50, 50, 100, 25);
        p1.add(l1);

        JTextField t1 = new JTextField();
        t1.setBounds(150, 50, 100, 25);
        p1.add(t1);

        JLabel l2 = new JLabel("Nombre:");
        l2.setBounds(50, 125, 100, 25);
        p1.add(l2);

        JTextField t2 = new JTextField();
        t2.setBounds(150, 125, 100, 25);
        p1.add(t2);

        JLabel l3 = new JLabel("Lugar:");
        l3.setBounds(50, 200, 100, 25);
        p1.add(l3);

        JTextField t3 = new JTextField();
        t3.setBounds(150, 200, 100, 25);
        p1.add(t3);

        Calendar calendar = new GregorianCalendar();
        String date = "" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);

        JButton b1 = new JButton("Guardar");
        b1.setBounds(125, 325, 100, 25);
        p1.add(b1);

        ActionListener guardar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(t1.getText().equals("")) && !(t2.getText().equals("")) ) {
                    try {
                        agregar(Integer.parseInt(t1.getText()), t2.getText(), t3.getText());
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(formulario.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    t1.setText(null);
                    t2.setText(null);
                    t3.setText(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Completa los datos");
                }
            }
        };

        b1.addActionListener(guardar);

    }

    public void tabla() throws ClassNotFoundException {

        String[] columnas = {"Codigo", "nombre", "Lugar"};
        load();
        table = new JTable(filas, columnas);
        sp = new JScrollPane(table);
        sp.setSize(400, 250);
        sp.setLocation(300, 50);
        sp.setVisible(true);
        p1.add(sp);

    }

    private void agregar(int codigo, String nombre, String lugar) throws ClassNotFoundException {

        for (int i = 0; i < filas.length; i++) {
            if (filas[i][0] == null) {
                filas[i][0] = codigo;
                filas[i][1] = nombre;
                filas[i][2] = lugar;
                break;
            }

            save();
            sp.setVisible(false);
            tabla();

        }

    }

    public static void main(String[] args) throws ClassNotFoundException {
        Formularios f = new Formularios();
        f.form();
        f.tabla();
    }
    
   
    
}
