package game.sample.ball;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Prize extends ImageOnMap implements Serializable {

    public transient double angle = 0;
    boolean usable;

    public Prize(String imageName, double centerTileX, double centerTileY) {
        super(imageName, centerTileX, centerTileY,false);
        usable = true;
    }

    public void draw (Graphics2D g2d, GameState state) {
        if (usable) {
            updateLocs(state);
            angle += (0.1047);
            g2d.drawImage(GameFrame.rotatePic(img, angle), locX, locY, null);
        }
    }

    public void checkCollisionWithTank (GameState state, Map map) {

    }
}
