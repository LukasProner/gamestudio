package sk.tuke.kpi.kp.numberlink;

import sk.tuke.kpi.kp.numberlink.core.Field;
import sk.tuke.kpi.kp.numberlink.core.Number;
import sk.tuke.kpi.kp.numberlink.core.Tile;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        var field = new Field(6, 6);
        // test vztvorenia dvojice, nasledneho prerusenia inou liniu a znovupokusenia sa o spojenie
        field.markTile(0,0);
        field.markTile(1,0);
        field.markTile(2,0);
        field.markTile(2,1);
        field.markTile(2,2);
        field.markTile(2,3);

        field.markTile(0,1);
        field.markTile(1,1);
        field.markTile(1,0);

        field.markTile(0,0);
        field.markTile(1,0);
        field.markTile(2,0);
        field.markTile(2,1);
        field.markTile(2,2);
        field.markTile(2,3);

      /*  field.markTile(1,3);
        field.markTile(1,4);
        field.markTile(2,4);
        field.markTile(3,4);
        field.markTile(4,4);
        field.markTile(5,4);

        field.markTile(2,3);
        field.markTile(2,4);
        field.markTile(3,4);

        field.markTile(1,3);
        field.markTile(1,4);
        field.markTile(2,4);
        field.markTile(3,4);
        field.markTile(4,4);
        field.markTile(5,4);*/


        //game of 6x6
        //1
       /*field.markTile(0,0);
        field.markTile(1,0);
        field.markTile(2,0);
        field.markTile(2,1);
        field.markTile(2,2);
        field.markTile(2,3);
        //2
        field.markTile(0,1);
        field.markTile(1,1);
        field.markTile(1,2);
        field.markTile(1,3);
        field.markTile(1,4);
        System.out.println("helo");

        //2 correct
        field.markTile(0,1);
        field.markTile(1,1);
        field.markTile(1,2);
        field.markTile(1,3);*/






        /*//changing of firsts
        field.markTile(0,1);
        field.markTile(0,2);
        field.markTile(0,3);
        field.markTile(0,4);
        //field.markTile(1,4);
        field.markTile(2,4);

        Number number = (Number)field.getTile(0,1);
        System.out.println(number.getIsFirst());
        number = (Number)field.getTile(2,4);
        System.out.println(number.getIsFirst());
        field.generateField();
*/
        // solved 5x5
        /*//4
        field.markTile(4,4);
        field.markTile(4,3);
        field.markTile(4,2);
        field.markTile(3,2);
        field.markTile(3,2);
        field.markTile(3,1);
        //1
        field.markTile(3,4);
        field.markTile(3,3);
        field.markTile(2,3);
        field.markTile(1,3);
        field.markTile(1,2);
        field.markTile(1,1);
        field.markTile(1,0);
        field.markTile(0,0);
        //2
        field.markTile(0,1);
        field.markTile(0,2);
        field.markTile(0,3);
        field.markTile(0,4);
        field.markTile(1,4);
        field.markTile(2,4);
        //3
        field.markTile(2,2);
        field.markTile(2,1);
        field.markTile(2,0);
        field.markTile(3,0);
        field.markTile(4,0);
        field.markTile(4,1);*/

        // test na kontrolu toho ak oznacim jedno cislo ci nebudem moct dat ku druhemu
        /*field.markTile(4, 4); // PORIESIT POTOM !!!!!!
        field.markTile(4, 3);
        field.markTile(3, 3);
        field.markTile(2, 3);
        field.markTile(2, 4);
        field.markTile(1, 1);
        field.markTile(0, 2);
        System.out.println(field.getTile(0,2).getColor());
        field.markTile(1, 4);*/



    }
}