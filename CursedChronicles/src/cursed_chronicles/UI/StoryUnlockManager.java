package cursed_chronicles.UI;

import java.awt.Dimension;

import javax.swing.*;

public class StoryUnlockManager {

    private Story story;

    public StoryUnlockManager(Story story) {
        this.story = story;
    }

    public void promptForPassword() {
        String message = "Tous les indices ont été découverts !\n\n";
        message += "🔎 **Récapitulatif des indices** :\n";
        message += String.join(", ", story.getDiscoveredClues()) + "\n\n";
        message += "Entrez le mot de passe final pour découvrir l'histoire complète :";

        boolean unlocked = false;

        while (!unlocked) {
            String input = JOptionPane.showInputDialog(null, message, "Déblocage de l'histoire", JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                return;
            }

            input = input.trim().toLowerCase();

            if (input.equals(story.getPassword().toLowerCase())) {
                unlocked = true;
                showStory();
                System.exit(0); 

            } else {
                JOptionPane.showMessageDialog(null, "❌ Mot de passe incorrect ! Réessayez.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private String formatStoryText(String text) {
        StringBuilder formattedText = new StringBuilder();
        int lineLength = 60;  

        int count = 0;
        for (String word : text.split(" ")) {
            formattedText.append(word).append(" ");
            count += word.length() + 1;
            if (count >= lineLength) {
                formattedText.append("\n");
                count = 0;
            }
        }
        return formattedText.toString();
    }

    private void showStory() {
    JTextArea textArea = new JTextArea(formatStoryText(story.toString()));
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);

    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(400, 300)); 

    JOptionPane.showMessageDialog(null, scrollPane, "Histoire Débloquée !", JOptionPane.INFORMATION_MESSAGE);
}
}