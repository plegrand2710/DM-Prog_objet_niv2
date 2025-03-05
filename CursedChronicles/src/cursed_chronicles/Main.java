package cursed_chronicles;

import cursed_chronicles.Player.*;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Player player = new Player("Hero");

            InventoryPanel inventoryPanel = new InventoryPanel();
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
