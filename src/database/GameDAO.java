package database;

import models.Game;
import models.RankingEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    public static int saveGame(Game game) {
        String sql = "INSERT INTO games (user_id, correct_words, wrong_words, total_time) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, game.getUserId());
            stmt.setInt(2, game.getCorrectWords());
            stmt.setInt(3, game.getWrongWords());
            stmt.setString(4, game.getTotalTimeFormatted());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static List<RankingEntry> getTopRankings(int limit) {
        List<RankingEntry> rankings = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "SELECT u.username, g.total_time FROM games g JOIN users u ON g.user_id = u.id ORDER BY g.total_time ASC LIMIT ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String time = rs.getString("total_time");
                rankings.add(new RankingEntry(username, time));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rankings;
    }

    public static RankingEntry getRankingOfGame(int gameId) {
        RankingEntry entry = null;
        String sql = "SELECT u.username, g.total_time " +
                "FROM games g " +
                "JOIN users u ON g.user_id = u.id " +
                "WHERE g.id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                String totalTime = rs.getString("total_time");
                entry = new RankingEntry(username, totalTime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entry;
    }
}