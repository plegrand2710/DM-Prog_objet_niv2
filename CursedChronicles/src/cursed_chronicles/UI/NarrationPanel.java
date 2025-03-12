package cursed_chronicles.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NarrationPanel extends JPanel {
    private JLabel roomLabel;
    private JTextArea narrationText;
    private Timer typingTimer;
    private String fullText;
    private int charIndex;   

    public NarrationPanel(String roomName, String initialText, JFrame parentFrame) {
        setLayout(new BorderLayout());

        int availableHeight = parentFrame.getHeight() - 600; 
        setPreferredSize(new Dimension(parentFrame.getWidth(), Math.max(availableHeight, 100)));

        roomLabel = new JLabel("Salle actuelle : " + roomName, SwingConstants.CENTER);
        roomLabel.setFont(new Font("Serif", Font.BOLD, 16));

        narrationText = new JTextArea();
        narrationText.setEditable(false);
        narrationText.setWrapStyleWord(true);
        narrationText.setLineWrap(true);
        narrationText.setFont(new Font("Serif", Font.PLAIN, 14));
        narrationText.setMargin(new Insets(10, 10, 10, 10));

        add(roomLabel, BorderLayout.NORTH);
        add(new JScrollPane(narrationText), BorderLayout.CENTER);

        setNarrationWithEffect(initialText);
    }

    public void updateRoomName(String newRoom) {
        roomLabel.setText("Salle actuelle : " + newRoom);
    }

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

    public void skipTypingEffect() {
        if (typingTimer != null && typingTimer.isRunning()) {
            typingTimer.stop();
            narrationText.setText(fullText);
        }
    }
}