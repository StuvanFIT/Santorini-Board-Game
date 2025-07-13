package game.GodCards;

import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Objects.GameComponent;
import game.Objects.Worker;
import game.Turn.Turn;
import game.Turn.TurnPhase;

import java.util.List;

/**
 * The GodCard class represents a special ability assigned to a player.
 * Each GodCard has a name, description, and image, and implements GodAbility.
 */
public abstract class GodCard extends GameComponent{

    private String name;
    private String description;

    /**
     * Constructs a GodCard with a name, description, and icon path.
     * @param name the name of the god
     * @param description a short summary of the god's power
     * @param pathToFileIcon path to the god card image
     */
    public GodCard(String name, String description, String pathToFileIcon) {
        super(pathToFileIcon);
        this.name = name;
        this.description = description;
    }

    public void apply(Board board, Turn turn, Worker worker, TurnPhase phase) {}





    /**
     * Returns the name of the god.
     * @return god name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the god's power.
     * @return god description
     */
    public String getDescription() {
        return description;
    }
}

