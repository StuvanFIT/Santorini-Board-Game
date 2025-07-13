package game.Turn;

import game.GameBoard.Board;
import game.GameBoard.Cell;
import game.Managers.BenevolenceManager;

public class BenevolencePhase extends TurnPhase {
    private Turn turn;
    private BenevolenceManager benevolenceManager;

    public BenevolencePhase(Turn turn){
        super("Benevolent");
        this.turn = turn;
        this.benevolenceManager = new BenevolenceManager(2);

    }

    @Override
    public void executePhase(Board board, Turn turn, Cell clickedCell) {

        //Display Window
        benevolenceManager.displayEmpathyOptions(board, turn);

        setComplete(true);
    }

    @Override
    public TurnPhase nextTurnPhase(Turn turn) {
        return new EndTurnPhase(turn);
    }
}
