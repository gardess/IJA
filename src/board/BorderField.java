
package board;

/**
 * Reprezentuje pomocné, neplatné políčko na hrací desce.
 * Políčko je vždy prázdné a nelze na něj nic vložit.
 * @author Tom
 */
public class BorderField implements Field{
    
    @Override
    public void addNextField(Direction dirs, Field field) {
    }
    
    @Override
    public Field nextField(Field.Direction dirs) {
        return null;
    }
    
    @Override
    public boolean putDisk(Disk disk) {
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public boolean canPlayDisk(Disk disk) {
        return false;
    }
    
    @Override
    public Disk getDisk() {
        return null;
    }
    
    @Override
    public boolean playDisk(Disk disk) {
        return false;
    }

    @Override
    public int getRow() {
        return -1;
    }

    @Override
    public int getCol() {
        return -1;
    }
}
