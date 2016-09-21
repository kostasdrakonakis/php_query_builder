package ijaeFinale;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import model.Shape;
import model.ShapesList;

public class MyPaint extends JFrame {

    private JPanel center, side, top, circlePanel, trianglePanel, linePanel, squarePanel;
    private JMenuBar menuBar;
    private JMenuItem open, about, save, loadXML, saveXML;
    private JMenu fileMenu, aboutMenu;
    private JToolBar toolBar;
    private JButton line, square, triangle, circle, lineButton1, lineButton2, squareButton1, squareButton2,
            triangleButton1, triangleButton2, circleButton1, circleButton2;
    private BufferedImage img;
    private JLabel imgLabel, lineLabel1, lineLabel2, squareLabel1, squareLabel2, squareLabel3, squareLabel4, triangleLabel1,
            triangleLabel2, triangleLabel3, circleLabel1, circleLabel2, circleLabel3;
    private ShapesList myList;
    private JTextField lineField1, lineField2, squareField1, squareField2, squareField3, squareField4, triangleField1,
            triangleField2, triangleField3, circleField1, circleField2, circleField3;
    private JFrame shapePropertiesPanel;
    private static MyPaint fp;
    private Canvas c;
    private Graphics2D g2d;
    private Color before, circleNow, squareNow, triangleNow, lineNow;
    private Ellipse2D.Double circleShape;

