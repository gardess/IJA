package Loadsave;

import game.*;
import board.*;
import GUI.Hraciplocha;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;

/**
 * Třida sloužící pro načtení uložené hry
 * @author Milan Gardáš
 */
public class Load {

    private Game hra;
    int pocetRadku;
    int cernyHrac;
    int bilyHrac;
    int zamrzani;
    int I;
    int B;
    int C;
    int naTahu;
    
    /**
     * Konstruktor, který nic nedělá
     * @param soubor Soubor ze kterého se načte hra
     * @throws IOException Pokud selze I/O operace
     */
    public Load(File soubor) throws IOException 
    {

    }
    
    /**
     * Metoda, která načte hru ze souboru
     * @param soubor Soubor ze kterého se načte hra
     * @return Hra s nastavenou deskou
     * @throws IOException Pokud selze I/O operace
     */
    public Game nacti(File soubor) throws IOException
    {
        try(BufferedReader br = new BufferedReader(new FileReader(soubor))) 
        {
            StringBuilder sb = new StringBuilder();
            
            String line = br.readLine();
            pocetRadku = parseInt(line);

            line = br.readLine();
            cernyHrac = parseInt(line);
            
            line = br.readLine();
            bilyHrac = parseInt(line);
            
            line = br.readLine();
            zamrzani = parseInt(line);
            
            line = br.readLine();
            I = parseInt(line);
            
            line = br.readLine();
            B = parseInt(line);
            
            line = br.readLine();
            C = parseInt(line);
            
            line = br.readLine();
            naTahu = parseInt(line);
            
            int pole;
            if(zamrzani == 0)
            {
                hra = new Game(pocetRadku);
            }
            else if(zamrzani == 1)
            {
                hra = new Game(pocetRadku, new Freezer(I,B,C));
            }
            for(int i = 1; i <= pocetRadku; i++)
            {
                for(int j = 1; j <= pocetRadku; j++)
                {
                    line = br.readLine();
                    pole = parseInt(line);
                    if(pole == 1) // vlozeni bileho kamene na hraci desku
                    {
                       hra.getBoard().getField(i, j).putDisk(new Disk(true));            
                    }
                    else if(pole == 2)
                    {
                        hra.getBoard().getField(i, j).putDisk(new Disk(false));
                    }
                    else if(pole == 3)
                    {
                        hra.getBoard().getField(i, j).putDisk(new Disk(true, true));
                    }
                    else if(pole == 4)
                    {
                        hra.getBoard().getField(i, j).putDisk(new Disk(false, true));
                    }
                }
            }
        }
        return hra;
    }
    
    /**
     * Metoda pro zjištění který hráč je na tahu
     * @return Číselná hodnot, která reprezentuje hráče na tahu
     */
    public int getNaTahu()
    {
        return naTahu;
    }

    /**
     * Metoda pro zjištění velikosti hracího pole
     * @return Počet řádků hracího pole
     */
    public int getPocetRadku() {
        return pocetRadku;
    }

    /**
     * Metoda, která zjišťuje typ černého hráče
     * @return Typ černého hráče
     */
    public int getCernyHrac() {
        return cernyHrac;
    }

    /**
     * Metoda, která zjišťuje typ bílého hráče
     * @return Typ bílého hráče
     */
    public int getBilyHrac() {
        return bilyHrac;
    }

    /**
     * Metoda, která zjišťuje zda je zapnuto zamrzání kamenů
     * @return Hodnota reprezentující zda je zamrzání kamenů zapnuto či vypnuto
     */
    public int getZamrzani() {
        return zamrzani;
    }

    /**
     * Metoda pro získání hodnoty parametru I při zamrzání kamenů
     * @return Hodnota parametru I
     */
    public int getI() {
        return I;
    }

    /**
     * Metoda pro získání hodnoty parametru B při zamrzání kamenů
     * @return Hodnota parametru B
     */
    public int getB() {
        return B;
    }

    /**
     * Metoda pro získání hodnoty parametru C při zamrzání kamenů
     * @return Hodnota parametru C
     */
    public int getC() {
        return C;
    }

}
