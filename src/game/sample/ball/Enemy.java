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
    int enemyWidth;
    int enemyHeight;
    int locX;
    int locY;
    int startTile;
    public double movingAngle = 1;
    boolean visible;
    boolean triggered;
    boolean alive = true;
    int health = 100;
    Rectangle enemyRectangle;
    int i = 0;

    public Enemy(String imageName, double centerTileX, double centerTileY,int width,int height, double speed, double firingSpeed, int health) {
        try {
            enemyImg = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.centerTileX = centerTileX;
        this.centerTileY = centerTileY;
        enemyWidth = width;
        enemyHeight = height;
        locX = (int) centerTileX * Tile.tileWidth;
        locY = Map.screenHeight - ((int) centerTileY - startTile) * Tile.tileHeight;
        startTile = GameState.cameraY / Tile.tileHeight;
        this.speed = speed;
        this.firingSpeed = firingSpeed;
        visible = false;
        triggered = false;
        this.health = health;
    }

    public void checkTriggered () {
        if (Math.abs(GameState.tankCenterTileY - centerTileY) < 30)
            triggered = true;
    }

    public void draw (Graphics2D g2d) {
        if (alive) {
            g2d.drawImage(enemyImg, locX, locY, null);
            checkTriggered();
        }
    }

    public void move(Graphics2D g2d) {
        if (triggered) {
            movingAngle = Math.atan2((locY - GameState.tankCenterY), (locX - GameState.tankCenterX));
            centerTileX = (centerTileX - speed * Math.cos(movingAngle));
            centerTileY = (centerTileY + speed * Math.sin(movingAngle));
            firingBullet(20);
            updateLocs();
        }
    }

    public void firingBullet (int distanceBetweenFiringPointAndCenter) {
        Random r = new Random();
        if (r.nextInt((int) (60 / firingSpeed)) == 1) {
            int firingLocX = (int) (locX + distanceBetweenFiringPointAndCenter * Math.cos(movingAngle));
            int firingLocY = (int) (locY + distanceBetweenFiringPointAndCenter * Math.sin(movingAngle));
            GameFrame.bullets.add(new Bullet(firingLocX, firingLocY, movingAngle,true));
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

    public void updateRectangles () {
        enemyRectangle = new Rectangle(locX,locY,enemyWidth,enemyHeight);
    }


}
