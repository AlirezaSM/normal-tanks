package game.sample.ball;

import java.awt.*;
import java.util.Random;

public class MovingEnemy extends Enemy {


    public MovingEnemy(String imageName, double centerTileX, double centerTileY, int width, int height, double speed, double firingSpeed, int health) {
        super(imageName, centerTileX, centerTileY, width, height, speed, firingSpeed, health);
    }

    public void move(Graphics2D g2d) {
        if (triggered) {
            movingAngle = Math.atan2((locY - GameState.tankCenterY), (locX - GameState.tankCenterX));
            centerTileX = (centerTileX - speed * Math.cos(movingAngle));
            centerTileY = (centerTileY + speed * Math.sin(movingAngle));
            firingBullet(20);
            updateLocs();
        }
    }
}

