package game.Objects;

/**
 * Represents a stackable building level (1, 2, or 3) used in the construction of towers.
 * Unlike Dome, these levels are walkable and can be built upon.
 * This class unifies all tower levels into one by parameterizing the level and icon path.
 */
//We make lvl1,lvl2,lvl3 into one class, instead of having a separate class for each lvl, we just define the level and icon image.
public class BuildingLevel extends Building implements Stackable {


    private int level;

    /**
     * Constructs a BuildingLevel with the specified level and icon.
     * @param level the numeric level of the tower (1–3)
     * @param pathToIconFile the path to the image icon representing this level
     */
    public BuildingLevel(int level, String pathToIconFile) {
        super(true, true, pathToIconFile);
        this.level = level;
        addBackgroundRenderableCapability();

    }

    /**
     * Returns the current level of the building.
     * @return the tower level (1–3)
     */
    @Override
    public int getLevel() {
        return this.level;
    }

}
