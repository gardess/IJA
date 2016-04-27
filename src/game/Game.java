
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
    
    /**
     * Inicializuje hru s danou velikostí hrací desky.
     * @param size Velikost hrací desky.
     */
    public Game(int size) {
        this.board = new Board(size);
        players = new Player[2];
        onTurn = 0;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        end = false;
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
     * @param row Řádek
     * @param col Sloupec.
     * @return Úspěšnost akce.
     */
    public boolean play(int row, int col) {
        if (this.currentPlayer().canPlayDisk(this.getBoard().getField(row, col))) {
            this.redoStack.clear();
            this.undoStack.push(new Board(board));
            this.currentPlayer().playDisk(this.getBoard().getField(row,col));
            this.nextPlayer();
            if (this.possiblePlays().isEmpty()) {
                this.nextPlayer();
                if (this.possiblePlays().isEmpty())
                    this.end = true;
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
