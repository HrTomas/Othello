package othello.frontend;

import othello.backend.AI.AdvanceComputer;
import othello.backend.AI.SimpleComputer;
import othello.backend.board.Field;
import othello.Main;
import othello.backend.board.Board;
import othello.backend.board.FrozenDisksHandler;
import othello.backend.game.Game;
import othello.backend.game.Player;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static java.awt.Component.CENTER_ALIGNMENT;

/**
 * Implements UI form for board.
 * Handle game events.
 *
 * @author Tomas Hreha
 */
public class BoardGUI {

    public JPanel mainPanelBoard;
    private JPanel playboard;

    private PlayerGUI firstPlayer;
    private PlayerGUI secondPlayer;
    private PlayerGUI currentPlayer;

    public JLabel fistPlayerName;
    private JLabel firstPlayerCountOfDisks;
    private JLabel firstPlayerCountOfMove;
    private JLabel secondPlayerName;
    private JLabel secondPlayerCountOfDisks;
    private JLabel secondPlayerCountOfMove;
    private JLabel firstPlayerScore;
    private JLabel secondPlayerScore;
    private JPanel firstPlayerJPanel;
    private JPanel secondPlayerJPanel;
    private JPanel prevGame;
    private JPanel newOrNextGame;
    private JLabel newOrNextText;
    private JLabel prevText;
    private JButton saveButton;
    private JButton quitButton;
    private JButton backButton;
    private JPanel firstPlayerAvailableDisks;
    private JPanel secondPlayerAvailableDisks;

    private int boardSize;
    private int currentPosCol;
    private JPanel oneLine;
    private Board board;
    private Game game;
    private FrozenDisksHandler frozenDisksHandler;
    private GameGUI gameGUI;
    private MainScreen mainScreen;
    private JPanel panel;

    private boolean firstPlayerPlay = true;
    private Player winner;

    private Border grayBorder = BorderFactory.createLineBorder(Color.GRAY);
    private Border greenBorder = BorderFactory.createLineBorder(Color.GREEN);
    private Border redBorder = BorderFactory.createLineBorder(Color.RED);

    /**
     * Constructor.
     * @param game Current game.
     * @param mainScreen Parent MainScreen.
     * @param gameGUI Object which handle game events.
     */
    public BoardGUI(Game game, MainScreen mainScreen, GameGUI gameGUI) {
        this.game = game;
        this.gameGUI = gameGUI;
        this.board = game.getBoard();
        this.boardSize = board.getSize();
        this.mainScreen = mainScreen;
        frozenDisksHandler = gameGUI.getFrozenDisksHandler();

        firstPlayerAvailableDisks.setLayout(new BoxLayout(firstPlayerAvailableDisks, BoxLayout.PAGE_AXIS));
        secondPlayerAvailableDisks.setLayout(new BoxLayout(secondPlayerAvailableDisks, BoxLayout.PAGE_AXIS));

        playboard.setLayout(new BoxLayout(playboard, BoxLayout.Y_AXIS));
        currentPosCol = 0;
        for(int row = 1; row <= boardSize; row++) {
            for(int col = 1; col <= boardSize; col++) {
                createOneLine(row, col);    //create playboard
            }
        }

        newOrNextGame.addMouseListener(new MouseAdapter() { //switch game
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(mainScreen.getPositionOfGame(gameGUI) == (mainScreen.getListSize() - 1)) {
                    mainScreen.addGameIntoList(new GameGUI(mainScreen));
                } else {
                    mainScreen.getNextGame(gameGUI);
                }
            }
        });

