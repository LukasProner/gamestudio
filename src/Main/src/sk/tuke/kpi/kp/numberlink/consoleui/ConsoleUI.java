package sk.tuke.kpi.kp.numberlink.consoleui;

import sk.tuke.kpi.kp.numberlink.core.Field;

import java.lang.reflect.Parameter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleUI {
    private static final Pattern INPUT_PATTERN = Pattern.compile("([A-I])([1-9])");
    private Field field;
    private Scanner scanner = new Scanner(System.in);

    public ConsoleUI(Field field) {
        this.field = field;
    }
    public void play(){
        while(true) {
            processInput();
            field.generateField();
        }
    }

    private void processInput() {
        System.out.print("Enter command (X - exit, A0 - mark tile): ");
        var line = scanner.nextLine().toUpperCase();
        if("X".equals(line)){
            System.exit(0);
        }
        var matcher = INPUT_PATTERN.matcher(line);
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
