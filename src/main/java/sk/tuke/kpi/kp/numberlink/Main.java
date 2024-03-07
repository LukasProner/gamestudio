package sk.tuke.kpi.kp.numberlink;

import sk.tuke.kpi.kp.numberlink.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.numberlink.core.Field;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.print("*");
        System.out.printf("%2d", 4);
        System.out.printf("%2d", 10);
        System.out.printf("%2d", 5);
        var ui = new ConsoleUI();
        ui.play();
    }
}