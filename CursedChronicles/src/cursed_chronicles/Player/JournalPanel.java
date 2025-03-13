package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class JournalPanel extends JFrame {
    private DefaultListModel<String> journalModel;
    private JList<String> journalList;

    public JournalPanel(Player player) {
        setTitle("📖 Player Journal");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        journalModel = new DefaultListModel<>();
        journalList = new JList<>(journalModel);
        add(new JScrollPane(journalList), BorderLayout.CENTER);

        updateJournal(player.getJournal().getEntries());

        player.addPropertyChangeListener(evt -> {
            if ("journalEntry".equals(evt.getPropertyName())) {
                addEntry((String) evt.getNewValue());
            } else if ("journalUpdate".equals(evt.getPropertyName())) {
                int index = (int) evt.getOldValue();
                String updatedText = (String) evt.getNewValue();
                updateEntry(index, updatedText);
            }
        });

        positionTopRight();
    }

    private void positionTopRight() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - getWidth() - 20;
        int y = 20;
        setLocation(x, y);
    }

    public void addEntry(String entry) {
        journalModel.addElement(entry);
    }
    
    public void updateEntry(int index, String newText) {
        if (index >= 0 && index < journalModel.size()) {
            journalModel.set(index, newText);
        }
    }

    public void updateJournal(List<String> entries) {
        journalModel.clear();
        for (String entry : entries) {
            journalModel.addElement(entry);
        }
    }

    public void showJournal() {
        setVisible(true);
    }
}