package game;

import game.Factories.GodCardFactory;
import game.Factories.PlayerFactory;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.GameBoard.Coordinate;
import game.GameBoard.StandardBoard;
import game.GodCards.GodCard;
import game.Injectors.ArtemisInjector;
import game.Injectors.DemeterInjector;
import game.Injectors.ZeusInjector;
import game.Managers.BenevolenceManager;
import game.Managers.TimerUIManager;
import game.Player.Player;
import game.Turn.TurnManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Responsible for setting up and launching the main Santorini game window.
 * Initializes the board, players, God cards, and event listeners for the game.
 */
public class GameLauncher {

    private TurnManager turnManager;

    /**
     * Launches the game by creating the main game window,
     * initializing the board, placing players, and rendering UI elements.
     * @return the main game JFrame
     */
    public JFrame launchGame() {
        Board board = new StandardBoard();

        // Setup main game window
        JFrame frame = new JFrame("Santorini Board Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(
            new GridLayout(board.getBoardWidth(), board.getBoardHeight(), 2, 2));
        panel.setPreferredSize(new Dimension(500, 500));

        JPanel container = new JPanel(new GridBagLayout());
        container.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Add cell panels to grid
        for (int x = 0; x < board.getBoardWidth(); x++) {
            for (int y = 0; y < board.getBoardHeight(); y++) {
                Coordinate coordinate = new Coordinate(x, y);
                Cell cell = board.getCell(coordinate);
                CellPanel cellPanel = cell.getCellPanel();

                // Cell Panel GUI Configuration
                cellPanel.setBackground(Color.LIGHT_GRAY);
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                cellPanel.setOpaque(true);

                cellPanel.addMouseListener(new MouseAdapter() {
                    final Color originalColour = cellPanel.getBackground();

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        cellPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                        cellPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (!board.getBoardUIManager().getHighlightedCells().contains(cell)) {
                            cellPanel.setBackground(originalColour);
                        }
                        cellPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    }
                });

                panel.add(cellPanel);
            }
        }

        GodCardFactory godFactory = createGodFactory();
        int timeDurationSeconds = 1000;
        PlayerFactory playerFactory = new PlayerFactory(godFactory, timeDurationSeconds);
        List<Player> players = playerFactory.createPlayers(board);

        // Setup Turn Manager
        TurnManager turnManager = new TurnManager(players);
        board.setTurnManager(turnManager);
        turnManager.setCurrentBoard(board);
        this.turnManager = turnManager;

        // Render God Card onto screen
        initialiseGodCardRendering(frame, players);


        // Create EAST container
        JPanel combinedEastPanel = new JPanel();
        combinedEastPanel.setLayout(new BoxLayout(combinedEastPanel, BoxLayout.Y_AXIS));
        combinedEastPanel.setBackground(Color.WHITE);

        combinedEastPanel.add(initialiseTimers(players));
        combinedEastPanel.add(Box.createVerticalStrut(20));

        frame.add(combinedEastPanel, BorderLayout.EAST);




        container.add(panel);
        frame.add(container, BorderLayout.CENTER);
        frame.setVisible(true);

        return frame;
    }

    /**
     * Creates and returns a factory instance with supported God cards.
     * @return a configured GodCardFactory
     */
    private GodCardFactory createGodFactory() {
        return new GodCardFactory(
            new ArtemisInjector().injectNewObject(),
            new DemeterInjector().injectNewObject(),
                new ZeusInjector().injectNewObject()
        );
    }

    private JPanel initialiseTimers(List<Player> players){
        TimerUIManager timerUIManager = new TimerUIManager();
        timerUIManager.initialiseTimerRendering(players);
        return timerUIManager.getTimerPanel();
    }


    /**
     * Displays the God cards for each player on the left side of the screen.
     * @param frame the main game window
     * @param players the list of players with assigned God cards
     */
    private void initialiseGodCardRendering(JFrame frame, List<Player> players) {
        JPanel godCardsPanel = new JPanel();
        godCardsPanel.setLayout(new BoxLayout(godCardsPanel, BoxLayout.Y_AXIS));

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Left padding
        wrapperPanel.add(godCardsPanel, BorderLayout.CENTER);
        frame.add(wrapperPanel, BorderLayout.WEST);

        for (Player player : players) {
            GodCard godCard = player.getGodCard();
            ImageIcon godImageIcon = new ImageIcon(godCard.getBackgroundImagePath());
            ImageIcon godImageScaled = Utilities.scaleIcon(godImageIcon, 130, 180);

            JLabel label = new JLabel(godImageScaled);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setText("<html><div style='text-align: center; width: 120px;font-weight:bold;'>"
                + "<p style='text-align: center; width: 120px; text-decoration:underline; font-weight:bold;'>"
                + player.getPlayerId().name() + "</p>"
                + godCard.getName() + ": " + godCard.getDescription()
                + "</div></html>");
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.BOTTOM);

            JPanel cardPanel = new JPanel();
            cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
            cardPanel.setBackground(Color.WHITE);
            cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            cardPanel.setMaximumSize(new Dimension(200, 330));
            cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            cardPanel.add(label);

            godCardsPanel.add(Box.createVerticalStrut(30));
            godCardsPanel.add(cardPanel);
        }
    }


    /**
     * Returns the turn manager responsible for handling game logic.
     * @return the TurnManager instance
     */
    public TurnManager getTurnManager() {
        return this.turnManager;
    }
}
