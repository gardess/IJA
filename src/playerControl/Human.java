package playerControl;

import game.PlayerControl;
import game.Game;
import board.Field;

/**
 * Rozhodování hráče člověka. Nedokáže se sám rozhodnout o dalším tahu (čeká na vstup z UI).
 * @author Tomáš Zahradníček
 */
public class Human implements PlayerControl{
    @Override
    public boolean isAI() {
        return false;
    }

    @Override
    public Field getNextMove(Game game) {
        throw new UnsupportedOperationException("Human is not AI.");
    }
}
