package game.Managers;

import game.Capabilities.GameComponentEnums;
import game.CellPanel;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.GameCondition.LosingCondition;
import game.GameCondition.NoValidMovesLoseCondition;
import game.GameCondition.StandardMoveWinCondition;
import game.GameCondition.WinningCondition;
import game.GodCards.GodCard;
import game.Objects.GameComponent;
import game.Objects.Worker;
import game.Turn.Turn;
import game.Turn.TurnManager;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * Handles UI interactions for the Move Phase, including highlighting valid move targets,
 * tracking active workers, and managing god power usage.
 */
public class MovePhaseUIManager extends PhaseUIManager {

    private List<Worker> currActiveWorkers;
    private PhaseUIManager movePhaseUIManager = this;

    /**
     * Highlights valid move cells for the selected worker.
     * @param board the game board
     * @param turn the current turn
     * @param cell the selected cell
     * @param movePhaseUIManager this UI manager instance
     * @param godAbilityStatus whether god ability is active
     * @param currentValidCells pre-filtered valid cells
     */
    @Override
    public void handleDisplayHighlights(Board board, Turn turn, Cell cell,
        PhaseUIManager movePhaseUIManager, boolean godAbilityStatus, List<Cell> currentValidCells) {
        //Remove highlight listeners
        movePhaseUIManager.removeHighlightListeners();
        BoardUIManager boardUIManager = board.getBoardUIManager();
        boardUIManager.clearHighlights();

        List<GameComponent> occupants = cell.getOccupants();

        // Look for movable components in clicked cell
        List<Worker> movableObjects = occupants.stream()
            .filter(o -> o instanceof Worker)
            .map(o -> (Worker) o)
            .toList();

        //Case 0: If there are no Movable objects, we do not do anything. --- FOR NOW.....

        // Case 1: AT least one movable object
        if (!movableObjects.isEmpty()) {
            System.out.println("THIS CELL HAS ONE OBJECT");

            Worker choice;

            if (movableObjects.size() > 1) {
                choice = (Worker) JOptionPane.showInputDialog(null,
                    "Select a component to move:",
                    "Choose Movable Game Object",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    movableObjects.toArray(), //Options to choose from
                    movableObjects.get(0)); //This is the initial option
            } else {
                choice = movableObjects.get(0);
            }

            board.setSelectedMovable(choice);

            Worker selectedWorker = board.getSelectedMovable();

            GodCard godCard = turn.getPlayer().getGodCard();
            if (godAbilityStatus && godCard.hasCapability(
                GameComponentEnums.RESTRICTION_ON_VALID_CELLS) && currentValidCells != null) {
                System.out.println("Modified the valid moves");
            } else {
                currentValidCells = selectedWorker.getValidMoves(board);
            }

            //display the valid cells
            boardUIManager.highlightCells(currentValidCells, Color.GREEN);

            List<Cell> highlightedCells = boardUIManager.getHighlightedCells();

            for (Cell highlightCell : highlightedCells) {

                CellPanel cellPanel = highlightCell.getCellPanel();

                // Create a MouseListener for the highlighted cells
                List<Cell> finalCurrentValidCells = currentValidCells;

                MouseListener highlightListener = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("MOVE PHASE: CLICKED ON HIGHLIGHTED CELL!!!!!!");
                        handleActionClick(board, turn, highlightCell, movePhaseUIManager,
                            turn.getCurrentPhase().getGodAbilityStatus(), finalCurrentValidCells);
                    }
                };

                // Attach the listener and store it for removal later
                cellPanel.addMouseListener(highlightListener);
                movePhaseUIManager.addHighlightListener(highlightCell, highlightListener);

            }
        }
    }

    /**
     * Executes the worker's move and checks for winning condition or god ability usage.
     * @param board the game board
     * @param turn the current turn
     * @param cell the cell clicked for movement
     * @param movePhaseUIManager this UI manager
     * @param godAbilityStatus whether god ability is already used
     * @param currentValidCells valid movement options
     */
    @Override
    public void handleActionClick(Board board, Turn turn, Cell cell,
        PhaseUIManager movePhaseUIManager, boolean godAbilityStatus, List<Cell> currentValidCells) {
        System.out.println(board.getSelectedMovable());

        BoardUIManager boardUIManager = board.getBoardUIManager();

        //IF THE CELL YOU CLICKED ON WAS ONE OF THE HIGHLIGHTED CELLS:
        if (boardUIManager.getHighlightedCells().contains(cell)) {
            System.out.println("MOVE PHASE: We have clicked on a highlighted cell");

            //Get the selected movable object
            Worker selectedMovable = board.getSelectedMovable();

            //Store the previous cell before overwriting it
            Cell sourceCell = selectedMovable.getCurrentCell();

            //Move the selected object
            selectedMovable.moveTo(cell);

            //Remove any event listeners in the highlighted cells
            movePhaseUIManager.removeHighlightListeners();
            // Remove any event listener's attached to the worker's cell
            movePhaseUIManager.removeWorkerListeners();

            //Remove the highlighted cells in the list
            boardUIManager.clearHighlights();

            //Check for standard game winning condition after the move
            WinningCondition moveWinningCondition = new StandardMoveWinCondition(3);

            if (moveWinningCondition.checkWin(board, turn, selectedMovable, sourceCell)) {
                System.out.println("FIT3077");
                TurnManager turnManager = board.getTurnManager();
                turnManager.setIsGameOver(true);
                turnManager.setWinningPlayer(turn.getPlayer());
                turn.getCurrentPhase().setComplete(true);
            } else {
                //After checking for a normal win move condition,
                GodCard godCard = turn.getPlayer().getGodCard();
                System.out.println(godCard);

                if (godCard.hasCapability(GameComponentEnums.AFTER_MOVEMENT)
                    && godAbilityStatus == false) {
                    System.out.println("GOD MOVE ABILITY!");

                    int message = JOptionPane.showConfirmDialog(
                        null,
                        "Would you like to use your God power in the move phase?",
                        "USE GOD ABILITY",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (message == JOptionPane.YES_OPTION) {
                        //YOU CAN ONLY MOVE THE WORKER U JUST MOVED PREVIOUSLY
                        godCard.apply(board, turn, selectedMovable, turn.getCurrentPhase());
                        System.out.println("STEVEN");


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

    }

    /**
     * Attaches mouse listeners to each movable worker.
     * Disables workers with no valid moves and ends turn if none can move.
     * @param board the game board
     * @param turn the current turn
     * @param playerWorkers list of the player's workers
     * @param selectedWorkerRef reference holder for selected worker
     * @param currentValidCells the list of valid move targets
     */
    @Override
    public void setUpListeners(Board board, Turn turn, List<Worker> playerWorkers,
        Worker[] selectedWorkerRef, List<Cell> currentValidCells) {

        GodCard godCard = turn.getPlayer().getGodCard();

        //BEFORE BUILDING PHASE:
        if (godCard.hasCapability(GameComponentEnums.BEFORE_MOVEMENT)){
            int message = JOptionPane.showConfirmDialog(
                    null,
                    "Would you like to use your God power before the move phase?",
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


        List<Worker> activeWorkers = new ArrayList<>();

        for (Worker worker : playerWorkers) {

            Cell workerCurrentCell = worker.getCurrentCell();

            //Do not add listener to worker if they do not have valid moves
            //If all the workers have no valid moves left, then they cannot move thus player loses
            LosingCondition noMovesCondition = new NoValidMovesLoseCondition();
            if ((noMovesCondition.checkLose(board, turn, worker, null))) {

                System.out.println("Worker: " + worker + " cannot move.");

                //If the one worker that cannot move is the worker that activated the second move, go to build phase
                if (playerWorkers.equals(List.of(board.getSelectedMovable()))) {

                    int message = JOptionPane.showConfirmDialog(
                        null,
                        "There are no valid moves!",
                        "MESSAGE",
                        JOptionPane.DEFAULT_OPTION
                    );

                    if (message == JOptionPane.OK_OPTION) {
                        this.cleanUp(board);
                        turn.getCurrentPhase().setComplete(true);
                        return;
                    }
                }
            } else {
                activeWorkers.add(worker);
            }

            CellPanel workerCellPanel = workerCurrentCell.getCellPanel();

            Map<Cell, MouseListener> workerListeners = this.getWorkerListeners();
            // Remove old listener if exists
            if (workerListeners.containsKey(workerCurrentCell)) {
                workerCellPanel.removeMouseListener(workerListeners.get(workerCurrentCell));
            }

            // Create and attach new listener
            MouseListener workerListener = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    System.out.println("MOVE PHASE: CLICKED ON WORKER:" + worker);

                    // Prevent stacking of listeners
                    if (selectedWorkerRef[0] == worker) {
                        return;
                    }
                    if (selectedWorkerRef[0] != worker) {
                        selectedWorkerRef[0] = worker;
                    }

                    handleDisplayHighlights(board, turn, worker.getCurrentCell(),
                        movePhaseUIManager, turn.getCurrentPhase().getGodAbilityStatus(),
                        currentValidCells);

                }
            };
            //Add the listener to the cell panel
            workerCellPanel.addMouseListener(workerListener);
            //Add the worker listener to te worker listeners hash map:
            this.addWorkerListener(workerCurrentCell, workerListener);
        }

        currActiveWorkers = activeWorkers;

    }


    public List<Worker> getActiveWorkers() {
        return this.currActiveWorkers;
    }
}
