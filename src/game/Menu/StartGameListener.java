package game.Menu;

import game.GameLauncher;
import game.Turn.TurnManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * ActionListener that starts the game when the "Start Game" button is clicked.
 * It launches the game board and begins the turn loop in a new thread.
 */
public class StartGameListener implements ActionListener {

    private JFrame frame;

    /**
     * Constructs the listener with a reference to the main menu frame.
     * @param frame the main menu frame to be closed when the game starts
     */
    public StartGameListener(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Launches the game window and starts the game loop when triggered.
     * @param e the ActionEvent triggered by the button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose(); // Close main menu

        GameLauncher gameLauncher = new GameLauncher();
        JFrame gameWindowFrame = gameLauncher.launchGame();

        //Get the turn manager produced by the game launcher:
        TurnManager turnManager = gameLauncher.getTurnManager();

        //Start the game loop
        new Thread(() -> turnManager.startGameLoop(gameWindowFrame)).start();


    }
}