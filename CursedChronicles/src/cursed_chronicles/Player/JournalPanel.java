package cursed_chronicles.Player;

import javax.swing.*;
import java.awt.*;

public class JournalPanel extends JFrame {
    private DefaultListModel<String> journalModel;
    private JList<String> journalList;

    public JournalPanel() {
        setTitle("📖 Player Journal");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        journalModel = new DefaultListModel<>();
        journalList = new JList<>(journalModel);
        add(new JScrollPane(journalList), BorderLayout.CENTER);
    }

    public void addEntry(String entry) {
        journalModel.addElement(entry);
    }

    public void showJournal() {
        setVisible(true);
    }
}