package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameScreen {
    private JPanel gamePanel;
    private Timer startTimer;
    private int counter = 3;
    private Timer gametimer;
    private JLabel gameTimerlbl;
    private Timer carTimer;
    private JLabel startLabel;
    private JLabel wordToTypeLabel;
    private JTextField wordTypeFiled;
    private String[] easyWords = {"sol", "pan", "luz", "voz", "mar", "pez", "día", "rey", "té", "mil",
            "uno", "dos", "tres", "flor", "agua", "gato", "mesa", "casa", "nube", "vino",
            "rojo", "paz", "dulce", "lento", "libro", "perro", "camino", "verde", "blanco", "negro"};
    private String[] mediumWords = {"espejo", "ratón", "cielo", "camisa", "amable", "escuela", "botella", "ciudad", "zapato", "amigo",
            "pelota", "puerta", "ventana", "parque", "alegre", "correr", "pintar", "viajar", "luchar", "bañera",
            "reloj", "abuelo", "castillo", "estudio", "trabajo", "madera", "mochila", "jardín", "invierno", "otoño"};
    private String[] hardWords = {"anfitrión", "murciélago", "paralelepípedo", "otorrinolaringólogo", "esternocleidomastoideo",
            "electroencefalograma", "desencriptar", "circunferencia", "metamorfosis", "irreversible",
            "intransigente", "constelación", "efervescente", "estigmatizar", "subconsciente",
            "inconmensurable", "impredecible", "caracterización", "transdisciplinar", "concatenación",
            "desproporcionadamente", "hipopotomonstrosesquipedaliofobia", "infraestructura", "empalabrarse", "tecnológico",
            "científico", "transporte", "transformación", "neuroplasticidad", "pseudoaleatorio"};

    public GameScreen(JFrame frame) {
        gamePanel = new BackgroundPanel("src/public/boxStopHero-background.jpg");
        gamePanel.setLayout(new BorderLayout());

        hacerCunetaAtras();
        iniciadorJuego();
        bucelJuego();
        gamePanel.add(startLabel, BorderLayout.CENTER);

    }

    private void hacerCunetaAtras() {
        startLabel = new JLabel ("", SwingConstants.CENTER);
        startLabel.setFont(new Font("Playfair Display", Font.BOLD, 50));
        startTimer = new Timer(1000 , new StartTimerLister(startLabel));
        startTimer.start();
        startLabel.setForeground(new Color(139, 30, 63));
    }

    private class StartTimerLister implements ActionListener {
        private JLabel startLabel;

        public StartTimerLister(JLabel startLabel) {
            this.startLabel = startLabel;
        }

        public void actionPerformed(ActionEvent e) {
            if (counter > 0) {
                this.startLabel.setText(String.valueOf(counter));
            } else if (counter == 0) {
                this.startLabel.setText("GOOO!!");
                this.startLabel.setForeground(new Color(27, 58, 52));
            } else {
                startTimer.stop();
                this.startLabel.setVisible(false);
            }
            counter--;
        }
    }

    private void iniciadorJuego() {
        wordToTypeLabel = new JLabel("", SwingConstants.CENTER);
        wordToTypeLabel.setFont(new Font("Playfair Display", Font.BOLD, 50));
        wordTypeFiled = new JTextField();
        wordTypeFiled.setFocusable(true);
        wordTypeFiled.setEditable(true);
        gamePanel.add(wordToTypeLabel, BorderLayout.NORTH);
        gamePanel.add(wordTypeFiled, BorderLayout.CENTER);
        gameTimerlbl = new JLabel("", SwingConstants.CENTER);
        gamePanel.add(gameTimerlbl, BorderLayout.SOUTH);
        gametimer = new Timer(1000 , new GameTimerListener());
        gametimer.start();
    }

    private class GameTimerListener implements ActionListener {
        private JLabel gameTimerLbl;

        public GameTimerListener(JLabel gameTimerLbl) {
            this.gameTimerLbl = gameTimerLbl;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.gameTimerLbl.setText(String.valueOf(counter));
        }
    }

    private void bucelJuego() {
        int wordIndex = 0;
        int writedWords = 0;
        while (writedWords <= 10) {
            wordIndex = generateRandomIndex(wordIndex);
            if (writedWords > 4)
                this.wordToTypeLabel.setText(easyWords[wordIndex]);
            if (writedWords > 4 && writedWords < 8)
                this.wordToTypeLabel.setText(hardWords[wordIndex]);
            if (writedWords > 8 && writedWords <= 10)
                this.wordToTypeLabel.setText(easyWords[wordIndex]);
            writedWords++;
        }
        gametimer.stop();
    }

    private int generateRandomIndex(int writedWords) {
        Random rand = new Random();
        int randIndex = 0;
        if (writedWords > 4)
            randIndex = rand.nextInt(easyWords.length);
        if (writedWords > 4 && writedWords < 8)
            randIndex = rand.nextInt(mediumWords.length);
        if (writedWords > 8 && writedWords <= 10)
            randIndex = rand.nextInt(hardWords.length);
        return randIndex;
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            this.backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}