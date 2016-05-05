/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import game.*;
import board.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *  Obrazovka samotne hry
 * @author Milan Gardáš (xgarda04)
 */
public class Hraciplocha {
    
    private JFrame okno;
    
    private JPanel polickaGui[][];
    private JPanel panel;
    
    private JLabel picLabel;
    private JLabel radekS;
    private JLabel sloupecS;
    private JLabel cerny;
    private JLabel cernySkoreS;
    private JLabel cernyPocet;
    private JLabel bily;
    private JLabel bilySkoreS;
    private JLabel bilyPocet;
    private JLabel pocetKamenuS;
    private JLabel skore;
    private JLabel naTahu;
    
    
    private JButton vloz;
    private JButton konec;
    private JButton uloz;
    private JButton undo;
    private JButton redo;
    
    private JSpinner radek;
    private JSpinner sloupec;
    
    private int pocetRadku;
    private int vlozRadek;
    private int vlozSloupec;
    
    private int bilySkore = 0;
    private int cernySkore = 0;
    private int pocetKamenu;
    
    // hraci plocha
    private Board deska;
    private Game hra;
    private Field pole;
    
    
    public Hraciplocha(int velikostDesky) throws IOException
    {
        //polickaGui.setLayout(null);
        this.pocetRadku = velikostDesky;
        this.pocetKamenu = (this.pocetRadku * this.pocetRadku);
        this.polickaGui = new JPanel[pocetRadku+2][pocetRadku+2];
        
        hra = new Game(pocetRadku);
        hra.addPlayer(new Player(true));
        hra.addPlayer(new Player(false));
        deska = hra.getBoard();
        vytvorGui();
    }
    
