package cursed_chronicles;

import cursed_chronicles.Player.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Définir les chemins pour les assets
        String playerPath = "assets/sprites/player/";
        String boosterPath = "assets/sprites/booster/";
        
        // Tableau de noms bruts
        String[] rawNames = {
            "booster_damage.png",
            "booster_defense_1.png",
            "booster_defense_2.png",
            "booster_defense_3.png",
            "booster_life_1.png",
            "booster_life_2.png",
            "booster_life_3.png",
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
            "bow_sprite.png",
            "hammer_sprite.png",
            "pistol_sprite.png",
            "rifle_sprite.png",
            "sword_sprite.png"
        };
        
        // Construire itemFileNames avec le chemin complet directement
        String[] itemFileNames = new String[rawNames.length];
        for (int i = 0; i < rawNames.length; i++) {
            String fileName = rawNames[i];
            // Si le nom contient "epe", c'est une arme (utiliser playerPath)
            if (fileName.toLowerCase().contains("epe")) {
                itemFileNames[i] = playerPath + fileName;
            } else {
                itemFileNames[i] = boosterPath + fileName;
            }
        }
        
        SwingUtilities.invokeLater(() -> {
            // Création du joueur
            Player player = new Player("Hero");

            // Création des panneaux d'information, inventaire et journal
            InventoryPanel inventoryPanel = new InventoryPanel(player);
            inventoryPanel.showInventory();
            JournalPanel journalPanel = new JournalPanel();
            PlayerPanel playerPanel = new PlayerPanel(player, inventoryPanel, journalPanel);
            playerPanel.setVisible(true);
            
            JFrame gameFrame = new JFrame("Game Window");
            gameFrame.setLayout(new BorderLayout());
            
            // Affichage du joueur (PlayerView)
            PlayerView playerView = new PlayerView(player);
            gameFrame.add(playerView, BorderLayout.CENTER);
            
            // Panel sud : affichage des items disponibles dans le monde
            JPanel itemsPanel = new JPanel();
            itemsPanel.setLayout(new GridLayout(0, 4, 5, 5));
            int scaledWidth = 32;
            int scaledHeight = 32;
            for (String fullPath : itemFileNames) {
                Image img = new ImageIcon(fullPath).getImage();
                if (img.getWidth(null) == -1) {
                    System.out.println("Erreur de chargement de l'image : " + fullPath);
                    continue;
                }
                // Pour l'affichage du monde, on peut scaler l'image ici
                Image scaledImg = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                // Créer l'item à partir du chemin complet
                Item item = new Item(fullPath);
                // Pour l'affichage, on peut utiliser scaledImg dans le label
                JLabel itemLabel = new JLabel(new ImageIcon(scaledImg));
                itemsPanel.add(itemLabel);
            }
            gameFrame.add(new JScrollPane(itemsPanel), BorderLayout.SOUTH);
            
            // Panel nord : contrôles pour modifier les caractéristiques et gérer l'inventaire
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
            
            // Panel de contrôle pour l'inventaire : on utilise seulement les items boosters (ceux qui ne contiennent pas "epe")
            ArrayList<String> invItemFilesList = new ArrayList<>();
            ArrayList<String> invItemNamesList = new ArrayList<>();
            for (String fullPath : itemFileNames) {
                // On exclut les items d'arme (ceux contenant playerPath)
                if (fullPath.toLowerCase().contains(playerPath.toLowerCase()))
                    continue;
                invItemFilesList.add(fullPath);
                // Extraire le nom sans chemin ni extension
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
                        Item newItem = new Item(fileName);
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
