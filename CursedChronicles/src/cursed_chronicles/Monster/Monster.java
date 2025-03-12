package cursed_chronicles.Monster;

import cursed_chronicles.Player.Characteristic;
import java.util.ArrayList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Monster {
    private String name;
    private int positionX;
    private int positionY;
    private int level;
    private ArrayList<Characteristic> characteristics;
    private String direction;
    private boolean isMoving;
    private MovementStrategy movementStrategy;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Monster(String name, int positionX, int positionY, int level) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.level = level;
        this.characteristics = new ArrayList<>();
        this.direction = "NONE";
        this.isMoving = false;

        // Ajouter les caractéristiques de base
        characteristics.add(new Characteristic("life", level == 1 ? 50 : 200));
        characteristics.add(new Characteristic("defense", level == 1 ? 5 : 20));
        characteristics.add(new Characteristic("speed", level == 1 ? 2 : 4));

        // Déterminer la stratégie de mouvement
        if (level == 1) {
            this.movementStrategy = new RandomMovementStrategy();
        } else {
            this.movementStrategy = new ChasePlayerStrategy();
        }
    }

    public void move(int playerX, int playerY) {
        movementStrategy.move(this, playerX, playerY);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    // Getters et Setters
    public String getName() { return name; }
    public int getPositionX() { return positionX; }
    public int getPositionY() { return positionY; }
    public int getLevel() { return level; }
    public ArrayList<Characteristic> getCharacteristics() { return characteristics; }
    public String getDirection() { return direction; }
    public boolean isMoving() { return isMoving; }
    public void setPositionX(int positionX) { 
        int oldX = this.positionX;
        this.positionX = positionX; 
        pcs.firePropertyChange("positionX", oldX, positionX);
    }
    public void setPositionY(int positionY) { 
        int oldY = this.positionY;
        this.positionY = positionY; 
        pcs.firePropertyChange("positionY", oldY, positionY);
    }
    public void setDirection(String direction) { 
        String oldDirection = this.direction;
        this.direction = direction; 
        pcs.firePropertyChange("direction", oldDirection, direction);
    }
    public void setMoving(boolean moving) { 
        boolean oldMoving = this.isMoving;
        this.isMoving = moving; 
        pcs.firePropertyChange("isMoving", oldMoving, moving);
    }
}
