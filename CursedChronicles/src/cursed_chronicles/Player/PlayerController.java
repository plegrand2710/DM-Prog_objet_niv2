package cursed_chronicles.Player;

import java.awt.event.*;
import javax.swing.Timer;

public class PlayerController extends KeyAdapter {
    private Player player;
    private PlayerView playerView;
    private Timer nonSpeedTimer = null;  

    public PlayerController(Player player, PlayerView playerView) {
        this.player = player;
        this.playerView = playerView;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int dx = 0, dy = 0;
        String dir = player.getDirection();
        
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
        
        final int baseDx = dx;
        final int baseDy = dy;
        final String directionFinal = dir;
        
        if (player.isSpeedActive()) {
            if (nonSpeedTimer != null) {
                nonSpeedTimer.stop();
                nonSpeedTimer = null;
            }
            player.move(directionFinal, baseDx, baseDy);
            playerView.updateView();
        } else {
            if (nonSpeedTimer == null) {
                nonSpeedTimer = new Timer(300, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        player.move(directionFinal, baseDx, baseDy);
                        playerView.updateView();
                    }
                });
                nonSpeedTimer.setInitialDelay(0); 
                nonSpeedTimer.start();
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (nonSpeedTimer != null) {
            nonSpeedTimer.stop();
            nonSpeedTimer = null;
        }
    }
}
