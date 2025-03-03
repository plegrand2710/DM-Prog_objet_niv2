package cursed_chronicles.Player;
import java.util.ArrayList;

public class Item {
    private String name;
    private String description;
    private ArrayList<Characteristic> characteristics;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        this.characteristics = new ArrayList<>();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + " - " + description + "\nCharacteristics:\n");
        for (Characteristic c : characteristics) {
            sb.append("- ").append(c).append("\n");
        }
        return sb.toString();
    }
}