
package game;

import board.Field;
import board.Disk;

/**
 * Reprezentuje hráče v rámci 1 hry. Má bílou nebo čenou barvu a má sadu kamenů se kterou může hrát.
 * @author Tomáš Zahradníček
 */
public class Player {
    private boolean isWhite;
    private int diskCount;
    private PlayerControl control;
    
    /**
     * Inicializace hráče
     * @param isWhite Barva hráče (true bílý, false černý).
     * @param control Objekt pro samostatné rozhodnování hráče.
     */
    public Player(boolean isWhite, PlayerControl control) {
        this.isWhite = isWhite;
        this.control = control;
    }
    
    /**
     * Test, zda je možné vložit nový kámen na dané pole. Kámen se ze sady
     * nevybíra, ani nevkládá na pole.
     * @param field Pole, na které se testuje vkládání kamene.
     * @return Zda je možné vložit kámen.
     */
    public boolean canPlayDisk(Field field) {
        return field.canPlayDisk(new Disk(this.isWhite)) && !this.emptyPool();
    }
    
    /**
     * Test prázdnosti sady kamenů, které má hráš k dispozici.
     * @return Zda je sada prázdná.
     */
    public boolean emptyPool() {
        return this.diskCount == 0;
    }
    
    /**
     * Inicializace hráče podle velikosti hrací desky.
     * Vytvoří sadu kamenů o příslušné velikosti.
     * @param size velikost hrací desky, v rámci které se hráč inicializuje.
     */
    public void init(int size) {
        this.diskCount = size*size/2;
    }
    
    /**
     * Test barvy hráče
     * @return Zda je hráč bílý.
     */
    public boolean isWhite() {
        return this.isWhite;
    }
    
    /**
     * Vloží nový kámen hráče na dané políčko, pokud to pravidla hry umožnují.
     * Pokud lze vložit, vybere kámen ze sady hráče a vloží na políčko.
     * @param field Políčko, na které se vkládá kámen.
     * @return Úspěch akce.
     */
    public boolean playDisk(Field field) {
        if (!this.emptyPool() && field.playDisk(new Disk(this.isWhite))) {
            this.useDisk();
            return true;
        }
        return false;
    }
    
    /**
     * Odebere 1 kámen z hráčovy sady
     */
    public void useDisk() {
        if (!this.emptyPool())
            this.diskCount--;
    }
    
    /**
     * Přidá 1 kámen do hráčovy sady
     */
    public void backDisk() {
        this.diskCount++;
    }
    
    /**
     * Zjistí počet kamenů které má hráč k dispozici
     * @return počet kamenů
     */
    public int poolSize() {
        return this.diskCount;
    }
    
    /**
     * Vrátí objekt rozhodování hráče.
     * @return objekt rozhodování hráče.
     */
    public PlayerControl getControl() {
        return this.control;
    }
    
    @Override
    public String toString() {
        if (isWhite)
            return "white";
        else
            return "black";
    }
}
