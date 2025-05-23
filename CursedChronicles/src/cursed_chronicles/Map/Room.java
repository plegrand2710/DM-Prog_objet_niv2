package cursed_chronicles.Map;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import cursed_chronicles.Monster.Monster;
import cursed_chronicles.Player.Item;
import cursed_chronicles.Player.ItemBooster;
import cursed_chronicles.Player.ItemWeapon;

public class Room {
    private String _name;
    private int[][] _floorLayer;
    private int[][] _trapsLayer;
    private int[][] _sideWallsLayer;
    private int[][] _sideDoorsLayer;
    private int[][] _frontWallsLayer;
    private int[][] _frontDoorsLayer;
    private int[][] _pillarLayer;
    private int[][] _chestsLayer;
    private int[][] _decorationsLayer;
    private int[][] _collisionsLayer;

    private HashMap<String, String> _doors;
    private HashMap<String, int[]> _spawnPoints;
    
    private ArrayList<Monster> _monsters; 
    private ArrayList<ItemBooster> _boosters;
    private ArrayList<ItemWeapon> _weapons; 
    private ArrayList<Chest> _chests; 

    private String _hint; 

    public Room(String name) {
        _name = name;
        _doors = new HashMap<>();
        _spawnPoints = new HashMap<>();
        _monsters = new ArrayList<>();
        _boosters = new ArrayList<>();
        _weapons = new ArrayList<>();

        _chests = new ArrayList<>();
        _hint = null; 
        initLayers();
    }

    
    
    private void initLayers() {
        String basePath = "assets/maps/" + _name + "/";

        _floorLayer = loadCSV(basePath + _name + "_Floor.csv");
        _trapsLayer = loadCSV(basePath + _name + "_Traps.csv");
        _sideWallsLayer = loadCSV(basePath + _name + "_Side_walls.csv");
        _sideDoorsLayer = loadCSV(basePath + _name + "_Side_doors.csv");
        _frontWallsLayer = loadCSV(basePath + _name + "_Front_walls.csv");
        _frontDoorsLayer = loadCSV(basePath + _name + "_Front_doors.csv");
        _pillarLayer = loadCSV(basePath + _name + "_Pillar.csv");
        _chestsLayer = loadCSV(basePath + _name + "_Chests.csv");
        _decorationsLayer = loadCSV(basePath + _name + "_Decorations.csv");
        _collisionsLayer = loadCSV(basePath + _name + "_Collisions.csv");
    }

    private int[][] loadCSV(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Fichier non trouvé : " + filePath);
            return null;
        }

