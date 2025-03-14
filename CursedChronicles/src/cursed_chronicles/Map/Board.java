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

        // ============== Donjon 1 ==============
        roomData.put("donjon1_room1", new RoomElements(
            new ArrayList<Monster>(),
            new ArrayList<Item>() {{
                add(new ItemBooster("booster_speed1", -1, -1));
            }},
            new ArrayList<>()
        ));

        roomData.put("donjon1_room2", new RoomElements(
            new ArrayList<Monster>() {{
                add(new Monster("skull", 12, 8, 1, null));
                add(new Monster("skull", 12, 8, 1, null));
                add(new Monster("skull", 12, 8, 1, null));
            }},
            new ArrayList<>(),
            new ArrayList<>()
        ));

        roomData.put("donjon1_room3", new RoomElements(
                new ArrayList<>(), 
                new ArrayList<Item>() {{
                    add(new ItemBooster("booster_damage", -1, -1));
                    add(new ItemBooster("booster_life_1", -1, -1));
                }},
                new ArrayList<ArrayList<Item>>() {{
                    add(new ArrayList<Item>() {{
                        add(new ItemBooster("booster_defense_1", -1, -1));
                        add(new ItemWeapon("rifler_sprite", -1, -1));
                    }});
                    add(new ArrayList<Item>() {{
                        add(new ItemBooster("booster_damage", -1, -1));
                        add(new ItemBooster("booster_defense_1", -1, -1));
                        add(new ItemBooster("booster_speed2", -1, -1));
                    }});
                    add(new ArrayList<Item>() {{

                        add(new ItemWeapon("bow_sprite", -1, -1));
                        }});
                }}
            ));

        roomData.put("donjon1_room4", new RoomElements(
                new ArrayList<Monster>() {{
                    add(new Monster("vampire", 20, 10, 1, null));
                    add(new Monster("vampire", 20, 10, 1, null));
                    add(new Monster("vampire", 20, 10, 1, null));
                    add(new Monster("vampire", 20, 10, 1, null));
                }},
                new ArrayList<>(),
                new ArrayList<>()
            ));
        
        roomData.put("donjon1_room5", new RoomElements(
                new ArrayList<Monster>() {{        
                	add(new Monster("Demon", 12, 8, 2, null));

                }},
                new ArrayList<>(),
                new ArrayList<>() 
            ));
        
        // ============== Donjon 2 ==============
        roomData.put("donjon2_room1", new RoomElements(
                new ArrayList<Monster>(),
                new ArrayList<Item>() {{
                    add(new ItemBooster("booster_defense_2", -1, -1));
                    add(new ItemBooster("booster_life_2", -1, -1));
                }},
                new ArrayList<>()
            ));
        roomData.put("donjon2_room2", new RoomElements(
                new ArrayList<Monster>() {{
                    add(new Monster("bat", 20, 10, 1, null));
                    add(new Monster("bat", 20, 10, 1, null));
                    add(new Monster("bat", 20, 10, 1, null));
                }},
                new ArrayList<Item>(),
                new ArrayList<>()
            ));
        roomData.put("donjon2_room3", new RoomElements(
                new ArrayList<Monster>() {{
                    add(new Monster("wolf", 0, 0, 1, null));
                    add(new Monster("wolf", 0, 0, 1, null));
                    add(new Monster("wolf", 0, 0, 1, null));
                    add(new Monster("wolf", 0, 0, 1, null));
                }},
                new ArrayList<Item>(),
                new ArrayList<>()
            ));
        roomData.put("donjon2_room4", new RoomElements(
                new ArrayList<Monster>() {{
                    add(new Monster("werewolf", 0, 0, 1, null));
                    add(new Monster("werewolf", 0, 0, 1, null));
                    add(new Monster("werewolf", 0, 0, 1, null));
                    add(new Monster("werewolf", 0, 0, 1, null));
                }},
                new ArrayList<Item>(),
                new ArrayList<>()
            ));
        roomData.put("donjon2_room5", new RoomElements(
                new ArrayList<Monster>(),
                new ArrayList<Item>() {{
                    add(new ItemBooster("booster_defense_3", -1, -1));
                    add(new ItemBooster("booster_life_3", -1, -1));
                    add(new ItemBooster("booster_speed1", -1, -1));
                }},
                new ArrayList<>()
            ));
        roomData.put("donjon2_room6", new RoomElements(
                new ArrayList<Monster>() {{        
                	add(new Monster("red_dragon", 0, 0, 2, null));

                }},
                new ArrayList<Item>(),
                new ArrayList<>()
            ));
        
        
        
        // ============== Donjon 3 ==============
        
        roomData.put("donjon3_room1", new RoomElements(
                new ArrayList<Monster>(),
                new ArrayList<Item>(),
                new ArrayList<>()
            ));
        roomData.put("donjon3_room2", new RoomElements(
                new ArrayList<Monster>() {{
                    add(new Monster("goblin", 0, 0, 1, null));
                }},
                new ArrayList<Item>(),
                new ArrayList<>()
            ));
        roomData.put("donjon3_room3", new RoomElements(
                new ArrayList<Monster>(),
                new ArrayList<Item>() {{
                    add(new ItemBooster("booster_defense_1", -1, -1));
                    add(new ItemBooster("booster_speed1", -1, -1));
                }},
                new ArrayList<>()
            ));
        roomData.put("donjon3_room4", new RoomElements(
                new ArrayList<Monster>() {{
                    add(new Monster("orc", 0, 0, 1, null));
                }},
                new ArrayList<Item>(),
                new ArrayList<>()
            ));
        roomData.put("donjon3_room5", new RoomElements(
                new ArrayList<Monster>() {{        
                	add(new Monster("golem", 0, 0, 2, null));

                }},
                new ArrayList<Item>(),
                new ArrayList<>()
            ));
        
        
        for (Room room : _rooms) {
            RoomElements elements = roomData.get(room.getName());
            if (elements != null) {
            	for (Monster monster : elements.monsters) {
                    int[] position = getRandomFreePosition(room);
                    monster.setPositionX(position[0]);
                    monster.setPositionY(position[1]);

                    Monster correctedMonster = new Monster(
                        monster.getName(), 
                        monster.getPositionX(), 
                        monster.getPositionY(), 
                        monster.getLevel(), 
                        room
                    );

                    room.addMonster(correctedMonster);
                }


                elements.items.forEach(item -> {
                    int[] position = getRandomFreePosition(room);
                    item.setPosition(position[0], position[1]);

                    System.out.println("Item ajouté : " + item.getName() + " | Booster: " + (item instanceof ItemBooster) + " | Weapon: " + (item instanceof ItemWeapon));

                    if (item instanceof ItemBooster) {
                        room.addBooster((ItemBooster) item);
                    } else if (item instanceof ItemWeapon) {
                        room.addWeapon((ItemWeapon) item);
                    }
                });

                addChestsFromLayer(room, elements.chestItems);

                if (elements.hint != null) {
                    room.setHint(elements.hint);
                }
            }
        }
    }

	private void addChestsFromLayer(Room room, ArrayList<ArrayList<Item>> chestContentsList) {
	    int[][] chestsLayer = room.getChestsLayer();
	
	    if (chestsLayer == null) {
	        System.err.println("⚠ Erreur : Le calque chests.csv est introuvable pour la salle " + room.getName());
	        return;
	    }
	
	    int chestIndex = 0;
	
	    for (int y = 0; y < chestsLayer.length; y++) {
	        for (int x = 0; x < chestsLayer[y].length; x++) {
	            if (chestsLayer[y][x] == 851) { 
	                if (chestIndex < chestContentsList.size()) {
	                    ArrayList<Item> chestContent = chestContentsList.get(chestIndex);
	                    room.addChest(chestContent);
	
	                    System.out.println("📦 Coffre #" + (chestIndex + 1) + " ajouté en (" + x + ", " + y + ") avec : " + chestContent);
	                    chestIndex++;
	                } else {
	                    System.out.println("⚠ Coffre en (" + x + ", " + y + ") mais pas assez de contenus définis !");
	                }
	            }
	        }
	    }
	}

    private int[] getRandomFreePosition(Room room) {
        int[][] collisions = room.getCollisionsLayer();
        int minX = 3, maxX = 12;  
        int minY = 3, maxY = 14;  
        int x, y;

        do {
            x = _rand.nextInt((maxX - minX) + 1) + minX;
            y = _rand.nextInt((maxY - minY) + 1) + minY;
        } while (collisions[y][x] == Constant.WALL_COLLISION_ID);

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
        ArrayList<Item> items; 
        ArrayList<ArrayList<Item>> chestItems; 
        String hint;

        RoomElements(ArrayList<Monster> monsters, ArrayList<Item> items, ArrayList<ArrayList<Item>> chestItems) {
            this.monsters = monsters;
            this.items = items;
            this.chestItems = chestItems;
        }
    }
}