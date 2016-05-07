package othello.backend.game;

import othello.backend.board.Board;
import othello.backend.board.Disk;
import othello.backend.board.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents one player.
 *
 * @author Tomas Hreha
 */
public class Player implements Serializable {

    private boolean playersColor; //true = white, false = black
    private int countOfDisks = 0;
    private List<Integer> dirWhereICanTurnDisks = new ArrayList<Integer>();
    private String playersName;
    private Board board;
    private ArrayList<ArrayList> historyOfMovements = new ArrayList<>();
    private boolean IAmComputer;

    /**
     * Constructor
     * @param isWhite Set players color.
     * @param playersName Set players name.
     * @param IAmComputer Set if it is computer or human being.
     */
    public Player(boolean isWhite, String playersName, boolean IAmComputer) {
        playersColor = isWhite;
        this.playersName = playersName;
        this.IAmComputer = IAmComputer;
    }

    /**
     * Get players color.
     * (Color represents just color in game, no segregation here)
     * @return True if players is white, False if player is black.
     */
    public boolean isWhite() {
        return playersColor;
    }

    /**
     * Check if player can put disk into specific field.
     * @param field Field for check.
     * @return True if players is able to insert disk into received field, False if not.
     */
    public boolean canPutDisk(Field field) {
        boolean opositeColorWasFound;
        boolean canIPutDisk = false;
        Field nextField;
        if(field.getDisk() != null)
            return false;
        if(field.isFrozen())
            return false;
        if(emptyPool())
            return false;
        Field.Direction direction[] = Field.values();
        dirWhereICanTurnDisks.clear();
        for(int i = 0; i < 8; i++) {
            opositeColorWasFound = false;
            nextField = field;
            while(true) {
                nextField = nextField.nextField(direction[i]);
                if(nextField == null || nextField.getDisk() == null || nextField.isFrozen()) {
                    break;
                }
                if(nextField.getDisk().isWhite() == isWhite()) {
                    if(opositeColorWasFound) {
                        dirWhereICanTurnDisks.add(i);   //also create list with every possible movements
                        canIPutDisk = true;
                        break;
                    }
                    break;
                } else {
                    opositeColorWasFound = true;
                }
            }
        }
        return canIPutDisk;
    }

    /**
     * Get if player how disks for insertion.
     * @return True if player do not have avaible disks, False if he got some.
     */
    public boolean emptyPool() {
        return (countOfDisks == 0) ? true : false;
    }

    /**
     * Put disk into specific field.
     * @param field Field where player is inserting disk.
     * @return True if operation was successfully, False if operation fail.
     */
    public boolean putDisk(Field field) {
        if(!canPutDisk(field))
            return false;
        Field nextField = field;
        Field.Direction direction[] = Field.values();
        ArrayList<Field> oneMove = new ArrayList<>();
        for(int i = 0; i < dirWhereICanTurnDisks.size(); i++) { //turns disk in every possible direction
            nextField = field.nextField(direction[dirWhereICanTurnDisks.get(i)]);
            do {
                oneMove.add(nextField); //crete record about movement
                nextField.turnDisk();
                nextField = nextField.nextField(direction[dirWhereICanTurnDisks.get(i)]);
            }while(nextField.getDisk().isWhite() != isWhite());
        }
        countOfDisks -= 1;
        oneMove.add(field);
        field.putDisk(new Disk(playersColor));
        addMovementToHistory(oneMove);  //insert it into list of every movement
        return true;
    }

    /**
     * Initialization of player.
     * Set how many disks are available for player to insertion.
     * Insert initialization disks.
     * @param board Board on which player play.
     */
    public void init(Board board) {
        this.board = board;
        if(!emptyPool())
            return;
        int size = board.realSize;
        countOfDisks = board.getRules().numberDisks();
        if(playersColor) {
            board.getField(size/2, size/2).putDisk(new Disk(playersColor));
            board.getField(size/2, size/2).getDisk().setInitDisk();
            board.getField((size/2)-1, (size/2)-1).putDisk(new Disk(playersColor));
            board.getField((size/2)-1, (size/2)-1).getDisk().setInitDisk();
        } else {
            board.getField((size/2)-1, size/2).putDisk(new Disk(playersColor));
            board.getField((size/2)-1, size/2).getDisk().setInitDisk();
            board.getField(size/2, (size/2)-1).putDisk(new Disk(playersColor));
            board.getField(size/2, (size/2)-1).getDisk().setInitDisk();
        }
        countOfDisks -= 2;
    }

    /**
     * Get how many possible moves player got in current movement.
     * @return Int value of players possible movements.
     */
    public int playerGotPossibleMove() {
        int possibleMoves = 0;
        for(int row = 1; row <= board.getSize(); row++) {
            for(int col = 1; col <= board.getSize(); col++) {
                if(canPutDisk(board.getField(row, col)))
                    possibleMoves++;
            }
        }
        return possibleMoves;
    }

    /**
     * Get players score.
     * @return Int value of players score.
     */
    public int getScore() {
        int score = 0;
        for(int row = 0; row <= board.getSize(); row++) {
            for(int col = 0; col <= board.getSize(); col++) {
                if(board.getField(row, col).getDisk() == null) {
                    continue;
                }
                if(board.getField(row, col).getDisk().isWhite() == isWhite())
                    score++;
            }
        }
        return score;
    }

    /**
     * Save how players move.
     * @param oneMove One players move.
     */
    private void addMovementToHistory(ArrayList oneMove) {
        historyOfMovements.add(oneMove);
    }

    /**
     * Get how players play (where he puts his disks).
     * @return ArrayList of movements.
     */
    public ArrayList getHistoryOfMovements() {
        return historyOfMovements;
    }

    /**
     * Increase count of available disks.
     */
    public void incCountOfDisks() {
        countOfDisks++;
    }

    /**
     * Get how much disks player currently have.
     * @return Int value of available disks.
     */
    public int getCountOfDisks() {
        return countOfDisks;
    }

    /**
     * Get directions where will occur changes if player insert disk.
     * @return List with positions of directions.
     */
    public List<Integer> getDirWhereICanTurnDisks() {
        return dirWhereICanTurnDisks;
    }

    /**
     * Get if player is computer or not.
     * @return True if player is computer, False if it is human.
     */
    public boolean getIAmComputer() {
        return IAmComputer;
    }

    /**
     * Get players name.
     * @return String value of players name.
     */
    @Override
    public String toString() {
        return playersName;
    }
}
