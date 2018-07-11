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
    // upper left location
    int locX;
    int locY;

    int startTile;
    public Rectangle imgRectangle;
    boolean obstacle;

    public ImageOnMap(String imageName, double centerTileX, double centerTileY,boolean obstacle) {
        try {
            img = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.centerTileX = centerTileX;
        this.centerTileY = centerTileY;
        width = img.getWidth();
        height = img.getHeight();
        startTile = GameState.cameraY / Tile.tileHeight;
        locX = (int) (centerTileX * Tile.tileWidth) - (width / 2);
        locY = (int) (Map.screenHeight - (centerTileY - startTile) * Tile.tileHeight) - (height / 2);
        imgRectangle = new Rectangle(locX,locY,width,height);
        this.obstacle = obstacle;
    }

    public void draw (Graphics2D g2d) {
        updateLocs();
        checkForCollisions();
        g2d.drawImage(img, locX, locY, null);
    }

    public void updateLocs () {
        startTile = GameState.cameraY / Tile.tileHeight;
        locX = (int) (centerTileX * Tile.tileWidth) - (width / 2);
        locY = (int) (Map.screenHeight - (centerTileY - startTile) * Tile.tileHeight) - (height / 2);
        imgRectangle = new Rectangle(locX,locY,width,height);
    }

    public void checkForCollisions () {
        if (GameState.mainTankRectangle().intersects(imgRectangle) && obstacle) {
            if (GameState.tankDirection == GameState.Right) {
                GameState.tankCenterX -= 8;
            }
            if (GameState.tankDirection == GameState.LEFT) {
                GameState.tankCenterX += 8;
            }
            if (GameState.tankDirection == GameState.UP) {
                GameState.tankCenterY += 8;
            }
            if (GameState.tankDirection == GameState.DOWN) {
                GameState.tankCenterY -= 8;
            }
        }
    }

    public void setImg(String imgName) {
        try {
            img = ImageIO.read(new File(imgName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
