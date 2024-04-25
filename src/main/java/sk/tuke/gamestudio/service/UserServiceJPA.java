package sk.tuke.gamestudio.service;



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Player;

@Transactional
public class UserServiceJPA implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Player getPlayer(String login) {
        return entityManager.find(Player.class, login);
    }

    @Override
    public void addPlayer(Player player) {
        entityManager.persist(player);
    }
    @Override
    public boolean authenticate(String login, String password) {
        Player player = getPlayer(login);
        return player != null && player.getPassword().equals(password);
    }
}
