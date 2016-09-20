package pdie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class DBProject extends JFrame {

    private JPanel main, login, register, dbProperties, hostPropertiesPanel, portPropertiesPanel, afterLogin, panel1, panel2, update;
    private JTextField registerUsername, userName, hostField, dbnameField, dbUsernameField, dbPortField;
    private JPasswordField registerPassword, retypePassword, password, dbPasswordField, old, newP, retypeNewPass;
    private JLabel registerUser, registerPass, retypePass, user, pass, dbhost, dbname,
            dbusername, dbpass, dbDriver, dbPort, loggedUser,
            loggedUserName, loggedUseremail, loggedUserEmailValue, loggedUserSubject,
            loggedUserSubjectValue, alreadyLog, alreadyReg, oldPass, newPass, newRetypePass, 
            loggedUserDate, loggedUserDateValue;
    private JButton registerBtn, loginBtn, dbSubmitBtn, changePassword, log, reg, logout, updateBtn, cancelBtn;
    private String leftFirstText, rightFirstText, host, driver, db, dbuser, dbpassword, port, a, b, c;
    private char[] leftSecondText, leftThirdText, rightText;
    private JComboBox dbDriverChooser;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public DBProject() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(450, 350);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setTitle("Project 2 PDIE");
        initComponents();
    }

    public static void main(String[] args) {
        new DBProject().setVisible(true);
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10));
        main = new JPanel();
        main.setLayout(new BorderLayout());
        register = new JPanel();
        register.setBorder(new EmptyBorder(20, 40, 40, 40));
        register.setBorder(new LineBorder(getBackground(), 30));
        login = new JPanel();
        login.setBorder(new LineBorder(getBackground(), 20));
        setupRegisterPanel();
        setupLoginPanel();
        setupDbPropertiesPanel();
        setupAfterLoginPanel();
        setupUpdatePanel();
        main.add(dbProperties, BorderLayout.CENTER);
        this.add(main, BorderLayout.CENTER);
    }

    private void setupRegisterPanel() {
        register.setLayout(new GridLayout(4, 2, 10, 5));
        initializeRegisterComponents();
        register.add(registerUser);
        register.add(registerUsername);
        register.add(registerPass);
        register.add(registerPassword);
        register.add(retypePass);
        register.add(retypePassword);
        register.add(registerBtn);
        register.add(panel1);
    }

    private void setupLoginPanel() {
        login.setLayout(new GridLayout(3, 2, 10, 5));
        initializeLoginComponents();
        login.add(user);
        login.add(userName);
        login.add(pass);
        login.add(password);
        login.add(loginBtn);
        login.add(panel2);
    }

    private void initializeRegisterComponents() {
        registerUsername = new JTextField(10);
        registerUser = new JLabel("Username");
        registerPassword = new JPasswordField(10);
        registerPass = new JLabel("Password");
        retypePassword = new JPasswordField(10);
        retypePass = new JLabel("Retype password");
        registerBtn = new JButton("SIGN UP");
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 1));
        alreadyReg = new JLabel("Already a member? Login ");
        reg = new JButton("Here");
        reg.setForeground(Color.red);
        panel1.add(alreadyReg);
        panel1.add(reg);
        setupRegisterEventListener();
        reg.addActionListener((ActionEvent e) -> {
            main.remove(register);
            main.add(login, BorderLayout.CENTER);
            DBProject.this.setTitle("Login Form");
            main.revalidate();
            main.repaint();
        });
    }

    private void initializeLoginComponents() {
        user = new JLabel("Username");
        pass = new JLabel("Password");
        userName = new JTextField(10);
        password = new JPasswordField(10);
        loginBtn = new JButton("SIGN IN");
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(2, 1));
        alreadyLog = new JLabel("Dont have account? Register ");
        log = new JButton("Here");
        log.setForeground(Color.red);
        panel2.add(alreadyLog);
        panel2.add(log);
        setupLoginEventListener();
        log.addActionListener((ActionEvent e) -> {
            main.remove(login);
            main.add(register, BorderLayout.CENTER);
            DBProject.this.setTitle("Registration Form");
            main.revalidate();
            main.repaint();
        });
    }

    private void setupRegisterEventListener() {
        registerBtn.addActionListener((ActionEvent e) -> {
            leftFirstText = registerUsername.getText();
            leftSecondText = registerPassword.getPassword();
            leftThirdText = retypePassword.getPassword();
            if (leftFirstText.isEmpty() && leftSecondText.length == 0 && leftThirdText.length == 0) {
                JOptionPane.showMessageDialog(DBProject.this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (leftSecondText.length == 0) {
                JOptionPane.showMessageDialog(DBProject.this, "Password is required", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (leftThirdText.length == 0) {
                JOptionPane.showMessageDialog(DBProject.this, "Please retype your password", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (leftSecondText.length != leftThirdText.length) {
                JOptionPane.showMessageDialog(DBProject.this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    connection = instanciateDB();
                    preparedStatement = connection.prepareStatement("insert into usersinfo(username, password, email, date, subject) values(?,?,?, now(), 'Distributed Application Environment')");
                    preparedStatement.setString(1, leftFirstText);
                    preparedStatement.setString(2, new String(leftSecondText));
                    preparedStatement.setString(3, leftFirstText + "@stud.fit.vutbr.cz");
                    int x = preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(DBProject.this, "Registration Failed");
                    System.out.println(ex.getMessage());
                }
                JOptionPane.showMessageDialog(DBProject.this, "Registration Successful", "Success", JOptionPane.PLAIN_MESSAGE);
                registerUsername.setText(null);
                registerPassword.setText(null);
                retypePassword.setText(null);
            }
        });
    }

    private void setupLoginEventListener() {
        loginBtn.addActionListener((ActionEvent e) -> {
            rightFirstText = userName.getText();
            rightText = password.getPassword();
            if (rightFirstText.isEmpty() && rightText.length == 0) {
                JOptionPane.showMessageDialog(DBProject.this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (rightText.length == 0) {
                JOptionPane.showMessageDialog(DBProject.this, "Password is required", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    connection = instanciateDB();
                    preparedStatement = connection.prepareStatement("select * from usersinfo where username =? and password=?");
                    preparedStatement.setString(1, rightFirstText);
                    preparedStatement.setString(2, new String(rightText));
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        JOptionPane.showMessageDialog(DBProject.this, "Login Successfull");
                        userName.setText(null);
                        password.setText(null);
                        loggedUserName.setText(rightFirstText);
                        loggedUserEmailValue.setText(resultSet.getString("email"));
                        loggedUserSubjectValue.setText(resultSet.getString("subject"));
                        loggedUserDateValue.setText(resultSet.getString("date"));
                        main.remove(login);
                        main.add(afterLogin);
                        DBProject.this.setTitle("User Profile");
                        main.revalidate();
                        main.repaint();
                    } else {
                        JOptionPane.showMessageDialog(DBProject.this, "Login Failed");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(DBProject.this, ex.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setupDbPropertiesPanel() {
        dbProperties = new JPanel();
        dbProperties.setLayout(new GridLayout(6, 2, 10, 10));
        dbProperties.setBorder(new EmptyBorder(10, 10, 10, 10));
        setupDBPanelComponents();
        hostPropertiesPanel = new JPanel();
        hostPropertiesPanel.setLayout(new GridLayout(1, 2));
        portPropertiesPanel = new JPanel();
        portPropertiesPanel.setLayout(new GridLayout(1, 2));
        hostPropertiesPanel.add(dbhost);
        hostPropertiesPanel.add(hostField);
        portPropertiesPanel.add(dbPort);
        portPropertiesPanel.add(dbPortField);
        dbProperties.add(dbDriver);
        dbProperties.add(dbDriverChooser);
        dbProperties.add(hostPropertiesPanel);
        dbProperties.add(portPropertiesPanel);
        dbProperties.add(dbname);
        dbProperties.add(dbnameField);
        dbProperties.add(dbusername);
        dbProperties.add(dbUsernameField);
        dbProperties.add(dbpass);
        dbProperties.add(dbPasswordField);
        dbProperties.add(dbSubmitBtn);
        createEventListenerDBProperties();
    }

    private void setupDBPanelComponents() {
        dbhost = new JLabel("Database Host");
        dbname = new JLabel("Database Name");
        dbusername = new JLabel("Database User Userame");
        dbpass = new JLabel("Database User Password");
        hostField = new JTextField("localhost");
        dbUsernameField = new JTextField("root");
        dbnameField = new JTextField("projectdatabase");
        dbPasswordField = new JPasswordField(10);
        dbSubmitBtn = new JButton("Submit");
        String[] items = {"com.mysql.jdbc.Driver", "org.postgresql.Driver", "oracle.jdbc.driver.OracleDriver", "org.sqlite.JDBC"};
        dbDriverChooser = new JComboBox(items);
        dbDriver = new JLabel("Database Driver");
        dbPort = new JLabel("Port");
        dbPortField = new JTextField("3306");
    }

    private void createEventListenerDBProperties() {
        dbSubmitBtn.addActionListener((ActionEvent e) -> {
            if (hostField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(DBProject.this, "Database Host is required", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (dbPortField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(DBProject.this, "Port is required", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (dbnameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(DBProject.this, "Database Name is required", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (dbUsernameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(DBProject.this, "Database User Username is required", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (dbDriverChooser.getSelectedItem().equals("com.mysql.jdbc.Driver")) {
                    driver = (String) dbDriverChooser.getSelectedItem();
                    port = dbPortField.getText();
                    host = "jdbc:mysql://" + hostField.getText() + ":" + port + "/";
                } else if (dbDriverChooser.getSelectedItem().equals("oracle.jdbc.driver.OracleDriver")) {
                    driver = (String) dbDriverChooser.getSelectedItem();
                    port = dbPortField.getText();
                    host = "jdbc:oracle:thin:@" + hostField.getText() + ":" + port + ":";
                } else if (dbDriverChooser.getSelectedItem().equals("org.postgresql.Driver")) {
                    driver = (String) dbDriverChooser.getSelectedItem();
                    port = dbPortField.getText();
                    host = "jdbc:postgresql://" + hostField.getText() + ":" + port + "/";
                } else {
                    driver = (String) dbDriverChooser.getSelectedItem();
                    host = "jdbc:sqlite:";
                }
                db = dbnameField.getText();
                dbuser = dbUsernameField.getText();
                dbpassword = new String(dbPasswordField.getPassword());
                JOptionPane.showMessageDialog(DBProject.this, "Connection Credentials \n"
                        + "\n" + "Driver: " + driver + "\n"
                        + "\n" + "Host: " + hostField.getText() + "\n"
                        + "\n" + "Host Url: " + host + "\n"
                        + "\n" + "Database Name: " + db + "\n"
                        + "\n" + "Database User Username: " + dbuser + "\n"
                        + "\n" + "Database User Password: " + dbpassword, "Connection Manager", JOptionPane.PLAIN_MESSAGE);
                main.remove(dbProperties);
                main.add(register, BorderLayout.CENTER);
                DBProject.this.setTitle("Registration Form");
                main.revalidate();
                main.repaint();
            }
        });
    }

    private Connection instanciateDB() {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(host + db, dbuser, dbpassword);
            System.out.println("Connection Established");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection not Established");
            JOptionPane.showMessageDialog(DBProject.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return con;
    }

    private void setupAfterLoginPanel() {
        afterLogin = new JPanel();
        afterLogin.setLayout(new GridLayout(5, 2));
        afterLogin.setBorder(new EmptyBorder(15, 15, 15, 15));
        setupComponentsAfterLogin();
        afterLogin.add(loggedUser);
        afterLogin.add(loggedUserName);
        afterLogin.add(loggedUseremail);
        afterLogin.add(loggedUserEmailValue);
        afterLogin.add(loggedUserSubject);
        afterLogin.add(loggedUserSubjectValue);
        afterLogin.add(loggedUserDate);
        afterLogin.add(loggedUserDateValue);
        afterLogin.add(changePassword);
        clickEventChangePassword();
        afterLogin.add(logout);
    }

    private void setupComponentsAfterLogin() {
        loggedUser = new JLabel("Username: ");
        loggedUserName = new JLabel("");
        loggedUseremail = new JLabel("Email: ");
        loggedUserEmailValue = new JLabel("");
        loggedUserSubject = new JLabel("Subject: ");
        loggedUserDate = new JLabel("Date Registered: ");
        loggedUserSubjectValue = new JLabel("");
        loggedUserDateValue = new JLabel("");
        changePassword = new JButton("Change Password");
        logout = new JButton("Logout");
        logout.addActionListener((ActionEvent e) -> {
            main.remove(afterLogin);
            main.add(login);
            DBProject.this.setTitle("Login Form");
            main.revalidate();
            main.repaint();
        });
    }

    private void clickEventChangePassword() {
        changePassword.addActionListener((ActionEvent e) -> {
            main.remove(afterLogin);
            main.add(update);
            DBProject.this.setTitle("Change Password Form");
            main.revalidate();
            main.repaint();
        });
    }

    private void setupUpdatePanel() {
        update = new JPanel();
        update.setLayout(new GridLayout(4, 2));
        oldPass = new JLabel("Current Password");
        old = new JPasswordField(10);
        newPass = new JLabel("New Password");
        newP = new JPasswordField(10);
        newRetypePass = new JLabel("Retype Password");
        retypeNewPass = new JPasswordField(10);
        updateBtn = new JButton("Update Password");
        cancelBtn = new JButton("Cancel");
        update.add(oldPass);
        update.add(old);
        update.add(newPass);
        update.add(newP);
        update.add(newRetypePass);
        update.add(retypeNewPass);
        update.add(updateBtn);
        update.add(cancelBtn);
        clickEventUpdatePassword();
        clickEventCancelUpdatePassword();
    }

    private void clickEventUpdatePassword() {
        updateBtn.addActionListener((ActionEvent e) -> {
            a = new String(old.getPassword());
            b = new String(newP.getPassword());
            c = new String(retypeNewPass.getPassword());
            if (a.isEmpty() && b.isEmpty() && c.isEmpty()) {
                JOptionPane.showMessageDialog(DBProject.this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (a.isEmpty()) {
                JOptionPane.showMessageDialog(DBProject.this, "Current Password is required", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (b.isEmpty()) {
                JOptionPane.showMessageDialog(DBProject.this, "Please type your preffered password", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (c.isEmpty()) {
                JOptionPane.showMessageDialog(DBProject.this, "Please retype your password", "Error", JOptionPane.ERROR_MESSAGE);
            }else if(!b.equals(c)){
                JOptionPane.showMessageDialog(DBProject.this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                
                try {
                    connection = instanciateDB();
                    preparedStatement = connection.prepareStatement("select * from usersinfo where password=? and id=?");
                    preparedStatement.setString(1, a);
                    preparedStatement.setString(2, resultSet.getString("id"));
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        if (a.equals(resultSet.getString("password"))){
                            preparedStatement = connection.prepareStatement("update usersinfo set password=? where password=? and id=?");
                            preparedStatement.setString(1, new String(newP.getPassword()));
                            preparedStatement.setString(2, a);
                            preparedStatement.setString(3, resultSet.getString("id"));
                            int z = preparedStatement.executeUpdate();
                            JOptionPane.showMessageDialog(DBProject.this, "Password Changed Successfully");
                            old.setText(null);
                            newP.setText(null);
                            retypeNewPass.setText(null);
                            main.remove(update);
                            main.add(login);
                            DBProject.this.setTitle("Login Form");
                            main.revalidate();
                            main.repaint();
                        }
                    } else {
                        JOptionPane.showMessageDialog(DBProject.this, "Action Failed");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(DBProject.this, ex.getMessage(), "Action Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void clickEventCancelUpdatePassword() {
        cancelBtn.addActionListener((ActionEvent e) -> {
            main.remove(update);
            main.add(afterLogin);
            DBProject.this.setTitle("User Profile");
            main.revalidate();
            main.repaint();
        });
    }
}
