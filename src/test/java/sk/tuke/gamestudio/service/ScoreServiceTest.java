package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Score;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreServiceTest {
    ScoreService scoreService;

    @Test
    void name() {
        scoreService.getTopScores("numberlink");
    }
}
