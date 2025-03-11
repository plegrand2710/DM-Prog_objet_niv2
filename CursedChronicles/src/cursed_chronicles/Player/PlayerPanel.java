package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JFrame {
    private JLabel nameLabel, levelLabel, xpLabel;
    private JTextArea characteristicsArea;
    private JProgressBar lifeBar, defenseBar;
    private InventoryPanel inventoryPanel;
    private JournalPanel journalPanel;

    public PlayerPanel(Player player, InventoryPanel inventory, JournalPanel journal) {
        setTitle("👤 Player Info");
        setSize(400, 350);
        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        nameLabel = new JLabel("Player: " + player.getName());
        levelLabel = new JLabel("⭐ Level: " + player.getLevel());
        xpLabel = new JLabel("🔹 XP: " + player.getExperiencePoints());

        characteristicsArea = new JTextArea();
        characteristicsArea.setEditable(false);

        JPanel topPanel = new JPanel(new GridLayout(5, 1));
        topPanel.add(nameLabel);
        topPanel.add(levelLabel);
        topPanel.add(xpLabel);
        topPanel.add(new JLabel("❤️ Life:"));
        lifeBar = new JProgressBar(0, 100);
        lifeBar.setStringPainted(true);
        lifeBar.setForeground(Color.GREEN);
        topPanel.add(lifeBar);

        topPanel.add(new JLabel("🛡️ Defense:"));

        defenseBar = new JProgressBar(0, 100);
        defenseBar.setStringPainted(true);
        defenseBar.setForeground(Color.BLUE);

        topPanel.add(defenseBar);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(characteristicsArea), BorderLayout.CENTER);

        inventoryPanel = inventory;
        journalPanel = journal;

        updateCharacteristics(player);

        player.addPropertyChangeListener(evt -> updateCharacteristics(player));
    }

    public void updateCharacteristics(Player player) {
        if (characteristicsArea == null) return; 

        for (Characteristic c : player.getCharacteristics()) {
            if (c.getName().equalsIgnoreCase("Life")) {
                lifeBar.setValue(Math.min(c.getValue(), 100)); 
                lifeBar.setString(c.getValue() + "/100");
            } else if (c.getName().equalsIgnoreCase("Defense")) {
                defenseBar.setValue(Math.min(c.getValue(), 100));
                defenseBar.setString(c.getValue() + "/100");
            }
        }

        characteristicsArea.setText("📜 Other Characteristics:\n");
        for (Characteristic c : player.getCharacteristics()) {
            if (!c.getName().equalsIgnoreCase("Life") && !c.getName().equalsIgnoreCase("Defense")) {
                characteristicsArea.append(c.getName() + ": " + c.getValue() + "\n");
            }
        }
    }

    public void showPlayerInfo() {
        setVisible(true);
    }
}
