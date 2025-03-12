package cursed_chronicles.Player;

public class ItemBooster extends Item {
    private int _x;
    private int _y;

    public ItemBooster(String filePath, int x, int y) {
        super(filePath);
        this._x = x;
        this._y = y;

        // On utilise le nom pour déterminer la caractéristique et le bonus
        String boosterName = getName();
        String type = ItemCharacteristicsUtil.getCharacteristicType(boosterName);
        int bonus = ItemCharacteristicsUtil.getBonusFromFilename(boosterName);

        if (!type.isEmpty()) {
            setDescription(type + " : " + bonus);
            addCharacteristic(new Characteristic(type, bonus));
        } else {
            setDescription(boosterName);
        }
    }

    // Getters pour récupérer les coordonnées du booster
    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    // Setter si besoin de modifier la position plus tard
    public void setPosition(int x, int y) {
        this._x = x;
        this._y = y;
    }
}