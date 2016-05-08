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
     * Obrázek bílého kamene
     */
    public static BufferedImage bilyKamen;

    /**
     * Obrázek černého kamene
     */
    public static BufferedImage cernyKamen;

    /**
     * Obrázek zamrzlého kamene
     */
    public static BufferedImage zamrzlyKamen;
    
    /**
     * Metoda pro načtání obrázků
     * @throws IOException Při nenalezení obrázků
     */
    public Nacitaniobrazku() throws IOException
    {
        Nacitaniobrazku.volnePole = ImageIO.read(new File("lib/img/pole1.png"));
        Nacitaniobrazku.bilyKamen = ImageIO.read(new File("lib/img/bilyKamen.png"));
        Nacitaniobrazku.cernyKamen = ImageIO.read(new File("lib/img/cernyKamen.png"));
        Nacitaniobrazku.zamrzlyKamen = ImageIO.read(new File("lib/img/zamrznutyKamen.png"));
    }
    
}
