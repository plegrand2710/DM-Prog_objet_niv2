package cursed_chronicles.Map;
import cursed_chronicles.Player.Item;
import java.util.ArrayList;

public class Chest {
    private ArrayList<Item> _contents; 

    public Chest(ArrayList<Item> contents) {
        this._contents = new ArrayList<>(contents); 
    }

    public ArrayList<Item> getContents() {
        return _contents;
    }

    public void printContents() {
        if (_contents.isEmpty()) {
            System.out.println("⚠ Vide");
        } else {
            for (Item item : _contents) {
                System.out.print(item.getName() + " ");
            }
            System.out.println();
        }
    }
}