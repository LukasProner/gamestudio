package sk.tuke.gamestudio.game.numberlink.consoleui;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.numberlink.core.Colors;
import sk.tuke.gamestudio.game.numberlink.core.GameState;
import sk.tuke.gamestudio.game.numberlink.core.Field;
import sk.tuke.gamestudio.game.numberlink.core.TimerOfGame;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.CommentServiceJDBC;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.regex.Pattern;

public class ConsoleUI {
    private static final Pattern INPUT_PATTERN_FOR_MOVE = Pattern.compile("([A-Z])([1-9][0-9]*)");
    private static final Pattern INPUT_PATTERN_FOR_FIELD_SIZE = Pattern.compile("([1-9][0-9]*)[*]([1-9][0-9]*)");
    private Field field;
    private Scanner scanner = new Scanner(System.in);
    private Timer timer;
    private TimerOfGame timerOfGame;
    private ScoreService scoreService = new ScoreServiceJDBC();
    private CommentService commentService = new CommentServiceJDBC();

    public ConsoleUI() {

    }

    public void play() {
        while (field == null) {
            field = handleSizeOfField();
        }
        this.timerOfGame = new TimerOfGame(field);
        this.timer = new Timer();
        timer.schedule(timerOfGame, 0, 1000);
        while (field != null/* && field.getState() != GameState.SOLVED*/) {
            show();
            System.out.println("time: " + timerOfGame.getTime());
            handleInput();
            if (field.getState() == GameState.SOLVED) {
                show();
                timer.cancel();
                scoreService.addScore( new Score("numberlink",System.getProperty("user.name"), timerOfGame.getTime()*100/field.getColumnCount(),new Date()));
                System.out.println("Solved!");
                wannaAddComment();
                break;
            }
        }
        if (wannaPlayAgain()) {
            field = null;
            play();
        }
    }

    private void wannaAddComment() {
        System.out.print("Do you want to add some comment?(A/N): ");
        var sizeInput = scanner.nextLine().toUpperCase();
        if ("A".equals(sizeInput)) {
            System.out.println(" Comment :");
            String comment = scanner.nextLine();
            commentService.addComment(new Comment("numberlink",System.getProperty("user.name"), comment,new Date()));
        }
    }

    private boolean wannaPlayAgain() {
        System.out.println("Gratulujeme, vyhrali ste!");
        System.out.println("Your time was: " + timerOfGame.getTime() + "seconds and your score was: " + timerOfGame.getTime()*100/field.getColumnCount() );
        System.out.println("Prajete si začatie novej hry (A/N)? _");
        var input = scanner.nextLine().toUpperCase();
        if ("X".equals(input) || "N".equals(input)) {
            System.out.println("Have a nice day");
            System.exit(0);
        } else if ("A".equals(input)) {
            return true;
        }
        return false;
    }

    private Field handleSizeOfField() {
        printScore();
        System.out.print("Enter size of field (X - exit, 5*5 - size of field): ");
        var sizeInput = scanner.nextLine().toUpperCase();
        if ("X".equals(sizeInput)) {
            System.out.println("Have a nice day");
            timer.cancel();
            System.exit(0);
        }
        var sizeMatcher = INPUT_PATTERN_FOR_FIELD_SIZE.matcher(sizeInput);
        if (sizeMatcher.matches()) {
            int rows = Integer.parseInt(sizeMatcher.group(1));
            int columns = Integer.parseInt(sizeMatcher.group(2));
            if (rows != columns) {
                System.out.println("the size has to be square");
                return null;
            }
            return new Field(rows, columns);
        } else {
            System.out.println("Invalid input for field size.");
            return null;
        }
    }

    private void handleInput() {
        System.out.print("Enter command (X - exit,P - pause, A0 - mark tile): ");
        var line = scanner.nextLine().toUpperCase();
        if ("X".equals(line)) {
            timer.cancel();
            System.out.println("  Have a nice day");
            System.exit(0);
        }
        if("P".equals(line)){
           field.setState(GameState.PAUSED);
            System.out.println("PAUSED AT : " + timerOfGame.getTime());
        }
        var matcher = INPUT_PATTERN_FOR_MOVE.matcher(line);
        if (matcher.matches()) {
            System.out.println("state = " +field.getState());
            field.setState(GameState.PLAYING);
            System.out.println(matcher.group(1) + " " + matcher.group(2));
            int row = matcher.group(1).charAt(0) - 'A';
            int column = Integer.parseInt(matcher.group(2)) - 1;
            field.markTile(row, column);
            System.out.println("field state = " + field.getState());
        } else {
            System.out.println("  wrong input");
        }
    }

    public void show() {
        printFirstLine();
        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print((char) ('A' + row) + "|");
            for (int column = 0; column < field.getColumnCount(); column++) {
                if (field.getTile(row, column).getColor() == Colors.NULL) {
                    System.out.print("   _");
                } else {
                    printColoredNumber(row, column);
                }
            }
            System.out.println();
        }
        System.out.print("Already connected pairs: ");
        field.connectedNumbers();
        System.out.println();
    }

    private void printColoredNumber(int row, int column) {
        System.out.print("  ");
        switch (field.getTile(row, column).getColor()) {
            case RED -> System.out.printf("\u001B[31m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
            case GREEN -> System.out.printf("\u001B[32m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
            case YELLOW -> System.out.printf("\u001B[33m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
            case BLUE -> System.out.printf("\u001B[34m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
            case PURPLE -> System.out.printf("\u001B[35m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
            case ORANGE ->
                    System.out.printf("\u001B[38;2;255;165;0m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
            case WHITE -> System.out.printf("%2d", field.getTile(row, column).getColor().ordinal());
            case CYAN -> System.out.printf("\u001B[36m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
            case GREY -> System.out.printf("\u001B[37m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
            case DARK_RED ->
                    System.out.printf("\u001B[91m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
            case PINK ->
                    System.out.printf("\u001B[38;2;255;192;203m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
            case GOLD ->
                    System.out.printf("\u001B[38;2;255;215;0m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
            case BROWN ->
                    System.out.printf("\u001B[38;2;165;42;42m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
        }
    }

    private void printFirstLine() {
        System.out.print("____");
        for (int i = 0; i < field.getColumnCount(); i++) {
            System.out.printf("%2d", (i + 1));

            System.out.print("__");
        }
        System.out.println();
    }

    private void printScore() {
        var scores = scoreService.getTopScores("numberlink");
        for (int i = 0; i < scores.size(); i++) {
            var score = scores.get(i);
            System.out.printf("%d %s %d\n", (i + 1), score.getPlayer(), score.getPoints());
        }
        System.out.println("--------------------------------------------------");
    }
}
