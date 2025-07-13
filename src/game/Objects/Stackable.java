package game.Objects;

/**
 * Represents a game component that has a measurable stackable level,
 * such as building levels in the tower construction.
 * Components like domes do not implement this interface.
 */
//Some buildings do not have levels at all. So, for stackable buildings, they implement stackable.
public interface Stackable {

    /**
     * Returns the current level of the stackable component.
     * @return the level (e.g., 1, 2, or 3 for tower blocks)
     */
    int getLevel();
}
