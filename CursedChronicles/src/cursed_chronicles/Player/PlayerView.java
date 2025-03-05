package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PlayerView extends JPanel {
    private Player player;
    private HashMap<String, Image[]> sprites; 
    private int frameIndex = 0; 
    private String path = "assets/sprites/player/";

    public PlayerView(Player player) {
        this.player = player;
        this.sprites = new HashMap<>();
        loadSprites();
        setPreferredSize(new Dimension(640, 640));
    }

    private void loadSprites() {
        sprites.put("down", new Image[]{
            loadImage(path+"pasDFace1.png"),
            loadImage(path+"pasDFace2.png"),
            loadImage(path+"pasDFace3.png"),
            loadImage(path+"pasMFace.png"),
            loadImage(path+"pasGFace1.png"),
            loadImage(path+"pasGFace2.png"),
            loadImage(path+"pasGFace3.png")
        });
        sprites.put("up", new Image[]{
            loadImage(path+"pasD1.png"),
            loadImage(path+"pasD2.png"),
            loadImage(path+"pasD3.png"),
            loadImage(path+"pasD4.png"),
            loadImage(path+"pasMDos.png"),
            loadImage(path+"pasGDos1.png"),
            loadImage(path+"pasGDos2.png"),
            loadImage(path+"pasGDos3.png")
        });
        sprites.put("right", new Image[]{
            loadImage(path+"pasDDroite1.png"),
            loadImage(path+"pasDDroite2.png"),
            loadImage(path+"pasDDroite3.png"),
            loadImage(path+"pasMDroite.png"),
            loadImage(path+"pasGDroite1.png"),
            loadImage(path+"pasGDroite2.png"),
            loadImage(path+"pasGDroite3.png")
        });
        sprites.put("left", new Image[]{
            loadImage(path+"pasDGauche1.png"),
            loadImage(path+"pasDGauche2.png"),
            loadImage(path+"pasDGauche3.png"),
            loadImage(path+"pasMGauche.png"),
            loadImage(path+"pasGGauche1.png"),
            loadImage(path+"pasGGauche2.png"),
            loadImage(path+"pasGGauche3.png")
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
        
        int cellSize = 64;
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x < getWidth(); x += cellSize) {
            g.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += cellSize) {
            g.drawLine(0, y, getWidth(), y);
        }
        
        String direction = player.getDirection();
        Image[] frames = sprites.get(direction);
        if (frames != null && frames.length > 0) {
            Image currentFrame = frames[frameIndex];
            g.drawImage(currentFrame, player.getPositionX() * cellSize, player.getPositionY() * cellSize, cellSize, cellSize, this);
        }
    }


    public void updateView() {
        Image[] currentAnimation = sprites.get(player.getDirection());
        if (currentAnimation != null && currentAnimation.length > 0) {
            frameIndex = (frameIndex + 1) % currentAnimation.length;
        }
        repaint();
    }
}
