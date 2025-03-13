package cursed_chronicles.Monster;

import cursed_chronicles.Player.Characteristic;
import cursed_chronicles.Map.Room;

import java.util.ArrayList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Monster {
    private String name;
    private int positionX;
    private int positionY;
    private int level;
    private Room room;
    private ArrayList<Characteristic> characteristics;
    private String direction;
    private boolean isMoving;
    private MovementStrategy movementStrategy;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Monster(String name, int positionX, int positionY, int level, Room room) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.level = level;
        this.room = room;
        this.characteristics = new ArrayList<>();
        this.direction = "NONE";
        this.isMoving = false;

        characteristics.add(new Characteristic("life", level == 1 ? 50 : 500)); 
        characteristics.add(new Characteristic("speed", level == 1 ? 2 : 1)); 
        characteristics.add(new Characteristic("damage", level == 1 ? 20 : 50)); 
        if (level == 1) {
            this.movementStrategy = new RandomMovementStrategy();
        } else {
            this.movementStrategy = new ChasePlayerStrategy();
        }
    }

    public void move(int playerX, int playerY) {
        movementStrategy.move(this, playerX, playerY, room);
    }

    public boolean isLevel2() {
        return level == 2;
    }

    public ArrayList<int[]> getOccupiedTiles() {
        ArrayList<int[]> occupiedTiles = new ArrayList<>();
        occupiedTiles.add(new int[]{positionX, positionY});     
        occupiedTiles.add(new int[]{positionX + 1, positionY});  
        occupiedTiles.add(new int[]{positionX, positionY + 1});  
        occupiedTiles.add(new int[]{positionX + 1, positionY + 1}); 
        return occupiedTiles;
    }

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

    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    public Characteristic getCharacteristic(String name) {
        for (Characteristic c : characteristics) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }
    
    public String getName() {
        return name;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getLevel() {
        return level;
    }

    public Room getRoom() {
        return room;
    }

    public boolean isMoving() {
        return isMoving;
    }
}