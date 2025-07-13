package game.Turn;

/*
The turn:
-belongs to one player
-tracks a current turnState
-controls the logic of moving, building and god power effects
-This turn state transitions through phases: MOVE, BUILD, GOD ABILITY, END
*/

import game.Player.Player;

/**
 * Represents a single turn taken by a player.
 * Each turn progresses through distinct phases: Move, Build, God Ability, and End.
 * The Turn object maintains the player context and current active phase.
 */
public class Turn {

    private Player player;
    private TurnPhase currentPhase;

    /**
     * Constructs a new Turn instance for the given player.
     * The turn begins in the MovePhase.
     * @param player the player taking this turn
     */
    public Turn(Player player) {
        this.player = player;
        this.currentPhase = new MovePhase(this);
    }

    /**
     * Returns the player associated with this turn.
     * @return the player taking the turn
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Returns the current phase of the turn.
     * @return the current TurnPhase
     */
    public TurnPhase getCurrentPhase() {
        return this.currentPhase;
    }

    /**
     * Sets the current phase of the turn manually.
     * @param phase the TurnPhase to set as current
     */
    public void setCurrentPhase(TurnPhase phase) {
        this.currentPhase = phase;
    }

    /**
     * Advances the turn to the next phase in the sequence.
     */
    public void advancePhase() {
        this.currentPhase = currentPhase.nextTurnPhase(this);
    }


}


