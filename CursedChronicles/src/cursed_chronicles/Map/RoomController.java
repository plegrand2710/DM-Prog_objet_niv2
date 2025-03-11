package cursed_chronicles.Map;

import cursed_chronicles.Player.PlayerController;

public class RoomController {
    private Room _currentRoom;
    private RoomView _roomView;
    private PlayerController _playerController;

    public RoomController(RoomView roomView) {
        _roomView = roomView;
    }

    public void loadRoom(Room room) {
        if (room == null) {
            System.err.println("Erreur : La salle est null.");
            return;
        }
        
        _currentRoom = room;
        _roomView.displayRoom(_currentRoom);
        _playerController.setCollisionsLayer(_currentRoom.getCollisionsLayer());
    }
    
    public PlayerController getPlayerController() {
    	return _playerController;
    }
    
    public void setPlayerController(PlayerController playerController) {
    	_playerController = playerController;
    }
}
