package game.Managers;

import game.Action.Builder;
import game.Capabilities.GameComponentEnums;
import game.CellPanel;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.GodCards.GodCard;
import game.Objects.Worker;
import game.Turn.Turn;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * Manages UI interactions for the Build Phase. Handles user clicks,
 * highlights valid build cells, and triggers building actions or god powers.
 */
public class BuildPhaseUIManager extends PhaseUIManager {

    private BuildPhaseUIManager buildPhaseUIManager = this;

    /**
     * Highlights all valid build cells for the selected worker.
     * @param board the current game board
     * @param turn the current turn
     * @param cell the selected cell
     * @param buildUIManager the UI manager instance
     * @param godAbilityStatus whether a god power is active
     * @param currentValidCells pre-filtered valid cells
     */
    @Override
    public void handleDisplayHighlights(Board board, Turn turn, Cell cell,
        PhaseUIManager buildUIManager, boolean godAbilityStatus, List<Cell> currentValidCells) {
        buildUIManager.removeHighlightListeners();
        BoardUIManager boardUIManager = board.getBoardUIManager();
        boardUIManager.clearHighlights();

        //Get the current selectedMovable we have just moved in the first step.
        Worker selectedMovable = board.getSelectedMovable();
        board.setSelectedMovable(selectedMovable);

        Builder selectedBuilder = selectedMovable;

        if (selectedBuilder != null) {

            GodCard godCard = turn.getPlayer().getGodCard();
            if (godAbilityStatus && godCard.hasCapability(
                GameComponentEnums.RESTRICTION_ON_VALID_CELLS) && currentValidCells != null) {
                System.out.println("Modified the valid moves");
            } else {
                System.out.println("EKKO");
                currentValidCells = selectedBuilder.getValidBuildingLocations(board);
            }

            //if there are no valid building cells,
            if (currentValidCells.isEmpty()) {
                int message = JOptionPane.showConfirmDialog(
                    null,
                    "There are no more Valid Building cells available!",
                    "MESSAGE",
                    JOptionPane.DEFAULT_OPTION
                );

                if (message == JOptionPane.OK_OPTION) {
                    //YOU CAN ONLY MOVE THE WORKER U JUST MOVED PREVIOUSLY
                    turn.getCurrentPhase().setComplete(true);
                }
            }

            boardUIManager.highlightCells(currentValidCells, Color.YELLOW);

            List<Cell> highlightedCells = boardUIManager.getHighlightedCells();

            for (Cell highlightCell : highlightedCells) {

                CellPanel cellPanel = highlightCell.getCellPanel();

                // Create a MouseListener for the highlighted cells
                List<Cell> finalCurrentValidCells = currentValidCells;
                MouseListener highlightListener = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("BUILD PHASE: CLICKED ON HIGHLIGHTED CELL!!!!!!");
                        handleActionClick(board, turn, highlightCell, buildUIManager,
                            godAbilityStatus, finalCurrentValidCells);
                    }
                };

                // Attach the listener and store it for removal later
                cellPanel.addMouseListener(highlightListener);
                buildUIManager.addHighlightListener(highlightCell, highlightListener);

            }
        }

    }

    /**
     * Handles the logic when a valid build cell is clicked.
     * @param board the game board
     * @param turn the current turn
     * @param cell the clicked cell
     * @param buildUIManager the UI manager instance
     * @param godAbilityStatus whether god power has been activated
     * @param currentValidCells list of valid build cells
     */
    @Override
    public void handleActionClick(Board board, Turn turn, Cell cell, PhaseUIManager buildUIManager,
        boolean godAbilityStatus, List<Cell> currentValidCells) {
        System.out.println(board.getSelectedMovable());

        BoardUIManager boardUIManager = board.getBoardUIManager();

        //IF THE CELL YOU CLICKED ON WAS ONE OF THE HIGHLIGHTED CELLS:
        if (boardUIManager.getHighlightedCells().contains(cell)) {
            System.out.println("BUILD PHASE: We have clicked on a highlighted cell");
            System.out.println("Building on: " + cell.getCoordinate());

            //get worker
            Worker worker = board.getSelectedMovable();

            worker.buildTo(cell, board);

            //Remove any event listeners in the highlighted cells
            buildUIManager.removeHighlightListeners();

            // Remove any event listener's attached to the worker's cell
            buildUIManager.removeWorkerListeners();

            boardUIManager.clearHighlights();

            //After checking for a normal win move condition,
            GodCard godCard = turn.getPlayer().getGodCard();
            System.out.println(godCard);

            if (godCard.hasCapability(GameComponentEnums.AFTER_BUILDING) && godAbilityStatus == false) {
                System.out.println("GOD MOVE ABILITY!");

                int message = JOptionPane.showConfirmDialog(
                    null,
                    "Would you like to use your God power in the build phase?",
                    "USE GOD ABILITY",
                    JOptionPane.YES_NO_OPTION
                );

                if (message == JOptionPane.YES_OPTION) {
                    //YOU CAN ONLY MOVE THE WORKER U JUST MOVED PREVIOUSLY
                    godCard.apply(board, turn, worker, turn.getCurrentPhase());


                } else {
                    System.out.println("DID NOT ACTIVATE GOD POWER");
                    turn.getCurrentPhase().setComplete(true);

                }
            } else {
                //Complete the phase
                turn.getCurrentPhase().setComplete(true);
            }

        }

    }

    /**
     * Attaches click listeners to each of the player's workers to initiate the build phase.
     * @param board the game board
     * @param turn the current turn
     * @param playerWorkers list of the player's workers
     * @param selectedBuilderRef an array reference to track the selected worker
     * @param currentValidCells the list of valid build cells
     */
    @Override
    public void setUpListeners(Board board, Turn turn, List<Worker> playerWorkers,
        Worker[] selectedBuilderRef, List<Cell> currentValidCells) {

        GodCard godCard = turn.getPlayer().getGodCard();

        System.out.println("WHAT IS THE GOD ABILITY STATUS BEFORE");
        //BEFORE BUILDING PHASE:
        if (godCard.hasCapability(GameComponentEnums.BEFORE_BUILDING)){
            int message = JOptionPane.showConfirmDialog(
                    null,
                    "Would you like to use your God power before the build phase?",
                    "USE GOD ABILITY",
                    JOptionPane.YES_NO_OPTION
            );

            if (message == JOptionPane.YES_OPTION) {
                //YOU CAN ONLY MOVE THE WORKER U JUST MOVED PREVIOUSLY
                godCard.apply(board, turn, board.getSelectedMovable(), turn.getCurrentPhase());

            } else {
                System.out.println("DID NOT ACTIVATE GOD POWER");
            }
        }

        System.out.println("WHAT IS THE GOD ABILITY STATUS AFTER");
        System.out.println(turn.getCurrentPhase().getGodAbilityStatus());
        System.out.println(turn.getCurrentPhase().getCurrentValidMoves());

        for (Worker worker : playerWorkers) {
            Cell workerCurrentCell = worker.getCurrentCell();

            CellPanel workerCellPanel = workerCurrentCell.getCellPanel();

            Map<Cell, MouseListener> workerListeners = getWorkerListeners();
            // Remove old listener if exists
            if (workerListeners.containsKey(workerCurrentCell)) {
                workerCellPanel.removeMouseListener(workerListeners.get(workerCurrentCell));
            }

            // Create a MouseListener for this worker
            MouseListener workerListener = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("BUILD PHASE:CLICKED ON WORKER" + worker);

                    // Prevent stacking of listeners
                    if (selectedBuilderRef[0] == worker) {
                        return;
                    }
                    if (selectedBuilderRef[0] != worker) {
                        selectedBuilderRef[0] = worker;
                    }

                    handleDisplayHighlights(board, turn, worker.getCurrentCell(),
                        buildPhaseUIManager, turn.getCurrentPhase().getGodAbilityStatus(),
                            turn.getCurrentPhase().getCurrentValidMoves());
                }
            };

            // Attach the MouseListener to the worker's cell panel
            workerCellPanel.addMouseListener(workerListener);
            this.addWorkerListener(workerCurrentCell, workerListener);

        }

    }
}
