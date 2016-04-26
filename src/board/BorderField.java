
package board;

public class BorderField implements Field{
    
    public void addNextField(Direction dirs, Field field) {
    }
    
    public Field nextField(Field.Direction dirs) {
        return null;
    }
    
    public boolean forceDisk(Disk disk) {
        return false;
    }
    
    public boolean isEmpty() {
        return true;
    }
    
    public boolean canPutDisk(Disk disk) {
        return false;
    }
    
    public Disk getDisk() {
        return null;
    }
    
    public boolean putDisk(Disk disk) {
        return false;
    }
}
