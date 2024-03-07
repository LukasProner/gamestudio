package sk.tuke.kpi.kp.numberlink.core;

public class Field {
    private final int rowCount;
    private final int columnCount;
    private final Tile[][] tiles;
    private Maps maps;
    private final Integer[] pairs;
    private int volueOfPrevious = 0;
    private GameState state = GameState.PLAYING;
    private Colors[] colors = Colors.values(); //to convert a number to a color

    public Field(int rowCount, int columnCount) {
        if (rowCount != columnCount) {
            throw new IllegalArgumentException("Pole musí mať rovnaké hodnoty strán.");
        }
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        tiles = new Tile[rowCount][columnCount];
        prepareField();
        this.pairs = new Integer[maps.getCountOfNumber(rowCount)];
    }

    public void prepareField() {
        this.maps = new Maps(getRowCount(), getColumnCount());
        int[][] mapValues = maps.getMap(getRowCount());
        System.out.print("  _");
        for (int i = 0; i < getColumnCount(); i++) {
            System.out.print(i + 1 + "_");
        }
        System.out.println();
        for (int row = 0; row < getRowCount(); row++) {
            System.out.print((char) ('A' + row) +"|");
            for (int column = 0; column < getColumnCount(); column++) {
                if (mapValues[row][column] == 0) {
                    tiles[row][column] = new Line();
                    tiles[row][column].setColor(Colors.NULL);
                    System.out.print(" _");
                } else {
                    System.out.print(" ");
                    tiles[row][column] = new Number(mapValues[row][column]);
                    tiles[row][column].setColor(colors[mapValues[row][column]]);
                    System.out.print(((Number) tiles[row][column]).getVolue());
                }
            }
            System.out.println(" |");
        }
        System.out.println();
    }

