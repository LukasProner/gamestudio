package sk.tuke.kpi.kp.numberlink;

import sk.tuke.kpi.kp.numberlink.core.Field;
import sk.tuke.kpi.kp.numberlink.core.Number;
import sk.tuke.kpi.kp.numberlink.core.Tile;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        var field = new Field(5,5);
        /*field.markTile(4,4);
        field.markTile(4,3);
        field.markTile(3,3);
        field.markTile(2,3);
       // field.markTile(2,4);
        field.markTile(1,3);
        field.markTile(0,3);
        field.markTile(3,4);
        field.markTile(3,3);*/
        field.markTile(4,4);//***************
        field.markTile(4,3);
        field.markTile(3,3);
        field.markTile(3,2);
        field.markTile(3,1);
       /* field.markTile(2,4);
        field.markTile(1,4);
        field.markTile(1,3);
        field.markTile(1,2);
        field.markTile(1,1);
        field.markTile(1,0);
        field.markTile(1,2);*/


       // field.checkConnection(4);

    /*field.markTile(4,4);
        field.markTile(4,3);
        field.markTile(3,3);
        field.markTile(2,3);
       // field.markTile(2,4);
        field.markTile(1,3);
        field.markTile(0,3);
        field.markTile(3,4);
        field.markTile(3,3);*/

        /*field.markTile(4,4); // PORIESIT POTOM !!!!!!
        field.markTile(4,3);
        field.markTile(3,3);
        field.markTile(2,3);
        field.markTile(2,4);
        field.markTile(1,1);*/

        /*field.markTile(1,0);
        field.markTile(1,1);
        field.markTile(1,2);
        field.markTile(1,3);
        field.markTile(1,4);
        field.markTile(1,1);
        field.markTile(2,1);*/
        //field.markTile(0,1);
        //field.markTile(0,2);
       // field.markTile(1,2);

        /*field.markTile(0,1);
        field.markTile(0,2);
        field.markTile(0,3);
        field.markTile(0,4);
        field.markTile(0,1);
        field.markTile(1,1);*/


       // field.removeContinuedLines(1,2);

//********************************
/*if(tiles[row][column] instanceof Number ){
            volueOfPrevious = ((Number) tiles[row][column]).getVolue();
            removeContinuedLines(row,column);
            if(((Number) tiles[row][column]).getIsFirst()==false){
                if (column+1 < columnCount && tiles[row][column +1].getColor().ordinal() == volueOfPrevious) {
                    tiles[row][column+1].setNextLine(tiles[row][column]);
                    System.out.println("robi sa toto");
                }
                if(checkConnection(((Number) tiles[row][column]).getVolue())== true){
                    System.out.println("ano je to par");
                }else{
                    System.out.println("nie nie je to par");
                    ((Number) tiles[row][column]).setFirst(true);
                }

            }
            else{
                removeLines(((Number) tiles[row][column]).getVolue());
            }
        }*/



        //field.generateField();

        /*for (int row = 0; row<field.getRowCount();row++){
            for(int column = 0;column<field.getColumnCount();column++){
                var tile = field.getTile(row,column);
                System.out.print(" -");
            }
            System.out.println("");
        }*/
    }
}