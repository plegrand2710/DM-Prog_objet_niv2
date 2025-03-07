package cursed_chronicles.Monster;

public class ChasePlayerStrategy implements MovementStrategy {
    @Override
    public void move(Monster monster, int playerX, int playerY) {
        int deltaX = Integer.compare(playerX, monster.getPositionX());
        int deltaY = Integer.compare(playerY, monster.getPositionY());

        monster.setPositionX(monster.getPositionX() + deltaX);
        monster.setPositionY(monster.getPositionY() + deltaY);
    }
}
