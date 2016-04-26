
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
    
    public void addNextField(Direction dirs, Field field) {
        this.nextFields[dirs.ordinal()] = field;
    }
    
    public Field nextField(Field.Direction dirs) {
        return this.nextFields[dirs.ordinal()];
    }
    
    public boolean forceDisk(Disk disk) {
        if (this.disk == null) {
            this.disk = disk;
            return true;
        }
        else
            return false;
    }
    
    public boolean isEmpty(){
        return this.disk == null;
    }
    
    public Disk getDisk() {
        return this.disk;
    }
    
    public boolean canPutDisk(Disk disk) {
        if (this.disk != null)
            return false;
        boolean valid = false;
        Disk oponentDisk = new Disk(!disk.isWhite());
        for (Direction dir: Direction.values()) {
            Field field = this;
            boolean oponentBetween = false;
            do {
                field = field.nextField(dir);
                if (oponentDisk.equals(field.getDisk()))
                    oponentBetween = true;
                else if (disk.equals(field.getDisk()) && oponentBetween)
                    return true;
            } while (field.getDisk() != null);
        }
        return false;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof BoardField))
            return false;
        BoardField f = (BoardField) o;
        return (this.row == f.row && this.col == f.col);
    }
    
    public boolean putDisk(Disk disk) {
        if (this.canPutDisk(disk)) {
            this.disk = disk;
            Disk oponentDisk = new Disk(!disk.isWhite());
            for (Direction dir: Direction.values()) {
                Field field = this;
                boolean oponentBetween = false;
                do {  //check if direction will be turned
                    field = field.nextField(dir);
                    if (oponentDisk.equals(field.getDisk()))
                        oponentBetween = true;
                    else if (disk.equals(field.getDisk()) && oponentBetween) {
                        field = this;
                        field = field.nextField(dir);
                        while (!field.getDisk().equals(disk)) { // turn all disks in direction
                            field.getDisk().turn();
                            field = field.nextField(dir);
                        }
                    }
                } while (field.getDisk() != null);
            }

            return true;
        }
        else
            return false;
    }
}
