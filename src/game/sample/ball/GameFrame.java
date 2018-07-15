/*** In The Name of Allah ***/
package game.sample.ball;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class GameFrame extends JFrame implements Serializable {

    transient GameState state;
    public static transient final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static transient final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio

    //uncomment all /*...*/ in the class for using Tank icon instead of a simple circle

    private transient long lastRender;
    private transient ArrayList<Float> fpsHistory;

    private transient BufferStrategy bufferStrategy;
    transient Map map;
    public  ArrayList<Bullet> bullets = new ArrayList<>();
   // public  ArrayList<Enemy> enemies = new ArrayList<>();
    public  LinkedList<Enemy> enemies = new LinkedList<>();
    transient BufferedImage numOfHeavyBullets;
    transient BufferedImage numOfMachineGunBullets;
    transient BufferedImage health;
    static int numOfEnemies = 3;
    transient KhengEnemy me2 = new KhengEnemy(3,22);
    transient AlienEnemy ae1 = new AlienEnemy(33,60);
    transient AlienEnemy ae2 = new AlienEnemy(15,120);
    transient MachineGun mg1 = new MachineGun(15,70);
    transient MachineGun mg2 = new MachineGun(20,92);
    transient MachineGun mg3 = new MachineGun(5,115);
    transient MachineGun mg4 = new MachineGun(25,115);
    transient Mine mine1 = new Mine(13,96);
    transient Mine mine2 = new Mine(5,106);
    transient Mine mine3 = new Mine(25,106);

    Mine m = new Mine(20,20);
    transient FileOutputStream fos;
    transient ObjectOutputStream oos;
    transient ObjectInputStream ois;
    transient FileInputStream fis;
    transient BufferedInputStream bis;
    transient BufferedOutputStream bos;
    Server server;
    Client client;
    boolean multiplayer;
    boolean serverOrClient;
    Player2Tank player2;
    LocalTime t1;
    LocalTime t2;
    Bullet b1;

    public SoundPlayer soundPlayer = new SoundPlayer();

    public GameFrame(String title, GameState st,boolean multiplayer, boolean serverOrClient) {
        super(title);
        state = st;
        map = new Map();
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        lastRender = -1;
        ThreadPool.init();
        player2 = new Player2Tank();
        this.multiplayer = multiplayer;
        this.serverOrClient = serverOrClient;
        try {
            BufferedImage cursorIcon = ImageIO.read(new File(".\\cursorIcon.png"));
            Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorIcon, new Point(0, 0), "Cursor");
            setCursor(cursor);
        }catch(Exception e){

        }
        if (serverOrClient && multiplayer) {
            server = new Server();
        }
        else if (multiplayer) {
            client = new Client("192.168.43.31");
    }
        fpsHistory = new ArrayList<>(100);
        try {
            numOfHeavyBullets = ImageIO.read(new File("NumberOfHeavyBullets.png"));
            numOfMachineGunBullets = ImageIO.read(new File("NumberOfMachineGunBullets.png"));
            health = ImageIO.read(new File("health.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        b1 = new Bullet(100,100,1,true);
        b1.removed = true;
        bullets.add(b1);

        /**
         *  firing a bullet with left click
         */

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (state.isUsingHeavyGun && state.numOfHeavyBullets > 0) {
                        int firingLocXOfBullet = (int) (state.tankCenterX + 75 * Math.cos(state.tankGunAngle));
                        int firingLocYOfBullet = (int) (state.tankCenterY + 75 * Math.sin(state.tankGunAngle));
                        bullets.add(new HeavyBullet(firingLocXOfBullet, firingLocYOfBullet, state.tankGunAngle, false));
                        state.numOfHeavyBullets--;
                        System.out.println(state.numOfHeavyBullets);
                        soundPlayer.heavyShot();
                    }else if(state.isUsingHeavyGun && state.numOfHeavyBullets <= 0){
                        soundPlayer.emptyGun();
                    }
                    if (!state.isUsingHeavyGun && state.numOfMachineGunBullets > 0) {
                        int firingLocXOfBullet = (int) (state.tankCenterX + 75 * Math.cos(state.tankGunAngle));
                        int firingLocYOfBullet = (int) (state.tankCenterY + 75 * Math.sin(state.tankGunAngle));
                        bullets.add(new MachineGunBullet(firingLocXOfBullet, firingLocYOfBullet, state.tankGunAngle, false));
                        state.numOfMachineGunBullets--;
                        System.out.println(state.numOfMachineGunBullets);
                        soundPlayer.machinGunShot();
                    }else if(!state.isUsingHeavyGun && state.numOfMachineGunBullets <= 0){
                        soundPlayer.emptyGun();
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
                    if (state.isUsingHeavyGun) {
                        GameState.gunInUse = GameState.machineGun;
                        state.isUsingHeavyGun = false;
                    }
                    else {
                        GameState.gunInUse = GameState.heavyGun;
                        state.isUsingHeavyGun = true;
                    }
                }

            }
        });

        enemies.add(me2);
        enemies.add(ae1);
        enemies.add(ae2);
        enemies.add(mg1);
        enemies.add(mg2);
        enemies.add(mg3);
        enemies.add(mg4);
        enemies.add(mine1);
        enemies.add(mine2);
        enemies.add(mine3);
        if (!Menu.continueGame)
            deleteSavedInfo();
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
    public void render() {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics);
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
    private void doRendering(Graphics2D g2d) {

        System.out.println(GameLoop.loopNum);

        if (multiplayer && ((GameLoop.loopNum - 1) % 1 == 0) || GameLoop.loopNum == 2 ) {
            if (serverOrClient && multiplayer) {
                server.networkWriteState(state);
            }
            else if (multiplayer) {
                client.networkWriteState(state);
            }
        }
        if (!multiplayer && (((GameLoop.loopNum - 1) % 60 == 0) || GameLoop.loopNum == 2 )) {
            System.out.println("here");
            map.deserializeAndUpdate();
            deserializeAndUpdate("enemies.jtank");
        //    deserializeAndUpdate("bullets.jtank");
            state.deserializeAndUpdate();
        }
        // draw the map
        map.designMap();

        map.drawMap(g2d, state.cameraY,state);

        while (GameState.paused) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Drawing the rotated image at the required drawing locations

        state.tankGunAngle = Math.atan2((state.aimY - state.tankCenterY), (state.aimX - state.tankCenterX));

        g2d.drawImage(rotatePic(state.mainTank, state.tankBodyAngle), state.tankCenterX - 90, state.tankCenterY - 90, null);

        g2d.drawImage(rotatePic(state.gunInUse, state.tankGunAngle), state.tankCenterX - 90, state.tankCenterY - 90, null);

        if (multiplayer)
            player2.draw(g2d);

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g2d, state);
            enemies.get(i).move(g2d, map.pregnableWalls, state, this);
        }
        updateBulletsState(g2d);
        updateEnemiesState(enemies);
        updateHealth(g2d,enemies,bullets,map);
        updatePrizes();
        checkIfGameIsOver();

        if (multiplayer && ((GameLoop.loopNum % 1) == 0) && GameLoop.loopNum != 0) {
            if (serverOrClient && multiplayer) {
                server.getAndProcessInfo(map,player2,this,g2d);
            }
            else if (multiplayer) {
                   client.getAndProcessInfo(map,player2,this,g2d);
            }
        }
        if (!multiplayer && ((GameLoop.loopNum % 60) == 0) && GameLoop.loopNum != 0) {
            System.out.println("here");
            map.serializeAndSave(map);
            serializeAndSave(enemies,"enemies.jtank");
         //   serializeAndSave(bullets,"bullets.jtank");
            state.serializeAndSave(state);
        }

        /**
         * bullets monitors
         */
        g2d.drawImage(numOfHeavyBullets,25,40,null);
        g2d.drawImage(numOfMachineGunBullets,30,110,null);
        g2d.setColor(new Color(0,173,17));
        g2d.setFont(new Font("TimesRoman", Font.BOLD , 30));
        g2d.drawString("" + state.numOfHeavyBullets,90,100);
        g2d.drawString("" + state.numOfMachineGunBullets,90,170);

        /**
         * health monitor
         */

        for (int i = 0; i < (state.mainTankHealth / 100) + 1; i++) {
            g2d.drawImage(health,500 + (50 * i), 50 , null);
        }

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
            String str = String.format("Average FPS = %.1f , Last Interval = %d ms,angle = %f, ae-health = %d,ke-health = %d, locX = %d, locY = %d,health = %d. direction = %d" +
                            "cameraY = %d",
                    avg, (currentRender - lastRender), state.tankBodyAngle, ae1.health,me2.health, mg1.locX, mg1.locY, state.mainTankHealth, state.tankDirection, state.cameraY);
            g2d.setColor(Color.BLACK);
            g2d.setFont(g2d.getFont().deriveFont(18.0f));
            g2d.drawString(str, 10, 700);
        }
        lastRender = currentRender;
        // Draw GAME OVER
        if (state.gameOver && !state.won) {
            String str = "GAME OVER";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }

        if (state.won) {
            String str = "You Won";
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
            bullets.get(i).fire(g2d,  state);
            if (!bullets.get(i).removed && serverOrClient && multiplayer) {
                server.networkWriteBullet(bullets.get(i));
            }
            else if (!bullets.get(i).removed && multiplayer) {
                client.networkWriteBullet(bullets.get(i));
            }
        }

    }

    public void updateEnemiesState (LinkedList <Enemy> enemies) {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).updateRectangles();
            if (enemies.get(i).health <= 0)
                enemies.get(i).alive = false;
        }
    }

    public void checkIfGameIsOver () {
        if (state.mainTankHealth <= 0)
            state.gameOver = true;
    }

    public void updatePrizes () {
        if (multiplayer) {
            for (int i = 0; i < map.prizes.size(); i++) {
                if (!map.prizes.get(i).usable) {
                    if (serverOrClient && multiplayer) {
                        server.networkWritePregnableWallsAndPrizes(map);
                    } else if (multiplayer) {
                        client.networkWritePregnableWallsAndPrizes(map);
                    }
                    map.prizes.remove(i);
                }
            }
        }
    }

    public void updateHealth(Graphics2D g2d, LinkedList<Enemy> enemies, ArrayList<Bullet> bullets,Map map) {
        for (int j = 0; j < bullets.size(); j++) {
            /**
             * case 1 : if enemies fire a bullet toward the main tank
             */
            if (bullets.get(j).bulletRectangle.intersects(state.mainTankRectangle) && bullets.get(j).firedByEnemy &&
                    !bullets.get(j).removed) {
                g2d.drawImage(bullets.get(j).bulletExplodedImg, bullets.get(j).bulletCenterLocX, bullets.get(j).bulletCenterLocY, null);
                state.mainTankHealth -= EnemyBullet.damagingPower;
                SoundPlayer soundPlayer = new SoundPlayer();
                soundPlayer.bulletToMainTank();
                bullets.get(j).removed = true;
            }
            /**
             * case 4 : bullets fired toward pregnable walls
             */
            for (int i = 0; i < map.pregnableWalls.size(); i++) {
                if (bullets.get(j).bulletRectangle.intersects(map.pregnableWalls.get(i).imgRectangle) &&
                        map.pregnableWalls.get(i).obstacle && !bullets.get(j).removed) {
                    map.pregnableWalls.get(i).numOfBulletCollisions++;
                    bullets.get(j).removed = true;
                    SoundPlayer soundPlayer = new SoundPlayer();
                    soundPlayer.crushedWall();
                    if (serverOrClient && multiplayer) {
                        System.out.println("------------------------------------------------> Pregnable server");
                        server.networkWritePregnableWallsAndPrizes(map);
                    }
                    else if (multiplayer) {
                        System.out.println("------------------------------------------------> Pregnable client");
                        client.networkWritePregnableWallsAndPrizes(map);
                    }
                }
            }


            for (int i = 0; i < enemies.size(); i++) {
//                g2d.drawRect(enemies.get(i).locX,enemies.get(i).locY,enemies.get(i).enemyWidth,enemies.get(j).enemyHeight);
  //              g2d.fillRect(enemies.get(i).locX,enemies.get(i).locY,enemies.get(i).enemyWidth,enemies.get(j).enemyHeight);

                /**
                 * case 2 : if main tank fires a bullet toward the enemies
                 */
                if (bullets.get(j).bulletRectangle.intersects(enemies.get(i).enemyRectangle) && !bullets.get(j).firedByEnemy &&
                        !bullets.get(j).removed && enemies.get(i).alive) {
                    g2d.drawImage(bullets.get(j).bulletExplodedImg, bullets.get(j).bulletCenterLocX, bullets.get(j).bulletCenterLocY, null);
                    if (bullets.get(j) instanceof HeavyBullet)
                        enemies.get(i).health -= HeavyBullet.damagingPower;
                    else if (bullets.get(j) instanceof MachineGunBullet)
                        enemies.get(i).health -= MachineGunBullet.damagingPower;
                    System.out.println("enemies health = " + enemies.get(i).health);
                    if (enemies.get(i).health <= 0)
                        enemies.get(i).alive = false;
                    bullets.get(j).removed = true;
                    if (serverOrClient && multiplayer) {
                        server.networkWriteEnemies(enemies);
                    }
                    else if (multiplayer) {
                        client.networkWriteEnemies(enemies);
                    }
                }
            }
        }
        /**
         * case 3 : if explosive enemies collide with the main tank
         */

        /**
         * case 5 : when the main tank wants to go over non-explosive enemies
         */


        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).enemyRectangle.intersects(state.mainTankRectangle) &&
                            enemies.get(i).explodeInCollisions && enemies.get(i).alive)
            {
                g2d.drawImage(Bullet.bulletExplodedImg, enemies.get(i).locX, enemies.get(i).locY, null);
                state.mainTankHealth -= 25;
                enemies.get(i).health = 0;
                enemies.get(i).alive = false;
                SoundPlayer soundPlayer = new SoundPlayer();
                soundPlayer.mineBoom();
                if (serverOrClient && multiplayer) {
                    server.networkWriteEnemies(enemies);
                }
                else if (multiplayer) {
                    client.networkWriteEnemies(enemies);
                }
            }

            else if (state.mainTankRectangle.intersects(enemies.get(i).enemyRectangle) &&
                    !enemies.get(i).explodeInCollisions && enemies.get(i).alive)
            {
                if (state.tankDirection == 1)
                    state.tankCenterX -= 8;
                if (state.tankDirection == 3)
                    state.tankCenterX += 8;
                if (state.tankDirection == 2)
                    state.tankCenterY += 8;
                if (state.tankDirection == 4)
                    state.tankCenterY -= 8;
            }
        }
    }

    public void serializeAndSave (ArrayList ar,String fileName) {
        try {
            fos = new FileOutputStream(new File(fileName));
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(ar);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void serializeAndSave (LinkedList ar,String fileName) {
        try {
            fos = new FileOutputStream(new File(fileName));
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(ar);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSavedInfo () {
        File f1 = new File("enemies.jtank");
        File f2 = new File("state.jtank");
        File f3 = new File("map.jtank");
        f1.delete();
        f2.delete();
        f3.delete();
    }

    public void deserializeAndUpdate (String fileName) {
        try {
            fis = new FileInputStream(new File(fileName));
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
            if (fileName.equals("enemies.jtank")) {
                LinkedList<Enemy> temp = (LinkedList<Enemy>) ois.readObject();
                for (int i = 0; i < temp.size(); i++) {
                    enemies.get(i).centerTileX = temp.get(i).centerTileX;
                    enemies.get(i).centerTileY = temp.get(i).centerTileY;
                    enemies.get(i).health = temp.get(i).health;
                    enemies.get(i).alive = temp.get(i).alive;
                }
            }
        /*    else if (fileName.equals("bullets.jtank")) {
                ArrayList<Bullet> temp = (ArrayList<Bullet>) ois.readObject();
                for (int i = 0; i < temp.size(); i++) {
                    bullets.get(i).bulletCenterLocX = temp.get(i).bulletCenterLocX;
                    bullets.get(i).bulletCenterLocY = temp.get(i).bulletCenterLocY;
                    bullets.get(i).removed = temp.get(i).removed;
                    bullets.get(i).bulletAngle = temp.get(i).bulletAngle;
                }
            } */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
