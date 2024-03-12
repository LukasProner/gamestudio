package sk.tuke.gamestudio.game.numberlink.core;

import java.util.TimerTask;

public class TimerOfGame extends TimerTask {
    private int time = 0;
    private Field field;

    public TimerOfGame(Field field) {
        this.field = field;
    }

    @Override
    public void run() {
        if (field.getState() == GameState.PLAYING)
            time++;
    }

    public int getTime() {
        return time;
    }
}
