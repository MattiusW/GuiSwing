package skrzynka;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Skrzynka extends JFrame {

    private JButton przycisk1, przycisk2, przycisk3;

    public Skrzynka() throws IOException {
        setTitle("Skrzynka gra");
        setSize(400, 400);
        setLocationRelativeTo(null); // Ustawienie okienka na środku ekranu
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false); // Zablokowanie możliwosci powiększania ekranu
        this.setLayout(null);

        // JPanel
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(0, 0, 400, 400);
        jPanel.setBackground(Color.GRAY);

        // Dodanie obrazka
        BufferedImage skrzyniaObrazek = ImageIO.read(new File("skrzynka/Skrzynka.png"));
        JLabel skrzynia = new JLabel(new ImageIcon(skrzyniaObrazek));

        // Dodanie przycisków
        przycisk1 = new JButton();
        przycisk2 = new JButton();
        przycisk3 = new JButton();

        // Zmiana koloru przycisków
        przycisk1.setBackground(Color.RED);
        przycisk2.setBackground(Color.RED);
        przycisk3.setBackground(Color.RED);

        // Ustawienie pozycji przycisków
        przycisk1.setBounds(100, 200, 50, 50);
        przycisk2.setBounds(150, 200, 50, 50);
        przycisk3.setBounds(200, 200, 50, 50);
        skrzynia.setBounds(80, 50, 200, 200);

        // Dodanie akcji dla przycisków
        ActionListener akcja = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton nacisnietyPrzycisk = (JButton) e.getSource();
                Color kolorPrzycisku = nacisnietyPrzycisk.getBackground();

                if (kolorPrzycisku.equals(Color.GREEN)) {
                    nacisnietyPrzycisk.setBackground(Color.RED);
                    nacisnietyPrzycisk.setText("X");
                }

                else {
                    nacisnietyPrzycisk.setBackground(Color.GREEN);
                    nacisnietyPrzycisk.setText("O");
                }
            }
        };

        przycisk1.addActionListener(akcja);
        przycisk2.addActionListener(akcja);
        przycisk3.addActionListener(akcja);

        // Dodawanie przycisków
        jPanel.add(przycisk1);
        jPanel.add(przycisk2);
        jPanel.add(przycisk3);
        jPanel.add(skrzynia);

        add(jPanel);

        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Skrzynka();
    }

}
