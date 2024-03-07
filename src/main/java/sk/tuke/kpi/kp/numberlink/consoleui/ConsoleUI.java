package sk.tuke.kpi.kp.numberlink.consoleui;

import sk.tuke.kpi.kp.numberlink.core.Colors;
import sk.tuke.kpi.kp.numberlink.core.GameState;
import sk.tuke.kpi.kp.numberlink.core.Field;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    private static final Pattern INPUT_PATTERN_FOR_MOVE = Pattern.compile("([A-I])([1-9])");
    private static final Pattern INPUT_PATTERN_FOR_FIELD_SIZE = Pattern.compile("([1-9])[*]([1-9])");
    private Field field;
    private Scanner scanner = new Scanner(System.in);

    /*public ConsoleUI(Field field) {
        this.field = field;
    }*/
    public void play(){
        while(field==null) {
            field = handleSizeOfField();
        }
        while(field != null && field.getState() == GameState.PLAYING) {
            handleInput();
            show();
            if(field.getState() == GameState.SOLVED) {
                System.out.println("Solved!");
            }
        }
        wannaPlayAgain();
    }
    private boolean wannaPlayAgain(){
        System.out.println("Gratulujeme, vyhrali ste!");
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
        System.out.print("___");
        for(int i = 0; i<field.getColumnCount(); i++) {
            System.out.print(i+1+"_");
        }
        System.out.println();
        for (int row = 0; row<field.getRowCount(); row++){
            System.out.print((char) ('A' + row) +"|");
            for(int column = 0; column<field.getColumnCount(); column++) {
                if(field.getTile(row,column).getColor() == Colors.NULL/*tiles[row][column].getColor() == Colors.NULL*/){
                    System.out.print(" _");
                }else{
                    System.out.print(" ");
                    System.out.print(field.getTile(row,column).getColor().ordinal());
                }
            }
            System.out.println();
        }
        System.out.print("already connected pairs: ");
        field.connectedNumbers();
        System.out.println();
    }
}
