package game.Objects;

import game.Capabilities.CapabilitySet;
import game.Capabilities.GameComponentEnums;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Represents the base class for all game components that can be placed on the board,
 * such as workers or buildings. Encapsulates image rendering and capability logic.
 */
public abstract class GameComponent {

    protected final CapabilitySet capabilitySet = new CapabilitySet();
    private final URL pathToIconFile;

    /**
     * Constructs a GameComponent by resolving the provided image path.
     * @param pathToIconFile the relative path to the image used for this component
     */
    public GameComponent(String pathToIconFile) {

        URL url = GameComponent.class.getResource(pathToIconFile);
        if (url == null) {
            throw new IllegalArgumentException("Image not found: " + pathToIconFile);
        }

        this.pathToIconFile = url;
    }

    /**
     * @return the icon used to visually represent this component
     */
    public ImageIcon getIcon() {
        return new ImageIcon(pathToIconFile);
    }

    /**
     * Abstract method for subclasses to define their specific capabilities.
     */
    public abstract void generateCapabilities();

    /**
     * Adds a new capability to this component.
     * @param newCapability the capability to be added
     */
    public void addNewCapability(Enum<?> newCapability) {
        capabilitySet.addCapability(newCapability);
    }

    /**
     * Removes a capability from this component.
     * @param newCapability the capability to be removed
     */
    public void deleteNewCapability(Enum<?> newCapability) {
        capabilitySet.removeCapability(newCapability);
    }

    /**
     * Checks if the component has the specified capability.
     * @param capability the capability to check
     * @return boolean
     */
    public boolean hasCapability(Enum<?> capability) {
        return capabilitySet.containsCapability(capability);
    }

    /**
     * @return the background image path associated with this component
     */
    public URL getBackgroundImagePath() {
        return pathToIconFile;
    }

    /**
     * @return true if this component should be rendered as a background
     */
    public boolean isBackgroundRenderable() {
        return hasCapability(GameComponentEnums.RENDER_AS_BACKGROUND);
    }

}
