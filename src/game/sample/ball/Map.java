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
    public static int tileHorizontalSize = 32;
    public static int tileVerticalSize = 24;
    public static int numOfHorizontalTiles = 40;
    public static int numOfVerticalTiles = 150;
    public static int numOfPlantedSoilPlants = 400;
    static int [][] tiles = new int[numOfHorizontalTiles][numOfVerticalTiles];
    public BufferedImage soilImg;
    public BufferedImage plantImg;
    public BufferedImage wallImg;
    public BufferedImage plantedSoilImg;
    HashMap<Integer,Integer> plantedSoilPoints = new HashMap<>();
    HashMap<Integer,BufferedImage> mapImages = new HashMap<>();



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
        Random r = new Random();

        for (int i = 0; i < numOfHorizontalTiles;i++) {
            for (int j = 0; j < numOfVerticalTiles; j++) {
                tiles[i][j] = soil;
            }
        }

        for (int i = 0; i < numOfPlantedSoilPlants;i++ )
            tiles[r.nextInt(numOfHorizontalTiles)][r.nextInt(numOfVerticalTiles)] = plantedSoil;

    }

    public void designMap () {

        for (int i = 30; i < 33; i++){
            for (int j = 0; j < 10;j++) {
                tiles[i][j] = wall;
            }
        }
        for (int i = 30; i < 33; i++){
            for (int j = 20; j < numOfVerticalTiles;j++) {
                tiles[i][j] = wall;
            }
        }

    }

    public void drawMap (Graphics2D g2d,int startingY) {

        for (int i = 0; i < numOfHorizontalTiles; i++) {
            if (startingY / tileVerticalSize >= 0) {
                for (int j = startingY / tileVerticalSize; j < ((startingY / tileVerticalSize) + (numOfVerticalTiles / 5)); j++) {
                    int tileImg = tiles[i][j];
                    g2d.drawImage(mapImages.get(tileImg), i * tileHorizontalSize, 720 - (j - startingY / tileVerticalSize) * tileVerticalSize, null);
                }
            }
        }
    }

    public static int[][] getTiles() {
        return tiles;
    }
}
