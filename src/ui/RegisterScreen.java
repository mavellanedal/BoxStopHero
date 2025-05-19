package ui;

import database.UserDAO;
import models.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterScreen {
    private JPanel registerPanel;
    private JPanel registerFormPanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel repeatPasswordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    private JButton registerButton;
    private JButton returnToLoginButton;
    private JLabel errorLabel;

    public RegisterScreen(JFrame frame) {
        registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(new Color(11, 20, 28));

        registerFormPanel = new JPanel();
        registerFormPanel.setPreferredSize(new Dimension(400, 400));
        registerFormPanel.setBackground(Color.WHITE);
        registerFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        registerFormPanel.setLayout(new BoxLayout(registerFormPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Playfair Display", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(74, 78, 105));
        registerFormPanel.add(titleLabel);
        registerFormPanel.add(Box.createVerticalStrut(20));

        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setOpaque(false);
        usernameLabel = new JLabel("Usuario:");
        styleLabel(usernameLabel);
        usernameField = new JTextField();
        usernamePanel.add(usernameLabel, BorderLayout.NORTH);
        usernamePanel.add(usernameField, BorderLayout.CENTER);
        usernamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        registerFormPanel.add(usernamePanel);
        registerFormPanel.add(Box.createVerticalStrut(10));

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setOpaque(false);
        passwordLabel = new JLabel("Contraseña:");
        styleLabel(passwordLabel);
        passwordField = new JPasswordField();
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        registerFormPanel.add(passwordPanel);
        registerFormPanel.add(Box.createVerticalStrut(10));

        JPanel repeatPasswordPanel = new JPanel(new BorderLayout());
        repeatPasswordPanel.setOpaque(false);
        repeatPasswordLabel = new JLabel("Repetir contraseña:");
        styleLabel(repeatPasswordLabel);
        repeatPasswordField = new JPasswordField();
        repeatPasswordPanel.add(repeatPasswordLabel, BorderLayout.NORTH);
        repeatPasswordPanel.add(repeatPasswordField, BorderLayout.CENTER);
        repeatPasswordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        registerFormPanel.add(repeatPasswordPanel);
        registerFormPanel.add(Box.createVerticalStrut(20));

        returnToLoginButton = new RoundedButton("Go to Sign In");
        returnToLoginButton.setFont(new Font("Playfair Display", Font.BOLD, 20));
        returnToLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnToLoginButton.addMouseListener(new ReturnToLoginBtnListener(frame, returnToLoginButton));


        registerButton = new RoundedButton("Register");
        registerButton.setFont(new Font("Playfair Display", Font.BOLD, 20));
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.addMouseListener(new RegisterBtnListener(passwordField, repeatPasswordField, usernameField, frame, registerButton));

        errorLabel = new JLabel(
                "<html><div style='text-align: center;'>The password must contain at least:<br> - 1 digit<br>- 1 uppercase letter<br>- 1 special character</div></html>"
        );
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Playfair Display", Font.PLAIN, 14));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false);
        registerFormPanel.add(errorLabel);
        registerFormPanel.add(Box.createVerticalStrut(10));
        registerFormPanel.add(registerButton);
        registerFormPanel.add(Box.createVerticalStrut(10));
        registerFormPanel.add(returnToLoginButton);
        registerPanel.add(registerFormPanel, new GridBagConstraints());
    }

    private class ReturnToLoginBtnListener extends MouseAdapter {
        private JFrame frame;
        private JButton returnToLoginButton;

        public ReturnToLoginBtnListener(JFrame frame, JButton returnToLoginButton) {
            this.frame = frame;
            this.returnToLoginButton = returnToLoginButton;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            SignInScreen signInScreen = new SignInScreen(frame);
            frame.setContentPane(signInScreen.getSignInPanel());
            frame.revalidate();
            frame.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            returnToLoginButton.setBackground(new Color(27, 58, 52));
        }
        public void mouseExited(MouseEvent e) {
            returnToLoginButton.setBackground(new Color(194, 159, 97));
        }
    }

    private class RegisterBtnListener extends MouseAdapter {
        private JPasswordField passwordField;
        private JPasswordField repeatPasswordField;
        private JTextField usernameField;
        private JFrame frame;
        private JButton registerButton;

        public RegisterBtnListener(JPasswordField passwordField, JPasswordField repeatPasswordField, JTextField usernameField, JFrame frame, JButton registerButton) {
            this.passwordField = passwordField;
            this.repeatPasswordField = repeatPasswordField;
            this.usernameField = usernameField;
            this.frame = frame;
            this.registerButton = registerButton;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            registerButton.setBackground(new Color(27, 58, 52));
        }
        public void mouseExited(MouseEvent e) {
            registerButton.setBackground(new Color(194, 159, 97));
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (usernameField.getText().isEmpty()) {
                passwordLabel.setForeground(new Color(74, 78, 105));
                passwordLabel.setText("Password:");
                repeatPasswordLabel.setForeground(new Color(74, 78, 105));
                repeatPasswordLabel.setText("Repeat password: ");
                usernameLabel.setForeground(Color.RED);
                usernameLabel.setText("You must enter a username:");
                registerFormPanel.setSize(new Dimension(400, 400));
            } else if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                repeatPasswordLabel.setForeground(new Color(74, 78, 105));
                repeatPasswordLabel.setText("Repeat password: ");
                usernameLabel.setForeground(new Color(74, 78, 105));
                usernameLabel.setText("Username:");
                passwordLabel.setForeground(Color.RED);
                passwordLabel.setText("You must enter a password:");
                registerFormPanel.setSize(new Dimension(400, 400));
            } else if (!String.valueOf(passwordField.getPassword()).equals(String.valueOf(repeatPasswordField.getPassword()))) {
                passwordLabel.setForeground(new Color(74, 78, 105));
                passwordLabel.setText("Password: ");
                usernameLabel.setForeground(new Color(74, 78, 105));
                usernameLabel.setText("Username:");
                repeatPasswordLabel.setForeground(Color.RED);
                repeatPasswordLabel.setText("The passwords do not match:");
                registerFormPanel.setSize(new Dimension(400, 400));
            } else if (!isValidPassword(String.valueOf(passwordField.getPassword()))) {
                repeatPasswordLabel.setForeground(new Color(74, 78, 105));
                repeatPasswordLabel.setText("Repeat password: ");
                usernameLabel.setForeground(new Color(74, 78, 105));
                usernameLabel.setText("Username:");
                passwordLabel.setForeground(Color.RED);
                errorLabel.setVisible(true);
                registerFormPanel.setPreferredSize(new Dimension(400, 475));
                registerFormPanel.revalidate();
            } else {
                UserDAO dao = new UserDAO();
                boolean success = dao.registerUser(usernameField.getText(), String.valueOf(passwordField.getPassword()));
                if (success) {
                    SignInScreen signInScreen = new SignInScreen(frame);
                    frame.setContentPane(signInScreen.getSignInPanel());
                    frame.revalidate();
                    frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "El nombre de usuario ya existe o hubo un error.");
                }
            }
        }
        private boolean isValidPassword(String password) {
            String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$";
            return password.matches(regex);
        }
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Playfair Display", Font.PLAIN, 14));
        label.setForeground(new Color(74, 78, 105));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
    }

    public JPanel getRegisterPanel() {
        return registerPanel;
    }
}

