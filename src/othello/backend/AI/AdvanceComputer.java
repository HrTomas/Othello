package othello.backend.AI;

import othello.backend.board.Board;
import othello.backend.game.Player;
import othello.frontend.GameGUI;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Simulates player. It is trying to insert disk into best available field.
 *
 * @author Tomas Hreha
 */
public class AdvanceComputer extends Player implements Serializable {

    private ArrayList<Integer> rowPrediction = new ArrayList<>();
    private ArrayList<Integer> colPrediction = new ArrayList<>();

    /**
     * Constructor.
     * @param isWhite Players color.
     * @param playersName Players name.
     * @param IAmComputer Player is computer.
     */
    public AdvanceComputer(boolean isWhite, String playersName, boolean IAmComputer) {
        super(isWhite, playersName, IAmComputer);
    }

    /**
     * Create array of positions.
     * @param board Play board of game.
     */
    public void initComputerPlayer(Board board) {
        /**
         * Best row index for 8x8.
         */
        Integer tmpRowPos8[] = {
                1, 1, 8, 8, //  20
                1, 1, 3, 3, 6, 6, 8, 8, //  8
                3, 3, 6, 6,  //  7
                1, 1, 4, 5, 4, 5, 8, 8, //  6
                3, 3, 4, 5, 4, 5, 6, 6,  //  4
                2, 2, 4, 5, 7, 7, 4, 5,  //  -3
                2, 2, 6, 6, 3, 3, 7, 7,  // -4
                1, 1, 2, 2, 7, 7, 8, 8, //  -8
        };

        /**
         * Best col index for 8x8.
         */
        Integer tmpColPos8[] = {
                1, 8, 1, 8, //  20
                3, 6, 1, 8, 1, 8, 3, 6,  //  8
                3, 6, 3, 6,  //  7
                4, 5, 1, 1, 8, 8, 4, 5,  //  6
                4, 5, 4, 4, 6, 6, 4, 5,  //  4
                4, 5, 7, 7, 4, 5, 2, 2,  //  -3
                2, 7, 2, 7, 7, 2, 3, 6,  //  -4
                2, 7, 1, 8, 1, 8, 2, 7,  //  -8
        };

        /**
         * Best row index for 10x10.
         */
        Integer tmpRowPos10[] = {
                1, 1, 10, 10,   //  1000
                1, 1, 3, 3, 8, 8, 10, 10,   //  100
                3, 3, 8, 8,     //  50
                1, 1, 5, 5, 6, 6, 10, 10,   //  40
                2, 2, 4, 4, 7, 7, 9, 9, //  30
                4, 4, 7, 7, //  7
                4, 4, 5, 5, 6, 6, 7, 7, //  3
                3, 3, 5, 5, 6, 6, 8, 8, //  -10
                3, 3, 4, 4, 7, 7, 8, 8, //  -20
                2, 2, 5, 5, 6, 6, 9, 9, //  -40
                2, 2, 3, 3, 8, 8, 9, 9, //  -50
                1, 1, 4, 4, 7, 7, 10, 10,   //  -400
                1, 1, 2, 2, 9, 9, 10, 10,   //  -500
                2, 2, 9, 9  //  -1000
        };

        /**
         * Best col index for 10x10.
         */
        Integer tmpColPos10[] = {
                1, 10, 10, 1,   //  1000
                3, 8, 1, 10, 1, 10, 3, 8,   //  100
                3, 8, 3, 8, //  50
                5, 6, 1, 10, 1, 10, 5, 6,   //  40
                4, 7, 2, 9, 2, 9, 4, 7,     //  30
                4, 7, 4, 7,     //  7
                5, 6, 4, 7, 4, 7, 5, 6,     //  3
                5, 6, 3, 8, 3, 8, 5, 6,     //  -10
                4, 7, 3, 8, 3, 8, 4, 7,     //  -20
                5, 6, 2, 9, 2, 9, 5, 6,     //  -40
                3, 8, 2, 9, 2, 9, 3, 8,     //  -50
                4, 7, 1, 10, 1, 10, 4, 7,   //  -400
                2, 9, 1, 10, 1, 10, 2, 9,   //  -500
                2, 9, 2, 9  //  -1000
        };

        /**
         * Best row index for 12x12.
         */
        Integer tmpRowPos12[] = {
                1, 1, 12, 12,   //  1000
                1, 1, 3, 3, 10, 10, 12, 12, //  500
                2, 2, 3, 3, 10, 10, 11, 11, //  400
                3, 3, 10, 10,   //  300
                1, 1, 5, 5, 8, 8, 12, 12,   //  200
                1, 1, 6, 6, 7, 7, 12, 12,   //  150
                2, 2, 5, 5, 8, 8, 11, 11,   //  100
                3, 3, 5, 5, 8, 8, 10, 10,   //  50
                3, 3, 6, 6, 7, 7, 10, 10,   //  30
                4, 4, 5, 5, 8, 8, 9, 9,     //  20
                5, 5, 8, 8,     //  7
                4, 4, 6, 6, 7, 7, 9, 9,     //  5
                5, 5, 6, 6, 7, 7, 8, 8,     //  4
                4, 4, 9, 9, //  -100
                2, 2, 6, 6, 7, 7, 11, 11,   //  -150
                3, 3, 4, 4, 9, 9, 10, 10,   //  -200
                2, 2, 4, 4, 9, 9, 11, 11,   //  -300
                1, 1, 4, 4, 9, 9, 12, 12,   //  -400
                1, 1, 2, 2, 11, 11, 12, 12, //  -500
                2, 2, 11, 11    //  -1000
        };


        /**
         * Best col index for 12x12.
         */
        Integer tmpColPos12[] = {
                1, 12, 1, 12,   //  1000
                3, 10, 1, 12, 1, 12, 3, 10, //  500
                3, 10, 2, 11, 2, 11, 3, 10, //  400
                3, 10, 3, 10,   //  300
                5, 8, 1, 12, 1, 12, 5, 8,   //  200
                6, 7, 1, 12, 1, 12, 6, 7,   //  150
                5, 8, 2, 11, 2, 11, 5, 8,   //  100
                5, 8, 3, 10, 3, 10, 5, 8,   //  50
                6, 7, 3, 10, 3, 10, 6, 7,   //  30
                5, 8, 4, 9, 4, 9, 5, 8,     //  20
                5, 8, 5, 8,     //  7
                6, 7, 4, 9, 4, 9, 6, 7,     //  5
                6, 7, 5, 8, 5, 8, 6, 7,     //  4
                4, 9, 4, 9,     //  -100
                6, 7, 2, 11, 2, 11, 6, 7,   //  -150
                4, 9, 3, 10, 3, 10, 4, 9,   //  -200
                4, 9, 2, 11, 2, 11, 4, 9,   //  -300
                4, 9, 1, 12, 1, 12, 4, 9,   //  -400
                2, 11, 1, 12, 1, 12, 2, 11, //  -500
                2, 11, 2, 11    //  -1000
        };

        if(board.getSize() == 8) {
            initRowPrediction(tmpRowPos8);
            initColPrediction(tmpColPos8);
        } else {
            if(board.getSize() == 10) {
                initRowPrediction(tmpRowPos10);
                initColPrediction(tmpColPos10);
            } else {
                initRowPrediction(tmpRowPos12);
                initColPrediction(tmpColPos12);
            }
        }
    }

