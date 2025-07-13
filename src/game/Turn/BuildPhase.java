package game.Turn;

import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Managers.BuildPhaseUIManager;
import game.Managers.PhaseUIManager;
import game.Objects.Worker;
import game.Player.Player;
import java.util.List;

/**
 * Represents the build phase in a player's turn.
 * Handles logic for building structures after a worker has moved.
 */
public class BuildPhase extends TurnPhase {

    private Turn turn;
    private Worker selectedBuilder;
    private boolean workerSelected = false;
    private PhaseUIManager buildUIManager;

    /**
     * Constructs the BuildPhase associated with a specific turn.
     * @param turn the current player's turn context
     */
    public BuildPhase(Turn turn) {
        super("Build");
        this.turn = turn;
        this.buildUIManager = new BuildPhaseUIManager();
    }

    /**
     * Executes the logic for the build phase.
     * Attaches listeners to valid worker(s) and prepares UI interaction.
     *
     * @param board the current game board
     * @param turn the current turn object
     * @param cell the cell clicked to initiate this phase (can be null)
     */
    @Override
    public void executePhase(Board board, Turn turn, Cell cell) {
        System.out.println(turn.getPlayer().getPlayerId() + " is Building...");
        System.out.println("We are in BuildPhase → handleCellClickFunc");

        System.out.println("THE WORKER SELECTED FROM THE MOVE PHASE:" + board.getSelectedMovable());

        //Start of the Build Phase:

        //1. We need to attach an event listener to the worker that was moved in the move phase
        Player currentPlayer = turn.getPlayer();

        List<Worker> playerWorkers = List.of(board.getSelectedMovable());
        if (!getGodAbilityStatus()) {
            System.out.println("No god ability yet");
        } else {
            System.out.println("This is a god ability building phase");
            selectedBuilder = null;
            workerSelected = false;
        }

        Worker[] finalSelectedBuilder = new Worker[1];
        finalSelectedBuilder[0] = selectedBuilder;
        List<Cell> finalCurrentValidCells = currentValidCells;
        buildUIManager.setUpListeners(board, turn, playerWorkers, finalSelectedBuilder,
            finalCurrentValidCells);


    }

    /**
     * Defines the next phase in the game after building.
     * @param turn the current player's turn
     * @return the EndTurnPhase instance
     */
    @Override
    public TurnPhase nextTurnPhase(Turn turn) {
        return new BenevolencePhase(turn);
    }

    /**
     * Returns phase ui manager
     * @return PhaseUIManager
     */
    public PhaseUIManager getPhaseUIManager() {
        return buildUIManager;
    }

}