package game.Initialiser;

import game.Action.Timeable;
import game.Managers.GameTimerManager;

import java.util.List;

public class TimerInitialiser {
    private final int timeInSeconds;

    public TimerInitialiser(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public void assignTimers(List<? extends Timeable> timeableObjs) {
        for (Timeable timeable : timeableObjs) {
            timeable.setTimerManager(new GameTimerManager(timeInSeconds));
        }
    }
}

