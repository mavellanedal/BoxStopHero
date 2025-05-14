    package ui;

    import database.UserDAO;

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

            // DEBUUUG
            usernameField.setText("Test04");
            passwordField.setText("Temporal001!");
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
                if (usernameField.getText().isEmpty()) {
                    this.passwordLabel.setForeground(new Color(74, 78, 105));
                    this.passwordLabel.setText("Password:");
                    this.usernameLabel.setForeground(Color.RED);
                    this.usernameLabel.setText("You must enter a username:");
                } else if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    this.usernameLabel.setForeground(new Color(74, 78, 105));
                    this.usernameLabel.setText("Username:");
                    this.passwordLabel.setForeground(Color.RED);
                    this.passwordLabel.setText("You must enter a password:");
                } else {
                    UserDAO dao = new UserDAO();
                    boolean success = dao.loginUser(usernameField.getText(), String.valueOf(
                            passwordField.getPassword()));
                    if (success) {
                        GameScreen gameScreen = new GameScreen(frame);
                        frame.setContentPane(gameScreen.getGamePanel());
                        frame.revalidate();
                        frame.repaint();
                    } else {
                        this.usernameField.setText("");
                        this.passwordField.setText("");
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

        public class RoundedButton extends JButton {
            private final int arcWidth = 30;
            private final int arcHeight = 30;

            public RoundedButton(String text) {
                super(text);
                setContentAreaFilled(false);
                setFocusPainted(false);
                setBorderPainted(false);
                setForeground(Color.WHITE);
                setBackground(new Color(194, 159, 97));
                setFont(new Font("Playfair Display", Font.BOLD, 18));
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 4;

                g2.setColor(getForeground());
                g2.setFont(getFont());
                g2.drawString(getText(), x, y);

                g2.dispose();
            }

            @Override
            public void updateUI() {}
        }
    }