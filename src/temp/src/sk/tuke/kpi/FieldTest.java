package sk.tuke.kpi.kp.numberlink.test;

import sk.tuke.kpi.kp.numberlink.core.Colors;
import sk.tuke.kpi.kp.numberlink.core.GameState;
import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.numberlink.core.Field;
import sk.tuke.kpi.kp.numberlink.core.Number;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;


public class FieldTest {
    @Test
    public void gameAlmostCompletedButThereAreFreeSpaces() {
        // test na kontrolu toho ci su vsetky dlazdice po dokonceni plne ale este nemam mapu kde by sa to dalo overi
    }

    @Test
    public void testOfRemovingNumberAndGettinItAgainFromLineTile() {
        var field = new Field(6, 6);
        field.markTile(0,2);
        field.markTile(0,3);
        field.markTile(0,4);
        field.markTile(1,4);
        field.markTile(2,4);
        field.markTile(3,4);
        field.markTile(3,3);
        System.out.println("-----1");
        field.markTile(3,0);
        System.out.println("-----2");
        field.markTile(3,3);
        System.out.println("-----3");
        field.markTile(3,2);
        System.out.println("-----4");

        Assertions.assertSame((field.getTile(3,2).getColor()), Colors.YELLOW);

        field.generateField();
    }

    @Test
    public void twoXCircleSPrerusenimADokoncenim() {
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
        Assertions.assertSame((field.getTile(3,5).getNextLine()),null);
        Assertions.assertSame((field.getTile(2,4).getNextLine()),null);
        Assertions.assertSame((field.getTile(2,5).getNextLine()),null);
        Assertions.assertSame((field.getTile(0,5).getNextLine()),field.getTile(1,5));
        field.markTile(3,5);
        Assertions.assertSame((field.getTile(2,5).getNextLine()),field.getTile(3,5));
        field.markTile(4,5);
        field.markTile(4,4);
        Assertions.assertSame((field.getTile(4,5).getNextLine()),field.getTile(4,4));
        field.markTile(3,4);
        field.markTile(2,4);
        Assertions.assertSame((field.getTile(3,4).getNextLine()),field.getTile(2,4));
        field.markTile(1,4);
        field.markTile(1,5);
        Assertions.assertSame((field.getTile(1,4).getNextLine()),null);
        Assertions.assertSame((field.getTile(1,5).getColor()),Colors.ORANGE);
        Assertions.assertSame((field.getTile(2,5).getColor()),Colors.NULL);
    }

    @Test
    public void twoXCircleSoZrusenimAOpakovanim() {
        var field = new Field(6, 6);
        field.markTile(0,5);
        field.markTile(1,5);
        field.markTile(2,5);
        Assertions.assertSame((field.getTile(1,5).getNextLine()),field.getTile(2,5));
        field.markTile(3,5);
        Assertions.assertSame((field.getTile(2,5).getNextLine()),field.getTile(3,5));
        field.markTile(4,5);
        Assertions.assertSame((field.getTile(3,5).getNextLine()),field.getTile(4,5));
        field.markTile(4,4);
        Assertions.assertSame((field.getTile(4,5).getNextLine()),field.getTile(4,4));
        field.markTile(3,4);
        Assertions.assertSame((field.getTile(4,4).getNextLine()),field.getTile(3,4));
        field.markTile(2,4);
        Assertions.assertSame((field.getTile(3,4).getNextLine()),field.getTile(2,4));
        field.markTile(2,5);
        Assertions.assertSame((field.getTile(2,5).getNextLine()),null);
        Assertions.assertSame((field.getTile(3,5).getNextLine()),null);
        Assertions.assertSame((field.getTile(3,5).getColor()),Colors.NULL);
        Assertions.assertSame((field.getTile(4,5).getNextLine()),null);
        Assertions.assertSame((field.getTile(4,4).getNextLine()),null);
        Assertions.assertSame((field.getTile(3,4).getNextLine()),null);
        Assertions.assertSame((field.getTile(2,4).getNextLine()),null);
        Assertions.assertSame((field.getTile(1,4).getNextLine()),null);
        Assertions.assertSame((field.getTile(0,5).getNextLine()),field.getTile(1,5));
        Assertions.assertSame(((Number) field.getTile(0, 5)).getIsFirst(),true);
        field.markTile(0,5);
        Assertions.assertSame(((Number) field.getTile(0, 5)).getIsFirst(),true);
        Assertions.assertSame((field.getTile(0,5).getNextLine()),null);

        field.markTile(0,5);
        Assertions.assertSame(((Number) field.getTile(0, 5)).getIsFirst(),true);
        Assertions.assertSame((field.getTile(0,5).getNextLine()),null);
        field.markTile(1,5);
        Assertions.assertSame((field.getTile(0,5).getNextLine()),field.getTile(1,5));
        field.markTile(2,5);
        Assertions.assertSame((field.getTile(1,5).getNextLine()),field.getTile(2,5));
        field.markTile(3,5);
        Assertions.assertSame((field.getTile(2,5).getNextLine()),field.getTile(3,5));
        field.markTile(4,5);
        Assertions.assertSame((field.getTile(3,5).getNextLine()),field.getTile(4,5));
        field.markTile(4,4);
        Assertions.assertSame((field.getTile(4,5).getNextLine()),field.getTile(4,4));
        field.markTile(3,4);
        Assertions.assertSame((field.getTile(4,4).getNextLine()),field.getTile(3,4));
        field.markTile(2,4);
        Assertions.assertSame((field.getTile(3,4).getNextLine()),field.getTile(2,4));
        field.markTile(2,5);
        Assertions.assertSame((field.getTile(2,5).getNextLine()),null);
        Assertions.assertSame((field.getTile(3,5).getNextLine()),null);
        Assertions.assertSame((field.getTile(3,5).getColor()),Colors.NULL);
        Assertions.assertSame((field.getTile(4,5).getNextLine()),null);
        Assertions.assertSame((field.getTile(4,4).getNextLine()),null);


    }

