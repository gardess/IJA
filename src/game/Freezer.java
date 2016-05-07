
package game;

import board.Board;
import board.Field;
import board.Disk;
import static java.lang.Long.remainderUnsigned;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Zajišťujě zamrzání kamenů. Kameny k zamrznutí jsou vybírány náhodně.
 * zamrznutí může být provedeno jen při volání run().
 * Střídají se 2 fáze: rozmrznuté a zamrznuté kameny.
 * Po uplynutí času fáze se na druhou změní až po volání run().
 */
public class Freezer {
    Random random;
    private int timeNoFreeze;
    private int timeFreeze;
    private int diskCount;
    private boolean freezePhase;
    private long timeToChange; // cas (v ms), ve ktery se zmeni faze
    
    /**
     * Nastavení zamrzávání. Po náhodnou dobu (0 až timeNoFreeze) jsou všechny kameny rozmrzlé.
     * Potom jsou po náhodnou dobu (0 až timeFreeze) kameny zamrzlé.
     * @param timeNoFreeze Maximální čas v sekundách kdy nebudou zamrzlé kameny.
     * @param timeFreeze Maximální čas v sekudnách kdy budou zamrzlé kameny.
     * @param diskCount Počet kamenů které se zamrazí.
     */
    public Freezer(int timeNoFreeze, int timeFreeze, int diskCount) {
        random = new Random();
        this.timeNoFreeze = timeNoFreeze;
        this.timeFreeze = timeFreeze;
        this.diskCount = diskCount;
        freezePhase = false;
        timeToChange = generateTime();
    }
    
    /**
     * Provede odpovídající fázi (zamrznutí nebo rozmrznutí) na hrací desce
     * podle uplynutého času.
     * @param board Herní deska na které se má zamrznutí/rozmrznutí provést
     */
    public void run(Board board) {
        if (System.currentTimeMillis() >= timeToChange) {
            freezePhase = !freezePhase;
            timeToChange = generateTime();
        }
        if (freezePhase)
            freeze(board);
        else
            unfreeze(board);
    }
    
    /**
     * Rozmrazí všechny kameny na hrací desce
     * @param board Herní deska na které se má rozmrznutí provést
     */
    public void unfreeze(Board board) {
        for (Field field : board) {
            if (!field.isEmpty())
                field.getDisk().unfreeze();
        }
    }
    
    /**
     * Náhodně zamrazí nastavený počet kamenů na herní desce.
     * Pokud jsou na desce již nějaké zamrzlé kameny, první je rozmrazí.
     * @param board Herní deska na které se má zamrznutí provést
     */
    public void freeze(Board board) {
        unfreeze(board);
        List<Disk> allDisks = new ArrayList<>();
        for (Field field : board) {
            if (!field.isEmpty())
                allDisks.add(field.getDisk());
        }
        // zamrazi se vsechny kameny
        if (allDisks.size() < this.diskCount)
            for (Disk disk : allDisks)
                disk.freeze();
        
        // budou se nahodne vybirat kameny k zamrznuti
        else {
            for (int i = 0; i < this.diskCount; i++) {
                int r = random.nextInt(allDisks.size());
                Disk disk = allDisks.remove(r);
                disk.freeze();
            }
        }
    }
    
    /**
     * Vygeneruje nahodny cas dalsi zmeny, podle stavajci faze.
     */
    private long generateTime() {
        int maxSec = (freezePhase) ? timeFreeze : timeNoFreeze;
        long maxMilisec = 1000 * maxSec;
        if (maxMilisec > 0)
            return System.currentTimeMillis() + remainderUnsigned(random.nextLong(), maxMilisec);
        else
            return System.currentTimeMillis();
    }
}
