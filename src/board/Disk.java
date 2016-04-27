
package board;

public class Disk {
    private boolean isWhite;
    
    /**
     * Inicializace kamene.
     * @param isWhite nastavení barvy - true je bílá, false černá.
     */
    public Disk(boolean isWhite) {
        this.isWhite = isWhite;
    }
    
    /**
     * Inicializuje kámen na stejné vlastnosti podle předlohy.
     * @param disk původní kámen.
     */
    public Disk(Disk disk) {
        this.isWhite = disk.isWhite;
    }
    
    /**
     * Test, zda je kámen bílý
     * @return true, pokud je kámen bílý.
     */
    public boolean isWhite() {
        return this.isWhite;
    }
    
    /**
     * Obrátí barvu kamene.
     */
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
