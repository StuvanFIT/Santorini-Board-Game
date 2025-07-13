package game.GodCards;

import game.GameBoard.Board;
import game.Objects.Worker;
import game.Turn.Turn;
import game.Turn.TurnPhase;

/**
 * The GodAbility interface defines a special ability that can be applied
 * by a GodCard during a specific turn phase.
 */
public interface GodAbility {

    /**
     * Applies the god power to modify the current turn or game phase.
     * @param board the current game board
     * @param context the current turn
     * @param worker the worker triggering the god ability
     * @param turnPhase the phase being modified or extended
     */
    void apply(Board board, Turn context, Worker worker, TurnPhase turnPhase);


}


