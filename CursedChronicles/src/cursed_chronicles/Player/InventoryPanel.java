package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InventoryPanel extends JFrame {
    private DefaultListModel<Item> itemListModel;
    private JList<Item> itemList;

    public InventoryPanel() {
        setTitle("🎒 Inventory");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        itemListModel = new DefaultListModel<>();
        itemList = new JList<>(itemListModel);
        itemList.setCellRenderer(new ItemCellRenderer());
        add(new JScrollPane(itemList), BorderLayout.CENTER);
    }

    public void updateInventory(ArrayList<Item> items) {
        itemListModel.clear();
        if (items.isEmpty()) {
            // Afficher un élément indiquant que l'inventaire est vide
            itemListModel.addElement(null);
        } else {
            for (Item item : items) {
                itemListModel.addElement(item);
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

// Renderer personnalisé pour afficher un Item avec son image et son texte
class ItemCellRenderer extends JLabel implements ListCellRenderer<Item> {

    public ItemCellRenderer() {
        setOpaque(true);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Item> list, Item item, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (item == null) {
            setText("Inventaire vide");
            setIcon(null);
        } else {
            setText(item.getName() + " - " + item.getDescription());
            setIcon(new ImageIcon(item.getImage()));
        }
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }
}
