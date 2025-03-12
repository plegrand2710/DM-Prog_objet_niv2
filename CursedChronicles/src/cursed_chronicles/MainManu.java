package cursed_chronicles;

import java.awt.BorderLayout;
import javax.swing.*;

import cursed_chronicles.Map.Room;
import cursed_chronicles.Map.RoomController;
import cursed_chronicles.Map.RoomView;
import cursed_chronicles.Player.Player;
import cursed_chronicles.Player.PlayerController;
import cursed_chronicles.Player.PlayerView;

public class MainManu {
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            // Création de la fenêtre de jeu (256x256 pixels)
            JFrame gameFrame = new JFrame("Game Window");
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setSize(Constant.GAME_FRAME_WIDTH, Constant.GAME_FRAME_HEIGHT);
            gameFrame.setLocationRelativeTo(null);

            // Création de la salle
            RoomView roomView = new RoomView();
            RoomController roomController = new RoomController(roomView);
            Room room = new Room("donjon1_room3");

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
            playerController.setSpawn();
            playerController.setPlayerPosition(7, 14);

            // Ajout du joueur dans la salle avec un Z-index spécifique
            roomView.add(playerView, Integer.valueOf(2)); 

            // Ajout du `RoomView` à la fenêtre
            gameFrame.add(roomView);
            
            // Ajout des contrôles du joueur
            gameFrame.addKeyListener(playerController);
            gameFrame.setFocusable(true);
            gameFrame.requestFocusInWindow();

            gameFrame.pack();
            gameFrame.setVisible(true);
        });
    }
}
