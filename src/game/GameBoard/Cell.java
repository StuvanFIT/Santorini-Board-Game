package game.GameBoard;

import game.CellPanel;
import game.Objects.GameComponent;
import game.Objects.Stackable;
import game.Utilities;
import java.awt.Dimension;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * The Cell class represents a single square on the game board.
 * A cell can hold multiple GameComponent objects (e.g. workers, buildings).
 */
public class Cell implements Stackable {


    private final Coordinate coordinate;
    private CellPanel cellPanel;
    private List<GameComponent> occupants; //A cell can be occupied with any game component: worker, building, non-movable objects etc...
    private int levelHeight = 0;

    /**
     * Constructs a Cell at the specified coordinate.
     * @param coordinate the location of the cell on the board
     */
    public Cell(Coordinate coordinate) {

        this.coordinate = coordinate;
        this.cellPanel = new CellPanel("/images/GamePieces/empty.png");
        this.cellPanel.setPreferredSize(new Dimension(64, 64));
        this.occupants = new ArrayList<>();
    }

    /**
     * Increments the internal level height counter of the cell.
     */
    public void incrementLevel() {
        this.levelHeight += 1;
    }

    /**
     * Returns the panel used to visually represent the cell.
     * @return the CellPanel
     */
    public CellPanel getCellPanel() {
        return this.cellPanel;
    }

    /**
     * Returns the coordinate of the cell.
     * @return the cell's coordinate
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    /**
     * Checks if the cell has no occupants.
     * @return boolean
     */
    public boolean notOccupied() {
        return this.occupants.isEmpty();
    }

    /**
     * Adds a new GameComponent to the cell and updates the display.
     * @param newOccupant the component to add
     */
    public void addOccupant(GameComponent newOccupant) {
        this.occupants.add(newOccupant);
        updateDisplay();
    }

    /**
     * Removes a GameComponent from the cell and updates the display.
     * @param occupant the component to remove
     */
    public void removeOccupant(GameComponent occupant) {
        this.occupants.remove(occupant);
        updateDisplay();
    }

    /**
     * Returns a copy of the list of current occupants in the cell.
     * @return list of GameComponents
     */
    public List<GameComponent> getOccupants() {
        //Defensive Copying technique: instead of returning the original copy of the occupants list, we return another copy with the same occupants
        return new ArrayList<>(occupants);
    }

    //We need to refresh the game board and redraw the icons

    /**
     * When we moved a movable object, add a new building or highlighted a valid mode
     * Revalidate() = Tells the layout manager that the contents of the panel (like what's inside a
     * panel) has changed. -It then recalculates the layout
     * Repaint() = Redraws the components and refreshes the visual appearance of the board. -If you
     * don't use repaint, then the changes would be applied in the back end, but will not be seen in
     * the front end
     **/
    private void updateDisplay() {

        //remove all labels,icons (i.e. old workers) etc..
        cellPanel.removeAll();

        //WE NEED TO FIND THE TOP MOST ICON OF THE STACKABLE BUILDING component
        GameComponent backgroundComponent = occupants.stream()
            .filter(GameComponent::isBackgroundRenderable)
            .reduce(
                (first, second) -> second) //FOR A STACKABLE BUILDING, WE ONLY WANT TO DISPLAY THE TOP MOST ICON IMAGE
            .orElse(null);

        //SET THE IMAGE PANEL BACKGROUND OF THE CELL PANEL
        if (backgroundComponent != null) {
            URL imagePath = backgroundComponent.getBackgroundImagePath();
            System.out.println(imagePath);
            cellPanel.setImagePanelBackground(imagePath);
        } else {
            URL url = Cell.class.getResource("/images/GamePieces/empty.png");
            System.out.println(url);
            cellPanel.setImagePanelBackground(url);
        }

        // Display other (non-background) components as icons: this includes workers etc......7
        for (GameComponent g : occupants) {
            if (!g.isBackgroundRenderable()) {
                ImageIcon icon = Utilities.scaleIcon(g.getIcon(), 32, 32);
                JLabel label = new JLabel(icon);
                cellPanel.add(label);
            }
        }

        cellPanel.revalidate();
        cellPanel.repaint();
    }

    /**
     * Returns the height level of the building on this cell.
     * @return current level height
     */
    @Override
    public int getLevel() {
        return this.levelHeight;
    }
}
