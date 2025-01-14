package sk.tuke.gamestudio.game.numberlink.core;

import java.util.Arrays;
import java.util.Random;

public class Field {
    private final int rowCount;
    private long startMillis;
    private final int columnCount;
    private final Tile[][] tiles;
    private Maps maps;
    private int[][] hintMaps;
    private final Integer[] pairs;
    private int volueOfPrevious = 0;
    private GameState state;
    private final Colors[] colors = Colors.values(); //to convert a number to a color
    //123


    public Field(int rowCount, int columnCount) {
        if (rowCount != columnCount) {
            throw new IllegalArgumentException("Pole musí mať rovnaké hodnoty strán.");
        }
        startMillis = System.currentTimeMillis();
        this.state = GameState.PLAYING;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        tiles = new Tile[rowCount][columnCount];
        prepareField();
        this.pairs = new Integer[maps.getCountOfNumber(rowCount)];
    }

    public void prepareField() {
        this.maps = new Maps(getRowCount(), getColumnCount());
        int[][] mapValues = maps.getMap(getRowCount());
        this.hintMaps = maps.getHintMap(getRowCount());
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (mapValues[row][column] == 0) {
                    tiles[row][column] = new Line();
                    tiles[row][column].setColor(Colors.NULL);
                } else {
                    tiles[row][column] = new Number(mapValues[row][column]);
                    tiles[row][column].setColor(colors[mapValues[row][column]]);
                }
            }
        }
    }

    public void markTile(int row, int column) {
        if (row >= rowCount || column >= columnCount) {
            return;
        }
        if (tiles[row][column] instanceof Number) {
            volueOfPrevious = ((Number) tiles[row][column]).getVolue();
            if (checkConnection(volueOfPrevious)) {
                removeLines(volueOfPrevious);
                if (!((Number) tiles[row][column]).getIsFirst()) {
                    changeFirstInNumber(volueOfPrevious);
                }
            } else if (!((Number) tiles[row][column]).getIsFirst()) {
                connectLinesWithNumber(row, column);
                writeGameState(row, column);
            } else {
                tiles[row][column].setNextLine(null);
                removeLines(((Number) tiles[row][column]).getVolue());

            }
        } else if (tiles[row][column] instanceof Line && volueOfPrevious != 0) {
            connectLineWithLine(row, column);
        }
    }

    private void connectLineWithLine(int row, int column) {
        if (volueOfPrevious != tiles[row][column].getColor().ordinal()) {
            if (volueOfPrevious == -1 && tiles[row][column].getColor() != Colors.NULL) {
                volueOfPrevious = tiles[row][column].getColor().ordinal();
                removeContinuedLines(row, column);

            } else if (row > 0 && tiles[row - 1][column].getColor().ordinal() == volueOfPrevious && tiles[row - 1][column].hasNextLine() &&
                    (tiles[row - 1][column] instanceof Line || (tiles[row - 1][column] instanceof Number && ((Number) tiles[row - 1][column]).getIsFirst()))) {
                changeStateOfTile(row, column);
                (tiles[row - 1][column]).setNextLine(tiles[row][column]);

            } else if (column > 0 && tiles[row][column - 1].getColor().ordinal() == volueOfPrevious && tiles[row][column - 1].hasNextLine() &&
                    (tiles[row][column - 1] instanceof Line || (tiles[row][column - 1] instanceof Number && ((Number) tiles[row][column - 1]).getIsFirst()))) {
                changeStateOfTile(row, column);

                (tiles[row][column - 1]).setNextLine(tiles[row][column]);

            } else if (column + 1 < columnCount && tiles[row][column + 1].getColor().ordinal() == volueOfPrevious && tiles[row][column + 1].hasNextLine() &&
                    (tiles[row][column + 1] instanceof Line || (tiles[row][column + 1] instanceof Number && ((Number) tiles[row][column + 1]).getIsFirst()))) {
                changeStateOfTile(row, column);

                (tiles[row][column + 1]).setNextLine(tiles[row][column]);

            } else if (row + 1 < rowCount && tiles[row + 1][column].getColor().ordinal() == volueOfPrevious && tiles[row + 1][column].hasNextLine() &&
                    (tiles[row + 1][column] instanceof Line || (tiles[row + 1][column] instanceof Number && ((Number) tiles[row + 1][column]).getIsFirst()))) {
                changeStateOfTile(row, column);
                (tiles[row + 1][column]).setNextLine(tiles[row][column]);

            } else if (tiles[row][column].getColor() == Colors.NULL) {
                volueOfPrevious = -1;
            } else {
                removeContinuedLines(row, column);
                volueOfPrevious = tiles[row][column].getColor().ordinal();
            }
        } else if (volueOfPrevious == tiles[row][column].getColor().ordinal()) {
            removeContinuedLines(row, column);
        } else {
            removePreviousLine(row, column);
            removeContinuedLines(row, column);
        }
    }

    private void changeStateOfTile(int row, int column) {
        tiles[row][column].setColor(colors[volueOfPrevious]);
        removeContinuedLines(row, column);
        removePreviousLine(row, column);
    }

    private void writeGameState(int row, int column) {
        if (checkConnection(((Number) tiles[row][column]).getVolue())) {
            if (checkStateOfTheGame()) {
                System.out.println("GAME OVER");
                state = GameState.SOLVED;
            } else {
                //   System.out.println("THE GAME IS NOT OVER YER");
            }
        } else {
            if (!isThereFirstOfNumber(volueOfPrevious)) {
                ((Number) tiles[row][column]).setFirst(true);
            } else {
                changeFirstInNumber(volueOfPrevious);
            }
        }
    }

    private void connectLinesWithNumber(int row, int column) {
        if (column + 1 < columnCount && tiles[row][column + 1].getColor().ordinal() == volueOfPrevious) {
            removeContinuedLines(row, column + 1);
            tiles[row][column + 1].setNextLine(tiles[row][column]);
        } else if (row + 1 < rowCount && tiles[row + 1][column].getColor().ordinal() == volueOfPrevious) {
            removeContinuedLines(row + 1, column);
            tiles[row + 1][column].setNextLine(tiles[row][column]);
        } else if (row - 1 >= 0 && tiles[row - 1][column].getColor().ordinal() == volueOfPrevious) {
            removeContinuedLines(row - 1, column);
            tiles[row - 1][column].setNextLine(tiles[row][column]);
        } else if (column - 1 >= 0 && tiles[row][column - 1].getColor().ordinal() == volueOfPrevious) {
            removeContinuedLines(row, column - 1);
            tiles[row][column - 1].setNextLine(tiles[row][column]);
        }
    }

    private boolean checkStateOfTheGame() {
        int counter = 0;
        for (int i = 0; i < pairs.length; i++) {
            if (checkConnection(i + 1)) {
                counter++;
            } else {
                return false;
            }
        }
        if (counter == pairs.length) {
            for (int row = 0; row < getRowCount(); row++) {
                for (int column = 0; column < getColumnCount(); column++) {
                    if (tiles[row][column].getColor() == Colors.NULL) {
                        System.out.println("NOT EVERY TILE IS COLORED");
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean isThereFirstOfNumber(int valueOfPrevious) {
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (tiles[row][column] instanceof Number && ((Number) tiles[row][column]).getIsFirst() && ((Number) tiles[row][column]).getVolue() == valueOfPrevious) {
                    return true;
                }
            }
        }
        return false;
    }

    private void changeFirstInNumber(int valueOfPrevious) {
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (tiles[row][column] instanceof Number && ((Number) tiles[row][column]).getVolue() == valueOfPrevious) {
                    if (((Number) tiles[row][column]).getIsFirst()) {
                        ((Number) tiles[row][column]).setFirst(false);
                        (tiles[row][column]).setNextLine(null);
                    } else {
                        ((Number) tiles[row][column]).setFirst(true);
                    }
                }
            }
        }
        removeLines(valueOfPrevious);

    }

    public void removeContinuedLines(int row, int column) {
        if ((tiles[row][column]).getNextLine() != null && tiles[row][column].getNextLine() instanceof Line) {
            Line nextLine = (Line) (tiles[row][column]).getNextLine();

            while (nextLine.getNextLine() != null && nextLine.getNextLine() instanceof Line) {
                nextLine.setColor(Colors.NULL);
                Line prevline = nextLine;
                nextLine = (Line) nextLine.getNextLine();
                prevline.setNextLine(null);
            }
            nextLine.setColor(Colors.NULL);
            nextLine.setNextLine(null);
        }
        (tiles[row][column]).setNextLine(null);
    }

    private void removePreviousLine(int row, int column) {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                if (tiles[i][j].getNextLine() == tiles[row][column] && tiles[i][j].getColor().ordinal() != volueOfPrevious) {
                    tiles[i][j].setNextLine(null);
                }
            }
        }
    }
    public int[] getPrevTile(int row, int column) {
        // Assuming tiles is a 2D array of some class that has the method getNextLine()
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                if (tiles[i][j].getNextLine() == tiles[row][column]) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
    //123
    public void connectNumbersInField(){
        Random random = new Random();
        int number = random.nextInt(maps.getCountOfNumber(getRowCount())+1);
        this.hintMaps = maps.getHintMap(getRowCount());
        while(number==0){
            number = random.nextInt(maps.getCountOfNumber(getRowCount())+1);
        }
        findFirstOfNumber(number);

        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (hintMaps[row][column] == number ){
                    tiles[row][column].setColor(colors[number]);
                }
            }
        }
    }

    public int[] findFirstOfNumber(int valueOfPrevious) {
        if(isThereFirstOfNumber(volueOfPrevious)) {
            for (int row = 0; row < getRowCount(); row++) {
                for (int column = 0; column < getColumnCount(); column++) {
                    if (tiles[row][column] instanceof Number && ((Number) tiles[row][column]).getIsFirst() && ((Number) tiles[row][column]).getVolue() == valueOfPrevious) {
                        return new int[]{row, column};
                    }
                }
            }
        }
        return null;
    }



    public int getMaxNumberOfMap(int sizeOfRowsInMap){
        return maps.getCountOfNumber(sizeOfRowsInMap);
    }
    public void unMarkTile(int row,int column){
        tiles[row][column].setColor(Colors.NULL);
    }

    public int[] chooseHint(){
        Random random = new Random();
        int row = random.nextInt(rowCount);
        int column = random.nextInt(columnCount);
        while((hintMaps[row][column]==0 || tiles[row][column].getColor() != Colors.NULL) && !checkStateOfTheGame()) {
            row = random.nextInt(rowCount);
            column = random.nextInt(columnCount);
        }
        System.out.println(tiles[row][column].getColor());
        tiles[row][column].setColor(colors[hintMaps[row][column]]);
        System.out.println(row + " + " + column);
        return null;
    }



    public boolean checkConnection(int valueOfNumber) {
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (tiles[row][column] instanceof Number && ((Number) tiles[row][column]).getIsFirst() &&
                        tiles[row][column].getColor().ordinal() == valueOfNumber) {
                    Tile nextLine = (tiles[row][column]);
                    while (nextLine.getNextLine() != null) {
                        nextLine = nextLine.getNextLine();
                    }
                    if (nextLine instanceof Number && nextLine.getColor().ordinal() == valueOfNumber && !((Number) nextLine).getIsFirst()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void removeLines(int value) {
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (tiles[row][column] instanceof Line && tiles[row][column].getColor().ordinal() == value) {
                    tiles[row][column].setColor(Colors.NULL);
                    tiles[row][column].setNextLine(null);
                }
            }
        }
    }

    public void connectedNumbers() {
        for (int i = 0; i < pairs.length; i++) {
            if (checkConnection(i + 1)) {
                System.out.print(" " + (i + 1));
            }
        }
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

    public void setState(GameState state) {
        this.state = state;
    }

    public int getScore() {
        return state == GameState.SOLVED ?
                (int) (rowCount * columnCount + (System.currentTimeMillis() - startMillis) / 1000)
                :
                0;
    }

}
