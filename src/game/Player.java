
package game;

import board.Board;
import board.Field;
import board.Disk;

public class Player {
    private boolean isWhite;
    private Board board;
    private int diskCount;
    
    public Player(boolean isWhite) {
        this.isWhite = isWhite;
    }
    
    public boolean canPutDisk(Field field) {
        return field.canPutDisk(new Disk(this.isWhite));
    }
    
    public boolean emptyPool() {
        return this.diskCount == 0;
    }
    
    public void init(Board board) {
        this.board = board;
        this.diskCount = board.getRules().numberDisks();
    }
    
    public boolean isWhite() {
        return this.isWhite;
    }
    
    public boolean putDisk(Field field) {
        return field.putDisk(new Disk(this.isWhite));
    }
    
    public String toString() {
        if (isWhite)
            return "white";
        else
            return "black";
    }
}
