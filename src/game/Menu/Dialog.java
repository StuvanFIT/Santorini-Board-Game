package game.Menu;

/**
 * An abstract base class for displaying dialog windows such as help or game rules.
 * All custom dialog screens should extend this class and implement the show method.
 */
public abstract class Dialog {

    /**
     * Displays the dialog window.
     */
    public abstract void show();
}
