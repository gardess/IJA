package UI;

import board.*;
import game.*;

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
                else if(field.getDisk().isWhite())
                    System.out.print("o");
                else
                    System.out.print("x");
            }
            System.out.println();
        }
    }
            
    public static void main(String [] args) {
        Game game = new Game(8);
        game.addPlayer(new Player(true));
        game.addPlayer(new Player(false));
        if (game.undo()) System.exit(1);
        printGame(game);
        game.play(5,6);
        game.play(6,4);
        game.play(4,3);
        printGame(game);
        game.play(5,7);
        game.play(7,4);
        printGame(game);
        if (!game.undo()) System.exit(1);
        game.undo();
        printGame(game);
        game.redo();
        game.redo();
        printGame(game);
        if (game.redo()) System.exit(1);
    }
}
