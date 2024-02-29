package sk.tuke.kpi.kp.numberlink;

import sk.tuke.kpi.kp.numberlink.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.numberlink.core.Field;
import sk.tuke.kpi.kp.numberlink.core.Number;
import sk.tuke.kpi.kp.numberlink.core.Tile;
import sk.tuke.kpi.kp.numberlink.test.FieldTest;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
      /*  var field = new Field(6, 6);
        var ui = new ConsoleUI(field);
        ui.play();
*/
        var field = new Field(6, 6);
        field.markTile(0,2);
        field.markTile(0,3);
        field.markTile(0,4);
        field.markTile(1,4);
        field.markTile(2,4);
        field.markTile(3,4);
        field.markTile(3,3);
        field.markTile(3,0);
        field.markTile(3,3);
        field.markTile(3,2);
        field.generateField();

        //test prekryvania 2 a 4
       /* var field = new Field(6, 6);
        field.markTile(0,5);
        field.markTile(1,5);
        field.markTile(2,5);
        field.markTile(3,5);
        field.markTile(4,5);
        field.markTile(4,4);
        field.markTile(3,4);
        field.markTile(2,4);
        field.markTile(2,4);
        field.markTile(1,4);
        field.markTile(1,3);
        field.markTile(1,4);
        field.markTile(1,5);
        field.markTile(0,5);
        field.markTile(1,5);
        field.markTile(2,5);
        field.markTile(3,5);
        field.markTile(4,5);
        field.markTile(4,4);
        field.markTile(3,4);
        field.markTile(2,4);
        field.markTile(2,4);
        field.markTile(1,4);
        field.markTile(1,3);
        field.markTile(1,4);*/


        /*//dve stvorkove kruznice
        var field = new Field(6, 6);
        field.markTile(0,5);
        field.markTile(1,5);
        field.markTile(2,5);
        field.markTile(3,5);
        field.markTile(4,5);
        field.markTile(4,4);
        field.markTile(3,4);
        field.markTile(2,4);
        field.markTile(2,5);
        System.out.println("main - "+field.getTile(2,4).getNextLine());

       field.markTile(3,5);
        field.markTile(4,5);
        field.markTile(4,4);
        field.markTile(3,4);
        field.markTile(2,4);
        field.markTile(1,4);
        field.markTile(1,5);
        field.markTile(2,5);
        field.markTile(3,5);
        field.markTile(4,5);
        field.markTile(4,4);
        field.markTile(3,4);
*/


        //test toho že ak napriklad spojim v 6x6 1 a potom spätne pojdem 2 po 1 ceste
        /*var field = new Field(6, 6);
        // test vztvorenia dvojice, nasledneho prerusenia inou liniu a znovupokusenia sa o spojenie
        field.markTile(0,0);
        field.markTile(1,0);
        field.markTile(2,0);
        System.out.println("main - "+field.getTile(2,1).getNextLine());

        field.markTile(2,1);
        System.out.println("main - "+field.getTile(2,1).getNextLine());

        field.markTile(2,2);
        System.out.println("main - "+field.getTile(2,1).getNextLine());
        field.markTile(2,3);
        field.markTile(0,1);
        field.markTile(1,1);
        field.markTile(2,1);
        //field.getTile(2,0).setNextLine(null);
        System.out.println("main - "+field.getTile(2,1).getNextLine());

        field.markTile(2,0);
        System.out.println("main - "+field.getTile(2,1).getNextLine());
        System.out.println("main - "+field.getTile(2,2).getNextLine());

        if(field.getTile(2,2) == field.getTile(2,1).getNextLine()){
            System.out.println("ano ma referenciu na tento prvok");
        }
        field.markTile(1,0);
        field.generateField();

*/
       /* field.markTile(0,0);
        field.markTile(1,0);
        field.markTile(2,0);
        field.markTile(2,1);
        field.markTile(2,2);
        field.markTile(2,3);*/




        // solved 5x5
        //4
        /*Field field = new Field(5, 5);
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
        field.markTile(4,1);
*/
       /* // test na kontrolu toho ak oznacim jedno cislo ci nebudem moct dat ku druhemu
        Field field = new Field(5, 5);
        field.markTile(4, 4); // PORIESIT POTOM !!!!!!
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