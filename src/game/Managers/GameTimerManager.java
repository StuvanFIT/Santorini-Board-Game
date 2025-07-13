package game.Managers;

import game.Managers.TimerUIManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimerManager {

    private int remainingSeconds;
    private Timer timer; //use the java swing timer. Recommended for java swing applications. Custom timer vs swing timer in documentation.
    private JLabel timerLabel;
    private Runnable onTimerCompleteCallback;
    private TimerUIManager timerUIManager = new TimerUIManager();

    public GameTimerManager(int totalDurationSeconds){
        this.remainingSeconds = totalDurationSeconds;
        this.timer  = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingSeconds--;
                timerUIManager.updateTimerLabel(timerLabel,remainingSeconds);

                if (remainingSeconds <=0 ){
                    timer.stop();
                    timerUIManager.endTimerLabel(timerLabel, "TIME IS UP!");

                    if (onTimerCompleteCallback != null){
                        onTimerCompleteCallback.run();
                    }
                }

            }
        });
    }

    public void startTimer(){
        this.timer.start();
    }

    public void stopTimer(){
        this.timer.stop();
    }

    public void restartTimer(){
        this.timer.restart();
    }

    public void setTimer(int setDuration){
        stopTimer();
        remainingSeconds = setDuration;
    }

    public int getRemainingTime(){
        return this.remainingSeconds;
    }

    public boolean isTimerActive(){
        return this.timer.isRunning();
    }

    public void setTimerLabel(JLabel label){
        this.timerLabel = label;
        timerUIManager.updateTimerLabel(label,remainingSeconds);
    }
    public void updateTimer(){
        timerUIManager.updateTimerLabel(timerLabel,remainingSeconds);
    }

    public void setOnTimerCompleteCallback(Runnable callback){
        this.onTimerCompleteCallback = callback;
    }



}
