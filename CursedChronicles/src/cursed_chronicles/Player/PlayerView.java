package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PlayerView extends JPanel {
    private Player player;
    private HashMap<String, Image[]> sprites;  // Stocke les différentes animations
    private int frameIndex = 0;  // Pour animer le joueur

    public PlayerView(Player player) {
        this.player = player;
        this.sprites = new HashMap<>();
        loadSprites();
        setPreferredSize(new Dimension(640, 640));
    }

    private void loadSprites() {
        // Charger les images pour chaque direction
        sprites.put("down", new Image[]{
            new ImageIcon("assets/frame_1.png").getImage(),
            new ImageIcon("assets/frame_2.png").getImage()
        });

        sprites.put("up", new Image[]{
            new ImageIcon("assets/frame_3.png").getImage(),
            new ImageIcon("assets/frame_4.png").getImage()
        });

        sprites.put("left", new Image[]{
            new ImageIcon("assets/frame_5.png").getImage(),
            new ImageIcon("assets/frame_2.png").getImage()
        });

        sprites.put("right", new Image[]{
            new ImageIcon("assets/frame_3.png").getImage(),
            new ImageIcon("assets/frame_4.png").getImage()
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Sélectionner la bonne animation en fonction du mouvement
        String direction = player.getDirection();
        Image[] frames = sprites.get(direction);
        Image currentFrame = frames[frameIndex];

        // Dessiner le joueur
        g.drawImage(currentFrame, player.getPositionX() * 64, player.getPositionY() * 64, 64, 64, this);
    }

    public void updateView() {
        frameIndex = (frameIndex + 1) % 2; // Alterne entre deux frames pour animer
        repaint();
    }
}