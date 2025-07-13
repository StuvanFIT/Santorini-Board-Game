package game.GodCards;

import game.Capabilities.GameComponentEnums;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Objects.Worker;
import game.Turn.Turn;
import game.Turn.TurnPhase;
import java.util.List;

/**
 * Demeter is a GodCard that allows a worker to build one additional time,
 * but not on the same cell as the first build.
 */
public class Demeter extends GodCard implements MoveModification {

    /**
     * Constructs the Demeter god card with its name, description, and image path.
     */
    public Demeter() {
        super("Demeter", "Your Worker may build one additional time, but not on the same space.",
            "/images/GodCards/Demeter.png");
        generateCapabilities();
    }


    /**
     * Applies Demeter’s power by reactivating the build phase and restricting the
     * valid build cells to exclude the previous build location.
     * @param board the game board
     * @param turn the current turn
     * @param recentMovedObject the worker that just built
     * @param turnPhase the current build phase to reactivate
     */
    @Override
    public void apply(Board board, Turn turn, Worker recentMovedObject, TurnPhase turnPhase) {
        //Activate the build phase again:
        turnPhase.setGodAbilityStatus(
                true); //this tells the turn phase that a god ability is activated and can only be used once

        List<Cell> modifiedValidCells = modifyValidMoves(recentMovedObject,
                recentMovedObject.getValidBuildingLocations(board));

        turnPhase.setCurrentValidMoves(modifiedValidCells);
        turnPhase.executePhase(board, turn, null);

    }

    /**
     * Adds Demeter-specific capabilities: extra building and restricted cell reuse.
     */
    @Override
    public void generateCapabilities() {
        addNewCapability(GameComponentEnums.GOD_BUILD);
        addNewCapability(GameComponentEnums.RESTRICTION_ON_VALID_CELLS);
        addNewCapability(GameComponentEnums.AFTER_BUILDING);

    }

    /**
     * Removes the previous build cell from the list of valid build options.
     * @param worker the current worker
     * @param validMoves the original valid build locations
     * @return a filtered list excluding the last built cell
     */
    @Override
    public List<Cell> modifyValidMoves(Worker worker, List<Cell> validMoves) {
        Cell previousBuildCell = worker.getPreviousBuildCell();

        if (previousBuildCell == null) {
            return validMoves;
        }

        return validMoves.stream().filter(cell -> !cell.equals(previousBuildCell)).toList();

    }
}
