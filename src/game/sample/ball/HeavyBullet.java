package game.sample.ball;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * a type of bullet aka cannon
 * it has higher damaging power
 */
public class HeavyBullet extends Bullet {

    public static transient int damagingPower = 50;

    public HeavyBullet(int bulletLocX, int bulletLocY, double bulletAngle, boolean firedByEnemy) {
        super(bulletLocX, bulletLocY, bulletAngle, firedByEnemy);
        try {
            bulletImg = ImageIO.read(new File("heavyBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bulletSpeed = 10;
    }
}
