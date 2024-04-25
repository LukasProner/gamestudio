package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Player;

public interface UserService {
    Player getPlayer(String login);
    void addPlayer(Player player);

    boolean authenticate(String login, String password);
}
