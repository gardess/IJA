package playerControl;

import game.PlayerControl;
import game.Game;
import board.Field;
import java.util.List;
import java.util.Random;

/**
 * Implementuje rozhodování hráče jako počítač. Tahy vybírá zcela náhodně ze všech možných.
 * @author Tomáš Zahradníček
 */
public class ComputerRandom implements PlayerControl {
    private Random random;
    
    /**
     * Vytvoří generátor náhodných čísel
     */
    public ComputerRandom() {
        random = new Random();
    }

    @Override
    public boolean isAI() {
        return true;
    }

    @Override
    public Field getNextMove(Game game) {
        List<Field> allPlays = game.possiblePlays();
        int playsCount = allPlays.size();
        int i = random.nextInt(playsCount);
        return allPlays.get(i);
    }
    
}
