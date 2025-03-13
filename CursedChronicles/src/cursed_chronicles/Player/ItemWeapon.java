package cursed_chronicles.Player;

public class ItemWeapon extends Item {
    private int positionX;
    private int positionY;

    public ItemWeapon(String filePath, int x, int y) {
        super(filePath);
        this.positionX = x;
        this.positionY = y;
        setDescription(getName());
    }

    // ✅ Getters
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    // ✅ Setters
    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    @Override
    public String toString() {
        return super.toString() + " (Position: " + positionX + ", " + positionY + ")";
    }
}