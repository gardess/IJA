/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Loadsave;

import game.*;
import board.*;
import GUI.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author milan
 */
public class Save {
    
    private int pocetRadku;
    File soubor;
    Board deska;
    Field pole;
    String naTahu;
    
    public Save(Game game, File file) throws FileNotFoundException, UnsupportedEncodingException
    {
        this.pocetRadku = game.getBoard().getSize();
        this.soubor = file;
        this.deska = game.getBoard();
        this.naTahu = game.currentPlayer().toString();
        uloz(this.soubor);
        
    }
    
    public void uloz(File soubor) throws FileNotFoundException, UnsupportedEncodingException 
    {
        try (PrintWriter zapis = new PrintWriter(soubor, "UTF-8")) {
            // velikost hraci desky
            zapis.println(pocetRadku);
            
            // typ cerneho hrace (0 - clovek, 1 - AI1, 2 - AI2)
            zapis.println(0);
            
            // typ bileho hrace (0 - clovek, 1 - AI1, 2 - AI2)
            zapis.println(0);
            
            // zamrzani kamenu (0 - ne, 1 - ano)
            zapis.println(0);
            // zamrzani kamenu parametry IBC
            zapis.println(0); // I
            zapis.println(0); // B
            zapis.println(0); // C
            
            // hrac na tahu (0 - cerny, 1 - bily)
            if(naTahu == "black")
            {
                zapis.println(0);
            }
            else
            {
                zapis.println(1);
            }
            
            // ulozeni hraci desky
            for(int i = 1; i <= pocetRadku; i++)
            {
                for(int j = 1; j <= pocetRadku; j++)
                {
                    pole = deska.getField(i, j);
                    if(pole.getDisk() == null)
                    {
                        zapis.println(0);
                    }
                    else if(pole.getDisk().isWhite() == true)
                    {
                        zapis.println(1);
                    }
                    else
                    {
                        zapis.println(2);
                    }
                }
            }
        }
    }
    
}
