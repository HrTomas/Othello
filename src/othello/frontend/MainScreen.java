package othello.frontend;

import othello.backend.GameLuncher;

import othello.backend.saves.SaveObjects;
import othello.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Main screen of application.
 *
 * @author Tomas Hreha
 */
public class MainScreen {

    public JPanel mainPanel;
    private JPanel homePanel;
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton resultsButton;
    private JButton exitButton;

    private MainScreen thisObject = this;

    /**
     * UI for game.
     */
    private GameGUI gameGUI;

    /**
     * List of games for switching between them.
     */
    private ArrayList<GameGUI> listOfGames = new ArrayList<GameGUI>();

    private SaveObjects saveObjects;

    /**
     * Create main screen with buttons for basic functions.
     * Handle switching between open games, aborting games, saving games.
     */
    public MainScreen() {

        saveObjects = new SaveObjects();
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameGUI = new GameGUI(thisObject);  //create new game
                addGameIntoList(gameGUI);
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowSavedGames showSavedGames = new ShowSavedGames(thisObject);
                homePanel.setVisible(false);
                Main.mainFrame.setContentPane(showSavedGames.mainPanel);
            }
        });

        resultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long time = System.currentTimeMillis();
                System.out.println(time);

            }
        });
    }

    /**
     * Insert new game into list. Show lunch screen for new game.
     * @param gameGUI UI for game.
     */
    public void addGameIntoList(GameGUI gameGUI) {  //create list of games
        if(listOfGames.size() == 0)
            homePanel.setVisible(false);
        else {
            listOfGames.get(listOfGames.size() - 1).getGamePanel().setVisible(false);
        }
        listOfGames.add(gameGUI);
        Main.mainFrame.setContentPane(gameGUI.mainPanel);
    }

    /**
     * Show next game.
     * @param currentGame Game which is currently visible.
     * @return  False if there is no next game. True if successfully switch to next game.
     */
    public boolean getNextGame(GameGUI currentGame) {   //switch game
        if(listOfGames.indexOf(currentGame) < listOfGames.size()) {
            listOfGames.get(listOfGames.indexOf(currentGame)).getGamePanel().setVisible(false);
            Main.mainFrame.setContentPane((listOfGames.get(listOfGames.indexOf(currentGame) + 1).getGamePanel()));
            listOfGames.get(listOfGames.indexOf(currentGame) + 1).getGamePanel().setVisible(true);
            listOfGames.get(listOfGames.indexOf(currentGame) + 1).getGamePanel().repaint();
            return true;
        }
        return false;
    }

    /**
     * Show previous game.
     * @param currentGame   Game which is currently visible.
     * @return False if there is no previous game. True if successfully switch to previous game.
     */
    public boolean getPrevGame(GameGUI currentGame) {   //switch game
        if(listOfGames.indexOf(currentGame) < listOfGames.size()) {
            listOfGames.get(listOfGames.indexOf(currentGame)).getGamePanel().setVisible(false);
            Main.mainFrame.setContentPane((listOfGames.get(listOfGames.indexOf(currentGame) - 1).getGamePanel()));
            (listOfGames.get(listOfGames.indexOf(currentGame) - 1).getGamePanel()).setVisible(true);
            (listOfGames.get(listOfGames.indexOf(currentGame) - 1).getGamePanel()).repaint();
            return true;
        }
        return false;
    }

    /**
     * Exit only one specific game.
     * Garbage collector handle object with no reference onto it.
     * @param game Game which user want to exit.
     */
    public void exitSpecificGame(GameGUI game) {    //exit one game
        int gameIndex = listOfGames.indexOf(game);
        if(listOfGames.size() == 1) {
            Main.mainFrame.setContentPane(mainPanel);
            homePanel.setVisible(true);
        } else {
            if(gameIndex > 0)
                getPrevGame(game);
            else
                getNextGame(game);
        }
        listOfGames.remove(gameIndex);
    }

    /**
     * Exit all currently open games.
     */
    public void exitAllGames() {    //exit all games
        int size = listOfGames.size();
        for(int i = 0; i < size; i++) {
            listOfGames.remove(listOfGames.size() - 1);
        }
        Main.mainFrame.setContentPane(mainPanel);
        homePanel.setVisible(true);
    }

    /**
     * Save specific game.
     * @param gameLuncher Backend object with references to all objects which should be saved.
     */
    public void saveGame(GameLuncher gameLuncher) {
        saveObjects.saveGame(gameLuncher);
    }

    /**
     * Get index of specific game from listOfGames.
     * @param currentGame Game which index is required.
     * @return Int value of game index.
     */
    public int getPositionOfGame(GameGUI currentGame) {
        return listOfGames.indexOf(currentGame);
    }

    /**
     * Get number of games, stored in listOfGames.
     * @return Int value.
     */
    public int getListSize() {
        return listOfGames.size();
    }
}
