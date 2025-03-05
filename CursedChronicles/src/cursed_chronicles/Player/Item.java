package cursed_chronicles.Player;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Item {
    private String name;
    private String description;
    private ArrayList<Characteristic> characteristics;
    private Image image;
    private int quantity; 

    public Item(String filePath) {
        this.quantity = 1;
        this.characteristics = new ArrayList<>();
        
        int lastSlash = filePath.lastIndexOf('/');
        String fileName = (lastSlash >= 0) ? filePath.substring(lastSlash + 1) : filePath;
        int dotIndex = fileName.lastIndexOf('.');
        this.name = (dotIndex > 0) ? fileName.substring(0, dotIndex) : fileName;
        
        this.image = new ImageIcon(filePath).getImage();
        
        String type = ItemCharacteristicsUtil.getCharacteristicType(fileName);
        int bonus = ItemCharacteristicsUtil.getBonusFromFilename(fileName);
        
        if (!type.isEmpty()) {
            this.description = type + " : " + bonus;
            addCharacteristic(new Characteristic(type, bonus));
        } else {
            this.description = this.name;
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
        /*if (!characteristics.isEmpty()) {
            sb.append(" [");
            for (Characteristic c : characteristics) {
                sb.append(c.toString()).append(", ");
            }
            int lastIndex = sb.lastIndexOf(", ");
            if (lastIndex != -1) {
                sb.delete(lastIndex, sb.length());
            }
            sb.append("]");
        }*/
        return sb.toString();
    }
}
