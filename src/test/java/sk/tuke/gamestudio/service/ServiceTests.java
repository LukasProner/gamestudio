package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTests {
    ScoreService scoreService;
    CommentService commentService;
    @BeforeEach
    void setUp() {
        scoreService = new ScoreServiceJDBC();
        commentService = new CommentServiceJDBC();
    }

    @Test
    public void reset() {
        scoreService.reset();
        assertEquals(0, scoreService.getTopScores("numberlink").size());
    }

    @Test
    public void addedScore() {
        scoreService.reset();
        var date = new Date();

        scoreService.addScore(new Score("numberlink", "Jaro", 100, date));

        var scores = scoreService.getTopScores("numberlink");
        assertEquals(1, scores.size());
        assertEquals("numberlink", scores.get(0).getGame());
        assertEquals("Jaro", scores.get(0).getPlayer());
        assertEquals(100, scores.get(0).getPoints());
    }
    @Test
    public void getTopScores() {
        scoreService.reset();
        var date = new Date();
        scoreService.addScore(new Score("numberlink", "Jaro", 100, date));
        scoreService.addScore(new Score("numberlink", "Mirka", 120, date));
        scoreService.addScore(new Score("numberlink", "Churchill", 1965, date));
        scoreService.addScore(new Score("numberlink", "Lincoln", 1865, date));
        scoreService.addScore(new Score("numberlink", "Thatcher", 2013, date));
        var scores = scoreService.getTopScores("numberlink");
        assertEquals(5, scores.size());
        assertEquals("numberlink", scores.get(0).getGame());
        assertEquals("Jaro", scores.get(0).getPlayer());
        assertEquals(100, scores.get(0).getPoints());

        assertEquals("numberlink", scores.get(1).getGame());
        assertEquals("Mirka", scores.get(1).getPlayer());
        assertEquals(120, scores.get(1).getPoints());

        assertEquals("numberlink", scores.get(2).getGame());
        assertEquals("Lincoln", scores.get(2).getPlayer());
        assertEquals(1865, scores.get(2).getPoints());
    }
 // COMENT TESTS
    @Test
    public void resetComment() {
        commentService.reset();
        assertEquals(0, commentService.getComments("numberlink").size());}



    @Test
    public void addedComment() {
        commentService.reset();
        var date = new Date();

        commentService.addComment(new Comment("numberlink", "Banič", "zomrel som 1870", date));

        var comments = commentService.getComments("numberlink");
        assertEquals(1, comments.size());
        assertEquals("numberlink", comments.get(0).getGame());
        assertEquals("Banič", comments.get(0).getPlayer());
        assertEquals("zomrel som 1870", comments.get(0).getComment());
    }


 }



