package cursed_chronicles.Map;

public class RoomController {
    private Room _currentRoom;
    private RoomView _roomView;
    private String _tilesetPath;

    public RoomController(RoomView roomView, String tilesetPath) {
        this._roomView = roomView;
        this._tilesetPath = tilesetPath;
    }

    public void loadRoom(Room room) {
        if (room == null) {
            System.err.println("Erreur : La salle est null.");
            return;
        }
        
        _currentRoom = room;
        _roomView.displayRoom(_currentRoom, _tilesetPath);
    }
}
