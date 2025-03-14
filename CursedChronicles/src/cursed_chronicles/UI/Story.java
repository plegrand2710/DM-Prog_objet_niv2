package cursed_chronicles.UI;

import java.util.ArrayList;
import java.util.List;

public class Story {
    private String password;
    private List<String> clues;
    private List<String> _discoveredClues;
    private String title;
    private String content;

    public Story(String password, List<String> clues, String title, String content) {
        this.password = password;
        this.clues = new ArrayList<>(clues);
        this._discoveredClues = new ArrayList<>();
        this.title = title;
        this.content = content;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getClues() {
        return new ArrayList<>(clues);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNextClue() {
        for (String clue : clues) {
            if (!_discoveredClues.contains(clue)) {
                _discoveredClues.add(clue);
                
                if (allCluesDiscovered()) {
                    new StoryUnlockManager(this).promptForPassword();
                }
                
                return clue;
            }
        }
        return null; 
    }

    
    public boolean allCluesDiscovered() {
        return _discoveredClues.size() == clues.size();
    }

    public List<String> getDiscoveredClues() {
        return new ArrayList<>(_discoveredClues);
    }
    
    @Override
    public String toString() {
        return "Titre : " + title + "\n" +
               "Mot de passe : " + password + "\n" +
               "Indices : " + String.join(", ", clues) + "\n\n" +
               content;
    }
}