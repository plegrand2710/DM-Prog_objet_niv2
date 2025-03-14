package cursed_chronicles.Monster;

import javax.swing.*;

import cursed_chronicles.Map.Room;
import cursed_chronicles.Constant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChasePlayerStrategy implements MovementStrategy {
    private final int baseMoveSpeed = 500; 
    private final int pauseTime = 200; 

    @Override
    public void move(Monster monster, int playerX, int playerY, Room room) {
        if (monster.isMoving()) return;
        if (room == null) return;

        int[][] collisions = room.getCollisionsLayer();
        if (collisions == null) return;

        int dx = Integer.compare(playerX, monster.getPositionX());
        int dy = Integer.compare(playerY, monster.getPositionY());

        int targetX = monster.getPositionX() + dx;
        int targetY = monster.getPositionY() + dy;

        if (!isValidMove(targetX, targetY, room)) {
            startPauseBeforeNextMove(monster, playerX, playerY, room);
            return;
        }

        monster.setMoving(true);

        Timer movementTimer = new Timer(baseMoveSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monster.setPositionX(targetX);
                monster.setPositionY(targetY);
                ((Timer) e.getSource()).stop();
                monster.setMoving(false);
                startPauseBeforeNextMove(monster, playerX, playerY, room);
            }
        });

        movementTimer.setRepeats(false);
        movementTimer.start();
    }

    private boolean isValidMove(int x, int y, Room room) {
        int[][] collisions = room.getCollisionsLayer();
        if (collisions == null) return false;

        if (x < 0 || y < 0 || x >= collisions[0].length || y >= collisions.length) {
            return false; 
        }

        return collisions[y][x] != Constant.WALL_COLLISION_ID; 
    }

    private void startPauseBeforeNextMove(Monster monster, int playerX, int playerY, Room room) {
        Timer pauseTimer = new Timer(pauseTime, e -> {
            ((Timer) e.getSource()).stop();
            move(monster, playerX, playerY, room);
        });
        pauseTimer.setRepeats(false);
        pauseTimer.start();
    }
}