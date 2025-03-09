package cursed_chronicles.Map;

import java.io.File;
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

    public Room(String name) {
        _name = name;
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

    public int[][] getPillarLayer() {
        return _pillarLayer;
    }

    public String getName() {
        return _name;
    }
}