        try (Scanner scanner = new Scanner(file)) {
            int rows = 0, cols = 0;
            while (scanner.hasNextLine()) {
                String[] numbers = scanner.nextLine().split(",");
                cols = numbers.length;
                rows++;
            }
            scanner.close();

            int[][] tileGrid = new int[rows][cols];

            Scanner scanner2 = new Scanner(file);
            for (int i = 0; i < rows; i++) {
                String[] numbers = scanner2.nextLine().split(",");
                for (int j = 0; j < cols; j++) {
                    tileGrid[i][j] = Integer.parseInt(numbers[j].trim());
                }
            }
            scanner2.close();

            return tileGrid;

        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + filePath);
            return null;
        }
    }

    public void addDoor(String direction, String destination) {
        _doors.put(direction, destination);
    }

    public String getDoorDestination(String direction) {
        return _doors.get(direction);
    }

    public void setSpawnPoint(String direction, int x, int y) {
        _spawnPoints.put(direction, new int[]{x, y});
    }

    public int[] getSpawnPoint(String direction) {
        return _spawnPoints.getOrDefault(direction, new int[]{0, 0});
    }

    public int[][] getFloorLayer() {
        return _floorLayer;
    }

    public int[][] getTrapsLayer() {
        return _trapsLayer;
    }

    public int[][] getSideWallsLayer() {
        return _sideWallsLayer;
    }

    public int[][] getSideDoorsLayer() {
        return _sideDoorsLayer;
    }

    public int[][] getFrontWallsLayer() {
        return _frontWallsLayer;
    }

    public int[][] getFrontDoorsLayer() {
        return _frontDoorsLayer;
    }

    public int[][] getDecorationsLayer() {
        return _decorationsLayer;
    }

    public int[][] getPillarLayer() {
        return _pillarLayer;
    }

    public int[][] getChestsLayer() {
        return _chestsLayer;
    }

    public int[][] getCollisionsLayer() {
        return _collisionsLayer;
    }

    public String getName() {
        return _name;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Room Name: ").append(_name).append("\n");

        sb.append("Doors:\n");
        for (String direction : _doors.keySet()) {
            sb.append("  ").append(direction).append(" -> ").append(_doors.get(direction)).append("\n");
        }

        sb.append("Spawn Points:\n");
        for (String direction : _spawnPoints.keySet()) {
            int[] coords = _spawnPoints.get(direction);
            sb.append("  ").append(direction).append(" -> (").append(coords[0]).append(", ").append(coords[1]).append(")\n");
        }

        return sb.toString();
    }
    public void addMonster(Monster monster) {
        _monsters.add(monster);
    }

    public void removeMonster(Monster monster) {
        _monsters.remove(monster);
    }

    public void addBooster(ItemBooster booster) {
        _boosters.add(booster);
    }

    public void removeBooster(ItemBooster booster) {
        _boosters.remove(booster);
    }

    public void addChest(ArrayList<Item> contents) {
        Chest newChest = new Chest(contents); 
        _chests.add(newChest);

        System.out.println("📦 Nouveau coffre ajouté avec " + contents.size() + " objets");
        if (contents.isEmpty()) {
            System.out.println("⚠ Coffre vide.");
        } else {
            System.out.print("📍 Contenu : ");
            for (Item item : contents) {
                System.out.print(item.getName() + " ");
            }
            System.out.println();
        }
    }

    public ArrayList<Item> openChest(int chestIndex) {
        if (chestIndex >= 0 && chestIndex < _chests.size()) {
            Chest chest = _chests.remove(chestIndex); 
            return chest.getContents(); 
        }
        return null; 
    }

    public int getChestCount() {
        return _chests.size();
    }

    public void printChests() {
        System.out.println("📜 Liste des coffres dans " + _name + " :");
        for (int i = 0; i < _chests.size(); i++) {
            System.out.print("📦 Coffre " + (i + 1) + " contient : ");
            _chests.get(i).printContents();
        }
    }

    public void setHint(String hint) {
        _hint = hint;
    }

    public String getHint() {
        return _hint;
    }

    public ArrayList<Monster> getMonsters() {
        return new ArrayList<>(_monsters);
    }

    public ArrayList<ItemBooster> getBoosters() {
        return new ArrayList<>(_boosters);
    }


    public HashMap<int[], ItemBooster> getBoosterPositions() {
        HashMap<int[], ItemBooster> boosterPositions = new HashMap<>();

        for (ItemBooster booster : _boosters) {
            int[] position = new int[]{booster.getX(), booster.getY()};
            boosterPositions.put(position, booster);
        }

        return boosterPositions;
    }

    public ItemBooster pickUpBooster(int x, int y) {
        Iterator<ItemBooster> iterator = _boosters.iterator();
        while (iterator.hasNext()) {
            ItemBooster booster = iterator.next();
            if (booster.getX() == x && booster.getY() == y) {
                iterator.remove();  
                return booster;   
            }
        }
        return null; 
    }
    
    public void addWeapon(ItemWeapon weapon) {
        _weapons.add(weapon);
    }

    public void removeWeapon(ItemWeapon weapon) {
        _weapons.remove(weapon);
    }

    public ArrayList<ItemWeapon> getWeapons() {
        return new ArrayList<>(_weapons);
    }

    public HashMap<int[], ItemWeapon> getWeaponPositions() {
        HashMap<int[], ItemWeapon> weaponPositions = new HashMap<>();
        for (ItemWeapon weapon : _weapons) {
            int[] position = new int[]{weapon.getPositionX(), weapon.getPositionY()};
            weaponPositions.put(position, weapon);
        }
        return weaponPositions;
    }

    public ItemWeapon pickUpWeapon(int x, int y) {
        Iterator<ItemWeapon> iterator = _weapons.iterator();
        while (iterator.hasNext()) {
            ItemWeapon weapon = iterator.next();
            if (weapon.getPositionX() == x && weapon.getPositionY() == y) {
                iterator.remove(); 
                return weapon; 
            }
        }
        return null;
    }
}
