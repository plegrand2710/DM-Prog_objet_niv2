package cursed_chronicles;

import cursed_chronicles.Player.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String playerPath = "assets/sprites/player/";
        String boosterPath = "assets/sprites/booster/";
        
        FileLoader fileLoader = new FileLoader();
        String[] rawNames = fileLoader.loadFileNames(playerPath, boosterPath);
        
        String[] itemFileNames = new String[rawNames.length];
        for (int i = 0; i < rawNames.length; i++) {
            String fileName = rawNames[i];
            if (fileName.toLowerCase().contains("epe")) {
                itemFileNames[i] = playerPath + fileName;
            } else {
                itemFileNames[i] = boosterPath + fileName;
            }
        }
        
        SwingUtilities.invokeLater(() -> {
            Player player = new Player("Hero");

            InventoryPanel inventoryPanel = new InventoryPanel(player);
            inventoryPanel.showInventory();
            JournalPanel journalPanel = new JournalPanel();
            PlayerPanel playerPanel = new PlayerPanel(player, inventoryPanel, journalPanel);
            playerPanel.setVisible(true);
            
            JFrame gameFrame = new JFrame("Game Window");
            gameFrame.setLayout(new BorderLayout());
            
            PlayerView playerView = new PlayerView(player);
            gameFrame.add(playerView, BorderLayout.CENTER);
            
            
            JPanel controlPanel = new JPanel(new FlowLayout());
            String[] characteristicOptions = {"Life", "Defense", "Speed"};
            JComboBox<String> charCombo = new JComboBox<>(characteristicOptions);
            controlPanel.add(new JLabel("Sélectionnez la caractéristique : "));
            controlPanel.add(charCombo);
            
            JTextField deltaField = new JTextField(5);
            controlPanel.add(new JLabel("Delta : "));
            controlPanel.add(deltaField);
            
            JButton modifyButton = new JButton("Modifier");
            modifyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedChar = (String) charCombo.getSelectedItem();
                    int delta;
                    try {
                        delta = Integer.parseInt(deltaField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(gameFrame, "Entrez un nombre entier valide pour le delta.");
                        return;
                    }
                    player.modifyCharacteristic(selectedChar, delta);
                    playerPanel.updateCharacteristics(player);
                    gameFrame.requestFocusInWindow();
                }
            });
            controlPanel.add(modifyButton);
            
            JButton addDamageButton = new JButton("Ajouter item Damage (+5)");
            addDamageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player.modifyCharacteristic("Damage", 5);
                    playerPanel.updateCharacteristics(player);
                    gameFrame.requestFocusInWindow();
                }
            });
            controlPanel.add(addDamageButton);
            
            JButton activateSpeedButton = new JButton("Activer Speed");
            activateSpeedButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player.activateSpeed();
                    playerPanel.updateCharacteristics(player);
                    gameFrame.requestFocusInWindow();
                }
            });
            controlPanel.add(activateSpeedButton);
            
            ArrayList<String> invItemFilesList = new ArrayList<>();
            ArrayList<String> invItemNamesList = new ArrayList<>();
            for (String fullPath : itemFileNames) {
                if (fullPath.toLowerCase().contains(playerPath.toLowerCase()))
                    continue;
                invItemFilesList.add(fullPath);
                int lastSlash = fullPath.lastIndexOf('/');
                String fileName = (lastSlash != -1) ? fullPath.substring(lastSlash + 1) : fullPath;
                int dotIndex = fileName.lastIndexOf('.');
                String name = (dotIndex != -1) ? fileName.substring(0, dotIndex) : fileName;
                invItemNamesList.add(name);
            }
            String[] invItemNames = invItemNamesList.toArray(new String[0]);
            String[] invItemFiles = invItemFilesList.toArray(new String[0]);
            
            JPanel inventoryControlPanel = new JPanel(new FlowLayout());
            JComboBox<String> invItemCombo = new JComboBox<>(invItemNames);
            inventoryControlPanel.add(new JLabel("Choisissez un booster : "));
            inventoryControlPanel.add(invItemCombo);
            JButton addInventoryButton = new JButton("Ajouter à l'inventaire");
            addInventoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = invItemCombo.getSelectedIndex();
                    if (index < 0) return;
                    String filePath = invItemFiles[index];
                    Image itemImg = new ImageIcon(filePath).getImage();
                    if (itemImg.getWidth(null) == -1) {
                        System.out.println("Erreur de chargement de l'image pour " + invItemNames[index]);
                    } else {
                        Item newItem;
                        int lastSlash = filePath.lastIndexOf('/');
                        String justName = (lastSlash >= 0) ? filePath.substring(lastSlash + 1) : filePath;
                        if (justName.toLowerCase().contains("booster")) {
                            newItem = new ItemBooster(filePath);
                        } else {
                            newItem = new ItemWeapon(filePath);
                        }
                        player.getInventory().addItem(newItem);
                        inventoryPanel.updateInventory(player.getInventory().getItems());
                        inventoryPanel.showInventory();
                    }
                    gameFrame.requestFocusInWindow();
                }
            });

            inventoryControlPanel.add(addInventoryButton);
            controlPanel.add(inventoryControlPanel);
            
            gameFrame.add(controlPanel, BorderLayout.NORTH);
            
            gameFrame.pack();
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
            
            gameFrame.addKeyListener(new PlayerController(player, playerView));

            gameFrame.setFocusable(true);
            gameFrame.requestFocusInWindow();
            
            player.move("right", 1, 0);
            player.move("down", 0, 1);
            playerView.updateView();
        });
    }
}
