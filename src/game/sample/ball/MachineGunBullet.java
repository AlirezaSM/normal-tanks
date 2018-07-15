package game.sample.ball;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * this is a subclass representing
 * bullets fired by machine guns
 */
public class MachineGunBullet extends Bullet {

    public static transient int damagingPower = 25;

    public MachineGunBullet(int bulletLocX, int bulletLocY, double bulletAngle, boolean firedByEnemy) {
        super(bulletLocX, bulletLocY, bulletAngle, firedByEnemy);
        try {
            bulletImg = ImageIO.read(new File("machineGunBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bulletSpeed = 20;
    }
}
