/*** In The Name of Allah ***/
package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState implements Serializable {
	
	public double tankCenterTileX, tankCenterTileY;
	public transient int aimX, aimY, diam, tankDirection;
	public int tankCenterX,tankCenterY;
	public transient static boolean gameOver,paused;
	public static int cameraY;
	public int cameraY2;
	public double tankBodyAngle,tankGunAngle;
	public int mainTankHealth, numOfHeavyBullets, numOfMachineGunBullets;
	public transient boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
	public transient boolean mouseStateChanged;
	public  boolean isUsingHeavyGun;
	public transient int mouseX, mouseY;
	private transient KeyHandler keyHandler;
	private transient MouseHandler mouseHandler;
	public static transient BufferedImage mainTank, heavyGun, machineGun, gunInUse;
	public transient Rectangle mainTankRectangle;
    transient FileOutputStream fos;
    transient ObjectOutputStream oos;
    transient FileInputStream fis;
    transient ObjectInputStream ois;
    transient BufferedOutputStream bos;
    transient BufferedInputStream bis;
    public transient static final int NUMOFLIVES = 3;
	public transient static final int Right = 1;
	public transient static final int UP = 2;
	public transient static final int LEFT = 3;
	public transient static final int DOWN = 4;


	public GameState() {
		cameraY = 0;
		tankCenterX = 190;
		tankCenterY = 190;
		aimX = 150;
		aimY = 150;
		diam = 75;
		gameOver = false;
		//
		keyUP = false;
		keyDOWN = false;
		keyRIGHT = false;
		keyLEFT = false;
		//
		mouseStateChanged = false;
		mouseX = 0;
		mouseY = 0;
		//
        tankBodyAngle = 0;
        //
		keyHandler = new KeyHandler();
		mouseHandler = new MouseHandler();
		//
        mainTankRectangle = new Rectangle(tankCenterX - 90,tankCenterY - 90, 150, 150);
        //
        mainTankHealth = 100 * NUMOFLIVES - 1;
        numOfHeavyBullets = 30;
        numOfMachineGunBullets = 300;
        //
        isUsingHeavyGun = true;
        //
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mainTank = ImageIO.read(new File("tankBody.png"));
            heavyGun = ImageIO.read(new File("tankGun.png"));
            machineGun = ImageIO.read(new File ("tankGun02.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gunInUse = heavyGun;
	}
	
	/**
	 * The method which updates the game state.
	 */
	public void update() {
		if (mouseStateChanged) {
			aimX = mouseX;
			aimY = mouseY;
		}
		if (keyUP) {
		    if (Math.abs(tankCenterY - (diam)) > 100) {
                tankCenterY -= 8;
            }
		    else {
                cameraY += 8;
            }
            changeTankBodyAngle("up");
            tankDirection = UP;
        }
		if (keyDOWN) {
            if (Math.abs(tankCenterY - (720 - diam)) < 25) {
                cameraY -= 8;
            }
            else {
                tankCenterY += 8;
            }
            changeTankBodyAngle("down");
            tankDirection = DOWN;
        }
		if (keyLEFT) {
            tankCenterX -= 8;
            changeTankBodyAngle("left");
            tankDirection = LEFT;
        }
		if (keyRIGHT) {
            tankCenterX += 8;
            changeTankBodyAngle("right");
            tankDirection = Right;
        }

		tankCenterX = Math.max(tankCenterX, diam);
		tankCenterX = Math.min(tankCenterX, GameFrame.GAME_WIDTH - diam);
		tankCenterY = Math.max(tankCenterY, diam);
		tankCenterY = Math.min(tankCenterY, GameFrame.GAME_HEIGHT - diam);

		tankCenterTileX = tankCenterX / Tile.tileWidth;
        int startTile = cameraY / Tile.tileHeight;
		tankCenterTileY = (Tile.numOfVerticalTilesInOneScreen - (tankCenterY / Tile.tileHeight)) + startTile;

		if (cameraY < 0)
		    cameraY = 0;
		if (cameraY > 2850)
		    cameraY = 2850;

        checkForMainTankCollision();
        updateMainTankRectangle();
        cameraY2 = cameraY;
	}
	
	
	public KeyListener getKeyListener() {
		return keyHandler;
	}
	public MouseListener getMouseListener() {
		return mouseHandler;
	}
	public MouseMotionListener getMouseMotionListener() {
		return mouseHandler;
	}



	/**
	 * The keyboard handler.
	 */
	class KeyHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_UP:
					keyUP = true;
					break;
				case KeyEvent.VK_DOWN:
					keyDOWN = true;
					break;
				case KeyEvent.VK_LEFT:
					keyLEFT = true;
					break;
				case KeyEvent.VK_RIGHT:
					keyRIGHT = true;
					break;
				case KeyEvent.VK_ESCAPE:
					gameOver = true;
					break;
                case KeyEvent.VK_BACK_SPACE:
                    CheatSheet cheatSheet = new CheatSheet(GameState.this);
                    break;
                case KeyEvent.VK_P:
                    if (paused)
                        paused = false;
                    else
                        paused = true;
                    break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_UP:
					keyUP = false;
					break;
				case KeyEvent.VK_DOWN:
					keyDOWN = false;
					break;
				case KeyEvent.VK_LEFT:
					keyLEFT = false;
					break;
				case KeyEvent.VK_RIGHT:
					keyRIGHT = false;
					break;
			}
		}

	}

	/**
	 * The mouse handler.
	 */
	class MouseHandler extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			mouseStateChanged = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mouseStateChanged = false;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseStateChanged = true;
            mouseX = e.getX();
            mouseY = e.getY();
        }

    }

    public void changeTankBodyAngle (String orientation) {
	    double firstQuarter = 1.570796;
	    double secondQuarter = 3.141592;
	    double thirdQuarter = -1.570796;
	    double forthQuarter = -3.141592;
	    double zeroQuarter = 0;
	    double move = 0.03272492347;
        switch (orientation) {
            case "up":
                if (tankBodyAngle <= firstQuarter && tankBodyAngle >= thirdQuarter && (Math.abs(tankBodyAngle-firstQuarter)) > 0.03)
                    tankBodyAngle+= move;
                else if ((Math.abs(tankBodyAngle-firstQuarter)) > 0.03)
                    tankBodyAngle-= move;
                break;
            case "right":
                if (tankBodyAngle >= zeroQuarter && tankBodyAngle <= secondQuarter && (Math.abs(tankBodyAngle-zeroQuarter)) > 0.03)
                    tankBodyAngle-= move;
                else if ((Math.abs(tankBodyAngle-zeroQuarter)) > 0.03)
                    tankBodyAngle+= move;
                break;
            case "down":
                if (tankBodyAngle <= firstQuarter && tankBodyAngle >= thirdQuarter && (Math.abs(tankBodyAngle-thirdQuarter)) > 0.03)
                    tankBodyAngle-= move;
                else if ((Math.abs(tankBodyAngle-thirdQuarter)) > 0.03)
                    tankBodyAngle+= move;
                break;
            case "left":
                if (tankBodyAngle >= zeroQuarter && tankBodyAngle <= secondQuarter && (Math.abs(tankBodyAngle-secondQuarter)) > 0.03)
                    tankBodyAngle+= move;
                else if ((Math.abs(tankBodyAngle-secondQuarter)) > 0.03)
                    tankBodyAngle-= move;
                break;
        }
        while (tankBodyAngle > 3.14)
            tankBodyAngle -= (6.28);
        while (tankBodyAngle <= -3)
            tankBodyAngle += (6.28);

    }

    public void checkForMainTankCollision () {
        int startTile = cameraY / Tile.tileHeight  ;
        int endTile = startTile + (Tile.numOfVerticalTiles / Map.numOfVerticalScreens);
        Point tankCenterTile = new Point(tankCenterX / Tile.tileWidth, startTile + ((720 - tankCenterY) / Tile.tileHeight));

        if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY() + 1].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY() + 2].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY() - 1].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY() - 2].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX() + 1][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX() + 1][(int) tankCenterTile.getY() + 1].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX() + 1][(int) tankCenterTile.getY() + 2].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX() + 1][(int) tankCenterTile.getY() - 1].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX() + 1][(int) tankCenterTile.getY() - 2].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX() + 2][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX() + 2][(int) tankCenterTile.getY() + 1].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX() + 2][(int) tankCenterTile.getY() + 2].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX() + 2][(int) tankCenterTile.getY() - 1].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == Right && Map.tiles[(int) tankCenterTile.getX() + 2][(int) tankCenterTile.getY() - 2].isObstacle()){
            tankCenterX -= 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY() + 1].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY() + 2].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY() - 1].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY() - 2].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX() - 1][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX() - 1][(int) tankCenterTile.getY() + 1].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX() - 1][(int) tankCenterTile.getY() + 2].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX() - 1][(int) tankCenterTile.getY() - 1].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX() - 1][(int) tankCenterTile.getY() - 2].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX() - 2][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX() - 2][(int) tankCenterTile.getY() + 1].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX() - 2][(int) tankCenterTile.getY() + 2].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX() - 2][(int) tankCenterTile.getY() - 1].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == LEFT && Map.tiles[(int) tankCenterTile.getX() - 2][(int) tankCenterTile.getY() - 2].isObstacle()){
            tankCenterX += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX() + 1][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX() + 2][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX() - 1][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX() - 2][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY() + 1].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX() + 1][(int) tankCenterTile.getY() + 1].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX() + 2][(int) tankCenterTile.getY() + 1].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX() - 1][(int) tankCenterTile.getY() + 1].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX() - 2][(int) tankCenterTile.getY() + 1].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY() + 2].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX() + 1][(int) tankCenterTile.getY() + 2].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX() + 2][(int) tankCenterTile.getY() + 2].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX() - 1][(int) tankCenterTile.getY() + 2].isObstacle()){
            tankCenterY += 8;
        }
        else if (tankDirection == UP && Map.tiles[(int) tankCenterTile.getX() - 2][(int) tankCenterTile.getY() + 2].isObstacle()){
            tankCenterY += 8;
        }

        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX() + 1][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX() + 2][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX() - 1][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX() - 2][(int) tankCenterTile.getY()].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY() - 1].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX() + 1][(int) tankCenterTile.getY() - 1].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX() + 2][(int) tankCenterTile.getY() - 1].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX() - 1][(int) tankCenterTile.getY() - 1].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX() - 2][(int) tankCenterTile.getY() - 1].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX()][(int) tankCenterTile.getY() - 2].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX() + 1][(int) tankCenterTile.getY() - 2].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX() + 2][(int) tankCenterTile.getY() - 2].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX() - 1][(int) tankCenterTile.getY() - 2].isObstacle()){
            tankCenterY -= 8;
        }
        else if (tankDirection == DOWN && Map.tiles[(int) tankCenterTile.getX() - 2][(int) tankCenterTile.getY() - 2].isObstacle()){
            tankCenterY -= 8;
        }

    }

    public void updateMainTankRectangle () {
        mainTankRectangle = new Rectangle(this.tankCenterX - 90,this.tankCenterY - 90,150,150);
    }

    public void serializeAndSave (GameState state) {
        try {
            fos = new FileOutputStream(new File("state.jtank"));
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(state);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deserializeAndUpdate () {
        try {
            fis = new FileInputStream(new File("state.jtank"));
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
            GameState temp = (GameState) ois.readObject();
            tankCenterX = temp.tankCenterX;
            tankCenterY = temp.tankCenterY;
            mainTankHealth = temp.mainTankHealth;
            numOfMachineGunBullets = temp.numOfMachineGunBullets;
            numOfHeavyBullets = temp.numOfHeavyBullets;
            cameraY = temp.cameraY2;
            tankBodyAngle = temp.tankBodyAngle;
          //  tankGunAngle = temp.tankGunAngle;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

