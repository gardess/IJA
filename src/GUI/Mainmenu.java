/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author milan
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 *  Hlavni menu
 * @author Milan Gardáš (xgarda04)
 */
public class Mainmenu extends JFrame
{
    private JFrame okno;
    
    private JButton novaHra;
    private JButton nacistHru;
    private JButton konec;
    
    private JLabel text;
    
    public Mainmenu()
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
        
        novaHra = new JButton("Nová hra");
        novaHra.setBounds(470, 220, 260, 90);
        novaHra.addActionListener((ActionEvent e) -> {
            okno.dispose();
            new Newgame();
        });
        okno.getContentPane().add(novaHra);
        
        nacistHru = new JButton("Načíst hru");
        nacistHru.setBounds(470, 370, 260, 90);
        nacistHru.addActionListener((ActionEvent e) -> {
            okno.dispose();
            //nacteni hry
        });
        okno.getContentPane().add(nacistHru);
        
        konec = new JButton("Konec");
        konec.setBounds(470, 530, 260, 90);
        konec.addActionListener((ActionEvent e) -> {
            okno.dispose();
            //nacteni hry
        });
        okno.getContentPane().add(konec);
    }

    public static void main(String[] args) throws IOException {
        Nacitaniobrazku obr = new Nacitaniobrazku();
        new Mainmenu();
    }
}