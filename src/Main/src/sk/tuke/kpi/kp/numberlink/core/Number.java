package sk.tuke.kpi.kp.numberlink.core;

public class Number extends Tile{
    private final int volue;
    private boolean isFirst;

    public Number(int volue) {
        this.volue = volue;
    }

    public int getVolue() {
        return volue;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
