package cursed_chronicles.Map;

public class Door {
    private String _direction;
    private String _destinationRoom;

    public Door(String direction, String destinationRoom) {
        this._direction = direction;
        this._destinationRoom = destinationRoom;
    }

    public String getDirection() {
        return _direction;
    }

    public String getDestinationRoom() {
        return _destinationRoom;
    }
}
