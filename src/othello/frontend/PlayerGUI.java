package othello.frontend;

import othello.backend.game.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static java.awt.Component.CENTER_ALIGNMENT;

/**
 * Class which handle UI events for player.
 *
 * @author Tomas Hreha
 */
public class PlayerGUI {

    public JLabel playerName;
    private JLabel playerCountOfDisks;
    private JLabel playerCountOfMove;
    private JLabel playerScore;
    private JPanel playerJPanel;
    private JPanel playerAvailableDisks;

    private Border greenBorder = BorderFactory.createLineBorder(Color.GREEN);
    private boolean thisPlayerPlays = false;
    private boolean thisFirstComputer = false;

    private Player player;

    private BoardGUI boardGUI;

    /**
     * Constructor.
     * @param boardGUI Set from which board belongs this player.
     */
    public PlayerGUI(BoardGUI boardGUI) {
        this.boardGUI = boardGUI;
    }

    /**
     * Initialization of first player.
     * @param player First player of game.
     */
    public void initFirstPlayer(Player player) {
        if(player.getIAmComputer()) {
            if(boardGUI.getGameGUI().getFirstPlayerComputer1())
                thisFirstComputer = true;
            else
                thisFirstComputer = false;
        }
        this.player = player;
        playerName = boardGUI.getFistPlayerName();
        playerCountOfDisks = boardGUI.getFirstPlayerCountOfDisks();
        playerCountOfMove = boardGUI.getFirstPlayerCountOfMove();
        playerScore = boardGUI.getFirstPlayerScore();
        playerJPanel = boardGUI.getFirstPlayerJPanel();
        playerAvailableDisks = boardGUI.getFirstPlayerAvailableDisks();

        playerName.setText(boardGUI.getGame().getFirstPlayer().toString());
        recalculate();
        showAvailableDisks();

        switchPlayer();
    }

    /**
     * Initialization of second player.
     * @param player Second player.
     */
    public void initSecondPlayer(Player player) {
        if(player.getIAmComputer()) {
            if(boardGUI.getGameGUI().getSecondPlayerComputer1())
                thisFirstComputer = true;
            else
                thisFirstComputer = false;
        }
        this.player = player;
        playerName = boardGUI.getSecondPlayerName();
        playerCountOfDisks = boardGUI.getSecondPlayerCountOfDisks();
        playerCountOfMove = boardGUI.getSecondPlayerCountOfMove();
        playerScore = boardGUI.getSecondPlayerScore();
        playerJPanel = boardGUI.getSecondPlayerJPanel();
        playerAvailableDisks = boardGUI.getSecondPlayerAvailableDisks();

        playerName.setText(player.toString());
        recalculate();
        showAvailableDisks();
    }

    /**
     * Put disk into board.
     * @param row Row position, where player want to put disk.
     * @param col Column position, where player want to put disk.
     */
    public void playerMove(int row, int col) {
        player.putDisk(boardGUI.getGame().getBoard().getField(row, col)); //put disk
    }

    /**
     * Swich players.
     */
    public void switchPlayer() {
        if(!thisPlayerPlays) {
            playerJPanel.setBorder(greenBorder);
        } else
            playerJPanel.setBorder(null);
        thisPlayerPlays = !thisPlayerPlays;
    }

    /**
     * Recalculate players values.
     */
    public void recalculate() {    //recalculate players values
        playerCountOfMove.setText(String.valueOf(player.playerGotPossibleMove()));
        playerScore.setText(String.valueOf(player.getScore()));
        playerCountOfDisks.setText(String.valueOf(player.getCountOfDisks()));
        showAvailableDisks();
    }

    /**
     * Show available disks.
     */
    private void showAvailableDisks() {
        playerAvailableDisks.removeAll();
        if(player.getCountOfDisks() > (boardGUI.getGame().getBoard().getSize() * boardGUI.getGame().getBoard().getSize() / 4)) {
            playerAvailableDisks.add(createSmallLine((boardGUI.getGame().getBoard().getSize() * boardGUI.getGame().getBoard().getSize() / 4), player.isWhite()), CENTER_ALIGNMENT);
            playerAvailableDisks.add(createSmallLine(player.getCountOfDisks() - (boardGUI.getGame().getBoard().getSize() * boardGUI.getGame().getBoard().getSize() / 4), player.isWhite()), CENTER_ALIGNMENT);
        }
        else
            playerAvailableDisks.add(createSmallLine(boardGUI.getGame().getSecondPlayer().getCountOfDisks(), player.isWhite()), CENTER_ALIGNMENT);
    }

    /**
     * Create small line of available disks.
     * @param countOfDisks Count of players available disks.
     * @param isWhite Players color.
     * @return Object of JPanel.
     */
    private JPanel createSmallLine(int countOfDisks, boolean isWhite) {
        JPanel oneLine = new JPanel();
        oneLine.setLayout(new BoxLayout(oneLine, BoxLayout.X_AXIS));
        for(int i = 0; i < countOfDisks; i++) {
            oneLine.add(createSmallPanel(isWhite), CENTER_ALIGNMENT);
        }
        return oneLine;
    }

    /**
     * Create small disk for showing available disks.
     * @param isWhite Players color.
     * @return Object of JPanel represents one available disk.
     */
    private JPanel createSmallPanel(boolean isWhite) {  //create one playable field
        Dimension dimension = new Dimension(10, 10);
        JPanel onePanel = new JPanel();
        onePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        onePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        onePanel.setSize(dimension);
        onePanel.setMaximumSize(dimension);
        onePanel.setMinimumSize(dimension);
        if(isWhite)
            onePanel.setBackground(Color.WHITE);
        else
            onePanel.setBackground(Color.BLACK);
        onePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return onePanel;
    }

    /**
     * Get this player.
     * @return Object of PlayerGUI.
     */
    public PlayerGUI getPlayer() {
        return this;
    }
}
