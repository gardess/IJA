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
    
    private JButton vloz;
    private JButton konec;
    private JButton uloz;
    private JButton undo;
    private JButton redo;
    
    private JSpinner radek;
    private JSpinner sloupec;
    
    private int pocetRadku = 12;
    private int vlozRadek;
    private int vlozSloupec;
    
    
    // hraci plocha
    private Board deska;
    private Game hra;
    private Field pole;
    
    
    public Hraciplocha(int velikostDesky) throws IOException
    {
        //polickaGui.setLayout(null);
        this.pocetRadku = velikostDesky;
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
        
// vlozeni kamene
        radekS = new JLabel("Řádek");
        radekS.setBounds((60*pocetRadku)+150, 100, 90, 30);
        okno.getContentPane().add(radekS);
        sloupecS = new JLabel("Sloupec");
        sloupecS.setBounds((60*pocetRadku)+250, 100, 90, 30);
        okno.getContentPane().add(sloupecS);
        radek = new JSpinner();
        radek.setBounds((60*pocetRadku)+200, 105, 30, 20);
        okno.getContentPane().add(radek);
        sloupec = new JSpinner();
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
        
        undo = new JButton("UNDO");
        undo.setBounds((60*pocetRadku)+150, 225, 100, 30);
        undo.addActionListener((ActionEvent e) -> {
            //okno.dispose();
            //nacteni hry
        });
        okno.getContentPane().add(undo);
        
        redo = new JButton("REDO");
        redo.setBounds((60*pocetRadku)+300, 225, 100, 30);
        redo.addActionListener((ActionEvent e) -> {
            //okno.dispose();
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
                }
                else
                {
                    picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.cernyKamen));
                    polickaGui[i][j] = new JPanel();
                    polickaGui[i][j].add(picLabel);
                    panel.add(polickaGui[i][j]);
                }
                
            }
        }
        okno.getContentPane().add(panel);
    }
    
    public void updateBoard()
    {
        panel.removeAll();
        
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
                }
                else
                {
                    picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.cernyKamen));
                    polickaGui[i][j] = new JPanel();
                    polickaGui[i][j].add(picLabel);
                    panel.add(polickaGui[i][j]);
                }
                
            }
        }
        okno.getContentPane().add(panel);
        okno.repaint();
        okno.revalidate();
    }
    
}
