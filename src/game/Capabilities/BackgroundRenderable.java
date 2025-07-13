package game.Capabilities;

import java.net.URL;

/**
 * The BackgroundRenderable interface allows a game component to be rendered as a background image
 * within a cell panel on the game board.,
 */
public interface BackgroundRenderable {

    /**
     * Adds the RENDER_AS_BACKGROUND capability to the component.
     */
    void addBackgroundRenderableCapability();

    /**
     * Returns the file path o the image that should be used
     * for rendering this object as a background in a cell.
     * @return the URL to the background image resource
     */
    URL getBackgroundRenderImagePath();
}
