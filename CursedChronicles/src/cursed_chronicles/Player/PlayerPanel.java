package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JFrame {
    private JLabel nameLabel, levelLabel, xpLabel;
    private JTextArea characteristicsArea;
    private InventoryPanel inventoryPanel;
    private JournalPanel journalPanel;

    public PlayerPanel(Player player, InventoryPanel inventory, JournalPanel journal) {
        setTitle("👤 Player Info");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        nameLabel = new JLabel("Player: " + player.getName());
        levelLabel = new JLabel("⭐ Level: " + player.getLevel());
        xpLabel = new JLabel("🔹 XP: " + player.getExperiencePoints());

        characteristicsArea = new JTextArea();
        characteristicsArea.setEditable(false);
        updateCharacteristics(player);

        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.add(nameLabel);
        topPanel.add(levelLabel);
        topPanel.add(xpLabel);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(characteristicsArea), BorderLayout.CENTER);

        inventoryPanel = inventory;
        journalPanel = journal;
    }

    public void updateCharacteristics(Player player) {
        characteristicsArea.setText("🛡️ Characteristics:\n");
        for (Characteristic c : player.getCharacteristics()) {
            characteristicsArea.append(c.getName() + ": " + c.getValue() + "\n");
        }
    }

    public void showPlayerInfo() {
        setVisible(true);
    }
}