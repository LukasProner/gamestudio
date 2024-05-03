package sk.tuke.gamestudio.service;



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Player;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Transactional
public class UserServiceJPA implements UserService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Player getPlayer(String login) {
        return entityManager.find(Player.class, login);
    }

    @Override
    public void addPlayer(Player player) {
        // Získajte heslo hráča
        String plainPassword = player.getPassword();

        // Hashujte heslo pomocou BCrypt a nastavte hashované heslo hráčovi
        String hashedPassword = passwordEncoder.encode(plainPassword);
        player.setPassword(hashedPassword);

        // Uložte hráča do databázy
        entityManager.persist(player);
    }
    @Override
    public boolean authenticate(String login, String password) {
        Player player = getPlayer(login);
        return player != null && passwordEncoder.matches(password, player.getPassword());
    }
}
