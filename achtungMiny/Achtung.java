import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Achtung extends JFrame {
    private static final int ROWS = 4;
    private static final int COLS = 3;
    private static final int TOTAL_MINES = 5;

    private JButton[][] mineButtons = new JButton[ROWS][COLS];
    private Set<Point> mines = new HashSet<>();

    public Achtung() {
        setTitle("Gra w Miny");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Dodanie etykiety ze stanem gry
        JLabel gameStateLabel = new JLabel(":-O", SwingConstants.CENTER);
        gameStateLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        add(gameStateLabel, BorderLayout.NORTH);

        // Dodanie panelu z miejscami min
        JPanel minesPanel = new JPanel();
        minesPanel.setLayout(new GridLayout(ROWS, COLS));

        // Inicjalizacja przycisków i rozmieszczenie min
        initializeMines();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                mineButtons[row][col] = new JButton();
                mineButtons[row][col].setPreferredSize(new Dimension(50, 50));
                mineButtons[row][col].addActionListener(new MineButtonListener(row, col, gameStateLabel));
                minesPanel.add(mineButtons[row][col]);
            }
        }

        add(minesPanel, BorderLayout.CENTER);

        // Ustawienie widoczności okna
        setVisible(true);
    }

    private void initializeMines() {
        Random random = new Random();
        while (mines.size() < TOTAL_MINES) {
            int row = random.nextInt(ROWS);
            int col = random.nextInt(COLS);
            mines.add(new Point(row, col));
        }
    }

    private class MineButtonListener implements ActionListener {
        private int row;
        private int col;
        private JLabel gameStateLabel;

        public MineButtonListener(int row, int col, JLabel gameStateLabel) {
            this.row = row;
            this.col = col;
            this.gameStateLabel = gameStateLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Point clickedPoint = new Point(row, col);
            if (mines.contains(clickedPoint)) {
                mineButtons[row][col].setText("X");
                mineButtons[row][col].setBackground(Color.RED);
                gameStateLabel.setText("XD");
                JOptionPane.showMessageDialog(Achtung.this, "Trafiłeś na minę! Przegrałeś.");
                resetGame();
            } else {
                mineButtons[row][col].setText("O");
                mineButtons[row][col].setBackground(Color.GREEN);
                boolean allClear = true;
                for (int row = 0; row < ROWS; row++) {
                    for (int col = 0; col < COLS; col++) {
                        if (!mines.contains(new Point(row, col)) && mineButtons[row][col].getText().isEmpty()) {
                            allClear = false;
                            break;
                        }
                    }
                }
                if (allClear) {
                    gameStateLabel.setText(":)");
                    JOptionPane.showMessageDialog(Achtung.this, "Wygrałeś!");
                    resetGame();
                }
            }
        }

        private void resetGame() {
            mines.clear();
            initializeMines();
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    mineButtons[row][col].setText("");
                    mineButtons[row][col].setBackground(null);
                }
            }
            gameStateLabel.setText(":-O");
        }
    }
}
