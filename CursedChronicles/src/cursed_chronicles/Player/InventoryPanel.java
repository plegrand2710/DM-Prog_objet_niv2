package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InventoryPanel extends JFrame {
    private DefaultListModel<Item> boosterListModel;
    private JList<Item> boosterList;
    private DefaultListModel<Item> weaponListModel;
    private JList<Item> weaponList;
    private Player player;
    private JLabel itemDescriptionLabel;

    public InventoryPanel(Player player) {
        this.player = player;
        setTitle("🎒 Inventory");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Création de la liste pour les boosters
        boosterListModel = new DefaultListModel<>();
        boosterList = new JList<>(boosterListModel);
        boosterList.setCellRenderer(new ItemCellRenderer());
        boosterList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        boosterList.setVisibleRowCount(-1);
        boosterList.setFixedCellWidth(64);
        boosterList.setFixedCellHeight(64);
        JScrollPane boosterScrollPane = new JScrollPane(boosterList);
        boosterScrollPane.setBorder(BorderFactory.createTitledBorder("Boosters"));

        // Création de la liste pour les armes
        weaponListModel = new DefaultListModel<>();
        weaponList = new JList<>(weaponListModel);
        weaponList.setCellRenderer(new ItemCellRenderer());
        weaponList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        weaponList.setVisibleRowCount(-1);
        weaponList.setFixedCellWidth(64);
        weaponList.setFixedCellHeight(64);
        JScrollPane weaponScrollPane = new JScrollPane(weaponList);
        weaponScrollPane.setBorder(BorderFactory.createTitledBorder("Armes"));

        // Panel central : affichage vertical des deux listes
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(boosterScrollPane);
        centerPanel.add(weaponScrollPane);
        add(centerPanel, BorderLayout.CENTER);

        // Panel bas : affichage de la description
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        itemDescriptionLabel = new JLabel("");
        descriptionPanel.add(itemDescriptionLabel, BorderLayout.CENTER);
        add(descriptionPanel, BorderLayout.SOUTH);

        // Listener de souris commun aux deux listes
        MouseAdapter listMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList<Item> sourceList = (JList<Item>) e.getSource();
                int index = sourceList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    Item selectedItem = sourceList.getModel().getElementAt(index);
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
        };
        boosterList.addMouseListener(listMouseAdapter);
        weaponList.addMouseListener(listMouseAdapter);
    }

    public void updateInventory(ArrayList<Item> items) {
        boosterListModel.clear();
        weaponListModel.clear();
        if (items.isEmpty()) {
            boosterListModel.addElement(null);
            weaponListModel.addElement(null);
        } else {
            for (Item item : items) {
                if (isWeapon(item)) {
                    weaponListModel.addElement(item);
                } else {
                    boosterListModel.addElement(item);
                }
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

    private boolean isWeapon(Item item) {
        String name = item.getName().toLowerCase();
        return name.contains("sword") || name.contains("pistol") || name.contains("rifle")
                || name.contains("bow") || name.contains("hammer") || name.contains("epe");
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
            // Pour tous les items, afficher uniquement l'image
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
