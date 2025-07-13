package game.Action;

import game.GameBoard.Board;
import game.GameBoard.Cell;
import java.util.List;

/**
 * The Builder interface represents any game component that is capable of performing a build action
 * on the board, such as placing building levels or domes on adjacent cells.
 * This interface is implemented by worker objects
 */
public interface Builder {

    /**
     * Performs a build action on the specified destination cell using the current board.

     * A Worker may increment the building level of the cell,
     * add a building component, and visually update the cell’s UI.
     *
     * @param destination the cell where the build action should occur
     * @param board the current game board on which the action is performed
     */
    void buildTo(Cell destination, Board board);

    /**
     * Returns the cell where the previous build action was executed.
     * This is used for logic that may depend on recent build history,
     * such as god powers that restrict multiple builds to the same cell.
     * In particular, this is used by Demeter.
     * @return the last cell built on
     */
    Cell getPreviousBuildCell();

    /**
     * Sets the cell where the previous build action was executed.
     * This is prepared by the interface for any possibilities that may arise for this method.
     * @return sets last cell built on board.
     */
    void setPreviousBuildCell(Cell cell);

    /**
     * Returns a list of valid cells where a build action can be performed
     * Workers can only build on adjacent, unoccupied available cells.
     * @param board the current game board used to determine valid build locations
     * @return a list of cells that are valid for building
     */
    List<Cell> getValidBuildingLocations(Board board);

}
