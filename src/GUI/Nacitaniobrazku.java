/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *  Načtení obrazků pro rychlejší běh programu
 * @author Milan Gardáš (xgarda04)
 */
public class Nacitaniobrazku {
    
    public static BufferedImage volnePole;
    public static BufferedImage bilyKamen;
    public static BufferedImage cernyKamen;
    public static BufferedImage zamrzlyKamen;
    
    /**
     * Metoda, která načte obrázky
     * @throws IOException vyjímka při nenalezení obrázku
     */
    public Nacitaniobrazku() throws IOException
    {
        Nacitaniobrazku.volnePole = ImageIO.read(new File("lib/img/pole1.png"));
        Nacitaniobrazku.bilyKamen = ImageIO.read(new File("lib/img/bilyKamen.png"));
        Nacitaniobrazku.cernyKamen = ImageIO.read(new File("lib/img/cernyKamen.png"));
        Nacitaniobrazku.zamrzlyKamen = ImageIO.read(new File("lib/img/zamrznutyKamen.png"));
    }
    
}
