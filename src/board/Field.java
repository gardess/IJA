
package board;

public interface Field {
    public static enum Direction {
        D, L, LD, LU, R, RD, RU, U
    }
    public void addNextField(Direction dirs, Field field);
    public boolean canPutDisk(Disk disk);
    public Disk getDisk();
    public boolean isEmpty();
    public Field nextField(Direction dirs);
    public boolean forceDisk(Disk disk);
    public boolean putDisk(Disk disk);
}
