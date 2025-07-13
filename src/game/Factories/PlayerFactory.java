package game.Factories;

import game.GameBoard.Board;
import game.Initialiser.GodInitialiser;
import game.Player.Player;
import game.Player.PlayerId;
import game.Player.StandardPlayer;
import game.Initialiser.WorkerInitialiser;
import game.Initialiser.TimerInitialiser;

import java.util.ArrayList;
import java.util.List;

/**
 * The PlayerFactory creates and initializes Player objects,
 * assigning each a unique GodCard, assigning a timer and placing their workers on the board.
 */
public class PlayerFactory {

    private final GodCardFactory godCardFactory;
    private final TimerInitialiser timerInitialiser;
    private final GodInitialiser godInitialiser;
    private final WorkerInitialiser workerInitialiser;

    /**
     * Creates a PlayerFactory that uses the given GodCardFactory to assign god cards.
     * @param godCardFactory the factory to retrieve GodCards from
     */
    public PlayerFactory(GodCardFactory godCardFactory, int timeDurationSeconds) {
        this.godCardFactory = godCardFactory;
        this.timerInitialiser = new TimerInitialiser(timeDurationSeconds);
        this.godInitialiser = new GodInitialiser(godCardFactory);
        this.workerInitialiser = new WorkerInitialiser();


    }

    /**
     * Creates two players, assigns each a GodCard, and places their workers on the board.
     * @param board the game board to place workers on
     * @return a list of initialized players
     */
    public List<Player> createPlayers(Board board) {

        List<Player> players = new ArrayList<>();
        players.add(new StandardPlayer("Player 1", PlayerId.PLAYER_1));
        players.add(new StandardPlayer("Player 2", PlayerId.PLAYER_2));

        godInitialiser.assignGodCards(players);
        workerInitialiser.assignWorkersToBoard(players, board);
        timerInitialiser.assignTimers(players);

        return players;
    }


}
