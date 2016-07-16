package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame {

    private JToolBar toll;
    private JMenu menu;
    private JMenuBar menuBar;
    private JMenuItem item;
    private JPanel vasi, buttons, panw;
    private JTextArea results;
    private JScrollPane scrollPane;
    private JFileChooser fc;
    private File f;
    private BufferedReader br;
    private FileOutputStream outputstream;
    private BufferedWriter w;
    private JButton b0, b2, b3, b4, b5, b6, b7;

    public Main() {
        initComponent();

    }

    public final void initComponent() {
        vasi = new JPanel(new BorderLayout());
        results = new JTextArea(20, 10);
        scrollPane = new JScrollPane(results);

        toll = new JToolBar();

        b0 = new JButton("");
        b0.setIcon(new ImageIcon("icons/open.png"));
        b0.setToolTipText("Open");
        b0.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fc = new JFileChooser();
                fc.showOpenDialog(vasi);
                f = fc.getSelectedFile();
                if (f == null) {
                } else {
                    FileReader myFile = null;
                    br = null;

                    try {
                        myFile = new FileReader(f.getAbsolutePath());
                        br = new BufferedReader(myFile);
                        results.setText("");
                        while (true) {

                            String line = br.readLine();
                            if (line == null) {   //EOF
                                break;
                            }
                            results.append(line + "\n");
                        }
                    } catch (IOException ee) {
                        System.out.println(ee.getMessage());
                    } finally {
                        try {
                            if (br != null) {
                                br.close();
                            }
                            if (f != null) {
                                myFile.close();
                            }
                        } catch (IOException e1) {
                        }
                    }
                }
            }
        });
        b2 = new JButton("");
        b2.setIcon(new ImageIcon("icons/import.png"));
        b2.setToolTipText("Import");
        b2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fc = new JFileChooser();
                fc.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
                fc.showOpenDialog(vasi);
                f = fc.getSelectedFile();
                if (f == null) {
                } else {
                    FileReader myFile = null;
                    br = null;

                    try {
                        myFile = new FileReader(f.getAbsolutePath());
                        br = new BufferedReader(myFile);
                        results.setText("");
                        while (true) {

                            String line = br.readLine();
                            if (line == null) {   //EOF
                                break;
                            }
                            results.append(line + "\n");
                        }
                    } catch (IOException ee) {
                        System.out.println(ee.getMessage());
                    } finally {
                        try {
                            if (br != null) {
                                br.close();
                            }
                            if (f != null) {
                                myFile.close();
                            }
                        } catch (IOException e1) {
                        }
                    }
                }
            }
        });
        b3 = new JButton("");
        b3.setIcon(new ImageIcon("icons/export.png"));
        b3.setToolTipText("Export");
        b3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    fc = new JFileChooser();
                    int returnVal = fc.showSaveDialog(null); 
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        outputstream = new FileOutputStream(fc.getSelectedFile()); 
                        w = new BufferedWriter(new OutputStreamWriter(outputstream)); 
                        for (String line : results.getText().split("\\n")) {
                            w.append(line);
                            w.newLine();
                        }
                        System.out.println(results.getText());
                    }
                    w.close();
                }catch (Exception ex) {
                }
            }
        });
        b4 = new JButton("");
        b4.setIcon(new ImageIcon("icons/save.png"));
        b4.setToolTipText("Save");
        b5 = new JButton("");
        b5.setIcon(new ImageIcon("icons/run.png"));
        b5.setToolTipText("Run");
        b6 = new JButton("");
        b6.setIcon(new ImageIcon("icons/debug.png"));
        b6.setToolTipText("Debug");
        b7 = new JButton("");
        b7.setIcon(new ImageIcon("icons/link.png"));
        b7.setToolTipText("Link");
        toll.add(b0);
        toll.add(b2);
        toll.add(b3);
        toll.add(b4);
        toll.add(b5);
        toll.add(b6);
        toll.add(b7);

        menu = new JMenu("Color Menu");
        item = new JMenuItem("Pick a Color");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Color after = JColorChooser.showDialog(null, "Please pick a color", Color.BLACK);
            }
        });
        menu.add(item);
        menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);

        panw = new JPanel();
        panw.setLayout(new BorderLayout());
        panw.add(toll, BorderLayout.WEST);
        JTextField praksi = new JTextField("0");
        vasi.add(panw, BorderLayout.NORTH);
        vasi.add(scrollPane, BorderLayout.CENTER);
        add(vasi);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        Main k = new Main();
        k.setBounds(500, 100, 800, 800);
    }

}
