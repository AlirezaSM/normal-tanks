/*** In The Name of Allah ***/
package game.sample.ball;

import java.awt.event.*;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState {
	
	public int AimX, AimY, locX, locY, diam;
	public boolean gameOver;
	public double tankBodyAngle;
	private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
	private boolean mouseStateChanged;
	private int mouseX, mouseY;	
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	
	public GameState() {
		locX = 100;
		locY = 100;
		AimX = 150;
		AimY = 150;
		diam = 32;
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
	}
	
	/**
	 * The method which updates the game state.
	 */
	public void update() {
		if (mouseStateChanged) {
			AimY = mouseY - diam / 2;
			AimX = mouseX - diam / 2;
		}
		if (keyUP) {
            locY -= 8;
            changeTankBodyAngle("up");
        }
		if (keyDOWN) {
            locY += 8;
            changeTankBodyAngle("down");
        }
		if (keyLEFT) {
            locX -= 8;
            changeTankBodyAngle("left");
        }
		if (keyRIGHT) {
            locX += 8;
            changeTankBodyAngle("right");
        }

		locX = Math.max(locX, 0);
		locX = Math.min(locX, GameFrame.GAME_WIDTH - diam);
		locY = Math.max(locY, 0);
		locY = Math.min(locY, GameFrame.GAME_HEIGHT - diam);
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

}

