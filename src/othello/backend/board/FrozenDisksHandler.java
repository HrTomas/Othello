package othello.backend.board;

import othello.backend.game.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Random;

/**
 * Object created if user set frozening of stones.
 * Handle timers, set if it is possible to frozen/unfrozen disks.
 *
 * @author Tomas Hreha
 */
public class FrozenDisksHandler implements Serializable {

    private int initiateFrozening;
    private int periodOfFrozening;
    private int countOfDiskToFrozen;
    private Game game;

    private boolean canIFreeze = false;
    private boolean canIUnfreeze = false;
    private boolean ITimerIsRunning = false;
    private boolean RTimerIsRunning = false;

    private Timer ITimer;
    private Timer RTimer;

    /**
     * Constructor.
     * Create timers, set events.
     * @param game Game to which this handler belongs.
     * @param I End of interval for timer, this interval set when is possible to freeze disks.
     * @param R End of interval for timer, this interval set when is possible to unfreeze disks.
     * @param C Count of disks for freezing.
     */
    public FrozenDisksHandler(Game game, int I, int R, int C) {
        this.game = game;
        initiateFrozening = I;
        periodOfFrozening = R;
        countOfDiskToFrozen = C;
        //when times up
        ITimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canIFreeze = true;
                ITimerIsRunning = false;
                ITimer.stop();
            }
        });
        //when times up
        RTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canIUnfreeze = true;
                RTimerIsRunning = false;
                RTimer.stop();
            }
        });
    }

    /**
     * Lunch timer, which will determines if it is possible to freeze disks.
     */
    public void lunchITimer() { //start timer
        Random random = new Random();
        ITimerIsRunning = true;
        ITimer.setInitialDelay(random.nextInt((initiateFrozening + 1) * 1000));
        ITimer.start();
    }

    /**
     * Lunch timer, which will determines if it is possible to unfreeze disks.
     */
    public void lunchRTimer() { //start timer
        Random random = new Random();
        RTimerIsRunning = true;
        RTimer.setInitialDelay(random.nextInt((periodOfFrozening + 1) * 1000));
        RTimer.start();
    }

    /**
     * Freeze disks.
     */
    public void freezeDisks() { //freeze disks
        int randomRow = 0;
        int randomCol = 0;
        int countOfSelectedDisks = 0;
        Random random = new Random();
        while(countOfSelectedDisks < countOfDiskToFrozen) {
            randomRow = random.nextInt(game.getBoard().getSize()) + 1;
            randomCol = random.nextInt(game.getBoard().getSize()) + 1;
            if(game.getBoard().getField(randomRow, randomCol).getDisk() != null) {
                game.getBoard().getField(randomRow, randomCol).freezeDisk();
                countOfSelectedDisks++;
            }
        }
        canIFreeze = false;
        lunchRTimer();
    }

    /**
     * Unfreeze disks.
     */
    public void unfreezeDisks() {   //unfreeze disks
        for(int row = 1; row <= game.getBoard().getSize(); row++) {
            for(int col = 1; col <= game.getBoard().getSize(); col++) {
                game.getBoard().getField(row, col).unfreezeDisk();
            }
        }
        canIUnfreeze = false;
    }

    /**
     * Stop both of timers.
     */
    public void stopTimers() {
        ITimer.stop();
        RTimer.stop();
    }

    /**
     * Get if it is possible to freeze disks.
     * @return True if it is possible, False if it is not.
     */
    public boolean getIfICanFreeze() {
        return canIFreeze;
    }

    /**
     * Get if it is possible to unfreeze disks.
     * @return True if it is possbile, False if it is not.
     */
    public boolean getIfICanUnfreeze() {
        return canIUnfreeze;
    }

    /**
     * Get if first timer is running.
     * @return True if timer is running, False if not.
     */
    public boolean getIfITimerIsRunning() {
        return ITimerIsRunning;
    }

    /**
     * Get if second timer is running.
     * @return True if timer is running, False if not.
     */
    public boolean getIfRTimerIsRunning() {
        return RTimerIsRunning;
    }
}
