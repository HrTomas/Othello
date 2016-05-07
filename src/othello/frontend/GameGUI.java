package othello.frontend;

/*
Author: Tomas Hreha
Date: 20.4.2016
Project: IJA Othello (Reversi) 2016 LS
Class: GameGUI
Purpose: Represents one game
 */

import othello.backend.AI.AdvanceComputer;
import othello.backend.AI.SimpleComputer;
import othello.backend.GameLuncher;
import othello.Main;
import othello.backend.board.Board;
import othello.backend.board.FrozenDisksHandler;
import othello.backend.game.Game;
import othello.backend.game.Player;
import othello.backend.game.ReversiRules;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * UI for game.
 * Load necessary data from user.
 *
 * @author Tomas Hreha
 */
public class GameGUI {
    private JPanel lunchGamePanel;
    private JTextField gameName;
    private JRadioButton a8RadioButton;
    private JRadioButton a10RadioButton;
    private JRadioButton a12RadioButton;
    private JTextField player1Name;
    private JTextField player2Name;
    private JCheckBox diskFrozen;
    private JButton lunchGame;
    public JPanel mainPanel;
    private JTextField initiateFrozening;
    private JTextField periodOfFrozening;
    private JTextField countOfDiskToFrozen;
    private JPanel frozenProperties;
    private JCheckBox repeatFrozening;
    private JRadioButton firstPlayerHuman;
    private JRadioButton firstPlayerSimpleComp;
    private JRadioButton firstPlayerAdvanceComp;
    private JRadioButton secondPlayerHuman;
    private JRadioButton secondPlayerSimpleComp;
    private JRadioButton secondPlayerAdvanceComp;

    private ReversiRules rules;
    private Board board;
    private Game game;
    private Player blackPlayer;
    private Player whitePlayer;
    private BoardGUI boardGUI;
    private MainScreen mainScreen;
    private FrozenDisksHandler frozenDisksHandler = null;

    private GameLuncher gameLuncher;

    public String firstPlayerName, secondPlayerName, nameOfGame;
    public boolean frozenStones = false;
    public int sizeOfBoard = 8;

