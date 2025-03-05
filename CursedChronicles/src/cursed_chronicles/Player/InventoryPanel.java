package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InventoryPanel extends JFrame {
    private DefaultListModel<Item> itemListModel;
    private JList<Item> itemList;
    private Player player;

    public InventoryPanel(Player player) {
        this.player = player;

        setTitle("🎒 Inventory");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        itemListModel = new DefaultListModel<>();
        itemList = new JList<>(itemListModel);
        itemList.setCellRenderer(new ItemCellRenderer());
        add(new JScrollPane(itemList), BorderLayout.CENTER);
        
        itemList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = itemList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Item selectedItem = itemListModel.getElementAt(index);
                        player.useItem(selectedItem);
                        player.getInventory().removeItem(selectedItem);
                        updateInventory(player.getInventory().getItems());
                    }
                }
            }
        });
    }

    public void updateInventory(ArrayList<Item> items) {
        itemListModel.clear();
        if (items.isEmpty()) {
            itemListModel.addElement(null);
        } else {
            for (Item item : items) {
                itemListModel.addElement(item);
            }
        }
    }

    public void showInventory() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - getWidth();
        int y = screenSize.height - getHeight();
        setLocation(x, y);
        setVisible(true);
    }
}

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
            setText(item.toString());
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
