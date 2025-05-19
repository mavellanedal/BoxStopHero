package ui;

import database.GameErrorDAO;
import models.Game;
import models.User;
import models.WrongWord;
import database.GameDAO;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.*;

public class GameScreen {
    private User currentUser;
    private JPanel gamePanel;
    private Timer startTimer;
    private int counter = 3;
    private Timer gametimer;
    private int elapsedTime = 0;
    private final int TIMER_DELAY = 10;
    private JLabel gameTimerlbl;
    private JLabel startLabel;
    private JLabel wordToTypeLabel;
    private JTextField wordTypeField;
    private int writedWords = 0;
    private int correctWords = 0;
    private int wrongWords = 0;
    private String currentWord = "";
    private ArrayList<String> easyWords = new ArrayList<>(Arrays.asList("sol", "pan", "luz", "voz", "mar", "pez", "día", "rey", "té", "mil",
            "uno", "dos", "tres", "flor", "agua", "gato", "mesa", "casa", "nube", "vino",
            "rojo", "paz", "dulce", "lento", "libro", "perro", "camino", "verde", "blanco", "negro"));
    private ArrayList<String> mediumWords = new ArrayList<>(Arrays.asList(
            "espejo", "ratón", "cielo", "camisa", "amable", "escuela", "botella", "ciudad", "zapato", "amigo",
            "pelota", "puerta", "ventana", "parque", "alegre", "correr", "pintar", "viajar", "luchar", "bañera",
            "reloj", "abuelo", "castillo", "estudio", "trabajo", "madera", "mochila", "jardín", "invierno", "otoño"
    ));
    private ArrayList<String> hardWords = new ArrayList<>(Arrays.asList(
            "anfitrión", "murciélago", "paralelepípedo", "otorrinolaringólogo", "esternocleidomastoideo",
            "electroencefalograma", "desencriptar", "circunferencia", "metamorfosis", "irreversible",
            "intransigente", "constelación", "efervescente", "estigmatizar", "subconsciente",
            "inconmensurable", "impredecible", "caracterización", "transdisciplinar", "concatenación",
            "desproporcionadamente", "hipopotomonstrosesquipedaliofobia", "infraestructura", "empalabrarse", "tecnológico",
            "científico", "transporte", "transformación", "neuroplasticidad", "pseudoaleatorio"
    ));

