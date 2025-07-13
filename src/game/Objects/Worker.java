package game.Objects;

import game.Action.Builder;
import game.Action.Movable;
import game.Capabilities.GameComponentEnums;
import game.Factories.BuildingFactory;
import game.Factories.Factory;
import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.GameBoard.Coordinate;
import game.Player.Player;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Worker on the game board. A Worker can move and build,
 * and is associated with a specific Player.
 */
public class Worker extends GameComponent implements Movable, Builder, PlayerItem {

    private Cell currentCell;
    private Cell previousCell;
    private Cell previousBuildCell;
    private Player owner;

    /**
     * Constructs a Worker and associates it with a cell, icon, and player.
     * @param sourceCell the initial cell the worker is placed on
     * @param pathToIconFile the path to the worker's image
     * @param player the owning player
     */
    public Worker(Cell sourceCell, String pathToIconFile, Player player) {
        super(pathToIconFile);
        this.currentCell = sourceCell;
        this.owner = player;

        //Generate capabilities
        generateCapabilities();
    }

    /**
     * Assigns the default capabilities for a worker, such as the ability to move and mark cell occupation.
     */
    public void generateCapabilities() {
        addNewCapability(GameComponentEnums.CAN_WALK_ON_TILES);
        addNewCapability(GameComponentEnums.WORKING_ON_CURRENT_CELL);

    }

    /**
     * Moves the worker to a new cell, updating its previous and current cell state.
     * @param destination the cell to move to
     */
    @Override
    public void moveTo(Cell destination) {

        //The previous cell becomes the current cell
        previousCell = currentCell;

        //Remove from current cell
        currentCell.removeOccupant(this);

        //Add to new cell destination
        destination.addOccupant(this);

        //Update currentCell
        currentCell = destination;

    }

    /**
     * Builds a tower level or dome on the specified destination cell.
     * Also updates the worker's previous build cell.
     * @param destination the cell to build on
     * @param board the game board reference
     */
    @Override
    public void buildTo(Cell destination, Board board) {

        Factory<Building, Integer> buildingFactory = new BuildingFactory();

        previousBuildCell = destination;

        System.out.println("BEFORE LEVEL: " + destination.getLevel());
        // Increase the level
        destination.incrementLevel();

        // Get new level
        int newLevel = destination.getLevel();
        Integer newLevelInteger = Integer.valueOf(newLevel);

        System.out.println("NEW LEVEL: " + newLevel);
        //Add to new destination
        Building buildingComponent = buildingFactory.getComponent(newLevelInteger);
        destination.addOccupant(buildingComponent);

        // Set background image for the cell panel
        URL imagePath = buildingComponent.getBackgroundRenderImagePath();
        destination.getCellPanel().setImagePanelBackground(imagePath);
    }

    /**
     * Returns the last cell where the worker built during this turn.
     * @return the previous build cell
     */
    @Override
    public Cell getPreviousBuildCell() {
        return previousBuildCell;
    }

    /**
     * Sets the cell where the worker just built.
     * @param cell the destination cell of the most recent build
     */
    @Override
    public void setPreviousBuildCell(Cell cell) {
        this.previousBuildCell = cell;

    }

    /**
     * Returns the current cell where the worker is located.
     * @return the current cell
     */
    @Override
    public Cell getCurrentCell() {
        return currentCell;
    }

    /**
     * Returns the cell the worker was previously located before its most recent move.
     * @return the previous cell
     */
    @Override
    public Cell getPreviousCell() {
        return previousCell;
    }

    /**
     * Updates the worker’s previous cell to the provided cell.
     * @param sourceCell the new previous cell
     */
    @Override
    public void setPreviousCell(Cell sourceCell) {
        previousCell = sourceCell;
    }

    /**
     * Returns a list of all valid adjacent cells where the worker can build.
     * @param board the game board reference
     * @return list of valid building locations
     */
    @Override
    public List<Cell> getValidBuildingLocations(Board board) {
        List<Cell> validBuildingCells = new ArrayList<>();
        Coordinate currCoordinate = currentCell.getCoordinate();

        //From the current coordinate we have, we need to get valid adjacent building coordinates
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {

                if (x == 0 && y == 0) {
                    continue;
                }

                int new_x = currCoordinate.getxCoordinate() + x;
                int new_y = currCoordinate.getyCoordinate() + y;

                //Construct the coordinate
                Coordinate newCoordinate = new Coordinate(new_x, new_y);
                //Get the cell
                Cell newCell = board.getCell(newCoordinate);

                if (board.isValidBuild(this, currentCell, newCell)) {

                    validBuildingCells.add(newCell);
                }
            }
        }

        return validBuildingCells;

    }

    /**
     * Returns a list of all valid adjacent cells where the worker can move.
     * @param board the game board reference
     * @return list of valid movement locations
     */
    @Override
    public List<Cell> getValidMoves(Board board) {

        //THE BOARD SHOULD BE RESPONSIBLE FOR VALIDATING WHETHER A CELL IS VALID TO MOVE TO, WHILST
        // THE MOVABLE OBJECT SHOULD RETRIEVE AND RETURN ALL POSSIBLE VALID CELLS.

        List<Cell> validMoves = new ArrayList<>();
        Coordinate currCoordinate = currentCell.getCoordinate();

        //From the current coordinate we have, we need to get valid adjacent move coordinates
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {

                if (x == 0 && y == 0) {
                    continue;
                }

                int new_x = currCoordinate.getxCoordinate() + x;
                int new_y = currCoordinate.getyCoordinate() + y;

                //Construct the coordinate
                Coordinate newCoordinate = new Coordinate(new_x, new_y);
                //Get the cell
                Cell newCell = board.getCell(newCoordinate);

                if (board.isValidMove(this, currentCell, newCell)) {

                    validMoves.add(newCell);
                }
            }
        }
        return validMoves;
    }

    /**
     * Checks if this worker belongs to the specified player.
     * @param player the player to check against
     * @return true if owned by the player, false otherwise
     */
    @Override
    public boolean belongsTo(Player player) {
        return this.owner == player;
    }

    /**
     * @return the player who owns this worker
     */
    @Override
    public Player getOwner() {
        return owner;
    }
}
