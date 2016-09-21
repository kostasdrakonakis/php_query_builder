package skaki;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Chess {
    private Board board;
    private Piece alive, dead;
    private String turn;
    private JPanel nekrotafeio, panwwhite, panwblack, mesiwhite, mesiblack, panw, mesi, kiniseis, kiniseiswhite, kiniseisblack, moves, deksio;
    private Paiktis aspros;
    private JLabel pinakidaAspro, pinakidaMauro, seira;
    private Paiktis mauros;
    private JTextArea lwhite, lblack;
    private King white, black;
    private Queen whiteQ, blackQ;
    private Knight whiteK,whiteKn, blackK, blackKn;
    private Rook whiteR,whiteRo, blackR, blackRo;
    private Bishop whiteB,whiteBi, blackBi, blackB;
    private ArrayList<Piece> kommatia;
    private ArrayList<Point> kinisi;
    private JFrame parathiro;
    private static final int cnt = 0;
    private JMenuBar menuBar;
    private JMenu fileMenu, shareMenu, soundMenu, colorMenu;
    private JMenuItem newItem, historyItem, exitItem, fb, twitter, googleplus, rss, youtube, email, donate, winsound, loosesound, sound, xrwmaBlack,xrwmaWhite;

    
    public Chess() {
        String name = JOptionPane.showInputDialog(null, "Please type your name", "Player 1", JOptionPane.PLAIN_MESSAGE);
        if (name == null || name.length() == 0) {
            JOptionPane.showMessageDialog(null, "You did not specify any name, you will be Player X", "No Name", JOptionPane.WARNING_MESSAGE);
            name = "Player X";
        }
        aspros = new Paiktis(name, "White");
        mauros = new Paiktis("PC", "Black");
        initComponents();
    }

    private boolean epitrepomeniKinisi(Piece p, Point position) {
        kinisi = p.getPossibleMovements(board);
        for(int i=0; i<kinisi.size(); i++){
            if(kinisi.get(i).equals(position)){
                board.clearAllHighlights();
                return true;
            }
        }
        return false;
    }

    private boolean isGameFinished() {
        for(int i=0; i<mesiwhite.getComponentCount();i++){
            if(mesiwhite.getComponent(i).equals(white)){
                mauros.setScore(10);
                return true;
            }
        }
        for(int i=0; i<mesiblack.getComponentCount();i++){
            if(mesiblack.getComponent(i).equals(black)){
                aspros.setScore(10);
                return true;
            }
        }
        return false;
    }

    private void initComponents() {
        if (parathiro != null) {
            parathiro.dispose();
        }
        
        whiteR = new Rook("White", new Point(7, 0));
        whiteK = new Knight("White", new Point(7, 1));
        whiteB = new Bishop("White", new Point(7, 2));
        whiteQ = new Queen("White", new Point(7, 3));
        white = new King("White", new Point(7, 4));
        whiteBi = new Bishop("White", new Point(7, 5));
        whiteKn = new Knight("White", new Point(7, 6));
        whiteRo = new Rook("White", new Point(7, 7));
        
        blackR = new Rook("Black", new Point(0, 0));
        blackK = new Knight("Black", new Point(0, 1));
        blackB = new Bishop("Black", new Point(0, 2));
        blackQ = new Queen("Black", new Point(0, 3));
        black = new King("Black", new Point(0, 4));
        blackBi = new Bishop("Black", new Point(0, 5));
        blackKn = new Knight("Black", new Point(0, 6));
        blackRo = new Rook("Black", new Point(0, 7));
        
        nekrotafeio = new JPanel();
        panwwhite = new JPanel();
        mesiwhite = new JPanel();
        mesiblack = new JPanel();
        kiniseis = new JPanel();
        deksio = new JPanel();
        deksio.setPreferredSize(new Dimension(Board.BOARD_WIDTH, Board.BOARD_HEIGHT));
        deksio.setLayout(new GridLayout(1, 2));
        kiniseiswhite = new JPanel();
        kiniseisblack = new JPanel();
        panw = new JPanel();
        mesi = new JPanel();
        pinakidaAspro = new JLabel("White");
        pinakidaMauro = new JLabel("Black");
        panwblack = new JPanel();
        lwhite = new JTextArea();
        moves = new JPanel();
        lblack = new JTextArea();
        nekrotafeio.setBackground(Color.WHITE);
        nekrotafeio.setPreferredSize(new Dimension(Board.BOARD_WIDTH / 2, Board.BOARD_HEIGHT));
        nekrotafeio.setLayout(new BorderLayout());
        panwwhite.setLayout(new GridLayout(1, 2));
        panwblack.setLayout(new GridLayout(1, 2));
        panw.setLayout(new GridLayout(1,2));
        panwwhite.add(pinakidaAspro);
        panwblack.add(pinakidaMauro);
        panw.add(panwwhite);
        panw.add(panwblack);
        panw.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mesiwhite.setLayout(new GridLayout(8, 2));
        mesiwhite.setBackground(Color.BLACK);
        mesiblack.setBackground(Color.WHITE);
        mesiblack.setLayout(new GridLayout(8, 2));
        mesi.setLayout(new GridLayout(1, 4));
        mesi.add(mesiwhite);
        mesi.add(mesiblack);
        kiniseiswhite.setLayout(new GridLayout(1, 1));
        kiniseiswhite.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        kiniseisblack.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        kiniseisblack.setLayout(new GridLayout(1, 1));
        kiniseiswhite.add(lwhite);
        kiniseisblack.add(lblack);
        kiniseis.setLayout(new BorderLayout());
        moves.setLayout(new GridLayout(1, 2));
        moves.add(kiniseiswhite);
        moves.add(kiniseisblack);
        kiniseis.add(moves, BorderLayout.CENTER);
        kiniseis.add(new JLabel("Moves", SwingConstants.CENTER), BorderLayout.NORTH);
        mesi.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nekrotafeio.add(mesi, BorderLayout.CENTER);
        nekrotafeio.add(panw, BorderLayout.NORTH);
        deksio.add(nekrotafeio);
        deksio.add(kiniseis);
        seira = new JLabel("White's Turn");
        seira.setPreferredSize(new Dimension(Board.BOARD_WIDTH, 20));
        seira.setFont(new Font(Font.SERIF, 2, 20));
        seira.setHorizontalAlignment(JLabel.CENTER);
        
        kommatia = new ArrayList<Piece>();
        
        kommatia.add(blackR);
        kommatia.add(blackK);
        kommatia.add(blackB);
        kommatia.add(blackQ);
        kommatia.add(black);
        kommatia.add(blackBi);
        kommatia.add(blackKn);
        kommatia.add(blackRo);

        for (int i = 0; i < 8; i++) {
            kommatia.add(new Pawn("Black", new Point(1, i)));
        }

        for (int j = 0; j < 8; j++) {
            kommatia.add(new Pawn("White", new Point(6, j)));
        }

        kommatia.add(whiteR);
        kommatia.add(whiteK);
        kommatia.add(whiteB);
        kommatia.add(whiteQ);
        kommatia.add(white);
        kommatia.add(whiteBi);
        kommatia.add(whiteKn);
        kommatia.add(whiteRo);

        this.board = new Board(kommatia);
        this.alive = null;
        this.turn = "White";

        this.board.getForegroundPanel().addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getY() / 64;
                int y = e.getX() / 64;
                
                if (alive != null && epitrepomeniKinisi(alive, new Point(x, y))) {
                    dead = board.movePieceToPosition(alive, new Point(x, y));
                    if (dead != null) {
                        if(dead.getColor().equals("White")){
                            mesiwhite.add(dead);
                            mesiwhite.revalidate();
                        }
                        if(dead.getColor().equals("Black")){
                            mesiblack.add(dead);
                            mesiblack.revalidate();
                        }
                    }
                    playSound("sounds/sound.wav");
                    alive.setIconImage("Normal");
                    if(alive.getColor().equals("White")){
                        int z = convertNumToRow(x);
                        String k = convertLetterToCol(y);
                        lwhite.append("White at: " + String.valueOf(z) + ", " + k + "\n");
                    }else{
                        int z = convertNumToRow(x);
                        String k = convertLetterToCol(y);
                        lblack.append("Black at: " + String.valueOf(z) + ", " + k + "\n");
                    }
                    changeTurn();

                    if (isGameFinished()) {
                        if(mauros.getScore()>aspros.getScore()){
                            playSound("sounds/loose.wav");
                        }else{
                            playSound("sounds/win.wav");
                        }
                        int telos = JOptionPane.showConfirmDialog(null,"New Game", "Game Over",
                                JOptionPane.YES_NO_OPTION);
                        seira.setText("Game Over");
                        
                        if (telos == JOptionPane.YES_OPTION) {
                            initComponents();
                        } else {
                            System.exit(0);
                        }
                    }
                } else {
                    Piece p = board.findPieceAtPosition(new Point(x, y));
                    if (p != null) {
                        if (alive != null) {
                            alive.setIconImage("Normal");
                        }

                        if (p.getColor().equals(turn)) {
                            alive = p;
                            if (!alive.getPossibleMovements(board).isEmpty()) {
                                alive.setIconImage("Selected");
                            } else {
                                alive.setIconImage("NotMove");
                            }
                            board.clearAllHighlights();
                            board.highlightSomePositions(alive.getPossibleMovements(board));
                        }
                    }
                    board.revalidate();
                    board.repaint();
                }
            }
        });

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        shareMenu = new JMenu("Share");
        colorMenu = new JMenu("Color");
        newItem = new JMenuItem("New Game");
        historyItem = new JMenuItem("Statistics");
        donate = new JMenuItem("Donate");
        exitItem = new JMenuItem("Exit");
        winsound = new JMenuItem("Choose winning sound");
        loosesound = new JMenuItem("Choose loosing sound");
        sound = new JMenuItem("Choose Movement sound");
        fb = new JMenuItem("Like us on Facebook");
        xrwmaBlack = new JMenuItem("Change color in Black Panel");
        xrwmaWhite = new JMenuItem("Change color in White Panel");
        twitter = new JMenuItem("Follow us on Twitter");
        googleplus = new JMenuItem("Follow us on Google+");
        youtube = new JMenuItem("Subscribe in Youtube");
        rss = new JMenuItem("Subscribe in our LiveFeed");
        email = new JMenuItem("Share via Email");
        email.setIcon(new ImageIcon("smallicons/email.png"));
        fb.setIcon(new ImageIcon("smallicons/fb.png"));
        twitter.setIcon(new ImageIcon("smallicons/twitter.png"));
        youtube.setIcon(new ImageIcon("smallicons/youtube.png"));
        xrwmaBlack.setIcon(new ImageIcon("smallicons/black.png"));
        xrwmaWhite.setIcon(new ImageIcon("smallicons/aspro.png"));
        xrwmaBlack.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
        xrwmaWhite.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
        rss.setIcon(new ImageIcon("smallicons/rss.png"));
        googleplus.setIcon(new ImageIcon("smallicons/google.png"));
        donate.setIcon(new ImageIcon("smallicons/donate.png"));
        newItem.setIcon(new ImageIcon("smallicons/folder.png"));
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        historyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        historyItem.setIcon(new ImageIcon("smallicons/stats.png"));
        exitItem.setIcon(new ImageIcon("smallicons/exit.png"));
        winsound.setIcon(new ImageIcon("smallicons/sound.png"));
        loosesound.setIcon(new ImageIcon("smallicons/sound.png"));
        sound.setIcon(new ImageIcon("smallicons/sound.png"));
        soundMenu = new JMenu("Ήχος");
        
        fb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Desktop.getDesktop().browse(new URL("http://www.facebook.com").toURI());
                }catch (Exception xe) {
                }
            }
        });
        twitter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Desktop.getDesktop().browse(new URL("http://www.twitter.com").toURI());
                }catch (Exception xe) {
                }
            }
        });
        googleplus.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Desktop.getDesktop().browse(new URL("http://plus.google.com").toURI());
                }catch (Exception xe) {
                }
            }
        });
        rss.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Desktop.getDesktop().browse(new URL("http://www.rss.com").toURI());
                }catch (Exception xe) {
                }
            }
        });
        youtube.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Desktop.getDesktop().browse(new URL("http://www.youtube.com").toURI());
                }catch (Exception xe) {
                }
            }
        });
        email.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Desktop.getDesktop().browse(new URL("http://www.hotmail.com").toURI());
                }catch (Exception xe) {
                }
            }
        });
        newItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                initComponents();
            }
        });
        historyItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Statistics\n" + aspros.getName()
                                + " score is " + aspros.getScore() + "\n" + mauros.getName()
                                + "'s score is " + mauros.getScore(), "History", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        exitItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        donate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Desktop.getDesktop().browse(new URL("http://spinner.bl.ee/lol.html").toURI());
                }catch (Exception xe) {
                }
            }
        });
        sound.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser(new File("File to start in"));
                fileChooser.setFileFilter(new FileNameExtensionFilter("WAV file", "wav"));
                fileChooser.showOpenDialog(sound);
                File f = new File(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        winsound.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser(new File("File to start in"));
                fileChooser.setFileFilter(new FileNameExtensionFilter("WAV file", "wav"));
                fileChooser.showOpenDialog(sound);
            }
        });
        loosesound.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser(new File("File to start in"));
                fileChooser.setFileFilter(new FileNameExtensionFilter("WAV file", "wav"));
                fileChooser.showOpenDialog(sound);
            }
        });
        xrwmaBlack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Color arxiko = mesiblack.getBackground();
                Color twrino = JColorChooser.showDialog(null, "Choose a color", arxiko);
                if(twrino != null){
                    mesiblack.setBackground(twrino);
                }
            }
        });
        xrwmaWhite.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Color arxiko = mesiwhite.getBackground();
                Color twrino = JColorChooser.showDialog(null, "Choose a color", arxiko);
                mesiwhite.setBackground(twrino);
                if(twrino != null){
                    mesiwhite.setBackground(twrino);
                }
            }
        });
        fileMenu.add(newItem);
        fileMenu.add(historyItem);
        fileMenu.add(exitItem);
        shareMenu.add(fb);
        shareMenu.add(twitter);
        shareMenu.add(googleplus);
        shareMenu.add(youtube);
        shareMenu.add(rss);
        shareMenu.add(email);
        shareMenu.add(donate);
        soundMenu.add(sound);
        soundMenu.add(winsound);
        soundMenu.add(loosesound);
        colorMenu.add(xrwmaBlack);
        colorMenu.add(xrwmaWhite);
        menuBar.add(fileMenu);
        menuBar.add(shareMenu);
        menuBar.add(soundMenu);
        menuBar.add(colorMenu);
        
        parathiro = new JFrame("Project Chess");
        parathiro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parathiro.setLayout(new BorderLayout());
        parathiro.add(board, BorderLayout.CENTER);
        parathiro.add(deksio, BorderLayout.EAST);
        parathiro.add(seira, BorderLayout.SOUTH);
        parathiro.pack();
        parathiro.setJMenuBar(menuBar);
        parathiro.setVisible(true);
        parathiro.setResizable(false);
    }
    
    public void playSound(String soundName){
        try 
        {
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
         Clip clip = AudioSystem.getClip( );
         clip.open(audioInputStream);
         clip.start( );
        }
        catch(Exception ex)
        {
          ex.printStackTrace( );
        }
      }

    private String convertLetterToCol(int y) {
        String q = null;
        if(y == 0){
            q = "a";
        }
        if(y == 1){
            q = "b";
        }
        if(y == 2){
            q = "c";
        }
        if(y == 3){
            q = "d";
        }
        if(y == 4){
            q = "e";
        }
        if(y == 5){
            q = "f";
        }
        if(y == 6){
            q = "g";
        }
        if(y == 7){
            q = "h";
        }
        return q;
    }
    
    private int convertNumToRow(int x) {
        int z = 0;
        if(x == 0){
            z = 8;
        }
        if(x == 1){
            z = 7;
        }
        if(x == 2){
            z = 6;
        }
        if(x == 3){
            z = 5;
        }
        if(x == 4){
            z = 4;
        }
        if(x == 5){
            z = 3;
        }
        if(x == 6){
            z = 5;
        }
        if(x == 7){
            z = 1;
        }
        if(x == 8){
            z = 0;
        }
        return z;
    }
    
    public void changeTurn() {
        this.board.clearAllHighlights();
        this.alive = null;
        if (this.turn.equals("White")) {
            this.turn = "Black";
            seira.setText("Black's Turn");
        } else {
            this.turn = "White";
            seira.setText("White's Turn");
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Piece getAlive() {
        return alive;
    }

    public void setAlive(Piece alive) {
        this.alive = alive;
    }

    public JPanel getNekrotafeio() {
        return nekrotafeio;
    }

    public void setNekrotafeio(JPanel nekrotafeio) {
        this.nekrotafeio = nekrotafeio;
    }

    public Paiktis getAspros() {
        return aspros;
    }

    public void setAspros(Paiktis aspros) {
        this.aspros = aspros;
    }

    public Paiktis getMauros() {
        return mauros;
    }

    public void setMauros(Paiktis mauros) {
        this.mauros = mauros;
    }

    public ArrayList<Piece> getKommatia() {
        return kommatia;
    }

    public void setKommatia(ArrayList<Piece> kommatia) {
        this.kommatia = kommatia;
    }

    public JFrame getParathiro() {
        return parathiro;
    }

    public void setParathiro(JFrame parathiro) {
        this.parathiro = parathiro;
    }
    
    public static void main(String[] args) {
        new Chess();
    }
}
