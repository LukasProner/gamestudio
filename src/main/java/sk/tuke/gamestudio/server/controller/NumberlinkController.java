package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.numberlink.core.Colors;
import sk.tuke.gamestudio.game.numberlink.core.Field;
import sk.tuke.gamestudio.game.numberlink.core.GameState;
import sk.tuke.gamestudio.game.numberlink.core.Tile;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/numberlink")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class NumberlinkController {
    @Autowired
    private UserController userController;
    private Field field = new Field(5,5);
    @Autowired
    private CommentService commentService;

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    private boolean scoreAdded = false;
    @ModelAttribute("averageRating")
    public int getAverageRating() {
        return ratingService.getAverageRating("numberlink");
    }

    @RequestMapping
    public String numberlink(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model) {
        double averageRating = ratingService.getAverageRating("numberlink");
        model.addAttribute("averageRating", averageRating);

        if (userController.isLogged()) {
            int playerRating = ratingService.getRating("numberlink", userController.getLoggedUser().getLogin());
            model.addAttribute("playerRating", playerRating);
        }

        List<Comment> comments = commentService.getComments("numberlink");
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        model.addAttribute("scores",scoreService.getTopScores("numberlink"));
        if (row != null && column != null && field.getState()!=GameState.SOLVED)
            field.markTile(row, column);
        if (field.getState() == GameState.SOLVED && userController.isLogged() && !scoreAdded) {
            scoreAdded = true;
            scoreService.addScore(new Score("numberlink",userController.getLoggedUser().getLogin(),field.getScore(),new Date()));
            System.out.println("saved");
            model.addAttribute("isSolved", true);
        } else {
            model.addAttribute("isSolved", false);
        }
        return "numberlink";
    }

    @RequestMapping("/new")
    public String newGame(Model model){
        scoreAdded = false;
        field = new Field(5,5);
        List<Comment> comments = commentService.getComments("numberlink");
        List<Score> scores = scoreService.getTopScores("numberlink");
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        model.addAttribute("scores", scores);

        return "numberlink";
    }


    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table id=\"fieldTable\">\n");
        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < field.getColumnCount(); column++) {
                var tile = field.getTile(row, column);
                sb.append("<td>\n");
                sb.append("<a href='/numberlink?row=" + row + "&column=" + column + "'>\n");
                sb.append("<img src = '/images/numberlink/" + getImageName(tile) + ".png'>");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");

        return sb.toString();
    }
    private String getImageName(Tile tile) {
        switch (tile.getColor()) {
            case NULL -> {
                return "ciste";
            }
            case RED -> {
                if(tile.getClass().getName().contains("Line")){
                    return "oranzova";
                }
                return "oranzova1";
            }
            case GREEN -> {
                if(tile.getClass().getName().contains("Line")){
                    return "cervena";
                }
                return "cervena2";

            }
            case YELLOW -> {
                if(tile.getClass().getName().contains("Line")){
                    return "tirkysova";
                }
                return "tirkysova3";
            }
            case ORANGE -> {
                if(tile.getClass().getName().contains("Line")){
                    return "zelena";
                }
                return "zelena4";

            }
            case BLUE -> {
                if(tile.getClass().getName().contains("Line")){
                    return "zlta";
                }
                return "zlta5";
            }
            case WHITE -> {
                if(tile.getClass().getName().contains("Line")){
                    return "fialova";
                }
                return "fialova6";
            }
            case CYAN -> {
                if(tile.getClass().getName().contains("Line")){
                    return "ruzova";
                }
                return "ruzova7";
            }case PURPLE -> {
                if(tile.getClass().getName().contains("Line")){
                    return "hneda";
                }
                return "hneda8";
            }


            default -> throw new RuntimeException("Neočakávaný stav dlaždice: " + tile.getColor());
        }
    }
    private Field handleSizeOfField(int rows, int columns) {
        return new Field(rows, columns);
    }
    @RequestMapping("/chooseSize")
    public String chooseSize(@RequestParam(required = false) Integer rows, Model model) {
        if (rows != null ) {
            field = new Field(rows,rows);
        } else {
            // Default size, if not specified
            field = new Field(3, 3);
        }
        // Add field to the model
        model.addAttribute("field", getHtmlField());
        // Return view
        return "numberlink";
    }
    @PostMapping("/addComment")
    public String addComment(@RequestParam(required = false) String playerName, @RequestParam("comment") String commentText) {
        if (!userController.isLogged()) {
            return "redirect:/register";
        }

        Comment comment = new Comment("numberlink", userController.getLoggedUser().getLogin(), commentText, new Date());
        commentService.addComment(comment);
        return "numberlink";
    }
    @PostMapping("/addRating")
    public String addRating(@RequestParam("rating") int rating) {
        System.out.println("***********");
        if (!userController.isLogged()) {
            return "redirect:/register";
        }
        Rating rating1 = new Rating("numberlink",userController.getLoggedUser().getLogin(),rating,new Date());
        ratingService.setRating(rating1);
        return "numberlink";
    }
}