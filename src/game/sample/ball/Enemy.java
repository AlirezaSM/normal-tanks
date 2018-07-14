package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Enemy implements Serializable {
    transient BufferedImage enemyImg;
    // speed is tile per frame
    public transient double speed;
    // firing speed is bullet per second
    public transient double firingSpeed;
    double centerTileX;
    double centerTileY;
    transient int enemyWidth;
    transient int enemyHeight;

    // center location
    transient int locX;
    transient int locY;

    transient int firingLocX;
    transient int firingLocY;
    public transient static int startTile;
    public transient double movingAngle = 1;
    transient boolean explodeInCollisions;
    transient boolean triggered;
    boolean alive = true;
    int health = 100;
    transient int distanceBetweenFiringPointAndCenter;
    transient Rectangle enemyRectangle;
    transient int enemyDirection;
    public transient static final int Right = 1;
    public transient static final int UP = 2;
    public transient static final int LEFT = 3;
    public transient static final int DOWN = 4;


    public Enemy(String imageName, double centerTileX, double centerTileY,
                 double speed, double firingSpeed, int health, int distance,
                 int firingLocX, int firingLocY, boolean explodeInCollisions) {
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
        startTile = 0;
        enemyRectangle = new Rectangle(locX, locY, enemyWidth, enemyHeight);
        this.speed = speed;
        this.firingSpeed = firingSpeed;
        triggered = false;
        this.health = health;
        distanceBetweenFiringPointAndCenter = distance;
        this.firingLocX = firingLocX;
        this.firingLocY = firingLocY;
        this.explodeInCollisions = explodeInCollisions;
    }

    public void checkTriggered(GameState state) {
        if (Math.abs(state.tankCenterTileY - centerTileY) < 30)
            triggered = true;
    }

    public void draw(Graphics2D g2d,GameState state) {
        if (alive) {
            checkTriggered(state);
            g2d.drawImage(enemyImg, locX, locY, null);
        }
    }

    public void move(Graphics2D g2d, ArrayList<PregnableWall> imgs,GameState state , GameFrame frame) {
        if (triggered && alive) {
            movingAngle = Math.atan2((locY - state.tankCenterY), (locX - state.tankCenterX));
            centerTileX = (centerTileX - speed * Math.cos(movingAngle));
            centerTileY = (centerTileY + speed * Math.sin(movingAngle));
            escapeFromObstacles(imgs, g2d, state);
            firingBullet(frame);
            updateLocs(state);
            updateDirection();
        }
    }

    public void firingBullet(GameFrame frame) {
        Random r = new Random();
        if (r.nextInt((int) (60 / firingSpeed)) == 1) {
            int currentFiringLocX = (int) (locX + firingLocX + distanceBetweenFiringPointAndCenter * Math.cos(movingAngle));
            int currentFiringLocY = (int) (locY + firingLocY - distanceBetweenFiringPointAndCenter * Math.sin(movingAngle));
            frame.bullets.add(new EnemyBullet(currentFiringLocX, currentFiringLocY, movingAngle, true));
        }
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

    public boolean collisioned(ArrayList<PregnableWall> imageObstacles,GameState state) {
        int tileX = (int) centerTileX + (enemyWidth / 32);
        int tileY = (int) centerTileY - (enemyHeight / 24);
        for (int i = 0; i < Tile.numOfHorizontalTiles; i++) {
            for (int j = startTile; j < (startTile + Tile.numOfVerticalTilesInOneScreen); j++) {
                if (enemyRectangle.intersects(Map.tileRectangle(i, j)) && Map.tiles[i][j].isObstacle()) {
                    if (i > tileX && enemyDirection == Right) {
                        return true;
                    } else if (i < tileX && enemyDirection == LEFT) {
                        return true;
                    } else if (j > tileY && enemyDirection == UP) {
                        return true;
                    } else if (j < tileY && enemyDirection == DOWN) {
                        return true;
                    }
                }
            }
        }
        for (int i = 0; i < imageObstacles.size(); i++) {
            if (enemyRectangle.intersects(imageObstacles.get(i).imgRectangle) && imageObstacles.get(i).obstacle) {
                if (enemyDirection == Right && tileX < imageObstacles.get(i).centerTileX) {
                    return true;
                } else if (enemyDirection == LEFT && tileX > imageObstacles.get(i).centerTileX) {
                    return true;
                } else if (enemyDirection == UP && tileY < imageObstacles.get(i).centerTileY) {
                    return true;
                } else if (enemyDirection == DOWN && tileY > imageObstacles.get(i).centerTileY) {
                    return true;
                }
            }
        }
        if (enemyRectangle.intersects(state.mainTankRectangle) && !explodeInCollisions ) {
            return true;
        }
        return false;
    }

    public void escapeFromObstacles(ArrayList<PregnableWall> obstacleImages, Graphics2D g2d, GameState state) {
        int countR = 0;
        int countL = 0;
        int countU = 0;
        int countD = 0;
        int tileX = (int) centerTileX + (enemyWidth / 32);
        int tileY = (int) centerTileY - (enemyHeight / 24);
        int locX = (int) (tileX * Tile.tileWidth) - (Tile.tileWidth);
        int locY = (int) (Map.screenHeight - (tileY - Enemy.startTile) * Tile.tileHeight) + (Tile.tileHeight);
        if (collisioned(obstacleImages,state)) {
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
                if (Map.tileIsVisible(tileY - i) &&
                        !Map.tiles[tileX][tileY - i].isObstacle()) {
                    countD++;
                }
            }
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



    public void updateLocs (GameState state) {
        startTile = state.cameraY / Tile.tileHeight;
        locX = (int) (centerTileX * Tile.tileWidth) + (enemyWidth / 2);
        locY = (int) (Map.screenHeight - (centerTileY - startTile) * Tile.tileHeight) + (enemyHeight / 2);
    }

    public void updateRectangles () {
        enemyRectangle = new Rectangle(locX,locY,enemyWidth,enemyHeight);
    }


}
