package sk.tuke.kpi.kp.numberlink.core;

public class Field {
    private final int rowCount;

    private final int columnCount;

    private final Tile[][] tiles;
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
        //Colors[] colors = Colors.values(); // získame pole všetkých konštánt enumu

        for (int row = 0; row<getRowCount(); row++){
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
    }

    public void generateField(){
        for (int row = 0; row<getRowCount(); row++){
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
    }

    public void markTile(int volueOfPrev, int row, int column){
        if(tiles[row][column] instanceof Number){
            volueOfPrev = ((Number) tiles[row][column]).getVolue();
        }else if(tiles[row][column] instanceof Line){
            tiles[row][column].setColor(Colors.BLUE);
            System.out.println("+++" + tiles[row][column].getColor());

           // tiles[row][column].setColor(colors[volueOfPrev]);
           // tiles[row][column]=tiles[row][column].getColor();

            generateField();
        }
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
