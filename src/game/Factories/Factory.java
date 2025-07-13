package game.Factories;

/**
 * The Factory interface defines a generic method to retrieve a component
 * based on a given input key.
 * @param <T> the type of object to return
 * @param <I> the type of input key used to generate or select the object
 */
public interface Factory<T, I> {

    /**
     * Returns a component based on the input key.
     * @param inputKey the unique key used to retrieve the component
     * @return the corresponding component
     */
    T getComponent(I inputKey);
}