    @Test
    public void spatnyPrechod() {
        var field = new Field(6, 6);
        // test vztvorenia dvojice, nasledneho prerusenia inou liniu a znovupokusenia sa o spojenie
        field.markTile(0,0);
        field.markTile(1,0);
        field.markTile(2,0);
        System.out.println("main - "+field.getTile(2,1).getNextLine());
        Assertions.assertSame((field.getTile(2,1).getNextLine()),null);
        field.markTile(2,1);
        Assertions.assertSame((field.getTile(2,1).getNextLine()),null);
        field.markTile(2,2);
        Assertions.assertSame((field.getTile(2,1).getNextLine()),field.getTile(2,2));

        field.markTile(2,3);
        Assertions.assertSame((field.checkConnection(1)),true);

        field.markTile(0,1);
        field.markTile(1,1);
        field.markTile(2,1);
        //field.getTile(2,0).setNextLine(null);
        Assertions.assertSame((field.checkConnection(1)),false);
        Assertions.assertSame((field.getTile(2,1).getNextLine()),null);
        field.markTile(2,0);
        Assertions.assertSame((field.getTile(2,1).getNextLine()),field.getTile(2,0));
        Assertions.assertSame((field.getTile(2,2).getNextLine()),null);
        Assertions.assertSame((field.getTile(2,0).getNextLine()),null);

        field.markTile(1,0);
        Assertions.assertSame((field.getTile(1,0).getNextLine()),null);
        Assertions.assertSame((field.getTile(2,0).getNextLine()),field.getTile(1,0));
    }

    @Test
    public void usualControlOfOverlayFor6x6() {
        Field field = new Field(6, 6);
        Assertions.assertSame(field.getRowCount(),6);
        Assertions.assertSame(field.getColumnCount(),6);
        field.markTile(1,3);
        Assertions.assertSame(((Number) field.getTile(1, 3)).getVolue(),2);
        Assertions.assertSame(((Number) field.getTile(1, 3)).getIsFirst(),true);
        field.markTile(1,4);
        field.markTile(2,4);
        field.markTile(3,4);
        field.markTile(4,4);
        field.markTile(5,4);
        Assertions.assertSame((field.getTile(4, 4)).getNextLine(),field.getTile(5, 4));
        Assertions.assertSame((field.getTile(4, 4)).getColor(), Colors.GREEN);
        Assertions.assertSame(((Number) field.getTile(2, 3)).getIsFirst(),false);
        field.markTile(2,3);
        Assertions.assertSame(((Number) field.getTile(2, 3)).getIsFirst(),true);
        field.markTile(2,4);
        Assertions.assertSame((field.getTile(4, 4)).getNextLine(),null);
        Assertions.assertSame((field.getTile(4, 4)).getColor(), Colors.NULL);
        field.markTile(3,4);
        field.markTile(1,3);
        field.markTile(1,4);
        field.markTile(2,4);
        field.markTile(3,4);
        field.markTile(4,4);
        field.markTile(5,4);
        Assertions.assertSame((field.getTile(4, 4)).getNextLine(),field.getTile(5, 4));
        Assertions.assertSame((field.getTile(4, 4)).getColor(), Colors.GREEN);
        field.markTile(0,0);
        Assertions.assertSame(((Number) field.getTile(2, 3)).getIsFirst(),false);
        Assertions.assertSame(((Number) field.getTile(0, 0)).getIsFirst(),true);
    }

