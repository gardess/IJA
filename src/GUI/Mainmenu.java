package GUI;

import Loadsave.Load;
import game.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hlavní menu
 * @author Milan Gardáš
 */
public class Mainmenu extends JFrame
{
    private JFrame okno;
    
    private JButton novaHra;
    private JButton nacistHru;
    private JButton konec;
    
    private JLabel text;
    
    /**
     * Kontruktor pro vytvoření GUI hlavního menu
     */
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
        nacistHru.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(Mainmenu.this) == JFileChooser.APPROVE_OPTION)
                {
                    File soubor = fileChooser.getSelectedFile();
                    System.out.print(soubor);
                    Load nacist;
                    try {
                        nacist = new Load(soubor);
                        
                        Game hra = nacist.nacti(soubor);
                        //okno.dispose();
                        new Hraciplocha(hra, nacist.getNaTahu(), nacist.getCernyHrac(), nacist.getBilyHrac(), nacist.getZamrzani(), nacist.getI(), nacist.getB(), nacist.getC());
                        
                    } catch (IOException ex) {
                        Logger.getLogger(Mainmenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                }
                
            }
        });
        okno.getContentPane().add(nacistHru);
        
        konec = new JButton("Konec");
        konec.setBounds(470, 530, 260, 90);
        konec.addActionListener((ActionEvent e) -> {
            okno.dispose();
            System.exit(0);
        });
        okno.getContentPane().add(konec);
    }

    /**
     * Metoda spouštějící celou hru
     * @param args Parametry příkazové řádky
     * @throws IOException Chyba I/O operace
     */
    public static void main(String[] args) throws IOException {
        Nacitaniobrazku obr = new Nacitaniobrazku();
        new Mainmenu();
    }
}
