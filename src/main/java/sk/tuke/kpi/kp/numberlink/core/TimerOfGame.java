package sk.tuke.kpi.kp.numberlink.core;

import java.util.TimerTask;

public class TimerOfGame extends TimerTask {
    private int time = 0;
    @Override
    public void run() {
        time++;
        //System.out.print("\rtime : " + time + ",Enter command (X - exit, A0 - mark tile):");
        //System.out.flush();
    }

    public int getTime() {
        return time;
    }
}
