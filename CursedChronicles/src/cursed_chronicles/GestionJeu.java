package cursed_chronicles;

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

import cursed_chronicles.Map.Room;
import cursed_chronicles.Map.RoomController;
import cursed_chronicles.Map.RoomView;
import cursed_chronicles.Player.InventoryPanel;
import cursed_chronicles.Player.JournalPanel;
import cursed_chronicles.Player.Player;
import cursed_chronicles.Player.PlayerController;
import cursed_chronicles.Player.PlayerPanel;
import cursed_chronicles.Player.PlayerView;
import cursed_chronicles.UI.NarrationPanel;

public class GestionJeu {
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
            RoomController roomController = new RoomController(roomView);
            Room room = new Room("donjon1_room5");
            
            NarrationPanel narrationPanel = new NarrationPanel(
                room.getName(),
                "Une brise glaciale souffle à travers les fissures des murs. "
                        + "Chaque pas résonne étrangement, comme si la pierre elle-même murmurait. "
                        + "Un frisson parcourt ton échine. Quelque chose rôde dans l'ombre...",
                gameFrame
            );
            gameFrame.add(narrationPanel, BorderLayout.SOUTH);
            

            gameFrame.add(roomView, BorderLayout.CENTER);

            gameFrame.pack();
            int gameFrameWidth = gameFrame.getContentPane().getWidth() + 2;
            int gameFrameHeight = gameFrame.getContentPane().getHeight();
            int narrationHeight = narrationPanel.getHeight();
            int availableHeight = screenHeight - narrationHeight + taskbarHeight; 

            Player player = new Player("Hero");
            PlayerView playerView = new PlayerView(player);

            int panelWidth = screenWidth - gameFrameWidth;
            int panelHeight = availableHeight / 3;

            InventoryPanel inventoryPanel = new InventoryPanel(player);
            JournalPanel journalPanel = new JournalPanel(player);
            PlayerPanel playerPanel = new PlayerPanel(player, inventoryPanel, journalPanel);

            playerPanel.setSize(panelWidth, panelHeight);
            playerPanel.setLocation(gameFrameWidth, screenHeight - panelHeight - taskbarHeight);
            playerPanel.setVisible(true);

            inventoryPanel.setSize(panelWidth, panelHeight);
            inventoryPanel.setLocation(gameFrameWidth, screenHeight - (2 * panelHeight) - taskbarHeight);
            inventoryPanel.setVisible(true);

            journalPanel.setSize(panelWidth, panelHeight-60);
            journalPanel.setLocation(gameFrameWidth , 0); 
            journalPanel.setVisible(true);

            PlayerController playerController = new PlayerController(player, playerView);
            roomController.setPlayerController(playerController);
            playerController.setRoomController(roomController);

            roomController.loadRoom(room);
            playerController.setSpawn();
            playerController.setPlayerPosition(7, 14);
            roomView.add(playerView, Integer.valueOf(2));

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
                            journalPanel.setLocation(gameFrameWidth -6, - 1); 
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
}
