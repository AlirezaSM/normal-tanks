package game.sample.ball;

import java.awt.*;
import java.io.Serializable;

public class PregnableWall extends ImageOnMap implements Serializable {
    int numOfBulletCollisions = 0;

    public PregnableWall(double centerTileX, double centerTileY) {
        super("softWall.png", centerTileX, centerTileY,true);
    }

    public void draw (Graphics2D g2d, GameState state) {
        updateLocs(state);
        checkForCollisions(state);
        switch (numOfBulletCollisions) {
            case 0:
                this.setImg("softWall.png");
                g2d.drawImage(img, locX, locY, null);
                break;
            case 1:
                this.setImg("softWall1.png");
                g2d.drawImage(img, locX, locY, null);
                break;
            case 2:
                this.setImg("softWall2.png");
                g2d.drawImage(img, locX, locY, null);
                break;
            case 3:
                this.setImg("softWall3.png");
                g2d.drawImage(img, locX, locY, null);
                break;
            case 4:
                this.setImg("softWall4.png");
                g2d.drawImage(img, locX, locY, null);
                obstacle = false;
                break;
        }
    }
}
