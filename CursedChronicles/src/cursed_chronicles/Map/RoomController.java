package cursed_chronicles.Map;

import cursed_chronicles.Constant;
import cursed_chronicles.Player.PlayerController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RoomController {
    private Room _currentRoom;
    private RoomView _roomView;
    private PlayerController _playerController;
    private LinkedHashMap<String, Room> _rooms;

    public RoomController(RoomView roomView) {
        _roomView = roomView;
        _rooms = new LinkedHashMap<>();
        setupRooms();
        setFirstRoomAsCurrent();
    }
    
    public void setFirstRoomAsCurrent() {
        if (!_rooms.isEmpty()) {
            _currentRoom = _rooms.values().iterator().next();
            System.out.println("Première salle définie : " + _currentRoom.getName());
        } else {
            System.err.println("Aucune salle disponible.");
        }
    }
    
    public void changeRoomFrom(String directionSource) {
    	String nextRoomName = _currentRoom.getDoorDestination(directionSource);
    	
    	String directionDestination;
    	switch (directionSource) {
    	case "N":
    		directionDestination = "S";
    		break;
    	case "S":
    		directionDestination = "N";
    		break;
    	case "W":
    		directionDestination = "E";
    		break;
    	case "E":
    		directionDestination = "W";
    		break;
    	case "ENTERDONJON1":
    		directionDestination = "SPAWNENTERDONJON1";
    		break;
    	case "ENTERDONJON2":
    		directionDestination = "SPAWNENTERDONJON2";
    		break;
    	case "ENTERDONJON3":
    		directionDestination = "SPAWNENTERDONJON3";
    		break;
    		
    	case "EXITDONJON1":
    		directionDestination = "SPAWNEXITDONJON1";
    		break;
    	case "EXITDONJON2":
    		directionDestination = "SPAWNEXITDONJON2";
    		break;
    	case "EXITDONJON3":
    		directionDestination = "SPAWNEXITDONJON3";
    		break;
    	default:
    		directionDestination = "S";
    		break;
    	
    	}
    	
    	
    	Room nextRoom = _rooms.get(nextRoomName);
    	int[] nextRoomCoords = nextRoom.getSpawnPoint(directionDestination);
        System.out.println("📍 Nouvelle position du joueur : X=" + nextRoomCoords[0] + ", Y=" + nextRoomCoords[1]);
    	setCurrentRoom(nextRoomName);
    	loadRoom();
    	
    	System.out.println("Next room spawn point : " + nextRoomCoords[0] + "/" + nextRoomCoords[1]);
    	
    	
    	_playerController.setSpawn();
    	_playerController.setPlayerPosition(nextRoomCoords[0], nextRoomCoords[1]);



        _playerController.getPlayerView().stopAnimation();
        _playerController.getPlayerView().repaint();
        
        _playerController.notifyAnimationFinished();
//    	_roomView.add(_playerController.getPlayerView(), Integer.valueOf(2)); 

        _playerController.getPlayerView().resetMovement();
    }


    public void setupRooms() {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/rooms_config.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("/");
                if (parts.length >= 3) {
                    String roomName = parts[0];
                    Room room = new Room(roomName);

//                    System.out.println("Lecture de la salle: " + roomName); // Debug

                    initRoomDoors(room, parts[1]);
                    initRoomSpawns(room, parts[2]);

//                    System.out.println("Ajout de la salle: " + roomName);
                    _rooms.put(roomName, room);

                    System.out.println(room); 
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des salles : " + e.getMessage());
        }
    }


    public void initRoomDoors(Room room, String doors) {
        if (doors.trim().isEmpty()) {
            System.err.println("Aucune porte définie pour " + room.getName());
            return;
        }

//        System.out.println("Parsing doors: " + doors); // Debug

        String[] doorPairs = doors.split(";");
        for (String doorPair : doorPairs) {
//            System.out.println("Processing doorPair: " + doorPair); // Debug
            String[] keyValue = doorPair.split(":");
            if (keyValue.length == 2 && !keyValue[1].trim().isEmpty()) { 
                String direction = keyValue[0].trim();
                String destination = keyValue[1].trim();
                room.addDoor(direction, destination);
//                System.out.println("Ajout de la porte: " + direction + " -> " + destination);
            }
        }
    }



    public void initRoomSpawns(Room room, String spawns) {
        if (spawns.trim().isEmpty()) {
            System.err.println("Aucun spawn défini pour " + room.getName());
            return;
        }

//        System.out.println("Parsing spawns: " + spawns); // Debug

        String[] spawnPairs = spawns.split(";");
        for (String spawnPair : spawnPairs) {
//            System.out.println("Processing spawnPair: " + spawnPair); // Debug
            String[] keyValue = spawnPair.split(":");
            if (keyValue.length == 2 && !keyValue[1].trim().isEmpty()) { 
                String direction = keyValue[0].trim();
                String[] coords = keyValue[1].split(",");
                if (coords.length == 2) {
                    try {
                        int spawnX = Integer.parseInt(coords[0].trim());
                        int spawnY = Integer.parseInt(coords[1].trim());
                        room.setSpawnPoint(direction, spawnX, spawnY);
//                        System.out.println("Ajout du spawn: " + direction + " -> (" + spawnX + ", " + spawnY + ")");
                    } catch (NumberFormatException e) {
                        System.err.println("Coordonnées invalides pour spawnPair: " + spawnPair);
                    }
                }
            }
        }
    }


    
    public void setCurrentRoom(String roomName) {
        Room room = _rooms.get(roomName);
        if (room != null) {
            _currentRoom = room;
        } else {
            System.err.println("Erreur : La salle '" + roomName + "' n'existe pas.");
        }
    }

    public void loadRoom() {
//        if (room == null) {
//            System.err.println("Erreur : La salle est null.");
//            return;
//        }
//
//        _currentRoom = room;
        _roomView.displayRoom(_currentRoom);
        _playerController.setCollisionsLayer(_currentRoom.getCollisionsLayer());
        
        _roomView.add(_playerController.getPlayerView(), Integer.valueOf(2));
        _roomView.revalidate();
        _roomView.repaint();
    }

    public Room getRoom(String roomName) {
        return _rooms.get(roomName);
    }

    public PlayerController getPlayerController() {
        return _playerController;
    }

    public void setPlayerController(PlayerController playerController) {
        _playerController = playerController;
        _playerController.getPlayerView().stopAnimation();
        _playerController.getPlayerView().repaint();

    }

    public Room getCurrentRoom() {
        return _currentRoom;
    }

    public boolean isInCollision(int positionX, int positionY) {
        return _currentRoom.getCollisionsLayer()[positionY][positionX] == Constant.WALL_COLLISION_ID;
    }
    
    public LinkedHashMap<String, Room> getRooms() {
        return _rooms;
    }
}
