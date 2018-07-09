package game.sample.ball;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bullet {
    BufferedImage bulletImg;
    BufferedImage bulletExplodedImg;
    int bulletCenterLocX;
    int bulletCenterLocY;
    static final int bulletSpeed  = 12;
    static final int bulletWidth  = 40;
    static final int bulletHeight = 10;
    double bulletAngle;
    public Rectangle bulletRectangle;
    boolean removed = false;
    boolean firedByEnemy;

    public Bullet(int bulletLocX, int bulletLocY,double bulletAngle,boolean firedByEnemy) {
        try {
            bulletImg = ImageIO.read(new File("bullet.png"));
            bulletExplodedImg = ImageIO.read(new File("bulletExploded.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bulletCenterLocX = bulletLocX;
        bulletCenterLocY = bulletLocY;
        this.bulletAngle = bulletAngle;
        bulletRectangle = new Rectangle(bulletLocX,bulletLocY,bulletWidth,bulletHeight);
        this.firedByEnemy = firedByEnemy;
    }

    public void moveBullet (boolean isEnemy) {
        if (! removed && !isEnemy) {
            bulletCenterLocX = (int) (bulletCenterLocX + bulletSpeed * Math.cos(bulletAngle));
            bulletCenterLocY = (int) (bulletCenterLocY + bulletSpeed * Math.sin(bulletAngle));
        }
        if (! removed && isEnemy) {
            bulletCenterLocX = (int) (bulletCenterLocX - bulletSpeed * Math.cos(bulletAngle));
            bulletCenterLocY = (int) (bulletCenterLocY - bulletSpeed * Math.sin(bulletAngle));
        }

    }



    public void draw (Graphics2D g2d) {
        if (!removed) {
            g2d.drawImage(GameFrame.rotatePic(bulletImg, bulletAngle), bulletCenterLocX, bulletCenterLocY, null);
        }
    }

    public void checkForBulletCollision (Graphics2D g2d) {
        int startTile = GameState.cameraY / Tile.tileHeight  ;
        int bulletTileX = (int) (bulletCenterLocX / Tile.tileWidth);
        int bulletTileY = (int) (startTile + ((720 - bulletCenterLocY) / Tile.tileHeight));
        if (bulletTileX >= Tile.numOfHorizontalTiles || bulletTileX < 0
                || bulletTileY >= Tile.numOfVerticalTiles || bulletTileY < 0) {
            removed = true;
        }
        else if (Map.tiles[bulletTileX][bulletTileY].isObstacle()) {
            removed = true;
            g2d.drawImage(bulletExplodedImg, bulletCenterLocX, bulletCenterLocY,null);
        }
    }

    public void fire (Graphics2D g2d,Boolean isEnemy) {
        draw(g2d);
        moveBullet(isEnemy);
        checkForBulletCollision(g2d);
        updateBulletRectangle();
    }

    public void setBulletAngle(double bulletAngle) {
        this.bulletAngle = bulletAngle;
    }

    public BufferedImage getBulletImg() {
        return bulletImg;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void updateBulletRectangle () {
        bulletRectangle = new Rectangle(bulletCenterLocX,bulletCenterLocY,bulletWidth,bulletHeight);
    }

}
