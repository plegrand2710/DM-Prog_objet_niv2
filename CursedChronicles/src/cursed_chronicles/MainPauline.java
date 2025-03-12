/*package cursed_chronicles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import cursed_chronicles.Map.*;
import cursed_chronicles.Map.Room;
import cursed_chronicles.Map.RoomController;
import cursed_chronicles.Map.RoomView;
import cursed_chronicles.Monster.*;
import cursed_chronicles.Player.*;
import cursed_chronicles.Player.JournalPanel;
import cursed_chronicles.Player.Player;
import cursed_chronicles.Player.PlayerController;
import cursed_chronicles.Player.PlayerPanel;
import cursed_chronicles.Player.PlayerView;
import cursed_chronicles.UI.*;

public class MainPauline {
	
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	            GraphicsDevice gd = ge.getDefaultScreenDevice();
	            GraphicsConfiguration gc = gd.getDefaultConfiguration();
	            Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

	            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	            int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	            int taskbarHeight = screenInsets.bottom; 

	            JFrame gameFrame = new JFrame("Game Window");
	            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            gameFrame.setLayout(new BorderLayout());
	            gameFrame.setLocation(-5,0);
	            
	            RoomView roomView = new RoomView();
	            Board board = new Board(roomView);
	            RoomController roomController = board.getRoomController();
	            Room currentRoom = roomController.getCurrentRoom();

	            Player player = new Player("Hero");
	            PlayerView playerView = new PlayerView(player);
	            player.addJournalEntry("📍 Lieu actuel : " + currentRoom.getName());

	            // 📌 Génération de la narration en fonction des éléments de la salle
	            String narrationText = generateRoomNarration(currentRoom);

	            // 📌 Ajout du panneau de narration
	            NarrationPanel narrationPanel = new NarrationPanel(
	                currentRoom.getName(),
	                narrationText,
	                gameFrame
	            );
	            gameFrame.add(narrationPanel, BorderLayout.SOUTH);
	            
	            gameFrame.add(roomView, BorderLayout.CENTER);
	            gameFrame.pack();
	            int gameFrameWidth = gameFrame.getContentPane().getWidth() + 2;
	            int availableHeight = screenHeight - narrationPanel.getHeight() + taskbarHeight; 

	            InventoryPanel inventoryPanel = new InventoryPanel(player);
	            JournalPanel journalPanel = new JournalPanel(player);
	            PlayerPanel playerPanel = new PlayerPanel(player, inventoryPanel, journalPanel);

	            int panelWidth = screenWidth - gameFrameWidth;
	            int panelHeight = availableHeight / 3;

	            playerPanel.setSize(panelWidth, panelHeight);
	            playerPanel.setLocation(gameFrameWidth, screenHeight - panelHeight - taskbarHeight);
	            playerPanel.setVisible(true);

	            inventoryPanel.setSize(panelWidth, panelHeight);
	            inventoryPanel.setLocation(gameFrameWidth, screenHeight - (2 * panelHeight) - taskbarHeight);
	            inventoryPanel.setVisible(true);

	            journalPanel.setSize(panelWidth, panelHeight-60);
	            journalPanel.setLocation(gameFrameWidth, 0); 
	            journalPanel.setVisible(true);

	            // 📌 Initialisation du contrôleur de joueur
	            PlayerController playerController = new PlayerController(player, playerView);
	            roomController.setPlayerController(playerController);
	            playerController.setRoomController(roomController);

	            // 📌 Chargement de la salle depuis Board
	            roomController.loadRoom();
	            playerController.setSpawn();
	            playerController.setPlayerPosition(7, 14);
	            roomView.add(playerView, Integer.valueOf(2));

	            // 📌 Gestion des entrées clavier pour le jeu
	            gameFrame.addKeyListener(new KeyAdapter() {
	                @Override
	                public void keyPressed(KeyEvent e) {
	                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	                        narrationPanel.skipTypingEffect();
	                    }
	                    if (e.getKeyCode() == KeyEvent.VK_J) {
	                        if (journalPanel.isVisible()) {
	                            journalPanel.setVisible(false);
	                        } else {
	                            journalPanel.setSize(panelWidth + 10, panelHeight + 15);
	                            journalPanel.setLocation(gameFrameWidth - 6, -1); 
	                            journalPanel.setVisible(true);
	                        }
	                    }
	                }
	            });

	            gameFrame.addKeyListener(playerController);
	            gameFrame.setFocusable(true);
	            gameFrame.requestFocusInWindow();
	            gameFrame.setVisible(true);
	        });
	    }

	    private static String generateRoomNarration(Room room) {
	        StringBuilder narration = new StringBuilder();

	        // 📌 Description de base
	        narration.append("Une brise glaciale souffle à travers les fissures des murs.\n");

	        // 📌 Ajout des monstres
	        if (!room.getMonsters().isEmpty()) {
	            narration.append("Des créatures rôdent dans l'ombre : ");
	            for (Monster monster : room.getMonsters()) {
	                narration.append(monster.getName()).append(" ");
	            }
	            narration.append("\n");
	        }

	        // 📌 Ajout des boosters
	        if (!room.getBoosters().isEmpty()) {
	            narration.append("Vous remarquez des objets brillants au sol : ");
	            for (ItemBooster booster : room.getBoosters()) {
	                narration.append(booster.getName()).append(" ");
	            }
	            narration.append("\n");
	        }

	        // 📌 Ajout des coffres
	        if (!room.getChests().isEmpty()) {
	            narration.append("Quelques coffres anciens sont disposés dans la salle...\n");
	        }

	        // 📌 Ajout d'un indice si présent
	        if (room.getHint() != null) {
	            narration.append("Un indice est gravé sur le mur : ").append(room.getHint()).append("\n");
	        }

	        return narration.toString();
	    }
}*/


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
            //gameFrame.add(playerView, BorderLayout.CENTER);
            
            
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
                            newItem = new ItemBooster(filePath, 7, 9);
                        } else {
                            newItem = new ItemWeapon(filePath);
                        }
                        player.getInventory().addItem(newItem);
                        inventoryPanel.updateInventory(player.getInventory().getItems());
                        //inventoryPanel.showInventory();
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
}
