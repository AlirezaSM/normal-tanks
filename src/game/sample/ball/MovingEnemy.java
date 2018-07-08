package game.sample.ball;

import java.awt.*;
import java.util.Random;

public class MovingEnemy extends Enemy {

    // speed is tile per frame
    public static final double speed = 0.1;
    public static double movingAngle = 1;

    public MovingEnemy(String imageName, double centerTileX, double centerTileY) {
        super(imageName, centerTileX, centerTileY);
    }

    public void move() {
        if (triggered) {
            int startTile = GameState.cameraY / Tile.tileHeight;
            int locX = (int) centerTileX * Tile.tileWidth;
            int locY = Map.screenHeight - ((int) centerTileY - startTile) * Tile.tileHeight;

            movingAngle = Math.atan2((locY - GameState.tankCenterY), (locX - GameState.tankCenterX));

            centerTileX = (centerTileX - speed * Math.cos(movingAngle));
            centerTileY = (centerTileY + speed * Math.sin(movingAngle));
        }
    }
}

