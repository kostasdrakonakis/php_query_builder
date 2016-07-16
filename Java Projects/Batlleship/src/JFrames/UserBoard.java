package JFrames;

import JPanels.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.*;
import JPanels.Board;

/**
 *
 * @author 2450-3199-3277 Ανδρέας Μανατάκης Χαράλαμπος Πολυχρονάκης Χάρης
 * Σαριδάκης
 * @see Constructor Περιεχει ολα τα αντικειμενα που χρειαζονται για να φτιαξει
 * συν οτι περιεχει το πεδιο Name οπου εχει μεσα του το ονομα απο την κλαση που
 * δημιουργηθηκε
 * @see MouseAdapter Ειναι ο setAdapter ο οποιος υλοποιει την τοποθετηση των
 * πλοιων
 * @see #enableStart(int) Ειναι η συναρτηση οι οποια ενεργοποιει το κουμπι Start
 * ετσι ωστε να ξεκινησει το παιχνιδι
 * @see Οι πανω μεταβλητες ειναι τα Components που χρειαζομαστε για να
 * υλοποιησουμε το παραθυρο
 * @see MouseListener τους χρησιμοποιουμε ετσι ωστε να δινουμε το καθε πλοιο στον Adapter
 * @see #getSetBoard() την χρηισημοποιουμε οταν τελειωσουμε να δωσουμε στο επομενο παραθυρο την Board
 */
public class UserBoard extends JFrame implements MouseListener {

    private JLabel userTxt;
    private JPanel ships;
    private JPanel sea;
    private JPanel setShips;
    private JLabel shipsTxt;
    private JLabel seaTxt;
    private JPanel board;
    private JPanel buttons;
    private JButton rotate;
    private JButton startGame;
    private Board setBoard;
    private static AircraftCarrier userAC = new AircraftCarrier();
    private static Battleship userBat = new Battleship();
    private static Destroyer userDes = new Destroyer();
    private static Submarine userSub = new Submarine();
    private static Patrolboat userPat = new Patrolboat();
    private String userName;
    private String nameShip;
    private int sizeShip;
    private boolean Rotate;
    private int enableBtnStart = 0;
    private MouseAdapter setAdapter;

