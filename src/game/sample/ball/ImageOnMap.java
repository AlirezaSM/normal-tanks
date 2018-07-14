package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class ImageOnMap implements Serializable {
    transient BufferedImage img;
    double centerTileX;
    double centerTileY;
    transient int width;
    transient int height;
    // upper left location
    transient int locX;
    transient int locY;

    transient int startTile;
    public transient Rectangle imgRectangle;
    transient boolean obstacle;

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
        startTile = 0;
        locX = (int) (centerTileX * Tile.tileWidth) - (width / 2);
        locY = (int) (Map.screenHeight - (centerTileY - startTile) * Tile.tileHeight) - (height / 2);
        imgRectangle = new Rectangle(locX,locY,width,height);
        this.obstacle = obstacle;
    }

    public void draw (Graphics2D g2d, GameState state) {
        updateLocs(state);
        checkForCollisions(state);
        g2d.drawImage(img, locX, locY, null);
    }

    public void updateLocs (GameState state) {
        startTile = state.cameraY / Tile.tileHeight;
        locX = (int) (centerTileX * Tile.tileWidth) - (width / 2);
        locY = (int) (Map.screenHeight - (centerTileY - startTile) * Tile.tileHeight) - (height / 2);
        imgRectangle = new Rectangle(locX,locY,width,height);
    }

    public void checkForCollisions (GameState state) {
        if (state.mainTankRectangle.intersects(imgRectangle) && obstacle) {
            if (state.tankDirection == GameState.Right) {
                state.tankCenterX -= GameState.EACHMOVE;
            }
            if (state.tankDirection == GameState.LEFT) {
                state.tankCenterX += GameState.EACHMOVE;
            }
            if (state.tankDirection == GameState.UP) {
                state.tankCenterY += GameState.EACHMOVE;
            }
            if (state.tankDirection == GameState.DOWN) {
                state.tankCenterY -= GameState.EACHMOVE;
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

    public void setImg(BufferedImage img) {
        this.img = img;
    }
}
