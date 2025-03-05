package cursed_chronicles.Player;

import java.util.ArrayList;
import java.awt.Image;

public class Item {
    private String name;
    private String description;
    private ArrayList<Characteristic> characteristics;
    private Image image; 

    public Item(String name, String description, Image image) {
        this.name = name;
        this.description = description;
        this.characteristics = new ArrayList<>();
        this.image = image;
    }
    
    public Item(String name, String description) {
        this(name, description, null);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Characteristic> getCharacteristics() {
        return new ArrayList<>(characteristics);
    }

    public void addCharacteristic(Characteristic characteristic) {
        characteristics.add(characteristic);
    }

    public void removeCharacteristic(String characteristicName) {
        characteristics.removeIf(c -> c.getName().equalsIgnoreCase(characteristicName));
    }
    
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + " - " + description + "\nCharacteristics:\n");
        for (Characteristic c : characteristics) {
            sb.append("- ").append(c).append("\n");
        }
        return sb.toString();
    }
}
