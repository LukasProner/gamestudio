package sk.tuke.kpi.kp.numberlink;

import sk.tuke.kpi.kp.numberlink.core.Field;
import sk.tuke.kpi.kp.numberlink.core.Number;
import sk.tuke.kpi.kp.numberlink.core.Tile;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        var field = new Field(6,6);
        System.out.println(field.getTile(0,4).getColor());
        field.markTile(5,1,1);
        System.out.println(field.getTile(1,1).getColor());
        field.generateField();

        /*for (int row = 0; row<field.getRowCount();row++){
            for(int column = 0;column<field.getColumnCount();column++){
                var tile = field.getTile(row,column);
                System.out.print(" -");
            }
            System.out.println("");
        }*/
    }
}