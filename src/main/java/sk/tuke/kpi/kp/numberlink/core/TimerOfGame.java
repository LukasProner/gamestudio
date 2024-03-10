package sk.tuke.kpi.kp.numberlink.core;

import java.util.TimerTask;

public class TimerOfGame extends TimerTask {
    private int time = 0;
    @Override
    public void run() {
        time++;
        System.out.print("Uplynulo sekúnd: " + time + "\r"); // Použitie '\r' pre vynulovanie riadku pred výpisom
        System.out.flush();
    }
}
