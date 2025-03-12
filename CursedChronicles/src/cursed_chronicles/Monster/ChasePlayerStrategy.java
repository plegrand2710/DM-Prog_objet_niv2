package cursed_chronicles.Monster;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChasePlayerStrategy implements MovementStrategy {
    private final int stepSize = 8;
    private final int baseMoveSpeed = 250;

    @Override
    public void move(Monster monster, int playerX, int playerY) {
        if (monster.isMoving()) return; 

        int dx = Integer.compare(playerX, monster.getPositionX());
        int dy = Integer.compare(playerY, monster.getPositionY());

        int targetX = monster.getPositionX() + dx;
        int targetY = monster.getPositionY() + dy;

        if (detectCollision(targetX, targetY)) return;

        monster.setMoving(true);
        Timer movementTimer = new Timer(baseMoveSpeed / 10, new ActionListener() {
            int steps = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (steps < 6) {
                    monster.setPositionX(monster.getPositionX() + dx * stepSize);
                    monster.setPositionY(monster.getPositionY() + dy * stepSize);
                    steps++;
                } else {
                    ((Timer) e.getSource()).stop();
                    monster.setPositionX(targetX);
                    monster.setPositionY(targetY);
                    monster.setMoving(false);
                }
            }
        });

        movementTimer.start();
    }

    private boolean detectCollision(int x, int y) {
        return false; 
    }
}