    @Test
    public void SpacielCaseOfChangingPositions() {
        var field = new Field(6,6);
        field.markTile(0,0);
        field.markTile(1,0);
        field.markTile(2,0);
        field.markTile(2,1);
        field.markTile(2,2);
        field.markTile(2,3);
        //2
        Assertions.assertSame(((Number) field.getTile(0, 1)).getIsFirst(),false);
        field.markTile(0,1);
        Assertions.assertSame(((Number) field.getTile(0, 1)).getIsFirst(),true);
        field.markTile(1,1);
        field.markTile(1,2);
        field.markTile(1,3);
        Assertions.assertSame(((Number) field.getTile(1, 3)).getIsFirst(),false);
        field.markTile(1,3);
        field.markTile(1,4);//neprechadza to ale to je asi fajn
        Assertions.assertSame(((Number) field.getTile(0, 1)).getIsFirst(),true);
        field.markTile(0,1);
        Assertions.assertSame(((Number) field.getTile(0, 1)).getIsFirst(),true);
        field.markTile(1,1);
        Assertions.assertSame(((Number) field.getTile(0, 1)).getIsFirst(),true);
        field.markTile(1,2);
        field.markTile(1,3);
        Assertions.assertSame(((Number) field.getTile(0, 1)).getIsFirst(),true);

    }

    @Test
    public void changingOfFirsts() {
        var field = new Field(5, 5);
        Assertions.assertSame(((Number) field.getTile(0, 1)).getIsFirst(),false);
        field.markTile(0,1);
        Assertions.assertSame(((Number) field.getTile(0, 1)).getIsFirst(),true);
        field.markTile(0,2);
        field.markTile(0,3);
        field.markTile(0,4);
        field.markTile(2,4);
        Assertions.assertSame(((Number) field.getTile(0, 1)).getIsFirst(),false);
    }

    @Test
    public void solvedGame() {
        Field field = new Field(5, 5);
        field.markTile(4,4);
        field.markTile(4,3);
        field.markTile(4,2);
        field.markTile(3,2);
        Assertions.assertSame(field.checkConnection(4),false);

        field.markTile(3,1);
        Assertions.assertSame(field.checkConnection(4),true);

        Assertions.assertSame(field.getState(), GameState.PLAYING);
        //1
        field.markTile(3,4);
        field.markTile(3,3);
        field.markTile(2,3);
        field.markTile(1,3);
        field.markTile(1,2);
        field.markTile(1,1);
        field.markTile(1,0);
        Assertions.assertSame(field.checkConnection(1),false);
        field.markTile(0,0);
        Assertions.assertSame(field.checkConnection(1),true);
        Assertions.assertSame(field.getState(),GameState.PLAYING);
        //2
        field.markTile(0,1);
        field.markTile(0,2);
        field.markTile(0,3);
        field.markTile(0,4);
        field.markTile(1,4);
        Assertions.assertSame(field.checkConnection(2),false);
        field.markTile(2,4);
        Assertions.assertSame(field.checkConnection(2),true);
        Assertions.assertSame(field.checkConnection(1),true);
        Assertions.assertSame(field.checkConnection(4),true);
        Assertions.assertSame(field.getState(),GameState.PLAYING);

        //3
        field.markTile(2,2);
        field.markTile(2,1);
        field.markTile(2,0);
        field.markTile(3,0);
        field.markTile(4,0);
        Assertions.assertSame(field.checkConnection(3),false);
        field.markTile(4,1);
        Assertions.assertSame(field.checkConnection(3),true);
        Assertions.assertSame(field.checkConnection(2),true);
        Assertions.assertSame(field.checkConnection(1),true);
        Assertions.assertSame(field.checkConnection(4),true);
        Assertions.assertSame(field.getState(),GameState.SOLVED);
    }
}
