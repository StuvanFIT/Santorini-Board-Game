package game.GameCondition;

import game.Action.Movable;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Turn.Turn;

/**
 * The LosingCondition interface defines a condition where a player
 * loses the game, such as when no valid moves are available.
 */
public interface LosingCondition {

    /**
     * Checks whether the given movable object meets the loss condition.
     * @param board the current game board
     * @param turn the player's turn
     * @param movable the movable object to evaluate
     * @param cell the source cell of the object
     * @return boolean
     */
    boolean checkLose(Board board, Turn turn, Movable movable, Cell cell);
}
