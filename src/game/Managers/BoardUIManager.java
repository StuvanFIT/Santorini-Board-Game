package game.Managers;

import game.GameBoard.Cell;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * BoardUIManager handles cell highlighting and visual feedback
 * for valid moves or actions on the game board.
 */
public class BoardUIManager {

    private final List<Cell> highlightedCells = new ArrayList<>();

    /**
     * Returns a copy of all currently highlighted cells.
     * @return list of highlighted cells
     */
    public List<Cell> getHighlightedCells() {
        //DEFENSIVE COPYING
        return new ArrayList<>(highlightedCells);
    }

    /**
     * Highlights the given cells with the specified color.
     * @param validCells the cells to highlight
     * @param colour the highlight color
     */
    public void highlightCells(List<Cell> validCells, Color colour) {
        System.out.println("THIS IS THE HIGHLIGHTED CELLS SHIT");
        System.out.println(validCells);

        for (Cell c : validCells) {
            if (c != null) {
                JPanel panel = c.getCellPanel();
                panel.setBackground(colour);
                highlightedCells.add(c);
            }
        }
    }

    /**
     * Clears all current highlights and resets cell background colors.
     */
    //If we click on a cell again, we want to remove the current highlights
    public void clearHighlights() {
        System.out.println(highlightedCells);

        for (Cell c : highlightedCells) {
            c.getCellPanel().setBackground(Color.LIGHT_GRAY);
        }
        highlightedCells.clear();
    }


}
