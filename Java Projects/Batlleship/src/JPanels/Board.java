package JPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author 2450-3199-3277 Ανδρέας Μανατάκης Χαράλαμπος Πολυχρονάκης Χάρης
 * Σαριδάκης
 * @see Οι πανω μεταβλητες ειναι τα Components που χρειαζομαστε για να
 * υλοποιησουμε το πινακα μας για να τοποθετουμε τα πλοια 
 * @see #setPanels() την χρηισημοποιουμε για να κανουμε αλλαγες στο καθε Panel
 * 
 */
public class Board extends JPanel {

    JPanel[][] panels;
    private int sizeShip = 0;
    private String shipName;
    private int rows = 10;
    private int cols = 10;
    private Color backgroundColor = Color.blue;
    private int cpuHits = 0;

    private MouseAdapter switchAdapter = new MouseAdapter() {};

    public Board() {

        panels = new JPanel[rows][cols];

        setVisible(true);
        setLayout(new GridLayout(rows, cols));
//        switchAdapter = setAdapter;

        for (int i = 0; i < panels.length; i++) {
            for (int j = 0; j < 10; j++) {

                panels[i][j] = new JPanel();

                panels[i][j].setName("Panels" + i + "_" + j);
                panels[i][j].setBackground(backgroundColor);
                panels[i][j].setLayout(new GridLayout());
                panels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panels[i][j].setPreferredSize(new Dimension(50, 50));
                panels[i][j].addMouseListener(switchAdapter);
                panels[i][j].setName("BoardPanel " + i + " " + j);

                add(panels[i][j]);
            }
        }
    }

    public void hit(int x, int y) {
        Random rand = new Random();
        if (this.panels[x][y].getBackground() == Color.RED || this.panels[x][y].getBackground() == Color.WHITE) {
            hit(rand.nextInt(10), rand.nextInt(10));

        } else if (this.panels[x][y].getBackground() == Color.GRAY) {
            this.panels[x][y].setBackground(Color.RED);
            cpuHits++;
//            this.panels[x][y].setRequestFocusEnabled(true);

        } else if (this.panels[x][y].getBackground() == Color.blue) {
            this.panels[x][y].setBackground(Color.WHITE);
        }
//            this.panels[x][y].setRequestFocusEnabled(true);

        if (cpuHits == 17) {          
            JOptionPane.showMessageDialog(null,"Computer is the winner!");
            System.exit(0);
        }

    }

    public void setSizeShip(int sizeShip) {
        this.sizeShip = sizeShip;
    }

    public int getSizeShip() {
        return sizeShip;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setPanels(JPanel[][] panels) {
        this.panels = panels;
    }

    public JPanel[][] getPanels() {
        return panels;
    }

}
