package sk.tuke.gamestudio.game.numberlink.core;

public abstract class Tile {
    private boolean isColored;
    private Colors color = Colors.NULL;

    private Tile nextLine;

    public boolean hasNextLine() {
        if (getNextLine() != null) {
            return false;
        }
        return true;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public Colors getColor() {
        return color;
    }

    public boolean isColored() {
        return isColored;
    }

    public void setNextLine(Tile nextLine) {
        this.nextLine = nextLine;
    }

    public Tile getNextLine() {
        return nextLine;
    }

}
