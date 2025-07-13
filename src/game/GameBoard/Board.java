package game.GameBoard;

import game.Managers.BoardUIManager;
import game.Objects.GameComponent;
import game.Objects.Worker;
import game.Turn.TurnManager;
import java.util.HashMap;
import java.util.Map;

/**
 * The Board class defines a blueprint for game boards in the board game.
 * It stores cells, manages selected workers, and provides move/build validation.
 */
public abstract class Board {

    private final Map<Coordinate, Cell> board = new HashMap<Coordinate, Cell>();
    private final int width;
    private final int height;
    protected Worker selectedWorker; //Every board instance should manage its own selectedMovable.
    protected TurnManager turnManager;
    private BoardUIManager boardUIManager;

    /**
     * Initializes a new board with the given dimensions.
     * @param length the board's width
     * @param height the board's height
     */
    public Board(int length, int height) {
        this.width = length;
        this.height = height;
        this.boardUIManager = new BoardUIManager();
    }

    /**
     * Initializes all cells on the board.
     */
    public abstract void initialiseBoard();

    /**
     * Returns the BoardUIManager used for managing UI interactions.
     * @return the BoardUIManager
     */
    public BoardUIManager getBoardUIManager() {
        return this.boardUIManager;
    }

    /**
     * Returns the width of the board.
     * @return board width
     */
    public int getBoardWidth() {
        return width;
    }

    /**
     * Returns the height of the board.
     * @return board height
     */
    public int getBoardHeight() {
        return height;
    }

    /**
     * Returns the cell at the specified coordinate.
     * @param coordinate the cell's coordinate
     * @return the cell at the coordinate
     */
    public abstract Cell getCell(Coordinate coordinate);

    /**
     * Checks if a move from source to destination is valid for the given object.
     * @param gameObject the object being moved
     * @param source the starting cell
     * @param destination the destination cell
     * @return boolean
     */
    public abstract boolean isValidMove(GameComponent gameObject, Cell source, Cell destination);

    /**
     * Checks if a build action is valid at the destination cell.
     * @param gameObject the object performing the build
     * @param source the cell performing the action
     * @param destination the target cell
     * @return boolean
     */
    public abstract boolean isValidBuild(GameComponent gameObject, Cell source, Cell destination);

    /**
     * Returns the currently selected movable worker.
     * @return selected worker
     */
    public Worker getSelectedMovable() {
        return selectedWorker;
    }

    /**
     * Sets the selected movable worker.
     * @param selectedWorker the worker to set
     */
    public void setSelectedMovable(Worker selectedWorker) {
        this.selectedWorker = selectedWorker;
    }

    /**
     * Returns the current TurnManager.
     * @return the TurnManager
     */
    public TurnManager getTurnManager() {
        return this.turnManager;
    }

    /**
     * Sets the TurnManager for this board.
     * @param turnManager the TurnManager to set
     */
    public void setTurnManager(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    /**
     * Returns the map of cells in the board.
     * @return map of coordinates to cells
     */
    public Map<Coordinate, Cell> getBoard() {
        return this.board;
    }


}
