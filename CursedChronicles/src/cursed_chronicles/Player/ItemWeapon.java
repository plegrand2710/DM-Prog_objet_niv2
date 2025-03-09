package cursed_chronicles.Player;

public class ItemWeapon extends Item {
    public ItemWeapon(String filePath) {
        super(filePath);
        setDescription(getName());
    }
}
