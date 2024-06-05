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
import sk.tuke.gamestudio.game.numberlink.core.*;
import sk.tuke.gamestudio.game.numberlink.core.Number;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Stack;

@Controller
@RequestMapping("/numberlink")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class NumberlinkController {
    private int num_of_hints = 2;

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
    private int num_of_rows_to_connect = 1;

    //pridane 123
    private Stack<int[]> clickedTiles = new Stack<>();

    @ModelAttribute("averageRating")
    public int getAverageRating() {
        return ratingService.getAverageRating("numberlink");
    }

    @RequestMapping
    public String numberlink(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model) {
        num_of_rows_to_connect = 1;
        double averageRating = ratingService.getAverageRating("numberlink");
        model.addAttribute("averageRating", averageRating);

        if (userController.isLogged()) {
            int playerRating = ratingService.getRating("numberlink", userController.getLoggedUser().getLogin());
            model.addAttribute("playerRating", playerRating);
        }

        List<Comment> comments = commentService.getComments("numberlink");
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        System.out.println(getConnectedNumbers() + '*');
        model.addAttribute("isSolved", false);
        model.addAttribute("numofhints",num_of_hints);
        if (row != null && column != null && field.getState()!=GameState.SOLVED) {
            field.markTile(row, column);
            //123
            if(field.getPrevTile(row,column)!= null && field.getTile(row,column).getClass() == Line.class) {
                clickedTiles.push(new int[]{row, column});
            }
        }
        if(field.getState() == GameState.SOLVED){
            model.addAttribute("isSolved", true);
        }
        if (field.getState() == GameState.SOLVED && userController.isLogged() && !scoreAdded) {
            scoreAdded = true;
         //   String sizeofField = field.getRowCount()  + "*" + field.getRowCount();
//            scoreService.addScore(new Score("numberlink",userController.getLoggedUser().getLogin(),field.getScore(),new Date(),sizeofField));
            scoreService.addScore(new Score("numberlink",userController.getLoggedUser().getLogin(),field.getScore(),new Date()));
            System.out.println("saved");
            model.addAttribute("isSolved", true);
        } else {
           // model.addAttribute("isSolved", false);
        }
        model.addAttribute("connectedNumbers",getConnectedNumbers());
        model.addAttribute("scores",scoreService.getTopScores("numberlink"));
        return "numberlink";
    }
    @RequestMapping("/zolik")
    public String zolik(Model model){
        if(num_of_rows_to_connect>0) {
            field.connectNumbersInField();
            num_of_rows_to_connect--;
        }
        double averageRating = ratingService.getAverageRating("numberlink");
        List<Comment> comments = commentService.getComments("numberlink");
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        model.addAttribute("scores",scoreService.getTopScores("numberlink"));
        model.addAttribute("numofhints",num_of_hints);
        return "numberlink";
    }


//123
    @RequestMapping("/undo")
    public String undo(Model model){
        if(!clickedTiles.isEmpty()){
            int[] lastclick = clickedTiles.pop();
            int[] prevtile = field.getPrevTile(lastclick[0],lastclick[1]);
            field.unMarkTile(lastclick[0],lastclick[1]);
        }
        double averageRating = ratingService.getAverageRating("numberlink");
        List<Comment> comments = commentService.getComments("numberlink");
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        model.addAttribute("scores",scoreService.getTopScores("numberlink"));
        model.addAttribute("numofhints",num_of_hints);
        return "numberlink";
    }

    @RequestMapping("/hint")
    public String hint(Model model){
        if(num_of_hints>0) {
            field.chooseHint();
            num_of_hints--;
        }
        double averageRating = ratingService.getAverageRating("numberlink");
        List<Comment> comments = commentService.getComments("numberlink");
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        model.addAttribute("scores", scoreService.getTopScores("numberlink"));
        model.addAttribute("numofhints",num_of_hints);
        return "numberlink";
    }

    @RequestMapping("/new")
    public String newGame(Model model){
        num_of_rows_to_connect = 1;
        scoreAdded = false;
        field = new Field(5,5);
        clickedTiles.clear();
        num_of_hints = 2;
        List<Comment> comments = commentService.getComments("numberlink");
        List<Score> scores = scoreService.getTopScores("numberlink");
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        model.addAttribute("scores", scores);
        model.addAttribute("numofhints",num_of_hints);

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
    public String getConnectedNumbers() {
        StringBuilder connectedNumbers = new StringBuilder();

        for (int row = 0; row < field.getRowCount(); row++) {
            for (int column = 0; column < field.getColumnCount(); column++) {
                Tile tile = field.getTile(row, column);
                if (tile instanceof Number && ((Number) tile).getIsFirst()) {
                    int valueOfNumber = tile.getColor().ordinal();
                    if (field.checkConnection(valueOfNumber)) {
                        connectedNumbers.append(valueOfNumber).append(" ");
                        System.out.println(valueOfNumber + "*********************");
                    }
                }
            }
        }

        return connectedNumbers.toString();
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
        num_of_rows_to_connect = 1;
        if (rows != null ) {
            field = new Field(rows,rows);
        } else {
            field = new Field(3, 3);
        }
        List<Comment> comments = commentService.getComments("numberlink");
        List<Score> scores = scoreService.getTopScores("numberlink");
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        model.addAttribute("scores", scores);
        return "numberlink";
    }
    @RequestMapping("/removeConnection")
    public String removeConnection(@RequestParam(required = false) Integer number, Model model) {
        if (number != null && number<= field.getMaxNumberOfMap(field.getRowCount()) && field.findFirstOfNumber(number)!=null) {
           int [] position = field.findFirstOfNumber(number);
           field.removeContinuedLines(position[0],position[1]);
        }
        List<Comment> comments = commentService.getComments("numberlink");
        List<Score> scores = scoreService.getTopScores("numberlink");
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        model.addAttribute("scores", scores);
        return "numberlink";
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam(required = false) String playerName, @RequestParam("comment") String commentText,Model model) {
        if (!userController.isLogged()) {
            return "redirect:/register";
        }
        Comment comment = new Comment("numberlink", userController.getLoggedUser().getLogin(), commentText, new Date());
        commentService.addComment(comment);
        List<Comment> comments = commentService.getComments("numberlink");
        List<Score> scores = scoreService.getTopScores("numberlink");
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        model.addAttribute("scores", scores);

        return "numberlink";
    }
    @PostMapping("/addRating")
    public String addRating(@RequestParam("rating") int rating,Model model) {
        System.out.println("***********");
        if (!userController.isLogged()) {
            return "redirect:/register";
        }
        Rating rating1 = new Rating("numberlink",userController.getLoggedUser().getLogin(),rating,new Date());
        ratingService.setRating(rating1);
        List<Comment> comments = commentService.getComments("numberlink");
        List<Score> scores = scoreService.getTopScores("numberlink");
        model.addAttribute("field", getHtmlField());
        model.addAttribute("comments", comments);
        model.addAttribute("scores", scores);
        return "numberlink";
    }
}