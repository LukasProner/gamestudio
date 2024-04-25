package sk.tuke.gamestudio.service;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceJDBCTest {
    private static final String TEST_USER_LOGIN = "testUser";
    private static final String TEST_USER_PASSWORD = "testPassword";

    private UserService userService;

    @Test
    public void testAddAndGetPlayer() {
        userService = new UserServiceJDBC();

        // Add test user
        userService.addPlayer(new Player(TEST_USER_LOGIN, TEST_USER_PASSWORD));

        // Get the user from the database
        Player retrievedPlayer = userService.getPlayer(TEST_USER_LOGIN);

        // Check if the retrieved player is not null
        assertNotNull(retrievedPlayer);

        // Check if the login matches
        assertEquals(TEST_USER_LOGIN, retrievedPlayer.getLogin());

        // Check if the password matches
        assertEquals(TEST_USER_PASSWORD, retrievedPlayer.getPassword());
    }
}