    public void vytvorGui() throws IOException
    {

        okno = new JFrame("Othello");
        okno.setVisible(true);
        okno.setSize(1200, 850);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        okno.getContentPane().setLayout(null);
        okno.toFront();
        
        // vypis informaci
        pocetKamenuS = new JLabel("počet kamenů");
        pocetKamenuS.setBounds((60*pocetRadku)+50, 350, 90, 30);
        skore = new JLabel("skóre");
        skore.setBounds((60*pocetRadku)+50, 400, 90, 30);
        naTahu = new JLabel("na tahu hráč " + hra.currentPlayer().toString());
        naTahu.setBounds((60*pocetRadku)+125, 450, 250, 35);
        naTahu.setFont(new java.awt.Font("Dodger", 1, 25));
        okno.getContentPane().add(pocetKamenuS);
        okno.getContentPane().add(skore);
        okno.getContentPane().add(naTahu);
        
        cerny = new JLabel("Black");
        cerny.setBounds((60*pocetRadku)+150, 300, 90, 30);
        cerny.setFont(new java.awt.Font("Dodger", 1, 20));
        cernySkoreS = new JLabel("2");
        cernySkoreS.setBounds((60*pocetRadku)+170, 400, 90, 30);
        cernyPocet = new JLabel(Integer.toString(pocetKamenu));
        cernyPocet.setBounds((60*pocetRadku)+170, 350, 90, 30);
        bily = new JLabel("White");
        bily.setBounds((60*pocetRadku)+300, 300, 90, 30);
        bily.setFont(new java.awt.Font("Dodger", 1, 20));
        bilySkoreS = new JLabel("2");
        bilySkoreS.setBounds((60*pocetRadku)+320, 400, 90, 30);
        bilyPocet = new JLabel(Integer.toString(pocetKamenu));
        bilyPocet.setBounds((60*pocetRadku)+320, 350, 90, 30);
        
        okno.getContentPane().add(cerny);
        okno.getContentPane().add(cernySkoreS);
        okno.getContentPane().add(cernyPocet);
        okno.getContentPane().add(bily);
        okno.getContentPane().add(bilySkoreS);
        okno.getContentPane().add(bilyPocet);
        
        
        SpinnerModel sm1 = new SpinnerNumberModel(1,1,pocetRadku,1);
        SpinnerModel sm2 = new SpinnerNumberModel(1,1,pocetRadku,1);
        
// vlozeni kamene
        radekS = new JLabel("Řádek");
        radekS.setBounds((60*pocetRadku)+150, 100, 90, 30);
        okno.getContentPane().add(radekS);
        sloupecS = new JLabel("Sloupec");
        sloupecS.setBounds((60*pocetRadku)+250, 100, 90, 30);
        okno.getContentPane().add(sloupecS);
        radek = new JSpinner(sm1);
        radek.setBounds((60*pocetRadku)+200, 105, 30, 20);
        okno.getContentPane().add(radek);
        sloupec = new JSpinner(sm2);
        sloupec.setBounds((60*pocetRadku)+300, 105, 30, 20);
        okno.getContentPane().add(sloupec);
        
        vloz = new JButton("Vlož kámen");
        vloz.setBounds((60*pocetRadku)+350, 105, 100, 20);
        vloz.addActionListener((ActionEvent e) -> {
            //okno.dispose();
            //nacteni hry
            vlozRadek = ((Integer) radek.getValue());
            vlozSloupec = ((Integer) sloupec.getValue());
            
            //System.out.println("vlozil jsem kamen na: " + vlozRadek + vlozSloupec);
            hra.play(vlozRadek,vlozSloupec);
            updateBoard();
        });
        okno.getContentPane().add(vloz);
        
        uloz = new JButton("Ulož hru");
        uloz.setBounds((60*pocetRadku)+150, 25, 100, 30);
        uloz.addActionListener((ActionEvent e) -> {
            //okno.dispose();
            //nacteni hry
        });
        okno.getContentPane().add(uloz);
        
        konec = new JButton("Konec hry");
        konec.setBounds((60*pocetRadku)+300, 25, 100, 30);
        konec.addActionListener((ActionEvent e) -> {
            okno.dispose();
        });
        okno.getContentPane().add(konec);
        
// undo
        undo = new JButton("UNDO");
        undo.setBounds((60*pocetRadku)+150, 225, 100, 30);
        undo.addActionListener((ActionEvent e) -> {
            hra.undo();
            deska = hra.getBoard();
            updateBoard();
        });
        okno.getContentPane().add(undo);
        
        redo = new JButton("REDO");
        redo.setBounds((60*pocetRadku)+300, 225, 100, 30);
        redo.addActionListener((ActionEvent e) -> {
            hra.redo();
            deska = hra.getBoard();
            updateBoard();
        });
        okno.getContentPane().add(redo);
        
        GridLayout lay = new GridLayout(pocetRadku, pocetRadku, 0, 0);
        lay.setHgap(0);
        lay.setVgap(0);
        
        panel = new JPanel();
        panel.setSize(pocetRadku*65, pocetRadku*65);
        panel.setLayout(lay);
        okno.add(panel);

        
        //System.out.println("TEST 1");
        for(int i = 1; i < pocetRadku+1; i++)
        {
            for(int j = 1; j < pocetRadku+1; j++)
            {
                pole = deska.getField(i, j);
                if(pole.getDisk() == null)
                {
                    picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.volnePole));
                    polickaGui[i][j] = new JPanel();
                    polickaGui[i][j].add(picLabel);
                    panel.add(polickaGui[i][j]);
                }
                else if(pole.getDisk().isWhite() == true)
                {
                    picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.bilyKamen));
                    polickaGui[i][j] = new JPanel();
                    polickaGui[i][j].add(picLabel);
                    panel.add(polickaGui[i][j]);
                    bilySkore++;
                }
                else
                {
                    picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.cernyKamen));
                    polickaGui[i][j] = new JPanel();
                    polickaGui[i][j].add(picLabel);
                    panel.add(polickaGui[i][j]);
                    cernySkore++;
                }
                
            }
        }
        okno.getContentPane().add(panel);
    }
    
    public void updateBoard()
    {
        
        panel.removeAll();
        bilySkore = 0;
        cernySkore = 0;
        for(int i = 1; i < pocetRadku+1; i++)
        {
            for(int j = 1; j < pocetRadku+1; j++)
            {
                pole = deska.getField(i, j);
                if(pole.getDisk() == null)
                {
                    picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.volnePole));
                    polickaGui[i][j] = new JPanel();
                    polickaGui[i][j].add(picLabel);
                    panel.add(polickaGui[i][j]);
                }
                else if(pole.getDisk().isWhite() == true)
                {
                    picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.bilyKamen));
                    polickaGui[i][j] = new JPanel();
                    polickaGui[i][j].add(picLabel);
                    panel.add(polickaGui[i][j]);
                    bilySkore++;
                }
                else
                {
                    picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.cernyKamen));
                    polickaGui[i][j] = new JPanel();
                    polickaGui[i][j].add(picLabel);
                    panel.add(polickaGui[i][j]);
                    cernySkore++;
                }
                
            }
        }
        bilySkoreS.setText((Integer.toString(bilySkore)));
        cernySkoreS.setText((Integer.toString(cernySkore)));
        bilyPocet.setText((Integer.toString(pocetKamenu-bilySkore)));
        cernyPocet.setText((Integer.toString(pocetKamenu-cernySkore)));
        naTahu.setText("na tahu hráč " + hra.currentPlayer().toString());
        okno.getContentPane().add(panel);
        okno.repaint();
        okno.revalidate();
        konecHry();
    }
    
    public void konecHry()
    {
        if(hra.isEnd() == true)
        {
            okno.getContentPane().removeAll();
            JLabel vitez;
            JPanel vitezP = new JPanel();
            vitezP.setBackground(Color.red);
            vitezP.setBounds(0, 0, 1200, 850);
            vitezP.setLayout(null);
            if(bilySkore > cernySkore)
            {
                vitez = new JLabel("Vítězem je hráč White se skóre " + Integer.toString(bilySkore));
            }
            else if(bilySkore < cernySkore)
            {
                vitez = new JLabel("Vítězem je hráč Black se skóre " + Integer.toString(cernySkore));
            }
            else
            {
                vitez = new JLabel("Remíza");
            }
            vitez.setBackground(Color.red);
            vitez.setFont(new java.awt.Font("Dodger", 1, 50));
            vitez.setBounds(150, 250, 1000, 100);
            vitezP.add(konec);
            konec.setBounds(290, 400, 260, 90);
            JButton menu = new JButton("Hlavní menu");
            menu.setBounds(650, 400, 260, 90);
            menu.addActionListener((ActionEvent e) -> {
                okno.dispose();
                new Mainmenu();
            });
            vitezP.add(menu);
            vitezP.add(vitez);
            
            okno.getContentPane().add(vitezP);
            okno.repaint();
            okno.revalidate();
        }
    }
}
