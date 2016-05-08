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
    
    /** Zkopíruje hru. Nová hra má stejnou aktualní desku a hráče,
     * ale nekopíruje se historie tahů a zamrzávání kamenů je vypnuto.
     * @param game Původní hra.
     */
    public Game(Game game) {
        this.board = new Board(game.board);
        this.players = new Player[2];
        this.players[0] = new Player(game.players[0]);
        this.players[1] = new Player(game.players[1]);
        this.onTurn = game.onTurn;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.end = game.end;
        this.freezer = null;
    }
    
    /**
     * Vloží na desku 4 startovní kameny, a pokud začíná AI, nechá ho táhnout.
     */
    public void start() {
       board.start();
       while (playAI()) {}
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
     * Lze vrátit operací undo1.
     * @param row Rádek od 1 do velikosti desky (včetně).
     * @param col Sloupec od 1 do velikosti desky (včetně).
     * @return Úspěšnost akce.
     * @throws IndexOutOfBoundsException pokud se zadané políčko nenachází na hrací desce.
     */
    public boolean play1(int row, int col) {
        if (row < 1 || row > board.getSize() || col < 1 || col > board.getSize())
            throw new IndexOutOfBoundsException("Field is not in board");
        
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
    
    /**
     * Nechá táhnout hráče ovládaného AI.
     * @return false, pokud na tahu není hráč ovládaný AI.
     */
    public boolean playAI() {
        if (!this.currentPlayer().getControl().isAI() || isEnd())
            return false;
        
        Field field = this.currentPlayer().getControl().getNextMove(this);
        play1(field.getRow(), field.getCol());
        return true;
    }
    
    /**
     * Tah hráče člověka. Po provedení tahu nechá táhnout AI, pokud je na tahu.
     * Lze vrátit operací undo.
     * @param row Rádek od 1 do velikosti desky (včetně).
     * @param col Sloupec od 1 do velikosti desky (včetně).
     * @return Úspěšnost tahu.
     * @throws IndexOutOfBoundsException pokud se zadané políčko nenachází na hrací desce.
     */
    public boolean play(int row, int col) {
        if (play1(row, col)) {
            while (this.currentPlayer().getControl().isAI())
                playAI();
            return true;
        }
        return false;
    }
    
    /**
     * Zjistí zda je možné provést operaci undo
     * @return true, pokud operace undo změní hrací desku.
     */
    public boolean undoable() {
        return !undoStack.empty();
    }
    
    /**
     * Zjistí zda je možné provést operaci redo
     * @return true, pokud operace redo změní hrací desku.
     */
    public boolean redoable() {
        return !redoStack.empty();
    }
    
    /**
     * Vrátí 1 tah zpět. Změní aktuální hrací desku. Tah lze opět provést operací redo1.
     * @return úspěšnost akce
     */
    public boolean undo1() {
        if (undoStack.empty())
                return false;

        end = false;

        Board boardAfter = board;

        
        this.redoStack.push(this.board);
        board = this.undoStack.pop();
        
        Board boardBefore = board;
        int size = board.getSize();
        boolean lastPlayed = false; // barva hrace na tahu predchozi tah

        // zjisti kdo dal posledni tah kamen
        for (int row = 1; row <= size; row++)
                for (int col = 1; col <= size; col++)
                        // na policku kde pribyl kamen
                        if (!boardAfter.getField(row,col).isEmpty() && boardBefore.getField(row,col).isEmpty())
                                // barva pridaneho kamene predchozi tah tahla
                                lastPlayed = boardAfter.getField(row,col).getDisk().isWhite();

        // zmeni hrace na toho, kdo polozil predchozi tah kamen
        if (currentPlayer().isWhite() != lastPlayed)
                nextPlayer();

        // hrac, ktery je nyni na tahu dostane zpet kamen
        currentPlayer().backDisk();
        return true;
    }
    
    /** Vrátí desku o 1 tah hráče člověka zpět. 
     * Všechny tahy hráče AI jsou také vráceny, až do chvíle kdy byl na tahu člověk.
     * @return Úspěšnost akce.
     */
    public boolean undo() {
        if (undoable()) {
            // provadi undo po 1 tahu, dokud neni na tahu člověk
            while (undo1() && currentPlayer().getControl().isAI()) {}
            
            // pokud už nelze undo ale AI je stále na tahu, nechá ho tánout
            while (playAI()) {}
            
            return true;
        }
        return false;
    }
    
    /**
     * Opět provede 1 vácený tah. Změní aktuální hrací desku.
     * @return úspěšnost akce
     */
    public boolean redo1() {
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
    
    /** Opět provede 1 tah hráče člověka, který byl vrácen. 
     * Všechny tahy hráče AI jsou také opět provedeny, až do chvíle kdy byl na tahu člověk.
     * @return Úspěšnost akce.
     */
    public boolean redo() {
        if (redoable()) {
            // provede redo hrace a pripadne vsechny redo pocitace, dokud je pocitac na tahu
            while (redo1() && currentPlayer().getControl().isAI()) {}

            // pokud je AI stale na tahu, provadi tahy
            while (playAI()) {}
            
            return true;
        }
        return false;
    }
    
    /**
     * Zjistí skore hráče dané barvy na aktuální desce.
     * @param isWhite Barva hráče.
     * @return Skore hráče dané barvy.
     */
    public int score(boolean isWhite) {
        int ret = 0;
        for (Field field : this.board) {
            if (!field.isEmpty() && field.getDisk().isWhite() == isWhite)
                ret++;
        }
        return ret;
    }
}