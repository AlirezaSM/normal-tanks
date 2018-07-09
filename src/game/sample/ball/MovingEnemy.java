package game.sample.ball;

import java.awt.*;
import java.util.Random;

public class MovingEnemy extends Enemy {


    public MovingEnemy(String imageName, double centerTileX, double centerTileY, int width, int height, double speed, double firingSpeed, int health) {
        super(imageName, centerTileX, centerTileY, width, height, speed, firingSpeed, health);
    }


}

