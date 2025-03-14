package cursed_chronicles.Monster;

import cursed_chronicles.Player.*;

import javax.swing.*;
import java.awt.*;

public class MonsterPanel extends JFrame {
    private JLabel nameLabel, levelLabel;
    private JTextArea characteristicsArea;
    private JProgressBar lifeBar, defenseBar;

    public MonsterPanel(Monster monster) {
        setTitle("👹 Monster Info");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        nameLabel = new JLabel("Monster: " + monster.getName());
        levelLabel = new JLabel("🔥 Level: " + monster.getLevel());

        characteristicsArea = new JTextArea();
        characteristicsArea.setEditable(false);

        JPanel topPanel = new JPanel(new GridLayout(4, 1));
        topPanel.add(nameLabel);
        topPanel.add(levelLabel);
        topPanel.add(new JLabel("❤️ Life:"));
        lifeBar = new JProgressBar(0, 200);
        lifeBar.setStringPainted(true);
        lifeBar.setForeground(Color.RED);
        topPanel.add(lifeBar);

        topPanel.add(new JLabel("🛡️ Defense:"));
        defenseBar = new JProgressBar(0, 50);
        defenseBar.setStringPainted(true);
        defenseBar.setForeground(Color.BLUE);
        topPanel.add(defenseBar);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(characteristicsArea), BorderLayout.CENTER);

        updateCharacteristics(monster);
    }

    public void updateCharacteristics(Monster monster) {
        for (Characteristic c : monster.getCharacteristics()) {
            if (c.getName().equalsIgnoreCase("life")) {
                lifeBar.setValue(Math.min(c.getValue(), 200)); 
                lifeBar.setString(c.getValue() + "/200");
            } else if (c.getName().equalsIgnoreCase("defense")) {
                defenseBar.setValue(Math.min(c.getValue(), 50));
                defenseBar.setString(c.getValue() + "/50");
            }
        }

        characteristicsArea.setText("⚔️ Other Characteristics:\n");
        for (Characteristic c : monster.getCharacteristics()) {
            if (!c.getName().equalsIgnoreCase("life") && !c.getName().equalsIgnoreCase("defense")) {
                characteristicsArea.append(c.getName() + ": " + c.getValue() + "\n");
            }
        }
    }

    public void showMonsterInfo() {
        setVisible(true);
    }
}
