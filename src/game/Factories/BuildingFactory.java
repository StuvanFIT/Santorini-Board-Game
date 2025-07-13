package game.Factories;

import game.Objects.Building;
import game.Objects.BuildingLevel;
import game.Objects.Dome;

/**
 * The BuildingFactory creates Building objects such as Tower levels or Domes
 * based on the given level input.
 */
public class BuildingFactory implements Factory<Building, Integer> {

    /**
     * Returns a Building based on the input level.
     * Level 1–3 returns a BuildingLevel, level 4 returns a Dome.
     * @param inputKey the level of the building to create
     * @return the corresponding Building object
     */
    @Override
    public Building getComponent(Integer inputKey) {
        // Handle inputKey to create different buildings based on level
        int level = inputKey; // Autoboxing already happens with Integer
        return switch (level) {
            case 1 -> new BuildingLevel(1, "/images/GamePieces/Tower 1.png");
            case 2 -> new BuildingLevel(2, "/images/GamePieces/Tower 2.png");
            case 3 -> new BuildingLevel(3, "/images/GamePieces/Tower 3.png");
            case 4 -> new Dome();
            default -> throw new IllegalArgumentException("Invalid Level: " + level);
        };
    }
}
