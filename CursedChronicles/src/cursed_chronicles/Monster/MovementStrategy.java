package cursed_chronicles.Monster;

import cursed_chronicles.Map.Room;

public interface MovementStrategy {
    void move(Monster monster, int playerX, int playerY, Room room);
}