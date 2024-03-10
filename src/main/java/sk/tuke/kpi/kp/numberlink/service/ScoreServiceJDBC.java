package sk.tuke.kpi.kp.numberlink.service;

import sk.tuke.kpi.kp.numberlink.entity.Score;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService{
    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String DELETE = "DELETE FROM score";

    public static final String INSERT = "INSERT INTO score ( game, player, points, playedOn) VALUES (?, ?, ?, ?)";
    public static final String SELECT = "SELECT game,player,points,playedOn from score where game = ? order by points desc limit 10";


    @Override
    public void addScore(Score score) {
        try (var connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             var statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, score.getPlayer());
            statement.setString(2, score.getGame());
            statement.setInt(3, score.getPoints());
            statement.setTimestamp(4, new Timestamp(score.getPlayedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException("Problem inserting score", e);
        }
    }

    @Override
    public List<Score> getTopScores(String game) {
        try(var connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            var statement = connection.prepareStatement(SELECT);
            //var rs = statement.executeQuery(SELECT);
        ){
            statement.setString(1, game);
            try(var rs = statement.executeQuery()) {
                List<Score> scores = new ArrayList<>();
                while (rs.next()) {
                    scores.add(new Score(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                }
                return scores;
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem selecting score", e);
        }
    }


    @Override
    public void reset() {
        try(var connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            var statement = connection.createStatement();
        ){
            statement.executeUpdate(DELETE);
        }catch (SQLException e) {
            throw new ScoreException("Problem deleting score", e);
        }
    }
}