    public void markTile(int row, int column) {
        if (row >= rowCount || column >= columnCount) {
            return;
        }
        if (tiles[row][column] instanceof Number) {
            volueOfPrevious = ((Number) tiles[row][column]).getVolue();
            if (checkConnection(volueOfPrevious)) {
                System.out.println("preco ako?" + volueOfPrevious );
                removeLines(volueOfPrevious);
                if (!((Number) tiles[row][column]).getIsFirst()) {
                    changeFisrtInNumber(volueOfPrevious);
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
        if (volueOfPrevious != tiles[row][column].getColor().ordinal()) {//skontroluj ci je potrebne posledna podmienka
            if (volueOfPrevious == -1) {
                volueOfPrevious = tiles[row][column].getColor().ordinal();
                removeContinuedLines(row, column);

            } else if (row > 0 && tiles[row - 1][column].getColor().ordinal() == volueOfPrevious && !tiles[row - 1][column].hasNextLine() &&
                    (tiles[row - 1][column] instanceof Line || (tiles[row - 1][column] instanceof Number && ((Number) tiles[row - 1][column]).getIsFirst()))) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                removeContinuedLines(row, column);
                (tiles[row - 1][column]).setNextLine(tiles[row][column]);

            } else if (column > 0 && tiles[row][column - 1].getColor().ordinal() == volueOfPrevious && !tiles[row][column - 1].hasNextLine() &&
                    (tiles[row][column - 1] instanceof Line || (tiles[row][column - 1] instanceof Number && ((Number) tiles[row][column - 1]).getIsFirst()))) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                removeContinuedLines(row, column);
                (tiles[row][column - 1]).setNextLine(tiles[row][column]);

            } else if (column + 1 < columnCount && tiles[row][column + 1].getColor().ordinal() == volueOfPrevious && !tiles[row][column + 1].hasNextLine() &&
                    (tiles[row][column + 1] instanceof Line || (tiles[row][column + 1] instanceof Number && ((Number) tiles[row][column + 1]).getIsFirst()))) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                removeContinuedLines(row, column);
                (tiles[row][column + 1]).setNextLine(tiles[row][column]);

            } else if (row + 1 < rowCount && tiles[row + 1][column].getColor().ordinal() == volueOfPrevious && !tiles[row + 1][column].hasNextLine() &&
                    (tiles[row + 1][column] instanceof Line || (tiles[row + 1][column] instanceof Number && ((Number) tiles[row + 1][column]).getIsFirst()))) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                removeContinuedLines(row, column);
                (tiles[row + 1][column]).setNextLine(tiles[row][column]);

            } else if (tiles[row][column].getColor() == Colors.NULL) {
                volueOfPrevious = -1;
            } else {//specialny pripad bude na to test *********************************************
                removeContinuedLines(row, column);
                volueOfPrevious = tiles[row][column].getColor().ordinal();
            }
        } else if (volueOfPrevious == tiles[row][column].getColor().ordinal()) {
            removeContinuedLines(row, column);
            // generateField();
        }else{
            removeContinuedLines(row, column);
            removePreviousLine(row,column);
        }
    }

    private void writeGameState(int row, int column) {
        if (checkConnection(((Number) tiles[row][column]).getVolue())) {
            System.out.println("ano je to par");
            if (checkStateOfTheGame()) {
                System.out.println("GAME OVER");
                state = GameState.SOLVED;
            } else {
                System.out.println("THE GAME IS NOT OVER YER");
            }
        } else {
            System.out.println("nie nie je to par pre hodnotu " + volueOfPrevious);
            if (!isThereFisrtOfNumber(volueOfPrevious)) {
                ((Number) tiles[row][column]).setFirst(true);
            } else {
                changeFisrtInNumber(volueOfPrevious);
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
        // System.out.println("CHECKING STATE OF THE GAME");
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

    private boolean isThereFisrtOfNumber(int volueOfPrevious) {
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (tiles[row][column] instanceof Number && ((Number) tiles[row][column]).getIsFirst() && ((Number) tiles[row][column]).getVolue() == volueOfPrevious) {
                    return true;
                }
            }
        }
        return false;
    }

    private void changeFisrtInNumber(int volueOfPrevious) {
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (tiles[row][column] instanceof Number && ((Number) tiles[row][column]).getVolue() == volueOfPrevious) {
                    if (((Number) tiles[row][column]).getIsFirst()) {
                        ((Number) tiles[row][column]).setFirst(false);
                        (tiles[row][column]).setNextLine(null);
                    } else {
                        ((Number) tiles[row][column]).setFirst(true);
                    }
                }
            }
        }
        removeLines(volueOfPrevious);

    }

    public void removeContinuedLines(int row, int column) {
        //removePreviousLine(row, column);
        if ((tiles[row][column]).getNextLine() != null && tiles[row][column].getNextLine() instanceof Line) {
            Line nextLine = (Line) (tiles[row][column]).getNextLine();

            while (nextLine.getNextLine() != null && nextLine.getNextLine() instanceof Line) {
                nextLine.setColor(Colors.NULL);//nextLine.setNextLine(null);
                Line prevline = nextLine;
                nextLine = (Line) nextLine.getNextLine();
                prevline.setNextLine(null);
            }
            if (nextLine instanceof Line) {
                nextLine.setColor(Colors.NULL);
                nextLine.setNextLine(null);
            }
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

    public boolean checkConnection(int volueOfNumber) {
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (tiles[row][column] instanceof Number && ((Number) tiles[row][column]).getIsFirst() &&
                        tiles[row][column].getColor().ordinal() == volueOfNumber) {
                    Tile nextLine = (tiles[row][column]);
                    System.out.println("------------ " + row + " r  c " + column);
                    while (nextLine.getNextLine() != null) {
                        nextLine = nextLine.getNextLine();
                        System.out.println("a");
                    }
                    //generateField();
                    if (nextLine instanceof Number && nextLine.getColor().ordinal() == volueOfNumber && !((Number) nextLine).getIsFirst()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void removeLines(int volue) {
        for (int row = 0; row < getRowCount(); row++) {
            for (int column = 0; column < getColumnCount(); column++) {
                if (tiles[row][column] instanceof Line && tiles[row][column].getColor().ordinal() == volue) {
                    tiles[row][column].setColor(Colors.NULL);
                    tiles[row][column].setNextLine(null);
                }
            }
        }
        //  generateField();
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
    /*public void generateField(){
        System.out.print("___");
        for(int i = 0; i<getColumnCount(); i++) {
            System.out.print(i+1+"_");
        }
        System.out.println();
        for (int row = 0; row<getRowCount(); row++){
            System.out.print((char) ('A' + row) +"|");
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
    }*/
}
