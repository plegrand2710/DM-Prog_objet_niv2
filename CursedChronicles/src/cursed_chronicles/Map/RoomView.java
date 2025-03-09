package cursed_chronicles.Map;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class RoomView extends JLayeredPane {
    private static final long serialVersionUID = 1L;
	private JPanel _floorLayer;
    private JPanel _trapsLayer;
    private JPanel _sideWallsLayer;
    private JPanel _sideDoorsLayer;
    private JPanel _frontWallsLayer;
    private JPanel _frontDoorsLayer;
    private JPanel _chestsLayer;
    private JPanel _decorationsLayer; // Nouveau layer pour les décorations
    private JPanel _pillarLayer;

    private final int _tileSize = 16;
    private final int _scaleFactor = 3;
    private final int _displayTileSize = _tileSize * _scaleFactor;

    public RoomView() {
        setPreferredSize(new Dimension(16 * _displayTileSize, 16 * _displayTileSize));
    }

    public void displayRoom(Room room, String tilesetBasePath) {
        removeAll(); // Supprime les anciens calques si existants

        // Création et ajout des calques avec leur Z-index
        _floorLayer = createLayerPanel(room.getFloorLayer(), tilesetBasePath + "floor/", 0);
        _trapsLayer = createLayerPanel(room.getTrapsLayer(), tilesetBasePath + "traps/", 1);
        _sideWallsLayer = createLayerPanel(room.getSideWallsLayer(), tilesetBasePath + "side_walls/", 2);
        _sideDoorsLayer = createLayerPanel(room.getSideDoorsLayer(), tilesetBasePath + "side_doors/", 3);
        _frontWallsLayer = createLayerPanel(room.getFrontWallsLayer(), tilesetBasePath + "front_walls/", 4);
        _frontDoorsLayer = createLayerPanel(room.getFrontDoorsLayer(), tilesetBasePath + "front_doors/", 5);
        _chestsLayer = createLayerPanel(room.getChestsLayer(), tilesetBasePath + "chests/", 6);
        _pillarLayer = createLayerPanel(room.getPillarLayer(), tilesetBasePath + "pillars/", 7);
        _decorationsLayer = createLayerPanel(room.getDecorationsLayer(), tilesetBasePath + "decorations/", 8);

        add(_floorLayer, Integer.valueOf(0));
        add(_trapsLayer, Integer.valueOf(1));
        add(_sideWallsLayer, Integer.valueOf(2));
        add(_sideDoorsLayer, Integer.valueOf(3));
        add(_frontWallsLayer, Integer.valueOf(4));
        add(_frontDoorsLayer, Integer.valueOf(5));
        add(_chestsLayer, Integer.valueOf(6));
        add(_pillarLayer, Integer.valueOf(7));
        add(_decorationsLayer, Integer.valueOf(8));

        repaint();
    }

    private JPanel createLayerPanel(int[][] layerGrid, String tilesetPath, int zIndex) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawLayer(g, layerGrid, tilesetPath);
            }
        };
        panel.setOpaque(false);
        panel.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
        return panel;
    }

    private void drawLayer(Graphics g, int[][] layerGrid, String tilesetPath) {
        if (layerGrid == null) return;

        Map<Integer, Image> tileImages = loadImagesFromFolder(tilesetPath);
        for (int i = 0; i < layerGrid.length; i++) {
            for (int j = 0; j < layerGrid[i].length; j++) {
                int tileNumber = layerGrid[i][j];
                if (tileNumber < 0) continue;

                Image tileImage = tileImages.get(tileNumber);
                if (tileImage != null) {
                    g.drawImage(tileImage, j * _displayTileSize, i * _displayTileSize, _displayTileSize, _displayTileSize, this);
                }
            }
        }
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
}
