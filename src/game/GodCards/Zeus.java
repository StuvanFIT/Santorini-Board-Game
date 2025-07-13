package game.GodCards;

import game.Capabilities.GameComponentEnums;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Objects.Worker;
import game.Turn.Turn;
import game.Turn.TurnPhase;

import java.sql.SQLOutput;
import java.util.List;

/**
 * Zeus is a GodCard that allows a worker to build under itself. Note: a worker building a 3rd level
 * block underneath itself does not count as a win condition.
 */
public class Zeus extends GodCard implements MoveModification {

    /**
     * Constructs the Zeus god card with its name, description, and image path.
     */
    public Zeus() {
        super("Zeus", "Your worker may build a block under itself. Note: a worker building a 3rd level\n" +
                        "block underneath itself does not count as a win condition.",
                "/images/GodCards/Zeus.png");
        generateCapabilities();
    }

    /**
     * Applies Zeus’s power by being able to place a block under itself
     * @param board the game board
     * @param turn the current turn
     * @param recentMovedObject the worker that just built
     * @param turnPhase the current build phase to reactivate
     */
    @Override
    public void apply(Board board, Turn turn, Worker recentMovedObject, TurnPhase turnPhase) {
        turnPhase.setGodAbilityStatus(
                true); //this tells the turn phase that a god ability is activated and can only be used once
        List<Cell> modifiedValidCells = modifyValidMoves(recentMovedObject,
                recentMovedObject.getValidBuildingLocations(board));

        turnPhase.setCurrentValidMoves(modifiedValidCells);

    }

    /**
     * Adds Zeus-specific capabilities: extra building
     */
    @Override
    public void generateCapabilities() {
        addNewCapability(GameComponentEnums.GOD_BUILD);
        addNewCapability(GameComponentEnums.RESTRICTION_ON_VALID_CELLS);
        addNewCapability(GameComponentEnums.BEFORE_BUILDING);
    }

    /**
     * Adds the current worker cell to build locations.
     * @param worker the current worker
     * @param validMoves the original valid build locations
     * @return a filtered list with the current cell
     */
    @Override
    public List<Cell> modifyValidMoves(Worker worker, List<Cell> validMoves) {

        Cell currentCell = worker.getCurrentCell();

        if (currentCell == null) {
            return validMoves;
        }

        //if the current cell is not one level less than the unstable component, add the current cell
        if (currentCell.getLevel() != 3){
            validMoves.add(currentCell);
        }



        System.out.println(validMoves);

        return validMoves;
    }
}
