package cursed_chronicles.UI;

import javax.swing.*;

import cursed_chronicles.Map.Room;
import cursed_chronicles.Monster.Monster;
import cursed_chronicles.Player.ItemBooster;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NarrationPanel extends JPanel {
    private JLabel roomLabel;
    private JTextArea narrationText;
    private Timer typingTimer;
    private String fullText;
    private int charIndex;   

    public NarrationPanel(Room room, JFrame parentFrame) {
        setLayout(new BorderLayout());

        int availableHeight = parentFrame.getHeight() - 600;
        setPreferredSize(new Dimension(parentFrame.getWidth(), Math.max(availableHeight, 100)));

        String roomName = room.getName(); 
        String initialText = generateRoomNarrationInitial(room); 

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
        narrationText.repaint(); 

        typingTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (charIndex < fullText.length()) {
                    narrationText.append(String.valueOf(fullText.charAt(charIndex)));
                    narrationText.repaint(); 
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
    
    private static String generateRoomNarrationInitial(Room room) {
	    StringBuilder narration = new StringBuilder();

	    narration
	    	.append("🏰 **Bienvenue dans *Cursed Chronicles* !** ⚔️\n")
	        .append("Tu es un aventurier piégé dans un donjon maudit... Trouve des indices, combat les créatures et essaye de survivre !\n\n")
	        .append("💡 **Raccourcis clavier :**\n")
	        .append("- 🏹 **Se déplacer** : Flèches directionnelles ⬆️⬇️⬅️➡️\n")
	        .append("- ⚔️ **Attaquer** : Espace\n")
	        .append("- 🎁 **Ouvrir un coffre** : C\n")
	        .append("- 📖 **Ouvrir le journal** : J\n")
	        .append("- ⏭️ **Passer cette affichage** : Entrée\n")
	        .append("- 🏃 **Activer sprint** : S (augmente la vitesse de déplacement)\n\n")
	        .append("Une brise glaciale souffle à travers les fissures des murs...\n");


	    if (!room.getMonsters().isEmpty()) {
	        narration.append("👹 **Des créatures rôdent dans l'ombre :** ");
	        for (Monster monster : room.getMonsters()) {
	            narration.append(monster.getName()).append(", ");
	        }
	        narration.append("reste sur tes gardes !\n");
	    }

	    if (!room.getBoosters().isEmpty()) {
	        narration.append("✨ **Tu remarques des objets brillants au sol :** ");
	        for (ItemBooster booster : room.getBoosters()) {
	            narration.append(booster.getName()).append(", ");
	        }
	        narration.append("ils pourraient t’être utiles.\n");
	    }

	    if (room.getChestCount() > 0) {
	        narration.append("📦 Quelques coffres anciens sont disposés dans la salle... Ouvre-les avec `C` pour voir ce qu'ils contiennent !\n");
	    }

	    if (room.getHint() != null) {
	        narration.append("🔎 **Un indice est gravé sur le mur :** \"").append(room.getHint()).append("\"\n");
	    }

	    narration.append("\n⚠️ **Attention aux dangers :**\n")
	            .append("- ☠️ **Les pièges** : Si tu marches sur une trappe, tu perds **10 PV** !\n")
	            .append("- 👹 **Les monstres** : Ils **t’attaqueront** si tu es sur leur position.\n")
	            .append("- ⚔️ **Combat** : Attaque avec `Espace` si un monstre est **adjacent**.\n\n")
	            .append("🛠 **Les armes changent tes dégâts :**\n")
	            .append("- 🗡 **Épée** : Dégâts normaux.\n")
	            .append("- 🏹 **Flèche** : +10 dégâts.\n")
	            .append("- 🔨 **Marteau** : +20 dégâts.\n")
	            .append("- 🔫 **Pistolet** : +15 dégâts.\n\n")
	            .append("🎯 **Objectif** : Explore les salles, combats les monstres et trouve le moyen de **sortir du donjon vivant** !\n");

	    return narration.toString();
	}
    
    public String generateRoomNarration(Room room) {
        StringBuilder narration = new StringBuilder();

        narration.append("Une brise glaciale souffle à travers les fissures des murs.\n");

        if (!room.getMonsters().isEmpty()) {
            narration.append("Des créatures rôdent dans l'ombre : ");
            for (Monster monster : room.getMonsters()) {
                narration.append(monster.getName()).append(" ");
            }
            narration.append("\n");
        }

        if (!room.getBoosters().isEmpty()) {
            narration.append("Vous remarquez des objets brillants au sol : ");
            for (ItemBooster booster : room.getBoosters()) {
                narration.append(booster.getName()).append(" ");
            }
            narration.append("\n");
        }

        if (room.getChestCount() > 0) {
            narration.append("Quelques coffres anciens sont disposés dans la salle...\n");
        }

        if (room.getHint() != null) {
            narration.append("Un indice est gravé sur le mur : ").append(room.getHint()).append("\n");
        }

        return narration.toString();
    }
}