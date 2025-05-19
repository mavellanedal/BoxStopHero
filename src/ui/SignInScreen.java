    package ui;

    import database.UserDAO;
    import models.RoundedButton;
    import models.User;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;

    public class SignInScreen {
        private JPanel signInPanel;
        private JPanel signInFormPanel;
        private JLabel usernameLabel;
        private JLabel passwordLabel;
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton signInButton;
        private JLabel errorLabel;
        private JButton goToRegisterButton;

        public SignInScreen(JFrame frame) {
            signInPanel = new JPanel(new GridBagLayout());
            signInPanel.setBackground(new Color(11, 20, 28));

            signInFormPanel = new JPanel();
            signInFormPanel.setPreferredSize(new Dimension(400, 350));
            signInFormPanel.setBackground(Color.WHITE);
            signInFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
            signInFormPanel.setLayout(new BoxLayout(signInFormPanel, BoxLayout.Y_AXIS));

            JLabel titleLabel = new JLabel("Sign In");
            titleLabel.setFont(new Font("Playfair Display", Font.BOLD, 24));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titleLabel.setForeground(new Color(74, 78, 105));
            signInFormPanel.add(titleLabel);
            signInFormPanel.add(Box.createVerticalStrut(20));

            JPanel usernamePanel = new JPanel();
            usernamePanel.setLayout(new BorderLayout());
            usernamePanel.setOpaque(false);
            usernameLabel = new JLabel("Username:");
            createLabelStyle(usernameLabel);
            usernameField = new JTextField();
            usernamePanel.add(usernameLabel, BorderLayout.NORTH);
            usernamePanel.add(usernameField, BorderLayout.CENTER);
            usernamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            signInFormPanel.add(usernamePanel);
            signInFormPanel.add(Box.createVerticalStrut(10));

            JPanel passwordPanel = new JPanel();
            passwordPanel.setLayout(new BorderLayout());
            passwordPanel.setOpaque(false);
            passwordLabel = new JLabel("Password:");
            createLabelStyle(passwordLabel);
            passwordField = new JPasswordField();
            passwordPanel.add(passwordLabel, BorderLayout.NORTH);
            passwordPanel.add(passwordField, BorderLayout.CENTER);
            passwordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            signInFormPanel.add(passwordPanel);
            signInFormPanel.add(Box.createVerticalStrut(20));

            errorLabel = new JLabel("Username or Password failed");
            errorLabel.setForeground(Color.RED);
            errorLabel.setFont(new Font("Playfair Display", Font.PLAIN, 14));
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            errorLabel.setVisible(false);
            signInFormPanel.add(errorLabel);
            signInFormPanel.add(Box.createVerticalStrut(10));
            createSignInBtn(frame);
            signInFormPanel.add(signInButton);
            signInFormPanel.add(Box.createVerticalStrut(10));
            createRegisterBtn(frame);
            signInFormPanel.add(goToRegisterButton);
            signInPanel.add(signInFormPanel, new GridBagConstraints());
        }

        private void createSignInBtn(JFrame frame) {
            signInButton = new RoundedButton("Login");
            signInButton.setFont(new Font("Playfair Display", Font.BOLD, 18));
            signInButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            signInButton.addMouseListener(new SignInListener(frame, usernameLabel, usernameField, passwordLabel,
                    passwordField, signInButton));
            signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        private void createRegisterBtn(JFrame frame) {
            goToRegisterButton = new RoundedButton("Go to Register");
            goToRegisterButton.setFont(new Font("Playfair Display", Font.BOLD, 18));
            goToRegisterButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            goToRegisterButton.addMouseListener(new RegisterBtnListener(frame, goToRegisterButton));
            goToRegisterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        private class RegisterBtnListener extends MouseAdapter {
            private JFrame frame;
            private JButton goToRegisterButton;

            RegisterBtnListener(JFrame frame, JButton goToRegisterButton) {
                this.frame = frame;
                this.goToRegisterButton = goToRegisterButton;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterScreen registerScreen = new RegisterScreen(frame);
                frame.setContentPane(registerScreen.getRegisterPanel());
                frame.revalidate();
                frame.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                goToRegisterButton.setBackground(new Color(27, 58, 52));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                goToRegisterButton.setBackground(new Color(194, 159, 97));
            }
        }

        private class SignInListener extends MouseAdapter {
            private JFrame frame;
            private JLabel usernameLabel;
            private JTextField usernameField;
            private JLabel passwordLabel;
            private JPasswordField passwordField;
            private JButton signInButton;

            public SignInListener(JFrame frame, JLabel usernameLabel, JTextField usernameField, JLabel passwordLabel,
                                  JPasswordField passwordField, JButton signInButton) {
                this.frame = frame;
                this.usernameLabel = usernameLabel;
                this.usernameField = usernameField;
                this.passwordLabel = passwordLabel;
                this.passwordField = passwordField;
                this.signInButton = SignInScreen.this.signInButton;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (username.isEmpty()) {
                    passwordLabel.setForeground(new Color(74, 78, 105));
                    passwordLabel.setText("Password:");
                    usernameLabel.setForeground(Color.RED);
                    usernameLabel.setText("You must enter a username:");
                } else if (password.isEmpty()) {
                    usernameLabel.setForeground(new Color(74, 78, 105));
                    usernameLabel.setText("Username:");
                    passwordLabel.setForeground(Color.RED);
                    passwordLabel.setText("You must enter a password:");
                } else {
                    UserDAO dao = new UserDAO();
                    boolean success = dao.loginUser(username, password);
                    if (success) {
                        User user = dao.getUserByUsername(username);
                        if (user != null) {
                            GameScreen gameScreen = new GameScreen(frame, user);
                            frame.setContentPane(gameScreen.getGamePanel());
                            frame.revalidate();
                            frame.repaint();
                        } else {
                            errorLabel.setText("Error loading user data.");
                            errorLabel.setVisible(true);
                        }
                    } else {
                        errorLabel.setText("Username or password incorrect.");
                        errorLabel.setVisible(true);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                signInButton.setBackground(new Color(27, 58, 52));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signInButton.setBackground(new Color(194, 159, 97));
            }
        }

        private void createLabelStyle(JLabel label) {
            label.setFont(new Font("Playfair Display", Font.PLAIN, 14));
            label.setForeground(new Color(74, 78, 105));
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        }

        public JPanel getSignInPanel() {
            return signInPanel;
        }
    }