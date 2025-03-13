package cursed_chronicles.Player;

public class Clue {
    private String _text;

    public Clue(String text) {
        this._text = text;
    }

    public String getText() {
        return _text;
    }

    public void setText(String text) {
        this._text = text;
    }
}