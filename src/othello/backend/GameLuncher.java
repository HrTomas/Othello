package othello.backend;

import othello.backend.AI.AdvanceComputer;
import othello.backend.AI.SimpleComputer;
import othello.backend.board.Board;
import othello.backend.game.Game;
import othello.backend.game.Player;
import othello.backend.game.ReversiRules;
import othello.backend.board.FrozenDisksHandler;
import othello.frontend.GameGUI;

import java.io.Serializable;

/**
 * Class represents application backend.
 * Create all necessary objects for proper functionality of game.
 *
 * @author Tomas Hreha
 */
public class GameLuncher implements Serializable {

    private ReversiRules rules;
    private Board board;
    private Game game;
    private Player blackPlayer;
    private Player whitePlayer;
    private FrozenDisksHandler frozenDisksHandler;
    private SimpleComputer simpleComputer;
    private AdvanceComputer advanceComputer;

    private boolean firstPlayerIsSimpleCmp = false;
    private boolean secondPlayerIsSimpleCmp = false;

    private boolean loadeGame = false;

    transient private GameGUI gameGUI;

    /**
     * Constructor.
     * Set to which game UI belongs this launcher
     *
     * @param gameGUI UI for this game.
     */
    public GameLuncher(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        createGame();
    }

    /**
     * Set that game is saved.
     */
    public void setLoadGame() {
        this.loadeGame = true;
    }

    /**
     * Create new game.
     */
    private void createGame() {

        rules = new ReversiRules(gameGUI.getSizeOfBoard());
        board = new Board(rules);
        game = new Game(board);
        game.setNameOfGame(gameGUI.getNameOfGame());

        if(gameGUI.getFirstPlayerHuman())
            blackPlayer = new Player(false, gameGUI.getFirstPlayerName(), false);
        else {
            if(gameGUI.getSecondPlayerComputer1())
                blackPlayer = new SimpleComputer(false, gameGUI.getFirstPlayerName(), true);
            else {
                blackPlayer = new AdvanceComputer(false, gameGUI.getFirstPlayerName(), true);
                ((AdvanceComputer) blackPlayer).initComputerPlayer(board);
            }
        }
        game.addPlayer(blackPlayer);

        if(gameGUI.getSecondPlayerHuman())
            whitePlayer = new Player(true, gameGUI.getSecondPlayerName(), false);
        else {
            if(gameGUI.getSecondPlayerComputer1())
                whitePlayer = new SimpleComputer(true, gameGUI.getSecondPlayerName(), true);
            else {
                whitePlayer = new AdvanceComputer(true, gameGUI.getSecondPlayerName(), true);
                ((AdvanceComputer) whitePlayer).initComputerPlayer(board);
            }
        }
        game.addPlayer(whitePlayer);

        if(gameGUI.getDiskFrozen()) {
            frozenDisksHandler = new FrozenDisksHandler(game, gameGUI.getInitiateFrozening(),
                    gameGUI.getPeriodOfFrozening(), gameGUI.getCountOfDiskToFrozen());
        }
    }

    /**
     * Get game.
     * @return Object of Game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Get rules
     * @return Object of ReversiRules.
     */
    public ReversiRules getRules() {
        return rules;
    }

    /**
     * Get board.
     * @return Object of Board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Get player (human/SimpleComputer/AdvanceComputer).
     * @return Object of Player.
     */
    public Player getBlackPlayer() {
        if(blackPlayer.getIAmComputer()) {
            if(loadeGame) {
                if(firstPlayerIsSimpleCmp)
                    return (SimpleComputer) blackPlayer;
                else
                    return (AdvanceComputer) blackPlayer;
            } else {
                if(gameGUI.getFirstPlayerComputer1()) {
                    firstPlayerIsSimpleCmp = true;
                    return (SimpleComputer) blackPlayer;
                } else
                    return (AdvanceComputer) blackPlayer;
            }
        } else
            return blackPlayer;
    }

    /**
     * Get player (human/SimpleComputer/AdvanceComputer).
     * @return Object of Player.
     */
    public Player getWhitePlayer() {
        if(whitePlayer.getIAmComputer()) {
            if(loadeGame) {
                if(secondPlayerIsSimpleCmp)
                    return (SimpleComputer) whitePlayer;
                else
                    return (AdvanceComputer)whitePlayer;
            } else {
                if(gameGUI.getSecondPlayerComputer1()) {
                    secondPlayerIsSimpleCmp = true;
                    return (SimpleComputer) whitePlayer;
                } else
                    return (AdvanceComputer)whitePlayer;
            }
        } else
        return whitePlayer;
    }

    /**
     * If player is computer, get if it is SimpleComputer or AdvanceComputer.
     * @return True if player is object of SimpleComputer, False if it is object of AdvanceComputer.
     */
    public boolean getIfFirstPlayerIsSimpleCmp() {
        return firstPlayerIsSimpleCmp;
    }

    /**
     * If player is computer, get if it is SimpleComputer or AdvanceComputer.
     * @return True if player is object of SimpleComputer, False if it is object of AdvanceComputer.
     */
    public boolean getIfSecondPlayerIsSimpleCmp() {
        return secondPlayerIsSimpleCmp;
    }

    /**
     * Get if game allows disk freezing.
     * @return Object of FrozenDiskHandler, or null if game does not allow disk freezing.
     */
    public FrozenDisksHandler getFrozenDisksHandler() {
        return frozenDisksHandler;
    }

    /**
     * Get game UI.
     * @return Object of GameGUI.
     */
    public GameGUI getGameGUI() {
        return gameGUI;
    }
}
