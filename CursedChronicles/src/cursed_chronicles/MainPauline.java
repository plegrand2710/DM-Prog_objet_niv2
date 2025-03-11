package cursed_chronicles;

import java.awt.BorderLayout;
import javax.swing.*;

import cursed_chronicles.Map.Room;
import cursed_chronicles.Map.RoomController;
import cursed_chronicles.Map.RoomView;
import cursed_chronicles.Player.Player;
import cursed_chronicles.Player.PlayerController;
import cursed_chronicles.Player.PlayerView;

public class MainPauline {
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            // Création de la fenêtre de jeu (256x256 pixels)
            JFrame gameFrame = new JFrame("Game Window");
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setSize(800, 800);
            gameFrame.setLocationRelativeTo(null);

            // Création de la salle
            RoomView roomView = new RoomView();
            RoomController roomController = new RoomController(roomView, "assets/maps/tiles/");
            Room room = new Room("donjon1_room5");

            // Création du joueur
            Player player = new Player("Hero");
            PlayerView playerView = new PlayerView(player);

            PlayerController playerController = new PlayerController(player, playerView);
            
            // Set other controllers
            roomController.setPlayerController(playerController);
            playerController.setRoomController(roomController);
            
            // Chargement de la salle (IMPORTANT : après avoir initialisé le PlayerController)
            roomController.loadRoom(room);
            
            // Définition des tailles et positions
            roomView.setBounds(0, 0, gameFrame.getWidth(), gameFrame.getHeight());
            playerView.setBounds(143, 140, gameFrame.getWidth(), gameFrame.getHeight());

            // Ajout du joueur dans la salle avec un Z-index spécifique
            roomView.add(playerView, Integer.valueOf(3)); 

            // Ajout du `RoomView` à la fenêtre
            gameFrame.add(roomView, BorderLayout.CENTER);
            
            // Ajout des contrôles du joueur
            gameFrame.addKeyListener(playerController);
            gameFrame.setFocusable(true);
            gameFrame.requestFocusInWindow();

            gameFrame.pack();
            gameFrame.setVisible(true);
        });
    }
}

/*
package cursed_chronicles;

import cursed_chronicles.Player.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainPauline {

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
            JournalPanel journalPanel = new JournalPanel(player);
            journalPanel.showJournal();

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

            player.addPropertyChangeListener(evt -> {
                if ("journalEntry".equals(evt.getPropertyName())) {
                    journalPanel.addEntry((String) evt.getNewValue());
                }
            });

            gameFrame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_J) {
                        if (journalPanel.isVisible()) {
                            journalPanel.setVisible(false);
                        } else {
                            journalPanel.showJournal();
                        }
                    }
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
            
            playerView.movePlayer(1, 0); // Move right
            playerView.movePlayer(0, 1); // Move down

        });
    }
}*/
