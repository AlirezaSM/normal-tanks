/*** In The Name of Allah ***/
package game.sample.ball;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Program start.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class Main2 {

    public static void main(String[] args) {
        // Initialize the global thread-pool
        ThreadPool.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                boolean multiplayer = true;
                boolean server = false;
                GameState state = new GameState();
                GameFrame frame = new GameFrame("client",state,multiplayer,server);
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame);
                game.init(state);
                ThreadPool.execute(game);
                // and the game starts ...
            }
        });
    }
}
