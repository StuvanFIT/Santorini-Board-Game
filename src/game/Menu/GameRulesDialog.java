package game.Menu;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Displays a scrollable dialog window containing the rules for playing Santorini.
 * This class extends the abstract Dialog class.
 */
public class GameRulesDialog extends Dialog {

    private final JFrame frame;

    /**
     * Constructs a GameRulesDialog positioned relative to the provided frame.
     * @param frame the parent frame used for positioning
     */
    public GameRulesDialog(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Opens a new window containing a scrollable text area with the game rules.
     */
    @Override
    public void show() {
        JFrame gameRulesFrame = new JFrame("Game Rules");
        gameRulesFrame.setSize(600, 600);
        gameRulesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea gameRulesTextArea = new JTextArea();
        gameRulesTextArea.setText(getRulesText());
        gameRulesTextArea.setWrapStyleWord(true);
        gameRulesTextArea.setLineWrap(true);
        gameRulesTextArea.setEditable(false);
        gameRulesTextArea.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(gameRulesTextArea);

        gameRulesFrame.add(scrollPane);
        gameRulesFrame.setVisible(true);
    }

    /**
     * Returns a multi-line string containing the full rules of the game.
     * @return the rules as a formatted String
     */
    private String getRulesText() {
        return """
            SANTORINI - HOW TO PLAY

            Win Objective:
            - Be the first player to move one of your workers onto the 3rd level of a building
            OR trap the opposing player's workers so that they cannot move during their phase.

            Setup:
            - Each player has two workers that are randomly placed onto the board at the start.
            - Players take turns moving their workers and placing buildings onto the board.

            Gameplay:
            - Each player will have a move and build phase during their turn.
            - Move: Move one of your workers to an adjacent unoccupied space.
              You may move up one level, down any number of levels, or stay on the same level.
            - Build: After moving, build a block on an adjacent space (levels 1, 2, 3, then Dome).
            - A worker cannot move onto a space with a Dome.

            Special Rules:
            - God Powers may modify basic movement, building rules or even win conditions.

            Have fun!
            """;
    }
}
