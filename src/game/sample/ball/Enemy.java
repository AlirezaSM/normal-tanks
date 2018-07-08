package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
    BufferedImage enemyImg;
    double centerTileX;
    double centerTileY;
    boolean visible;
    boolean triggered;

    public Enemy(String imageName, double centerTileX, double centerTileY) {
        try {
            enemyImg = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.centerTileX = centerTileX;
        this.centerTileY = centerTileY;
        visible = false;
        triggered = false;
    }

    public void checkTriggered () {
        if (Math.abs(GameState.tankCenterTileY - centerTileY) < 15)
            triggered = true;
    }

    public void draw (Graphics2D g2d) {
        int startTile = GameState.cameraY / Tile.tileHeight  ;
        int endTile = startTile + (Tile.numOfVerticalTiles / Map.numOfVerticalScreens);
        int locX = (int) centerTileX * Tile.tileWidth;
        int locY = Map.screenHeight - ((int) centerTileY - startTile) * Tile.tileHeight;
        g2d.drawImage(enemyImg,locX,locY,null);
        checkTriggered();
    }

    public void checkVisibility () {
        int startTile = GameState.cameraY / Tile.tileHeight  ;
        int endTile = startTile + (Tile.numOfVerticalTiles / Map.numOfVerticalScreens);
        if (centerTileY >= startTile && centerTileY <= endTile)
            visible = true;
    }


}
