package game.Injectors;

import game.GodCards.Artemis;
import game.GodCards.GodCard;

/**
 * The ArtemisInjector creates and returns a new instance of the Artemis GodCard.
 * Used to inject dependencies into the GodCardFactory.
 */
public class ArtemisInjector implements DependencyInjector<GodCard> {

    /**
     * Returns a new Artemis GodCard.
     * @return a new Artemis instance
     */
    @Override
    public GodCard injectNewObject() {
        return new Artemis();
    }
}
