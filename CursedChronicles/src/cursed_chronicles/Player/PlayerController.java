package cursed_chronicles.Player;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.Timer;

import cursed_chronicles.Constant;
import cursed_chronicles.Map.Room;
import cursed_chronicles.Map.RoomController;

public class PlayerController extends KeyAdapter {
    private Player player;
    private PlayerView playerView;
    private Timer nonSpeedTimer = null;  
    private boolean canMove = true; 
    private RoomController _roomController;
    
    private int[][] _collisionsLayer;


    public PlayerController(Player player, PlayerView playerView) {
        this.player = player;
        this.playerView = playerView;
        this.playerView.setController(this);

    }
    
    public PlayerView getPlayerView() {
    	return playerView;
    }
    

    public RoomController getRoomController() {
    	return _roomController;
    }
    
    public void setRoomController(RoomController roomController) {
    	_roomController = roomController;
    }
    
    public int[][] getCollisionsLayer() {
        return _collisionsLayer;
    }
    
    public void setCollisionsLayer(int[][] collisionsLayer) {
    	_collisionsLayer = collisionsLayer;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    	if (!canMove) {
            return; 
        }
    	
        int keyCode = e.getKeyCode();
        int dx = 0, dy = 0;
        String dir = player.getDirection();
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("espace appuyé ");

            playerView.updateWeaponSkin();
            return;
        }
        
        switch (keyCode) {
            case KeyEvent.VK_UP:
                dy = -1;
                dir = "up";
                break;
            case KeyEvent.VK_DOWN:
                dy = 1;
                dir = "down";
                break;
            case KeyEvent.VK_LEFT:
                dx = -1;
                dir = "left";
                break;
            case KeyEvent.VK_RIGHT:
                dx = 1;
                dir = "right";
                break;
            default:
                return;
        }
        
        if (!isAllowedToMove(player.getPositionX()+dx, player.getPositionY()+dy)) {
        	return;
        }
        
