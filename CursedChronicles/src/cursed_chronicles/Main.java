package cursed_chronicles;

import cursed_chronicles.Player.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) {
    	
    	 setupLWJGLLibraryPath();
    	
        String playerPath = "assets/sprites/player/";
        String boosterPath = "assets/sprites/booster/";
        
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
            
            JPanel itemsPanel = new JPanel();
            itemsPanel.setLayout(new GridLayout(0, 4, 5, 5));
            String[] itemFileNames = {
                boosterPath+"booster_damage.png",
                boosterPath+"booster_general_1.png",
                boosterPath+"booster_general_2.png",
                boosterPath+"booster_general_3.png",
                boosterPath+"booster_health_1.png",
                boosterPath+"booster_health_2.png",
                boosterPath+"booster_health_3.png",
                boosterPath+"booster_speed1.png",
                boosterPath+"booster_speed2.png",
                boosterPath+"booster_speed3.png",
                playerPath+"epeActionDos.png",
                playerPath+"epeActionDroite.png",
                playerPath+"epeActionFace.png",
                playerPath+"epeActionGauche.png",
                playerPath+"epeActionViteDos.png",
                playerPath+"epeActionViteDroite.png",
                playerPath+"epeActionViteFace.png",
                playerPath+"epeActionViteGauche.png",
                playerPath+"epeSimpleDDroite.png",
                playerPath+"epeSimpleDFace.png",
                playerPath+"epeSimpleDFace1.png",
                playerPath+"epeSimpleDGauche.png",
                playerPath+"epeSimpleDos.png",
                playerPath+"epeSimpleDroite.png",
                playerPath+"epeSimpleFace.png",
                playerPath+"epeSimpleGauche.png",
                boosterPath+"final_bow_sprite.png",
                boosterPath+"hammer_sprite.png",
                boosterPath+"life_booster.png",
                boosterPath+"life_booster2.png",
                boosterPath+"pistol_sprite.png",
                boosterPath+"rifle_sprite.png",
                boosterPath+"sword_sprite.png"
            };
            int scaledWidth = 32;
            int scaledHeight = 32;
            for (String fileName : itemFileNames) {
                Image img = new ImageIcon(fileName).getImage();
                if (img.getWidth(null) == -1) {
                    System.out.println("Erreur de chargement de l'image : " + fileName);
                    continue;
                }
                Image scaledImg = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                Item item = new Item(fileName, "Description de " + fileName, scaledImg);
                JLabel itemLabel = new JLabel(new ImageIcon(item.getImage()));
                itemsPanel.add(itemLabel);
            }
            gameFrame.add(new JScrollPane(itemsPanel), BorderLayout.SOUTH);
            
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
                    int delta = 0;
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
            for (String fileName : itemFileNames) {
                if (fileName.toLowerCase().contains("epe"))
                    continue;
                invItemFilesList.add(fileName);
                String name = fileName;
                if (name.toLowerCase().endsWith(".png")) {
                    name = name.substring(0, name.length() - 4);
                }
                invItemNamesList.add(name);
            }
            String[] invItemNames = invItemNamesList.toArray(new String[0]);
            String[] invItemFiles = invItemFilesList.toArray(new String[0]);
            
            JPanel inventoryControlPanel = new JPanel(new FlowLayout());
            JComboBox<String> invItemCombo = new JComboBox<>(invItemNames);
            inventoryControlPanel.add(new JLabel("Choisissez un objet : "));
            inventoryControlPanel.add(invItemCombo);
            JButton addInventoryButton = new JButton("Ajouter à l'inventaire");
            addInventoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = invItemCombo.getSelectedIndex();
                    if (index < 0) return;
                    String itemName = invItemNames[index];
                    String fileName = invItemFiles[index];
                    Image itemImg = new ImageIcon(fileName).getImage();
                    if (itemImg.getWidth(null) == -1) {
                        System.out.println("Erreur de chargement de l'image pour " + itemName);
                    } else {
                        Item newItem = new Item(itemName, "Description de " + itemName, itemImg);
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
    
    private static void setupLWJGLLibraryPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String nativePath = "lib/native/";

        if (osName.contains("win")) {
            nativePath += "windows";
        } else if (osName.contains("mac")) {
            nativePath += "macosx";
        } else if (osName.contains("linux")) {
            nativePath += "linux";
        } else {
            System.err.println("OS non supporté !");
            return;
        }

        System.setProperty("org.lwjgl.librarypath", new File(nativePath).getAbsolutePath());
        
        System.out.println("Setup natives for : " + osName + "\n");
    }
}
