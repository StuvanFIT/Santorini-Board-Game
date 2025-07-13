package game.Capabilities;


public enum GameComponentEnums {


    /**
     * CAN_WALK_ON_TILES = Assigned to game components that can move on tiles
     * WORKING_ON_CURRENT_CELL = Assigned to game components like WORKER that are currently
     * working/standing on top of a cell. This prevents other workers from moving to the cell.
     * STABLE = Assigned to game components that can be built on top of (cell/ground, lvl1, lvl2,
     * lvl3) UNSTABLE = Assigned to game components that cannot be placed on top (i.e. DOME)
     * MULTI_OCCUPANCY = Assigned to game components that can move to cells that already have a
     * worker/movable object. This can be altered in the future. STACKING = for buildings to stack
     * on top of each other.
     **/

    CAN_WALK_ON_TILES,
    WORKING_ON_CURRENT_CELL,
    WALKABLE_ON,
    STABLE,
    UNSTABLE,
    STACKING,
    RENDER_AS_BACKGROUND,
    GOD_MOVE,
    GOD_BUILD,
    RESTRICTION_ON_VALID_CELLS,
    BEFORE_MOVEMENT,
    AFTER_MOVEMENT,
    BEFORE_BUILDING,
    AFTER_BUILDING

}
