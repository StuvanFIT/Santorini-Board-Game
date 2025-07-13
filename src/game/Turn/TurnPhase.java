package game.Turn;


import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Managers.PhaseUIManager;

import java.util.List;

/**
 * Abstract class representing a phase in a player's turn.
 * Each TurnPhase controls a segment of the gameplay such as Move, Build, or End.
 * Subclasses define specific logic for how the phase behaves and transitions.
 */
public abstract class TurnPhase {

    protected boolean isComplete = false;
    protected List<Cell> currentValidCells;
    private boolean godAbilityActivated = false;
    private String name;
    private PhaseUIManager phaseUIManager;
    private TurnManager turnManager;

    /**
     * Constructs a TurnPhase with a specific name.
     * @param name the name of the phase
     */
    public TurnPhase(String name) {
        this.name = name;
    }

    /**
     * Checks whether this phase has completed.
     * @return boolean
     */
    public boolean isComplete() {
        return isComplete;
    }

    /**
     * Sets the completion status of the phase.
     * @param bool boolean
     */
    public void setComplete(Boolean bool) {
        isComplete = bool;
    }

    /**
     * Returns phase ui manager
     * @return PhaseUIManager
     */
    public PhaseUIManager getPhaseUIManager() {
        return phaseUIManager;
    }

    /**
     * Returns  turn manager
     * @return TurnManager
     */
    public TurnManager getTurnManager(){
        return turnManager;
    }

    /**
     * Sets  turn manager
     */
    public void setTurnManager(TurnManager turnManager){
        this.turnManager = turnManager;

    }

    /**
     * Updates the valid cells that can be acted upon during this phase.
     * @param validCells the list of valid Cell objects
     */
    public void setCurrentValidMoves(List<Cell> validCells) {
        this.currentValidCells = validCells;
    }

    /**
     * retrieves the valid cells that can be acted upon during this phase.
     */
    public List<Cell> getCurrentValidMoves() {
        return this.currentValidCells;
    }
    /**
     * Returns the name of the phase.
     * @return phase name as a String
     */
    public String getPhaseName() {
        return this.name;
    }

    /**
     * Returns true if a god ability was activated in this phase.
     * @return boolean
     */
    public boolean getGodAbilityStatus() {
        return godAbilityActivated;
    }

    /**
     * Sets the status of whether a god ability was used in this phase.
     * @param bool boolean
     */
    public void setGodAbilityStatus(Boolean bool) {
        godAbilityActivated = bool;
    }

    /**
     * Executes the logic specific to this phase.
     * @param board the current game board
     * @param turn the current player's turn
     * @param clickedCell the cell that was clicked (if any)
     */
    public abstract void executePhase(Board board, Turn turn, Cell clickedCell);

    /**
     * Returns the next TurnPhase to transition into after this one completes.
     * @param turn the current Turn context
     * @return the next TurnPhase
     */
    public abstract TurnPhase nextTurnPhase(Turn turn);


}
