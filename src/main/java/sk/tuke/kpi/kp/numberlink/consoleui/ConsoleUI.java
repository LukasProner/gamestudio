package sk.tuke.kpi.kp.numberlink.consoleui;

import sk.tuke.kpi.kp.numberlink.core.Colors;
import sk.tuke.kpi.kp.numberlink.core.GameState;
import sk.tuke.kpi.kp.numberlink.core.Field;
import sk.tuke.kpi.kp.numberlink.core.TimerOfGame;
import sk.tuke.kpi.kp.numberlink.service.ScoreService;
import sk.tuke.kpi.kp.numberlink.service.ScoreServiceJDBC;

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

    public ConsoleUI() {
        this.timer = new Timer();
        this.timerOfGame = new TimerOfGame();

    }
    public void play(){
        while(field==null) {
            field = handleSizeOfField();
        }
        timer.schedule(timerOfGame,0,1000);
        while(field != null && field.getState() == GameState.PLAYING) {
            show();
            System.out.println("timer je " + timerOfGame.getTime());
            handleInput();
            if(field.getState() == GameState.SOLVED) {
                show();
                timer.cancel();
                System.out.println("Solved!");
            }
        }
        if(wannaPlayAgain()){
            //play();
        }
    }
    private boolean wannaPlayAgain(){
        System.out.println("Gratulujeme, vyhrali ste!");
        System.out.println("Your time was " + timerOfGame.getTime() );
        System.out.println("Prajete si začatie novej hry (A/N)? _");
        var input = scanner.nextLine().toUpperCase();
        if ("X".equals(input) || "N".equals(input)) {
            System.out.println("have a nice day");
            System.exit(0);
        }else if("A".equals(input)){
            return true;
        }
        return false;
    }
    private Field handleSizeOfField(){
        printScore();
        System.out.print("Enter size of field (X - exit, 5*5 - size of field): ");
        var sizeInput = scanner.nextLine().toUpperCase();
        if ("X".equals(sizeInput)) {
            System.out.println("have a nice day");
            System.exit(0);
        }
        var sizeMatcher = INPUT_PATTERN_FOR_FIELD_SIZE.matcher(sizeInput);
        if (sizeMatcher.matches()) {
            int rows = Integer.parseInt(sizeMatcher.group(1));
            int columns = Integer.parseInt(sizeMatcher.group(2));
            if(rows!=columns){
                System.out.println("the size have to be square");
                return null;
            }
            return new Field(rows,columns);
        } else {
            System.out.println("Invalid input for field size.");
            return null;
        }
    }

    private void handleInput() {
        System.out.print("Enter command (X - exit, A0 - mark tile): ");
        var line = scanner.nextLine().toUpperCase();
        if("X".equals(line)){
            timer.cancel();
            System.out.println("have a nice day");
            System.exit(0);
        }
        var matcher = INPUT_PATTERN_FOR_MOVE.matcher(line);
        if(matcher.matches()){
            System.out.println(matcher.group(1)+" "+matcher.group(2));
            int row = matcher.group(1).charAt(0)-'A';
            int column = Integer.parseInt(matcher.group(2))-1;
            //System.out.println(row +" "+ column);
            field.markTile(row,column);

        }else{
            System.out.println("wrong input");
        }
    }
    public void show(){
        System.out.print("____");
        for(int i = 0; i<field.getColumnCount(); i++) {
            System.out.printf("%2d", (i+1));

            System.out.print("__");
        }
        System.out.println();
        for (int row = 0; row<field.getRowCount(); row++){
            System.out.print((char) ('A' + row) +"|");
            for(int column = 0; column<field.getColumnCount(); column++) {
                if(field.getTile(row,column).getColor() == Colors.NULL/*tiles[row][column].getColor() == Colors.NULL*/){
                    System.out.print("   _");
                }else{
                    System.out.print("  ");
                    switch (field.getTile(row,column).getColor()){
                        case RED -> System.out.printf("\u001B[31m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
                        case GREEN ->System.out.printf("\u001B[32m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
                        case YELLOW ->System.out.printf("\u001B[33m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
                        case BLUE -> System.out.printf("\u001B[34m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
                        case PURPLE ->System.out.printf("\u001B[35m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
                        case ORANGE-> System.out.printf("\u001B[38;2;255;165;0m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
                        case WHITE->System.out.printf("%2d",field.getTile(row,column).getColor().ordinal());
                        case CYAN -> System.out.printf("\u001B[36m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
                        case GREY -> System.out.printf("\u001B[37m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
                        case DARK_RED ->System.out.printf("\u001B[91m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
                        case PINK -> System.out.printf("\u001B[38;2;255;192;203m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
                        case GOLD ->System.out.printf("\u001B[38;2;255;215;0m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());
                        case BROWN ->System.out.printf("\u001B[38;2;165;42;42m%2d\u001B[0m", field.getTile(row, column).getColor().ordinal());


                    }
                }
            }
            System.out.println();
        }
        System.out.print("already connected pairs: ");
        field.connectedNumbers();
        System.out.println();
    }

    private void printScore(){
        var scores = scoreService.getTopScores("mines");
        for(int i=0; i<scores.size();i++){
            var score = scores.get(i);
            System.out.printf("%d %s %d\n",(i+1),score.getPlayer(),score.getPoints());
        }
        System.out.println("--------------------------------------------------");
    }
}
