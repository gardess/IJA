
package board;

public class BoardField implements Field {
    private int row, col;
    private Field nextFields[];
    private Disk disk;
    
    public BoardField(int row, int col) {
        this.row = row;
        this.col = col;
        this.nextFields = new Field[8];
        disk = null;
    }
    
    public BoardField(BoardField field) {
        this.row = field.row;
        this.col = field.col;
        this.nextFields = new Field[8];
        if (field.isEmpty())
            disk = null;
        else
            disk = new Disk(field.getDisk());
    }
    
    @Override
    public void addNextField(Direction dirs, Field field) {
        this.nextFields[dirs.ordinal()] = field;
    }
    
    @Override
    public Field nextField(Field.Direction dirs) {
        return this.nextFields[dirs.ordinal()];
    }
    
    @Override
    public boolean putDisk(Disk disk) {
        if (this.disk == null) {
            this.disk = disk;
            return true;
        }
        else
            return false;
    }
    
    @Override
    public boolean isEmpty(){
        return this.disk == null;
    }
    
    @Override
    public Disk getDisk() {
        return this.disk;
    }
    
    @Override
    public boolean canPlayDisk(Disk disk) {
        if (this.disk != null)
            return false;
        
        Disk oponentDisk = new Disk(!disk.isWhite());
        for (Direction dir: Direction.values()) {
            Field currentField = this;
            boolean oponentBetween = false;
            currentField = currentField.nextField(dir);
            // dokud se nenarazi na prazdne policko (zamrzle se chova jako prazdne)
            while (!currentField.isEmpty() && !currentField.getDisk().isFrozen()) {
                if (oponentDisk.equals(currentField.getDisk()))
                    oponentBetween = true;
                else
                    if (oponentBetween)
                        return true; // kamen stejne barvy, v tomto smeru se muzou otacet
                    else
                        break; // nalezen kamen vlastni barvy, ale zadne souperovy
                currentField = currentField.nextField(dir);
            }
        }
        return false;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BoardField))
            return false;
        BoardField f = (BoardField) o;
        return (this.row == f.row && this.col == f.col);
    }
    
    @Override
    public boolean playDisk(Disk disk) {
        if (!this.canPlayDisk(disk))
            return false;
        
        this.disk = disk;
        Disk oponentDisk = new Disk(!disk.isWhite());
        for (Direction dir: Direction.values()) {
            Field field = this;
            boolean oponentBetween = false;
            // dokud se nenarazi na prazdne policko (zamrzle se chova jako prazdne)
            while (!field.isEmpty() && !field.getDisk().isFrozen()) {
                if (oponentDisk.equals(field.getDisk()))
                    oponentBetween = true;
                else if (disk.equals(field.getDisk()) && oponentBetween) {
                    field = this;
                    field = field.nextField(dir);
                    while (!field.getDisk().equals(disk)) { // obrati kameny v tomto smeru
                        field.getDisk().turn();
                        field = field.nextField(dir);
                    }
                    break;
                }
                field = field.nextField(dir);
            }
        }

        return true;
    }
    
    /**
     * Vrátí řádek políčka
     * @return řádek políčka
     */
    @Override
    public int getRow(){
        return this.row;
    }
    
    /**
     * Vrátí sloupec políčka
     * @return sloupec políčka
     */
    @Override
    public int getCol(){
        return this.col;
    }       
}
