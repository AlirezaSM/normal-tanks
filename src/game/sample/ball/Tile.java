package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tile {
    BufferedImage img;
    int size;

    public Tile(String img) {
        size = 8;
        try {
            ImageIO.read(new File(img));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
