package othello.backend.board;

import java.io.Serializable;

/**
 * Create playing surface.
 *
 * @author Tomas Hreha
 */
public class Board implements Serializable {

    public Field PlayBoard[][] = null;  //matrix of fields
    private int sizeForReturn;
    public int realSize;
    private Rules rulesForReturn;

    /**
     * Initialization of board.
     * @param rules Object with game rules.
     */
    public Board(Rules rules) {
        rulesForReturn = rules;
        realSize = rules.getSize() + 2;
        sizeForReturn = rules.getSize();
        PlayBoard = new Field[realSize][realSize];
        for(int row = 0; row < realSize; row++) {
            for(int col = 0; col < realSize; col++) {
                if(col == 0 || row == 0 || col == realSize - 1 || row == realSize - 1)
                    PlayBoard[row][col] = new BorderField();    //insert border
                else {
                    PlayBoard[row][col] = rules.createField(row, col, this);    //insert playable field
                }
            }
        }
        Field.Direction direction[] = Field.values();
        for(int row = 0; row < realSize; row++) {
            for(int col = 0; col < realSize; col++) {
                if(col != 0 || row != 0 || col != realSize - 1 || row != realSize - 1) {
                    for(int i = 0; i < 8; i++)  //get familiar whit neighbors
                        PlayBoard[row][col].addNextField(direction[i], PlayBoard[row][col]);
                }
            }
        }
    }

    /**
     * Get specific field from board.
     * @param row Fields row position.
     * @param col Fields column position.
     * @return Return object of required field.
     */
    public Field getField(int row, int col) {
        return PlayBoard[row][col];
    }

    /**
     * Get count of playable fields.
     * @return Int number.
     */
    public int getSize() {
        return sizeForReturn;
    }

    /**
     * Get games rules.
     * @return Object of Rules class.
     */
    public Rules getRules() {
        return rulesForReturn;
    }

    /**
     * Get playing surface.
     * @return Matrix of fields.
     */
    public Field[][] getBoard() {
        return PlayBoard;
    }
}
