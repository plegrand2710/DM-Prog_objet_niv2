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
        // Charger et vérifier le chargement des images depuis le dossier "player"
        sprites.put("down", new Image[]{
            loadImage("frame_1.png"),
            loadImage("frame_2.png")
        });

        sprites.put("up", new Image[]{
            loadImage("frame_3.png"),
            loadImage("frame_4.png")
        });

        sprites.put("left", new Image[]{
            loadImage("frame_5.png"),
            loadImage("frame_2.png")
        });

        sprites.put("right", new Image[]{
            loadImage("frame_3.png"),
            loadImage("frame_4.png")
        });
    }

    private Image loadImage(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        // Vérifier si l'image est chargée (getWidth retourne -1 si l'image n'est pas chargée)
        if (image.getWidth(null) == -1) {
            System.out.println("Erreur de chargement de l'image : " + path);
        } else {
            System.out.println("Image chargée avec succès : " + path);
        }
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Sélectionner la bonne animation en fonction du mouvement
        String direction = player.getDirection();
        Image[] frames = sprites.get(direction);
        Image currentFrame = frames[frameIndex];
        // Dessiner le sprite du joueur
        g.drawImage(currentFrame, player.getPositionX() * 64, player.getPositionY() * 64, 64, 64, this);
    }

    public void updateView() {
        frameIndex = (frameIndex + 1) % 2; // Alterner entre deux frames pour animer
        repaint();
    }
}
