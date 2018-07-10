package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageOnMap {
    BufferedImage img;
    double centerTileX;
    double centerTileY;
    int width;
    int height;
    int locX;
    int locY;
    int startTile;

    public ImageOnMap(String imageName, double centerTileX, double centerTileY) {
        try {
            img = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.centerTileX = centerTileX;
        this.centerTileY = centerTileY;
        locX = (int) (centerTileX * Tile.tileWidth) + (width / 2);
        locY = (int) (Map.screenHeight - (centerTileY - startTile) * Tile.tileHeight) + (height / 2);
        startTile = GameState.cameraY / Tile.tileHeight;
    }

    public void draw (Graphics2D g2d) {
        updateLocs();
        g2d.drawImage(img, locX, locY, null);
    }

    public void updateLocs () {
        startTile = GameState.cameraY / Tile.tileHeight;
        locX = (int) (centerTileX * Tile.tileWidth) + (width / 2);
        locY = (int) (Map.screenHeight - (centerTileY - startTile) * Tile.tileHeight) + (height / 2);
    }
}
