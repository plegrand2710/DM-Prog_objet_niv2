package cursed_chronicles.Player;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Player {
    private String name;
    private int positionX;
    private int positionY;
    private Inventory inventory;
    
    private InventoryPanel inventoryPanel;

    private ArrayList<Characteristic> characteristics;
    private Journal journal;

    private String direction;  
    private boolean isMoving; 
    private boolean speedActive = false;
    
    private ItemWeapon currentWeapon;

    private int lastMoveEntryIndex = -1; 

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private ArrayList<Clue> _cluesJournal = new ArrayList<>();
    
    
    public Player(String name) {
        this.name = name;
        this.positionX = 0;
        this.positionY = 0;        
        this.inventory = new Inventory(this);
        this.characteristics = new ArrayList<>();
        this.journal = new Journal();
        this.direction = "down";
        this.isMoving = false;
        initializeDefaultCharacteristics();
        String swordPath = "assets/sprites/booster/sword_sprite.png";
        currentWeapon = new ItemWeapon(swordPath, -1, -1); 
        this.inventory.addItem(currentWeapon);
    }

    private void initializeDefaultCharacteristics() {
        characteristics.add(new Characteristic("life", 100));
        characteristics.add(new Characteristic("Defense", 0));
        characteristics.add(new Characteristic("speed", 0));
        characteristics.add(new Characteristic("damage", 10));
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public int getCharacteristicValue(String charName) {
        for (Characteristic c : characteristics) {
            if (c.getName().equalsIgnoreCase(charName)) {
                return c.getValue();
            }
        }
        return 0;
    }
    
    public Characteristic getCharacteristic(String name) {
        for (Characteristic c : characteristics) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null; 
    }
    
    public String getName() { return name; }
    public void setName(String name) { 
        String oldName = this.name;
        this.name = name; 
        pcs.firePropertyChange("name", oldName, name);
    }

    public int getPositionX() { return positionX; }
    public void setPositionX(int positionX) { 
        int oldX = this.positionX;
        this.positionX = positionX; 
        pcs.firePropertyChange("positionX", oldX, positionX);
    }

    public int getPositionY() { return positionY; }
    public void setPositionY(int positionY) { 
        int oldY = this.positionY;
        this.positionY = positionY; 
        pcs.firePropertyChange("positionY", oldY, positionY);
    }

    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    
    public InventoryPanel getInventoryPanel() { return inventoryPanel; }
    public void setInventoryPanel(InventoryPanel inventoryPanel) { this.inventoryPanel = inventoryPanel; }

    public ArrayList<Characteristic> getCharacteristics() { return new ArrayList<>(characteristics); }
    public void setCharacteristics(ArrayList<Characteristic> characteristics) { this.characteristics = characteristics; }

    public Journal getJournal() { return journal; }
    public void setJournal(Journal journal) { this.journal = journal; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { 
        String oldDirection = this.direction;
        this.direction = direction; 
        pcs.firePropertyChange("direction", oldDirection, direction);
    }

    public boolean isMoving() { return isMoving; }
    public void setMoving(boolean moving) { 
        boolean oldMoving = this.isMoving;
        this.isMoving = moving; 
        pcs.firePropertyChange("isMoving", oldMoving, moving);
    }

    public void move(String direction, int dx, int dy) {
        setDirection(direction);
        int oldX = positionX;
        int oldY = positionY;
        positionX += dx;
        positionY += dy;
        setMoving(true);
        
        String moveEntry = "Déplacement actuel : " + direction + " (" + positionX + ", " + positionY + ")";

        if (lastMoveEntryIndex == -1) { 
            addJournalEntry(moveEntry);
            lastMoveEntryIndex = journal.getEntries().size() - 1; 
        } else { 
        	journal.updateEntry(lastMoveEntryIndex, moveEntry);
        	pcs.firePropertyChange("journalUpdate", lastMoveEntryIndex, moveEntry);            
        }    
   }


    public void stopMoving() {
        setMoving(false);
    }
    
    public void modifyCharacteristic(String charName, int delta) {
        boolean found = false;
        for (Characteristic c : characteristics) {
            if (c.getName().equalsIgnoreCase(charName)) {
                int currentValue = c.getValue();
                if (charName.equalsIgnoreCase("life") && delta < 0) {
                    int damage = -delta;
                    Characteristic defenseChar = null;
                    for (Characteristic ch : characteristics) {
                        if (ch.getName().equalsIgnoreCase("Defense")) {
                            defenseChar = ch;
                            break;
                        }
                    }
                    if (defenseChar != null && defenseChar.getValue() > 0) {
                        int defenseValue = defenseChar.getValue();
                        if (defenseValue >= damage) {
                            defenseChar.setValue(defenseValue - damage);
                            pcs.firePropertyChange("Defense", defenseValue, defenseChar.getValue());
                            damage = 0;
                        } else {
                            damage -= defenseValue;
                            defenseChar.setValue(0);
                            pcs.firePropertyChange("Defense", defenseValue, 0);
                        }
                        delta = -damage;
                    }
                    if (currentValue + delta < 0) {
                        delta = -currentValue;
                    }
                    int oldLife = currentValue;
                    c.setValue(currentValue + delta);
                    pcs.firePropertyChange("characteristic_life", oldLife, c.getValue());
                    found = true;
                    if (c.getValue() == 0) {
                        JOptionPane.showMessageDialog(null, "Game Over", "Game Over, il faut recommencer la quête !", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);  

                    }
                    break;
                }
                else if (charName.equalsIgnoreCase("Defense") && delta < 0) {
                    int extraDamage = 0;
                    if (currentValue + delta < 0) {
                        extraDamage = -(currentValue + delta);
                        delta = -currentValue;
                    }
                    int oldDef = currentValue;
                    c.setValue(currentValue + delta);
                    pcs.firePropertyChange("characteristic_Defense", oldDef, c.getValue());
                    found = true;
                    if (extraDamage > 0) {
                        modifyCharacteristic("life", -extraDamage);
                    }
                    break;
                }
                else if ((charName.equalsIgnoreCase("speed") || charName.equalsIgnoreCase("damage")) && delta < 0) {
                    if (currentValue + delta < 0) {
                        delta = -currentValue;
                    }
                    int oldVal = currentValue;
                    c.setValue(currentValue + delta);
                    pcs.firePropertyChange("characteristic_" + charName, oldVal, c.getValue());
                    found = true;
                    break;
                }
                else {
                    int oldVal = currentValue;
                    c.setValue(currentValue + delta);
                    pcs.firePropertyChange("characteristic_" + charName, oldVal, c.getValue());
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            if (delta > 0) {
                characteristics.add(new Characteristic(charName, delta));
                pcs.firePropertyChange("characteristic_" + charName, 0, delta);
            } else {
                System.out.println("Caractéristique " + charName + " non trouvée et delta négatif. Aucune création.");
            }
        }
    }

    public void applyBooster(String characteristicName, int bonus) {
        modifyCharacteristic(characteristicName, bonus);
    }
    
    public void activateSpeed() {
        if (!speedActive && getCharacteristicValue("speed") > 0) {
            speedActive = true;
            int delay = 1000; 
            Timer speedTimer = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int currentSpeed = getCharacteristicValue("speed");
                    if (currentSpeed > 0) {
                        modifyCharacteristic("speed", -1);
                    } else {
                        speedActive = false;
                        ((Timer)e.getSource()).stop();
                    }
                }
            });
            speedTimer.start();
        }
    }
    
    public void addJournalEntry(String entry) {
        journal.addEntry(entry);
        pcs.firePropertyChange("journalEntry", null, entry); 
    }
    
    public void useItem(Item item) {
        for (Characteristic booster : item.getCharacteristics()) {
            String type = booster.getName().toLowerCase();
            int bonus = booster.getValue();
            if (type.equals("life")) {
                int currentLife = getCharacteristicValue("life");
                int maxLife = 100;
                int bonusToApply = Math.min(bonus, maxLife - currentLife);
                if (bonusToApply > 0) {
                    modifyCharacteristic("life", bonusToApply);
                }
            } 
            else if (type.equals("defense")) {
                int currentDefense = getCharacteristicValue("defense");
                int maxDefense = 100;
                int bonusToApply = Math.min(bonus, maxDefense - currentDefense);
                if (bonusToApply > 0) {
                    modifyCharacteristic("defense", bonusToApply);
                }
            } 
            else {
                modifyCharacteristic(type, bonus);
            }
        }
    }
    
    public void setCurrentWeapon(ItemWeapon weapon) {
        this.currentWeapon = weapon;
    }

    public ItemWeapon getCurrentWeapon() {
        return currentWeapon;
    }
    
    public boolean isSpeedActive() {
        return speedActive;
    }
    
    public int getWeaponDamage() {
        ItemWeapon weapon = this.getCurrentWeapon();
        if (weapon == null) {
            return 0; 
        }

        switch (weapon.getName().toLowerCase()) {
            case "bow_sprite":
                return 10;
            case "hammer_sprite":
                return 20;
            case "rifler_sprite":
                return 15;
            case "sword_sprite":
            default:
                return 0; 
        }
    }
    
    public void setPosition(int x, int y) {
    	setPositionX(x);
    	setPositionY(y);
    }
    
    public void addClue(Clue clue) {
        if (clue == null) return;
        
        _cluesJournal.add(clue);
        
        StringBuilder cluesEntry = new StringBuilder("📜 **Journal des Indices :**\n");
        for (Clue c : _cluesJournal) {
            cluesEntry.append("- ").append(c.getText()).append("\n");
        }
        
        addJournalEntry(cluesEntry.toString());
    }

    public ArrayList<Clue> getCluesJournal() {
        return _cluesJournal;
    }
}
