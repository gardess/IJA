
package board;

public class Disk {
    private boolean isWhite;
    private boolean isFrozen;
    
    /**
     * Inicializace kamene.
     * @param isWhite nastavení barvy - true je bílá, false černá.
     */
    public Disk(boolean isWhite) {
        this(isWhite, false);
    }
    
    /**
     * Inicializace kamene včetně nastavení zamrznutí.
     * @param isWhite nastavení barvy - true je bílá, false černá.
     * @param isFrozen zda je kámen zamrzlý
     */
    public Disk(boolean isWhite, boolean isFrozen) {
        this.isWhite = isWhite;
        this.isFrozen = isFrozen;
    }
    
    /**
     * Zkopíruje kámen.
     * @param disk původní kámen.
     */
    public Disk(Disk disk) {
        this.isWhite = disk.isWhite;
        this.isFrozen = disk.isFrozen;
    }
    
    /**
     * Test, zda je kámen bílý
     * @return true, pokud je kámen bílý.
     */
    public boolean isWhite() {
        return this.isWhite;
    }
    
    /**
     * Test, zda je kámen zamrzlý
     * @return true, pokud je kámen zamrzlý
     */
    public boolean isFrozen() {
        return this.isFrozen;
    }
    
    /**
     * Obrátí barvu kamene.
     */
    public void turn() {
        this.isWhite = !this.isWhite;
    }
    
    /**
     * Zamrazí kámen.
     */
    public void freeze() {
        isFrozen = true;
    }
    
    /**
     * Odmrazí kámen.
     */
    public void unfreeze() {
        isFrozen = false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Disk))
            return false;
        Disk d = (Disk) o;
        return d.isWhite == this.isWhite;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.isWhite ? 1 : 0);
        hash = 37 * hash + (this.isFrozen ? 1 : 0);
        return hash;
    }
}
