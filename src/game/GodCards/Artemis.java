package game.GodCards;

import game.Capabilities.GameComponentEnums;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Objects.Worker;
import game.Turn.Turn;
import game.Turn.TurnPhase;
import java.util.List;

/**
 * Artemis is a GodCard that allows a worker to move one additional time,
 * as long as it does not return to its original cell.
 */
public class Artemis extends GodCard implements MoveModification {

    /**
     * Constructs the Artemis god card with its name, description, and image.
     */
    public Artemis() {
        super("Artemis",
            "Your Worker may move one additional time, but not back to its initial space.",
            "/images/GodCards/Artemis.png");
        generateCapabilities();
    }

    /**
     * Applies Artemis's god power by executing the move phase again,
     * with a restricted set of valid moves (excluding the previous cell).
     * @param board the game board
     * @param turn the current turn
     * @param recentMovedObject the worker that just moved
     * @param turnPhase the current move phase to reactivate
     */

    @Override
    public void apply(Board board, Turn turn, Worker recentMovedObject, TurnPhase turnPhase){
        //Execute the move phase again
        turnPhase.setGodAbilityStatus(
                true); //this tells the turn phase that a god ability is activated and can only be used once
        System.out.println(turnPhase);
        //Before starting the move phase again, artemis can modify the valid cells: cant move to original cell
        List<Cell> modifiedValidCells = modifyValidMoves(recentMovedObject,
                recentMovedObject.getValidMoves(board));

        //Now set the valid moves in the move phase
        turnPhase.setCurrentValidMoves(modifiedValidCells);
        turnPhase.executePhase(board, turn, null);

    }



    /**
     * Adds Artemis-specific capabilities such as extra movement and cell restriction.
     */
    @Override
    public void generateCapabilities() {
        addNewCapability(GameComponentEnums.GOD_MOVE);
        addNewCapability(GameComponentEnums.RESTRICTION_ON_VALID_CELLS);
        addNewCapability(GameComponentEnums.AFTER_MOVEMENT);
    }

    /**
     * Filters out the worker's previous cell so it cannot move back to it.
     * @param worker the current worker
     * @param validMoves the list of default valid moves
     * @return the filtered list of valid moves
     */
    @Override
    public List<Cell> modifyValidMoves(Worker worker, List<Cell> validMoves) {
        Cell previousCell = worker.getPreviousCell();

        if (previousCell == null) {
            return validMoves;
        }

        return validMoves.stream().filter(cell -> !cell.equals(previousCell)).toList();
    }
}
