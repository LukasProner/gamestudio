package sk.tuke.gamestudio.game.numberlink;

import sk.tuke.gamestudio.game.numberlink.consoleui.ConsoleUI;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
   public static void main(String[] args) {
        var ui = new ConsoleUI();
        ui.play();


    }
}
   /*public static void main(String[] args) {
           // Inicializácia hry
           // ...

           // Spustenie časovača
           Timer timer = new Timer();
           timer.schedule(new Casovac(), 0, 1000);
       Scanner scanner = new Scanner(System.in);

           // Čakanie na vstup od používateľa
       for(int i =0 ; i<5;i++) {
           //System.out.print("Zadajte vstup: ");
           String input = scanner.nextLine();
           System.out.println("Zadal si1: " + input);
       }
           scanner.close();

           // Zastavenie časovača
           timer.cancel();
       }
   }

    class Casovac extends TimerTask {
        private int cas = 0; // Počiatočný čas

        @Override
        public void run() {
            cas++; // Zvýši sa čas o jednu sekundu

            // Vypíše čas v každom kroku časovača a čaká na vstup od používateľa
            System.out.print("\rUplynulo sekúnd: " + cas + ", zadajte vstup2: "); // Použitie '\r' pre vynulovanie riadku pred výpisom
            System.out.flush(); // Vyčistí výstupný buffer, aby sa zabezpečila okamžitá aktualizácia
        }
    }*/