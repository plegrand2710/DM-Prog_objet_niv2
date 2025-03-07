package cursed_chronicles.Map;

import org.newdawn.slick.tiled.TiledMap;

import java.util.HashMap;
import java.util.Map;

public class Room {
    private String _filePath;
    private Map<String, Door> _doors;
    private TiledMap _tiledMap;

    public Room(String filePath) {
        this._filePath = filePath;
        _doors = new HashMap<>();
        loadTiledMap();
        initDoors();
    }

    public String getFilePath() {
        return _filePath;
    }

    public Door getDoor(String direction) {
        return _doors.get(direction);
    }
    
    public TiledMap getTiledMap() {
        return _tiledMap;
    }
    
    private void loadTiledMap() {
        try {
            _tiledMap = new TiledMap(_filePath);
            Map<String, Door> doors;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initDoors() {
//        int objectLayerIndex = _tiledMap.getObjectGroupCount(); // Nombre de calques objets
//        
//        for (int i = 0; i < objectLayerIndex; i++) {
//            int objectCount = _tiledMap.getObjectCount(i);
//
//            for (int j = 0; j < objectCount; j++) {
//                String type = _tiledMap.getObjectType(i, j);
//                
//                if ("door".equals(type)) {
//                    String direction = _tiledMap.getObjectProperty(i, j, "direction", "unknown");
//                    String destinationRoom = _tiledMap.getObjectProperty(i, j, "destination", null);
//                    
//                    if (destinationRoom != null) {
//                        _doors.put(direction, new Door(direction, destinationRoom));
//                    }
//                }
//            }
//        }
    }
}

