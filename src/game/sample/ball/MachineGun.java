package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MachineGun extends Enemy {

    transient BufferedImage tankGun;
    public MachineGun() {
        super("MachineTank.png", 8, 80,
                0.1, 2, 200,0,20,75, false);
        try {
            tankGun = ImageIO.read(new File("MachineTankGun.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(Menu.gameLevel.equals("Easy")){
            health = 100;
            speed = 0.05;
        }else if(Menu.gameLevel.equals("Normal")) {
            health = 300;
            speed = 0.1;
        }else if(Menu.gameLevel.equals("Hard")){
            health = 500;
            speed = 0.15;
        }
    }

    public void draw (Graphics2D g2d, GameState state) {
        if (alive) {

            g2d.setColor(Color.RED);
            g2d.fillOval(locX + 20,locY + 20,10,10);
            g2d.fillOval(locX - 40, locY + 20, 10, 10);
            g2d.drawImage(enemyImg, locX, locY, null);
            g2d.drawImage(GameFrame.rotatePic(tankGun,movingAngle + 1.57),locX - 40,locY + 20,null);
            checkTriggered(state);
        }
    }

    public void draw () {

    }
}
