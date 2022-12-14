/*** In The Name of Allah ***/
package game.sample.ball;

/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods 
 * in the while loop (update() and render()) should be 
 * long running! Both must execute very quickly, without 
 * any waiting and blocking!
 * 
 * Detailed discussion on different game loop design
 * patterns is available in the following link:
 *    http://gameprogrammingpatterns.com/game-loop.html
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GameLoop implements Runnable {
	
	/**
	 * Frame Per Second.
	 * Higher is better, but any value above 24 is fine.
	 */
	public static final int FPS = 70;
	
	private GameFrame canvas;
	private GameState state;
	static int loopNum = 1;

	public GameLoop(GameFrame frame) {
		canvas = frame;
	}
	
	/**
	 * This must be called before the game loop starts.
	 */
	public void init(GameState st) {
		state = st;
		canvas.addKeyListener(state.getKeyListener());
		canvas.addMouseListener(state.getMouseListener());
		canvas.addMouseMotionListener(state.getMouseMotionListener());
	}

	@Override
	public void run() {
		boolean gameOver = false;
		while (!gameOver) {
			try {
				loopNum++;
				long start = System.currentTimeMillis();
				//
				state.update();
				canvas.render();
				gameOver = state.gameOver;
				//
				long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
				if (delay > 0)
					Thread.sleep(delay);
			} catch (InterruptedException ex) {
			    ex.printStackTrace();
			}
		}
		canvas.render();
	}
}
