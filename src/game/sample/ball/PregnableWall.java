package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class PregnableWall extends ImageOnMap implements Serializable {
    int numOfBulletCollisions = 0;
    transient BufferedImage softwall;
    transient BufferedImage softwall1;
    transient BufferedImage softwall2;
    transient BufferedImage softwall3;
    transient BufferedImage softwall4;


    public PregnableWall(double centerTileX, double centerTileY) {
        super("softWall.png", centerTileX, centerTileY,true);
        try {
            softwall = ImageIO.read(new File("softWall.png"));
            softwall1 = ImageIO.read(new File("softWall1.png"));
            softwall2 = ImageIO.read(new File("softWall2.png"));
            softwall3 = ImageIO.read(new File("softWall3.png"));
            softwall4 = ImageIO.read(new File("softWall4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw (Graphics2D g2d, GameState state) {
        updateLocs(state);
        checkForCollisions(state);
        switch (numOfBulletCollisions) {
            case 0:
                g2d.drawImage(softwall, locX, locY, null);
                break;
            case 1:
                g2d.drawImage(softwall1, locX, locY, null);
                break;
            case 2:
                g2d.drawImage(softwall2, locX, locY, null);
                break;
            case 3:
                g2d.drawImage(softwall3, locX, locY, null);
                break;
            case 4:
                g2d.drawImage(softwall4, locX, locY, null);
                obstacle = false;
                break;
        }
    }
}
