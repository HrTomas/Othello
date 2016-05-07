package othello.backend.saves;

import othello.backend.GameLuncher;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Save game.
 *
 * @author Tomas Hreha
 */
public class SaveObjects implements Serializable {

    /**
     * Constructor.
     */
    public SaveObjects() {

    }

    /**
     * Save specific game.
     * @param gameLuncher Object which represents application backend.
     */
    public void saveGame(GameLuncher gameLuncher) {
        gameLuncher.setLoadGame();
        String fileName = gameLuncher.getGame().toString() + String.valueOf(System.currentTimeMillis())+ ".bin";
        Path path = FileSystems.getDefault().getPath("examples").toAbsolutePath();
        System.out.println(path);
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path + "\\" + fileName));
            os.writeObject(gameLuncher);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("Subor nenajdeny.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Vstup/vystupna chyba");
            e.printStackTrace();
        }
        System.out.println("zapisane");
    }
}
