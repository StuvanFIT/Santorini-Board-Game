package game.Managers;

import game.CellPanel;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Objects.Worker;
import game.Turn.Turn;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An abstract base class that manages UI interactions for different turn phases
 * (e.g., Move, Build). It handles the attachment and cleanup of event listeners
 * for both workers and highlighted cells.
 */
public abstract class PhaseUIManager {

    protected Map<Cell, MouseListener> workerListeners = new HashMap<>();
    protected Map<Cell, MouseListener> highlightListeners = new HashMap<>();

    /**
     * Removes all active listeners and clears board highlights.
     * Called when a turn phase completes.
     * @param board the game board
     */
    public void cleanUp(Board board) {
        removeHighlightListeners();
        removeWorkerListeners();
        board.getBoardUIManager().clearHighlights();
    }

    /**
     * Registers a mouse listener for a worker cell.
     * @param workerCurrentCell the cell containing the worker
     * @param workerListener the listener to attach
     */
    public void addWorkerListener(Cell workerCurrentCell, MouseListener workerListener) {
        workerListeners.put(workerCurrentCell, workerListener);
    }

    /**
     * Registers a mouse listener for a highlighted cell.
     * @param highlightCell the highlighted cell
     * @param highlightListener the listener to attach
     */
    public void addHighlightListener(Cell highlightCell, MouseListener highlightListener) {
        highlightListeners.put(highlightCell, highlightListener);
    }

    /**
     * Returns all registered highlight listeners.
     * @return map of highlight listeners
     */
    public Map<Cell, MouseListener> getHighlightListeners() {
        return this.highlightListeners;
    }

    /**
     * Returns all registered worker listeners.
     * @return map of worker listeners
     */
    public Map<Cell, MouseListener> getWorkerListeners() {
        return this.workerListeners;
    }


    /**
     * Removes all highlight listeners and resets cell visuals.
     */
    // Remove all event listeners from the highlighted cells
    public void removeHighlightListeners() {

        for (Cell highlightCell : highlightListeners.keySet()) {
            CellPanel cellPanel = highlightCell.getCellPanel();
            MouseListener highlightListener = highlightListeners.get(highlightCell);
            if (highlightListener != null) {
                cellPanel.removeMouseListener(highlightListener);
            }
        }
        highlightListeners.clear();
    }

    /**
     * Removes all listeners attached to worker cells.
     */
    // Remove the event listeners from the worker's cells
    public void removeWorkerListeners() {
        List<Cell> cellsToRemove = new ArrayList<>();

        for (Map.Entry<Cell, MouseListener> entry : workerListeners.entrySet()) {
            Cell cell = entry.getKey();
            MouseListener listener = entry.getValue();

            if (listener != null) {
                cell.getCellPanel().removeMouseListener(listener);
                cellsToRemove.add(cell);  // Mark for removal
            }
        }
        // Remove the entries after iteration
        for (Cell cell : cellsToRemove) {
            workerListeners.remove(cell);
        }
    }

    /**
     * Highlights valid target cells when a user selects a worker or tile.
     * @param board the game board
     * @param turn the current turn
     * @param cell the selected cell
     * @param UIManager the current UI manager
     * @param godAbilityStatus whether god ability is active
     * @param currentValidCells optional pre-filtered list of valid cells
     */
    public abstract void handleDisplayHighlights(Board board, Turn turn, Cell cell,
        PhaseUIManager UIManager, boolean godAbilityStatus, List<Cell> currentValidCells);

    /**
     * Handles the action triggered when a valid cell is clicked (move/build).
     * @param board the game board
     * @param turn the current turn
     * @param cell the clicked cell
     * @param UIManager the current UI manager
     * @param godAbilityStatus whether god ability is active
     * @param currentValidCells list of valid options
     */
    public abstract void handleActionClick(Board board, Turn turn, Cell cell,
        PhaseUIManager UIManager, boolean godAbilityStatus, List<Cell> currentValidCells);

    /**
     * Sets up event listeners for each of the player's workers.
     * @param board the game board
     * @param turn the current turn
     * @param workers list of the player's workers
     * @param selectedWorkerRef shared reference for tracking selected worker
     * @param currentValidCells valid targets based on game state
     */
    public abstract void setUpListeners(Board board, Turn turn, List<Worker> workers,
        Worker[] selectedWorkerRef, List<Cell> currentValidCells);


}
