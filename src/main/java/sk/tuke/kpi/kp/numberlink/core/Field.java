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
        if(rowCount != columnCount) {
            throw new IllegalArgumentException("Pole musí mať rovnaké hodnoty strán.");
        }
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        tiles = new Tile[rowCount][columnCount];
        prepareField();
        this.pairs = new Integer[maps.getCountOfNumber(rowCount)];
        //System.out.println("pairs = " +pairs.length);
    }

    public void prepareField() {
        this.maps = new Maps(getRowCount(), getColumnCount());
        int[][] mapValues = maps.getMap(getRowCount());
        System.out.print("__");
        for(int i = 0; i<getColumnCount(); i++) {
            System.out.print(i+1+"_");
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
    }

   public void markTile(int row, int column){
        if(tiles[row][column] instanceof Number ){
            volueOfPrevious = ((Number) tiles[row][column]).getVolue();
            if(((Number) tiles[row][column]).getIsFirst()==false){
                if (column+1 < columnCount && tiles[row][column +1].getColor().ordinal() == volueOfPrevious) {
                    tiles[row][column+1].setNextLine(tiles[row][column]);
                   // System.out.println("1* sa robi pri hodnote "+ volueOfPrevious);
                }else if (row +1 < rowCount && tiles[row+1][column].getColor().ordinal() == volueOfPrevious) {
                    tiles[row+1][column].setNextLine(tiles[row][column]);
                    //System.out.println("2* sa robi pri hodnote "+ volueOfPrevious);
                }else if (row -1 >= 0 && tiles[row-1][column].getColor().ordinal() == volueOfPrevious) {
                    tiles[row-1][column].setNextLine(tiles[row][column]);
                    //System.out.println("3* sa robi pri hodnote "+ volueOfPrevious);
                }else if (column -1 >= 0 && tiles[row][column-1].getColor().ordinal() == volueOfPrevious) {
                    tiles[row][column-1].setNextLine(tiles[row][column]);
                    //System.out.println("4* sa robi pri hodnote "+ volueOfPrevious);
                }/*else{
                    changeFisrtInNumber(volueOfPrevious);
                }*/

                if(checkConnection(((Number) tiles[row][column]).getVolue())){
                    System.out.println("ano je to par");
                    if(checkStateOfTheGame()){
                        System.out.println("GAME OVER");
                        state = GameState.SOLVED;
                    }else{
                        System.out.println("THE GAME IS NOT OVER YER");
                    }
                }else{
                    System.out.println("nie nie je to par pre hodnotu "+ volueOfPrevious);

                    if(!isThereFisrtOfNumber(volueOfPrevious)){
                        ((Number) tiles[row][column]).setFirst(true);
                    }else{
                        changeFisrtInNumber(volueOfPrevious);
                    }
                }
            }
            else{
                tiles[row][column].setNextLine(null);
                removeLines(((Number) tiles[row][column]).getVolue());

            }
        }
        else if(tiles[row][column] instanceof Line && volueOfPrevious != 0 && volueOfPrevious!=tiles[row][column].getColor().ordinal()){//skontroluj ci je potrebne posledna podmienka
            if(volueOfPrevious==-1){
                volueOfPrevious = tiles[row][column].getColor().ordinal();
                System.out.println("anio volue = " + volueOfPrevious);
                return;
            }
            if (row > 0 && tiles[row - 1][column].getColor().ordinal() == volueOfPrevious && !tiles[row - 1][column].hasNextLine() &&
                    (tiles[row - 1][column] instanceof Line ||  (tiles[row - 1][column] instanceof Number && ((Number) tiles[row - 1][column]).getIsFirst()))) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
               // System.out.println("--1--");
                removeContinuedLines(row,column);
                ( tiles[row - 1][column]).setNextLine(tiles[row][column]);
               // generateField();
            }
            else if (column > 0 && tiles[row][column - 1].getColor().ordinal() == volueOfPrevious && !tiles[row][column - 1].hasNextLine() &&
                    (tiles[row][column-1] instanceof Line || (tiles[row][column-1] instanceof Number && ((Number) tiles[row][column-1]).getIsFirst()))) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
               // System.out.println("--2--");
                removeContinuedLines(row,column);
                (tiles[row][column-1]).setNextLine(tiles[row][column]);

               // generateField();
            }
            else if (column+1 < columnCount && tiles[row][column+1].getColor().ordinal() == volueOfPrevious && !tiles[row][column+1].hasNextLine() &&
                    (tiles[row][column+1] instanceof Line|| (tiles[row][column+1] instanceof Number && ((Number) tiles[row][column+1]).getIsFirst()))) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                removeContinuedLines(row,column);
                (tiles[row][column+1]).setNextLine(tiles[row][column]);
               // generateField();
            }
            else if (row+1 < rowCount && tiles[row+1][column].getColor().ordinal() == volueOfPrevious && !tiles[row+1][column].hasNextLine() &&
                    (tiles[row+1][column] instanceof Line|| (tiles[row+1][column] instanceof Number && ((Number) tiles[row+1][column]).getIsFirst()))) {
                tiles[row][column].setColor(colors[volueOfPrevious]);
                removeContinuedLines(row,column);
                (tiles[row+1][column]).setNextLine(tiles[row][column]);
              //  generateField();
            }else{
                volueOfPrevious = -1;
            }
        }else if(tiles[row][column] instanceof Line && volueOfPrevious != 0 && volueOfPrevious==tiles[row][column].getColor().ordinal()){
            removeContinuedLines(row,column);
           // generateField();
           // System.out.println("KRUZNICA");
        }
    }

    private boolean checkStateOfTheGame() {
       // System.out.println("CHECKING STATE OF THE GAME");
        int counter = 0;
        for( int i = 0; i< pairs.length; i++){
            if(checkConnection(i+1)){
                counter++;
            }
            else{
                return false;
            }
        }
        if(counter == pairs.length){
            for (int row = 0; row<getRowCount(); row++){
                for(int column = 0; column<getColumnCount(); column++) {
                    if(tiles[row][column].getColor() == Colors.NULL){
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
        for (int row = 0; row<getRowCount(); row++){
            for(int column = 0; column<getColumnCount(); column++) {
                if(tiles[row][column]instanceof Number && ((Number) tiles[row][column]).getIsFirst() && ((Number) tiles[row][column]).getVolue() == volueOfPrevious){
                    return true;
                }
            }
        }
        return false;
    }

    private void changeFisrtInNumber(int volueOfPrevious) {
      //  System.out.println("**********************");
        for (int row = 0; row<getRowCount(); row++){
            for(int column = 0; column<getColumnCount(); column++) {
                if(tiles[row][column] instanceof Number && ((Number) tiles[row][column]).getVolue()==volueOfPrevious){
                    if(((Number) tiles[row][column]).getIsFirst()){
                        ((Number) tiles[row][column]).setFirst(false);
                      //  System.out.println("last = ["+row+"]["+column+"]");
                    }else{
                        ((Number) tiles[row][column]).setFirst(true);
                       // System.out.println("first = ["+row+"]["+column+"]");
                    }
                }
            }
        }
        removeLines(volueOfPrevious);

    }

    public void removeContinuedLines(int row,int column){
        removePreviousLine(row,column);
        if((tiles[row][column]).getNextLine()!=null) {
            Line nextLine = (Line) (tiles[row][column]).getNextLine();
            //Line nextLine = (Line) (tiles[row][column]);

            while (nextLine.getNextLine() != null && nextLine.getNextLine() instanceof Line) {
                nextLine.setColor(Colors.NULL);//nextLine.setNextLine(null);
                Line prevline = nextLine;
                nextLine = (Line) nextLine.getNextLine();
                prevline.setNextLine(null);
            }
            if(nextLine instanceof Line) {
                nextLine.setColor(Colors.NULL);
                nextLine.setNextLine(null);
            }
        }
        (tiles[row][column]).setNextLine(null);
    }
    private void removePreviousLine(int row,int column){
        for (int i = 0; i<getRowCount(); i++){
            for(int j = 0; j<getColumnCount(); j++) {
                if (tiles[i][j].getNextLine() ==tiles[row][column] && tiles[i][j].getColor().ordinal()!=volueOfPrevious){
                    tiles[i][j].setNextLine(null);
                }
            }
        }
    }
    public boolean checkConnection(int volueOfNumber){
        for (int row = 0; row<getRowCount(); row++){
            for(int column = 0; column<getColumnCount(); column++) {
                if(tiles[row][column] instanceof Number && ((Number) tiles[row][column]).getIsFirst() == true &&
                        tiles[row][column].getColor().ordinal() == volueOfNumber){
                   // System.out.println("row = " + row + " column = " + column);
                    Tile nextLine =  (tiles[row][column]);
                    while (nextLine.getNextLine() != null) {
                     //   System.out.println("*row = " + row + " column = " + column);
                        nextLine =  nextLine.getNextLine();
                    }
                    //generateField();
                    if(nextLine instanceof Number && nextLine.getColor().ordinal() ==volueOfNumber && !((Number) nextLine).getIsFirst()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void removeLines(int volue){
       // System.out.println("REMOVING LINES");
        for (int row = 0; row<getRowCount(); row++){
            for(int column = 0; column<getColumnCount(); column++) {
                if(tiles[row][column] instanceof Line && tiles[row][column].getColor().ordinal() == volue){
                    tiles[row][column].setColor(Colors.NULL);
                    tiles[row][column].setNextLine(null);
                }
            }
        }
      //  generateField();
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
