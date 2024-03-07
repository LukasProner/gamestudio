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
    private int [][] eightXeight = {
            {1, 0, 0, 0, 0, 0, 2, 3},
            {4, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 3, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 6, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 4, 0, 0, 0, 0, 0, 0},
            {0, 5, 0, 0, 0, 7, 0, 0},
            {1, 0, 0, 6, 0, 0, 2, 7},
    };
    private int [][] nineXnine = {
            {6, 5, 0, 0, 0, 3, 4, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 7, 0, 0, 0, 0, 0, 0, 4},
            {0, 6, 0, 0, 0, 8, 0, 0, 0},
            {0, 0, 0, 0, 0, 2, 0, 5, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 3},
            {0, 0, 7, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 8, 0},
            {0, 0, 0, 0, 0, 2, 0, 0, 0},
    };
    private int [][] tenXten = {
            {1, 0, 0, 0, 0, 0, 2, 0, 0, 2},
            {0, 0, 0, 0, 0, 0, 4, 0, 0, 4},
            {0, 0, 0, 5, 3, 0, 0, 0, 0, 0},
            {3, 0, 0, 0, 0, 0, 0, 0, 6, 0},
            {5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {7, 0, 0, 0, 8, 9, 0, 0, 9,10},
            {11,0, 6, 0, 0, 0, 8, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0,12,13},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {11,7,10,12, 0, 0,13, 0, 0, 0},
    };
    private int [][] elevenXeleven = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };
    /*public int getCountOfNumber(int size){
        if(size == 5){
            return 4;
        }else if(size == 6){
            return 5;
        }else if(size == 7){
            return 6;
        }else if(size == 8){
            return 7;
        }else if(size == 9){
            return 8;
        }
        return 0;
    }*/
    public int getCountOfNumber(int size) {
        switch (size) {
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            case 8:
                return 7;
            case 9:
                return 8;
            case 10:
                return 13;
            default:
                return 0;
        }
    }
    public int[][] getMap(int row) {
        switch (row) {
            case 5:
                return fiveXfive;
            case 6:
                return sixXsix;
            case 7:
                return sevenXseven;
            case 8:
                return eightXeight;
            case 9:
                return nineXnine;
            case 10:
                return tenXten;
            default:
                return null;
        }
    }
}
