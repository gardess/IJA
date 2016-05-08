package GUI;

import game.*;
import board.*;
import Loadsave.*;
import playerControl.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

/**
 * Obrazovka samotne hry
 * @author Milan Gardáš
 */
public class Hraciplocha {
    
    private JFrame okno;
    
    private JPanel polickaGui[][];
    private JPanel panel;
    
    private JLabel picLabel;
    private JLabel cerny;
    private JLabel cernySkoreS;
    private JLabel cernyPocet;
    private JLabel bily;
    private JLabel bilySkoreS;
    private JLabel bilyPocet;
    private JLabel pocetKamenuS;
    private JLabel skore;
    private JLabel naTahu;
    
    private JButton konec;
    private JButton uloz;
    private JButton undo;
    private JButton redo;
    
    private int pocetRadku;
    
    // hraci plocha
    private Board deska;
    private Game hra;
    private Field pole;
    
    private int protihrac;
    private int barvaHrace;
    
    private int zamrzani;
    private int I;
    private int B;
    private int C;
    
    /**
     * Konstruktor pro založení nové hry
     * @param velikostDesky Velikost desky získaná od uživatele
     * @param protihrac Typ protihráče
     * @param barva Barva vybraná hráčem
     * @param zamrzani Nastavení zamrzání kamenů
     * @param IParam Doba než se zablokují kameny
     * @param BParam Maximální doba po kterou jsou kameny zablokovány
     * @param CParam Počet zablokovaných kamenů
     */
    public Hraciplocha(int velikostDesky, int protihrac, int barva, int zamrzani, int IParam, int BParam, int CParam) 
    {
        //polickaGui.setLayout(null);
        this.pocetRadku = velikostDesky;
        this.polickaGui = new JPanel[pocetRadku+2][pocetRadku+2];
        if(zamrzani == 1)
        {
            hra = new Game(pocetRadku, new Freezer(IParam,BParam,CParam));
            this.zamrzani = zamrzani;
            this.I = IParam;
            this.B = BParam;
            this.C = CParam;
        }
        else if(zamrzani == 0)
        {
            hra = new Game(pocetRadku);
        }
        this.protihrac = protihrac;
        this.barvaHrace = barva;
        
        if(barva == 0)
        {
            hra.addPlayer(new Player(false, new Human()));
            
            if(protihrac == 0)
            {
                hra.addPlayer(new Player(true, new Human()));
            }
            else if(protihrac == 1)
            {
                hra.addPlayer(new Player(true, new ComputerRandom()));
            }
            else if(protihrac == 2) // druha umela inteligence
            { 
                hra.addPlayer(new Player(true, new ComputerMinMax()));
            }
        }
        else if(barva == 1)
        {
            hra.addPlayer(new Player(true, new Human()));
            if(protihrac == 0)
            {
                hra.addPlayer(new Player(false, new Human()));
            }
            else if(protihrac == 1)
            {
                hra.addPlayer(new Player(false, new ComputerRandom()));
            }
            else if(protihrac == 2) // druha umela inteligence
            { 
                hra.addPlayer(new Player(false, new ComputerMinMax()));
            }
        }
        deska = hra.getBoard();
        
        
        hra.start();
        
        try {
            vytvorGui();
        } catch (IOException ex) {
            Logger.getLogger(Hraciplocha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // konstruktor pro nacteni hry

    /**
     * Konstruktor pro načtenou hru
     * @param game Načtená hra s nastavenou deskou
     * @param tah Určuje, který hráč je na tahu
     * @param cernyHrac Typ černého hráče
     * @param bilyHrac Typ bílého hráče
     * @param zamrzani Nastavení zamrzání kamenů
     * @param IParam Doba než se zablokují kameny
     * @param BParam Maximální doba po kterou jsou kameny zablokovány
     * @param CParam Počet zablokovaných kamenů
     */
    public Hraciplocha(Game game, int tah, int cernyHrac, int bilyHrac, int zamrzani, int IParam, int BParam, int CParam)
    {
        this.hra = game;
        this.deska = hra.getBoard();
        if(cernyHrac == 0)
        {
            hra.addPlayer(new Player(false, new Human()));
        }
        else if(cernyHrac == 1)
        {
            hra.addPlayer(new Player(false, new ComputerRandom()));
        }
        else if(cernyHrac == 2)
        {
            hra.addPlayer(new Player(false, new ComputerMinMax()));
        }
        
        if(bilyHrac == 0)
        {
            hra.addPlayer(new Player(true, new Human()));
        }
        else if(bilyHrac == 1)
        {
            hra.addPlayer(new Player(true, new ComputerRandom()));
        }
        else if(bilyHrac == 2)
        {
            hra.addPlayer(new Player(true, new ComputerMinMax()));
        }
        
        this.zamrzani = zamrzani;
        this.I = IParam;
        this.B = BParam;
        this.C = CParam;
        
        this.pocetRadku = this.deska.getSize();
        this.polickaGui = new JPanel[pocetRadku+2][pocetRadku+2];
        if(tah == 1)
        {
            game.nextPlayer();
        }
        try {
            vytvorGui();
        } catch (IOException ex) {
            Logger.getLogger(Hraciplocha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metoda pro vytvoření GUI pro danou hru
     * @throws IOException Nezdařená I/O operace
     */
    public void vytvorGui() throws IOException
    {

        okno = new JFrame("Othello");
        okno.setVisible(true);
        okno.setSize(1200, 850);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        okno.getContentPane().setLayout(null);
        okno.toFront();
        
        // vypis informaci
        pocetKamenuS = new JLabel("zbývá kamenů");
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
        cernyPocet = new JLabel(Integer.toString(hra.getPlayer(false).poolSize()));
        cernyPocet.setBounds((60*pocetRadku)+170, 350, 90, 30);
        bily = new JLabel("White");
        bily.setBounds((60*pocetRadku)+300, 300, 90, 30);
        bily.setFont(new java.awt.Font("Dodger", 1, 20));
        bilySkoreS = new JLabel("2");
        bilySkoreS.setBounds((60*pocetRadku)+320, 400, 90, 30);
        bilyPocet = new JLabel(Integer.toString(hra.getPlayer(true).poolSize()));
        bilyPocet.setBounds((60*pocetRadku)+320, 350, 90, 30);
        
        okno.getContentPane().add(cerny);
        okno.getContentPane().add(cernySkoreS);
        okno.getContentPane().add(cernyPocet);
        okno.getContentPane().add(bily);
        okno.getContentPane().add(bilySkoreS);
        okno.getContentPane().add(bilyPocet);
        
        uloz = new JButton("Ulož hru");
        uloz.setBounds((60*pocetRadku)+150, 25, 100, 30);
        uloz.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
              File soubor = fileChooser.getSelectedFile();
              System.out.print(soubor);
              Save ulozit;
                try {
                    ulozit = new Save(hra, soubor, protihrac, barvaHrace, zamrzani, I, B, C);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Hraciplocha.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Hraciplocha.class.getName()).log(Level.SEVERE, null, ex);
                }
                /*try {
                    ulozenie.uloz(subor);
                } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                    Logger.getLogger(HlavneGUI.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
//new Save(pocetRadku, "temp.save", deska);
        });
        okno.getContentPane().add(uloz);
        
        konec = new JButton("Konec hry");
        konec.setBounds((60*pocetRadku)+300, 25, 100, 30);
        konec.addActionListener((ActionEvent e) -> {
            okno.dispose();
            //System.exit(0);
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

        
        // mozne tahy
        java.util.List<Field> mozneTahy = hra.possiblePlays();
        Field policko;
        int velikost = mozneTahy.size();
        tahy posiblePlay[] = new tahy[velikost+1];
        for(int i = 0; i < velikost; i++)
        {
            policko = mozneTahy.get(i);
            posiblePlay[i] = new tahy(policko.getRow(),policko.getCol());
            
        }
        int k = 0;
        
        for(int i = 1; i < pocetRadku+1; i++)
        {
            for(int j = 1; j < pocetRadku+1; j++)
            {
                pole = deska.getField(i, j);
                polickaGui[i][j] = new JPanel();
                polickaGui[i][j].addMouseListener(new FieldListener(i, j));
                if(pole.getDisk() == null)
                {
                    if((k != velikost) && ((posiblePlay[k].row == i) && (posiblePlay[k].col == j)))
                    {
                        picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.moznePole));
                        polickaGui[i][j].add(picLabel);
                        panel.add(polickaGui[i][j]);
                        k++;
                        System.out.println("k: " + k);
                    }
                    else
                    {
                        picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.volnePole));
                        polickaGui[i][j].add(picLabel);
                        panel.add(polickaGui[i][j]);
                    }
                }
                else if(pole.getDisk().isWhite() == true)
                {
                    if(pole.getDisk().isFrozen() == true)
                    {
                        picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.zamrzlyKamen));
                        polickaGui[i][j].add(picLabel);
                        panel.add(polickaGui[i][j]);
                    }
                    else
                    {
                        
                        picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.bilyKamen));
                        polickaGui[i][j].add(picLabel);
                        panel.add(polickaGui[i][j]);
                    }
                }
                else
                {
                    if(pole.getDisk().isFrozen() == true)
                    {
                        picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.zamrzlyKamen));
                        polickaGui[i][j].add(picLabel);
                        panel.add(polickaGui[i][j]);
                    }
                    else
                    {
                        picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.cernyKamen));
                        polickaGui[i][j].add(picLabel);
                        panel.add(polickaGui[i][j]);
                    }
                }
                
            }
        }
        okno.getContentPane().add(panel);
    }
    
    /**
     * Metoda pro aktualizaci GUI hrací desky
     */
    public void updateBoard()
    {
        
        panel.removeAll();
        java.util.List<Field> mozneTahy = hra.possiblePlays();
        Field policko;
        int velikost = mozneTahy.size();
        tahy posiblePlay[] = new tahy[velikost+1];
        for(int i = 0; i < velikost; i++)
        {
            policko = mozneTahy.get(i);
            posiblePlay[i] = new tahy(policko.getRow(),policko.getCol());
            
        }
        int k = 0;
        
        for(int i = 1; i < pocetRadku+1; i++)
        {
            for(int j = 1; j < pocetRadku+1; j++)
            {
                pole = deska.getField(i, j);
                polickaGui[i][j] = new JPanel();
                polickaGui[i][j].addMouseListener(new FieldListener(i, j));
                if(pole.getDisk() == null)
                {
                    if((k != velikost) && ((posiblePlay[k].row == i) && (posiblePlay[k].col == j)))
                    {
                        picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.moznePole));
                        polickaGui[i][j].add(picLabel);
                        panel.add(polickaGui[i][j]);
                        k++;
                        System.out.println("k: " + k);
                    }
                    else
                    {
                        picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.volnePole));
                        polickaGui[i][j].add(picLabel);
                        panel.add(polickaGui[i][j]);
                    }
                }
                else if(pole.getDisk().isWhite() == true)
                {
                    if(pole.getDisk().isFrozen() == true)
                    {
                        picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.zamrzlyKamen));
                        polickaGui[i][j].add(picLabel);
                        panel.add(polickaGui[i][j]);
                    }
                    else
                    {
                        
                        picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.bilyKamen));
                        polickaGui[i][j].add(picLabel);
                        panel.add(polickaGui[i][j]);
                    }
                }
                else
                {
                    if(pole.getDisk().isFrozen() == true)
                    {
                        picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.zamrzlyKamen));
                        polickaGui[i][j].add(picLabel);
                        panel.add(polickaGui[i][j]);
                    }
                    else
                    {
                        picLabel = new JLabel(new ImageIcon(Nacitaniobrazku.cernyKamen));
                        polickaGui[i][j].add(picLabel);
                        panel.add(polickaGui[i][j]);
                    }
                }
                
            }
        }
        bilySkoreS.setText((Integer.toString(hra.score(true))));
        cernySkoreS.setText((Integer.toString(hra.score(false))));
        bilyPocet.setText((Integer.toString(hra.getPlayer(true).poolSize())));
        cernyPocet.setText((Integer.toString(hra.getPlayer(false).poolSize())));
        naTahu.setText("na tahu hráč " + hra.currentPlayer().toString());
        okno.getContentPane().add(panel);
        okno.repaint();
        okno.revalidate();
        konecHry();
    }
    
    /**
     * Metoda kontrolující zda nenastal konec hry
     */
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
            if(hra.score(true) > hra.score(false))
            {
                vitez = new JLabel("Vítězem je hráč White se skóre " + Integer.toString(hra.score(true)));
            }
            else if(hra.score(true) < hra.score(false))
            {
                vitez = new JLabel("Vítězem je hráč Black se skóre " + Integer.toString(hra.score(false)));
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
    
    /**
     * Metoda pro zjištění počtu řádků hrací desky
     * @return Počet řádků hrací desky
     */
    public int radkuPocet()
    {
        return this.pocetRadku;
    }

    /**
     * Trida pro sledovani klikani na policka.
     */
    private class FieldListener extends MouseAdapter {

        private int row;
        private int col;
        private boolean pressed;
        
        public FieldListener(int row, int col) {
            this.col = col;
            this.row = row;
            this.pressed = false;
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            if (pressed) {
                hra.play(row, col);
                updateBoard();
                pressed = false;
            }
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            pressed = false;
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            pressed = true;
        }
    }
    
    /**
     * Třída reprezentující možné tahy hráče
     */
    public class tahy{

        /**
         * Rádková souřadnice tahu
         */
        public int row;

        /**
         * Sloupcová souřadnice tahu
         */
        public int col;
        
        /**
         * Konstruktor pro uložení souřadnic tahu
         * @param row Rádková souřadnice tahu
         * @param col Sloupcová souřadnice tahu
         */
        public tahy(int row, int col)
        {
            this.row = row;
            this.col = col;
        }
    }
}


