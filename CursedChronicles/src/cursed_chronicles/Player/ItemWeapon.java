package cursed_chronicles.Player;

public class ItemWeapon extends Item {
    public ItemWeapon(String filePath) {
        super(filePath);
        // Pour une arme, la description est simplement le nom de l'item
        setDescription(getName());
    }
}
