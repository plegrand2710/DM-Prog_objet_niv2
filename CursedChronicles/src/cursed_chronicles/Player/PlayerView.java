package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PlayerView extends JPanel {
    private Player player;
    private HashMap<String, Image[]> sprites; 
    private int frameIndex = 0; 

    public PlayerView(Player player) {
        this.player = player;
        this.sprites = new HashMap<>();
        loadSprites();
        setPreferredSize(new Dimension(640, 640));
    }

    private void loadSprites() {
        sprites.put("down", new Image[]{
            loadImage("pasDFace1.png"),
            loadImage("pasDFace2.png"),
            loadImage("pasDFace3.png"),
            loadImage("pasMFace.png"),
            loadImage("pasGFace1.png"),
            loadImage("pasGFace2.png"),
            loadImage("pasGFace3.png")
        });
        sprites.put("up", new Image[]{
            loadImage("pasD1.png"),
            loadImage("pasD2.png"),
            loadImage("pasD3.png"),
            loadImage("pasD4.png"),
            loadImage("pasMDos.png"),
            loadImage("pasGDos1.png"),
            loadImage("pasGDos2.png"),
            loadImage("pasGDos3.png")
        });
        sprites.put("right", new Image[]{
            loadImage("pasDDroite1.png"),
            loadImage("pasDDroite2.png"),
            loadImage("pasDDroite3.png"),
            loadImage("pasMDroite.png"),
            loadImage("pasGDroite1.png"),
            loadImage("pasGDroite2.png"),
            loadImage("pasGDroite3.png")
        });
        sprites.put("left", new Image[]{
            loadImage("pasDGauche1.png"),
            loadImage("pasDGauche2.png"),
            loadImage("pasDGauche3.png"),
            loadImage("pasMGauche.png"),
            loadImage("pasGGauche1.png"),
            loadImage("pasGGauche2.png"),
            loadImage("pasGGauche3.png")
        });
    }

    private Image loadImage(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
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
        String direction = player.getDirection();
        Image[] frames = sprites.get(direction);
        Image currentFrame = frames[frameIndex];
        g.drawImage(currentFrame, player.getPositionX() * 64, player.getPositionY() * 64, 64, 64, this);
    }

    public void updateView() {
        Image[] currentAnimation = sprites.get(player.getDirection());
        if (currentAnimation != null && currentAnimation.length > 0) {
            frameIndex = (frameIndex + 1) % currentAnimation.length;
        }
        repaint();
    }
}
