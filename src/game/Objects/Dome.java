package game.Objects;

import game.Capabilities.BackgroundRenderable;
import game.Capabilities.GameComponentEnums;
import java.net.URL;

/**
 * Represents the final level of a tower that prevents further building or movement.
 * Domes are neither walkable nor buildable on and are considered unstable.
 */
public class Dome extends Building implements BackgroundRenderable {

    /**
     * Constructs a Dome object with its image and capability flags.
     * Automatically sets it as non-walkable, non-buildable.
     */
    public Dome() {
        super(false, false, "/images/GamePieces/Dome.png");
        generateCapabilities();
    }

    /**
     * Adds the dome-specific capabilities to the component, including rendering and instability.
     */
    @Override
    public void generateCapabilities() {
        super.generateCapabilities();
        addBackgroundRenderableCapability();
        addNewCapability(GameComponentEnums.UNSTABLE);
    }

    /**
     * Marks this object to be rendered as a background on a cell panel.
     */
    @Override
    public void addBackgroundRenderableCapability() {
        //Building levels would be the background of the cell panel
        addNewCapability(GameComponentEnums.RENDER_AS_BACKGROUND);
    }

    /**
     * @return the image path used for rendering this dome as a background
     */
    @Override
    public URL getBackgroundRenderImagePath() {
        return this.getBackgroundImagePath();

    }

}
