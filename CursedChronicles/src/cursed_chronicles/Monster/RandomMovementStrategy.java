package cursed_chronicles.Monster;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RandomMovementStrategy implements MovementStrategy {
    private final Random random = new Random();
    private final int stepSize = 8;  
    private final int baseMoveSpeed = 250;
    private final int pauseTime = 1000;  

    @Override
    public void move(Monster monster, int playerX, int playerY) {
        if (monster.isMoving()) return;  

        int moveDistance = (random.nextInt(3) + 2) * 48;  // Déplacement de 2, 3 ou 4 cases
        int dx = 0, dy = 0;
        String newDirection = "NONE";

        switch (random.nextInt(4)) { 
            case 0: dx = 1; newDirection = "right"; break;
            case 1: dx = -1; newDirection = "left"; break;
            case 2: dy = 1; newDirection = "down"; break;
            case 3: dy = -1; newDirection = "up"; break;
        }

        int finalDx = dx, finalDy = dy;
        String finalDirection = newDirection;
        monster.setDirection(finalDirection);
        monster.setMoving(true);

        Timer movementTimer = new Timer(baseMoveSpeed / 10, new ActionListener() {
            int steps = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (steps < 6 * (moveDistance / 48)) { 
                    int newX = monster.getPositionX() + finalDx;
                    int newY = monster.getPositionY() + finalDy;

                    if (detectCollision(newX, newY)) {
                        ((Timer) e.getSource()).stop();
                        monster.setMoving(false);
                        startPauseBeforeNextMove(monster);
                        return;
                    }

                    monster.setPositionX(newX);
                    monster.setPositionY(newY);
                    steps++;
                } else {
                    ((Timer) e.getSource()).stop();
                    monster.setMoving(false);
                    startPauseBeforeNextMove(monster);
                }
            }
        });

        movementTimer.start();
    }

    private boolean detectCollision(int x, int y) {
        // Vérifier si (x, y) est un mur (à connecter avec la carte)
        return false;
    }

    private void startPauseBeforeNextMove(Monster monster) {
        Timer pauseTimer = new Timer(pauseTime, e -> {
            ((Timer) e.getSource()).stop();
            move(monster, 0, 0);
        });
        pauseTimer.setRepeats(false);
        pauseTimer.start();
    }
}
