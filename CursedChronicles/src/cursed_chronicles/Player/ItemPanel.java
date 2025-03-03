package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;

public class ItemPanel extends JFrame {
    private JLabel titleLabel;
    private JTextArea characteristicsArea;

    public ItemPanel(Item item) {
        setTitle("Item Details");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        titleLabel = new JLabel("🔹 " + item.getName());
        characteristicsArea = new JTextArea();
        characteristicsArea.setEditable(false);

        for (Characteristic c : item.getCharacteristics()) {
            characteristicsArea.append(c.getName() + ": " + c.getValue() + "\n");
        }

        add(titleLabel, BorderLayout.NORTH);
        add(new JScrollPane(characteristicsArea), BorderLayout.CENTER);
    }
}