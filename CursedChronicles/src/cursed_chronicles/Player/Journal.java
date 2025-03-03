package cursed_chronicles.Player;

import java.util.ArrayList;

public class Journal {
    private ArrayList<String> entries;

    public Journal() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(String entry) {
        entries.add(entry);
    }

    public ArrayList<String> getEntries() {
        return new ArrayList<>(entries);
    }

    public void displayJournal() {
        if (entries.isEmpty()) {
            System.out.println("The journal is empty.");
        } else {
            System.out.println("Player Journal:");
            for (String entry : entries) {
                System.out.println("- " + entry);
            }
        }
    }
}