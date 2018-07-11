package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


public class Map implements Serializable{
    public static int soil = 1;
    public static int plant = 2;
    public static int plantedSoil = 3;
    public static int wall = 4;
    public static int hardWall = 5;
    public static int hardWall1 = 6;
    public static int hardWall2 = 7;
    public static int hardWall3 = 8;
    public static int hardWall4 = 9;
    public static int numOfPlantedSoilPlants = 400;
    public static int numOfVerticalScreens = 5;
    public static int screenHeight = 720;
  //  public static double screenRatio = 16 / 9;
    static Tile [][] tiles = new Tile[Tile.numOfHorizontalTiles][Tile.numOfVerticalTiles];
    public BufferedImage soilImg;
    public BufferedImage plantImg;
    public BufferedImage wallImg;
    public BufferedImage plantedSoilImg;
    public BufferedImage hardWallImg;
    public BufferedImage hardWall1Img;
    public BufferedImage hardWall2Img;
    public BufferedImage hardWall3Img;
    public BufferedImage hardWall4Img;
    static HashMap<Integer,BufferedImage> mapImages = new HashMap<>();
    static ArrayList<ImageOnMap> imagesOnMap = new ArrayList<>();
    static ArrayList<PregnableWall> pregnableWalls = new ArrayList<>();





    public Map() {
        try {
            soilImg = ImageIO.read(new File("soil.png"));
            plantImg = ImageIO.read(new File("plant.png"));
            wallImg = ImageIO.read(new File("wall.png"));
            plantedSoilImg = ImageIO.read(new File("plantedSoil.png"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        mapImages.put(soil,soilImg);
        mapImages.put(plant,plantImg);
        mapImages.put(plantedSoil,plantedSoilImg);
        mapImages.put(wall,wallImg);

        imagesOnMap.add(new ImageOnMap("cactus.png",10,70,false));
        imagesOnMap.add(new ImageOnMap("cactus.png",10,110,false));
        imagesOnMap.add(new ImageOnMap("cactus.png",2,60,false));
        imagesOnMap.add(new ImageOnMap("cactus.png",5,45,false));
        imagesOnMap.add(new ImageOnMap("cactus.png",22,130,false));
        imagesOnMap.add(new ImageOnMap("cactus.png",10,10,false));
        imagesOnMap.add(new ImageOnMap("cactus.png",30,120,false));

        pregnableWalls.add(new PregnableWall(28,2));
        pregnableWalls.add(new PregnableWall(28,6));
        pregnableWalls.add(new PregnableWall(28,10));
        pregnableWalls.add(new PregnableWall(31,10));
        pregnableWalls.add(new PregnableWall(34,10));

        Random r = new Random();

        for (int i = 0; i < Tile.numOfHorizontalTiles;i++) {
            for (int j = 0; j < Tile.numOfVerticalTiles; j++) {
                tiles[i][j] = new Tile(soil,false);
            }
        }

        for (int i = 0; i < numOfPlantedSoilPlants;i++ )
            tiles[r.nextInt(Tile.numOfHorizontalTiles)][r.nextInt(Tile.numOfVerticalTiles)] = new Tile(plantedSoil,false);

    }

    public void designMap () {

        for (int i = 30; i < 33; i++){
            for (int j = 20; j < Tile.numOfVerticalTiles;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

    }

    public void drawMap (Graphics2D g2d,int startingY) {

        for (int i = 0; i < Tile.numOfHorizontalTiles; i++) {
            if (startingY / Tile.tileHeight >= 0) {
                for (int j = startingY / Tile.tileHeight; j < ((startingY / Tile.tileHeight) + (Tile.numOfVerticalTiles / 5)); j++) {
                    int tileImg = tiles[i][j].getImgNum();
                    g2d.drawImage(mapImages.get(tileImg), i * Tile.tileWidth, screenHeight - (j - startingY / Tile.tileHeight) * Tile.tileHeight, null);
                }
            }
        }

        for (int i = 0; i < imagesOnMap.size();i++) {
            imagesOnMap.get(i).draw(g2d);
        }

        for (int i = 0; i < pregnableWalls.size();i++) {
            pregnableWalls.get(i).draw(g2d);
        }



    }

    public static Tile[][] getTiles() {
        return tiles;
    }
}
