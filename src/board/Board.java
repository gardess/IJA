
package board;

public class Board {
    private Rules rules;
    private Field[][] fields;
    public Board(Rules rules) {
        this.rules = rules;
        int size = rules.getSize();
        this.fields = new Field[size+2][size+2];

        for (int i = 0; i < size+2; i++)
            for (int j = 0; j < size+2; j++)
                    this.fields[i][j] = rules.createField(i,j);
        
        for (int i = 1; i < size; i++)
            for (int j = 1; j < size; j++)
            {
                this.fields[i][j].addNextField(Field.Direction.U, this.fields[i-1][j]);
                this.fields[i][j].addNextField(Field.Direction.RU, this.fields[i-1][j+1]);
                this.fields[i][j].addNextField(Field.Direction.R, this.fields[i][j+1]);
                this.fields[i][j].addNextField(Field.Direction.RD, this.fields[i+1][j+1]);
                this.fields[i][j].addNextField(Field.Direction.D, this.fields[i+1][j]);
                this.fields[i][j].addNextField(Field.Direction.LD, this.fields[i+1][j-1]);
                this.fields[i][j].addNextField(Field.Direction.L, this.fields[i][j-1]);
                this.fields[i][j].addNextField(Field.Direction.LU, this.fields[i-1][j-1]);
            }
        this.fields[4][4].forceDisk(new Disk(true));
        this.fields[5][5].forceDisk(new Disk(true));
        this.fields[4][5].forceDisk(new Disk(false));
        this.fields[5][4].forceDisk(new Disk(false));
                
    }
    
    public Field getField(int row, int col) {
        return this.fields[row][col];
    }
    
    public Rules getRules() {
        return this.rules;
    }
    
    public int getSize() {
        return this.rules.getSize();
    }
}
