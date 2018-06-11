package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tile {
    private int img;
    public static final int tileHeight = 24;
    public static final int tileWidth = 32;
    public static final int numOfHorizontalTiles = (720 * 16) / (9 * tileWidth);
    public static final int numOfVerticalTiles = (720 * Map.numOfVerticalScreens) / (tileHeight);
    private boolean obstacle;

    public Tile(int img,boolean obstacle) {
        this.obstacle = obstacle;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }
}
