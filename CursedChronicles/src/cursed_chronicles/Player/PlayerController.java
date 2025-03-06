package cursed_chronicles.Player;

import java.awt.event.*;
import javax.swing.Timer;

public class PlayerController extends KeyAdapter {
    private Player player;
    private PlayerView playerView;
    private Timer nonSpeedTimer = null;  
    private boolean canMove = true; 


    public PlayerController(Player player, PlayerView playerView) {
        this.player = player;
        this.playerView = playerView;
        this.playerView.setController(this);

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
            player.move(dir, dx, dy);
            playerView.updateView();
            canMove = false; 
        }
    }
    
    public void notifyAnimationFinished() {
        canMove = true;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (nonSpeedTimer != null) {
            nonSpeedTimer.stop();
            nonSpeedTimer = null;
        }
    }
}
