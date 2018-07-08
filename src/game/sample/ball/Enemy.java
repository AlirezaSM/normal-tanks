package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
    BufferedImage enemyImg;
    // speed is tile per frame
    public  double speed;
    // firing speed is bullet per second
    public double firingSpeed;
    double centerTileX;
    double centerTileY;
    int locX;
    int locY;
    int startTile;
    public double movingAngle = 1;
    boolean visible;
    boolean triggered;
    ArrayList<Bullet> bullets = new ArrayList<>();
    int i = 0;

    public Enemy(String imageName, double centerTileX, double centerTileY, double speed, double firingSpeed) {
        try {
            enemyImg = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.centerTileX = centerTileX;
        this.centerTileY = centerTileY;
        locX = (int) centerTileX * Tile.tileWidth;
        locY = Map.screenHeight - ((int) centerTileY - startTile) * Tile.tileHeight;
        startTile = GameState.cameraY / Tile.tileHeight;
        this.speed = speed;
        this.firingSpeed = firingSpeed;
        visible = false;
        triggered = false;
    }

    public void checkTriggered () {
        if (Math.abs(GameState.tankCenterTileY - centerTileY) < 15)
            triggered = true;
    }

    public void draw (Graphics2D g2d) {
        g2d.drawImage(enemyImg,locX,locY,null);
        checkTriggered();
    }

    public void firingBullet (int distanceBetweenFiringPointAndCenter,Graphics2D g2d) {
        Random r = new Random();
        if (r.nextInt((int) (60 / firingSpeed)) == 1) {
            int firingLocX = (int) (locX + distanceBetweenFiringPointAndCenter * Math.cos(movingAngle));
            int firingLocY = (int) (locY + distanceBetweenFiringPointAndCenter * Math.sin(movingAngle));
            bullets.add(new Bullet(firingLocX, firingLocY, movingAngle));
            i++;
            System.out.println(i);
        }
    }

    public void checkVisibility () {
        int endTile = startTile + (Tile.numOfVerticalTiles / Map.numOfVerticalScreens);
        if (centerTileY >= startTile && centerTileY <= endTile)
            visible = true;
    }

    public void updateLocs () {
        locX = (int) centerTileX * Tile.tileWidth;
        locY = Map.screenHeight - ((int) centerTileY - startTile) * Tile.tileHeight;
        startTile = GameState.cameraY / Tile.tileHeight;
    }

    public void updateBulletsState (Graphics2D g2d) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).fire(g2d,true);
            if (bullets.get(i).isRemoved())
                bullets.remove(i);
        }
    }


}
