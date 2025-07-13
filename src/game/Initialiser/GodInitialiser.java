package game.Initialiser;

import game.Factories.GodCardFactory;
import game.GodCards.GodCard;
import game.Player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GodInitialiser {

    private GodCardFactory godCardFactory;

    public GodInitialiser(GodCardFactory godCardFactory){
        this.godCardFactory = godCardFactory;
    }

    /**
     * Randomly assigns unique GodCards to each player.
     * @param players the players to assign GodCards to
     */
    public void assignGodCards(List<Player> players){
        List<String> keys = new ArrayList<>(godCardFactory.getKeys());
        Collections.shuffle(keys);
        for (int i = 0; i < players.size(); i++) {
            String key = keys.get(i);
            GodCard god = godCardFactory.getComponent(key);
            players.get(i).setGodCard(god);
        }
    }
}
