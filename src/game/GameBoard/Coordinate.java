package game.GameBoard;

/**
 * The Coordinate class represents a position on the board using x and y values.
 * Used to identify the location of a Cell.
 */
public class Coordinate {

    private int xCoordinate;
    private int yCoordinate;

    /**
     * Creates a new Coordinate with the given x and y values.
     * @param x the horizontal position
     * @param y the vertical position
     */
    public Coordinate(int x, int y) {

        this.xCoordinate = x;

        this.yCoordinate = y;
    }

    /**
     * Returns the x (horizontal) value.
     * @return the x-coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Returns the y (vertical) value.
     * @return the y-coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }

    /**
     * Compares this coordinate to another for an equality check.
     * @param obj the object to compare to
     * @return true if both x and y match
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Coordinate)) {
            return false;
        }
        Coordinate other = (Coordinate) obj;
        return this.xCoordinate == other.xCoordinate && this.yCoordinate == other.yCoordinate;
    }

    /**
     * Generates a hash code for use in maps or sets.
     * @return the hash code based on x and y
     */
    @Override
    public int hashCode() {
        return 31 * xCoordinate + yCoordinate; // or use Objects.hash(x, y);
    }

    /**
     * Returns a string in the format (x,y).
     * @return string representation of the coordinate
     */
    @Override
    public String toString() {
        return "(" + xCoordinate + "," + yCoordinate + ")";
    }


}
