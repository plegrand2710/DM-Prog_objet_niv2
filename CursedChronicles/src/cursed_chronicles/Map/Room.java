package cursed_chronicles.Map;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

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

    public Room(String name) {
        _name = name;
        _doors = new HashMap<>();
        _spawnPoints = new HashMap<>();
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


}
