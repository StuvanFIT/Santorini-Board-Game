package game.Managers;

import game.Action.*;
import game.Capabilities.BenevolentEnums;
import game.GameBoard.Board;
import game.Player.Player;
import game.Player.PlayerId;
import game.Turn.Turn;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BenevolenceManager {

    private JPanel BenevolantPanel;
    private int MAX_BENEVOLANCE;
    private final Map<BenevolentEnums, BenevolentAction> BenevolentActionsMap = new EnumMap<>(BenevolentEnums.class);
    private Map<PlayerId, JLabel> benevolenceStatusLabels = new HashMap<>();
    public BenevolenceManager(int max){
        BenevolentActionsMap.put(BenevolentEnums.INCREASE_TIMER, new TradeTimeAction());
        BenevolentActionsMap.put(BenevolentEnums.SWAP_TURN_ORDER, new SwapTurnOrderAction());
        this.MAX_BENEVOLANCE = max;
        BenevolantPanel = new JPanel();

    }

    public void displayEmpathyOptions(Board board, Turn turn){

        //Get current player
        Benevolent currBenevolent = turn.getPlayer();


        if (!currBenevolent.hasUsedBenevolence()){
            int message = JOptionPane.showConfirmDialog(
                    null,
                    "Greetings, " + currBenevolent + ". This is the Benevolence Phase! Would you like to help another player?\n" +
                            "Note: You can only activate one Benevolant Action. Use it wisely!",
                    "Benevolence Opportunity",
                    JOptionPane.YES_NO_OPTION
            );

            if (message == JOptionPane.YES_OPTION) {
                BenevolentEnums[] options = BenevolentEnums.values();
                BenevolentEnums action = (BenevolentEnums) JOptionPane.showInputDialog(
                        null,
                        "Choose a Benevolent Action:",
                        "Select Action",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (action != null) {
                    executeBenevolence(board, turn, action, currBenevolent);
                    currBenevolent.incrementBenevolanceCounter();

                    int maxBenevolanceMessage = JOptionPane.showConfirmDialog(
                            null,
                            turn.getPlayer().getPlayerId() + " has: " + (MAX_BENEVOLANCE - currBenevolent.getBenevolenceCounter()) + " Benevolent Opportunities left! Use Wisely!",
                            "Remaining Benevolent Chances",
                            JOptionPane.DEFAULT_OPTION
                    );


                    if (currBenevolent.getBenevolenceCounter() == MAX_BENEVOLANCE){
                        currBenevolent.setUsedBenevolence(true);
                    }

                }


            } else {
                System.out.println("Did not activate Benevolence!");
                turn.getCurrentPhase().setComplete(true);
            }
        } else{

            int message = JOptionPane.showConfirmDialog(
                    null,
                    "You have already used your max number of benevolent chances!",
                    "MESSAGE",
                    JOptionPane.DEFAULT_OPTION
            );

            if (message == JOptionPane.OK_OPTION) {
                turn.getCurrentPhase().setComplete(true);
            } else {
                turn.getCurrentPhase().setComplete(true);
            }

        }



    }

    public void executeBenevolence(Board board, Turn turn, BenevolentEnums action, Benevolent benevolentObj){

        //Retrieve the benevolent action
        BenevolentAction benevolentAction = BenevolentActionsMap.get(action);

        if (benevolentAction != null){
            benevolentAction.applyBenevolence(benevolentObj, board, turn);
        }
    }


}
