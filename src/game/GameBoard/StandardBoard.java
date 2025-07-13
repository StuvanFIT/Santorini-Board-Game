package game.GameBoard;


import game.Capabilities.GameComponentEnums;
import game.Objects.GameComponent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The StandardBoard class represents a 5x5 board used in the game.
 * It manages cell creation, movement rules, and build validation logic.
 */
public class StandardBoard extends Board {

    //Instead of a 2d array, we use a hash map.
    //We pair each coordinate with a specific cell instance.
    private final Map<Coordinate, Cell> board = new HashMap<Coordinate, Cell>();

    /**
     * Creates a 5x5 board and initializes its cells.
     */
    public StandardBoard() {
        super(5, 5);
        initialiseBoard();
    }

    /**
     * Populates the board with empty cells mapped to each coordinate.
     */
    public void initialiseBoard() {
        for (int x = 0; x < this.getBoardWidth(); x++) {
            for (int y = 0; y < this.getBoardHeight(); y++) {

                Coordinate coordinate = new Coordinate(x, y);
                Cell cell = new Cell(coordinate);
                board.put(coordinate, cell);
            }
        }
    }

    /**
     * Returns the map of coordinates to cells.
     * @return the board map
     */
    public Map<Coordinate, Cell> getBoard() {
        return this.board;
    }

    /**
     * Retrieves the cell at the given coordinate.
     * @param coordinate the target coordinate
     * @return the matching cell
     */
    @Override
    public Cell getCell(Coordinate coordinate) {
        return board.get(coordinate);
    }

    /**
     * Checks whether a move to the destination is valid.
     * The move must be to an adjacent, walkable, and unoccupied cell with height difference ≤ 1.
     * @param gameObject the object attempting to move
     * @param source the current cell
     * @param destination the target cell
     * @return boolean
     */
    @Override
    public boolean isValidMove(GameComponent gameObject, Cell source, Cell destination) {

        /**
         * How to know if a cell is valid for a standard 5x5 board:
         * -If there are no workers on the cell. However, if the current selected movable
         * object has the ABILITY TO i.e. "CAN_MOVE_TO_OCCUPIED_CELL", then it can move the cell, despite there
         * being a worker/movable object there.
         * -The building level must be a difference of 1. So a worker cannot go to a level 2 building from lvl 0
         * **/

        if (source == null || destination == null) {
            return false;
        }

        //1. Adjacent Check: NO need to, the destination cells are already adjacent

        //2. Check if the destination cell is occupied by another Game Object
        List<GameComponent> cellOccupants = destination.getOccupants();

        //if the cell has no occupants, the game object can be placed here.
        if (cellOccupants.isEmpty()) {
            return true;
        }

        for (GameComponent occupant : cellOccupants) {

            //if the cell has occupants, but one of the occupants is a worker, return false
            if (occupant.hasCapability(GameComponentEnums.WORKING_ON_CURRENT_CELL)) {
                return false;
            }
            if (occupant.hasCapability(GameComponentEnums.UNSTABLE)) {
                return false;
            }

            int levelDifference = destination.getLevel() - source.getLevel();
            System.out.println("Level Difference");
            System.out.println(destination.getLevel());
            System.out.println(source.getLevel());
            System.out.println(levelDifference);

            //Movable object can move Up at most 1 level
            if (levelDifference > 1) {
                System.out.println("TOO HIGH TO CLIMB.");
                return false;
            }
        }

        return true;


    }

    /**
     * Checks whether a build at the destination cell is valid.
     * The destination must be adjacent, not occupied by a worker,
     * and either empty or stable and stackable.
     * @param gameObject the object performing the build
     * @param source the cell initiating the action
     * @param destination the target cell
     * @return boolean
     */
    @Override
    public boolean isValidBuild(GameComponent gameObject, Cell source, Cell destination) {

        if (source == null || destination == null) {
            return false;
        }

        //1. Adjacent Check: NO need to, the destination cells are already adjacent

        //2. Check if the destination cell is occupied by another Game Object
        List<GameComponent> cellOccupants = destination.getOccupants();

        //if the cell has no occupants, the game object can be placed here.
        if (cellOccupants.isEmpty()) {
            return true;
        }
        for (GameComponent occupant : cellOccupants) {

            //If the cell has a worker on the cell, return false
            if (occupant.hasCapability(GameComponentEnums.WORKING_ON_CURRENT_CELL)) {
                return false;
            }

            //if the cell has occupants, and one of the occupants is a building level that is stable and stackable
            if (occupant.hasCapability(GameComponentEnums.STABLE) && (occupant.hasCapability(
                GameComponentEnums.STACKING))) {
                //Builds can be up or down any number of levels
                return true;

            }
            //if the cell has an unstable component, it cannot be built on top of
            if (occupant.hasCapability(GameComponentEnums.UNSTABLE)) {
                System.out.println("BUILDING IS UNSTABLE!");
                return false;
            }
        }

        return true;

    }

}
