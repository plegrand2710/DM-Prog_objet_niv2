package cursed_chronicles.Map;

import java.util.List;

public class Map {
    private List<Dungeon> _dungeons;

    public Map(List<Dungeon> dungeons) {
        _dungeons = dungeons;
    }

    public List<Dungeon> getDungeons() {
        return _dungeons;
    }
}
