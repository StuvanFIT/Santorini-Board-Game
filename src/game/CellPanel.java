package game;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A custom JPanel used to represent a single cell in the game board.
 * Supports setting a background image that is scaled to fit the panel.
 */
//We create a class called Cell Panel that extends the java swing JPanel, to modify the paintComponent
public class CellPanel extends JPanel {

    private Image image;

    /**
     * Constructs a CellPanel with a background image.
     * @param imagePath the path to the image resource to be used as background
     */
    public CellPanel(String imagePath) {

        if (imagePath != null) {
            // Load the image
            URL url = CellPanel.class.getResource(imagePath);
            setImagePanelBackground(url);
        }

    }

    /**
     * Sets the background image for the panel using a URL.
     * @param imagePath the URL of the image to set as background
     */
    public void setImagePanelBackground(URL imagePath) {
        this.image = new ImageIcon(imagePath).getImage();
    }

    /**
     * Paints the component and draws the background image scaled to the panel size.
     * @param g the Graphics context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the image as the background
        //(0,0) = draw starting from the left corner
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

}
