package game.Factories;

import game.GodCards.GodCard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GodCardFactory stores and provides access to GodCard objects
 * using the card name as a lookup key.
 */
public class GodCardFactory implements Factory<GodCard, String> {

    private Map<String, GodCard> godCardLibrary = new HashMap<>();

    /**
     * Initializes the factory with a list of GodCards. (Constructor)
     * @param godCardItems one or more GodCard instances to register
     */
    public GodCardFactory(GodCard... godCardItems) {

        for (GodCard godCard : godCardItems) {
            //Add the god card to the storage, using the god card name as a key
            godCardLibrary.put(godCard.getName(), godCard);
        }
    }

    /**
     * Returns the GodCard matching the given name.
     * @param godCardName the name of the GodCard
     * @return the matching GodCard
     */
    @Override
    public GodCard getComponent(String godCardName) {
        return godCardLibrary.get(godCardName);
    }

    /**
     * Returns the number of GodCards stored in the factory.
     * @return the number of GodCards
     */
    public int getSizeOfFactory() {
        return godCardLibrary.size();
    }

    /**
     * Returns a list of all registered GodCard names.
     * @return list of GodCard keys
     */
    public List<String> getKeys() {
        return new ArrayList<>(godCardLibrary.keySet());
    }
}
