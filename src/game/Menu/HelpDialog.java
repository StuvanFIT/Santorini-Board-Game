package game.Menu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Displays a visual help dialog that introduces the different game components
 * (workers, tower levels, dome) with labels and images in a grid layout.
 */
public class HelpDialog extends Dialog {

    private final JFrame frame;

    /**
     * Constructs a HelpDialog
     * @param frame the parent frame to center the dialog on
     */
    // Constructor
    public HelpDialog(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Creates a labeled image component for a game object to be displayed in the help dialog.
     * @param path the image path
     * @param labelText the text shown below the image
     * @return a JLabel containing the image and its label
     */
    private static JLabel createLabeledImage(String path, String labelText) {
        ImageIcon icon = new ImageIcon(HelpDialog.class.getResource(path));
        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel label = new JLabel(labelText, scaledIcon, JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM); // Text goes below the image
        label.setHorizontalTextPosition(JLabel.CENTER); // Text is centered under the image
        label.setHorizontalAlignment(JLabel.CENTER); // Align everything center
        return label;
    }

    /**
     * Opens a new window displaying all the visual game components in a structured grid layout.
     * This dialog helps players recognize in-game elements.
     */
    public void show() {
        JFrame helpFrame = new JFrame("Help");
        helpFrame.setSize(600, 600);
        helpFrame.setLocationRelativeTo(frame);
        helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Summary of Components", JLabel.CENTER);
        title.setFont(new Font("Brush Script MT", Font.BOLD, 48));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        titlePanel.add(title, BorderLayout.CENTER);

        mainPanel.add(titlePanel, BorderLayout.NORTH); // ✅ only add titlePanel

        JPanel gridPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gridPanel.add(createLabeledImage("/images/GamePieces/Worker 1.png", "Player 1 Worker"));
        gridPanel.add(createLabeledImage("/images/GamePieces/Worker 2.png", "Player 2 Worker"));
        gridPanel.add(createLabeledImage("/images/GamePieces/Tower 1.png", "Tower Level 1"));
        gridPanel.add(createLabeledImage("/images/GamePieces/Tower 2.png", "Tower Level 2"));
        gridPanel.add(createLabeledImage("/images/GamePieces/Tower 3.png", "Tower Level 3"));
        gridPanel.add(createLabeledImage("/images/GamePieces/Dome.png", "Dome"));

        mainPanel.add(gridPanel, BorderLayout.CENTER);

        helpFrame.add(mainPanel);
        helpFrame.setVisible(true);
    }

}
