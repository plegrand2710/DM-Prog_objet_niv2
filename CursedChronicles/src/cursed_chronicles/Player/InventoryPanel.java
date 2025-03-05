package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InventoryPanel extends JFrame {
    private DefaultListModel<Item> itemListModel;
    private JList<Item> itemList;
    private Player player;
    private JLabel itemDescriptionLabel;

    public InventoryPanel(Player player) {
        this.player = player;
        setTitle("🎒 Inventory");
        setSize(300, 300); 
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        itemListModel = new DefaultListModel<>();
        itemList = new JList<>(itemListModel);
        itemList.setCellRenderer(new ItemCellRenderer());
        
        itemList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        itemList.setVisibleRowCount(-1);
        itemList.setFixedCellWidth(64);
        itemList.setFixedCellHeight(64);

        JScrollPane scrollPane = new JScrollPane(itemList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel descriptionPanel = new JPanel(new BorderLayout());
        itemDescriptionLabel = new JLabel("");
        descriptionPanel.add(itemDescriptionLabel, BorderLayout.CENTER);
        add(descriptionPanel, BorderLayout.SOUTH);

        itemList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = itemList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    Item selectedItem = itemListModel.getElementAt(index);
                    if (e.getClickCount() == 1) {
                        itemDescriptionLabel.setText(selectedItem.getDescription());
                    } else if (e.getClickCount() == 2) {
                        player.useItem(selectedItem);
                        player.getInventory().removeItem(selectedItem);
                        updateInventory(player.getInventory().getItems());
                        itemDescriptionLabel.setText("");
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
    private static final int ICON_WIDTH = 64;
    private static final int ICON_HEIGHT = 64;

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
            setText("");
            Image original = item.getImage();
            Image scaledImage = original.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
            Icon icon = new ImageIcon(scaledImage);
            if (item.getQuantity() > 1) {
                icon = new QuantityOverlayIcon(icon, item.getQuantity());
            }
            setIcon(icon);
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



class QuantityOverlayIcon implements Icon {
    private Icon baseIcon;
    private int quantity;

    public QuantityOverlayIcon(Icon baseIcon, int quantity) {
        this.baseIcon = baseIcon;
        this.quantity = quantity;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        baseIcon.paintIcon(c, g, x, y);
        String text = "x" + quantity;
        Font font = new Font("Arial", Font.BOLD, 12);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(x, y, textWidth, textHeight + 2);
        g.setColor(Color.WHITE);
        g.drawString(text, x, y + textHeight);
    }

    @Override
    public int getIconWidth() {
        return baseIcon.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return baseIcon.getIconHeight();
    }
}

