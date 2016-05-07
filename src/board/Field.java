
package board;

/**
 * Rozhraní k políčkům, které mohou být na hrací desce.
 * Políčko zná svoje okolí, tj. pole, která ho obklopují ve všech 8 směrech.
 * @author Tomáš Zahradníček
 */
public interface Field {
    /** 
     * Výčtovž typ reprezentujcí směry jednotlivých sousedních políček.
     */
    public static enum Direction {
        /**
        * Dole.
        */
        D,
        /**
        * Vlevo.
        */
        L,
        /**
        * Vlevo dole.
        */
        LD,
        /**
        * Vlevo nahoru.
        */
        LU,
        /**
        * Vpravo.
        */
        R,
        /**
        * Vpravo dole.
        */
        RD,
        /**
        * Vpravo nahoru.
        */
        RU,
        /**
        * Nahoru.
        */
        U
    }
    /**
    * Přidá sousedni políčko v daném směru
    * @param dirs Směr ve kterém se přidává sousední políčko
    * @param field Sousední políčko v daném směru
    */
    public void addNextField(Direction dirs, Field field);
    
    /**
    * Test, zda je možné na pole vložit kámen. Rídí se pravidly hry.
    * @param disk Vkládaný kámen
    * @return Zda je možné zahrát kámen.
    */
    public boolean canPlayDisk(Disk disk);
    
    /**
    * Vrací kámen, který je na políčku.
    * @return Kámen, který je na políčku. null pokud je políčko prázdné
    */
    public Disk getDisk();
    
    /**
    * Zjistí zda je políčko prázdné
    * @return Zda na políčku není kámen
    */
    public boolean isEmpty();
    
    /**
    * Vrátí sousední políčko v daném směru.
    * @param dirs Směr k sousednímu políčku.
    * @return Políčko v daném směru.
    */
    public Field nextField(Direction dirs);
    
    /**
    * Vloží na políčko kámen. Ignoruje pravidla hry (neotáčí okolní kameny).
    * @param disk Vkládaný kámen.
    * @return Úspěšnost akce.
    */
    public boolean putDisk(Disk disk);
    
    /**
    * Vloží na políčko kámen. Respektuje pravidla hry.
    * @param disk Vkládaný kámen.
    * @return Úspěšnost akce.
    */
    public boolean playDisk(Disk disk);
    
    /**
     * Vrátí řádek políčka
     * @return řádek políčka
     */
    public int getRow();
    
    /**
     * Vrátí sloupec políčka
     * @return sloupec políčka
     */
    public int getCol();
}
