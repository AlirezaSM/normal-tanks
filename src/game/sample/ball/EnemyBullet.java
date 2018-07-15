package game.sample.ball;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * a type of bullet which is fired by your enemies
 *
 *
 */

public class EnemyBullet extends Bullet {

    public static transient int damagingPower = 25;

    public EnemyBullet(int bulletLocX, int bulletLocY, double bulletAngle, boolean firedByEnemy) {
        super(bulletLocX, bulletLocY, bulletAngle, firedByEnemy);
        try {
            bulletImg = ImageIO.read(new File("enemyBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bulletSpeed = 12;
    }
}
