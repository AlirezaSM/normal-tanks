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
    public double speed;
    // firing speed is bullet per second
    public double firingSpeed;
    double centerTileX;
    double centerTileY;
    int enemyWidth;
    int enemyHeight;

    // center location
    int locX;
    int locY;

    int firingLocX;
    int firingLocY;
    public static int startTile;
    public double movingAngle = 1;
    boolean visible;
    boolean triggered;
    boolean alive = true;
    int health = 100;
    int distanceBetweenFiringPointAndCenter;
    Rectangle enemyRectangle;
    int enemyDirection;
    public static final int Right = 1;
    public static final int UP = 2;
    public static final int LEFT = 3;
    public static final int DOWN = 4;
    int i = 0;


    public Enemy(String imageName, double centerTileX, double centerTileY,
                 double speed, double firingSpeed, int health, int distance,
                 int firingLocX, int firingLocY) {
        try {
            enemyImg = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.centerTileX = centerTileX;
        this.centerTileY = centerTileY;
        enemyWidth = enemyImg.getWidth();
        enemyHeight = enemyImg.getHeight();
        locX = (int) (centerTileX * Tile.tileWidth) + (enemyWidth / 2);
        locY = (int) (Map.screenHeight - (centerTileY - startTile) * Tile.tileHeight) + (enemyHeight / 2);
        startTile = GameState.cameraY / Tile.tileHeight;
        enemyRectangle = new Rectangle(locX, locY, enemyWidth, enemyHeight);
        this.speed = speed;
        this.firingSpeed = firingSpeed;
        visible = false;
        triggered = false;
        this.health = health;
        distanceBetweenFiringPointAndCenter = distance;
        this.firingLocX = firingLocX;
        this.firingLocY = firingLocY;
    }

    public void checkTriggered() {
        if (Math.abs(GameState.tankCenterTileY - centerTileY) < 30)
            triggered = true;
    }

    public void draw(Graphics2D g2d) {
        if (alive) {
            checkTriggered();
            g2d.drawImage(enemyImg, locX, locY, null);
            g2d.drawRect(locX, locY, enemyWidth, enemyHeight);
            g2d.fillRect(locX, locY, enemyWidth, enemyHeight);
        }
    }

    public void move(Graphics2D g2d, ArrayList<PregnableWall> imgs) {
        if (triggered) {
            movingAngle = Math.atan2((locY - GameState.tankCenterY), (locX - GameState.tankCenterX));
            centerTileX = (centerTileX - speed * Math.cos(movingAngle));
            centerTileY = (centerTileY + speed * Math.sin(movingAngle));
            escapeFromObstacles(imgs, g2d);
            firingBullet();
            updateLocs();
            updateDirection();
        }
    }

    public void firingBullet() {
        Random r = new Random();
        if (r.nextInt((int) (60 / firingSpeed)) == 1) {
            int currentFiringLocX = (int) (locX + firingLocX + distanceBetweenFiringPointAndCenter * Math.cos(movingAngle));
            int currentFiringLocY = (int) (locY + firingLocY - distanceBetweenFiringPointAndCenter * Math.sin(movingAngle));
            GameFrame.bullets.add(new EnemyBullet(currentFiringLocX, currentFiringLocY, movingAngle, true));
        }
    }

    public void checkVisibility() {
        int endTile = startTile + (Tile.numOfVerticalTiles / Map.numOfVerticalScreens);
        if (centerTileY >= startTile && centerTileY <= endTile)
            visible = true;
    }

    public void updateDirection() {
        if ((movingAngle > 0 && movingAngle < 0.7853) || (movingAngle < 0 && movingAngle > -0.7853))
            enemyDirection = LEFT;
        else if ((movingAngle > 0.7853 && movingAngle < 2.3561))
            enemyDirection = UP;
        else if ((movingAngle > 2.3561 && movingAngle < 3.1415) || (movingAngle < -2.3561 && movingAngle > -3.1415))
            enemyDirection = Right;
        else if ((movingAngle < -0.7853 && movingAngle > -2.3561))
            enemyDirection = DOWN;
    }

    public boolean collisioned(ArrayList<PregnableWall> imageObstacles) {
        int count = 0;
        int tileX = (int) centerTileX + (enemyWidth / 32);
        int tileY = (int) centerTileY - (enemyHeight / 24);
        for (int i = 0; i < Tile.numOfHorizontalTiles; i++) {
            for (int j = startTile; j < (startTile + Tile.numOfVerticalTilesInOneScreen); j++) {
                if (enemyRectangle.intersects(Map.tileRectangle(i, j)) && Map.tiles[i][j].isObstacle()) {
                    if (i > tileX && enemyDirection == Right) {
                        count++;
                        return true;
                    } else if (i < tileX && enemyDirection == LEFT) {
                        count++;
                        return true;
                    } else if (j > tileY && enemyDirection == UP) {
                        count++;
                        return true;
                    } else if (j < tileY && enemyDirection == DOWN) {
                        count++;
                        return true;
                    }
                }
            }
        }
        for (int i = 0; i < imageObstacles.size(); i++) {
            if (enemyRectangle.intersects(imageObstacles.get(i).imgRectangle) && imageObstacles.get(i).obstacle) {
                if (enemyDirection == Right && tileX < imageObstacles.get(i).centerTileX) {
                    count++;
                    return true;
                } else if (enemyDirection == LEFT && tileX > imageObstacles.get(i).centerTileX) {
                    count++;
                    return true;
                } else if (enemyDirection == UP && tileY < imageObstacles.get(i).centerTileY) {
                    count++;
                    return true;
                } else if (enemyDirection == DOWN && tileY > imageObstacles.get(i).centerTileY) {
                    count++;
                    return true;
                }
            }
        }
        return false;
    }

    public void escapeFromObstacles(ArrayList<PregnableWall> obstacleImages, Graphics2D g2d) {
        int countR = 0;
        int countL = 0;
        int countU = 0;
        int countD = 0;
        int tileX = (int) centerTileX + (enemyWidth / 32);
        int tileY = (int) centerTileY - (enemyHeight / 24);
        int locX = (int) (tileX * Tile.tileWidth) - (Tile.tileWidth);
        int locY = (int) (Map.screenHeight - (tileY - Enemy.startTile) * Tile.tileHeight) + (Tile.tileHeight);
        g2d.drawRect(locX, locY, Tile.tileWidth, Tile.tileHeight);
        if (collisioned(obstacleImages)) {
            centerTileX = (centerTileX + speed * Math.cos(movingAngle));
            centerTileY = (centerTileY - speed * Math.sin(movingAngle));
            for (int i = 0; i < 10; i++) {
                if ((tileX + i) > 0 && (tileX + i) < 40 &&
                        !Map.tiles[tileX + i][tileY].isObstacle()) {
                    countR++;
                }
                if ((tileX - i) > 0 && (tileX - i) < 40 &&
                        !Map.tiles[tileX - i][tileY].isObstacle()) {
                    countL++;
                }
                if (Map.tileIsVisible(tileY + i) &&
                        !Map.tiles[tileX][tileY + i].isObstacle()) {
                    countU++;
                }
                if (Map.tileIsVisible(tileY) &&
                        !Map.tiles[tileX][tileY - i].isObstacle()) {
                    countD++;
                }
            }
            System.out.println("r = " + countR + " l = " + countL + " u = " + countU + " d = " + countD);
            if (countR == 10 && enemyDirection != Right) {
                centerTileX = (centerTileX + speed);
                return;
            } else if (countD == 10 && enemyDirection != DOWN) {
                centerTileY = (centerTileY - speed);
                return;
            } else if (countL == 10 && enemyDirection != LEFT) {
                centerTileX = (centerTileX - speed);
                return;
            } else if (countU == 10 && enemyDirection != UP) {
                centerTileY = (centerTileY + speed);
                return;
            }
        }
    }



    public void updateLocs () {
        startTile = GameState.cameraY / Tile.tileHeight;
        locX = (int) (centerTileX * Tile.tileWidth) + (enemyWidth / 2);
        locY = (int) (Map.screenHeight - (centerTileY - startTile) * Tile.tileHeight) + (enemyHeight / 2);
    }

    public void updateRectangles () {
        enemyRectangle = new Rectangle(locX,locY,enemyWidth,enemyHeight);
    }


}
