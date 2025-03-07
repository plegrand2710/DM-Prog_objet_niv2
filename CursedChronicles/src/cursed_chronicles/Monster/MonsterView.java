package cursed_chronicles.Monster;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MonsterView extends JPanel {
    private Monster monster;
    private HashMap<String, Image[]> sprites;
    private int frameIndex = 0;
    private final String spritePath = "assets/sprites/monster/";

    public MonsterView(Monster monster) {
        this.monster = monster;
        this.sprites = new HashMap<>();
        loadSprites();
        setPreferredSize(new Dimension(640, 640));
    }

    private void loadSprites() {
        String name = monster.getName().toLowerCase();
        sprites.put("down", new Image[]{ loadImage(spritePath + name + "_down.png") });
        sprites.put("up", new Image[]{ loadImage(spritePath + name + "_up.png") });
        sprites.put("left", new Image[]{ loadImage(spritePath + name + "_left.png") });
        sprites.put("right", new Image[]{ loadImage(spritePath + name + "_right.png") });
    }

    private Image loadImage(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        if (image.getWidth(null) == -1) {
            System.out.println("Erreur de chargement de l'image : " + path);
        }
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 64;

        String direction = monster.getDirection();
        Image[] frames = sprites.get(direction);
        if (frames != null && frames.length > 0 && frameIndex < frames.length) {
            g.drawImage(frames[frameIndex], monster.getPositionX() * cellSize, monster.getPositionY() * cellSize, cellSize, cellSize, this);
        }
    }

    public void updateView() {
        repaint();
    }
}
