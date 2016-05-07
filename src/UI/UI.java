package UI;

import board.*;
import game.*;
import playerControl.ComputerMinMax;
import playerControl.ComputerRandom;
import playerControl.Human;

/**
 * Testovací třída s textovým UI.
 * @author Tomáš Zahradníček
 */
public class UI {
    public static void printGame(Game game) {
        Board board = game.getBoard();
        System.out.println("-------------------");
        System.out.println("Pripravene kameny:");
        System.out.println(game.getPlayer(false) + " | " + game.getPlayer(false).poolSize());
        System.out.println(game.getPlayer(true) + " | " + game.getPlayer(true).poolSize());
        if (game.isEnd())
            System.out.println("Konec hry");
        else
            System.out.println("na tahu: " + game.currentPlayer());
        
        // hraci deska
        System.out.print(" ");
        for (int i = 1; i <= board.getSize(); i++)
            System.out.print(i);
        System.out.println();
        for (int i = 1; i <= board.getSize(); i++) {
            System.out.print(i);
            for (int j = 1; j <= board.getSize(); j++) {
                Field field = board.getField(i, j);
                if (field.isEmpty())
                    System.out.print(" ");
                else if(field.getDisk().isWhite()) {
                    if (field.getDisk().isFrozen())
                        System.out.print("c");
                    else
                        System.out.print("o");
                }
                else {
                    if (field.getDisk().isFrozen())
                        System.out.print("k");
                    else
                        System.out.print("x");
                }
            }
            System.out.println();
        }
    }
            
    public static void main(String [] args) {
        Game game = new Game(8, new Freezer(0,0,1)); // 0,0,1 - kazdy tah se zamrzne/rozmrzne 1 kamen
        game.addPlayer(new Player(false, new Human()));
        game.addPlayer(new Player(true, new ComputerMinMax()));
        game.start();
        printGame(game);
        game.play(5,6);
        printGame(game);
    }
}
