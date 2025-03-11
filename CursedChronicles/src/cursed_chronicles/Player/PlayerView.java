package cursed_chronicles.Player;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class PlayerView extends JPanel {
	private static int CONSTX = 48;
	private static int CONSTY = 48;
	
	private static int GAMEFRAMEWIDTH = 256;
	private static int GAMEFRAMEHEIGHT = 256;

    private Player player;
    private PlayerController controller;
    private HashMap<String, Image[]> sprites;
    private int frameIndex = 0;
    private String path = "assets/sprites/player/";
    private Image weaponSkin;
    private Timer movementTimer;
    
    private Timer animationTimer;  // Ajout d'un timer global pour l'animation

    private final int baseMoveSpeed = 250;
    private final int speedMoveSpeed = 50; 
    private int moveSpeed = baseMoveSpeed; 

    private boolean isAnimating = false;
    private int targetX, targetY;
    private int currentX, currentY;
    private int stepSize = 8; // Nombre de pixels par mise à jour d'animation
    
    

    public PlayerView(Player player) {
        this.player = player;
        this.sprites = new HashMap<>();
        loadSprites();
        setPreferredSize(new Dimension(640, 640));
        animationTimer = new Timer(moveSpeed/4, e -> {
            frameIndex = (frameIndex + 1) % sprites.get(player.getDirection()).length;
            repaint();
        });
        setOpaque(false); // Rendre transparent pour ne pas masquer la salle
    }

    public void setController(PlayerController controller) {
        this.controller = controller;
    }

    private void loadSprites() {
        sprites.put("down", new Image[]{
            loadImage(path + "pasDFace1.png"),
            loadImage(path + "pasDFace2.png"),
            loadImage(path + "pasDFace3.png"),
            loadImage(path + "pasMFace.png"),
            loadImage(path + "pasGFace1.png"),
            loadImage(path + "pasGFace2.png"),
            loadImage(path + "pasGFace3.png")
        });
        sprites.put("up", new Image[]{
            loadImage(path + "pasD1.png"),
            loadImage(path + "pasD2.png"),
            loadImage(path + "pasD3.png"),
            loadImage(path + "pasD4.png"),
            loadImage(path + "pasMDos.png"),
            loadImage(path + "pasGDos1.png"),
            loadImage(path + "pasGDos2.png"),
            loadImage(path + "pasGDos3.png")
        });
        sprites.put("right", new Image[]{
            loadImage(path + "pasDDroite1.png"),
            loadImage(path + "pasDDroite2.png"),
            loadImage(path + "pasDDroite3.png"),
            loadImage(path + "pasMDroite.png"),
            loadImage(path + "pasGDroite1.png"),
            loadImage(path + "pasGDroite2.png"),
            loadImage(path + "pasGDroite3.png")
        });
        sprites.put("left", new Image[]{
            loadImage(path + "pasDGauche1.png"),
            loadImage(path + "pasDGauche2.png"),
            loadImage(path + "pasDGauche3.png"),
            loadImage(path + "pasMGauche.png"),
            loadImage(path + "pasGGauche1.png"),
            loadImage(path + "pasGGauche2.png"),
            loadImage(path + "pasGGauche3.png")
        });
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
        int cellSize = 48;

        if (weaponSkin != null) {
            g.drawImage(weaponSkin, currentX, currentY, cellSize, cellSize, this);
        } else {
            String direction = player.getDirection();
            Image[] frames = sprites.get(direction);
            if (frames != null && frames.length > 0 && frameIndex < frames.length) {
                g.drawImage(frames[frameIndex], currentX, currentY, cellSize, cellSize, this);
            }
        }
    }

    public void movePlayer(int dx, int dy) {
        if (isAnimating) return;

        moveSpeed = player.isSpeedActive() ? speedMoveSpeed : baseMoveSpeed;

        currentX = player.getPositionX() * CONSTX;
        currentY = player.getPositionY() * CONSTY;
        targetX = currentX + dx * CONSTX;
        targetY = currentY + dy * CONSTY;

        String newDirection = determineDirection(dx, dy);
        player.setDirection(newDirection);

        player.move(newDirection, dx, dy);
        isAnimating = true;
        if (!animationTimer.isRunning()) {
            animationTimer.start();  // Démarre l'animation si ce n'est pas déjà fait
        }

        movementTimer = new Timer(moveSpeed / 10, e -> {
            if (Math.abs(targetX - currentX) <= stepSize && Math.abs(targetY - currentY) <= stepSize) {
                currentX = targetX;
                currentY = targetY;
                ((Timer) e.getSource()).stop();
                isAnimating = false;
                if (controller != null) {
                    controller.notifyAnimationFinished();
                }
            } else {
                currentX += (targetX > currentX) ? stepSize : (targetX < currentX) ? -stepSize : 0;
                currentY += (targetY > currentY) ? stepSize : (targetY < currentY) ? -stepSize : 0;
            }
            repaint();
        });

        movementTimer.start();
    }

    private String determineDirection(int dx, int dy) {
        if (dx > 0) return "right";
        if (dx < 0) return "left";
        if (dy > 0) return "down";
        if (dy < 0) return "up";
        return player.getDirection(); 
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public void stopAnimation() {
        animationTimer.stop();
        frameIndex = 0; // Réinitialise le sprite
        repaint();
    }

    public void setPlayerImage(Image image) {
        this.weaponSkin = image;
    }

    public void resetPlayerImage() {
        this.weaponSkin = null;
    }

    public void updateWeaponSkin() {
        ItemWeapon weapon = player.getCurrentWeapon();
        if (weapon != null) {
            String weaponName = extractWeaponName(weapon.getName());
            final String[] swingFrames;
            String direction = player.getDirection();
            
            if ("right".equals(direction)) {
                swingFrames = new String[] {
                    path + weaponName + "SimpleDroite.png",
                    path + weaponName + "ActionDroite.png",
                    path + weaponName + "ActionViteDroite.png"
                };
            } else if ("left".equals(direction)) {
                swingFrames = new String[] {
                    path + weaponName + "SimpleGauche.png",
                    path + weaponName + "ActionGauche.png",
                    path + weaponName + "ActionViteGauche.png"
                };
            } else if ("up".equals(direction)) {
                swingFrames = new String[] {
                    path + weaponName + "SimpleDos.png",
                    path + weaponName + "ActionDos.png",
                    path + weaponName + "ActionViteDos.png"
                };
            } else if ("down".equals(direction)) {
                swingFrames = new String[] {
                    path + weaponName + "SimpleFace.png",
                    path + weaponName + "ActionFace.png",
                    path + weaponName + "ActionViteFace.png"
                };
            } else {
                swingFrames = new String[0];
            }
            
            Timer timer = new Timer(200, new ActionListener() {
                int frame = 0;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (frame < swingFrames.length) {
                        setPlayerImage(new ImageIcon(swingFrames[frame]).getImage());
                        frame++;
                    } else {
                        ((Timer) e.getSource()).stop();
                        resetPlayerImage();
                    }
                    repaint();
                }
            });
            timer.start();
        }
    }

    private String extractWeaponName(String filePath) {
        if (filePath == null || !filePath.contains("_")) {
            return filePath;
        }
        return filePath.substring(0, filePath.indexOf("_"));
    }
    
    public void setSpawnPosition(int px, int py) {
    	setBounds(180, 135, GAMEFRAMEWIDTH, GAMEFRAMEHEIGHT);
    }

    
}
