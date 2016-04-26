
package game;
import board.BoardField;
import board.BorderField;
import board.Field;
import board.Rules;

public class ReversiRules implements Rules{
    private int size;
    
    public ReversiRules(int size) {
        this.size = size;
    }
    
    public Field createField(int row, int col) {
        if (row == 0 || col == 0 || row == this.size+1 || col == this.size+1)
            return new BorderField();
        else
            return new BoardField(row, col);
    }
    
    public int getSize() {
        return this.size;
    }
    
    public int numberDisks() {
        return this.size*this.size/2;
    }
}
