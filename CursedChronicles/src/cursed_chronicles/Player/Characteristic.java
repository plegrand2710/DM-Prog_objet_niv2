package cursed_chronicles.Player;


public class Characteristic {
    private String name;
    private int value;

    public Characteristic(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + ": " + value;
    }
}