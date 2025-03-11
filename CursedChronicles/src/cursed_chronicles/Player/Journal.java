package cursed_chronicles.Player;

import java.util.ArrayList;

public class Journal {
    private ArrayList<String> entries;

    public Journal() {
        this.entries = new ArrayList<>();
        initializeJournal();
    }

    public void initializeJournal() {
        entries.add("📖 Journal du joueur 📖\n"
                + "Ce journal consigne tes découvertes et événements marquants.\n"
                + "Utilise-le pour suivre ta progression et noter les indices cruciaux.\n"
                + "Bonne aventure !");
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