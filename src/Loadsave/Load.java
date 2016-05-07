/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author milan
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
    
    public Load(File soubor) throws IOException 
    {
        //nacti(soubor);
    }
    
    /**
     *
     * @param soubor Soubor ze ktereho se nacte hra
     * @return Hra s nastavenou deskou
     * @throws IOException Pokud selze IO operace
     */
    public Game nacti(File soubor) throws IOException
    {
        String everything;
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
    
    public int getNaTahu()
    {
        return naTahu;
    }

    public int getPocetRadku() {
        return pocetRadku;
    }

    public int getCernyHrac() {
        return cernyHrac;
    }

    public int getBilyHrac() {
        return bilyHrac;
    }

    public int getZamrzani() {
        return zamrzani;
    }

    public int getI() {
        return I;
    }

    public int getB() {
        return B;
    }

    public int getC() {
        return C;
    }

}
