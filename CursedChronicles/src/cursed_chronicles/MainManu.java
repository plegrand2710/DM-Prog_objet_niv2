package cursed_chronicles;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import cursed_chronicles.Map.Room;
import cursed_chronicles.Map.RoomController;
import cursed_chronicles.Map.RoomView;

public class MainManu {
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("donjon1_room1");
            RoomView roomView = new RoomView();
            RoomController controller = new RoomController(roomView, "assets/maps/tiles/");

            // Création d'une salle qui sera chargée automatiquement
            Room room = new Room("donjon1_room1");

            // Charger et afficher la salle
            controller.loadRoom(room);

            frame.add(roomView);
            frame.pack(); // Ajuste automatiquement la taille de la fenêtre
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
