package game.sample.ball;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Prize extends ImageOnMap {

    public double angle = 0;
    boolean usable;

    public Prize(String imageName, double centerTileX, double centerTileY) {
        super(imageName, centerTileX, centerTileY,false);
        usable = true;
    }

    public void draw (Graphics2D g2d) {
        updateLocs();
        angle += (0.1047);
        g2d.drawImage(GameFrame.rotatePic(img,angle), locX, locY, null);
    }

    public void checkCollisionWithTank () {

    }
}