        if (!playerView.isAnimating()) {
        	playerView.movePlayer(dx, dy);
        	displaySurroundingChestTiles();
            canMove = false; 
            checkForBoosterPickup();

        }
        
//        displayPlayerCurrentPosition();
        displayCurrentCollisionId();
        checkTeleportation();
    }
    
    private void checkTeleportation() {
    	int collisionId = _roomController.getCurrentRoom().getCollisionsLayer()[player.getPositionY()][player.getPositionX()];
    	
    	switch (collisionId) {
    	case Constant.NORTH_DOORS_ID:
    		_roomController.changeRoomFrom("N");
    		break;
    	case Constant.SOUTH_DOORS_ID:
    		_roomController.changeRoomFrom("S");
    		
    		break;
    	case Constant.WEST_DOORS_ID:
    		_roomController.changeRoomFrom("W");
    		break;
    	case Constant.EAST_DOORS_ID:
    		_roomController.changeRoomFrom("E");
    		break;
    	}
    	
    }
    
    private void displayPlayerCurrentPosition() {
    	System.out.println("Player current position : " + player.getPositionX() + "/" + player.getPositionY());
    }
    
    public void displayCurrentCollisionId() {
    	System.out.println("Current collision id : " + _roomController.getCurrentRoom().getCollisionsLayer()[player.getPositionY()][player.getPositionX()]);

    }
    
    public void notifyAnimationFinished() {
        canMove = true;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                playerView.stopAnimation();
                break;
            case KeyEvent.VK_C: 
                if (isNextToChest()) {
                    openChest();
                }
                break;
        }
    }
    
    private int getChestIndexAtPosition(Room room, int x, int y) {
        int[][] chestsLayer = room.getChestsLayer();
        int chestCount = room.getChestCount();

        int index = 0;
        for (int row = 0; row < chestsLayer.length; row++) {
            for (int col = 0; col < chestsLayer[row].length; col++) {
                if (chestsLayer[row][col] == 851) { 
                    if (row == y && col == x) {
                        return index; 
                    }
                    index++; 
                }
            }
        }
        return -1; 
    }
    
    private void openChest() {
        System.out.println("🔎 Détection coffre autour du joueur via le calque `chestsLayer`");

        int x = player.getPositionX();
        int y = player.getPositionY();
        Room currentRoom = _roomController.getCurrentRoom();
        int[][] chestsLayer = currentRoom.getChestsLayer();

        if (chestsLayer == null) {
            System.out.println("⚠ Erreur : Le calque `chestsLayer` est introuvable.");
            return;
        }

        int[][] directions = {
            {x - 1, y}, {x + 1, y},  
            {x, y - 1}, {x, y + 1}, 
            {x - 1, y - 1}, {x + 1, y - 1},  
            {x - 1, y + 1}, {x + 1, y + 1}  
        };

        for (int[] pos : directions) {
            int chestX = pos[0];
            int chestY = pos[1];

            if (chestY >= 0 && chestY < chestsLayer.length && chestX >= 0 && chestX < chestsLayer[0].length) {
                if (chestsLayer[chestY][chestX] == 851) { 

                    int chestIndex = getChestIndexAtPosition(currentRoom, chestX, chestY);
                    if (chestIndex != -1) {
                        ArrayList<Item> chestContents = currentRoom.openChest(chestIndex);

                        if (chestContents != null && !chestContents.isEmpty()) {
                            for (Item item : chestContents) {
                                player.getInventory().addItem(item);
                                player.getInventoryPanel().updateInventory(player.getInventory().getItems());

                            }
                            System.out.println("📦 Coffre ouvert en (" + chestX + ", " + chestY + ") ! Vous obtenez : " + chestContents);
                            return; 
                        } else {
                            System.out.println("📦 Coffre ouvert en (" + chestX + ", " + chestY + ")... mais il est vide !");
                            return;
                        }
                    }
                }
            }
        }

        System.out.println("📦 Aucun coffre détecté à proximité !");
    }
    
    public boolean isAllowedToMove(int newX, int newY) {
    	if (newX < 0 || newX >= 16 || newY < 0 || newY >= 16) {
    		
    		return false;
    	}
    	
    	if (_roomController.isInCollision(newX, newY) ) {
    		return false;
    	}
    	
    	return true;
    }
    
    public void setSpawn() {
    	playerView.setSpawn();
    }
    
    public void setPlayerPosition(int px, int py) {
    	playerView.setPosition(px, py);
    	player.setPosition(px, py);
    	displayPlayerCurrentPosition();
    	
    	playerView.repaint();

    }
    
    private void checkForBoosterPickup() {
        int[] playerPos = { player.getPositionX(), player.getPositionY() };
        ItemBooster booster = _roomController.getCurrentRoom().pickUpBooster(playerPos[0], playerPos[1]);

        if (booster != null) {
            player.getInventory().addItem(booster);
            player.getInventoryPanel().updateInventory(player.getInventory().getItems());
            _roomController.loadRoom();
            System.out.println("🎉 Booster ramassé : " + booster.getName());
        }
    }
    
    private boolean isNextToChest() {
        System.out.println("🔎 Vérification des coffres autour du joueur...");

        int[][] chestsLayer = _roomController.getCurrentRoom().getChestsLayer();
        int x = player.getPositionX();
        int y = player.getPositionY();

        if (chestsLayer == null) {
            System.out.println("⚠ Le calque des coffres (_chestsLayer) est introuvable.");
            return false;
        }

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int checkX = x + dx;
                int checkY = y + dy;

                if (checkX >= 0 && checkX < chestsLayer[0].length && checkY >= 0 && checkY < chestsLayer.length) {
                    if (chestsLayer[checkY][checkX] == 851) {
                        System.out.println("✅ Coffre détecté à proximité en (" + checkX + "," + checkY + ")");
                        return true;
                    }
                }
            }
        }

        System.out.println("❌ Aucun coffre proche.");
        return false;
    }
    
    private void displaySurroundingChestTiles() {
        int[][] chestsLayer = _roomController.getCurrentRoom().getChestsLayer();
        int x = player.getPositionX();
        int y = player.getPositionY();

        if (chestsLayer == null) {
            System.out.println("⚠ Le calque des coffres (_chestsLayer) est introuvable.");
            return;
        }

        System.out.println("📍 Positions des coffres autour du joueur (" + x + "," + y + ") :");
        
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int checkX = x + dx;
                int checkY = y + dy;
                if (checkX >= 0 && checkX < chestsLayer[0].length && checkY >= 0 && checkY < chestsLayer.length) {
                    System.out.print(chestsLayer[checkY][checkX] + "\t");
                } else {
                    System.out.print("X\t"); 
                }
            }
            System.out.println();
        }
    }
    
    public Player getPlayer() {
    	return this.player;
    }
}
