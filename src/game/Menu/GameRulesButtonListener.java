package game.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * An ActionListener that opens the game rules dialog when the
 * corresponding button is clicked.
 */
public class GameRulesButtonListener implements ActionListener {

    private final JFrame frame;

    /**
     * Constructs the listener with a reference to the parent frame.
     * @param frame the parent JFrame to position the dialog relative to
     */
    public GameRulesButtonListener(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Displays the GameRulesDialog when the action is performed.
     * @param e the ActionEvent triggered by the button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Display game rules dialog
        Dialog dialog = new GameRulesDialog(frame);
        dialog.show();
    }


}
