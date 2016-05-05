/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Obrazovka pro nastaveni vlastnosti hry
 * @author Milan Gardáš (xgarda04)
 */
public class Newgame extends JFrame{
    
    private JFrame okno;
    
    private JButton start;
    private JButton zpet;
    private JButton konec;
    
    private JLabel text;
    private JLabel velikost;
    private JLabel zamrzani;
    private JLabel hrac;
    
    //private JSlider velikost;
    
    private JRadioButton v6;
    private JRadioButton v8;
    private JRadioButton v10;
    private JRadioButton v12;
    
    private JRadioButton ano;
    private JRadioButton ne;
    
    private JRadioButton clovek;
    private JRadioButton ui1;
    private JRadioButton ui2;
    
    private ButtonGroup bG;
    private ButtonGroup zamrzBG;
    private ButtonGroup protiBG;
    
    private int velikostDesky = 8;
    private boolean zamrzaniB = false;
    private int protihrac = 0;
    
    public Newgame()
    {
        okno = new JFrame("Othello");
        okno.setVisible(true);
        okno.setSize(1200, 750);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        okno.getContentPane().setLayout(null);
        okno.toFront();
        
        text = new JLabel("Othello");
        text.setFont(new java.awt.Font("Dodger", 1, 70));
        text.setBounds(480, 30, 300, 90);
        okno.getContentPane().add(text);
        
        velikost = new JLabel("Velikost desky");
        velikost.setFont(new java.awt.Font("Dodger", 1, 20));
        velikost.setBounds(330, 200, 300, 40);
        okno.getContentPane().add(velikost);
        
        zamrzani = new JLabel("Zamrzání kamenů");
        zamrzani.setFont(new java.awt.Font("Dodger", 1, 20));
        zamrzani.setBounds(330, 300, 300, 40);
        okno.getContentPane().add(zamrzani);
        
        hrac = new JLabel("Protihráč");
        hrac.setFont(new java.awt.Font("Dodger", 1, 20));
        hrac.setBounds(330, 400, 300, 40);
        okno.getContentPane().add(hrac);

// velikost hraci desky        
        v6 = new JRadioButton("6");
        v6.setFont(new java.awt.Font("Century", 1, 18));
        //v6.setText("6");
        v6.setBounds(510, 200, 60, 40);
        //v6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        
        v8 = new JRadioButton("8");
        v8.setFont(new java.awt.Font("Century", 1, 18));
        //v8.setText("8");
        v8.setBounds(580, 200, 60, 40);
        //v8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        
        v10 = new JRadioButton("10");
        v10.setFont(new java.awt.Font("Century", 1, 18));
        //v10.setText("10");
        v10.setBounds(650, 200, 60, 40);
        //v10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        
        v12 = new JRadioButton("12");
        v12.setFont(new java.awt.Font("Century", 1, 18));
        //v12.setText("12");
        v12.setBounds(720, 200, 60, 40);
        //v12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        
        bG = new ButtonGroup();
        bG.add(v6);
        bG.add(v8);
        bG.add(v10);
        bG.add(v12);
        v8.setSelected(true);
        okno.getContentPane().add(v6);
        okno.getContentPane().add(v8);
        okno.getContentPane().add(v10);
        okno.getContentPane().add(v12);
        //this.setVisible(true);
        //okno.getContentPane().add(bG);
        
// zamrzani kamenu
        ne = new JRadioButton("NE");
        ne.setFont(new java.awt.Font("Century", 1, 18));
        //v6.setText("6");
        ne.setBounds(510, 300, 60, 40);
        //v6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        
        ano = new JRadioButton("ANO");
        ano.setFont(new java.awt.Font("Century", 1, 18));
        //v8.setText("8");
        ano.setBounds(580, 300, 80, 40);
        
        zamrzBG = new ButtonGroup();
        zamrzBG.add(ne);
        zamrzBG.add(ano);
        ne.setSelected(true);
        okno.getContentPane().add(ne);
        okno.getContentPane().add(ano);
        
// protihrac
        clovek = new JRadioButton("Člověk");
        clovek.setFont(new java.awt.Font("Century", 1, 18));
        //v6.setText("6");
        clovek.setBounds(510, 400, 90, 40);
        //v6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        
        ui1 = new JRadioButton("AI1");
        ui1.setFont(new java.awt.Font("Century", 1, 18));
        //v8.setText("8");
        ui1.setBounds(620, 400, 70, 40);
        
        ui2 = new JRadioButton("AI2");
        ui2.setFont(new java.awt.Font("Century", 1, 18));
        //v8.setText("8");
        ui2.setBounds(690, 400, 90, 40);
        
        protiBG = new ButtonGroup();
        protiBG.add(clovek);
        protiBG.add(ui1);
        protiBG.add(ui2);
        clovek.setSelected(true);
        okno.getContentPane().add(clovek);
        okno.getContentPane().add(ui1);
        okno.getContentPane().add(ui2);

        
        
        /*velikost = new JSlider();
        velikost.setBounds(480, 150, 300, 30);
        velikost.setMaximum(12);
        velikost.setMinimum(6);
        velikost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));*/
        
        //okno.getContentPane().add(velikost);
        
        start = new JButton("Start");
        start.setBounds(835, 530, 260, 90);
        start.addActionListener((ActionEvent e) -> {
            okno.dispose();
            getVelikost();
            try {
                // obrazovka se hrou
                new Hraciplocha(velikostDesky);
            } catch (IOException ex) {
                Logger.getLogger(Newgame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        okno.getContentPane().add(start);
        
        zpet = new JButton("Zpět");
        zpet.setBounds(105, 530, 260, 90);
        zpet.addActionListener((ActionEvent e) -> {
            okno.dispose();
            new Mainmenu();
        });
        okno.getContentPane().add(zpet);
        
        konec = new JButton("Konec");
        konec.setBounds(470, 530, 260, 90);
        konec.addActionListener((ActionEvent e) -> {
            okno.dispose();
        });
        okno.getContentPane().add(konec);
    }
    
    public void getVelikost()
    {
        if(v6.isSelected())
        {
            velikostDesky = 6;
        }
        else if(v8.isSelected())
        {
            velikostDesky = 8;
        }
        if(v10.isSelected())
        {
            velikostDesky = 10;
        }
        if(v12.isSelected())
        {
            velikostDesky = 12;
        }
    }
    
}
