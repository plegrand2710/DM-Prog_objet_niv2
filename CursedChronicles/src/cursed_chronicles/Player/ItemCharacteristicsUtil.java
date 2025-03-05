package cursed_chronicles.Player;

public class ItemCharacteristicsUtil {

    public static String getCharacteristicType(String filename) {
        String lower = filename.toLowerCase();
        if(lower.contains("defense"))
            return "defense";
        if(lower.contains("life"))
            return "life";
        if(lower.contains("speed"))
            return "speed";
        if(lower.contains("damage"))
            return "damage";
        return "";
    }

    public static int getBonusFromFilename(String filename) {
        for (char c : filename.toCharArray()) {
            if (Character.isDigit(c)) {
                int num = Character.getNumericValue(c);
                if (num == 1)
                    return 25;
                else if (num == 2)
                    return 50;
                else if (num == 3)
                    return 75;
            }
        }
        return 25;
    }
}
