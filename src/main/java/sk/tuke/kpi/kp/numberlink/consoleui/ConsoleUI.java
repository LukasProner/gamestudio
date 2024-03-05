package sk.tuke.kpi.kp.numberlink.consoleui;

import sk.tuke.kpi.kp.numberlink.core.GameState;
import sk.tuke.kpi.kp.numberlink.core.Field;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    private static final Pattern INPUT_PATTERN_FOR_MOVE = Pattern.compile("([A-I])([1-9])");
  //  private static final Pattern INPUT_PATTERN_FOR_SIZE = Pattern.compile("([1-9])([x]([1-9])");
    private Field field;
    private Scanner scanner = new Scanner(System.in);

    public ConsoleUI(Field field) {
        this.field = field;
    }
    public void play(){
        while(field.getState()== GameState.PLAYING) {
            handleInput();
            field.generateField();
        }
    }

    private void handleInput() {
        System.out.print("Enter size of field (X - exit, 5x5 - size of field): ");



        System.out.print("Enter command (X - exit, A0 - mark tile): ");
        var line = scanner.nextLine().toUpperCase();
        if("X".equals(line)){
            System.exit(0);
        }
        var matcher = INPUT_PATTERN_FOR_MOVE.matcher(line);
        if(matcher.matches()){
            System.out.println(matcher.group(1)+" "+matcher.group(2));
            int row = matcher.group(1).charAt(0)-'A';
            int column = Integer.parseInt(matcher.group(2))-1;
            System.out.println(row +" "+ column);
            field.markTile(row,column);

        }else{
            System.out.println("wrong input");
        }
    }
}
