package database;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class UserDAO {

    public boolean registerUser(String username, String plainPassword) {
        if (username == null || username.isEmpty() || plainPassword == null || plainPassword.isEmpty()) {
            return false; // Validación simple
        }

        if (userExists(username)) return false;

        String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO users (username, password_hash) VALUES (?, ?)"
             )) {
            stmt.setString(1, username);
            stmt.setString(2, hashed);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario en la base de datos");
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        String query = "SELECT password_hash FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    return BCrypt.checkpw(password, storedHash);
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar credenciales");
            e.printStackTrace();
            return false;
        }
    }

    public boolean userExists(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        String query = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al comprobar existencia de usuario");
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String passwordHash = rs.getString("password_hash");
                    Timestamp ts = rs.getTimestamp("created_at");
                    LocalDateTime createdAt = ts != null ? ts.toLocalDateTime() : null;
                    return new User(id, username, passwordHash, createdAt);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el usuario");
            e.printStackTrace();
        }
        return null;
    }
}