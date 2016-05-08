package playerControl;

import game.PlayerControl;
import game.Game;
import board.Field;
import game.Freezer;

/**
 * Implementuje rozhodování hráče jako počítač. Tahy vybírá metodou MinMax, s implicitní hloubkou 5.
 * @author Tomáš Zahradníček
 */
public class ComputerMinMax implements PlayerControl {
    private int maxDepth;
    
    /**
     * Konstruktor pro počítačového hráče hrajícího metodou MinMax.
     */
    public ComputerMinMax() {
        this(5);
    }
    
    /**
     * Vytvoří objekt s explicitní hloubkou prohledávání.
     * @param maxDepth hloubka prohledávání
     */
    public ComputerMinMax(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public boolean isAI() {
        return true;
    }

    @Override
    public Field getNextMove(Game game) {
        // zkopiruje hru pro vlastni vypocty
        Game myGame = new Game(game);
        return getNextScoredMove(myGame, maxDepth).field;
    }
    
    /**
     * Zjistí nejlepší tah pro hráče na tahu.
     * @param game Hra, pro kterou se zjistí tah.
     * @param depth Zbývající hloubka.
     * @return dvojce (nejlepší tah, ohodnocení), kde větší ohodnocení znamená lepší pro bíleho hráče.
     */
    private ScoredField getNextScoredMove(Game game, int depth) {
        // vytvori novou hru, ktera ma stejnou aktualni desku
        Freezer myFreezer = new Freezer();

        // zjistim zda chci zvysovat nebo snizovat ohodnoceni
        boolean myColor = game.currentPlayer().isWhite();
        boolean maximizing = myColor;
        
        // inicializace hodnot
        int bestScore = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        ScoredField bestField = new ScoredField(null, bestScore);                
        
        // ze vsech moznych tahu zjisti nejlepsi
        for (Field field : game.possiblePlays()) {
            game.play1(field.getRow(), field.getCol());
            
            // ohodnoceni tahu
            int score;
            if (depth <= 1 || game.isEnd())
                score = game.score(true) - game.score(false);
            else {
                // dalsi tahy uz neuvazuju zamrzani - nevim jake bude
                myFreezer.unfreeze(game.getBoard());
                score = getNextScoredMove(game, depth-1).score;
            }            
            
            if ((maximizing && score > bestScore) || !maximizing && score < bestScore) {
                bestField.score = score;
                bestField.field = field;
            }
            
            // nakonec vratit do puvodniho stavu
            game.undo1();
        }
        return bestField;
    }

    /**
     * Dvojice (políčko, ohodnocení) pro ohodnocovaní jednotlivých tahů
     */
    private class ScoredField {
        public Field field;
        public int score;
        
        public ScoredField() {
            this(null, 0);
        }
        
        public ScoredField(Field field, int score) {
            this.field = field;
            this.score = score;
        }
    }
}
