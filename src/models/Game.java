package models;

import java.sql.Time;

public class Game {
    private int userId;
    private int correctWords;
    private int wrongWords;
    private int elapsedTime;

    public Game(int userId, int correctWords, int wrongWords, int elapsedTime) {
        this.userId = userId;
        this.correctWords = correctWords;
        this.wrongWords = wrongWords;
        this.elapsedTime = elapsedTime;
    }

    public String getTotalTimeFormatted() {
        int totalCentiseconds = elapsedTime / 10;
        int minutes = totalCentiseconds / 6000;
        int seconds = (totalCentiseconds / 100) % 60;
        int centiseconds = totalCentiseconds % 100;
        return String.format("%02d:%02d:%02d", minutes, seconds, centiseconds);
    }

    public int getUserId() { return userId; }
    public int getCorrectWords() { return correctWords; }
    public int getWrongWords() { return wrongWords; }
}
