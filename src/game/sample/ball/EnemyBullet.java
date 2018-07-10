package game.sample.ball;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class EnemyBullet extends Bullet {
    public EnemyBullet(int bulletLocX, int bulletLocY, double bulletAngle, boolean firedByEnemy) {
        super(bulletLocX, bulletLocY, bulletAngle, firedByEnemy, 25);
        try {
            bulletImg = ImageIO.read(new File("enemyBullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bulletSpeed = 12;
    }
}
