package cursed_chronicles.UI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoryManager {
    private ArrayList<Story> stories;

    public StoryManager() {
        this.stories = new ArrayList<>();
    }

    public void loadStoriesFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Story currentStory = null;
            List<String> clues = new ArrayList<>();
            String password = "";
            String title = "";
            StringBuilder content = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.equals("=== Histoire ===")) {
                    if (currentStory != null) {
                        currentStory.setContent(content.toString().trim());
                        stories.add(currentStory);
                    }
                    clues.clear();
                    content.setLength(0);
                    currentStory = null;
                } 
                else if (line.startsWith("Titre :")) {
                    title = line.replace("Titre :", "").trim();
                } 
                else if (line.startsWith("Mot de passe :")) {
                    password = line.replace("Mot de passe :", "").trim();
                } 
                else if (line.startsWith("Indices :")) {
                    clues.clear();
                    String[] words = line.replace("Indices :", "").trim().split(", ");
                    for (String word : words) {
                        clues.add(word.trim());
                    }
                } 
                else if (line.startsWith("Contenu :")) {
                    content.setLength(0); 
                } 
                else {
                    content.append(line).append("\n");
                }

                if (!title.isEmpty() && !password.isEmpty() && !clues.isEmpty() && content.length() > 0) {
                    currentStory = new Story(password, new ArrayList<>(clues), title, content.toString().trim());
                }
            }

            if (currentStory != null) {
                stories.add(currentStory);
            }

        } catch (IOException e) {
            System.err.println("❌ Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }

    public ArrayList<Story> getStories() {
        return stories;
    }

    public Story getStoryByPassword(String password) {
        for (Story story : stories) {
            if (story.getPassword().equalsIgnoreCase(password)) {
                return story;
            }
        }
        return null;
    }
}