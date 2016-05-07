package othello.backend.saves;

import othello.backend.GameLuncher;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Get saved data for game.
 *
 * @author Tomas Hreha
 */
public class RestoreData implements Serializable {

    private GameLuncher gameLuncher;

    /**
     * Constructor.
     */
    public RestoreData() {

    }

    /**
     * Get saved game.
     * @param gameName Name of game wich player wants to make alive again.
     * @return Object of GameLuncher, which represents backend of game.
     */
    public GameLuncher getGameLuncher(String gameName) {
        Path path = FileSystems.getDefault().getPath("examples").toAbsolutePath();
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(path + "\\" + gameName));
            gameLuncher = (GameLuncher)is.readObject();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameLuncher;
    }
}
