package cursed_chronicles.Monster;

import java.util.Random;

public class RandomMovementStrategy implements MovementStrategy {
    private Random random = new Random();

    @Override
    public void move(Monster monster, int playerX, int playerY) {
        int randomX = random.nextInt(3) - 1; // -1, 0 ou 1
        int randomY = random.nextInt(3) - 1; // -1, 0 ou 1

        monster.setPositionX(monster.getPositionX() + randomX);
        monster.setPositionY(monster.getPositionY() + randomY);
    }
}
