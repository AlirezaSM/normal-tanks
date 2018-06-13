package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConstantEnemy {
    public BufferedImage enemyImg;
    static Point loc = new Point(500,500);
    Point corrospondingTile;
    public Rectangle rectangle = new Rectangle((int) loc.getX(), (int) loc.getY(),100,100);
    boolean showTank = true;

    public ConstantEnemy () {
        try {
            enemyImg = ImageIO.read(new File("constantEnemy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void collide () {
        if (rectangle.intersects(GameFrame.mainTankRectangle())) {
            showTank = false;
            enemyImg = null;
        }
    }
}
