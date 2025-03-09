package cursed_chronicles;

import cursed_chronicles.Map.Room;
import cursed_chronicles.Map.RoomController;
import cursed_chronicles.Map.RoomView;
import cursed_chronicles.Player.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainPauline {

    public static void main(String[] args) {
SwingUtilities.invokeLater(() -> {
            
            // Création de la fenêtre de jeu
            JFrame gameFrame = new JFrame("Game Window");
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setSize(800, 800);
            gameFrame.setLocationRelativeTo(null);

            // Création du LayeredPane pour gérer l'affichage en couches

            // Création de la salle
            RoomView roomView = new RoomView();
            RoomController controller = new RoomController(roomView, "assets/maps/tiles/");
            Room room = new Room("donjon1_room1");
            controller.loadRoom(room);

            // Création du joueur
            Player player = new Player("Hero");
            PlayerView playerView = new PlayerView(player);
            playerView.setOpaque(false); // Rendre transparent pour ne pas masquer la salle

            // Définition des tailles et positions
            roomView.setBounds(0, 0, gameFrame.getWidth(), gameFrame.getHeight());
            playerView.setBounds(5, 4, gameFrame.getWidth(), gameFrame.getHeight());

            // Ajout des éléments dans le LayeredPane avec un Z-index défini
            roomView.add(playerView, Integer.valueOf(3)); // Joueur entre les murs latéraux et frontaux

            // Ajout du LayeredPane à la fenêtre
            gameFrame.add(roomView, BorderLayout.CENTER);

            // Ajout des contrôles du joueur
            gameFrame.addKeyListener(new PlayerController(player, playerView));
            gameFrame.setFocusable(true);
            gameFrame.requestFocusInWindow();

            gameFrame.pack();
            gameFrame.setVisible(true);
        });
    }
    
}
