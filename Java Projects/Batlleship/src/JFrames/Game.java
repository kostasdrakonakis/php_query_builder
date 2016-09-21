package JFrames;

import JPanels.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*;

/**
 * @see Constructor Περιεχει ολα τα αντικειμενα που χρειαζονται για να φτιαξει το αντικειμενο Game
 * συν οτι περιεχει το πεδιο Name οπου εχει μεσα του το ονομα του παικτη απο την κλαση που
 * δημιουργηθηκε και τον πινακα που εστησε ο χρηστης τα πλοια
 * @see MouseAdapter Ειναι ο gameAdapter ο οποιος υλοποιει το παιχνιδι
 * @see #Winner Ειναι η συναρτηση οι οποια αν νικησει ο χρηστης μας ειδοποιει
 * @see Οι πανω μεταβλητες ειναι τα Components που χρειαζομαστε για να
 * υλοποιησουμε το παραθυρο
 */
public class Game extends JFrame {

    private JSplitPane jsp;
    private JPanel jspPanel;
    private JPanel userPanel;
    private JLabel userTxt;
    private JPanel cpuPanel;
    private JLabel cpuTxt;
    private CpuBoard cpuSide;
    private Board userSide;
    private String userID;
    private int userHits=0;

    public Game(Board userBoard, String userName) {

        userID = userName;
        userSide = userBoard;
        jsp = new JSplitPane();
        jspPanel = new JPanel();
        userPanel = new JPanel();
        userTxt = new JLabel(userName + "'s Board");
        cpuPanel = new JPanel();
        cpuTxt = new JLabel("Computer's Board");
        cpuSide = new CpuBoard();

        //setaroume to frame mas
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("BattleShip - Play Game");
        setResizable(false);
        setLocation(200,100);

        //pleura cpu
        cpuPanel.setLayout(new BorderLayout());
        cpuPanel.add(cpuTxt, BorderLayout.NORTH);
        cpuTxt.setAlignmentX(CENTER_ALIGNMENT);
        cpuTxt.setAlignmentY(CENTER_ALIGNMENT);
        cpuPanel.add(cpuSide.getCpuBoard(), BorderLayout.SOUTH);

        //pleura player
        userPanel.setLayout(new BorderLayout());
        userPanel.add(userTxt, BorderLayout.NORTH);
        userTxt.setAlignmentX(CENTER_ALIGNMENT);
        userTxt.setAlignmentY(CENTER_ALIGNMENT);
        userPanel.add(userBoard, BorderLayout.SOUTH);

        jsp.setDividerSize(10);
        jsp.setRightComponent(cpuPanel);
        jsp.setLeftComponent(userPanel);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cpuSide.getCpuBoard().getPanels()[i][j].addMouseListener(playAdapter);
            }
        }

        add(jsp);
        pack();

    }

    public void Winner() {
        if (userHits == cpuSide.getSum()) {
            JOptionPane.showMessageDialog(null, userID + " is the winner!");
            System.exit(0);
        }
    }

    private MouseAdapter playAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {

            JPanel x = (JPanel) e.getSource();
//            if (x.getBackground() == Color.gray) {       //elegxoume ean to sigekrimeno JPanel pou patisame einai ksanapatimeno
            if ((x.getName() == "Ship")&&(x.getBackground()!=Color.RED)) {
                Random rand = new Random();
                userSide.hit(rand.nextInt(10), rand.nextInt(10));                
                x.setBackground(Color.red);
                userHits++;
                Winner();
            
            } else if ((x.getName() != "Ship")&&(x.getBackground()!=Color.white)) {
                Random rand = new Random();
                userSide.hit(rand.nextInt(10), rand.nextInt(10));
                x.setBackground(Color.white);
                Winner();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JPanel x = (JPanel) e.getSource();
            if (x.getBackground() == Color.BLUE) {
                x.setBackground(Color.green);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JPanel x = (JPanel) e.getSource();
            if (x.getBackground() == Color.GREEN) {
                x.setBackground(Color.blue);
            }
        }

    };
}
