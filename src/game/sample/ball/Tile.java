package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tile {
    private int imgNum;
    public static final int tileHeight = 24;
    public static final int tileWidth = 32;
    public static final int numOfHorizontalTiles = (Map.screenHeight * 16) / (9 * tileWidth);
    public static final int numOfVerticalTiles = (Map.screenHeight * Map.numOfVerticalScreens) / (tileHeight);
    public static final int numOfVerticalTilesInOneScreen = Map.screenHeight / tileHeight;
    private boolean obstacle;

    public Tile(int imgNum,boolean obstacle) {
        this.obstacle = obstacle;
        this.imgNum = imgNum;
    }

    public int getImgNum() {
        return imgNum;
    }

    public boolean isObstacle() {
        return obstacle;
    }
}
