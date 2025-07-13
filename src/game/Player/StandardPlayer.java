package game.Player;

/**
 * Represents a standard player with a name and associated player ID.
 * Extends the abstract Player class.
 */
public class StandardPlayer extends Player {

    private String playerName;

    /**
     * Constructs a new StandardPlayer with a name and player ID.
     * @param name the display name of the player
     * @param playerId the identifier associated with the player
     */
    public StandardPlayer(String name, PlayerId playerId) {
        this.playerName = name;
        this.setPlayerId(playerId);
    }
}
