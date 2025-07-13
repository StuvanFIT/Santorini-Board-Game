package game.Turn;

import game.Capabilities.GameComponentEnums;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Managers.MovePhaseUIManager;
import game.Managers.PhaseUIManager;
import game.Objects.Worker;
import game.Player.Player;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Represents the movement phase in a player's turn.
 * Handles user interaction for selecting a worker and moving it to a valid cell.
 */
public class MovePhase extends TurnPhase {

    private Turn turn;
    private Worker selectedWorker;
    private boolean workerSelected = false;
    private MovePhaseUIManager movePhaseUIManager;

    /**
     * Constructs a new MovePhase for the specified turn.
     * @param turn the current player's turn
     */
    public MovePhase(Turn turn) {
        super("Move");
        this.turn = turn;
        this.movePhaseUIManager = new MovePhaseUIManager() {
        };
    }

    /**
     * Executes the logic for the move phase, including setting up worker listeners
     * and processing god ability overrides.
     * @param board the current game board
     * @param turn the current turn object
     * @param cell the clicked cell (not used during setup)
     */
    public void executePhase(Board board, Turn turn, Cell cell) {

        //Start of the Move Phase:
        System.out.println("EXECUTE PHASE");
        Player currentPlayer = turn.getPlayer();
        System.out.println(currentPlayer.getGodCard());
        List<Worker> playerWorkers;


        //When activating a god ability, it only affects the previous selected worker
        if (!getGodAbilityStatus()) {
            playerWorkers = currentPlayer.getWorkers();
        } else {
            playerWorkers = List.of(board.getSelectedMovable());
            selectedWorker = null;
            workerSelected = false;

            if (currentValidCells.isEmpty()) {
                //If the one worker that cannot move is the worker that activated the second move, go to build phase
                if (playerWorkers.equals(List.of(board.getSelectedMovable()))) {

                    int message = JOptionPane.showConfirmDialog(
                        null,
                        "There are no valid moves!",
                        "MESSAGE",
                        JOptionPane.DEFAULT_OPTION
                    );

                    if (message == JOptionPane.OK_OPTION) {
                        movePhaseUIManager.cleanUp(board);
                        setComplete(true);
                        return;
                    }
                }

            }
        }

        Worker[] finalSelectedWorker = new Worker[1];
        finalSelectedWorker[0] = selectedWorker;
        List<Cell> finalCurrentValidCells = currentValidCells;
        this.movePhaseUIManager.setUpListeners(board, turn, playerWorkers, finalSelectedWorker,
            finalCurrentValidCells);

        List<Worker> activeWorkers = movePhaseUIManager.getActiveWorkers();

        // After iterating through all workers, check if all workers are blocked
        if ((activeWorkers.size() == 0) && !getGodAbilityStatus()) {
            // If no workers have valid moves left, the player loses
            TurnManager turnManager = board.getTurnManager();
            turnManager.playerLoses(turn, this);

        }

    }

    /**
     * Returns the next turn phase after MovePhase.
     * @param turn the current player's turn
     * @return the next phase, BuildPhase
     */
    @Override
    public TurnPhase nextTurnPhase(Turn turn) {
        return new BuildPhase(turn);
    }

    /**
     * Returns phase ui manager
     * @return PhaseUIManager
     */
    public PhaseUIManager getPhaseUIManager() {
        return movePhaseUIManager;
    }



}