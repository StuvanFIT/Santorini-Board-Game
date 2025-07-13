package game;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Utility class containing helper methods for image operations.
 */
public class Utilities {

    /**
     * Scales a given ImageIcon to the specified width and height using smooth scaling.
     *
     * @param icon  the original ImageIcon to be scaled
     * @param width the desired width in pixels
     * @param height the desired height in pixels
     * @return a new ImageIcon scaled to the given dimensions
     */
    //Helper function to help scale icons
    public static ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
