package othello.backend.board;

import java.io.Serializable;

/**
 * Represents one disk inserted into playboard
 *
 * @author Tomas Hreha.
 */
public class Disk implements Serializable {

    private boolean diskColor; // false == black, true == white
    private boolean iAmInitDisk = false;

    /**
     * Constructor.
     * @param isWhite Color of disk. True is white, false is black.
     */
    public Disk(boolean isWhite) {
        diskColor = isWhite;
    }

    /**
     * Turn disks color.
     */
    public void turn() {
        diskColor = !diskColor;
    }

    /**
     * Get disks color.
     * @return True if disk is white, false if disk is black.
     */
    public boolean isWhite() {
        return this.diskColor;
    }

    /**
     * Initialization disk.
     */
    public void setInitDisk() {
        iAmInitDisk = true;
    }

    /**
     * Get if it is initialization disk.
     * @return True if it is, false if not.
     */
    public boolean amIInitDisk() {
        return iAmInitDisk;
    }

    /**
     * Compare this disk with received one.
     * @param obj Disk for comparison.
     * @return True if disks match, false if not.
     */
    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return (((Disk)obj).diskColor == diskColor);
    }

    /**
     * Get hash code of this disk.
     * @return 1 if disk is white, 0 if it is black.
     */
    @Override
    public int hashCode() {
        return (diskColor) ? 1 : 0;
    }
}
