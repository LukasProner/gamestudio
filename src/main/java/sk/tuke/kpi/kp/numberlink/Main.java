package sk.tuke.kpi.kp.numberlink;

import sk.tuke.kpi.kp.numberlink.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.numberlink.core.TimerOfGame;

import java.util.Timer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var ui = new ConsoleUI();
        ui.play();
        //Timer timer = new Timer();
        //timer.schedule(new TimerOfGame(),0,1000);
    }
}