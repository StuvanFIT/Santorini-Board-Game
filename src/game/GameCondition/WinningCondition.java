package game.GameCondition;

import game.Action.Movable;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Turn.Turn;

/**
 * The WinningCondition interface defines a rule that determines
 * if a player has met the conditions to win the game.
 */
public interface WinningCondition {

    /**
     * Checks whether the given movable object has met the win condition.
     * @param board the current game board
     * @param turn the current turn context
     * @param movable the object that was moved
     * @param cell the cell the object moved from
     * @return boolean
     */
    boolean checkWin(Board board, Turn turn, Movable movable, Cell cell);


}
