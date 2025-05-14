package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu {
    private JPanel mainPanel;
    private JButton startGameBtn;
    private JButton exitBtn;
    private JLabel lblIcon;
    private JLabel lblTitle;

    public MainMenu(JFrame frame) {
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(800, 600));
        mainPanel.setBackground(new Color(11, 20, 28));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        createLabelLogo();
        createLabelTitle();
        createStartGameBtn(frame);
        createExitBtn(frame);
    }

    private void createLabelLogo() {
        lblIcon = new JLabel();
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblIcon.setSize(200, 250);
        ImageIcon imageLogo = new ImageIcon("src/public/logoColorLetrasPNG.png");
        Icon iconLogo = new ImageIcon(imageLogo.getImage().getScaledInstance(lblIcon.getWidth(), lblIcon.getHeight(), Image.SCALE_DEFAULT));
        lblIcon.setIcon(iconLogo);
        mainPanel.add(lblIcon);
    }

    private void createLabelTitle() {
        lblTitle = new JLabel("Box Stop Hero", JLabel.CENTER);
        lblTitle.setFont(new Font("Eurostile Extended", Font.BOLD, 30));
        lblTitle.setForeground(new Color(244, 243, 238));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(lblTitle);
    }

    private void createStartGameBtn(JFrame frame) {
        startGameBtn = new RoundedButton("New Game");
        startGameBtn.setFont(new Font("Playfair Display", Font.BOLD, 20));
        startGameBtn.setPreferredSize(new Dimension(250, 70));
        startGameBtn.setMaximumSize(new Dimension(250, 70));
        startGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameBtn.addMouseListener(new BtnChangeScreenListener(frame, startGameBtn));
        mainPanel.add(Box.createVerticalStrut(80));
        mainPanel.add(startGameBtn);
    }

    private void createExitBtn(JFrame frame) {
        exitBtn = new RoundedButton("Exit Game");
        exitBtn.setFont(new Font("Playfair Display", Font.BOLD, 20));
        exitBtn.setPreferredSize(new Dimension(250, 70));
        exitBtn.setMaximumSize(new Dimension(250, 70));
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.addMouseListener(new BtnExitGameListener(frame, exitBtn));
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(exitBtn);
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
            setFont(new Font("Playfair Display", Font.BOLD, 20));
            setHorizontalAlignment(SwingConstants.CENTER);
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
        public void updateUI() {
        }
    }

    private class BtnExitGameListener extends MouseAdapter {
        private JFrame frame;
        private JButton exitBtn;

        public BtnExitGameListener(JFrame frame, JButton exitBtn) {
            this.frame = frame;
            this.exitBtn = exitBtn;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int confirmado = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to close the window?",
                    "Close window",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (confirmado == JOptionPane.YES_OPTION) {
                frame.dispose();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            this.exitBtn.setBackground(new Color(27, 58, 52));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            this.exitBtn.setBackground(new Color(194, 159, 97));
        }
    }

    private class BtnChangeScreenListener extends MouseAdapter {
        private final JFrame frame;
        private final JButton startGameBtn;

        public BtnChangeScreenListener(JFrame frame, JButton StartGameBtn) {
            this.frame = frame;
            this.startGameBtn = StartGameBtn;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            SignInOrRegister signInOrRegister = new SignInOrRegister(frame);
            frame.setContentPane(signInOrRegister.getSignInOrRegisterPanel());
            frame.revalidate();
            frame.repaint();
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            this.startGameBtn.setBackground(new Color(27, 58, 52));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            this.startGameBtn.setBackground(new Color(194, 159, 97));
        }
    }

    private static class FrameWindowsListener extends WindowAdapter {
        JFrame frame;

        public FrameWindowsListener(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);

            int confirmado = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to close the window?",
                    "Close window",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (confirmado == JOptionPane.YES_OPTION) {
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            } else {
                frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Box Stop Hero - Miquel Avellaneda");
        frame.setContentPane(new MainMenu(frame).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src/images/isotiposinfondo.png");
        frame.setIconImage(image);

        frame.addWindowListener(new FrameWindowsListener(frame));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}