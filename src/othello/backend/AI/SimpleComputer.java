package othello.backend.AI;

import othello.backend.game.Player;
import othello.frontend.GameGUI;

import java.io.Serializable;
import java.util.Random;

/**
 * Simulates player. Generate random positions, if it is possible to insert disk, it insert disk into this position.
 *
 * @author Tomas Hreha
 */
public class SimpleComputer extends Player implements Serializable {

    private Random random = new Random();

    /**
     * Constructor.
     * @param isWhite Players color.
     * @param playersName Players name.
     * @param IAmComputer Player is computer.
     */
    public SimpleComputer(boolean isWhite, String playersName, boolean IAmComputer) {
        super(isWhite, playersName, IAmComputer);
    }

    /**
     * Get random positions, if it is possible, insert on this positions disk.
     * @param currentGame
     */
    public void insertDisk(GameGUI currentGame) {
        int row;
        int col;
        do {    //get random positions
            row = random.nextInt(currentGame.getGame().getBoard().getSize()) + 1;
            col = random.nextInt(currentGame.getGame().getBoard().getSize()) + 1;
        }while(!currentGame.getGame().currentPlayer().canPutDisk(currentGame.getGame().getBoard().getField(row, col))); //if it is possible to insert disk
        currentGame.getBoardGUI().onClickEvent(currentGame.getGame().getBoard().getField(row, col).getJPanel(), row, col);  //do it
    }
}
