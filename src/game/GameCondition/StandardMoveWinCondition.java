package game.GameCondition;

import game.Action.Movable;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Turn.Turn;

/**
 * The StandardMoveWinCondition checks if a worker has moved to the specified winning level.
 * Used to implement the default Santorini win rule (e.g. move to level 3).
 */
public class StandardMoveWinCondition implements WinningCondition {

    private int winningLevel;

    /**
     * Creates a win condition based on reaching a specific level.
     * @param winningLevel the target level required to win (usually 3)
     */
    public StandardMoveWinCondition(int winningLevel) {
        this.winningLevel = winningLevel;
    }

    /**
     * Returns true if the movable object has reached the winning level after moving.
     * @param board the current game board
     * @param turn the current turn context
     * @param movableObj the moved object to evaluate
     * @param sourceCell the cell the object moved from
     * @return boolean
     */
    @Override
    public boolean checkWin(Board board, Turn turn, Movable movableObj, Cell sourceCell) {

        int destinationLevel = movableObj.getCurrentCell().getLevel();

        int sourceLevel = sourceCell.getLevel();

        System.out.println("THE CURRENT LEVEL OF THE CELL IS: " + destinationLevel);
        System.out.println(destinationLevel);
        System.out.println(sourceLevel);

        System.out.println( (destinationLevel == winningLevel) && (destinationLevel - sourceLevel == 1));
        //Player wins if one of their workers moves from a lower level to a level 3 tower
        return (destinationLevel == winningLevel) && (destinationLevel - sourceLevel == 1);
    }

    ;
}
