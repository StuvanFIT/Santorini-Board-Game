package game.Action;

import game.Managers.GameTimerManager;

public interface Timeable {

    void setTimerManager(GameTimerManager gameTimerManager);

    GameTimerManager getTimerManager();


}
