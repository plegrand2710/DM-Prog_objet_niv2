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
        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        boosterListModel = new DefaultListModel<>();
        boosterList = new JList<>(boosterListModel);
        boosterList.setCellRenderer(new ItemCellRenderer());
        boosterList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        boosterList.setVisibleRowCount(-1);
        boosterList.setFixedCellWidth(64);
        boosterList.setFixedCellHeight(64);
        JScrollPane boosterScrollPane = new JScrollPane(boosterList);
        boosterScrollPane.setBorder(BorderFactory.createTitledBorder("Boosters"));

        weaponListModel = new DefaultListModel<>();
        weaponList = new JList<>(weaponListModel);
        weaponList.setCellRenderer(new ItemCellRenderer());
        weaponList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        weaponList.setVisibleRowCount(-1);
        weaponList.setFixedCellWidth(64);
        weaponList.setFixedCellHeight(64);
        weaponList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane weaponScrollPane = new JScrollPane(weaponList);
        weaponScrollPane.setBorder(BorderFactory.createTitledBorder("Armes"));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(boosterScrollPane);
        centerPanel.add(weaponScrollPane);
        add(centerPanel, BorderLayout.CENTER);

        JPanel descriptionPanel = new JPanel(new BorderLayout());
        itemDescriptionLabel = new JLabel("");
        descriptionPanel.add(itemDescriptionLabel, BorderLayout.CENTER);
        add(descriptionPanel, BorderLayout.SOUTH);

        updateInventory(player.getInventory().getItems());

        MouseAdapter listMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList<Item> source = (JList<Item>) e.getSource();
                int index = source.locationToIndex(e.getPoint());
                if (index >= 0) {
                    Item selectedItem = source.getModel().getElementAt(index);
                    if (e.getClickCount() == 1) {
                        itemDescriptionLabel.setText(selectedItem.getDescription());
                    } else if (e.getClickCount() == 2) {
                        if (!(selectedItem instanceof ItemWeapon)) {
                            player.useItem(selectedItem);
                            player.getInventory().removeItem(selectedItem);
                            updateInventory(player.getInventory().getItems());
                            itemDescriptionLabel.setText("");
                        }
                    }
                }
            }
        };
        boosterList.addMouseListener(listMouseAdapter);
        weaponList.addMouseListener(listMouseAdapter);

        weaponList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Item selectedWeapon = weaponList.getSelectedValue();
                if (selectedWeapon != null && selectedWeapon instanceof ItemWeapon) {
                    player.setCurrentWeapon((ItemWeapon) selectedWeapon);
                    itemDescriptionLabel.setText("Arme sélectionnée : " + selectedWeapon.getDescription());
                }
            }
        });
        
        this.player.setInventoryPanel(this);
    }

    public void updateInventory(ArrayList<Item> items) {
        boosterListModel.clear();
        String selectedWeaponName = null;
        int selectedIndex = weaponList.getSelectedIndex();
        if (selectedIndex >= 0 && weaponList.getModel().getElementAt(selectedIndex) != null) {
            selectedWeaponName = weaponList.getModel().getElementAt(selectedIndex).getName();
        }

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

        if (selectedWeaponName != null) {
            for (int i = 0; i < weaponListModel.size(); i++) {
                Item weapon = weaponListModel.getElementAt(i);
                if (weapon != null && weapon.getName().equalsIgnoreCase(selectedWeaponName)) {
                    weaponList.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            if (weaponListModel.size() > 0 && weaponListModel.getElementAt(0) != null) {
                weaponList.setSelectedIndex(0);
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
        return item instanceof ItemWeapon;
    }
}

class ItemCellRenderer extends JLabel implements ListCellRenderer<Item> {
    private static final int ICON_WIDTH = 64;
    private static final int ICON_HEIGHT = 64;
    private static final Color WEAPON_SELECTED_BORDER_COLOR = new Color(128, 0, 128); // Violet

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
            setBorder(null);
        } else {
            setText("");
            Image original = item.getImage();
            Image scaledImage = original.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
            Icon icon = new ImageIcon(scaledImage);
            if (item.getQuantity() > 1) {
                icon = new QuantityOverlayIcon(icon, item.getQuantity());
            }
            setIcon(icon);
            if (item instanceof ItemWeapon) {
                if (isSelected) {
                    setBorder(BorderFactory.createLineBorder(WEAPON_SELECTED_BORDER_COLOR, 2));
                } else {
                    setBorder(null);
                }
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            } else {
                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
                setBorder(null);
            }
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
