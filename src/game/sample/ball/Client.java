package game.sample.ball;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * this is the client part of
 * socket programming
 */
public class Client {
    Socket client;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public Client(String gameServer) {
        try {
            client = new Socket(InetAddress.getByName(gameServer),8888);
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sends pregnable walls
     * @param map
     */
    public void networkWritePregnableWallsAndPrizes(Map map) {
        try {
            oos.flush();
            oos.reset();
            oos.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sends enemies
     * @param enemies
     */
    public void networkWriteEnemies(LinkedList<Enemy> enemies) {
        try {
            oos.flush();
            oos.reset();
            oos.writeObject(enemies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * send tank state frame by frame
     * @param state
     */

    public void networkWriteState(GameState state) {
        try {
            //   oos = new ObjectOutputStream(server.getOutputStream());
            oos.flush();
            oos.reset();
            oos.writeObject(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sends bullets
     * @param bullet
     */

    public void networkWriteBullet(Bullet bullet) {
        try {
            oos.flush();
            oos.reset();
            oos.writeObject(bullet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method would do required things with
     * the information which is sent at the other
     * end of the network
     *
     * @param map
     * @param player2state
     * @param frame
     * @param g2d
     */
    public void getAndProcessInfo (Map map, Player2Tank player2state, GameFrame frame, Graphics2D g2d) {
        try {

            Object obj = ois.readObject();

            if ( obj instanceof   Map ) {
                Map temp = (Map) obj;
                for (int i = 0; i < temp.prizes.size(); i++) {
                    if (temp.prizes.get(i).usable == false)
                        map.prizes.remove(i);
                }
                for (int i = 0; i < temp.pregnableWalls.size(); i++) {
                    map.pregnableWalls.get(i).numOfBulletCollisions = temp.pregnableWalls.get(i).numOfBulletCollisions;
                }
            }
            else if (obj.getClass() ==  GameState.class) {
                GameState temp = (GameState) obj;
                player2state.tankCenterTileX = temp.tankCenterTileX;
                player2state.tankCenterTileY = temp.tankCenterTileY;
                player2state.tankBodyAngle = temp.tankBodyAngle;
                player2state.tankGunAngle = temp.tankGunAngle;
                player2state.isUsingHeavyGun = temp.isUsingHeavyGun;
            }
            else if (obj instanceof  Bullet) {

                Bullet temp = (Bullet) obj;
                Bullet tempi = new Bullet(temp.bulletCenterLocX, temp.bulletCenterLocY
                        , temp.bulletAngle, false);
                tempi.draw(g2d);


            }
            else if (obj instanceof ArrayList) {
                ArrayList<Bullet> temp = (ArrayList<Bullet>) obj;
                for (int i = 0; i < temp.size(); i++) {
                    if (!temp.get(i).removed) {
                        Bullet tempi = new Bullet(temp.get(i).bulletCenterLocX, temp.get(i).bulletCenterLocY
                                , temp.get(i).bulletAngle, false);
                        tempi.draw(g2d);
                    }
                }
            }
            else if (obj instanceof LinkedList) {
                LinkedList<Enemy> temp = (LinkedList<Enemy>) obj;
                for (int i = 0; i < temp.size(); i++) {
                    frame.enemies.get(i).health = temp.get(i).health;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}