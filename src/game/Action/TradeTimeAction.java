package game.Action;

import game.GameBoard.Board;
import game.Player.Player;
import game.Managers.GameTimerManager;
import game.Turn.Turn;
import game.Turn.TurnManager;

import javax.swing.*;
import java.util.Queue;

public class TradeTimeAction implements BenevolentAction{

    @Override
    public void applyBenevolence(Benevolent benevolent, Board board, Turn turn) {

        //Get turn manager
        TurnManager turnManager = turn.getCurrentPhase().getTurnManager();

        //Get current player
        Player player = turn.getPlayer();

        //Get player queue
        Queue<Player> queuePlayers = turnManager.getOtherPlayerQueue(player);

        if (queuePlayers.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No other players to support!");
            return;
        }

        Player selectedPlayer = (Player) JOptionPane.showInputDialog(
                null,
                "Select a player to give extra time:",
                "Support Another Player! Show some empathy!",
                JOptionPane.QUESTION_MESSAGE,
                null,
                queuePlayers.toArray(),
                queuePlayers.toArray()[0]
        );

        //If the current players remaining time <=0, then they cannot trade off their time
        GameTimerManager currTimeManager = player.getTimerManager();
        int currPlayerTime = currTimeManager.getRemainingTime();

        if (currPlayerTime <=0){
            JOptionPane.showMessageDialog(null, "You do not have enough time to trade off!");
            return;
        }

        int timeToGive = 0;

        while (true) {
            String input = JOptionPane.showInputDialog(
                    null,
                    "Enter the number of seconds you want to trade off (MAX IS 30 SECONDS): ",
                    "Time Boost",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                return;
            }

            try {
                timeToGive = Integer.parseInt(input);

                if (timeToGive <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number.");
                } else if (timeToGive > currPlayerTime) {
                    JOptionPane.showMessageDialog(null, "You don't have that much time. Try a smaller number.");
                } else if (timeToGive >30 ){
                    JOptionPane.showMessageDialog(null, "Max is 30 seconds trade off");
                }else {
                    break;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a whole number.");
            }
        }


        //Add the timetogive to the other player
        GameTimerManager seltimerManager = selectedPlayer.getTimerManager();
        int selTime = seltimerManager.getRemainingTime();
        int extraTime = selTime + timeToGive;
        seltimerManager.setTimer(extraTime);
        seltimerManager.updateTimer();


        //Subtract the time to give from the current player
        GameTimerManager currTimerManager = player.getTimerManager();
        int currTime = currTimerManager.getRemainingTime();
        int newTime = currTime - timeToGive;
        currTimerManager.setTimer(newTime);
        currTimerManager.updateTimer();

        JOptionPane.showMessageDialog(
                null,
                "You've sacrificed and granted another Player a time boost of " + timeToGive + " seconds!\n",
                "Support Time Boost Activated",
                JOptionPane.INFORMATION_MESSAGE
        );

    }
}
