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

    // 🎮 Gestion des déplacements et des images
    private String direction;  // "up", "down", "left", "right"
    private boolean isMoving;  // Indique si le joueur est en mouvement

    public Player(String name) {
        this.name = name;
        this.positionX = 0;
        this.positionY = 0;
        this.level = 1;
        this.experiencePoints = 0;
        this.inventory = new Inventory();
        this.characteristics = new ArrayList<>();
        this.journal = new Journal();
        this.direction = "down"; // Direction par défaut
        this.isMoving = false;

        // 🎯 Initialisation des caractéristiques de base
        initializeDefaultCharacteristics();
    }

    private void initializeDefaultCharacteristics() {
        characteristics.add(new Characteristic("Strength", 5));
        characteristics.add(new Characteristic("Defense", 5));
        characteristics.add(new Characteristic("Agility", 5));
        characteristics.add(new Characteristic("Intelligence", 5));
    }

    // ✅ Getters & Setters
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

    // 🎮 Déplacement du joueur avec gestion des images
    public void move(String direction, int dx, int dy) {
        this.direction = direction;
        this.positionX += dx;
        this.positionY += dy;
        this.isMoving = true;
        journal.addEntry("Moved " + direction + " to (" + positionX + ", " + positionY + ").");
    }

    // ⏹️ Arrêter le mouvement du joueur (arrêt de l'animation)
    public void stopMoving() {
        this.isMoving = false;
    }
}