    public GameScreen(JFrame frame, User user) {
        this.currentUser = user;
        this.gamePanel = new BackgroundPanel("src/public/boxStopHero-background.jpg");
        this.gamePanel.setLayout(new BorderLayout());

        this.wordToTypeLabel = new JLabel("", SwingConstants.CENTER);
        this.wordToTypeLabel.setFont(new Font("Playfair Display", Font.BOLD, 30));
        this.wordToTypeLabel.setForeground(Color.WHITE);
        this.wordToTypeLabel.setVisible(false);

        // Timer arriba
        this.gameTimerlbl = new JLabel("", SwingConstants.CENTER);
        this.gameTimerlbl.setFont(new Font("Playfair Display", Font.BOLD, 20));
        this.gameTimerlbl.setForeground(Color.WHITE);
        this.gamePanel.add(gameTimerlbl, BorderLayout.NORTH);

        configurarCentro();

        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));

        wordToTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        wordTypeField.setMaximumSize(new Dimension(300, 30));
        wordToTypeLabel.setForeground(new Color(74, 78, 105));
        wordTypeField.setAlignmentX(Component.CENTER_ALIGNMENT);
        wordTypeField.addKeyListener(new GameCheckerListener(frame));

        southPanel.add(wordToTypeLabel);
        southPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        southPanel.add(wordTypeField);

        this.gamePanel.add(southPanel, BorderLayout.SOUTH);

        iniciadorJuego();
        hacerCuentaAtras();
        gametimer.stop();
    }

    private void configurarCentro() {
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        this.startLabel = new JLabel("", SwingConstants.CENTER);
        this.startLabel.setFont(new Font("Playfair Display", Font.BOLD, 50));
        this.startLabel.setForeground(new Color(139, 30, 63));
        this.startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(startLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        this.wordTypeField = new JTextField();
        this.wordTypeField.setMaximumSize(new Dimension(300, 30));
        this.wordTypeField.setFont(new Font("Playfair Display", Font.PLAIN, 20));
        this.wordTypeField.setEditable(false);
        this.wordTypeField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.wordTypeField.setVisible(false);

        // Envolver en un GridBagLayout para centrar todo el bloque
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(centerPanel);

        this.gamePanel.add(centerWrapper, BorderLayout.CENTER);
    }

    private void hacerCuentaAtras() {
        this.startLabel.setFont(new Font("Playfair Display", Font.BOLD, 50));
        this.startLabel.setForeground(new Color(139, 30, 63));
        this.startTimer = new Timer(1000 , new StartTimerLister(startLabel, wordToTypeLabel, gameTimerlbl, wordTypeField, gametimer));
        this.startTimer.start();
    }

    private class StartTimerLister implements ActionListener {
        private JLabel startLabel;
        private JLabel wordToTypeLabel;
        private JLabel gameTimerLbl;
        private JTextField wordTypeFiled;
        private Timer gameTimer;

        public StartTimerLister(JLabel startLabel, JLabel wordToTypeLabel, JLabel gameTimerLbl,
                                JTextField wordTypeFiled, Timer gameTimer) {
            this.startLabel = startLabel;
            this.wordToTypeLabel = wordToTypeLabel;
            this.gameTimerLbl = gameTimerLbl;
            this.wordTypeFiled = wordTypeFiled;
            this.gameTimer = gameTimer;
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
                this.wordToTypeLabel.setVisible(true);
                this.wordTypeFiled.setVisible(true);
                this.wordTypeFiled.setEditable(true);
                this.gameTimerLbl.setVisible(true);
                showNewWord();
                wordToTypeLabel.setText(currentWord);
                this.gameTimer.start();
            }
            counter--;
        }
    }

    private void iniciadorJuego() {
        this.wordToTypeLabel.setVisible(false);
        this.wordTypeField.setFocusable(true);
        this.wordTypeField.setEditable(false);
        this.gametimer = new Timer(TIMER_DELAY, new GameTimerListener(gameTimerlbl));
        this.elapsedTime = 0;
    }

    private class GameTimerListener implements ActionListener {
        private JLabel gameTimerLbl;

        public GameTimerListener(JLabel gameTimerLbl) {
            this.gameTimerLbl = gameTimerLbl;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            elapsedTime += TIMER_DELAY;

            int centiseconds = (elapsedTime / 10) % 100;
            int seconds = (elapsedTime / 1000) % 60;
            int minutes = elapsedTime / 60000;

            String formattedTime = String.format("%02d:%02d:%02d", minutes, seconds, centiseconds);
            gameTimerLbl.setText(formattedTime);
        }
    }

    private class GameCheckerListener extends KeyAdapter {
        private ArrayList<WrongWord> palabrasFalladas = new ArrayList<>();
        private JFrame frame;

        public GameCheckerListener(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            String wordTyped = wordTypeField.getText().trim();
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if(wordTyped.equals(currentWord)) {
                    wordToTypeLabel.setForeground(new Color(27, 58, 52));
                    correctWords++;
                } else {
                    int difficulty = 0;
                    if (hardWords.contains(currentWord))
                        difficulty = 3;
                    else if (mediumWords.contains(currentWord))
                        difficulty = 2;
                    else if (easyWords.contains(currentWord))
                        difficulty = 1;
                    wordToTypeLabel.setForeground(new Color(139, 30, 63));
                    WrongWord fallo = new WrongWord(currentWord, difficulty, wordTyped);
                    palabrasFalladas.add(fallo);
                }
                writedWords++;
                wordTypeField.setEditable(false);
                new Timer(500, evt -> {
                    if (writedWords >= 3) {
                        int centiseconds = (elapsedTime / 10) % 100;
                        int seconds = (elapsedTime / 1000) % 60;
                        int minutes = elapsedTime / 60000;
                        String formattedTime = String.format("%02d:%02d:%02d", minutes, seconds, centiseconds);
                        gametimer.stop();
                        startLabel.setVisible(true);
                        startLabel.setForeground(new Color(27, 58, 52));
                        wordToTypeLabel.setVisible(false);
                        wordTypeField.setVisible(false);
                        startLabel.setText("GAME ENDED!!");
                        Game partida = new Game(
                                currentUser.getId(),
                                correctWords,
                                writedWords - correctWords,
                                elapsedTime
                        );
                        GameDAO gameDAO = new GameDAO();
                        int game_id = gameDAO.saveGame(partida);
                        GameErrorDAO.insertarErrores(game_id, palabrasFalladas);
                        new Timer(3000, e2 -> {
                            EndGameScreen endGameScreen = new EndGameScreen(frame, game_id);
                            frame.setContentPane(endGameScreen.getEndGamePanel());
                            frame.revalidate();
                            frame.repaint();
                            ((Timer) e2.getSource()).stop();
                        }).start();

                    } else {
                        wordToTypeLabel.setForeground(new Color(74, 78, 105));
                        showNewWord();
                        wordToTypeLabel.setText(currentWord);
                        wordTypeField.setText("");
                        wordTypeField.setEditable(true);
                    }
                    ((Timer) evt.getSource()).stop();
                }).start();
            }
        }
    }

    private void showNewWord() {
        Random rand = new Random();
        if (writedWords < 4) {
            int easyWordIndex = rand.nextInt(easyWords.size());
            currentWord = easyWords.get(easyWordIndex);
            easyWords.remove(easyWordIndex);
        } else if (writedWords < 8) {
            int mediumWordIndex = rand.nextInt(mediumWords.size());
            currentWord = mediumWords.get(mediumWordIndex);
            mediumWords.remove(mediumWordIndex);
        } else {
            int hardWordIndex = rand.nextInt(hardWords.size());
            currentWord = hardWords.get(hardWordIndex);
            hardWords.remove(hardWordIndex);
        }
        wordToTypeLabel.setText(currentWord);
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