    /**
     * Create list of row positions (easier to work with).
     * @param tmpRowPos Array of positions.
     */
    private void initRowPrediction(Integer tmpRowPos[]) {   //create list of row index
        for(int i = 0; i < tmpRowPos.length; i++)
            rowPrediction.add(tmpRowPos[i]);
    }

    /**
     * Create list of column positions (easier to work with).
     * @param tmpColPos Array of positions.
     */
    private void initColPrediction(Integer tmpColPos[]) {   //create list of col index
        for(int i = 0; i < tmpColPos.length; i++)
            colPrediction.add(tmpColPos[i]);
    }

    /**
     * Retrieve best possible position from positions list and insert disk into this position.
     * @param currentGame Current playing game.
     */
    public void insertDisk(GameGUI currentGame) {
        int row;
        int col;
        for(int i = 0; i < rowPrediction.size(); i++) {
            row = rowPrediction.get(i); //pick first best option
            col = colPrediction.get(i);
            if(currentGame.getGame().currentPlayer().canPutDisk(currentGame.getGame().getBoard().getField(row, col))) {
                rowPrediction.remove(i);    //if it is possible to insert disk, do it, remove index from lists (make it little bit faster)
                colPrediction.remove(i);
                currentGame.getBoardGUI().onClickEvent(currentGame.getGame().getBoard().getField(row, col).getJPanel(), row, col);
                break;
            }
        }
    }
}
