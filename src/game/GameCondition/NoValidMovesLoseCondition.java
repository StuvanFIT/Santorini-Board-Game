package game.GameCondition;

import game.Action.Movable;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Turn.Turn;

/**
 * The NoValidMovesLoseCondition class checks if a movable object has no valid moves.
 * If true, the owning player loses the game.
 */
public class NoValidMovesLoseCondition implements LosingCondition {

    /**
     * Returns true if the given movable object has no valid moves on the board.
     * @param board the current game board
     * @param turn the current turn context
     * @param movableObj the object being evaluated
     * @param sourceCell the cell the object is currently on
     * @return boolean
     */
    @Override
    public boolean checkLose(Board board, Turn turn, Movable movableObj, Cell sourceCell) {

        //CHECK IF ALL WORKERS CANNOT MOVE: If one of the workers can move, the player does not lose yet
        if (movableObj.getValidMoves(board).isEmpty()) {
            return true;
        }
        ;

        return false;
    }


}
