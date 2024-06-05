package sk.tuke.gamestudio.entity;

import jakarta.persistence.*;

//Entity

@Entity
@NamedQuery(name = "Player.findByLogin",
        query = "SELECT p FROM Player p WHERE p.login = :login")
public class Player {

    @Id
    private String login;

    private String password;

    public Player() {
    }

    public Player(String login, String password) {
        this.login = login;
        this.password = password;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}