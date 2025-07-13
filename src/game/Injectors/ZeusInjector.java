package game.Injectors;

import game.GodCards.GodCard;
import game.GodCards.Zeus;

/**
 * The ZeusInjector creates and returns a new instance of the Zeus GodCard.
 * Used to inject dependencies into the GodCardFactory.
 */
public class ZeusInjector implements DependencyInjector<GodCard> {

    /**
     * Returns a new Zeus GodCard.
     * @return a new Zeus instance
     */
    @Override
    public GodCard injectNewObject() {
        return new Zeus();
    }
}
