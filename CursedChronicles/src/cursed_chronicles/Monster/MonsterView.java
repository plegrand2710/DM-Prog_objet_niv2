package cursed_chronicles.Monster;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MonsterView extends JPanel {
    private Monster monster;
    private HashMap<String, Image[]> sprites;
    private int frameIndex = 0;
    private final String spritePath = "assets/sprites/monster/";
    private int currentX, currentY;

    public MonsterView(Monster monster) {
        this.monster = monster;
        this.sprites = new HashMap<>();
        loadSprites();
        setPreferredSize(new Dimension(640, 640));
        setOpaque(false);
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
        return icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 48;

        String direction = monster.getDirection();
        Image[] frames = sprites.get(direction);
        if (frames != null && frames.length > 0) {
            g.drawImage(frames[frameIndex], currentX, currentY, cellSize, cellSize, this);
        }
    }

    public void updatePosition(int dx, int dy) {
        currentX += dx;
        currentY += dy;
        repaint();
    }
}
