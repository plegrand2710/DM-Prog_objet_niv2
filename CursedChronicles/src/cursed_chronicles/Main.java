package cursed_chronicles;

import cursed_chronicles.Player.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Création du joueur avec ses caractéristiques initiales (Life, Defense, Speed)
            Player player = new Player("Hero");
            
            // Création des panneaux pour l'inventaire, le journal et les infos joueur
            InventoryPanel inventoryPanel = new InventoryPanel();
            inventoryPanel.showInventory();

            JournalPanel journalPanel = new JournalPanel();
            PlayerPanel playerPanel = new PlayerPanel(player, inventoryPanel, journalPanel);
            playerPanel.setVisible(true);
            
            // Fenêtre principale du jeu
            JFrame gameFrame = new JFrame("Game Window");
            gameFrame.setLayout(new BorderLayout());
            
            // Affichage du joueur et de son animation
            PlayerView playerView = new PlayerView(player);
            gameFrame.add(playerView, BorderLayout.CENTER);
            
            // Panel sud : affichage des items (objets dont l'image est agrandie)
            JPanel itemsPanel = new JPanel();
            itemsPanel.setLayout(new GridLayout(0, 4, 5, 5));
            String[] itemFileNames = {
                "booster_damage.png",
                "booster_general_1.png",
                "booster_general_2.png",
                "booster_general_3.png",
                "booster_health_1.png",
                "booster_health_2.png",
                "booster_health_3.png",
                "booster_speed1.png",
                "booster_speed2.png",
                "booster_speed3.png",
                "epeActionDos.png",
                "epeActionDroite.png",
                "epeActionFace.png",
                "epeActionGauche.png",
                "epeActionViteDos.png",
                "epeActionViteDroite.png",
                "epeActionViteFace.png",
                "epeActionViteGauche.png",
                "epeSimpleDDroite.png",
                "epeSimpleDFace.png",
                "epeSimpleDFace1.png",
                "epeSimpleDGauche.png",
                "epeSimpleDos.png",
                "epeSimpleDroite.png",
                "epeSimpleFace.png",
                "epeSimpleGauche.png",
                "final_bow_sprite.png",
                "hammer_sprite.png",
                "life_booster.png",
                "life_booster2.png",
                "pistol_sprite.png",
                "rifle_sprite.png",
                "sword_sprite.png"
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
            
            // Panel nord : contrôles pour modifier les caractéristiques et gérer l'inventaire
            JPanel controlPanel = new JPanel(new FlowLayout());
            
            // Contrôle pour modifier une caractéristique
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
            
            // Contrôle pour choisir l'objet à ajouter dans l'inventaire
            // On définit ici un tableau de noms et un tableau correspondant aux fichiers images
            String[] invItemNames = {"Épée", "Bouclier", "Potion de vie", "Arc", "Pistolet"};
            String[] invItemFiles = {"sword_sprite.png", "hammer_sprite.png", "life_booster.png", "bow_item.png", "pistol_sprite.png"};
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
            
            // Simulation d'actions : déplacement et mise à jour de l'animation
            player.move("right", 1, 0);
            player.move("down", 0, 1);
            playerView.updateView();
        });
    }
}
