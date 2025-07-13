package game.Action;

import game.GameBoard.Board;
import game.Turn.Turn;

public interface BenevolentAction {

    void applyBenevolence(Benevolent benevolent, Board board, Turn turn);
}
