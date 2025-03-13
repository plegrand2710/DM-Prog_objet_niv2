package cursed_chronicles.Player;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public abstract class Item {
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

        String[] possiblePaths = {
            "assets/sprites/booster/" + this.name + ".png",
            "assets/sprites/player/" + this.name + ".png"
        };

        this.image = null;
        for (String path : possiblePaths) {
            if (new File(path).exists()) {
                this.image = new ImageIcon(path).getImage();
                System.out.println("✅ Image trouvée : " + path);
                break;
            }
        }

        if (this.image == null) {
            System.err.println("⚠ Image non trouvée pour : " + name);
        }
    }



    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    protected void setDescription(String description) {
        this.description = description;
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
        return sb.toString();
    }
}
