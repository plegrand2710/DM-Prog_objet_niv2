package cursed_chronicles.Player;

public class ItemBooster extends Item {
    public ItemBooster(String filePath) {
        super(filePath);
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
}
