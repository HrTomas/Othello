package othello.frontend;

import othello.backend.saves.RestoreData;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.awt.Component.CENTER_ALIGNMENT;

/**
 * Show list of saved games.
 *
 * @author Tomas Hreha
 */
public class ShowSavedGames {

    public JPanel mainPanel;
    private JButton button1;
    private JPanel listOfGamesJPanel;
    ArrayList<String> listOfGames = new ArrayList<>();
    private MainScreen mainScreen;
    private GameGUI gameGUI;

    private Border greenBorder = BorderFactory.createLineBorder(Color.GREEN);

    /**
     * Constructor
     * @param mainScreen Parent screen.
     */
    public ShowSavedGames(MainScreen mainScreen) {
        listOfGamesJPanel.setLayout(new BoxLayout(listOfGamesJPanel, BoxLayout.PAGE_AXIS));
        this.mainScreen = mainScreen;
        loadListOfGames();
        createList();
    }

    /**
     * Load names of saved games from folder.
     */
    private void loadListOfGames() {
        Path path = FileSystems.getDefault().getPath("examples").toAbsolutePath();
        File examples = new File(path.toString());
        File[] listOfFiles = examples.listFiles();
        for(File file : listOfFiles) {
            if(file.isFile())
//                System.out.println(file.getName());
                listOfGames.add(file.getName());
        }
    }

    /**
     * Create string list with games names.
     */
    private void createList() {
        for(int i = 0; i < listOfGames.size(); i++) {
            listOfGamesJPanel.add(createOneLine(listOfGames.get(i)), CENTER_ALIGNMENT);
        }
    }

    /**
     * Create one line of list.
     * Create click listener for lunching game.
     * @param gameName Name of game.
     * @return Object of Jlabel.
     */
    private JLabel createOneLine(String gameName) {
        JLabel oneName = new JLabel();
        oneName.setAlignmentX(Component.CENTER_ALIGNMENT);
        oneName.setAlignmentY(Component.CENTER_ALIGNMENT);
        oneName.setText(gameName);
        oneName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                RestoreData restoreData = new RestoreData();
                gameGUI = new GameGUI(mainScreen);
                mainPanel.setVisible(false);
                mainScreen.addGameIntoList(gameGUI);
                gameGUI.loadGame(restoreData.getGameLuncher(oneName.getText()));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                oneName.setBorder(greenBorder);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseEntered(e);
                oneName.setBorder(null);
            }
        });
        return oneName;
    }
}
