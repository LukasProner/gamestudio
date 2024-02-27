package sk.tuke.kpi.kp.numberlink.core;

public class Maps {
    private final int rowCount;

    private final int columnCount;

    public Maps(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    private int[][] fiveXfive = {
            {1, 2, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 3, 0, 2},
            {0, 4, 0, 0, 1},
            {0, 3, 0, 0, 4},
    };
    private int[][] sixXsix = {
            {1, 2, 3, 0, 0, 4},
            {0, 0, 0, 2, 0, 0},
            {0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {3, 4, 5, 0, 0, 5},
    };
    private int [][] sevenXseven = {
            {1, 2, 3, 0, 0, 0, 4},
            {0, 0, 0, 0, 4, 5, 0},
            {0, 0, 0, 0, 0, 3, 0},
            {6, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 6, 1, 0, 0},
            {2, 0, 0, 0, 0, 0, 5},
    };

    public int[][] getMap(int row) {
        if(row == 5){
            return fiveXfive;
        }else if(row ==6){
            return sixXsix;
        }else if(row == 7){
            return sevenXseven;
        }
        return null;
    }
}
