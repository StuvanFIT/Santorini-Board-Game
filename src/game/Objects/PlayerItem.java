package game.Objects;

import game.Player.Player;

/**
 * Not utilised in Sprint 2, however future implementations may require.
 * Represents an object that is owned or controlled by a player.
 * Typically implemented by components like workers that belong to a specific player.
 */
public interface PlayerItem {

    /**
     * Checks whether this item belongs to the given player.
     * @param player the player to check ownership against
     * @return boolean
     */
    boolean belongsTo(Player player);

    /**
     * Returns the player who owns this item.
     * @return the item's owner
     */
    Player getOwner();
}
