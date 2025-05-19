package models;

import java.sql.Time;

public class RankingEntry {
    private String username;
    private String totalTime;

    public RankingEntry(String username, String totalTime) {
        this.username = username;
        this.totalTime = totalTime;
    }

    public String getUsername() {
        return username;
    }

    public String getTotalTime() {
        return totalTime;
    }
}