        prevGame.addMouseListener(new MouseAdapter() {  //switch game
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(mainScreen.getPositionOfGame(gameGUI) != 0) {
                    mainScreen.getPrevGame(gameGUI);
                }
            }
        });

        backButton.addActionListener(new ActionListener() { //return movement
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!firstPlayerScore.getText().equals("2") && !secondPlayerScore.getText().equals("2"))
                    backButtonHandler();
            }
        });

        quitButton.addActionListener(new ActionListener() { //quit game
            @Override
            public void actionPerformed(ActionEvent e) {
                quitMessage();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainScreen.saveGame(gameGUI.getGameLuncher());
            }
        });

        initPlayers();
        repaintBoard();
    }

    /**
     * Create one line of board.
     * @param row Row position.
     * @param col Column position.
     */
    public void createOneLine(int row, int col) {   //create one line of playboard
        if(currentPosCol == 0) {
            oneLine = new JPanel();
            oneLine.setLayout(new BoxLayout(oneLine, BoxLayout.X_AXIS));
            oneLine.add(createPanel(row, col), CENTER_ALIGNMENT);
            currentPosCol++;
        } else {
            if(currentPosCol == boardSize - 1) {
                oneLine.add(createPanel(row, col), CENTER_ALIGNMENT);
                playboard.add(oneLine, CENTER_ALIGNMENT);
                currentPosCol = 0;
            } else {
                oneLine.add(createPanel(row, col), CENTER_ALIGNMENT);
                currentPosCol++;
            }
        }
    }

    /**
     * Create one JPanel which represents one BoardField.
     * @param row Row position of BoardField.
     * @param col Columng position of BoardField.
     * @return Object of JPanel representing one BoardField object.
     */
    private JPanel createPanel(int row, int col) {  //create one playable field
        Dimension dimension = new Dimension(50, 50);
        JPanel onePanel = new JPanel();
        onePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        onePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        onePanel.setSize(dimension);
        onePanel.setMaximumSize(dimension);
        onePanel.setMinimumSize(dimension);
        onePanel.setBackground(Color.LIGHT_GRAY);
        onePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        board.getField(row, col).insertJPanel(onePanel);
        createOnClickListener(onePanel, row, col);
        return onePanel;
    }

    /**
     * Initialization of players.
     */
    private void initPlayers() {    //initialization of players
        firstPlayer = new PlayerGUI(this);
        firstPlayer.initFirstPlayer(game.getFirstPlayer());
        currentPlayer = firstPlayer;

        secondPlayer = new PlayerGUI(this);
        secondPlayer.initSecondPlayer(game.getSecondPlayer());
    }

    /**
     * Click listener for playable field.
     * @param onePanel One panel to which will be one listener attached.
     * @param row Row position of panel.
     * @param col Column position of panel.
     */
    private void createOnClickListener(JPanel onePanel, int row, int col) {
        onePanel.addMouseListener(new MouseAdapter() {  //player want to insert disk into this field
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(game.currentPlayer().canPutDisk(board.getField(row, col))) {
                    onClickEvent(onePanel, row, col);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {    //chcek if player can insert disk into this field
                super.mouseEntered(e);
                if(game.currentPlayer().canPutDisk(board.getField(row, col))) {
                    showPossibleChanges(row, col);
                    onePanel.setBorder(greenBorder);
                    onePanel.repaint();
                } else {
                    onePanel.setBorder(redBorder);
                    onePanel.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                resetBorders();
            }
        });
    }

    /**
     * Events triggerd by click on playable panel.
     * @param onePanel Panel on which was clicked.
     * @param row Row position of panel.
     * @param col Column position of panel.
     */
    public void onClickEvent(JPanel onePanel, int row, int col) {   //handle inserting of disks
        resetBorders();
        currentPlayer.playerMove(row, col);
        recalculate();
        changePlayers();    //change players
        onePanel.setBorder(redBorder);
        onePanel.repaint();
        repaintBoard();
        if(frozenDisksHandler != null) {    //check if it is possible to freeze/unfreeze disks
            if(!frozenDisksHandler.getIfITimerIsRunning() && !frozenDisksHandler.getIfRTimerIsRunning() && !frozenDisksHandler.getIfICanFreeze() && !frozenDisksHandler.getIfICanUnfreeze()) {
                if(gameGUI.getRepeatFrozening())
                    frozenDisksHandler.lunchITimer();
            } else {
                if(frozenDisksHandler.getIfICanFreeze()) {
                    frozenDisksHandler.freezeDisks();
                }
                if(frozenDisksHandler.getIfICanUnfreeze()) {
                    frozenDisksHandler.unfreezeDisks();
                }
            }
        }
        if(checkGameStatus()) { //chcek if we have winner or not
            showMessage();
        }
    }

    /**
     * Repaint board (all panels)
     */
    private void repaintBoard() {
        for(int row = 1; row <= board.getSize(); row++) {
            for(int col = 1; col <= board.getSize(); col++) {
                if(board.getField(row, col).getDisk() != null) {
                    if(board.getField(row, col).isFrozen())
                        board.getField(row, col).getJPanel().setBackground(Color.BLUE);
                    else {
                        if(board.getField(row, col).getDisk().isWhite())
                            board.getField(row, col).getJPanel().setBackground(Color.WHITE);
                        else
                            board.getField(row, col).getJPanel().setBackground(Color.BLACK);
                    }
                } else
                    board.getField(row, col).getJPanel().setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    /**
     * Switch players.
     */
    private void changePlayers() {  //switch players
        firstPlayer.switchPlayer();
        secondPlayer.switchPlayer();
        game.nextPlayer();
        if(!firstPlayerPlay) {
            firstPlayerPlay = true;
            currentPlayer = firstPlayer;
            if(game.currentPlayer().getIAmComputer()) { //check if if first player is computer
                computerPlays(gameGUI.getFirstPlayerComputer1());
            }

        } else {
            firstPlayerPlay = false;
            currentPlayer = secondPlayer;
            if(game.currentPlayer().getIAmComputer()) { //check if if second player is computer
                computerPlays(gameGUI.getSecondPlayerComputer1());
            }
        }
    }

    /**
     * Computer plays.
     * @param simpleComputerPlays True if playing computer is SimpleComputer or AdvanceComputer.
     */
    private void computerPlays(boolean simpleComputerPlays) {   //ask computer to insert disk
        if(simpleComputerPlays)
            ((SimpleComputer)game.currentPlayer()).insertDisk(gameGUI);
        else
            ((AdvanceComputer)game.currentPlayer()).insertDisk(gameGUI);
    }

    /**
     * Check if player on the move got som available moves.
     * @param player Current player.
     * @return True if player do not have any available moves and there is no winner yet, False if he got move or game is over.
     */
    private boolean playerDontHaveMove(Player player) { //check if there is fields where current player can put disk
        if(player.playerGotPossibleMove() == 0 && winner == null)
            return true;
        return false;
    }

    /**
     * Recalculate both of players values.
     */
    private void recalculate() {    //recalculate players values
        firstPlayer.recalculate();
        secondPlayer.recalculate();
    }

    /**
     * Show which panels will turn if player insert disk into positions set by parameters.
     * @param row Row position of panel.
     * @param col Col position of panel.
     */
    private void showPossibleChanges(int row, int col) {    //show wich fields will turn
        Field currentField = board.getField(row, col);
        Field nextField;
        Field.Direction direction[] = Field.values();
        java.util.List<Integer> dirWhereICanTurnDisks = game.currentPlayer().getDirWhereICanTurnDisks();
        for(int i = 0; i < dirWhereICanTurnDisks.size(); i++) {
            nextField = currentField.nextField(direction[dirWhereICanTurnDisks.get(i)]);
            do {
                nextField.getJPanel().setBorder(greenBorder);
                nextField = nextField.nextField(direction[dirWhereICanTurnDisks.get(i)]);
            }while(nextField.getDisk().isWhite() != game.currentPlayer().isWhite());
            nextField.getJPanel().setBorder(greenBorder);
        }
    }

    /**
     * Set default borders to all of panels.
     */
    private void resetBorders() {   //set default borders
        for(int row = 1; row <= board.getSize(); row++) {
            for(int col = 1; col <= board.getSize(); col++) {
                board.getField(row, col).getJPanel().setBorder(grayBorder);
                board.getField(row, col).getJPanel().repaint();
            }
        }
    }

    /**
     * Undo move.
     */
    private void backButtonHandler() {  //back button handler
        Player playerToReturnMovement;
        if(game.getFirstPlayer().getHistoryOfMovements().size() > game.getSecondPlayer().getHistoryOfMovements().size())
            playerToReturnMovement = game.getFirstPlayer();
        else {
            if(game.getFirstPlayer().getHistoryOfMovements().size() < game.getSecondPlayer().getHistoryOfMovements().size())
                playerToReturnMovement = game.getSecondPlayer();
            else
                if(game.getFirstPlayer().getHistoryOfMovements().size() == 0)
                    return;
                else {
                    if(firstPlayerPlay)
                        playerToReturnMovement = game.getSecondPlayer();
                    else
                        playerToReturnMovement = game.getFirstPlayer();
                }
        }
        ArrayList<ArrayList> historyOfMovements = playerToReturnMovement.getHistoryOfMovements();
        ArrayList<Field> lastMove = historyOfMovements.get(historyOfMovements.size() - 1);
        historyOfMovements.remove(historyOfMovements.size() - 1);
        playerToReturnMovement.incCountOfDisks();
        for(int i = 0; i < lastMove.size(); i++) {
            lastMove.get(i).getPrevDisk();
        }
        repaintBoard();
        recalculate();
        changePlayers();
    }

    /**
     * Chceck if game is over.
     * @return True if game is over, False if it is not.
     */
    private boolean checkGameStatus() { //chcek if we got winner
        int finalScore = 0;
        if(game.getFirstPlayer().playerGotPossibleMove() == 0 && game.getSecondPlayer().playerGotPossibleMove() == 0 && playerDontHaveMove(game.getFirstPlayer()) && playerDontHaveMove(game.getSecondPlayer())) {
            if(game.getFirstPlayer().getScore() > game.getSecondPlayer().getScore()) {
                winner = game.getFirstPlayer();
                finalScore = game.getBoard().getSize() * game.getBoard().getSize() - game.getSecondPlayer().getScore();
                firstPlayerScore.setText(String.valueOf(finalScore));
            } else {
                if(game.getFirstPlayer().getScore() < game.getSecondPlayer().getScore()) {
                    winner = game.getSecondPlayer();
                    finalScore = game.getBoard().getSize() * game.getBoard().getSize() - game.getFirstPlayer().getScore();
                    secondPlayerScore.setText(String.valueOf(finalScore));
                } else
                    winner = null;
            }
            return true;
        }
        return false;
    }

    /**
     * Show how game ends.
     */
    private void showMessage() {    //show how it ens
        JOptionPane messageBox = new JOptionPane();
        if(checkGameStatus() && winner == null)
            messageBox.showMessageDialog(Main.mainFrame, "REMIZA", "Koniec hry.", JOptionPane.INFORMATION_MESSAGE, null);
        else
            messageBox.showMessageDialog(Main.mainFrame, "Vitazom je: " + winner.toString(), "Koniec hry.", JOptionPane.INFORMATION_MESSAGE, null);
    }

    /**
     * Show aborting dialog.
     */
    private void quitMessage() {    //quit game
        JDialog quitBox = new JDialog(Main.mainFrame);

        JRadioButton actualGame = new JRadioButton();
        actualGame.setText("Aktualnu");
        actualGame.setSelected(true);
        JRadioButton allGames = new JRadioButton();
        allGames.setText("Vsetky rozohrane");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(actualGame);
        buttonGroup.add(allGames);

        JButton saveAndQuit = new JButton("Ulozit a ukoncit");
        saveAndQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Ulozit a ukoncit");
                System.out.println(actualGame.isSelected());
            }
        });

        JButton quit = new JButton("ukoncit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(actualGame.isSelected())
                    mainScreen.exitSpecificGame(gameGUI);
                else
                    mainScreen.exitAllGames();
                quitBox.dispose();
            }
        });

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new FlowLayout());
        messagePanel.add(actualGame);
        messagePanel.add(allGames);
        messagePanel.add(saveAndQuit);
        messagePanel.add(quit);

        quitBox.setContentPane(messagePanel);
        quitBox.setModal(true);
        quitBox.setResizable(false);
        quitBox.setSize(300, 100);
        quitBox.setLocationRelativeTo(mainPanelBoard);
        quitBox.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        quitBox.setVisible(true);
    }

    /**
     * Get main panel.
     * @return Objects of JPanel.
     */
    public JPanel getMainPanel() {
        return mainPanelBoard;
    }

    /**
     * Get game.
     * @return Objects of Game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Get game UI.
     * @return Objects of GameGUI.
     */
    public GameGUI getGameGUI() {
        return gameGUI;
    }

    /**
     * Get first player name.
     * @return Objects of JLabel.
     */
    public JLabel getFistPlayerName() {
        return fistPlayerName;
    }

    /**
     * Get count of disk for firts player.
     * @return Objects of JLabel.
     */
    public JLabel getFirstPlayerCountOfDisks() {
        return firstPlayerCountOfDisks;
    }

    /**
     * Get count of move for first player.
     * @return Objects of JLabel.
     */
    public JLabel getFirstPlayerCountOfMove() {
        return firstPlayerCountOfMove;
    }

    /**
     * Get score for first player.
     * @return Objects of JLabel.
     */
    public JLabel getFirstPlayerScore() {
        return firstPlayerScore;
    }

    /**
     * Get panel for first player.
     * @return Objects of JLabel.
     */
    public JPanel getFirstPlayerJPanel() {
        return firstPlayerJPanel;
    }

    /**
     * Get panel of available disks for first player.
     * @return Objects of JLabel.
     */
    public JPanel getFirstPlayerAvailableDisks() {
        return firstPlayerAvailableDisks;
    }

    /**
     * Get first second name.
     * @return Objects of JLabel.
     */
    public JLabel getSecondPlayerName() {
        return secondPlayerName;
    }

    /**
     * Get count of disk for second player.
     * @return Objects of JLabel.
     */
    public JLabel getSecondPlayerCountOfDisks() {
        return secondPlayerCountOfDisks;
    }

    /**
     * Get count of move for second player.
     * @return Objects of JLabel.
     */
    public JLabel getSecondPlayerCountOfMove() {
        return secondPlayerCountOfMove;
    }

    /**
     * Get score for second player.
     * @return Objects of JLabel.
     */
    public JLabel getSecondPlayerScore() {
        return secondPlayerScore;
    }

    /**
     * Get panel for second player.
     * @return Objects of JLabel.
     */
    public JPanel getSecondPlayerJPanel() {
        return secondPlayerJPanel;
    }

    /**
     * Get panel of available disks for second player.
     * @return Objects of JLabel.
     */
    public JPanel getSecondPlayerAvailableDisks() {
        return secondPlayerAvailableDisks;
    }
}