    /**
     * Constructor.
     * @param mainScreen
     */
    public GameGUI(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        ButtonGroup boardSizeGroupe = new ButtonGroup();
        a8RadioButton.setSelected(true);    //board size
        boardSizeGroupe.add(a8RadioButton);
        boardSizeGroupe.add(a10RadioButton);
        boardSizeGroupe.add(a12RadioButton);

        ButtonGroup firstPlayerBttnGroup = new ButtonGroup();
        firstPlayerHuman.setSelected(true); //if player will be human or computer
        firstPlayerBttnGroup.add(firstPlayerHuman);
        firstPlayerBttnGroup.add(firstPlayerSimpleComp);
        firstPlayerBttnGroup.add(firstPlayerAdvanceComp);

        ButtonGroup secondPlayerBttnGroup = new ButtonGroup();
        secondPlayerHuman.setSelected(true);    //if player will be human or computer
        secondPlayerBttnGroup.add(secondPlayerHuman);
        secondPlayerBttnGroup.add(secondPlayerSimpleComp);
        secondPlayerBttnGroup.add(secondPlayerAdvanceComp);

        lunchGame.addActionListener(new ActionListener() {  //start game
            @Override
            public void actionPerformed(ActionEvent e) {
                retrieveData();
                loadGame(new GameLuncher(getThisGame()));
                if(frozenDisksHandler != null)
                    frozenDisksHandler.lunchITimer();
            }
        });

        diskFrozen.addItemListener(new ItemListener() { //show properties for frozening disks
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(diskFrozen.isSelected()) {
                    frozenProperties.setVisible(true);
                } else
                    frozenProperties.setVisible(false);
            }
        });
    }

    /**
     * Retrieve data from editable elements.
     */
    private void retrieveData() {
        firstPlayerName = player1Name.getText();
        secondPlayerName = player2Name.getText();
        nameOfGame = gameName.getText();
        frozenStones = diskFrozen.isSelected();
        if(a8RadioButton.isSelected())
            sizeOfBoard = 8;
        if(a10RadioButton.isSelected())
            sizeOfBoard = 10;
        if(a12RadioButton.isSelected())
            sizeOfBoard = 12;
    }

    /**
     * Load create game from backend.
     * @param gameLuncher Created game from backend.
     */
    public void loadGame(GameLuncher gameLuncher) {
        this.gameLuncher = gameLuncher;
        rules = gameLuncher.getRules();
        board = gameLuncher.getBoard();
        game = gameLuncher.getGame();
        blackPlayer = gameLuncher.getBlackPlayer();
        if(blackPlayer.getIAmComputer()) {
            if(gameLuncher.getIfFirstPlayerIsSimpleCmp()) {
                blackPlayer = (SimpleComputer) gameLuncher.getBlackPlayer();
                firstPlayerSimpleComp.setSelected(true);
            } else {
                blackPlayer = (AdvanceComputer) gameLuncher.getBlackPlayer();
                firstPlayerAdvanceComp.setSelected(true);
            }
        }
        whitePlayer = gameLuncher.getWhitePlayer();
        if(whitePlayer.getIAmComputer()) {
            if(gameLuncher.getIfSecondPlayerIsSimpleCmp()) {
                whitePlayer = (SimpleComputer) gameLuncher.getWhitePlayer();
                secondPlayerSimpleComp.setSelected(true);
            } else {
                whitePlayer = (AdvanceComputer) gameLuncher.getWhitePlayer();
                secondPlayerAdvanceComp.setSelected(true);
            }
        }
        frozenDisksHandler = gameLuncher.getFrozenDisksHandler();
        if(frozenDisksHandler != null) {
            if(frozenDisksHandler.getIfITimerIsRunning()) {
                frozenDisksHandler.lunchITimer();
            } else {
                if(frozenDisksHandler.getIfRTimerIsRunning()) {
                    frozenDisksHandler.stopTimers();
                    frozenDisksHandler.lunchRTimer();
                }
            }
        }
        boardGUI = new BoardGUI(game, mainScreen, this);
        lunchGamePanel.setVisible(false);
        Main.mainFrame.setContentPane(boardGUI.mainPanelBoard);
    }

    public GameGUI getThisGame() {
        return this;
    }

    public JPanel getGamePanel() {
        return boardGUI.getMainPanel();
    }

    public int getSizeOfBoard() {
        return sizeOfBoard;
    }

    public ReversiRules getRules() {
        return rules;
    }

    public Board getBoard() {
        return board;
    }

    public Game getGame() {
        return game;
    }

    public String getNameOfGame() {
        return nameOfGame;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public boolean getFirstPlayerHuman() {
        return firstPlayerHuman.isSelected();
    }

    public boolean getFirstPlayerComputer1() {
        return firstPlayerSimpleComp.isSelected();
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public boolean getSecondPlayerHuman() {
        return secondPlayerHuman.isSelected();
    }

    public boolean getSecondPlayerComputer1() {
        return secondPlayerSimpleComp.isSelected();
    }

    public FrozenDisksHandler getFrozenDisksHandler() {
        return frozenDisksHandler;
    }

    public boolean getDiskFrozen() {
        return diskFrozen.isSelected();
    }

    public int getPeriodOfFrozening() {
        return Integer.valueOf(periodOfFrozening.getText());
    }

    public int getCountOfDiskToFrozen() {
        return Integer.valueOf(countOfDiskToFrozen.getText());
    }

    public int getInitiateFrozening() {
        return Integer.valueOf(initiateFrozening.getText());
    }

    public boolean getRepeatFrozening() {
        return repeatFrozening.isSelected();
    }

    public BoardGUI getBoardGUI() {
        return boardGUI;
    }

    public GameLuncher getGameLuncher() {
        return gameLuncher;
    }

    @Override
    public String toString() {
        return nameOfGame;
    }
}
