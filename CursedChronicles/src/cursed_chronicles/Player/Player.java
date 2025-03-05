package cursed_chronicles.Player;

import java.util.ArrayList;

public class Player {
    private String name;
    private int positionX;
    private int positionY;
    private int level;
    private int experiencePoints;
    private Inventory inventory;
    private ArrayList<Characteristic> characteristics;
    private Journal journal;

    private String direction;  
    private boolean isMoving; 

    public Player(String name) {
        this.name = name;
        this.positionX = 0;
        this.positionY = 0;
        this.level = 1;
        this.experiencePoints = 0;
        this.inventory = new Inventory();
        this.characteristics = new ArrayList<>();
        this.journal = new Journal();
        this.direction = "down";
        this.isMoving = false;

        initializeDefaultCharacteristics();
    }

    private void initializeDefaultCharacteristics() {
        characteristics.add(new Characteristic("life", 100));
        characteristics.add(new Characteristic("Defense", 0));
        characteristics.add(new Characteristic("speed", 10));
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPositionX() { return positionX; }
    public void setPositionX(int positionX) { this.positionX = positionX; }

    public int getPositionY() { return positionY; }
    public void setPositionY(int positionY) { this.positionY = positionY; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getExperiencePoints() { return experiencePoints; }
    public void setExperiencePoints(int experiencePoints) { this.experiencePoints = experiencePoints; }

    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }

    public ArrayList<Characteristic> getCharacteristics() { return new ArrayList<>(characteristics); }
    public void setCharacteristics(ArrayList<Characteristic> characteristics) { this.characteristics = characteristics; }

    public Journal getJournal() { return journal; }
    public void setJournal(Journal journal) { this.journal = journal; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public boolean isMoving() { return isMoving; }
    public void setMoving(boolean moving) { isMoving = moving; }

    public void move(String direction, int dx, int dy) {
        this.direction = direction;
        this.positionX += dx;
        this.positionY += dy;
        this.isMoving = true;
        journal.addEntry("Moved " + direction + " to (" + positionX + ", " + positionY + ").");
    }

    public void stopMoving() {
        this.isMoving = false;
    }
}