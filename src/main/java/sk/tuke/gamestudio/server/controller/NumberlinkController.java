package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
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
    private Field field = new Field(3,3);
    @Autowired
    private CommentService commentService;

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;

    @ModelAttribute("averageRating")
    public double getAverageRating() {
        return ratingService.getAverageRating("numberlink");
    }

    @RequestMapping
    public String numberlink(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model) {
        if (row != null && column != null && field.getState()!=GameState.SOLVED)
            field.markTile(row, column);
        if (field.getState() == GameState.SOLVED && userController.isLogged()) {
            scoreService.addScore(new Score("numberlink",userController.getLoggedUser().getLogin(),field.getScore(),new Date()));
            System.out.println("saved");
            model.addAttribute("isSolved", true);
        } else {
            model.addAttribute("isSolved", false);
        }

        // Přidání průměrného hodnocení do modelu
        double averageRating = ratingService.getAverageRating("numberlink");
        model.addAttribute("averageRating", averageRating);

        List<Comment> comments = commentService.getComments("numberlink");
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        model.addAttribute("scores",scoreService.getTopScores("numberlink"));
        return "numberlink";
    }

    @RequestMapping("/new")
    public String newGame(Model model){
        field = new Field(3,3);
        List<Comment> comments = commentService.getComments("numberlink");
        List<Score> scores = scoreService.getTopScores("numberlink");
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        model.addAttribute("scores", scores);

        return "numberlink";
    }


    public String getHtmlField(){
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < field.getColumnCount(); column++) {
                var tile = field.getTile(row,column);
                System.out.println(tile.getClass() + " clas --------");
                sb.append("<td>\n");
                sb.append("<a href='/numberlink?row=" + row + "&column=" + column + "'>\n");
                sb.append("<img src = '/images/numberlink/" + getImageName(tile) + ".png'>");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("/<table>\n");
        return sb.toString();
    }

    private String getImageName(Tile tile) {
        switch (tile.getColor()) {
            case NULL -> {
                return "svetlofialova";
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
                    return "fialova";
                }
                return "fialova6";
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
            }


            default -> throw new RuntimeException("Neočakávaný stav dlaždice: " + tile.getColor());
        }
    }
    private Field handleSizeOfField(int rows, int columns) {
        return new Field(rows, columns);
    }
    @RequestMapping("/chooseSize")
    public String chooseSize(@RequestParam(required = false) Integer rows, @RequestParam(required = false) Integer columns, Model model) {
        if (rows != null && columns != null) {
            field = new Field(rows,columns);
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
    public String addComment(@RequestParam("playerName") String playerName, @RequestParam("comment") String commentText) {
        System.out.println("9999999999");
        Comment comment = new Comment("numberlink",playerName, commentText,new Date());
        commentService.addComment(comment);
        return "numberlink"; // Presmerovanie na hlavnú stránku po odoslaní komentára
    }
}