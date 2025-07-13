package game.Action;

import game.GameBoard.Board;
import game.Player.Player;
import game.Turn.Turn;
import game.Turn.TurnManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
* The current player gives someone their turn
* You skip the turn, and they play twice in a row
*
* */
public class SwapTurnOrderAction implements BenevolentAction {
    @Override
    public void applyBenevolence(Benevolent benevolent, Board board, Turn turn) {

        //Get current player
        Player player = turn.getPlayer();

        //Get Turn manager
        TurnManager turnManager = turn.getCurrentPhase().getTurnManager();
        //Current State of the player queue
        Queue<Player> playerQueue = turnManager.getPlayerQueue();

        //Queue without current player
        Queue<Player> queuePlayers = turnManager.getOtherPlayerQueue(player);


        //We need to select a player that we will give two turns to:
        Player twoTurnPlayer = (Player) JOptionPane.showInputDialog(
                null,
                "Select a player to give 2 extra turns:",
                "Support Another Player! Show some empathy!",
                JOptionPane.QUESTION_MESSAGE,
                null,
                queuePlayers.toArray(),
                queuePlayers.toArray()[0]
        );

        if (twoTurnPlayer != null){

            //Set the nwe player queue
            turnManager.getBonusTurnCounts().put(twoTurnPlayer, 2);

            JOptionPane.showMessageDialog(
                    null,
                    "You showed some cooperation! You skip your turn and they play twice!\n",
                    "Swap Turn Order",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }





    }
}
