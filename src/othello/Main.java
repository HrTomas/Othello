/**
 * Provides the classes necessary to create an
 * applet and the classes an applet uses
 * to communicate with its applet context.
 */
package othello;

import othello.frontend.MainScreen;
import javax.swing.*;
import java.awt.*;

/**
 * Create main frame. Show main screen.
 *
 * @author Tomas Hreha
 */
public class Main {

    /**
     * Main frame for UI of application.
     */
    public static final JFrame mainFrame = new JFrame();

    /**
     * Main constructor.
     */
    public static void main(String[] args) {
        mainFrame.setSize(850, 850);
        MainScreen mainScreen = new MainScreen();
        mainFrame.setContentPane(mainScreen.mainPanel);
        mainFrame.setMinimumSize(new Dimension(500, 500));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
