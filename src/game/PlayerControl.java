
package game;

import board.Field;

/**
 * Rozhraní pro rozhodování hráče. Pokud isAI() vrátí true, potom implementující třída
 * dokáže rozhodnout o dalším tahu.
 * @author Tomáš Zahradníček
 */
public interface PlayerControl {
    /**
     * Zjistí zda objekt dokáže rozhodnout o dalším tahu.
     * Pokud vrátí true, může být volána metoda getNextMove.
     * @return true, pokud objekt dokáže rozhodnout o dalším tahu.
     */
    public boolean isAI();
    
    /**
     * Rozhodne o dalším tahu. Muže být volána pouze pokud isAI() vrátil true.
     * @param game Hra, ve které se má hráč na tahu rozhodnout o dalším tahu.
     * @return Políčko, na které se objekt rozhodl táhnout.
     * @throws UnsupportedOperationException pokud isAI() vrátilo false;
     */
    public Field getNextMove(Game game);
}
