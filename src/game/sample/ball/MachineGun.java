package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MachineGun extends Enemy {

    BufferedImage tankGun;
    public MachineGun() {
        super("MachineTank.png", 8, 80,
                0.1, 2, 200,0,20,75);
        try {
            tankGun = ImageIO.read(new File("MachineTankGun.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw (Graphics2D g2d) {
        if (alive) {

            g2d.setColor(Color.RED);
            g2d.fillOval(locX + 20,locY + 20,10,10);
            g2d.fillOval(locX - 40, locY + 20, 10, 10);
            g2d.drawImage(enemyImg, locX, locY, null);
            g2d.drawImage(GameFrame.rotatePic(tankGun,movingAngle + 1.57),locX - 40,locY + 20,null);
            g2d.drawRect(locX,locY,enemyWidth,enemyHeight);
            g2d.fillRect(locX,locY,enemyWidth,enemyHeight);
            checkTriggered();
        }
    }

    public void draw () {

    }
}
