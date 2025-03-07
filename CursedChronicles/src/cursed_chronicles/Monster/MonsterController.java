package cursed_chronicles.Monster;

import java.awt.event.*;

public class MonsterController {
    private Monster monster;

    public MonsterController(Monster monster) {
        this.monster = monster;
    }

    public void updateMonsterPosition(int playerX, int playerY) {
        monster.move(playerX, playerY);
    }
}
