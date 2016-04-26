
package game;

import board.Board;

public class Game {
    private Board board;
    private Player[] players;
    private int onTurn;
    
    public Game(Board board) {
        this.board = board;
        players = new Player[2];
        onTurn = 0;
    }
    
    public boolean addPlayer(Player player) {
        boolean isWhite = player.isWhite();
        int playerNum = isWhite? 0 : 1;
        if (players[playerNum] != null)
            return false;
        else {
            players[playerNum] = player;
            player.init(this.board);
            return true;
        }
    }
    
    public Player currentPlayer() {
        return players[onTurn];
    }
    
    public void nextPlayer() {
        this.onTurn = (this.onTurn == 0) ? 1 : 0;
    }
    
    public Board getBoard() {
        return this.board;
    }
}
