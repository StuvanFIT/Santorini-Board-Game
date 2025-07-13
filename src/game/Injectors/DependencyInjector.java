package game.Injectors;

/**
 * The DependencyInjector interface provides a generic method
 * for creating and injecting new instances of a specific type.
 *
 * @param <T> the type of object to be created and injected
 */
interface DependencyInjector<T> {

    /**
     * Creates and returns a new instance of the injected object.
     * @return a new instance of type T
     */
    public T injectNewObject();
}
