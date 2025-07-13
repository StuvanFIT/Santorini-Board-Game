package game.Objects;

import game.Capabilities.BackgroundRenderable;
import game.Capabilities.GameComponentEnums;
import java.net.URL;

/**
 * An abstract class representing a building component that can be placed on a cell.
 * Buildings may be walkable or buildable upon and are rendered as cell backgrounds.
 */
public abstract class Building extends GameComponent implements BackgroundRenderable {

    private boolean walkable;
    private boolean buildableOn;

    /**
     * Constructs a building object with specified interaction properties and image path.
     * @param walkable whether a worker can walk on this building
     * @param buildableOn whether another building can be built on top of this one
     * @param pathToIconFile the image path representing the building visually
     */
    public Building(boolean walkable, boolean buildableOn, String pathToIconFile) {
        super(pathToIconFile);
        this.walkable = walkable;
        this.buildableOn = buildableOn;
        generateCapabilities();
    }

    /**
     * @return true if a worker can walk on this building
     */
    public boolean isWalkableOn() {
        return this.walkable;
    }

    /**
     * @return true if this building can support additional buildings stacked on top
     */
    public boolean isBuildableOn() {
        return this.buildableOn;
    }

    /**
     * Generates capability flags based on whether the building is walkable or buildable.
     */
    @Override
    public void generateCapabilities() {

        //Can be walked on top of
        if (this.walkable) {
            addNewCapability(GameComponentEnums.WALKABLE_ON);
        }

        //If the building is stable, then it can be built on top
        if (this.buildableOn) {
            addNewCapability(GameComponentEnums.STABLE);
        }
    }

    /**
     * Adds a capability to render this building as a background image in a cell.
     */
    @Override
    public void addBackgroundRenderableCapability() {
        //Building levels would be the background of the cell panel
        addNewCapability(GameComponentEnums.RENDER_AS_BACKGROUND);
    }

    /**
     * @return the image path used for rendering the background of the cell panel
     */
    @Override
    public URL getBackgroundRenderImagePath() {
        return this.getBackgroundImagePath();
    }
}
