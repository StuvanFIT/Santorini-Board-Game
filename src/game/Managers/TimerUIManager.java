package game.Managers;

import game.Player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TimerUIManager {

    private JPanel timerPanel;


    public TimerUIManager(){
        //Each timer will have its own ui manager
        timerPanel = new JPanel();
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
        timerPanel.setBorder(BorderFactory.createTitledBorder("Player Timers (Seconds)"));
    }

    public void initialiseTimerRendering(List<Player> players) {
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
        timerPanel.setBackground(new Color(245, 245, 245)); // Light background

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            // Player Timer Label
            JLabel timerLabel = new JLabel("# " + player.getTimerManager().getRemainingTime() + "s");
            timerLabel.setFont(new Font("Arial", Font.BOLD, 14));

            timerLabel.setForeground(new Color(0, 102, 204));

            player.getTimerManager().setTimerLabel(timerLabel);

            // Player Label
            JLabel playerLabel = new JLabel("# " + player.getPlayerId().name());
            playerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            playerLabel.setForeground(Color.DARK_GRAY);

            // Container block
            JPanel block = new JPanel(new BorderLayout(10, 0));
            //231, 224, 250
            block.setBackground(i % 2 == 0 ? new Color(230, 240, 255) : new Color(255, 240, 230));
            block.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));

            block.add(playerLabel, BorderLayout.WEST);
            block.add(timerLabel, BorderLayout.EAST);
            //Need to adjust this value to make it a bit bigger to the right
            block.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            // Space between the blocks
            timerPanel.add(Box.createVerticalStrut(10));
            timerPanel.add(block);
        }
    }

    public void updateTimerLabel(JLabel timerLabel, int remainingSeconds){
        if (timerLabel != null) {
            timerLabel.setText(" time: " + remainingSeconds);
        }
    }

    public void endTimerLabel(JLabel timerLabel, String endingString){
        timerLabel.setText(endingString);
    }

    public JPanel getTimerPanel(){
        return this.timerPanel;
    }
}
