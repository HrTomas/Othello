package othello.backend.game;

import othello.backend.board.Board;
import othello.backend.board.BoardField;
import othello.backend.board.Field;
import othello.backend.board.Rules;

import java.io.Serializable;

/**
 * Represents initialization of rules for game.
 *
 * @author Tomas Hreha
 */
public class ReversiRules implements Rules, Serializable {

    private int countOfDisksForPlayers;
    private int sizeForReturn;
    public int realSize;

    /**
     * Constructor.
     * Set size of board for game.
     * @param size Size of board.
     */
    public ReversiRules(int size) {
        this.sizeForReturn = size;
        this.realSize = size + 2;
        countOfDisksForPlayers = size*size/2;
    }

    public int getSize() {
        return sizeForReturn;
    }

    public int numberDisks() {
        return countOfDisksForPlayers;
    }

    public Field createField(int row, int col, Board board) {
        return new BoardField(row, col, board);
    }
}
