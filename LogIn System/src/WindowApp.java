import java.awt.Dimension;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

//import com.sun.glass.events.KeyEvent;

public class WindowApp extends JFrame implements ActionListener, ItemListener, PropertyChangeListener{

    @Override//This is for checkboxes
    public void itemStateChanged(ItemEvent i) {
        // TODO Auto-generated method stub
//        if (i. == cmbUserType.getSelectedItem()) System.out.println(cmbUserType.getSelectedItem());

    }

    @Override
    public void actionPerformed(ActionEvent a) {
        // TODO Auto-generated method stub
        if(a.getSource() == quit) System.exit(0);
        else if(a.getActionCommand().equalsIgnoreCase("Log out")) System.out.print("Hello Bitch");
        else if(a.getActionCommand().equalsIgnoreCase("Log in")) login();
        else if(a.getActionCommand().equalsIgnoreCase("Sign up")) registration();
    }

    @Override
    public void propertyChange(PropertyChangeEvent p) {
        // TODO Auto-generated method stub
        String propertyName = p.getPropertyName();
        if ("Individual".equals(propertyName)) System.out.println("Hello My friend");
    }

    private AdminSystem admin;
    private boolean isValid;
    private boolean isExists;

    String strUser;
    String strPassword;

    JMenuBar menu;

    JMenu user;
    JMenu view;

    JMenuItem signUp;
    JMenuItem login;
    JMenuItem logout;

    JPanel panel;

    String message;
    String[] userType = {"Individual", "Corporate", "Nonprofit Organisation"};
    int result;

    JTextField userField;
    JPasswordField passField;
    JComboBox cmbUserType;

    JMenuItem quit;

    public WindowApp(){

        admin = new AdminSystem();

        menubar();

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void menubar(){
        menu = new JMenuBar();

        user = new JMenu("User");
        view = new JMenu("View");

        view.setEnabled(false);

        signUp = new JMenuItem("Sign Up");
        signUp.setMnemonic(KeyEvent.VK_U);

        login = new JMenuItem("Log In");
        login.setMnemonic(KeyEvent.VK_L);

        logout = new JMenuItem("Log Out");
        logout.setMnemonic(KeyEvent.VK_O);

        quit = new JMenuItem("Quit");
        quit.setMnemonic(KeyEvent.VK_Q);

        user.add(signUp);
        user.add(login);
        user.add(logout);

        user.addSeparator();

        user.add(quit);

        menu.add(user);
        menu.add(view);

        this.setJMenuBar(menu);

        signUp.addActionListener(this);
        login.addActionListener(this);
        logout.addActionListener(this);

        quit.addActionListener(this);

        this.setLocation(450, 350);
        this.setPreferredSize(new Dimension(300, 300));

    }

    public void login(){
        message = "Please enter your user name and password.";

        userField = new JTextField();
        passField = new JPasswordField();

        result = JOptionPane.showOptionDialog(this, new Object[] {message, userField, passField},
                "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        strUser = userField.getText();
        strPassword = new String(passField.getPassword());

        User temp;

        isValid = admin.validateUser(strUser, strPassword);

        if(isValid) {
            temp = admin.getUser(strUser, strPassword);
//            System.out.println("User is Validated");
//            System.out.println("User: " + temp.getUserName() + "Password: " + temp.getUserPassword());
            view.setEnabled(true);
            panel(temp.getUserName());
        }else{
            JOptionPane.showMessageDialog(this, "Invalid user name or password !");
        }
    }

    public void registration(){

        message = "User registration:";

        userField = new JTextField();
        passField = new JPasswordField();

        cmbUserType = new JComboBox<>(userType);

        result = JOptionPane.showOptionDialog(this, new Object[] {message, userField, passField, cmbUserType},
                "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (result == JOptionPane.OK_OPTION) {
            //Sequence of taking the data Strings were in the wrong sequence.
            strUser = userField.getText(); // -> help of gpt
            strPassword = new String(passField.getPassword());// -> help of gpt
            if (admin.isUsernameTaken(strUser)) {
                JOptionPane.showMessageDialog(this, "The same account name already exists!");
            } else {
                if(!strPassword.isBlank()){
                    admin.addUser(strUser, strPassword, (String) cmbUserType.getSelectedItem());
                    System.out.println(strUser + " " + strPassword + " " + cmbUserType.getSelectedItem());
                    JOptionPane.showMessageDialog(this, "User is Stored!");
                }else JOptionPane.showMessageDialog(this, "Password cannot be blank");
            }
        }
    }

    public void panel(String userName){
        panel = new JPanel();
        JLabel lblWelcome = new JLabel();
        String strWelcome = "Welcome " + userName;
        lblWelcome.setText(strWelcome);

        panel.add(lblWelcome);

        this.add(panel);
        this.pack();
    }
}