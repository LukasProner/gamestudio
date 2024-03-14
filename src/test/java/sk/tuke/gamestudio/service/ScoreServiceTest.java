package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoreServiceTest {
    ScoreService scoreService;
    @BeforeEach
    void setUp() {
        // Inicializácia objektu ScoreService, napríklad inštanciou ScoreServiceJDBC
        scoreService = new ScoreServiceJDBC();
    }

    @Test
    void testGetTopScores() {
        System.out.println(scoreService.getTopScores("numberlink"));

    }
}
