
package board;

public class Disk {
    private boolean isWhite;
    
    public Disk(boolean isWhite) {
        this.isWhite = isWhite;
    }
    
    public boolean isWhite() {
        return this.isWhite;
    }
    
    public void turn() {
        this.isWhite = !this.isWhite;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof Disk))
            return false;
        Disk d = (Disk) o;
        return d.isWhite == this.isWhite;
    }
}
