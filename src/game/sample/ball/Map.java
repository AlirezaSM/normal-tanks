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
    HashMap<Integer,Integer> plantedSoilPoints = new HashMap<>();
    HashMap<Integer,BufferedImage> mapImages = new HashMap<>();



    public Map() {
        try {
            soilImg = ImageIO.read(new File("soil.png"));
            plantImg = ImageIO.read(new File("plant.png"));
            wallImg = ImageIO.read(new File("wall.png"));
            plantedSoilImg = ImageIO.read(new File("plantedSoil.png"));
            hardWall1Img = ImageIO.read(new File("hardWall1.png"));
            hardWall2Img = ImageIO.read(new File("hardWall2.png"));
            hardWall3Img = ImageIO.read(new File("hardWall3.png"));
            hardWall4Img = ImageIO.read(new File("hardWall4.png"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        mapImages.put(soil,soilImg);
        mapImages.put(plant,plantImg);
        mapImages.put(plantedSoil,plantedSoilImg);
        mapImages.put(wall,wallImg);
        mapImages.put(hardWall,hardWallImg);
        mapImages.put(hardWall1,hardWall1Img);
        mapImages.put(hardWall2,hardWall2Img);
        mapImages.put(hardWall3,hardWall3Img);
        mapImages.put(hardWall4,hardWall4Img);
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
            for (int j = 0; j < 10;j++) {
                tiles[i][j] = new Tile(wall,true);
            }
        }
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
                    int tileImg = tiles[i][j].getImg();
                    g2d.drawImage(mapImages.get(tileImg), i * Tile.tileWidth, screenHeight - (j - startingY / Tile.tileHeight) * Tile.tileHeight, null);
                }
            }
        }
    }

    public static Tile[][] getTiles() {
        return tiles;
    }
}
