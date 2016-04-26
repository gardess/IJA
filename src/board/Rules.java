
package board;

public interface Rules {
    public Field createField(int row, int col);
    public int getSize();
    public int numberDisks();
}
