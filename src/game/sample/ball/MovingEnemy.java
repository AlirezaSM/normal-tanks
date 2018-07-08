package game.sample.ball;

import java.awt.*;
import java.util.Random;

public class MovingEnemy extends Enemy {


    public MovingEnemy(String imageName, double centerTileX, double centerTileY, double speed, double firingSpeed) {
        super(imageName, centerTileX, centerTileY, speed, firingSpeed);
    }

    public void move(Graphics2D g2d) {
        if (triggered) {
            updateLocs();
            movingAngle = Math.atan2((locY - GameState.tankCenterY), (locX - GameState.tankCenterX));
            centerTileX = (centerTileX - speed * Math.cos(movingAngle));
            centerTileY = (centerTileY + speed * Math.sin(movingAngle));
            firingBullet(20,g2d);
            updateBulletsState(g2d);
        }
    }
}

