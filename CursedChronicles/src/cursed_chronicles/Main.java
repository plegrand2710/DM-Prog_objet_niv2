package cursed_chronicles;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import cursed_chronicles.Map.Board;
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
import cursed_chronicles.UI.Story;
import cursed_chronicles.UI.StoryManager;

public class Main {
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
            
            StoryManager storyManager = new StoryManager();
            storyManager.loadStoriesFromFile("resources/histoire-cursed-chronicles.txt");
            Story selectedStory = getRandomStory(storyManager);
            System.out.println("story " + selectedStory);
            if (selectedStory == null) {
                JOptionPane.showMessageDialog(null, "Aucune histoire sélectionnée. Le jeu va se fermer.", "Erreur", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            
            Player player = new Player("Hero");
            PlayerView playerView = new PlayerView(player);
            
            PlayerController playerController = new PlayerController(player, playerView);

            RoomView roomView = new RoomView(playerController, selectedStory);
            Board board = new Board(roomView);
            RoomController roomController = board.getRoomController();
            Room currentRoom = roomController.getCurrentRoom();

            player.addJournalEntry("📍 Lieu actuel : " + currentRoom.getName());

            NarrationPanel narrationPanel = new NarrationPanel(
                currentRoom,
                gameFrame
            );
            gameFrame.add(narrationPanel, BorderLayout.SOUTH);
            roomView.setNarrationPanel(narrationPanel);
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

            journalPanel.setSize(panelWidth + 12, panelHeight + 10);
            journalPanel.setLocation(gameFrameWidth -8, 0); 
            journalPanel.setVisible(true);

            roomController.setPlayerController(playerController);
            playerController.setRoomController(roomController);

            roomController.loadRoom();
            playerController.setSpawn();
            playerController.setPlayerPosition(7, 8);
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
 private static Story getRandomStory(StoryManager storyManager) {
        ArrayList<Story> stories = storyManager.getStories();
        if (stories.isEmpty()) {
        	System.out.println("vide");
            return null;
        }
        Random random = new Random();
        return stories.get(random.nextInt(stories.size()));
    }
}

