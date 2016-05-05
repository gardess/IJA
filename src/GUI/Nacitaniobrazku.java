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
 *  Nacteni obrazku pro rychlejsi beh
 * @author Milan Gardáš (xgarda04)
 */
public class Nacitaniobrazku {
    
    public static BufferedImage volnePole;
    public static BufferedImage bilyKamen;
    public static BufferedImage cernyKamen;
    public static BufferedImage zamrzlyKamen;
    
    /**
     * Metoda, ktora nacita obrazky a uklada ich do pola pre rychlejsiu pracu s GUI
     * @throws IOException vynimka ak sa nenajdu obrazky
     */
    public Nacitaniobrazku() throws IOException
    {
        Nacitaniobrazku.volnePole = ImageIO.read(new File("C:\\Java\\ija2015\\src\\lib\\img\\pole1.png"));
        Nacitaniobrazku.bilyKamen = ImageIO.read(new File("C:\\Java\\guitest\\src\\guitest\\bilyKamen.png"));
        Nacitaniobrazku.cernyKamen = ImageIO.read(new File("C:\\Java\\guitest\\src\\guitest\\cernyKamen.png"));
        Nacitaniobrazku.zamrzlyKamen = ImageIO.read(new File("C:\\Java\\guitest\\src\\guitest\\zamrznutyKamen.png"));
    }
    
}
