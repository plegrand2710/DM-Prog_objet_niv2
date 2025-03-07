package cursed_chronicles.Map;

import java.util.List;

public class Dungeon {
    private String _name;
    private List<Room> _rooms;

    public Dungeon(String name, int numberOfRooms) {
        _name = name;
        initRooms(numberOfRooms);
    }

    public String getName() {
        return _name;
    }

    public List<Room> getRooms() {
        return _rooms;
    }
    
    public void initRooms(int numberOfRooms) {
    	for (int i=1; i<=numberOfRooms; i++) {
    		String roomPath = _name + "_room" + Integer.toString(i);
    		Room room = new Room(roomPath);
    		_rooms.add(room);
    		System.out.println("Ajout : " + roomPath + "\n");
    	}
    }
}

