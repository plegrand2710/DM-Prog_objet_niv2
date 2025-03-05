package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InventoryPanel extends JFrame {
    private DefaultListModel<String> itemListModel;
    private JList<String> itemList;

    public InventoryPanel() {
        setTitle("🎒 Inventory");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        itemListModel = new DefaultListModel<>();
        itemList = new JList<>(itemListModel);
        add(new JScrollPane(itemList), BorderLayout.CENTER);
    }

    public void updateInventory(ArrayList<Item> items) {
        itemListModel.clear();
        if (items.isEmpty()) {
            itemListModel.addElement("Inventaire vide");
        } else {
            for (Item item : items) {
                itemListModel.addElement(item.getName() + " - " + item.getDescription());
            }
        }
    }


    public void showInventory() {
        // Positionne la fenêtre en bas à droite de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - getWidth();
        int y = screenSize.height - getHeight();
        setLocation(x, y);
        setVisible(true);
    }
}