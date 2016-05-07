package othello.backend.board;

import javax.swing.*;

/**
 * Interface for fields in board.
 *
 * @author Tomas Hreha
 */
public interface Field {

    /**
     * Enum of directions.
     */
    public static enum Direction {
        /**
         * Down.
         */
        D,
        /**
         * Left.
         */
        L,
        /**
         * Left-down.
         */
        LD,
        /**
         * Left-up.
         */
        LU,
        /**
         * Right.
         */
        R,
        /**
         * Righ-down
         */
        RD,
        /**
         * Right-up.
         */
        RU,
        /**
         * Up.
         */
        U
    };

    /**
     * Get directions as array for better exploitation.
     * @return Array of directions.
     */
    public static Field.Direction[] values() {
        return Direction.values();
    }

    /**
     * Get direction specified by string value.
     * @param name String value of direction.
     * @return Direction responding to string value.
     * @throws IllegalArgumentException if received string value does not mach to any direction.
     * @throws NullPointerException if received value is null.
     */
    public static Field.Direction valueOf(java.lang.String name) {
        if(name == null || name.isEmpty())
            throw new NullPointerException("name cannot be null");
        Direction direction[] = values();
        for(int i = 0; i < 8; i++) {
            if(String.valueOf(direction[i]).equals(name))
                return direction[i];
        }
        throw new IllegalArgumentException("name is not one of names from Directions.");
    }

    /**
     * Get familiar whit neighbors.
     * @param dirs Direction.
     * @param field
     * @throws IllegalArgumentException if received direction which does not exists.
     */
    void addNextField(Field.Direction dirs, Field field);

    /**
     * Get neighbor from specific direction.
     * @param dirs Direction from where is required neighbor.
     * @return Object of neighbor.
     * @throws IllegalArgumentException if received direction which does not exists.
     */
    Field nextField(Field.Direction dirs);

    /**
     * Insert disk into this field.
     * @param disk Disk for insert.
     * @return False if field already have disk. True if insertion was successfully.
     */
    boolean putDisk(Disk disk);

    /**
     * Get disk from this field.
     * @return Object of disk.
     */
    Disk getDisk();

    /**
     * Insert UI component for this field.
     * @param onePanel UI component.
     */
    void insertJPanel(JPanel onePanel);

    /**
     * Get UI component of this field.
     * @return Object of JPanel.
     */
    JPanel getJPanel();

    /**
     * Turn disks color.
     */
    void turnDisk();

    /**
     * Get previous state of this field.
     * @return True if there is something to get back. False if this field do not have history.
     */
    boolean getPrevDisk();

    /**
     * Forbidden changes for this field.
     */
    void freezeDisk();

    /**
     * Allow changes for this field.
     */
    void unfreezeDisk();

    /**
     * Check if it is possible to make change on this field.
     * @return True if it is forbidden to change current state of field. False if it is possible.
     */
    boolean isFrozen();

    boolean isEmpty();
    boolean canPutDisk(Disk disk);
}
