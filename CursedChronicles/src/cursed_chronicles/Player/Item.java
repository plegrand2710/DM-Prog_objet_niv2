package cursed_chronicles.Player;

import java.awt.Image;
import java.util.ArrayList;

public class Item {
    private String name;
    private String description;
    private ArrayList<Characteristic> characteristics;
    private Image image;
    private int quantity; 

    public Item(String name, String description, Image image) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.characteristics = new ArrayList<>();
        this.quantity = 1; 
        
        String type = ItemCharacteristicsUtil.getCharacteristicType(name);
        int bonus = ItemCharacteristicsUtil.getBonusFromFilename(name);
        if (!type.isEmpty()) {
            addCharacteristic(new Characteristic(type, bonus));
        }
    }

    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Image getImage() {
        return image;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void incrementQuantity() {
        this.quantity++;
    }
    
    public void decrementQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (quantity > 1) {
            sb.append(" x").append(quantity);
        }
        sb.append(" - ").append(description);
        if (!characteristics.isEmpty()) {
            for (Characteristic c : characteristics) {
                sb.append(c.toString()).append(" ; ");
            }
            int lastIndex = sb.lastIndexOf(" ; ");
            if(lastIndex != -1) {
                sb.delete(lastIndex, sb.length());
            }
        }
        return sb.toString();
    }

}
