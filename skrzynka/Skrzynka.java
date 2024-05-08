package skrzynka;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Skrzynka extends JFrame {

    private JButton przycisk1, przycisk2, przycisk3;
    private JLabel skrzynia;
    private JLabel pokazOdliczanie;
    private JLabel powiadomenie;
    private int licznik = 5;

    public Skrzynka() throws IOException {
        ArrayList<String> listaWylosowanych = randomCombination(); // Losowanie kombinacji

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
        BufferedImage skrzyniaOtwarta = ImageIO.read(new File("skrzynka/SkrzynkaOtwarta.png"));
        BufferedImage wybuch = ImageIO.read(new File("skrzynka/Wybuch.png"));
        skrzynia = new JLabel(new ImageIcon(skrzyniaObrazek));

        // Dodanie przycisków
        przycisk1 = new JButton();
        przycisk2 = new JButton();
        przycisk3 = new JButton();

        // Zmiana koloru przycisków
        przycisk1.setBackground(Color.RED);
        przycisk2.setBackground(Color.RED);
        przycisk3.setBackground(Color.RED);

        // Dodanie odliczanie
        pokazOdliczanie = new JLabel("Odliczanie do zakłady");
        pokazOdliczanie.setBounds(170, 270, 300, 50);
        powiadomenie = new JLabel();

        // Ustawienie pozycji przycisków
        przycisk1.setBounds(100, 200, 50, 50);
        przycisk2.setBounds(150, 200, 50, 50);
        przycisk3.setBounds(200, 200, 50, 50);
        skrzynia.setBounds(80, 50, 200, 200);
        powiadomenie.setBounds(80, 170, 300, 200);

        // Odliczanie do przegranej
        Thread odliczanie = new Thread(new Runnable() {
            @Override
            public void run() {
                while (licznik > 0 && !Thread.currentThread().isInterrupted()) {
                    pokazOdliczanie.setText(Integer.toString(licznik));
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        return; // Zatrzymanie wątku po wyjątku
                    }
                    licznik--;
                    // Warunek przegranej
                    if (licznik == 0) {
                        jPanel.setBackground(Color.RED);
                        pokazOdliczanie.setText("BOOM!");
                        skrzynia.setIcon(new ImageIcon(wybuch));
                        JLabel skrzynia = new JLabel(new ImageIcon(wybuch));
                        JOptionPane.showMessageDialog(jPanel, "Przegrałeś!!!");
                        // Zablokowanie możliwości interakcji
                        blokowaniePrzyciskow(jPanel);
                    }
                }
            }
        });
        odliczanie.start();

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

                if (listaWylosowanych.get(0).equals(przycisk1.getText())
                        && listaWylosowanych.get(1).equals(przycisk2.getText())
                        && listaWylosowanych.get(2).equals(przycisk3.getText())) {
                    odliczanie.interrupt();
                    przycisk1.setBackground(Color.GREEN);
                    przycisk2.setBackground(Color.GREEN);
                    przycisk3.setBackground(Color.GREEN);
                    skrzynia.setIcon(new ImageIcon(skrzyniaOtwarta));
                    JLabel skrzynia = new JLabel(new ImageIcon(skrzyniaObrazek));
                    jPanel.setBackground(Color.GREEN);
                    powiadomenie.setText("Ciesz się! Do końca pozstało Ci czasu:");
                    JOptionPane.showMessageDialog(skrzynia, "Brawo! Wygrałeś wielkie NIC!");
                    blokowaniePrzyciskow(jPanel);
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
        jPanel.add(pokazOdliczanie);
        jPanel.add(powiadomenie);

        add(jPanel);

        setVisible(true);
    }

    private ArrayList<String> randomCombination() {
        ArrayList<String> losowanie = new ArrayList<String>();

        for (int i = 0; i < 3; i++) {
            Random rand = new Random();
            int losowy_numer = rand.nextInt(2); // Losowanie od 0 do 1

            if (losowy_numer == 0) {
                losowanie.add("O");
            } else {
                losowanie.add("X");
            }
        }
        return losowanie;
    }

    private void blokowaniePrzyciskow(JPanel panel) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JButton) {
                component.setEnabled(false);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Skrzynka();
    }
}
