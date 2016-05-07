package othello.backend.board;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents one playable field of board.
 *
 * @author Tomas Hreha
 */
public class BoardField implements Field, Serializable {

    private Field myNeighborhood[] = new Field[8];
    private int myColCoordinates = 0;
    private int myRowCoordinates = 0;
    private Disk myDisk = null;
    private JPanel myJPanel = null;
    private Field playBoard[][] = null;
    private boolean amIFrozen = false;
    private ArrayList<Disk> diskHistory = new ArrayList<Disk>();

    /**
     * Constructor.
     * @param row Row position of this field.
     * @param col Col position of this field.
     * @param board Board to which this field belongs.
     */
    public BoardField(int row, int col, Board board) {
        myColCoordinates = col; //create field on specific position
        myRowCoordinates = row;
        playBoard = board.getBoard();
    }

    public void addNextField(Field.Direction dirs, Field field)  {
        switch(dirs) {  //create filed with neighbors
            case D:
                myNeighborhood[0] = playBoard[(myRowCoordinates + 1)][myColCoordinates];
                return;
            case L:
                myNeighborhood[1] = playBoard[myRowCoordinates][(myColCoordinates - 1)];
                return;
            case LD:
                myNeighborhood[2] = playBoard[(myRowCoordinates + 1)][(myColCoordinates - 1)];
                return;
            case LU:
                myNeighborhood[3] = playBoard[(myRowCoordinates - 1)][(myColCoordinates - 1)];
                return;
            case R:
                myNeighborhood[4] = playBoard[myRowCoordinates][(myColCoordinates + 1)];
                return;
            case RD:
                myNeighborhood[5] = playBoard[(myRowCoordinates + 1)][(myColCoordinates + 1)];
                return;
            case RU:
                myNeighborhood[6] = playBoard[(myRowCoordinates - 1)][(myColCoordinates + 1)];
                return;
            case U:
                myNeighborhood[7] = playBoard[(myRowCoordinates - 1)][myColCoordinates];
                return;
            default:
                throw new IllegalArgumentException("bad direction.");
        }
    }

    public Field nextField(Field.Direction dirs) {
        switch(dirs) {  //get neighbor
            case D:
                return myNeighborhood[0];
            case L:
                return myNeighborhood[1];
            case LD:
                return myNeighborhood[2];
            case LU:
                return myNeighborhood[3];
            case R:
                return myNeighborhood[4];
            case RD:
                return myNeighborhood[5];
            case RU:
                return myNeighborhood[6];
            case U:
                return myNeighborhood[7];
            default:
                throw new IllegalArgumentException("bad direction.");
        }
    }

    /**
     * Return fields hash code. (autogenerated)
     * @return Int value of hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.myColCoordinates;
        hash = 71 * hash + this.myRowCoordinates;
        return hash;
    }

    /**
     * Compare current Object with received one.
     * @param obj Object for comparing.
     * @return True if objects match. False if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BoardField other = (BoardField) obj;
        if (this.myColCoordinates != other.myColCoordinates) {
            return false;
        }
        if (this.myRowCoordinates != other.myRowCoordinates) {
            return false;
        }
        return true;
    }

    public boolean putDisk(Disk disk) { //insert disk into this field
        if(myDisk != null)  //field already have disk
            return false;
        diskHistory.add(myDisk);
        myDisk = disk;
        return true;
    }

    public void turnDisk() {    //turn disks color
        diskHistory.add(myDisk);
        myDisk.turn();
    }

    public Disk getDisk() {
        return myDisk;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean canPutDisk(Disk disk) {
        return false;
    }

    public void insertJPanel(JPanel onePanel) {
        myJPanel = onePanel;
    }

    public JPanel getJPanel() {
        return myJPanel;
    }

    public void freezeDisk() {  //freeze this disk
        amIFrozen = true;
    }

    public void unfreezeDisk() {    //unfreeze this disk
        amIFrozen = false;
        if(myDisk != null) {
            if(myDisk.isWhite())
                myJPanel.setBackground(Color.WHITE);
            else
                myJPanel.setBackground(Color.BLACK);
        }
    }

    public boolean isFrozen() {
        return amIFrozen;
    }

    public boolean getPrevDisk() {  //get previous state of disk
        if(diskHistory.size() > 0) {
            if(!myDisk.amIInitDisk()) {
                myDisk = diskHistory.get(diskHistory.size() - 1);
                diskHistory.remove(diskHistory.size() - 1);
                if(myDisk != null)
                    myDisk.turn();
            } else {
                if (diskHistory.size() != 1 && myDisk.amIInitDisk())
                    myDisk.turn();
            }
            return true;
        }
        return false;
    }
}