    public UserBoard(String name) {
        this.setAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JPanel pointer = (JPanel) e.getSource();
                String s = pointer.getName();
                String[] numbers = s.replaceAll("^\\D+", "").split("\\D+");
                int yPos = Integer.valueOf(numbers[0]);
                int xPos = Integer.valueOf(numbers[1]);
                if (sizeShip != 0) {
//                    if (pointer.getBackground() != Color.gray) {
                    if (pointer.getBackground() == Color.cyan) {
                        if (Rotate == false) {
                            try {
                                //if (xPos < 11 - sizeShip) {
                                if (xPos < 11 - 2) {
                                    pointer.setBackground(Color.gray);
                                    for (int i = 0; i < sizeShip; i++) {        // epanalhpsh gia na dei posa tetragwna tha ftiaxei pros ton x
                                        JPanel next = setBoard.getPanels()[yPos][xPos + i];
                                        if (next.getBackground() == Color.gray) {
                                            next.setBackground(Color.blue);
                                            pointer.setBackground(Color.blue);
                                        } else {
                                            next.setBackground(Color.gray);
                                            pointer.setBackground(Color.gray);
                                        }
                                    }
                                    if (("AircraftCarrier".equals(nameShip)) && (sizeShip == userAC.getShipSize())) {
                                        userAC.setSelectionColor(2);
                                        enableBtnStart++;
                                    } else if (("Battleship".equals(nameShip)) && (sizeShip == userBat.getShipSize())) {
                                        userBat.setSelectionColor(2);
                                        enableBtnStart++;
                                    } else if (("Destroyer".equals(nameShip)) && (sizeShip == userDes.getShipSize())) {
                                        userDes.setSelectionColor(2);
                                        enableBtnStart++;
                                    } else if (("Patrolboat".equals(nameShip)) && (sizeShip == userPat.getShipSize())) {
                                        userPat.setSelectionColor(2);
                                        enableBtnStart++;
                                    } else if (("Submarine".equals(nameShip)) && (sizeShip == userSub.getShipSize())) {
                                        userSub.setSelectionColor(2);
                                        enableBtnStart++;
                                    }
                                    enableStart(enableBtnStart);
                                    sizeShip = 0;
                                    Rotate = false;

                                }
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                //if (xPos < 11 - sizeShip) {
                                if (xPos < 11 - 2) {
                                    pointer.setBackground(Color.blue);
                                    for (int i = 0; i < sizeShip; i++) {        // epanalhpsh gia na dei posa tetragwna tha ftiaxei pros ton x
                                        JPanel next = setBoard.getPanels()[yPos][xPos + i];
                                        next.setBackground(Color.blue);
                                    }
                                }
                            }
                        }
                        if (Rotate == true) {
                            try {
                                //if (yPos < 11 - sizeShip) {
                                if (yPos < 11 - 2) {
                                    pointer.setBackground(Color.gray);
                                    for (int i = 0; i < sizeShip; i++) {        // epanalhpsh gia na dei posa tetragwna tha ftiaxei pros ton x
                                        JPanel next = setBoard.getPanels()[yPos + i][xPos];
                                        if (next.getBackground() == Color.gray) {
                                            next.setBackground(Color.blue);
                                            pointer.setBackground(Color.blue);
                                        } else {
                                            next.setBackground(Color.gray);
                                            pointer.setBackground(Color.gray);
                                        }
                                    }
                                    if (("AircraftCarrier".equals(nameShip)) && (sizeShip == userAC.getShipSize())) {
                                        userAC.setSelectionColor(2);
                                        enableBtnStart++;
                                    } else if (("Battleship".equals(nameShip)) && (sizeShip == userBat.getShipSize())) {
                                        userBat.setSelectionColor(2);
                                        enableBtnStart++;
                                    } else if (("Destroyer".equals(nameShip)) && (sizeShip == userDes.getShipSize())) {
                                        userDes.setSelectionColor(2);
                                        enableBtnStart++;
                                    } else if (("Patrolboat".equals(nameShip)) && (sizeShip == userPat.getShipSize())) {
                                        userPat.setSelectionColor(2);
                                        enableBtnStart++;
                                    } else if (("Submarine".equals(nameShip)) && (sizeShip == userSub.getShipSize())) {
                                        userSub.setSelectionColor(2);
                                        enableBtnStart++;
                                    }
                                    enableStart(enableBtnStart);
                                    sizeShip = 0;
                                    Rotate = false;
                                }
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                //if (yPos < 11 - sizeShip) {
                                if (yPos < 11 - 2) {
                                    pointer.setBackground(Color.blue);
                                    for (int i = 0; i < sizeShip; i++) {        // epanalhpsh gia na dei posa tetragwna tha ftiaxei pros ton x
                                        JPanel next = setBoard.getPanels()[yPos + i][xPos];
                                        next.setBackground(Color.blue);
                                    }
                                }
                            }
                        }
                    } else if (pointer.getBackground() == Color.RED) {
                        // edw einai gia na mhn topothetoume ploio panw se ploio
                        if (Rotate == false) {
                            try {
                                //if (xPos < 11 - sizeShip) {
                                if (xPos < 11 - 2) {
                                    pointer.setBackground(Color.blue);
                                    for (int i = 0; i < sizeShip; i++) {        // epanalhpsh gia na dei posa tetragwna tha ftiaxei pros ton x
                                        JPanel next = setBoard.getPanels()[yPos][xPos + i];
                                        if (next.getBackground() == Color.red) {
                                            next.setBackground(Color.gray);
                                        } else if (next.getBackground() == Color.cyan) {
                                            next.setBackground(Color.blue);
                                        }
                                    }

                                }
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                //if (xPos < 11 - sizeShip) {
                                if (xPos < 11 - 2) {
                                    pointer.setBackground(Color.blue);
                                    for (int i = 0; i < sizeShip; i++) {        // epanalhpsh gia na dei posa tetragwna tha ftiaxei pros ton x
                                        JPanel next = setBoard.getPanels()[yPos][xPos + i];
                                        next.setBackground(Color.blue);
                                    }
                                }
                            }
                        }
                        if (Rotate == true) {
                            try {
                                //if (yPos < 11 - sizeShip) {
                                if (yPos < 11 - 2) {
                                    pointer.setBackground(Color.blue);
                                    for (int i = 0; i < sizeShip; i++) {        // epanalhpsh gia na dei posa tetragwna tha ftiaxei pros ton x
                                        JPanel next = setBoard.getPanels()[yPos + i][xPos];
                                        if (next.getBackground() == Color.red) {
                                            next.setBackground(Color.gray);
                                        } else if (next.getBackground() == Color.cyan) {
                                            next.setBackground(Color.blue);
                                        }

                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                //if (yPos < 11 - sizeShip) {
                                if (yPos < 11 - 2) {
                                    pointer.setBackground(Color.blue);
                                    for (int i = 0; i < sizeShip; i++) {        // epanalhpsh gia na dei posa tetragwna tha ftiaxei pros ton x
                                        JPanel next = setBoard.getPanels()[yPos + i][xPos];
                                        next.setBackground(Color.blue);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override

            public void mouseEntered(MouseEvent e) {
                if (sizeShip != 0) {
                    JPanel pointer = (JPanel) e.getSource();
                    if (pointer.getBackground() == Color.blue) {
                        String s = pointer.getName();
                        String[] numbers = s.replaceAll("^\\D+", "").split("\\D+");
                        int yPos = Integer.valueOf(numbers[0]);
                        int xPos = Integer.valueOf(numbers[1]);
                        if (Rotate == false) {
                            try {
                                //if (xPos < 11 - sizeShip) {
                                if (xPos < 11 - 2) {
                                    pointer.setBackground(Color.cyan);
                                    for (int i = 0; i < sizeShip; i++) {

                                        JPanel next = setBoard.getPanels()[yPos][xPos + i];
                                        JPanel previous = pointer;
                                        if (next.getBackground() == Color.blue) {
                                            next.setBackground(Color.cyan);
                                        } else if (next.getBackground() == Color.gray) {
                                            pointer.setBackground(Color.red);
                                            next.setBackground(Color.red);
                                            previous = next;
                                            previous.setBackground(Color.red);
                                        }
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                //if (xPos < 11 - sizeShip) {
                                if (xPos < 11 - 2) {
                                    pointer.setBackground(Color.red);
                                    for (int i = 0; i < sizeShip; i++) {
                                        JPanel next = setBoard.getPanels()[yPos][xPos + i];
                                        next.setBackground(Color.red);
                                    }
                                }
                            }
                        }
                        if (Rotate == true) {
                            try {
                                //if (yPos < 11 - sizeShip) {
                                if (yPos < 11 - 2) {
                                    pointer.setBackground(Color.cyan);
                                    for (int i = 0; i < sizeShip; i++) {
                                        JPanel next = setBoard.getPanels()[yPos + i][xPos];
                                        JPanel previous = pointer;
                                        if (next.getBackground() == Color.blue) {
                                            next.setBackground(Color.cyan);
                                        } else if (next.getBackground() == Color.gray) {
                                            pointer.setBackground(Color.red);
                                            next.setBackground(Color.red);
                                            previous = next;
                                            previous.setBackground(Color.red);

                                        }
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                //if (yPos < 11 - sizeShip) {
                                if (yPos < 11 - 2) {
                                    pointer.setBackground(Color.red);
                                    for (int i = 0; i < sizeShip; i++) {
                                        JPanel next = setBoard.getPanels()[yPos + i][xPos];
                                        next.setBackground(Color.red);
                                    }
                                }
                            }
                        }
                    }
//                    else if (pointer.getBackground() == Color.gray) {
//                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (sizeShip != 0) {
                    JPanel pointer = (JPanel) e.getSource();
                    if ((pointer.getBackground() == Color.CYAN) || (pointer.getBackground() == Color.red)) {
                        String s = pointer.getName();
                        String[] numbers = s.replaceAll("^\\D+", "").split("\\D+");
                        int yPos = Integer.valueOf(numbers[0]);
                        int xPos = Integer.valueOf(numbers[1]);
                        if (Rotate == false) {
                            try {
                                //if (xPos < 11 - sizeShip) {
                                if (xPos < 11 - 2) {
                                    pointer.setBackground(Color.blue);
                                    for (int i = 0; i < sizeShip; i++) {
                                        JPanel next = setBoard.getPanels()[yPos][xPos + i];
                                        JPanel previous = pointer;

                                        if (next.getBackground() == Color.red) {
                                            next.setBackground(Color.gray);
                                        } else if (next.getBackground() == Color.cyan) {
                                            next.setBackground(Color.blue);
                                        }
                                        if ((pointer.getBackground() == Color.red) && (next.getBackground() == Color.BLUE)) {
                                            pointer.setBackground(Color.blue);
                                        }
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                //if (xPos < 11 - sizeShip) {
                                if (xPos < 11 - 2) {
                                    pointer.setBackground(Color.blue);
                                    for (int i = 0; i < sizeShip; i++) {
                                        JPanel next = setBoard.getPanels()[yPos][xPos + i];
                                        next.setBackground(Color.blue);
                                    }
                                }
                            }
                        }
                        if (Rotate == true) {
                            try {
                                //if (yPos < 11 - sizeShip) {
                                if (yPos < 11 - 2) {
                                    pointer.setBackground(Color.blue);
                                    for (int i = 0; i < sizeShip; i++) {
                                        JPanel next = setBoard.getPanels()[yPos + i][xPos];
                                        JPanel previous = pointer;
                                        if (next.getBackground() == Color.red) {
                                            next.setBackground(Color.gray);
                                        } else if (next.getBackground() == Color.cyan) {
                                            next.setBackground(Color.blue);
                                        }
                                        if ((pointer.getBackground() == Color.red) && (next.getBackground() == Color.BLUE)) {
                                            pointer.setBackground(Color.blue);
                                        }
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                //if (yPos < 11 - sizeShip) {
                                if (yPos < 11 - 2) {
                                    pointer.setBackground(Color.blue);
                                    for (int i = 0; i < sizeShip; i++) {
                                        JPanel next = setBoard.getPanels()[yPos + i][xPos];
                                        next.setBackground(Color.blue);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
        userName = name;
        userTxt = new JLabel();
        ships = new JPanel();
        sea = new JPanel();
        shipsTxt = new JLabel();
        setShips = new JPanel();
        seaTxt = new JLabel();
        board = new JPanel();
        buttons = new JPanel();
        rotate = new JButton("Rotate Ship");
        startGame = new JButton("Start Game");

        //sea
        setBoard = new Board();

//        setBoard.setSwitchAdapter(1);
        userAC.addMouseListener(this);
        userBat.addMouseListener(this);
        userDes.addMouseListener(this);
        userSub.addMouseListener(this);
        userPat.addMouseListener(this);
        setBoard.addMouseListener(this);

        // gia na ftiaksw olo to parathuro
        setTitle("BattleShip - Ships'Placement");
        setLayout(new BorderLayout(15, 15));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setSize(new Dimension(800, 700));
        setLocation(200, 100);

        //text gia kalosorisma
        userTxt.setText("Please select ships from left and place them in your board. Press start game when you are ready !");
        userTxt.setFont(new Font("Comic Sans MS", 2, 18));
        userTxt.setHorizontalAlignment(SwingConstants.CENTER);

        //text twn ploiwn
        shipsTxt.setText("Ships to be placed ");
        shipsTxt.setFont(new Font("Comic Sans MS", 2, 18));
        shipsTxt.setHorizontalAlignment(SwingConstants.CENTER);

        //ta ploia mas
        setShips.setVisible(true);

        //setShips.setSize(300, 300);
        setShips.setLayout(new GridLayout(6, 0, 10, 10));
        setShips.add(shipsTxt);
        setShips.add(userAC);
        setShips.add(userBat);
        setShips.add(userDes);
        setShips.add(userSub);
        setShips.add(userPat);
        ships.add(setShips);

        //txt gia thn sea
        seaTxt.setText("Your Board");
        seaTxt.setFont(new Font("Comic Sans MS", 2, 18));
        seaTxt.setHorizontalAlignment(SwingConstants.CENTER);

        //h thalassa
        board.add(setBoard);

        // panel gia sea kai text
        sea.setLayout(new BorderLayout());
        sea.setPreferredSize(new Dimension(500, 500));
        sea.add(seaTxt, BorderLayout.NORTH);
        sea.add(board, BorderLayout.CENTER);

        // button gia gurisma
        rotate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (Rotate == false) {
                    Rotate = true;
                } else if (Rotate == true) {
                    Rotate = false;
                }

            }
        });

        //button gia to na paei sto epomeno parathuro        
        startGame.setEnabled(false);
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
                new Game(setBoard, userName);
            }
        });

        // gia ta buttons katw
        buttons.setLayout(new BorderLayout());
        buttons.add(rotate, BorderLayout.WEST);
        buttons.add(startGame, BorderLayout.EAST);

        // topothesh components sto parathuro
        add(userTxt, BorderLayout.NORTH);
        add(ships, BorderLayout.WEST);
        add(sea, BorderLayout.EAST);
        add(buttons, BorderLayout.SOUTH);
        //userFrame.pack();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                setBoard.getPanels()[i][j].addMouseListener(setAdapter);
            }
        }

    }

    void enableStart(int Checker) {
        if (Checker == 5) {
            startGame.setEnabled(true);
        }
    }

    public void setBoard(JPanel board) {
        this.board = board;
    }

    public Board getSetBoard() {
        return setBoard;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (("JPanels.AircraftCarrier".equals(e.getSource().getClass().getName())) && (userAC.getSelectionColor() == Color.GRAY)) {
            userAC.setSelectionColor(1);
            sizeShip = userAC.getShipSize();
            nameShip = "AircraftCarrier";
        } else if (("JPanels.Battleship".equals(e.getSource().getClass().getName())) && (userBat.getSelectionColor() == Color.GRAY)) {
            userBat.setSelectionColor(1);
            sizeShip = userBat.getShipSize();
            nameShip = "Battleship";
        } else if (("JPanels.Destroyer".equals(e.getSource().getClass().getName())) && (userDes.getSelectionColor() == Color.GRAY)) {
            userDes.setSelectionColor(1);
            sizeShip = userDes.getShipSize();
            nameShip = "Destroyer";
        } else if (("JPanels.Submarine".equals(e.getSource().getClass().getName())) && (userSub.getSelectionColor() == Color.GRAY)) {
            userSub.setSelectionColor(1);
            sizeShip = userSub.getShipSize();
            nameShip = "Submarine";
        } else if (("JPanels.Patrolboat".equals(e.getSource().getClass().getName())) && (userPat.getSelectionColor() == Color.GRAY)) {
            userPat.setSelectionColor(1);
            sizeShip = userPat.getShipSize();
            nameShip = "Patrolboat";
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
