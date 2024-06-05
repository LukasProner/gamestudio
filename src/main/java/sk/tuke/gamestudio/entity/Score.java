package sk.tuke.gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.util.Date;
@Entity
@NamedQuery( name = "Score.getTopScores",
        query = "SELECT s FROM Score s WHERE s.game=:game ORDER BY s.points ASC")
@NamedQuery( name = "Score.resetScores",
        query = "DELETE FROM Score")

public class Score implements Serializable {
    @Id
    @GeneratedValue
    private int ident;
    private String game;

    private String player;

    private int points;

    private Date playedOn;
    //private String size;

    public Score() {
    }

    public Score(String game, String player, int points, Date playedOn/*,String size*/) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedOn = playedOn;
      //  this.size = size;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public String getGame() {
        return game;
    }

   /* public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }*/

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", playedOn=" + playedOn +
                '}';
    }

}
