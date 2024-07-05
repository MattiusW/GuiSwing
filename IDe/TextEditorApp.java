import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditorApp extends JFrame {
    private JTextArea textArea;
    private JPanel colorPanel;
    private JColorChooser colorChooser;
    private JFileChooser fileChooser;

    public TextEditorApp() {
        setTitle("Text Editor App");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu layoutMenu = new JMenu("Layout");
        JMenuItem flowLayoutItem = new JMenuItem("Flow Layout");
        JMenuItem borderLayoutItem = new JMenuItem("Border Layout");
        JMenuItem gridLayoutItem = new JMenuItem("Grid Layout");
        layoutMenu.add(flowLayoutItem);
        layoutMenu.add(borderLayoutItem);
        layoutMenu.add(gridLayoutItem);
        menuBar.add(layoutMenu);

        // Create "Pane" menu
        JMenu paneMenu = new JMenu("Pane");
        JMenuItem addTextPaneItem = new JMenuItem("Add Text Pane");
        JMenuItem addColorPaneItem = new JMenuItem("Add Color Pane");
        JMenuItem removePaneItem = new JMenuItem("Remove Pane");
        paneMenu.add(addTextPaneItem);
        paneMenu.add(addColorPaneItem);
        paneMenu.add(removePaneItem);
        menuBar.add(paneMenu);

        setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        colorPanel = new JPanel();
        colorChooser = new JColorChooser();
        colorPanel.add(colorChooser);
        colorPanel.setVisible(false);

        mainPanel.add(colorPanel, BorderLayout.SOUTH);

        flowLayoutItem.addActionListener(e -> mainPanel.setLayout(new FlowLayout()));
        borderLayoutItem.addActionListener(e -> mainPanel.setLayout(new BorderLayout()));
        gridLayoutItem.addActionListener(e -> mainPanel.setLayout(new GridLayout(2, 2)));

        addTextPaneItem.addActionListener(e -> addTextPane());
        addColorPaneItem.addActionListener(e -> colorPanel.setVisible(true));
        removePaneItem.addActionListener(e -> removePane());

        fileChooser = new JFileChooser();

        add(mainPanel);
    }

    private void addTextPane() {
        JPanel textPane = new JPanel(new BorderLayout());
        JTextArea newTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(newTextArea);
        textPane.add(scrollPane, BorderLayout.CENTER);

        JButton loadButton = new JButton("Load File");
        loadButton.addActionListener(e -> loadFile(newTextArea));
        textPane.add(loadButton, BorderLayout.SOUTH);

        add(textPane, BorderLayout.EAST);
        revalidate();
        repaint();
    }

    private void loadFile(JTextArea textArea) {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.read(reader, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void removePane() {
        if (getComponentCount() > 1) {
            remove(1);
            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TextEditorApp().setVisible(true);
        });
    }
}
