package GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Načtení obrázků pro rychlejší běh aplikace
 * @author Milan Gardáš
 */
public class Nacitaniobrazku {
    
    /**
     * Obrázek volného pole
     */
    public static BufferedImage volnePole;

    /**
     * Obrázek pole na které je možné vložit svůj kámen
     */
    public static BufferedImage moznePole;

    /**
     * Obrázek bílého kamene
     */
    public static BufferedImage bilyKamen;

    /**
     * Obrázek černého kamene
     */
    public static BufferedImage cernyKamen;

    /**
     * Obrázek zamrzlého bílého kamene
     */
    public static BufferedImage zamrzlyBilyKamen;
    
    /**
     * Obrázek zamrzlého černého kamene
     */
    public static BufferedImage zamrzlyCernyKamen;
    
    /**
     * Metoda pro načtání obrázků
     * @throws IOException Při nenalezení obrázků
     */
    public Nacitaniobrazku() throws IOException
    {
        Nacitaniobrazku.volnePole = ImageIO.read(new File("lib/img/pole1.png"));
        Nacitaniobrazku.moznePole = ImageIO.read(new File("lib/img/moznePole.png"));
        Nacitaniobrazku.bilyKamen = ImageIO.read(new File("lib/img/bilyKamen.png"));
        Nacitaniobrazku.cernyKamen = ImageIO.read(new File("lib/img/cernyKamen.png"));
        Nacitaniobrazku.zamrzlyBilyKamen = ImageIO.read(new File("lib/img/bilyZamrznutyKamen.png"));
        Nacitaniobrazku.zamrzlyCernyKamen = ImageIO.read(new File("lib/img/cernyZamrznutyKamen.png"));
    }
    
}
