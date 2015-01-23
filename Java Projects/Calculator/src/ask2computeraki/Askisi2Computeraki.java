package ask2computeraki;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Askisi2Computeraki extends JFrame {

    public Askisi2Computeraki() {
        buildCalculator();
    }

    private void buildCalculator() {
        final JTextField textField = new JTextField("0");
        textField.setPreferredSize(new Dimension(70, 70));
        textField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        textField.setEditable(false);
        textField.setFont(new Font(Font.SERIF, Font.TYPE1_FONT, 30));
        textField.setBorder(BorderFactory.createLineBorder(new Color(238, 238, 238), 8));
        textField.setBackground(Color.LIGHT_GRAY);
        add(textField, BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(0, 10, 10, 10));
        panel.setLayout(new GridLayout(4, 4, 7, 7));
        b1 = new JButton("1");
        b1.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        b1.setForeground(Color.BLUE);
        b2 = new JButton("2");
        b2.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        b2.setForeground(Color.BLUE);
        b3 = new JButton("3");
        b3.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        b3.setForeground(Color.BLUE);
        b4 = new JButton("4");
        b4.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        b4.setForeground(Color.BLUE);
        b5 = new JButton("5");
        b5.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        b5.setForeground(Color.BLUE);
        b6 = new JButton("6");
        b6.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        b6.setForeground(Color.BLUE);
        b7 = new JButton("7");
        b7.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        b7.setForeground(Color.BLUE);
        b8 = new JButton("8");
        b8.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        b8.setForeground(Color.BLUE);
        b9 = new JButton("9");
        b9.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        b9.setForeground(Color.BLUE);
        b0 = new JButton("0");
        b0.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        b0.setForeground(Color.BLUE);
        badd = new JButton("+");
        badd.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        badd.setForeground(Color.BLACK);
        bsub = new JButton("-");
        bsub.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        bsub.setForeground(Color.BLACK);
        bdiv = new JButton("/");
        bdiv.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        bdiv.setForeground(Color.BLACK);
        bmul = new JButton("*");
        bmul.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        bmul.setForeground(Color.BLACK);
        bequal = new JButton("=");
        bequal.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        bequal.setForeground(Color.BLACK);
        bc = new JButton("C");
        bc.setFont(new Font(Font.SERIF, Font.BOLD, 19));
        bc.setForeground(Color.RED);
        panel.add(b7);
        panel.add(b8);
        panel.add(b9);
        panel.add(bdiv);
        panel.add(b4);
        panel.add(b5);
        panel.add(b6);
        panel.add(bmul);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(bsub);
        panel.add(b0);
        panel.add(bc);
        panel.add(bequal);
        panel.add(badd);
        setFocusable(true);
        panel.setFocusable(true);
        panel.setPreferredSize(new Dimension(270, 270));
        add(panel, BorderLayout.SOUTH);
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                String action = e.getActionCommand();
                String textNow = textField.getText();
                if (action.equals("1")) {
                    textField.setText(textNow.replace(String.valueOf(result), "") + "1");
                    textAfter = textField.getText();
                    if(textAfter.equals("0")){
                        textField.setText(textAfter.replace("0", ""));
                    }
                }
            }
        });
        b1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                b1.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b1.setBackground(null);
            }
        });
        b2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();
                String textNow = textField.getText();
                if (action.equals("2")) {
                    textField.setText(textNow.replace(String.valueOf(result), "") + "2");
                    String textAfter = textField.getText();
                    if(textAfter.equals("0")){
                        textField.setText(textAfter.replace("0", ""));
                    }
                }
            }
        });
        b2.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                b2.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b2.setBackground(null);
            }
        });
        b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();
                String textNow = textField.getText();
                if (action.equals("3")) {
                    textField.setText(textNow.replace(String.valueOf(result), "") + "3");
                    String textAfter = textField.getText();
                    if(textAfter.equals("0")){
                        textField.setText(textAfter.replace("0", ""));
                    }
                }
            }
        });
        b3.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                b3.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b3.setBackground(null);
            }
        });
        b4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();
                String textNow = textField.getText();
                if (action.equals("4")) {
                    textField.setText(textNow.replace(String.valueOf(result), "") + "4");
                    String textAfter = textField.getText();
                    if(textAfter.equals("0")){
                        textField.setText(textAfter.replace("0", ""));
                    }
                }
            }
        });
        b4.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                b4.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b4.setBackground(null);
            }
        });
        b5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();
                String textNow = textField.getText();
                if (action.equals("5")) {
                    textField.setText(textNow.replace(String.valueOf(result), "") + "5");
                    String textAfter = textField.getText();
                    if(textAfter.equals("0")){
                        textField.setText(textAfter.replace("0", ""));
                    }
                }
            }
        });
        b5.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                b5.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b5.setBackground(null);
            }
        });
        b6.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();
                String textNow = textField.getText();
                if (action.equals("6")) {
                    textField.setText(textNow.replace(String.valueOf(result), "") + "6");
                    String textAfter = textField.getText();
                    if(textAfter.equals("0")){
                        textField.setText(textAfter.replace("0", ""));
                    }
                }
            }
        });
        b6.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                b6.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b6.setBackground(null);
            }
        });
        b7.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();
                String textNow = textField.getText();
                if (action.equals("7")) {
                    textField.setText(textNow.replace(String.valueOf(result), "") + "7");
                    String textAfter = textField.getText();
                    if(textAfter.equals("0")){
                        textField.setText(textAfter.replace("0", ""));
                    }
                }
            }
        });
        b7.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                b7.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b7.setBackground(null);
            }
        });
        b8.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();
                String textNow = textField.getText();
                if (action.equals("8")) {
                    textField.setText(textNow.replace(String.valueOf(result), "") + "8");
                    String textAfter = textField.getText();
                    if(textAfter.equals("0")){
                        textField.setText(textAfter.replace("0", ""));
                    }
                }
            }
        });
        b8.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                b8.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b8.setBackground(null);
            }
        });
        b9.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();
                String textNow = textField.getText();
                if (action.equals("9")) {
                    textField.setText(textNow.replace(String.valueOf(result), "") + "9");
                    String textAfter = textField.getText();
                    if(textAfter.equals("0")){
                        textField.setText(textAfter.replace("0", ""));
                    }
                }
            }
        });
        b9.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                b9.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b9.setBackground(null);
            }
        });
        b0.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String action = e.getActionCommand();
                String textNow = textField.getText();
                if (action.equals("0")) {
                    textField.setText(textNow + "0");
                }
                if (textNow.equals("0")) {
                    textField.setText("0");
                }
            }
        });
        b0.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                b0.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b0.setBackground(null);
            }
        });
        badd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                r1 = Integer.parseInt(textField.getText());
                option = badd.getText();
                textField.setText("");
            }
        });
        badd.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                badd.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                badd.setBackground(null);
            }
        });
        bsub.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                r1 = Integer.parseInt(textField.getText());
                option = bsub.getText();
                textField.setText("");
            }
        });
        bsub.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                bsub.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bsub.setBackground(null);
            }
        });
        bmul.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                r1 = Integer.parseInt(textField.getText());
                option = bmul.getText();
                textField.setText("");
            }
        });
        bmul.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                bmul.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bmul.setBackground(null);
            }
        });
        bdiv.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                r1 = Integer.parseInt(textField.getText());
                option = bdiv.getText();
                textField.setText("");
            }
        });
        bdiv.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                bdiv.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bdiv.setBackground(null);
            }
        });
        bequal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                r2 = Integer.parseInt(textField.getText());
                String la = e.getActionCommand();
                if (option.equals("+")) {
                    result = r1 + r2;
                }
                if (option.equals("-")) {
                    result = r1 - r2;
                }
                if (option.equals("*")) {
                    result = r1 * r2;
                }
                if (option.equals("/")) {
                    result = r1 / r2;
                }
                textField.setText(String.valueOf(result));
            }
        });
        bequal.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                bequal.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bequal.setBackground(null);
            }
        });
        bc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String c = "0";
                textField.setText(c);
                r2 = 0;
                r1 = 0;
                result = 0;
            }
        });
        bc.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                bc.setBackground(new Color(195, 206, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bc.setBackground(null);
            }
        });
        
    }

    public static void main(String[] args) {
        Askisi2Computeraki calc = new Askisi2Computeraki();
        calc.setTitle("Calculator");
        calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calc.setVisible(true);
        calc.setSize(300, 400);
        calc.setResizable(false);
        calc.setLocation(new Point(800, 300));
       
    }

    private static int r1;
    private static int r2;
    private static String option, textAfter;
    private static int result;
    private static JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, badd, bsub, bdiv, bmul, bequal, bc;
}
