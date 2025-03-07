package cursed_chronicles.Map;

import java.util.List;

public class MapController {
    private Map _map;

    public MapController(Map map) {
        _map = map;
    }

    public Map getMapModel() {
        return _map;
    }

    public Dungeon getDungeonByName(String name) {
        List<Dungeon> dungeons = _map.getDungeons();
        for (Dungeon dungeon : dungeons) {
            if (dungeon.getName().equals(name)) {
                return dungeon;
            }
        }
        return null;
    }
}

