package database;

import models.WrongWord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class GameErrorDAO {
    public static void insertarErrores(int gameId, List<WrongWord> errores) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "INSERT INTO game_errors (game_id, palabra_correcta, palabra_escrita, dificultad) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            for (WrongWord error : errores) {
                stmt.setInt(1, gameId);
                stmt.setString(2, error.getPalabraCorrecta());
                stmt.setString(3, error.getPalabraEscrita());
                stmt.setInt(4, error.getDificultad());
                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace(); // Puedes reemplazarlo con una ventana emergente o logueo
        }
    }
}