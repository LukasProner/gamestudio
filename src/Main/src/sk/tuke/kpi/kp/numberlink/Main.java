package sk.tuke.kpi.kp.numberlink;

import sk.tuke.kpi.kp.numberlink.core.Field;
import sk.tuke.kpi.kp.numberlink.core.Number;
import sk.tuke.kpi.kp.numberlink.core.Tile;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        var field = new Field(5,5);
        field.markTile(0,0);
        System.out.println(field.getVolueOfPrevious());
        field.markTile(1,1);
        field.markTile(0,1);
        field.markTile(1,1);
        field.markTile(1,2);
        field.markTile(0,2);
        field.markTile(1,2);
        field.markTile(1,2);
        field.markTile(1,3);
        field.removeLines(2);




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