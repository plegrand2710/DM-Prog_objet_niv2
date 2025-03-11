package cursed_chronicles.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NarrationPanel extends JPanel {
    private JLabel roomLabel;
    private JTextArea narrationText;
    private Timer typingTimer;
    private String fullText; // Texte complet à afficher
    private int charIndex;   // Position actuelle dans le texte

    public NarrationPanel(String roomName, String initialText, JFrame parentFrame) {
        setLayout(new BorderLayout());

        // 🔹 Ajuste la hauteur en fonction de la taille de la fenêtre
        int availableHeight = parentFrame.getHeight() - 600; // 600 = hauteur estimée pour la salle
        setPreferredSize(new Dimension(parentFrame.getWidth(), Math.max(availableHeight, 100)));

        // 📌 Label pour afficher le nom de la salle
        roomLabel = new JLabel("Salle actuelle : " + roomName, SwingConstants.CENTER);
        roomLabel.setFont(new Font("Serif", Font.BOLD, 16));

        // 📌 Zone de texte pour afficher la narration
        narrationText = new JTextArea();
        narrationText.setEditable(false);
        narrationText.setWrapStyleWord(true);
        narrationText.setLineWrap(true);
        narrationText.setFont(new Font("Serif", Font.PLAIN, 14));
        narrationText.setMargin(new Insets(10, 10, 10, 10));

        // 📌 Ajout des composants
        add(roomLabel, BorderLayout.NORTH);
        add(new JScrollPane(narrationText), BorderLayout.CENTER);

        // Effet d'écriture progressive
        setNarrationWithEffect(initialText);
    }

    // 🔹 Mettre à jour la salle actuelle
    public void updateRoomName(String newRoom) {
        roomLabel.setText("Salle actuelle : " + newRoom);
    }

    // 🔹 Effet d'écriture progressive
    public void setNarrationWithEffect(String newText) {
        if (typingTimer != null && typingTimer.isRunning()) {
            typingTimer.stop();
        }

        fullText = newText;
        charIndex = 0;
        narrationText.setText("");

        typingTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (charIndex < fullText.length()) {
                    narrationText.append(String.valueOf(fullText.charAt(charIndex)));
                    charIndex++;
                } else {
                    typingTimer.stop();
                }
            }
        });

        typingTimer.start();
    }

    // 🔹 Passer l'effet et afficher le texte immédiatement
    public void skipTypingEffect() {
        if (typingTimer != null && typingTimer.isRunning()) {
            typingTimer.stop();
            narrationText.setText(fullText);
        }
    }
}