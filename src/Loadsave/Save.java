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
    private int protihrac;
    private int barva;
    private int zamrzani;
    private int I;
    private int B;
    private int C;
    
    public Save(Game game, File file, int protihrac, int barva, int zamrzani, int IParam, int BParam, int CParam) throws FileNotFoundException, UnsupportedEncodingException
    {
        this.pocetRadku = game.getBoard().getSize();
        this.soubor = file;
        this.deska = game.getBoard();
        this.naTahu = game.currentPlayer().toString();
        this.protihrac = protihrac;
        this.barva = barva;
        uloz(this.soubor);
        
    }
    
    public void uloz(File soubor) throws FileNotFoundException, UnsupportedEncodingException 
    {
        try (PrintWriter zapis = new PrintWriter(soubor, "UTF-8")) {
            // velikost hraci desky
            zapis.println(pocetRadku);
            
            // typ cerneho hrace (0 - clovek, 1 - AI1, 2 - AI2)
            if(barva == 0)
            {
                zapis.println(0);
            }
            else if((barva != 0)&&(protihrac == 0))
            {
                zapis.println(0);
            }
            else if((barva != 0)&&(protihrac == 1))
            {
                zapis.println(1);
            }
            else if((barva != 0)&&(protihrac == 2))
            {
                zapis.println(2);
            }
            
            // typ bileho hrace (0 - clovek, 1 - AI1, 2 - AI2)
            if(barva == 1)
            {
                zapis.println(0);
            }
            else if((barva != 1)&&(protihrac == 0))
            {
                zapis.println(0);
            }
            else if((barva != 1)&&(protihrac == 1))
            {
                zapis.println(1);
            }
            else if((barva != 1)&&(protihrac == 2))
            {
                zapis.println(2);
            }
            
            // zamrzani kamenu (0 - ne, 1 - ano)
            if(zamrzani == 0)
            {
                zapis.println(0);
            }
            else if(zamrzani == 1)
            {
                zapis.println(1);
            }
            // zamrzani kamenu parametry IBC
            zapis.println(I); // I
            zapis.println(B); // B
            zapis.println(C); // C
            
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
                        if(pole.getDisk().isFrozen() == true)
                        {
                            zapis.println(3);
                        }
                        else
                        {
                            zapis.println(1);
                        }
                    }
                    else
                    {
                        if(pole.getDisk().isFrozen() == true)
                        {
                            zapis.println(4);
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
    
}
