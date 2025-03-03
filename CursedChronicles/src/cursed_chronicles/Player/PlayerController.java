package cursed_chronicles.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerController implements KeyListener {
    private Player player;
    private PlayerView playerView;

    public PlayerController(Player player, PlayerView playerView) {
        this.player = player;
        this.playerView = playerView;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int dx = 0, dy = 0;
        String direction = player.getDirection();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:    direction = "up"; dy = -1; break;
            case KeyEvent.VK_DOWN:  direction = "down"; dy = 1; break;
            case KeyEvent.VK_LEFT:  direction = "left"; dx = -1; break;
            case KeyEvent.VK_RIGHT: direction = "right"; dx = 1; break;
        }

        if (dx != 0 || dy != 0) {
            player.move(direction, dx, dy);
            playerView.updateView(); // Met à jour l'affichage
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.stopMoving(); // Arrête l’animation quand la touche est relâchée
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}