    public MyPaint() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 700);
        this.setTitle("Final Project");
        this.setLayout(new BorderLayout());
        initComponents();
        this.setJMenuBar(menuBar);
    }

    private void initComponents() {
        createMenu();
        createToolBar();
        createCenterPanel();
        createSidePanel();
        createXMLList();

    }

    public static void main(String[] args) {
        fp = new MyPaint();
        fp.setVisible(true);
    }

    private void createMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        aboutMenu = new JMenu("About");
        open = new JMenuItem("Open Image");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        open.addActionListener((ActionEvent e) -> {
            openImage();
        });
        about = new JMenuItem("About Us");
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK));
        about.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MyPaint.this, "This is the Final Project in Java(IJAE) for Winter Semester"
                        + "\n Student: Drakonakis Konstantinos"
                        + "\n Login: xdrako00", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        save = new JMenuItem("Save Image");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (side.getComponents() != null) {
                    JFileChooser fc = new JFileChooser();
                    int returnSaveValue = fc.showSaveDialog(MyPaint.this);
                    if (returnSaveValue == JFileChooser.APPROVE_OPTION) {
                        File f = fc.getSelectedFile();
                        String test = f.getAbsolutePath();
                        try {
                            BufferedImage image = new BufferedImage(center.getWidth(), center.getHeight(), BufferedImage.TYPE_INT_RGB);
                            Graphics2D graphics2D = image.createGraphics(); 

                            center.paint(graphics2D);
                            ImageIO.write(image, "png", new File(test));
                        } catch (IOException ex) {
                            Logger.getLogger(MyPaint.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
            }
        });
        loadXML = new JMenuItem("Load xml File");
        loadXML.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
        loadXML.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadXMLFileList();
                } catch (JAXBException ex) {
                    Logger.getLogger(MyPaint.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MyPaint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        saveXML = new JMenuItem("Save xml File");
        saveXML.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
        saveXML.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveXMLFileList();
                } catch (JAXBException ex) {
                    Logger.getLogger(MyPaint.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MyPaint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(loadXML);
        fileMenu.add(saveXML);
        aboutMenu.add(about);
        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);
    }

    private void createToolBar() {
        toolBar = new JToolBar();
        line = new JButton();
        line.setIcon(new ImageIcon("images/line.png", ""));
        line.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                createShapePropertiesPanel();
                createLinePanel();

                shapePropertiesPanel.add(linePanel);
                shapePropertiesPanel.revalidate();
                shapePropertiesPanel.repaint();
            }
        });
        square = new JButton();
        square.setIcon(new ImageIcon("images/square.png", ""));
        square.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                createShapePropertiesPanel();
                createSquarePanel();
                shapePropertiesPanel.add(squarePanel);
                shapePropertiesPanel.revalidate();
                shapePropertiesPanel.repaint();
            }
        });
        circle = new JButton();
        circle.setIcon(new ImageIcon("images/circle.png", ""));
        circle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                createShapePropertiesPanel();
                createCirclePanel();
                shapePropertiesPanel.add(circlePanel);
                shapePropertiesPanel.revalidate();
                shapePropertiesPanel.repaint();
            }
        });
        triangle = new JButton();
        triangle.setIcon(new ImageIcon("images/triangle.png", ""));
        triangle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                createShapePropertiesPanel();
                createTrianglePanel();
                shapePropertiesPanel.add(trianglePanel);
                shapePropertiesPanel.revalidate();
                shapePropertiesPanel.repaint();
            }
        });
        toolBar.add(line);
        toolBar.add(square);
        toolBar.add(circle);
        toolBar.add(triangle);
        this.add(toolBar, BorderLayout.NORTH);
    }

    private void openImage() {
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Images(PNG, JPG, GIF, Bitmap)", "png", "jpg", "tif", "gif", "bmp");
        fc.setFileFilter(filter);
        int returnValue = fc.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                BufferedImage image = ImageIO.read(file);
                this.img = image;
                imgLabel.setIcon(new ImageIcon(img));
                this.setSize(img.getWidth(), img.getHeight());
                this.setResizable(true);
                this.repaint();
                this.revalidate();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error: File is not image!");
            }
        }
    }

    private void createCenterPanel() {
        center = new JPanel();
        center.setLayout(new BorderLayout());
        center.setBackground(Color.WHITE);
        this.add(center);
    }

    private void createSidePanel() {
        side = new JPanel(new BorderLayout());
        imgLabel = new JLabel("");
        side.add(imgLabel, BorderLayout.CENTER);
        this.add(side, BorderLayout.EAST);
    }

    private void createXMLList() {
        myList = new ShapesList("MyShapes");
        myList.addShape(new Shape("Circle", 50, 50, 100));
        myList.addShape(new Shape("Line", 100, 100));
        myList.addShape(new Shape("Triangle", 100, 200, 20));
        myList.addShape(new Shape("Square", 150, 100, 50, 20));
    }

    private void loadXMLFileList() throws JAXBException, FileNotFoundException {
        System.out.println("Loading XML from file");
        JFileChooser fc = new JFileChooser();

        FileFilter filter1 = new FileNameExtensionFilter("XML Files", "xml");
        fc.setFileFilter(filter1);
        int returnValue = fc.showOpenDialog(MyPaint.this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            JAXBContext context = JAXBContext.newInstance(ShapesList.class);
            // Get current working director
            String cwd = System.getProperty("user.dir");
            // Create a path name
            File file = fc.getSelectedFile();
            String name = file.getAbsolutePath();
            Unmarshaller um = context.createUnmarshaller();
            ShapesList myContacts = (ShapesList) um.unmarshal(new FileReader(name));
            for (Shape p : myContacts.getShapeList()) {
                // Print the list
                System.out.println(p.toString());
            }
        }

    }

    private void saveXMLFileList() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(ShapesList.class);
        String cwd = System.getProperty("user.dir");
        JFileChooser fc = new JFileChooser();
        FileFilter filter1 = new FileNameExtensionFilter("XML Files", "xml");
        fc.setFileFilter(filter1);
        int returnValue = fc.showSaveDialog(MyPaint.this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            String name = f.getAbsolutePath();
            System.out.println("Saving XML to file: " + name);
            try {
                File file = new File(name);
                Marshaller m = context.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                m.marshal(myList, file);
                m.marshal(myList, System.out);
            } catch (Exception e) {
            }
        }

    }

    private void createShapePropertiesPanel() {
        shapePropertiesPanel = new JFrame("Shape Properties");
        shapePropertiesPanel.setSize(fp.getWidth() / 2, fp.getHeight() / 2);
        shapePropertiesPanel.setVisible(true);
    }

    private void createLinePanel() {
        linePanel = new JPanel();
        linePanel.setLayout(new GridLayout(3, 2, 10, 5));
        lineLabel1 = new JLabel("Point X :");
        lineLabel2 = new JLabel("Point Y :");
        lineField1 = new JTextField(8);
        lineField2 = new JTextField(8);
        lineButton1 = new JButton("OK");
        lineButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (lineField1.getText().isEmpty() || lineField2.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(shapePropertiesPanel, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    paintLine();
                    shapePropertiesPanel.dispose();
                }

            }
        });
        lineButton2 = new JButton("Choose Color");
        lineButton2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                before = new Color(0, 0, 0);
                lineNow = JColorChooser.showDialog(MyPaint.this, "Pick a Color for Line", before);
            }
        });
        linePanel.add(lineLabel1);
        linePanel.add(lineField1);
        linePanel.add(lineLabel2);
        linePanel.add(lineField2);
        linePanel.add(lineButton2);
        linePanel.add(lineButton1);
    }

    private void createSquarePanel() {
        squarePanel = new JPanel();
        squarePanel.setLayout(new GridLayout(5, 2, 10, 5));
        squareLabel1 = new JLabel("Point X :");
        squareLabel2 = new JLabel("Point Y :");
        squareLabel3 = new JLabel("Square Width :");
        squareLabel4 = new JLabel("Square Height :");
        squareField1 = new JTextField(8);
        squareField2 = new JTextField(8);
        squareField3 = new JTextField(8);
        squareField4 = new JTextField(8);
        squareButton2 = new JButton("OK");
        squareButton2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (squareField1.getText().isEmpty() || squareField2.getText().isEmpty() || squareField3.getText().isEmpty() || squareField4.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(shapePropertiesPanel, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    paintSquare();
                    shapePropertiesPanel.dispose();
                }
            }
        });
        squareButton1 = new JButton("Choose Color");
        squareButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                before = new Color(0, 0, 0);
                squareNow = JColorChooser.showDialog(MyPaint.this, "Pick a Color for Square", before);
            }
        });
        squarePanel.add(squareLabel1);
        squarePanel.add(squareField1);
        squarePanel.add(squareLabel2);
        squarePanel.add(squareField2);
        squarePanel.add(squareLabel3);
        squarePanel.add(squareField3);
        squarePanel.add(squareLabel4);
        squarePanel.add(squareField4);
        squarePanel.add(squareButton1);
        squarePanel.add(squareButton2);
    }

    private void createTrianglePanel() {
        trianglePanel = new JPanel(new GridLayout(4, 2, 10, 5));
        triangleLabel1 = new JLabel("Point X :");
        triangleLabel2 = new JLabel("Point Y :");
        triangleLabel3 = new JLabel("Point Z :");
        triangleField1 = new JTextField(8);
        triangleField2 = new JTextField(8);
        triangleField3 = new JTextField(8);
        triangleButton1 = new JButton("Choose Color");
        triangleButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                before = new Color(0, 0, 0);
                triangleNow = JColorChooser.showDialog(MyPaint.this, "Pick a Color for Triangle", before);
            }
        });
        triangleButton2 = new JButton("OK");
        triangleButton2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (triangleField1.getText().isEmpty() || triangleField2.getText().isEmpty() || triangleField3.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(shapePropertiesPanel, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    paintTriangle();
                    shapePropertiesPanel.dispose();
                }
            }
        });
        trianglePanel.add(triangleLabel1);
        trianglePanel.add(triangleField1);
        trianglePanel.add(triangleLabel2);
        trianglePanel.add(triangleField2);
        trianglePanel.add(triangleLabel3);
        trianglePanel.add(triangleField3);
        trianglePanel.add(triangleButton1);

        trianglePanel.add(triangleButton2);
    }

    private void createCirclePanel() {
        circlePanel = new JPanel(new GridLayout(4, 2, 10, 5));
        circleLabel1 = new JLabel("Point X :");
        circleLabel2 = new JLabel("Point Y :");
        circleLabel3 = new JLabel("Radius :");
        circleField1 = new JTextField(8);
        circleField2 = new JTextField(8);
        circleField3 = new JTextField(8);
        circleButton1 = new JButton("Choose Color");
        circleButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                before = new Color(0, 0, 0);
                circleNow = JColorChooser.showDialog(MyPaint.this, "Pick a Color for Circle", before);
            }
        });
        circleButton2 = new JButton("OK");
        circleButton2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (circleField1.getText().isEmpty() || circleField2.getText().isEmpty() || circleField3.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(shapePropertiesPanel, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    paintCircle();
                    shapePropertiesPanel.dispose();
                }
            }
        });
        circlePanel.add(circleLabel1);
        circlePanel.add(circleField1);
        circlePanel.add(circleLabel2);
        circlePanel.add(circleField2);
        circlePanel.add(circleLabel3);
        circlePanel.add(circleField3);
        circlePanel.add(circleButton1);
        circlePanel.add(circleButton2);
    }

    private void paintCircle() {
        center.add(new MyCircleCanvas());
        center.repaint();
        center.revalidate();
    }

    private void paintSquare() {
        center.add(new MySquareCanvas());
        center.repaint();
        center.revalidate();
    }

    private void paintTriangle() {
        center.add(new MyTriangleCanvas());
        center.repaint();
        center.revalidate();
    }

    private void paintLine() {
        center.add(new MyLineCanvas());
        center.repaint();
        center.revalidate();
    }

    public class MyCircleCanvas extends JComponent {

        private void doDrawing(Graphics g) {

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(circleNow);
            g2d.fillOval(Integer.parseInt(circleField1.getText()), Integer.parseInt(circleField2.getText()), Integer.parseInt(circleField3.getText()), 100);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            doDrawing(g);
        }

    }

    public class MySquareCanvas extends JComponent {

        private void doDrawing(Graphics g) {

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(squareNow);
            g2d.fillRect(Integer.parseInt(squareField1.getText()), Integer.parseInt(squareField2.getText()), Integer.parseInt(squareField3.getText()), Integer.parseInt(squareField3.getText()));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            doDrawing(g);
        }

    }

    public class MyTriangleCanvas extends JComponent {

        private void doDrawing(Graphics g) {

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(triangleNow);
            Point t1 = new Point(Integer.parseInt(triangleField1.getText()) + 6, Integer.parseInt(triangleField2.getText()));
            Point t2 = new Point(Integer.parseInt(triangleField2.getText()) + 6 / 2, Integer.parseInt(triangleField3.getText()) - 2);
            Point t3 = new Point(Integer.parseInt(triangleField3.getText()), Integer.parseInt(triangleField1.getText()));
            g2d.drawLine(Integer.parseInt(triangleField1.getText()), Integer.parseInt(triangleField2.getText()), t2.x, t2.y);
            g2d.drawLine(Integer.parseInt(triangleField2.getText()), Integer.parseInt(triangleField3.getText()), t3.x, t3.y);

            g2d.drawLine(t2.x, t2.y, t3.x, t3.y);

        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            doDrawing(g);
        }

    }

    public class MyLineCanvas extends JComponent {

        private void doDrawing(Graphics g) {

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(lineNow);
            g2d.drawLine(Integer.parseInt(lineField1.getText()), 20, Integer.parseInt(lineField2.getText()), 20);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            doDrawing(g);
        }

    }

}
