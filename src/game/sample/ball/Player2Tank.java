package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player2Tank  {


    public int tankCenterX,tankCenterY,cameraY;
    public double tankBodyAngle,tankGunAngle;
    public  boolean isUsingHeavyGun;
    public static transient BufferedImage mainTank, heavyGun, machineGun;

    public Player2Tank() {
        cameraY = 0;
        tankCenterX = 190;
        tankCenterY = 190;
        //
        tankBodyAngle = 0;
        //
        isUsingHeavyGun = true;
        //
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mainTank = ImageIO.read(new File("tankBody.png"));
            heavyGun = ImageIO.read(new File("tankGun.png"));
            machineGun = ImageIO.read(new File ("tankGun02.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw (Graphics2D g2d) {
        g2d.drawImage(GameFrame.rotatePic(mainTank, tankBodyAngle),
               tankCenterX - 90, tankCenterY - 90, null);

        if (isUsingHeavyGun) {
            g2d.drawImage(GameFrame.rotatePic(heavyGun, tankGunAngle),
                    tankCenterX - 90, tankCenterY - 90, null);
        }
        else {
            g2d.drawImage(GameFrame.rotatePic(machineGun, tankGunAngle),
                    tankCenterX - 90, tankCenterY - 90, null);
        }
    }
}
