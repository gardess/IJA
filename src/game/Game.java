
package game;

import board.Board;
import board.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Reprezentuje hru. Zná aktuální stav hry (hrací desku a hráče).
 * Pamatuje si historii tahů.
 * @author Tomáš Zahradníček
 */
public class Game {
    private Board board;
    private Player[] players;
    private int onTurn;
    private Stack<Board> undoStack;
    private Stack<Board> redoStack;
    private boolean end;
    private Freezer freezer;
    
    /**
     * Vytvoří hru s prázdnou deskou s a danou velikostí
     * @param size Velikost hrací desky.
     */
    public Game(int size) {
        this(size, null);
    }
    
    /**
     * Vytvoří hru s prázdnou deskou s a danou velikostí a nastavením zamrzání.
     * @param size Velikost hrací desky.
     * @param freezer nastavení zamrzání.
     */
    public Game(int size, Freezer freezer) {
        board = new Board(size);
        players = new Player[2];
        onTurn = 0;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        end = false;
        this.freezer = freezer;
    }
    
    /**
     * Vloží na desku 4 startovní kameny
     */
    public void start() {
       board.start();
    }
    
    /**
     * Přidá a inicializuje hráče. Pokud hráč dané barvy již existuje, nedělá nic.
     * @param player Přidávaný hráč
     * @return Úspěch akce
     */
    public boolean addPlayer(Player player) {
        boolean isWhite = player.isWhite();
        int playerNum = isWhite? 1 : 0;
        if (players[playerNum] != null)
            return false;
        else {
            players[playerNum] = player;
            player.init(this.board.getSize());
            return true;
        }
    }
    
    /**
     * Vrátí aktuálního hráče
     * @return Aktuální hráč.
     */
    public Player currentPlayer() {
        return players[onTurn];
    }
    
    /**
     * Změní aktuálního hráče
     */
    public void nextPlayer() {
        this.onTurn = (this.onTurn == 0) ? 1 : 0;
    }
    
    /**
     * Vrátí aktuální hrací desku.
     * @return Aktuální hrací deska. 
     */
    public Board getBoard() {
        return this.board;
    }
    
    /**
     * Vrátí zda je konec hry
     * @return zda je konec hry
     */
    public boolean isEnd() {
        return this.end;
    }
    
    /**
     * Vrátí hráče této hry s danou barvou
     * @param color Barva hráče (false černá, true bílá).
     * @return Hráč s danou barvou.
     */
    public Player getPlayer(boolean color) {
        return (color) ? players[1] : players[0];
    }
    
    /**
     * Zjistí všechny možné tahy hráče na tahu.
     * @return seznam všech políček kam může hráč zahrát.
     */
    public List<Field> possiblePlays() {
        List<Field> ret = new ArrayList<>();
        if (this.currentPlayer().emptyPool())
            return ret;
        for (Field field: this.board)
            if (this.currentPlayer().canPlayDisk(field))
                ret.add(field);
        return ret;
    }
    
    /**
     * Aktuální hráč zahraje kámen na políčko dané souřadnicemi.
     * Lze vrátit operací undo.
     * @param row Rádek
     * @param col Sloupec.
     * @return Úspěšnost akce.
     */
    public boolean play(int row, int col) {
        if (this.currentPlayer().canPlayDisk(this.getBoard().getField(row, col))) {
            this.redoStack.clear();
            this.undoStack.push(new Board(this.board));
            this.currentPlayer().playDisk(this.getBoard().getField(row,col));
            if (this.freezer != null)
                this.freezer.run(this.board);
            this.nextPlayer();
            if (this.possiblePlays().isEmpty()) {
                // 2. hrac nemuze tahnout, tahne opet ten samy
                this.nextPlayer();
                if (this.possiblePlays().isEmpty()) {
                    // ani jeden hrac nemuze tahnout
                    if (this.freezer == null) {
                        // bez zamrzani je konec hry
                        this.end = true;
                    }
                    else {
                        // se zamrzanim zrusi pro tentokrat jakekoliv zamrznuti
                        this.freezer.unfreeze(this.board);
                        // opakuje se to same
                        this.nextPlayer();
                        if (this.possiblePlays().isEmpty()) {
                            // 2. hrac nemuze tahnout, tahne opet ten samy
                            this.nextPlayer();
                            if (this.possiblePlays().isEmpty()) {
                                // ani jeden hrac nemuze tahnout
                                this.end = true;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean undo() {
        if (this.undoStack.empty())
            return false;
        
        // uloží stávajcí desku
        this.redoStack.push(this.board);
        // načte předchozí
        board = this.undoStack.pop();
        
        if (!end)
            this.nextPlayer();
        if (this.possiblePlays().isEmpty()) {
            this.nextPlayer();
        }
        
        this.end = false;
        // vrati kamen hráči nyní na tahu
        this.currentPlayer().backDisk();
        return true;
    }
    
    public boolean redo() {
        if (this.redoStack.empty())
            return false;
        
        // uloží stávajcí desku
        this.undoStack.push(this.board);
        // načte následujcí
        board = this.redoStack.pop();
        // vezme kámen ze sady hráče na tahu před změnou
        this.currentPlayer().useDisk();
        
        this.nextPlayer();
        if (this.possiblePlays().isEmpty()) {
            // 2. hráč nemůže táhnout, opět změní
            this.nextPlayer();
            if (this.possiblePlays().isEmpty())
                // ani jeden nemůže táhnout, konec hry
                this.end = true;
        }
        return true;
    }
}
