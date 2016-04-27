
package board;
import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Třída neprezentujicí hrací desku. Deska ma velikost (rozměr) N a rozlišuje
 * aktivní a neaktivní pole. Aktivní pole jsou na řádcích a sloupcích od 1 do N.
 * Neaktivní pole jsou na řádcích a sloupcích 0 a N+1.
 * @author Tomáš Zahradníček
 */
public class Board implements Iterable<Field> {
    private int size;
    private Field[][] fields;
    /**
     * Inicializuje desku. Vytvoří a uloží si všechna políčka. Pro každé aktivní
     * políčko nastaví jejich okolí.
     * @param size Velikost hrací desky.
     */
    public Board(int size) {
        this.size = size;
        this.fields = new Field[size+2][size+2];

        for (int i = 0; i < size+2; i++)
            for (int j = 0; j < size+2; j++) {
                if (i == 0 || j == 0 || i == this.size+1 || j == this.size+1)
                    this.fields[i][j] = new BorderField();
                else
                    this.fields[i][j] = new BoardField(i, j);
            }
        
        connectFields();
        
        this.fields[size/2][size/2].putDisk(new Disk(true));
        this.fields[size/2+1][size/2+1].putDisk(new Disk(true));
        this.fields[size/2][size/2+1].putDisk(new Disk(false));
        this.fields[size/2+1][size/2].putDisk(new Disk(false));
                
    }
    
    /**
     * Inicializuje novou desku na stejným obsazením kamenů jako předloha.
     * @param board původní deska
     */
    public Board(Board board) {
        this.size = board.size;
        this.fields = new Field[size+2][size+2];

        for (int i = 0; i < size+2; i++)
            for (int j = 0; j < size+2; j++) {
                if (i == 0 || j == 0 || i == this.size+1 || j == this.size+1)
                    this.fields[i][j] = new BorderField();
                else
                    this.fields[i][j] = new BoardField((BoardField)board.getField(i,j));
            }
        
        connectFields();
    }
    
    private void connectFields() {
        for (int i = 1; i <= size; i++)
            for (int j = 1; j <= size; j++) {
                this.fields[i][j].addNextField(Field.Direction.U, this.fields[i-1][j]);
                this.fields[i][j].addNextField(Field.Direction.RU, this.fields[i-1][j+1]);
                this.fields[i][j].addNextField(Field.Direction.R, this.fields[i][j+1]);
                this.fields[i][j].addNextField(Field.Direction.RD, this.fields[i+1][j+1]);
                this.fields[i][j].addNextField(Field.Direction.D, this.fields[i+1][j]);
                this.fields[i][j].addNextField(Field.Direction.LD, this.fields[i+1][j-1]);
                this.fields[i][j].addNextField(Field.Direction.L, this.fields[i][j-1]);
                this.fields[i][j].addNextField(Field.Direction.LU, this.fields[i-1][j-1]);
        }
    }
    
    /**
     * Vrátí políčko na uvedených souřadnicích.
     * @param row Řádek.
     * @param col Sloupec.
     * @return Políčko na daných souřadnicích
     */
    public Field getField(int row, int col) {
        return this.fields[row][col];
    }
    
    /**
     * Zjistí velikost hrací desky.
     * @return Velikost hrací desky.
     */
    public int getSize() {
        return this.size;
    }
    
    @Override
    public Iterator<Field> iterator() {
        return new BoardIterator();
    }
    
    class BoardIterator implements Iterator<Field> {
        private int row, col;
        
        public BoardIterator() {
            this.row = 1;
            this.col = 1;
        }
        
        @Override
        public boolean hasNext() {
            return row <= size;
        }
        
        @Override
        public Field next() {
            Field ret;
            if (hasNext()) {
                ret = getField(row, col);
                if (col == size) {
                    col = 1;
                    row++;
                }
                else
                    col++;
            }
            else
                throw new NoSuchElementException();
            return ret;
        }
    }
}
