package skaki;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Board extends JLayeredPane{
    private JPanel tiles[][];
    private JPanel foregroundPanel;
    private JPanel backgroundPanel;
    private JPanel numericalPanel;
    private JPanel alphabeticalPanel;
    public static final int BOARD_WIDTH = 512;
    public static final int BOARD_HEIGHT = 512;

    public Board(ArrayList<Piece> pieces) {
        this.setPreferredSize(new Dimension(BOARD_WIDTH + 20, BOARD_HEIGHT + 50));
        initforeground();
        initbackground();
        initnumeric();
        initalphabetical();

        for (int i = 0; i < pieces.size(); i++) {
            Piece tmp = pieces.get(i);
            this.tiles[tmp.getPosition().x][tmp.getPosition().y].add(tmp);
        }

        this.add(numericalPanel, 0, 0);
        this.add(alphabeticalPanel, 1, 0);
        this.add(backgroundPanel, 2, 0);
        this.add(foregroundPanel, 3, 0);

        this.revalidate();
        //this.repaint();
    }

    private void initforeground() {
        foregroundPanel = new JPanel();
        foregroundPanel.setOpaque(false);
        foregroundPanel.setBounds(20, 20, BOARD_WIDTH, BOARD_HEIGHT);
        foregroundPanel.setLayout(new GridLayout(8, 8));
        this.tiles = new JPanel[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.tiles[i][j] = new JPanel();
                this.tiles[i][j].setOpaque(true);
                this.tiles[i][j].setBackground(new Color(0f, 0f, 0f, 0f));
                this.tiles[i][j].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

                foregroundPanel.add(this.tiles[i][j]);
            }
        }
    }

    private void initbackground() {
        backgroundPanel = new JPanel();
        backgroundPanel.setBounds(20, 20, BOARD_WIDTH, BOARD_HEIGHT);
        backgroundPanel.setLayout(new GridLayout(8, 8));
        backgroundPanel.setOpaque(true);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                JPanel backPanel = new JPanel();

                if ((i + j) % 2 == 1) {
                    backPanel.setBackground(Color.DARK_GRAY);
                } else {
                    backPanel.setBackground(new Color(225, 222, 222));
                }

                backPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                backgroundPanel.add(backPanel);
            }
        }
    }

    private void initnumeric() {
        numericalPanel = new JPanel();
        numericalPanel.setBounds(0, 20, 20, BOARD_HEIGHT);
        numericalPanel.setLayout(new GridLayout(8, 2));
        for (int i = 8; i > 0; i--) {
            JLabel labelNumbers = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            labelNumbers.setBackground(Color.WHITE);
            labelNumbers.setForeground(Color.BLACK);
            labelNumbers.setOpaque(true);
            labelNumbers.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            numericalPanel.add(labelNumbers);
        }
    }

    private void initalphabetical() {
        alphabeticalPanel = new JPanel();
        alphabeticalPanel.setBounds(20, 0, BOARD_WIDTH, 20);
        alphabeticalPanel.setLayout(new GridLayout(1, 8));
        for (int i = 0; i < 8; i++) {
            char c = (char) ('a' + i);
            JLabel labelAlpha = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            labelAlpha.setBackground(Color.CYAN);
            labelAlpha.setForeground(Color.BLACK);
            labelAlpha.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            labelAlpha.setOpaque(true);
            alphabeticalPanel.add(labelAlpha);
        }
    }

    public JPanel[][] getTiles() {
        return tiles;
    }

    public void setTiles(JPanel[][] tiles) {
        this.tiles = tiles;
    }

    public JPanel getForegroundPanel() {
        return foregroundPanel;
    }

    public void setForegroundPanel(JPanel foregroundPanel) {
        this.foregroundPanel = foregroundPanel;
    }

    public JPanel getBackgroundPanel() {
        return backgroundPanel;
    }

    public void setBackgroundPanel(JPanel backgroundPanel) {
        this.backgroundPanel = backgroundPanel;
    }

    public JPanel getNumericalPanel() {
        return numericalPanel;
    }

    public void setNumericalPanel(JPanel numericalPanel) {
        this.numericalPanel = numericalPanel;
    }

    public JPanel getAlphabeticalPanel() {
        return alphabeticalPanel;
    }

    public void setAlphabeticalPanel(JPanel alphabeticalPanel) {
        this.alphabeticalPanel = alphabeticalPanel;
    }

    public void highlightAtPosition(Point position) {
        JPanel tmp = tiles[position.x][position.y];
        tmp.setBackground(new Color(0f, 1f, 1f, 0.6f));
        this.revalidate();
        //this.repaint();
    }

    public void highlightSomePositions(ArrayList<Point> positions) {
        for (int i = 0; i < positions.size(); i++) {
            highlightAtPosition(positions.get(i));
        }
    }
    
    public void clearHighlightAtPosition(Point position) {
        JPanel tmp = tiles[position.x][position.y];
        tmp.setBackground(new Color(0f, 0f, 0f, 0f));
        this.revalidate();
        //this.repaint();
    }

    public void clearAllHighlights() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Board.this.clearHighlightAtPosition(new Point(i, j));
            }
        }
    }

    public Piece movePieceToPosition(Piece piece, Point position) {
        this.tiles[piece.getPosition().x][piece.getPosition().y].removeAll();
        Piece exist = findPieceAtPosition(position);
        this.tiles[position.x][position.y].removeAll();
        this.tiles[position.x][position.y].add(piece);

        piece.setPosition(position);

        this.revalidate();
        this.repaint();

        return exist;
    }

    public Piece findPieceAtPosition(Point position) {
        if (this.tiles[position.x][position.y].getComponentCount() != 0) {
            return (Piece) this.tiles[position.x][position.y].getComponent(0);
        } else {
            return null;
        }
    }
}
