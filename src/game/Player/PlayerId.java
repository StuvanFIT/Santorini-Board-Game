package game.Player;

import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Enum representing player identifiers with associated image paths and icons.
 * Each player ID holds a reference to its image resource used in the game UI.
 */
public enum PlayerId {
    PLAYER_1("/images/GamePieces/Worker 1.png"),
    PLAYER_2("/images/GamePieces/Worker 2.png");

    private ImageIcon imageIcon;
    private URL url;
    private String imagePathString;

    /**
     * Constructs a PlayerId with the given image path.
     * @param imagePath the relative path to the player's image resource
     */
    PlayerId(String imagePath) {
        this.imagePathString = imagePath;

        URL url = PlayerId.class.getResource(imagePath);
        this.url = url;
        this.imageIcon = new ImageIcon(url);
    }

    /**
     * Returns the image icon associated with this player.
     * @return the player's image icon
     */
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    /**
     * Returns the raw image path as a string.
     * @return the image path string
     */
    public String getImagePathString() {
        return this.imagePathString;
    }

    /**
     * Returns the URL pointing to the image resource.
     * @return the image resource URL
     */
    public URL getURL() {
        return this.url;
    }


}

