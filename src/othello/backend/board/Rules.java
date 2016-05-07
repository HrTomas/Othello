package othello.backend.board;

/**
 * Interface for rules.
 *
 * @author Tomas Hreha
 */
public interface Rules {

    /**
     * Get size of board.
     * @return Int value of size.
     */
    int getSize();

    /**
     * Get count of available disks for player.
     * @return Int value of available disks.
     */
    int numberDisks();

    /**
     * Create new playable field.
     * @param row Row position of field.
     * @param col Col position of field.
     * @param board Board where field belongs.
     * @return Object of BoardField.
     */
    Field createField(int row, int col, Board board);
}
