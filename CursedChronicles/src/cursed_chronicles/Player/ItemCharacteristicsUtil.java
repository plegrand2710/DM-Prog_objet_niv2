package cursed_chronicles.Player;

public class ItemCharacteristicsUtil {

    public static String getCharacteristicType(String filename) {
        String lower = filename.toLowerCase();
        if (lower.contains("general")) {
            return "defense";
        } else if (lower.contains("health")) {
            return "life";
        } else if (lower.contains("speed")) {
            return "speed";
        } else if (lower.contains("damage")) {
            return "damage";
        }
        return "";
    }

    public static int getBonusFromFilename(String filename) {
        int bonus = 25; 
        int underscoreIndex = filename.lastIndexOf('_');
        int dotIndex = filename.lastIndexOf('.');
        if (underscoreIndex != -1 && dotIndex != -1 && underscoreIndex < dotIndex) {
            String numStr = filename.substring(underscoreIndex + 1, dotIndex);
            try {
                int num = Integer.parseInt(numStr);
                if (num == 1) {
                    bonus = 25;
                } else if (num == 2) {
                    bonus = 50;
                } else if (num == 3) {
                    bonus = 75;
                }
            } catch (NumberFormatException e) {
                bonus = extractFirstDigitBonus(filename);
            }
        } else {
            bonus = extractFirstDigitBonus(filename);
        }
        return bonus;
    }
    
    private static int extractFirstDigitBonus(String filename) {
        for (char c : filename.toCharArray()) {
            if (Character.isDigit(c)) {
                int num = Character.getNumericValue(c);
                if (num == 1) return 25;
                else if (num == 2) return 50;
                else if (num == 3) return 75;
            }
        }
        return 25; 
    }
}
