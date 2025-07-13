package game.Player;

import game.Action.Benevolent;
import game.GodCards.GodCard;
import game.Objects.Worker;
import game.Managers.GameTimerManager;
import game.Action.Timeable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract player in the game.
 * Each player has a unique ID, an associated GodCard, and a list of worker pieces.
 */
public abstract class Player implements Timeable, Benevolent {

    protected GodCard godCard;
    protected PlayerId playerId;
    private GameTimerManager gameTimer;
    private ArrayList<Worker> workers = new ArrayList<>();
    private boolean BenevolenceStatus = false;
    private int BenevolenceCounter = 0;

    /**
     * Returns the unique identifier for the player.
     * @return the player's ID
     */
    public PlayerId getPlayerId() {
        return playerId;
    }

    /**
     * Sets the unique identifier for the player.
     * @param playerId the ID to assign
     */
    public void setPlayerId(PlayerId playerId) {
        this.playerId = playerId;
    }

    /**
     * Initializes the worker pieces that belong to this player.
     * @param playerWorkers a list of worker instances to assign
     */
    public void initialiseWorkers(List<Worker> playerWorkers) {
        for (int i = 0; i < playerWorkers.size(); i++) {
            Worker worker = playerWorkers.get(i);
            this.workers.add(worker);
        }
    }

    /**
     * Returns a list of the player's worker pieces.
     * @return list of workers
     */
    public List<Worker> getWorkers() {
        return this.workers;
    }

    /**
     * Returns the GodCard assigned to this player.
     * @return the player's GodCard
     */
    public GodCard getGodCard() {
        return this.godCard;
    }

    /**
     * Assigns a GodCard to this player.
     * @param godCard the GodCard to assign
     */
    public void setGodCard(GodCard godCard) {
        this.godCard = godCard;
    }

    @Override
    public void setTimerManager(GameTimerManager gameTimerManager){
        this.gameTimer = gameTimerManager;
    }

    @Override
    public GameTimerManager getTimerManager(){
        return this.gameTimer;
    }

    @Override
    public boolean hasUsedBenevolence(){
        return BenevolenceStatus;
    }

    @Override
    public void setUsedBenevolence(boolean used){
         this.BenevolenceStatus = used;
    }

    @Override
    public int getBenevolenceCounter(){
        return this.BenevolenceCounter;
    }

    @Override
    public void incrementBenevolanceCounter(){
        this.BenevolenceCounter += 1;
    }
}
