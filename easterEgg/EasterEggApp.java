import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EasterEggApp extends JFrame {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JTextField textField;
    private JLabel label;
    private int easterEggStep = 0;

    public EasterEggApp() {
        setTitle("Easter Egg Example");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        button3 = new JButton("Button 3");
        textField = new JTextField(10);
        label = new JLabel("Wcisnij odpowiednia kombinacje Easter Egg!");

        setLayout(new FlowLayout());
        add(button1);
        add(button2);
        add(button3);
        add(textField);
        add(label);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (easterEggStep == 0) {
                    easterEggStep++;
                } else {
                    easterEggStep = 0;
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (easterEggStep == 1) {
                    easterEggStep++;
                } else {
                    easterEggStep = 0;
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (easterEggStep == 2) {
                    easterEggStep++;
                } else {
                    easterEggStep = 0;
                }
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (easterEggStep == 3 && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JOptionPane.showMessageDialog(null, "Boom!");
                    easterEggStep = 0;
                } else {
                    easterEggStep = 0;
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EasterEggApp().setVisible(true);
            }
        });
    }
}
