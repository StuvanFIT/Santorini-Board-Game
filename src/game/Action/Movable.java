package game.Action;

import game.GameBoard.Board;
import game.GameBoard.Cell;
import java.util.List;

/**
 * The Movable interface represents game components that can move from one cell to another.
 * Classes that utilise this interface are responsible for updating their own position and validating move options
 * according to the current state of the Board.
 */
public interface Movable {

    /**
     * Method that moves the object to the specified destination cell.
     * @param destination the cell to move to
     */
    void moveTo(Cell destination);

    /**
     * Returns the current cell the object is positioned.
     * @return the current cell of the movable object
     */
    Cell getCurrentCell();

    /**
     * Returns the cell the object was located on before the last move.
     *
     * @return the previous cell
     */
    Cell getPreviousCell();

    /**
     * Sets the cell the object was located on before the last move.
     *
     * @return the new previous cell
     */
    void setPreviousCell(Cell sourceCell);

    /**
     * Returns a list of cells that the object can move to.
     *
     * @param board the current game board
     * @return a list of valid destination cells
     */
    List<Cell> getValidMoves(Board board);

}
