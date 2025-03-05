package cursed_chronicles;

import cursed_chronicles.Player.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Lancer l'interface graphique dans le thread de l'Event Dispatching
        SwingUtilities.invokeLater(() -> {
            // Création du joueur
            Player player = new Player("Hero");

            // Création des panneaux pour l'inventaire et le journal
            InventoryPanel inventoryPanel = new InventoryPanel();
            JournalPanel journalPanel = new JournalPanel();

            // Création du panneau d'information du joueur et affichage
            PlayerPanel playerPanel = new PlayerPanel(player, inventoryPanel, journalPanel);
            playerPanel.setVisible(true);

            // Création de la fenêtre principale pour la vue du joueur (le "game window")
            JFrame gameFrame = new JFrame("Game Window");
            PlayerView playerView = new PlayerView(player);
            gameFrame.add(playerView);
            gameFrame.pack();
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);

            // Ajout du contrôleur de joueur pour capter les touches et mettre à jour la vue
            gameFrame.addKeyListener(new PlayerController(player, playerView));

            // Simulation d'actions pour tester le déplacement et la mise à jour du journal
            player.move("right", 1, 0);
            player.move("down", 0, 1);
            playerView.updateView();
        });
    }
}
