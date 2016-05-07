package othello.backend.game;

import othello.backend.board.Board;

import java.io.Serializable;

/**
 * Initialization of game.
 *
 * @author Tomas Hreha
 */
public class Game implements Serializable {

    public Board board;
    private Player firstPlayer = null;
    private Player secondPlayer = null;
    private boolean firstPlayerPlay = true;
    private String nameOfGame;

    /**
     * Constructor.
     * @param board Board of this game.
     */
    public Game(Board board) {
        this.board = board;
    }

    /**
     * Set player for this game.
     * @param player Player of this game.
     * @return True if player was successfully added into game, False if it was not that successfully.
     */
    public boolean addPlayer(Player player) {
        if(firstPlayer == null) {
            firstPlayer = player;
            firstPlayer.init(board);
        } else {
            if(secondPlayer == null && firstPlayer.isWhite() != player.isWhite()) {
                secondPlayer = player;
                secondPlayer.init(board);
            } else
                return false;
        }
        return true;
    }

    /**
     * Return current playing player.
     * @return Object of player.
     */
    public Player currentPlayer() {
        if(firstPlayerPlay)
            return firstPlayer;
        else
            return secondPlayer;
    }

    /**
     * Swich players.
     * @return Object of players, the one who got move now.
     */
    public Player nextPlayer() {
        Player player;
        if(!firstPlayerPlay) {
            firstPlayerPlay = true;
            player = firstPlayer;
        } else {
            firstPlayerPlay = false;
            player = secondPlayer;
        }
        if(!gameOver() && player.playerGotPossibleMove() == 0)
            nextPlayer();
        return player;
    }

    /**
     * Chcek if game is over.
     * @return True if non of players got possible move (so game is over), false if game go on.
     */
    public boolean gameOver() {
        if(firstPlayer.playerGotPossibleMove() == 0 && secondPlayer.playerGotPossibleMove() == 0)
            return true;
        return false;
    }

    /**
     * Get wich of players win.
     * @return Object of player if there is winner, null if there is no winner.
     */
    public Player getWinner() {
        if(firstPlayer.getScore() != secondPlayer.getScore()) {
            if(firstPlayer.getScore() > secondPlayer.getScore())
                return firstPlayer;
            else
                return secondPlayer;
        }
        return null;
    }

    /**
     * Set name of game.
     * @param nameOfGame String value = name of game.
     */
    public void setNameOfGame(String nameOfGame) {
        this.nameOfGame = nameOfGame;
    }

    /**
     * Get board connected to this game.
     * @return Object of board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Get first player of this game.
     * @return Object of player (first player specifically).
     */
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Get second players of this game.
     * @return Object of player (second one specifically).
     */
    public Player getSecondPlayer() {
        return secondPlayer;
    }

    /**
     * Get name of game.
     * @return String value of games name.
     */
    @Override
    public String toString() {
        return nameOfGame;
    }
}
