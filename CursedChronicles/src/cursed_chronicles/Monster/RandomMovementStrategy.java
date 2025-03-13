package cursed_chronicles.Monster;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import cursed_chronicles.Map.Room;
import cursed_chronicles.Constant;

public class RandomMovementStrategy implements MovementStrategy {
    private final Random random = new Random();
    private final int baseMoveSpeed = 1550; 
    private final int pauseTime = 500; 

    @Override
    public void move(Monster monster, int playerX, int playerY, Room room) {
        if (monster.isMoving()) return;  
        if (room == null) return; 

        int[][] collisions = room.getCollisionsLayer();
        if (collisions == null) return; 

        int[] direction = chooseNewDirection(monster, room);
        int dx = direction[0];
        int dy = direction[1];

        int targetX = monster.getPositionX() + dx;
        int targetY = monster.getPositionY() + dy;

        if (!isValidMove(targetX, targetY, room)) {
            startPauseBeforeNextMove(monster, room);
            return;
        }

        monster.setMoving(true);

        Timer movementTimer = new Timer(baseMoveSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monster.setPositionX(monster.getPositionX() + dx);
                monster.setPositionY(monster.getPositionY() + dy);
                ((Timer) e.getSource()).stop();
                monster.setMoving(false);
                startPauseBeforeNextMove(monster, room);
            }
        });

        movementTimer.setRepeats(false);
        movementTimer.start();
    }

    private int[] chooseNewDirection(Monster monster, Room room) {
        int[][] collisions = room.getCollisionsLayer();
        int x = monster.getPositionX();
        int y = monster.getPositionY();

        int[][] possibleDirections = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1} 
        };

        for (int i = 0; i < possibleDirections.length; i++) {
            int swapIndex = random.nextInt(possibleDirections.length);
            int[] temp = possibleDirections[i];
            possibleDirections[i] = possibleDirections[swapIndex];
            possibleDirections[swapIndex] = temp;
        }

        for (int[] dir : possibleDirections) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (isValidMove(newX, newY, room)) {
                return dir;
            }
        }

        return new int[]{0, 0}; 
    }

    private boolean isValidMove(int x, int y, Room room) {
        int[][] collisions = room.getCollisionsLayer();
        if (collisions == null) return false;

        if (x < 0 || y < 0 || x >= collisions[0].length || y >= collisions.length) {
            return false; 
        }

        return collisions[y][x] != Constant.WALL_COLLISION_ID; 
    }

    private void startPauseBeforeNextMove(Monster monster, Room room) {
        Timer pauseTimer = new Timer(pauseTime, e -> {
            ((Timer) e.getSource()).stop();
            move(monster, 0, 0, room);
        });
        pauseTimer.setRepeats(false);
        pauseTimer.start();
    }
}