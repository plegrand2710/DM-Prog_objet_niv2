package cursed_chronicles.Player;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item newItem) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(newItem.getName())) {
                item.incrementQuantity();
                return;
            }
        }
        items.add(newItem);
    }

    public void removeItem(Item itemToRemove) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getName().equalsIgnoreCase(itemToRemove.getName())) {
                if (item.getQuantity() > 1) {
                    item.decrementQuantity();
                } else {
                    items.remove(i);
                }
                break;
            }
        }
    }

    public boolean hasItem(String itemName) {
        return items.stream().anyMatch(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public Item getItem(String itemName) {
        return items.stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    public ArrayList<Item> getItems() {
        return new ArrayList<>(items);
    }

    public void displayInventory() {
        if (items.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Inventory:");
            for (Item item : items) {
                System.out.println("- " + item.getName() + ": " + item.getDescription());
            }
        }
    }
}