/*** In The Name of Allah ***/
package game.sample.ball;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering, 
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 *    http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 *    http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio

    //uncomment all /*...*/ in the class for using Tank icon instead of a simple circle

    private long lastRender;
    private ArrayList<Float> fpsHistory;

    private BufferStrategy bufferStrategy;
    Map map = new Map();
    double tankGunAngle;
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    BufferedImage numOfHeavyBullets;
    BufferedImage numOfMachineGunBullets;
    BufferedImage health;
    KhengEnemy me2 = new KhengEnemy();
    AlienEnemy ae = new AlienEnemy();
    MachineGun mg = new MachineGun();


    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        lastRender = -1;
        ThreadPool.init();
        fpsHistory = new ArrayList<>(100);
        try {
            numOfHeavyBullets = ImageIO.read(new File("NumberOfHeavyBullets.png"));
            numOfMachineGunBullets = ImageIO.read(new File("NumberOfMachineGunBullets.png"));
            health = ImageIO.read(new File("health.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         *  firing a bullet with left click
         */

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (GameState.isUsingHeavyGun && GameState.numOfHeavyBullets > 0) {
                        int firingLocXOfBullet = (int) (GameState.tankCenterX + 75 * Math.cos(tankGunAngle));
                        int firingLocYOfBullet = (int) (GameState.tankCenterY + 75 * Math.sin(tankGunAngle));
                        bullets.add(new HeavyBullet(firingLocXOfBullet, firingLocYOfBullet, tankGunAngle, false));
                        GameState.numOfHeavyBullets--;
                        System.out.println(GameState.numOfHeavyBullets);
                    }
                    if (!GameState.isUsingHeavyGun && GameState.numOfMachineGunBullets > 0) {
                        int firingLocXOfBullet = (int) (GameState.tankCenterX + 75 * Math.cos(tankGunAngle));
                        int firingLocYOfBullet = (int) (GameState.tankCenterY + 75 * Math.sin(tankGunAngle));
                        bullets.add(new MachineGunBullet(firingLocXOfBullet, firingLocYOfBullet, tankGunAngle, false));
                        GameState.numOfMachineGunBullets--;
                        System.out.println(GameState.numOfMachineGunBullets);
                    }
                }
            }
        });

        /**
         * changing tank gun with right click
         */

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (GameState.isUsingHeavyGun) {
                        GameState.gunInUse = GameState.machineGun;
                        GameState.isUsingHeavyGun = false;
                    }
                    else {
                        GameState.gunInUse = GameState.heavyGun;
                        GameState.isUsingHeavyGun = true;
                    }
                }

            }
        });

        enemies.add(me2);
        enemies.add(ae);
        enemies.add(mg);
    }

    /**
     * This must be called once after the JFrame is shown:
     * frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state) {

        // draw the map
        map.designMap();
        map.drawMap(g2d, state.cameraY);

        // Drawing the rotated image at the required drawing locations

        tankGunAngle = Math.atan2((state.aimY - state.tankCenterY), (state.aimX - state.tankCenterX));

        g2d.drawImage(rotatePic(state.mainTank, state.tankBodyAngle), state.tankCenterX - 90, state.tankCenterY - 90, null);

        g2d.drawImage(rotatePic(state.gunInUse, tankGunAngle), state.tankCenterX - 90, state.tankCenterY - 90, null);

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g2d);
            enemies.get(i).move(g2d, map.pregnableWalls);
        }

        updateBulletsState(g2d);
        updateEnemiesState(enemies);
        updateHealth(g2d,enemies,bullets);

        /**
         * bullets monitors
         */
        g2d.drawImage(numOfHeavyBullets,25,40,null);
        g2d.drawImage(numOfMachineGunBullets,30,110,null);
        g2d.setColor(new Color(0,173,17));
        g2d.setFont(new Font("TimesRoman", Font.BOLD , 30));
        g2d.drawString("" + GameState.numOfHeavyBullets,90,100);
        g2d.drawString("" + GameState.numOfMachineGunBullets,90,170);

        /**
         * health monitor
         */

        for (int i = 0; i < (GameState.mainTankHealth / 100) + 1; i++) {
            g2d.drawImage(health,500 + (50 * i), 50 , null);
        }


        System.out.println("x = " + GameState.aimX + "y = " + GameState.aimY);

        // 	g2d.drawImage(tankGun,state.locX,state.locY,null);

        g2d.setColor(Color.RED);
        g2d.fillOval(state.tankCenterX, state.tankCenterY, 10, 10);

        // Print FPS info
        long currentRender = System.currentTimeMillis();
        if (lastRender > 0) {
            fpsHistory.add(1000.0f / (currentRender - lastRender));
            if (fpsHistory.size() > 100) {
                fpsHistory.remove(0); // remove oldest
            }
            float avg = 0.0f;
            for (float fps : fpsHistory) {
                avg += fps;
            }
            avg /= fpsHistory.size();
            String str = String.format("Average FPS = %.1f , Last Interval = %d ms,mg-angle = %d, ae-angle = %d,ke-angle = %d, locX = %d, locY = %d,health = %d. direction = %d" +
                            "cameraY = %d",
                    avg, (currentRender - lastRender), mg.enemyDirection, ae.enemyDirection,me2.enemyDirection, mg.locX, mg.locY, GameState.mainTankHealth, state.tankDirection, state.cameraY);
            g2d.setColor(Color.BLACK);
            g2d.setFont(g2d.getFont().deriveFont(18.0f));
            g2d.drawString(str, 10, 700);
        }
        lastRender = currentRender;
        // Draw GAME OVER
        if (state.gameOver) {
            String str = "GAME OVER";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }
    }

    public static BufferedImage rotatePic(BufferedImage img, double angle) {
        double locationX = img.getWidth() / 2;
        double locationY = img.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(angle, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(img, null);
    }

    public void updateBulletsState(Graphics2D g2d) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).fire(g2d);
            if (bullets.get(i).isRemoved())
                bullets.remove(i);
        }
    }

    public void updateEnemiesState (ArrayList <Enemy> enemies) {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).updateRectangles();
            if (! enemies.get(i).alive)
                enemies.remove(i);
        }
    }

    public void updateHealth(Graphics2D g2d, ArrayList<Enemy> enemies, ArrayList<Bullet> bullets) {
        for (int j = 0; j < bullets.size(); j++) {
            /**
             * case 1 : if enemies fire a bullet toward the main tank
             */
            if (bullets.get(j).bulletRectangle.intersects(GameState.mainTankRectangle()) && bullets.get(j).firedByEnemy) {
                g2d.drawImage(bullets.get(j).bulletExplodedImg, bullets.get(j).bulletCenterLocX, bullets.get(j).bulletCenterLocY, null);
                GameState.mainTankHealth -= bullets.get(j).damagingPower;
                System.out.println(GameState.mainTankHealth);
                bullets.get(j).removed = true;
            }
            /**
             * case 4 : bullets fired toward pregnable walls
             */
            for (int i = 0; i < Map.pregnableWalls.size(); i++) {
                if (bullets.get(j).bulletRectangle.intersects(Map.pregnableWalls.get(i).imgRectangle)) {
                    Map.pregnableWalls.get(i).numOfBulletCollisions++;
                    Map.pregnableWalls.get(i).updateWall();
                    bullets.get(j).removed = true;
                }
            }

            for (int i = 0; i < enemies.size(); i++) {
//                g2d.drawRect(enemies.get(i).locX,enemies.get(i).locY,enemies.get(i).enemyWidth,enemies.get(j).enemyHeight);
  //              g2d.fillRect(enemies.get(i).locX,enemies.get(i).locY,enemies.get(i).enemyWidth,enemies.get(j).enemyHeight);

                /**
                 * case 2 : if main tank fires a bullet toward the enemies
                 */
                if (bullets.get(j).bulletRectangle.intersects(enemies.get(i).enemyRectangle) && !bullets.get(j).firedByEnemy) {
                    g2d.drawImage(bullets.get(j).bulletExplodedImg, bullets.get(j).bulletCenterLocX, bullets.get(j).bulletCenterLocY, null);
                    enemies.get(i).health -= bullets.get(j).damagingPower;
                    System.out.println("enemies health = " + enemies.get(i).health);
                    if (enemies.get(i).health == 0)
                        enemies.get(i).alive = false;
                    bullets.get(j).removed = true;
                }
            }
        }
        /**
         * case 3 : if enemies (except tank) collide with the main tank
         */
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).enemyRectangle.intersects(GameState.mainTankRectangle())
                    && !(enemies.get(i) instanceof MachineGun)) {
                g2d.drawImage(Bullet.bulletExplodedImg, enemies.get(i).locX, enemies.get(i).locY, null);
                GameState.mainTankHealth -= 25;
                enemies.get(i).alive = false;
            }
        }

    }
}
