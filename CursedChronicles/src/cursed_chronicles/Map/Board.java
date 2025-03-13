package cursed_chronicles.Map;

import cursed_chronicles.Player.*;
import cursed_chronicles.Constant;
import cursed_chronicles.Monster.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Board {
    private RoomController _roomController;
    private ArrayList<Room> _rooms;
    private final Random _rand = new Random();

    
    public Board(RoomView roomView) {
        _roomController = new RoomController(roomView);
        _rooms = new ArrayList<>(_roomController.getRooms().values());
        addGameElementsToRooms();
    }

    private void addGameElementsToRooms() {
        HashMap<String, RoomElements> roomData = new HashMap<>();

        roomData.put("donjon1_room1", new RoomElements(
            new ArrayList<Monster>() {{
                add(new Monster("Goblin", 10, 5, 1));
            }},
            new ArrayList<ItemBooster>() {{
                add(new ItemBooster("booster_speed1", -1, -1));
                add(new ItemBooster("booster_speed2", -1, -1));
                add(new ItemBooster("booster_defense_3", -1, -1));
            }},
            new ArrayList<ItemBooster>() {{
                add(new ItemBooster("booster_defense_1", -1, -1));
            }},
            "Un message est gravé sur le mur : 'La clé est à l'Est...'"
        ));

        roomData.put("donjon1_room2", new RoomElements(
            new ArrayList<Monster>() {{
                add(new Monster("Squelette", 12, 8, 2));
                add(new Monster("Zombie", 15, 7, 1));
            }},
            new ArrayList<>(),
            new ArrayList<>(),
            "Les ombres murmurent : 'Seule la lumière révélera le passage.'"
        ));

        roomData.put("donjon1_room3", new RoomElements(
            new ArrayList<Monster>() {{
                add(new Monster("Ogre", 20, 10, 2));
            }},
            new ArrayList<ItemBooster>() {{
                add(new ItemBooster("Potion de vitesse", -1, -1));
            }},
            new ArrayList<ItemBooster>() {{
                add(new ItemBooster("Potion secrète", -1, -1));
            }},
            null
        ));

        for (Room room : _rooms) {
            RoomElements elements = roomData.get(room.getName());
            if (elements != null) {
            	elements.monsters.forEach(monster -> {
                    int[] position = getRandomFreePosition(room);
                    monster.setPositionX(position[0]);
                    monster.setPositionY(position[1]);
                    room.addMonster(monster);
                });
                elements.boosters.forEach(booster -> {
                    int[] position = getRandomFreePosition(room);
                    booster.setPosition(position[0], position[1]);
                    room.addBooster(booster);
                });
                elements.chestBoosters.forEach(chestBooster -> {
                    int[] position = getRandomFreePosition(room);
                    chestBooster.setPosition(position[0], position[1]);
                    room.addChest(position[0], position[1], new ArrayList<ItemBooster>() {{ add(chestBooster); }});
                });

                if (elements.hint != null) {
                    room.setHint(elements.hint);
                }
            }
        }
    }
    
    private int[] getRandomFreePosition(Room room) {
        int[][] collisions = room.getCollisionsLayer();
        int minX = 3, maxX = 12;  // X compris entre 3 et 12
        int minY = 3, maxY = 14;  // Y compris entre 3 et 14
        int x, y;
        
        do {
            x = _rand.nextInt((maxX - minX) + 1) + minX; // Génère entre minX et maxX
            y = _rand.nextInt((maxY - minY) + 1) + minY; // Génère entre minY et maxY
        } while (collisions[y][x] == Constant.WALL_COLLISION_ID); // Vérifie que ce n'est pas un mur

        return new int[]{x, y};
    }


    public RoomController getRoomController() {
        return _roomController;
    }

    public Room getCurrentRoom() {
        return _roomController.getCurrentRoom();
    }

    public void changeRoom(String direction) {
        _roomController.changeRoomFrom(direction);
    }

    private static class RoomElements {
        ArrayList<Monster> monsters;
        ArrayList<ItemBooster> boosters;
        ArrayList<ItemBooster> chestBoosters;
        String hint;

        RoomElements(ArrayList<Monster> monsters, ArrayList<ItemBooster> boosters, ArrayList<ItemBooster> chestBoosters, String hint) {
            this.monsters = monsters;
            this.boosters = boosters;
            this.chestBoosters = chestBoosters;
            this.hint = hint;
        }
    }
}