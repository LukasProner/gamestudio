package sk.tuke.gamestudio.game.numberlink.core;

public class Number extends Tile{
    private final int volue;
    private boolean isFirst;
    private boolean isLast;

    public Number(int volue) {
        this.volue = volue;
        this.setFirst(false);
        this.setLast(false);
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public int getVolue() {
        return volue;
    }

    public boolean getIsFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
