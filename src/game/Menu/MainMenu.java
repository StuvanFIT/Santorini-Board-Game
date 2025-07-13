package game.Menu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * The main entry window for the Santorini game, providing buttons to
 * start the game, view the game rules, or access the help dialog.
 */
public class MainMenu {

    /**
     * Constructs the main menu window with interactive buttons and a title.
     * Initializes and displays the application start screen.
     */
    // Constructor
    public MainMenu() {
        // Creates a frame which is the window
        JFrame frame = new JFrame("Santorini");
        frame.setSize(500, 500);
        // Closing the window will also terminate the application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Uses the Grid Bag Layout Manager
        frame.setLayout(new GridBagLayout());

        // Grid Constraints Declaration
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Santorini title
        JLabel title = new JLabel("Santorini");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Brush Script MT", Font.BOLD, 64));
        frame.add(title, gbc);

        Dimension buttonSize = new Dimension(250, 40);

        // Start button
        gbc.gridy++;
        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(buttonSize);
        frame.add(startButton, gbc);
        startButton.addActionListener(new StartGameListener(frame));

        // Game Rules button
        gbc.gridy++;
        JButton gameRulesButton = new JButton("Game Rules");
        gameRulesButton.setPreferredSize(buttonSize);
        frame.add(gameRulesButton, gbc);
        frame.setVisible(true);
        gameRulesButton.addActionListener(new GameRulesButtonListener(frame));

        // Help button
        gbc.gridy++;
        JButton helpButton = new JButton("Help");
        helpButton.setPreferredSize(buttonSize);
        frame.add(helpButton, gbc);
        frame.setVisible(true);
        helpButton.addActionListener(new HelpButtonListener(frame));
    }
}
