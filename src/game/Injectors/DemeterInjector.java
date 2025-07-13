package game.Injectors;

import game.GodCards.Demeter;
import game.GodCards.GodCard;

/**
 * The DemeterInjector creates and returns a new instance of the Demeter GodCard.
 * Used to inject dependencies into the GodCardFactory.
 */
public class DemeterInjector implements DependencyInjector<GodCard> {

    /**
     * Returns a new Demeter GodCard.
     * @return a new Demeter instance
     */
    @Override
    public GodCard injectNewObject() {
        return new Demeter();
    }
}
