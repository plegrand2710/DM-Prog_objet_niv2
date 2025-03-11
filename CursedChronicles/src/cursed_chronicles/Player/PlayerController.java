package cursed_chronicles.Player;

import java.awt.event.*;
import javax.swing.Timer;

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
        
        if (!playerView.isAnimating()) {
        	playerView.movePlayer(dx, dy);

            canMove = false; 
        }
        
        displayPlayerCurrentPosition();
    }
    
    private void displayPlayerCurrentPosition() {
    	System.out.println("Player current position : " + player.getPositionX() + "/" + player.getPositionY());
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
                playerView.stopAnimation(); // Arrête l’animation quand la touche est relâchée
                break;
        }
    }
    
    public void setPlayerPosition(int px, int py) {
    	playerView.setPosition(px, py);
    	player.setPosition(px, py);
    	displayPlayerCurrentPosition();
    }
}
