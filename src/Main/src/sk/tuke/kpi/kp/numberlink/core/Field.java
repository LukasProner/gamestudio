package sk.tuke.kpi.kp.numberlink.core;

public class Field {
    private final int rowCount;

    private final int columnCount;

    private final Tile[][] tiles;
    //private int volueOfPrevious[][] = new int[10][3];
    private int volueOfPrevious = 0;
    private GameState state = GameState.PLAYING;
    private Colors[] colors = Colors.values(); //to convert a number to a color
    public Field(int rowCount, int columnCount) {
        if(rowCount != columnCount) {
            throw new IllegalArgumentException("Pole musí mať rovnaké hodnoty strán.");
        }
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        tiles = new Tile[rowCount][columnCount];
        prepareField();
    }


    public void prepareField() {
        Maps maps = new Maps(getRowCount(), getColumnCount());
        int[][] mapValues = maps.getMap(getRowCount());
        System.out.print("  ");
        for(int i = 0; i<getColumnCount(); i++) {
            System.out.print(i+1+" ");
        }
        System.out.println();
        for (int row = 0; row<getRowCount(); row++){
            System.out.print((char) ('A' + row) );
            for(int column = 0; column<getColumnCount(); column++){
                if(mapValues[row][column]==0){
                    tiles[row][column] = new Line();
                    tiles[row][column].setColor(Colors.NULL);
                    System.out.print(" _");
                }else{
                    System.out.print(" ");
                    tiles[row][column] = new Number(mapValues[row][column]);
                    tiles[row][column].setColor(colors[mapValues[row][column]]);
                    System.out.print(((Number) tiles[row][column]).getVolue());
                }
            }
            System.out.println("");
        }
        System.out.println();
    }

    public void generateField(){
        System.out.print("  ");
        for(int i = 0; i<getColumnCount(); i++) {
            System.out.print(i+1+" ");
        }
        System.out.println();
        for (int row = 0; row<getRowCount(); row++){
            System.out.print((char) ('A' + row) );
            for(int column = 0; column<getColumnCount(); column++) {
                if(tiles[row][column].getColor() == Colors.NULL){
                    System.out.print(" _");
                }else{
                    System.out.print(" ");
                    System.out.print(tiles[row][column].getColor().ordinal());
                }
            }
            System.out.println();
        }
        System.out.println();
    }
/*
    public void markTile(int row, int column){
        if(tiles[row][column] instanceof Number){
            volueOfPrevious[((Number) tiles[row][column]).getVolue()][0] = ((Number) tiles[row][column]).getVolue();
            volueOfPrevious[((Number) tiles[row][column]).getVolue()][1] = row;
            volueOfPrevious[((Number) tiles[row][column]).getVolue()][2] = column;
        }else if(tiles[row][column] instanceof Line){
            if (row > 0 && tiles[row - 1][column].getColor().ordinal() == volueOfPrevious) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                generateField();
            }
            else if (column > 0 && tiles[row][column - 1].getColor().ordinal() == volueOfPrevious) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                generateField();
            }
            else if (column+1 < columnCount && tiles[row][column +1].getColor().ordinal() == volueOfPrevious) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                generateField();
            }
            else if (row+1 < rowCount && tiles[row+1][column].getColor().ordinal() == volueOfPrevious) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                generateField();
            }
        }
    }
*/

    public void markTile(int row, int column){
        if(tiles[row][column] instanceof Number){
            volueOfPrevious = ((Number) tiles[row][column]).getVolue();
        }else if(tiles[row][column] instanceof Line){
            if (row > 0 && tiles[row - 1][column].getColor().ordinal() == volueOfPrevious) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                generateField();
            }
            else if (column > 0 && tiles[row][column - 1].getColor().ordinal() == volueOfPrevious) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                generateField();
            }
            else if (column+1 < columnCount && tiles[row][column +1].getColor().ordinal() == volueOfPrevious) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                generateField();
            }
            else if (row+1 < rowCount && tiles[row+1][column].getColor().ordinal() == volueOfPrevious) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                generateField();
            }
        }
    }
    public void removeLines(int volue){
        for (int row = 0; row<getRowCount(); row++){
            for(int column = 0; column<getColumnCount(); column++) {
                if(tiles[row][column] instanceof Line && tiles[row][column].getColor().ordinal() == volue){
                    tiles[row][column].setColor(Colors.NULL);
                }
            }
        }
        generateField();
    }

    private void checkConnection(){

    }



    public int getVolueOfPrevious() {
        return volueOfPrevious;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }


    public GameState getState() {
        return state;
    }
}
