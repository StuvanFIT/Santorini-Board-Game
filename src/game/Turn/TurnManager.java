package game.Turn;

import game.GameBoard.Board;
import game.Managers.PhaseUIManager;
import game.Player.Player;
import game.Managers.GameTimerManager;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Manages the overall game flow and player turns.
 * Maintains player queue, assigns active turns, and handles turn transitions and win conditions.
 */
public class TurnManager {

    private final List<Player> players;
    Deque<Player> playerQueue = new LinkedList<>();


    private Player activePlayer;
    private Turn currentTurn;
    private boolean isGameOver = false;
    private List<TurnPhase> phasesList = new ArrayList<>();
    private Board board;
    private JLabel playerTurnLabel = new JLabel("Player 1", SwingConstants.CENTER);
    private Player winningPlayer = null;
    private int twoTurnCounter = 2;
    Map<Player, Integer> bonusTurnCounts = new HashMap<>();

    /**
     * Constructs the TurnManager with a list of participating players.
     * @param players the list of players in the game
     */
    public TurnManager(List<Player> players) {
        this.players = players;

        setPlayerQueue(players);
    }

    /**
     * Returns the current turn being executed.
     * @return the active Turn
     */
    public Turn getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Sets the game board context for this TurnManager.
     * @param board the Board object to be set
     */
    public void setCurrentBoard(Board board) {
        this.board = board;
    }

    /**
     * Updates the game over state.
     * @param bool true if the game is over, false otherwise
     */
    public void setIsGameOver(boolean bool) {
        this.isGameOver = bool;
    }

    public Map<Player, Integer> getBonusTurnCounts(){
        return this.bonusTurnCounts;
    }

    /**
     * Sets the active player.
     * @param player the player currently taking a turn
     */
    public void setActivePlayer(Player player) {
        this.activePlayer = player;
    }

    public void setPlayerQueue(List<Player> players){
        playerQueue.addAll(players);
    }

    /**
     * Returns a defensive copy of the player queue.
     * @return a new Queue of players
     */
    public Queue<Player> getPlayerQueue() {
        return new LinkedList<>(this.playerQueue);
    }

    /**
     * Returns a players queue excluding the target player
     * @return a new Queue of players
     */
    public Queue<Player> getOtherPlayerQueue(Player currentPlayer) {
        return this.playerQueue.stream()
                .filter(p -> !p.equals(currentPlayer))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Returns true if the game is over.
     * @return game over state
     */
    public boolean isGameOver() {
        return this.isGameOver;
    }

    /**
     * Constructs a sequence of TurnPhases for a given turn.
     * @param turn the Turn object for the player
     * @return list of TurnPhase objects representing a complete turn
     */
    public List<TurnPhase> getPhases(Turn turn) {

        phasesList.clear();

        //Our starting phase
        TurnPhase currentPhase = new MovePhase(turn);

        while (currentPhase != null) {
            //ADD THE PHASE
            phasesList.add(currentPhase);

            //Move to next phase
            currentPhase = currentPhase.nextTurnPhase(turn);
        }

        return new ArrayList<>(this.phasesList);

    }

    /**
     * Begins the main game loop, alternating player turns until the game ends.
     * @param frame the game window frame used to update the player turn UI
     */
    public void startGameLoop(JFrame frame) {

        //Add player turn label at the top
        playerTurnLabel.setFont(new Font("Arial", Font.BOLD, 50));
        frame.add(playerTurnLabel, BorderLayout.NORTH);

        //The game ends when there is one player left in the queue
        System.out.println(playerQueue.size());
        while (!isGameOver()) {

            System.out.println("Queue before turn: " + playerQueue);

            if (playerQueue.isEmpty()) {
                System.out.println("ERROR: Queue is empty — ending prematurely!");
                break;
            }

            Player currentPlayer = playerQueue.poll(); // take first player
            activePlayer = currentPlayer;

            System.out.println("Current Player: " + currentPlayer.getPlayerId());
            System.out.println("Queue after turn: " + playerQueue);


            // BONUS TURN HANDLING
            if (bonusTurnCounts.containsKey(currentPlayer)) {
                int remaining = bonusTurnCounts.get(currentPlayer) - 1;

                if (remaining == 0) {
                    bonusTurnCounts.remove(currentPlayer); // done with bonus turns
                    playerQueue.add(currentPlayer);


                } else {
                    bonusTurnCounts.put(currentPlayer, remaining);
                    playerQueue.addFirst(currentPlayer);
                }

            } else {
                playerQueue.add(currentPlayer);
            }



            //Start the timer of the current player:
            GameTimerManager timerManager = currentPlayer.getTimerManager();
            timerManager.startTimer();

            timerManager.setOnTimerCompleteCallback( () ->{
                setIsGameOver(true);
            });


            System.out.println("CURRENT PLAYER'S TURN: " + currentPlayer.getPlayerId());

            //Create a Turn entity: Each player has 1 turn
            Turn turn = new Turn(currentPlayer);
            currentTurn = turn;

            executeTurn(turn);




        }

        //If the game is over and there is one player left, display the winning player
        if (isGameOver) {
            if (winningPlayer != null) {
                playerTurnLabel.setText(winningPlayer.getPlayerId() + " WINS!!!!!");

            } else {
                //Only 1 person in the queue
                Player winner = playerQueue.peek();
                playerTurnLabel.setText(winner.getPlayerId() + " WINS!!!!!");
            }

        } else{
            System.out.println("ERROR: GAME DID NOT END!");
            System.out.println(playerQueue);
        }


    }

    /**
     * Executes all phases for a given turn.
     * @param turn the Turn to be executed
     */
    private void executeTurn(Turn turn) {
        List<TurnPhase> phases = getPhases(turn);

        for (TurnPhase phase : phases) {

            if (isGameOver) {
                return;
            }

            //Set the turn manager for each phase
            phase.setTurnManager(this);

            System.out.println("CURRENT PHASE: " + phase.getPhaseName());

            turn.setCurrentPhase(phase);

            playerTurnLabel.setText(
                turn.getPlayer().getPlayerId().name() + ": " + turn.getCurrentPhase()
                    .getPhaseName());

            phase.executePhase(board, turn, null); // Initial call (null cell until clicked)

            // Loop: Wait until the player finishes this phase
            while (!phase.isComplete() && !isGameOver) {
                try {
                    Thread.sleep(100); // Sleep a bit so you don't lock the CPU

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (isGameOver) {
                PhaseUIManager phaseUIManager = phase.getPhaseUIManager();
                phaseUIManager.cleanUp(board);
                return;
            }
        }
    }

    /**
     * Returns the player who won the game, if determined.
     * @return the winning Player
     */
    public Player getWinningPlayer() {
        return this.winningPlayer;
    }

    /**
     * Sets the player who has won the game.
     * @param player the winning Player
     */
    public void setWinningPlayer(Player player) {
        this.winningPlayer = player;
    }

    /**
     * Marks a player as having lost due to no valid moves and ends their turn.
     * @param turn the current Turn
     * @param turnPhase the TurnPhase during which the loss was determined
     */
    public void playerLoses(Turn turn, TurnPhase turnPhase) {
        // If no workers have valid moves left, the player loses
        this.setIsGameOver(true);
        this.getPlayerQueue().remove(turn.getPlayer());
        turnPhase.setComplete(true);
    }


}