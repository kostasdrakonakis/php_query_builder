package JPanels;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @see Οι πανω μεταβλητες ειναι τα Components που χρειαζομαστε για να
 * υλοποιησουμε το καθε πλοιο 
 * @see #getSetBoard() την χρηισημοποιουμε οταν τελειωσουμε να δωσουμε στο επομενο παραθυρο την Board
 * @see #getSelectionColor()  την χρηισημοποιουμε ετσι ωστε να αλλαξουμε τα χρωματα στα πλοια 
 * @see #getShipSize()την χρηισημοποιουμε για να γνωριζουμε το μεγεθος του πλοιου που φτιαχνουμε
 * @see #getButtonsPanel() την χρησιμοποιουμε ετσι ωστε να επηρεαζουμε τα πλοια για τυχον αλλαγες μετα
 * 
 */
public abstract class Battleships extends JPanel{

    protected int shipSize;
    private int row;
    private int col;
    JPanel[] buttonsPanel;
    private Color selectionColor = Color.gray;
  
    public Battleships() {

        row = 1;
        col = shipSize;

        setLayout(new GridLayout(row, col));
        setVisible(true);

    }
    protected void createShips(int size) {

        buttonsPanel = new JPanel[5];

        for (int i = 0; i < 5; i++) {
            buttonsPanel[i] = new JPanel();

            if (i < size) {
                buttonsPanel[i].setBackground(selectionColor);
                buttonsPanel[i].setVisible(true);
                buttonsPanel[i].setPreferredSize(new Dimension(50, 50));
                buttonsPanel[i].setName("buttonsPanel" + i);
                buttonsPanel[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            } else {
                buttonsPanel[i].setBackground(Color.white);
                buttonsPanel[i].setVisible(true);
                buttonsPanel[i].setPreferredSize(new Dimension(50, 50));
                buttonsPanel[i].setName("buttonsPanel" + i);
                buttonsPanel[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
            add(buttonsPanel[i]);
        }
    }

    public int getShipSize() {
        return shipSize;
    }

    public void setSelectionColor(Color selectionColor) {
        this.selectionColor = selectionColor;
    }
    
    public void setSelectionColor(int select) {
        if (select == 0) {
            selectionColor = Color.gray;
        } else if (select == 1) {
            selectionColor = Color.yellow;
        } else if (select == 2) {
            selectionColor = Color.white;
        }
        for (int i = 0; i < buttonsPanel.length; i++) {
            if (buttonsPanel[i].getBackground() == Color.gray) {
                buttonsPanel[i].setBackground(selectionColor);
            }
        }
        for (int i = 0; i < buttonsPanel.length; i++) {
            if (buttonsPanel[i].getBackground() == Color.yellow) {
                buttonsPanel[i].setBackground(selectionColor);
            }
        }
    }

    public Color getSelectionColor() {
        return selectionColor;
    }

    public void setButtonsPanel(JPanel[] buttonsPanel) {
        this.buttonsPanel = buttonsPanel;
    }

    public JPanel[] getButtonsPanel() {
        return buttonsPanel;
    }
}
