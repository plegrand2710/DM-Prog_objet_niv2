package cursed_chronicles.Map;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class RoomView extends JPanel {
    private int[][] _floorGrid;
    private int[][] _trapsGrid;
    private int[][] _sideWallsGrid;
    private int[][] _sideDoorsGrid;
    private int[][] _frontWallsGrid;
    private int[][] _frontDoorsGrid;
    private int[][] _pillarGrid;

    private Map<Integer, Image> _floorImages;
    private Map<Integer, Image> _trapsImages;
    private Map<Integer, Image> _sideWallsImages;
    private Map<Integer, Image> _sideDoorsImages;
    private Map<Integer, Image> _frontWallsImages;
    private Map<Integer, Image> _frontDoorsImages;
    private Map<Integer, Image> _pillarImages;

    private final int _tileSize = 16; // Taille réelle des tuiles en pixels
    private final int _scaleFactor = 3; // Facteur d’agrandissement des tuiles
    private final int _displayTileSize = _tileSize * _scaleFactor; // Taille finale des tuiles affichées

    public RoomView() {
        setPreferredSize(new Dimension(16 * _displayTileSize, 16 * _displayTileSize)); // Nouvelle taille de la fenêtre
    }

    public void displayRoom(Room room, String tilesetBasePath) {
        _floorGrid = room.getFloorLayer();
        _trapsGrid = room.getTrapsLayer();
        _sideWallsGrid = room.getSideWallsLayer();
        _sideDoorsGrid = room.getSideDoorsLayer();
        _frontWallsGrid = room.getFrontWallsLayer();
        _frontDoorsGrid = room.getFrontDoorsLayer();
        _pillarGrid = room.getPillarLayer();

        loadTileImages(tilesetBasePath);
        repaint();
    }

    private void loadTileImages(String tilesetBasePath) {
        _floorImages = loadImagesFromFolder(tilesetBasePath + "floor/");
        _trapsImages = loadImagesFromFolder(tilesetBasePath + "traps/");
        _sideWallsImages = loadImagesFromFolder(tilesetBasePath + "side_walls/");
        _sideDoorsImages = loadImagesFromFolder(tilesetBasePath + "side_doors/");
        _frontWallsImages = loadImagesFromFolder(tilesetBasePath + "front_walls/");
        _frontDoorsImages = loadImagesFromFolder(tilesetBasePath + "front_doors/");
        _pillarImages = loadImagesFromFolder(tilesetBasePath + "pillar/");
    }

    private Map<Integer, Image> loadImagesFromFolder(String folderPath) {
        Map<Integer, Image> tileMap = new HashMap<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".png")) {
                    try {
                        int tileNumber = Integer.parseInt(file.getName().replace(".png", ""));
                        Image originalImage = ImageIO.read(file);
                        Image scaledImage = originalImage.getScaledInstance(_displayTileSize, _displayTileSize, Image.SCALE_SMOOTH);
                        tileMap.put(tileNumber, scaledImage);
                    } catch (Exception e) {
                        System.err.println("Erreur chargement image : " + file.getName());
                    }
                }
            }
        }
        return tileMap;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLayer(g, _floorGrid, _floorImages);
        drawLayer(g, _trapsGrid, _trapsImages);
        drawLayer(g, _sideWallsGrid, _sideWallsImages);
        drawLayer(g, _sideDoorsGrid, _sideDoorsImages);
        drawLayer(g, _frontWallsGrid, _frontWallsImages);
        drawLayer(g, _frontDoorsGrid, _frontDoorsImages);
        drawLayer(g, _pillarGrid, _pillarImages);
    }

    private void drawLayer(Graphics g, int[][] layerGrid, Map<Integer, Image> tileImages) {
        if (layerGrid == null || tileImages == null) return;

        for (int i = 0; i < layerGrid.length; i++) {
            for (int j = 0; j < layerGrid[i].length; j++) {
                int tileNumber = layerGrid[i][j];

                // Ne pas dessiner les cases ayant la valeur -1
                if (tileNumber < 0) continue;

                Image tileImage = tileImages.get(tileNumber);

                if (tileImage != null) {
                    g.drawImage(tileImage, j * _displayTileSize, i * _displayTileSize, this);
                }
            }
        }
    }
}
