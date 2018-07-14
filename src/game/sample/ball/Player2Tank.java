package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player2Tank  {


    public int tankCenterX,tankCenterY;
    public double tankCenterTileX,tankCenterTileY;
    public double tankBodyAngle,tankGunAngle;
    public  boolean isUsingHeavyGun;
    public static transient BufferedImage mainTank, heavyGun, machineGun;

    public Player2Tank() {
        tankCenterX = 190;
        tankCenterY = 190;
        //
        tankBodyAngle = 0;
        //

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
        int startTile = GameState.cameraY / Tile.tileHeight;
        int locX = (int) (tankCenterTileX * Tile.tileWidth);
        int locY = (int) (Map.screenHeight - (tankCenterTileY - startTile) * Tile.tileHeight);
        g2d.drawImage(GameFrame.rotatePic(mainTank, tankBodyAngle),
               locX - 90, locY - 90, null);

        if (isUsingHeavyGun) {
            g2d.drawImage(GameFrame.rotatePic(heavyGun, tankGunAngle),
                    locX - 90, locY - 90, null);
        }
        else {
            g2d.drawImage(GameFrame.rotatePic(machineGun, tankGunAngle),
                    locX - 90, locY - 90, null);
        }
    }
}
