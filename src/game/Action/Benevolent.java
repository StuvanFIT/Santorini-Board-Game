package game.Action;

public interface Benevolent {

    boolean hasUsedBenevolence();
    void setUsedBenevolence(boolean used);

    void incrementBenevolanceCounter();

    int getBenevolenceCounter();
}
