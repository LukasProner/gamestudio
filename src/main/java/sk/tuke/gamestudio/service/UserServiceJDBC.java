package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Player;

import java.sql.*;

public class UserServiceJDBC implements UserService{
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String SELECT_BY_LOGIN = "SELECT login, password FROM player WHERE login = ?";
    public static final String INSERT = "INSERT INTO player (login, password) VALUES (?, ?)";

    @Override
    public Player getPlayer(String login) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_LOGIN)
        ) {
            statement.setString(1, login);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String password = rs.getString("password");
                    return new Player(login, password);
                }
                return null; // Player not found
            }
        } catch (SQLException e) {
            throw new RuntimeException("Problem getting player", e);
        }
    }
    @Override
    public void addPlayer(Player player) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, player.getLogin());
            statement.setString(2, player.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Problem adding player", e);
        }
    }

    @Override
    public boolean authenticate(String login, String password) {
        return false;
    }
}
