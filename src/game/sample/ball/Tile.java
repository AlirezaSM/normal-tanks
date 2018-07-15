package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * tiles are the forming unit of maps.
 * it's a 24*32 image. a lot of these tiles
 * would eventually form a map.
 */

public class Tile {
    private int imgNum;
    // tile height
    public static final int tileHeight = 24;
    public static final int tileWidth = 32;
    // 40
    public static final int numOfHorizontalTiles = (Map.screenHeight * 16) / (9 * tileWidth);
    // 150
    public static final int numOfVerticalTiles = (Map.screenHeight * Map.numOfVerticalScreens) / (tileHeight);
    // 5
    public static final int numOfVerticalTilesInOneScreen = Map.screenHeight / tileHeight;
    // if it's obstacle or not.
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
