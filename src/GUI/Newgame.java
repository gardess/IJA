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
import static java.lang.Integer.parseInt;
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
    private JLabel IText;
    private JLabel BText;
    private JLabel CText;
    private JLabel barva;
    
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
    
    private JRadioButton cerna;
    private JRadioButton bila;
    
    private ButtonGroup bG;
    private ButtonGroup zamrzBG;
    private ButtonGroup protiBG;
    private ButtonGroup barvaBG;
    
    private JTextField I;
    private JTextField B;
    private JTextField C;
    
    private int velikostDesky = 8;
    private boolean zamrzaniB = false;
    private int protihrac;
    private int zamrzaniKamenuI;
    private int barvaV;
    private int II;
    private int BI;
    private int CI;
    
    
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
        velikost.setBounds(330, 150, 300, 40);
        okno.getContentPane().add(velikost);
        
        zamrzani = new JLabel("Zamrzání kamenů");
        zamrzani.setFont(new java.awt.Font("Dodger", 1, 20));
        zamrzani.setBounds(330, 250, 300, 40);
        okno.getContentPane().add(zamrzani);
        
        hrac = new JLabel("Protihráč");
        hrac.setFont(new java.awt.Font("Dodger", 1, 20));
        hrac.setBounds(330, 350, 300, 40);
        okno.getContentPane().add(hrac);
        
        barva = new JLabel("Barva");
        barva.setFont(new java.awt.Font("Dodger", 1, 20));
        barva.setBounds(330, 450, 300, 40);
        okno.getContentPane().add(barva);

// velikost hraci desky        
        v6 = new JRadioButton("6");
        v6.setFont(new java.awt.Font("Century", 1, 18));
        //v6.setText("6");
        v6.setBounds(510, 150, 60, 40);
        //v6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        
        v8 = new JRadioButton("8");
        v8.setFont(new java.awt.Font("Century", 1, 18));
        //v8.setText("8");
        v8.setBounds(580, 150, 60, 40);
        //v8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        
        v10 = new JRadioButton("10");
        v10.setFont(new java.awt.Font("Century", 1, 18));
        //v10.setText("10");
        v10.setBounds(650, 150, 60, 40);
        //v10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        
        v12 = new JRadioButton("12");
        v12.setFont(new java.awt.Font("Century", 1, 18));
        //v12.setText("12");
        v12.setBounds(720, 150, 60, 40);
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
        ne.setBounds(510, 250, 60, 40);
        //v6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        
        ano = new JRadioButton("ANO");
        ano.setFont(new java.awt.Font("Century", 1, 18));
        //v8.setText("8");
        ano.setBounds(580, 250, 80, 40);
        
        zamrzBG = new ButtonGroup();
        zamrzBG.add(ne);
        zamrzBG.add(ano);
        ne.setSelected(true);
        okno.getContentPane().add(ne);
        okno.getContentPane().add(ano);
        
        IText = new JLabel("I:");
        IText.setFont(new java.awt.Font("Dodger", 1, 15));
        IText.setBounds(715, 260, 40, 20);
        okno.getContentPane().add(IText);
        
        I = new JTextField();
        I.setText("0");
        I.setBounds(730, 260, 40, 20);
        okno.getContentPane().add(I);
        
        BText = new JLabel("B:");
        BText.setFont(new java.awt.Font("Dodger", 1, 15));
        BText.setBounds(780, 260, 40, 20);
        okno.getContentPane().add(BText);
        
        B = new JTextField();
        B.setText("0");
        B.setBounds(800, 260, 40, 20);
        okno.getContentPane().add(B);
        
        CText = new JLabel("C:");
        CText.setFont(new java.awt.Font("Dodger", 1, 15));
        CText.setBounds(850, 260, 40, 20);
        okno.getContentPane().add(CText);
        
        C = new JTextField();
        C.setText("0");
        C.setBounds(870, 260, 40, 20);
        okno.getContentPane().add(C);
        
        
// protihrac
        clovek = new JRadioButton("Člověk");
        clovek.setFont(new java.awt.Font("Century", 1, 18));
        //v6.setText("6");
        clovek.setBounds(510, 350, 90, 40);
        //v6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        
        ui1 = new JRadioButton("AI1");
        ui1.setFont(new java.awt.Font("Century", 1, 18));
        //v8.setText("8");
        ui1.setBounds(620, 350, 70, 40);
        
        ui2 = new JRadioButton("AI2");
        ui2.setFont(new java.awt.Font("Century", 1, 18));
        //v8.setText("8");
        ui2.setBounds(690, 350, 90, 40);
        
        protiBG = new ButtonGroup();
        protiBG.add(clovek);
        protiBG.add(ui1);
        protiBG.add(ui2);
        clovek.setSelected(true);
        okno.getContentPane().add(clovek);
        okno.getContentPane().add(ui1);
        okno.getContentPane().add(ui2);

// vyber barvy
        cerna = new JRadioButton("Černá");
        cerna.setFont(new java.awt.Font("Century", 1, 18));
        //v6.setText("6");
        cerna.setBounds(510, 450, 90, 40);
        //v6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        
        bila = new JRadioButton("Bílá");
        bila.setFont(new java.awt.Font("Century", 1, 18));
        //v8.setText("8");
        bila.setBounds(620, 450, 70, 40);
        
        barvaBG = new ButtonGroup();
        barvaBG.add(cerna);
        barvaBG.add(bila);
        cerna.setSelected(true);
        okno.getContentPane().add(cerna);
        okno.getContentPane().add(bila);
        
        /*velikost = new JSlider();
        velikost.setBounds(480, 150, 300, 30);
        velikost.setMaximum(12);
        velikost.setMinimum(6);
        velikost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));*/
        
        //okno.getContentPane().add(velikost);
        
        start = new JButton("Start");
        start.setBounds(835, 530, 260, 90);
        start.addActionListener((ActionEvent e) -> {
            //okno.dispose();
            getVelikost();
            zamrzaniKamenu();
            protihracVolba();
            barvaVolba();
            try {
                // obrazovka se hrou
                
                System.out.println("protihrac: " + protihrac);
                System.out.println("barva: " + barvaV);
                new Hraciplocha(velikostDesky, protihrac, barvaV, zamrzaniKamenuI, II, BI, CI);
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
            System.exit(0);
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
        else if(v10.isSelected())
        {
            velikostDesky = 10;
        }
        else if(v12.isSelected())
        {
            velikostDesky = 12;
        }
    }
    
    public void zamrzaniKamenu()
    {
        if(ne.isSelected())
        {
            zamrzaniKamenuI = 0;
        }
        else if(ano.isSelected())
        {
            zamrzaniKamenuI = 1;
            II = Integer.parseInt(I.getText());
            BI = Integer.parseInt(B.getText());
            CI = Integer.parseInt(C.getText());
        }
    }
    
    public void protihracVolba()
    {
        if(clovek.isSelected())
        {
            protihrac = 0;
        }
        else if(ui1.isSelected())
        {
            protihrac = 1;
        }
        else if(ui2.isSelected())
        {
            protihrac = 2;
        }
    }
    
    public void barvaVolba()
    {
        if(cerna.isSelected())
        {
            barvaV = 0;
        }
        else if(bila.isSelected())
        {
            barvaV = 1;
        }
    }
    
}
