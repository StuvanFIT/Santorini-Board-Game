package game.GodCards;

import game.GameBoard.Cell;
import game.Objects.Worker;
import java.util.List;

/**
 * The MoveModification interface allows GodCards to alter
 * the default list of valid cells a worker can move or build to.
 */
public interface MoveModification {

    /**
     * Modifies the list of valid cells based on a god's specific rule.
     * @param worker the worker performing the action
     * @param validMoves the original list of valid cells
     * @return the modified list of valid cells
     */
    List<Cell> modifyValidMoves(Worker worker, List<Cell> validMoves);

}


