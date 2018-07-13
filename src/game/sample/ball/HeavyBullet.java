package game.sample.ball;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class HeavyBullet extends Bullet {
    public HeavyBullet(int bulletLocX, int bulletLocY, double bulletAngle, boolean firedByEnemy) {
        super(bulletLocX, bulletLocY, bulletAngle, firedByEnemy, 100);
        try {
            bulletImg = ImageIO.read(new File("heavyBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bulletSpeed = 2;
    }
}
