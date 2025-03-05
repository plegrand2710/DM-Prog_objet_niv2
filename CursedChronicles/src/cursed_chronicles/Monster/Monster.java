/*package cursed_chronicles.Monster;

import java.util.ArrayList;
import cursed_chronicles.Player.Characteristic;
import cursed_chronicles.Player.Player;

public class Monster {
    private String name;
    private int positionX;
    private int positionY;
    private int level;
    private int difficultyLevel;
    private ArrayList<Characteristic> characteristics;
    //private StrategieCombat strategieCombat;

    public Monster(String name, int difficultyLevel, StrategieCombat strategieCombat) {
        this.name = name;
        this.positionX = 0;
        this.positionY = 0;
        this.level = difficultyLevel;
        this.difficultyLevel = difficultyLevel;
        this.characteristics = new ArrayList<>();
        //this.strategieCombat = strategieCombat;

        initializeDefaultCharacteristics();
    }

    // Initialisation des caractéristiques par défaut
    private void initializeDefaultCharacteristics() {
        characteristics.add(new Characteristic("Life", 100));
        characteristics.add(new Characteristic("Defense", 100));
        characteristics.add(new Characteristic("Speed", 5));
    }

    public String getName() {
        return name;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getLevel() {
        return level;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public ArrayList<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public void defineCharacteristics() {
        for (Characteristic c : characteristics) {
            System.out.println(c);
        }
    }

    public void attack(Player player) {
        strategieCombat.executer(this, player);
    }

    public void defend() {
        System.out.println(name + " se défend !");
    }

    @Override
    public String toString() {
        return "Monstre: " + name + " (Niveau: " + level + ", Difficulté: " + difficultyLevel + ")";
    }
}*/
