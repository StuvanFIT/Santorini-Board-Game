package game.Turn;

import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Managers.GameTimerManager;

/**
 * Represents the end phase of a player's turn.
 * Simply marks the phase as complete to allow the next player's turn to begin.
 */
public class EndTurnPhase extends TurnPhase {

    /**
     * Constructs the EndTurnPhase with a default name.
     * @param turn the current player's turn
     */
    public EndTurnPhase(Turn turn) {
        super("END");
    }

    /**
     * Executes the end-of-turn phase by immediately completing it.
     * @param board the current game board
     * @param turn the current turn object
     * @param cell the clicked cell (not used in this phase)
     */
    @Override
    public void executePhase(Board board, Turn turn, Cell cell) {

        //Stop the timer of the current player
        GameTimerManager playerTimerManager = turn.getPlayer().getTimerManager();

        playerTimerManager.stopTimer();


        setComplete(true);

    }

    /**
     * There is no next phase after EndTurnPhase, so returns null.
     * @param turn the current player's turn
     * @return null, indicating no further phase
     */
    @Override
    public TurnPhase nextTurnPhase(Turn turn) {
        return null;
    }
}

