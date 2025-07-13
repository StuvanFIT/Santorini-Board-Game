package game.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * An ActionListener that displays the help dialog showing
 * game components when the Help button is clicked.
 */
public class HelpButtonListener implements ActionListener {

    private final JFrame frame;

    /**
     * Constructs the listener with a reference to the main application frame.
     * @param frame the parent frame to align the dialog with
     */
    public HelpButtonListener(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Opens the HelpDialog when the button is clicked.
     * @param e the triggered action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Display help dialog
        Dialog dialog = new HelpDialog(frame);
        dialog.show();
    }

}
