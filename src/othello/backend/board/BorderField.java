package othello.backend.board;

import javax.swing.*;
import java.io.Serializable;

/**
 * Represents one field of playboard, specifically border field.
 *
 * @author Tomas Hreha
 */
public class BorderField implements Field, Serializable {
    public BorderField() {

    }

    public void addNextField(Field.Direction dirs, Field field) {}

    public Field nextField(Field.Direction dirs) {
        return null;
    }

    public boolean putDisk(Disk disk) {
        return false;
    }

    public Disk getDisk() {
        return null;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean canPutDisk(Disk disk) {
        return false;
    }

    public void insertJPanel(JPanel onePanel) {}

    public JPanel getJPanel() { return null; }

    public void freezeDisk() {}

    public void unfreezeDisk() {}

    public boolean isFrozen() {
        return false;
    }

    public void turnDisk() {}

    public boolean getPrevDisk() { return false; }
}
