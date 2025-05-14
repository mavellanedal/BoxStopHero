package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignInOrRegister {
    private JPanel signInOrRegisterPanel;
    private JLabel titleLbl;
    private JButton signInBtn;
    private JButton registerBtn;


    public SignInOrRegister(JFrame frame) {
        signInOrRegisterPanel = new JPanel();
        signInOrRegisterPanel.setLayout(new GridBagLayout());
        signInOrRegisterPanel.setBackground(new Color(27, 58, 52));

        createTitleLbl();
        createsignInBtn(frame);
        createRegisterBtn(frame);
    }

    private void createTitleLbl() {
        GridBagConstraints titleGBC = createConstraints(0, 0, 0, 40, 0);

        titleLbl = new JLabel("sign In Or Register");
        titleLbl.setForeground(new Color(194, 159, 97));
        titleLbl.setFont(new Font("Playfair Display", Font.BOLD, 32));
        signInOrRegisterPanel.add(titleLbl, titleGBC);
    }

    private void createsignInBtn(JFrame frame) {
        GridBagConstraints signInBtnGBC = createConstraints(1, 0, 0, 10, 0);
        signInBtn = new RoundedButton("Sign In");
        signInBtn.setFont(new Font("Playfair Display", Font.BOLD, 20));
        signInBtn.setPreferredSize(new Dimension(200, 70));
        signInBtn.setMaximumSize(new Dimension(200, 70));
        signInBtn.addMouseListener(new BtnsignInMousListner(frame, signInBtn));
        signInOrRegisterPanel.add(signInBtn, signInBtnGBC);
    }

    private class BtnsignInMousListner extends MouseAdapter {
        private final JFrame frame;
        private JButton signInBtn;

        public BtnsignInMousListner(JFrame frame, JButton signInBtn) {
            this.frame = frame;
            this.signInBtn = signInBtn;
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
            this.signInBtn.setBackground(new Color(11, 20, 28));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            this.signInBtn.setBackground(new Color(194, 159, 97));
        }
    }

    private void createRegisterBtn(JFrame frame) {
        GridBagConstraints registerGBC = createConstraints(2, 0, 0, 10, 0);
        registerBtn = new RoundedButton("Register");
        registerBtn.setFont(new Font("Playfair Display", Font.BOLD, 20));
        registerBtn.setPreferredSize(new Dimension(200, 70));
        registerBtn.setMaximumSize(new Dimension(200, 70));
        registerBtn.addMouseListener(new BtnRegisterMousListner(frame, registerBtn));
        signInOrRegisterPanel.add(registerBtn, registerGBC);
    }

    private class BtnRegisterMousListner extends MouseAdapter {
        private final JFrame frame;
        private JButton registerBtn;

        public BtnRegisterMousListner(JFrame frame, JButton registerBtn) {
            this.frame = frame;
            this.registerBtn = registerBtn;
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
            this.registerBtn.setBackground(new Color(11, 20, 28));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            this.registerBtn.setBackground(new Color(194, 159, 97));
        }
    }

    public JPanel getSignInOrRegisterPanel() {
        return signInOrRegisterPanel;
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

    private GridBagConstraints createConstraints(int y, int top, int left, int bottom, int rigth) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = y;
        constraints.gridwidth = 0;
        constraints.insets = new Insets(top, left, bottom, rigth);
        constraints.anchor = GridBagConstraints.CENTER;
        return constraints;
    }
}
