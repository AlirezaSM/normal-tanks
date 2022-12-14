package game.sample.ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * Map class which contains the
 * order in which pictures, tiles
 * and everything related to the
 * game are shown
 */

public class Map implements Serializable{
    public transient static int soil = 1;
    public transient static int plant = 2;
    public transient static int plantedSoil = 3;
    public transient static int wall = 4;
    public transient static int numOfPlantedSoilPlants = 400;
    public transient static int numOfVerticalScreens = 5;
    public transient static int screenHeight = 720;
  //  public static double screenRatio = 16 / 9;
    static transient Tile [][] tiles = new Tile[Tile.numOfHorizontalTiles][Tile.numOfVerticalTiles];
    public transient BufferedImage soilImg;
    public transient BufferedImage plantImg;
    public transient BufferedImage wallImg;
    public transient BufferedImage plantedSoilImg;
    transient FileOutputStream fos = null;
    transient ObjectOutputStream oos = null;
    transient BufferedOutputStream bos = null;
    transient FileInputStream fis = null;
    transient ObjectInputStream ois = null;
    transient BufferedInputStream bis = null;
    static transient HashMap<Integer,BufferedImage> mapImages = new HashMap<>();
    public transient ArrayList<ImageOnMap> imagesOnMap;
    public ArrayList<PregnableWall> pregnableWalls;
    public ArrayList<Prize> prizes;





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

        imagesOnMap = new ArrayList<>();
        pregnableWalls = new ArrayList<>();
        prizes = new ArrayList<>();

        mapImages.put(soil,soilImg);
        mapImages.put(plant,plantImg);
        mapImages.put(plantedSoil,plantedSoilImg);
        mapImages.put(wall,wallImg);

        imagesOnMap.add(new ImageOnMap("cactus.png",10,70,false));
        imagesOnMap.add(new ImageOnMap("cactus.png",10,110,false));
        imagesOnMap.add(new ImageOnMap("cactus.png",2,60,false));
        imagesOnMap.add(new ImageOnMap("cactus.png",5,45,false));
        imagesOnMap.add(new ImageOnMap("cactus.png",22,130,false));
        imagesOnMap.add(new ImageOnMap("cactus.png",10,12,false));
        imagesOnMap.add(new ImageOnMap("cactus.png",30,120,false));
        imagesOnMap.add(new EndingImage(33,Tile.numOfVerticalTiles - 20));

        pregnableWalls.add(new PregnableWall(Tile.numOfHorizontalTiles - 9,12));
        pregnableWalls.add(new PregnableWall(Tile.numOfHorizontalTiles - 6,12));
        pregnableWalls.add(new PregnableWall(Tile.numOfHorizontalTiles - 3,12));
        pregnableWalls.add(new PregnableWall(3,25));
        pregnableWalls.add(new PregnableWall(6,25));
        pregnableWalls.add(new PregnableWall(9,25));
        pregnableWalls.add(new PregnableWall(9,21));
        pregnableWalls.add(new PregnableWall(9,17));
        pregnableWalls.add(new PregnableWall(15,47));
        pregnableWalls.add(new PregnableWall(18,47));
        pregnableWalls.add(new PregnableWall(121,47));
        pregnableWalls.add(new PregnableWall(124,47));
        pregnableWalls.add(new PregnableWall(14,93));
        pregnableWalls.add(new PregnableWall(17,93));
        pregnableWalls.add(new PregnableWall(20,93));
        pregnableWalls.add(new PregnableWall(23,93));



        prizes.add(new TankRepair(23,93));
        prizes.add(new CannonShell(Tile.numOfHorizontalTiles - 6,12));
        prizes.add(new MachineGunCartridge(23,70));
        prizes.add(new GunUpdatePrize(19, 81));

        Random r = new Random();

        for (int i = 0; i < Tile.numOfHorizontalTiles;i++) {
            for (int j = 0; j < Tile.numOfVerticalTiles; j++) {
                tiles[i][j] = new Tile(soil,false);
            }
        }

        for (int i = 0; i < numOfPlantedSoilPlants;i++ )
            tiles[r.nextInt(Tile.numOfHorizontalTiles)][r.nextInt(Tile.numOfVerticalTiles)] = new Tile(plantedSoil,false);

    }

    /**
     * this method is used to design how different tiles
     * form a map together, put simply, design of the map
     * is done here.
     */

    public void designMap () {

        for (int i = Tile.numOfHorizontalTiles - 2; i < Tile.numOfHorizontalTiles; i++){
            for (int j = 0; j < Tile.numOfVerticalTiles;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

        for (int i = 0; i < 2; i++){
            for (int j = 0; j < Tile.numOfVerticalTiles;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

        for (int i = 0; i < Tile.numOfHorizontalTiles; i++){
            for (int j = 0; j < 3;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

        for (int i = 0; i < Tile.numOfHorizontalTiles; i++){
            for (int j = Tile.numOfVerticalTiles - 14; j < Tile.numOfVerticalTiles;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

        for (int i = 0; i < Tile.numOfHorizontalTiles - 10; i++){
            for (int j = 11; j < 15;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

        for (int i = 10; i < Tile.numOfHorizontalTiles; i++){
            for (int j = 24; j < 28;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

        for (int i = 10; i < 13; i++){
            for (int j = 37; j < 67;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

        for (int i = 25; i < 28; i++){
            for (int j = 37; j < 67;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

        for (int i = 2; i < 13; i++){
            for (int j = 80; j < 83;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

        for (int i = 25; i < Tile.numOfHorizontalTiles - 2; i++){
            for (int j = 80; j < 83;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

        for (int i = 2; i < 13; i++){
            for (int j = 93; j < 96;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

        for (int i = 24; i < Tile.numOfHorizontalTiles - 2; i++){
            for (int j = 93; j < 96;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }

        for (int i = 25; i < Tile.numOfHorizontalTiles; i++){
            for (int j = Tile.numOfVerticalTiles - 27; j < Tile.numOfVerticalTiles - 24;j++) {
                tiles[i][j] = new Tile (wall,true);
            }
        }


    }

    /**
     * this method draw all the images and prizes and tiles
     * and every thing related to map
     * @param g2d
     * @param startingY
     * @param state
     */
    public void drawMap (Graphics2D g2d,int startingY, GameState state) {

        long time1 = System.currentTimeMillis();

        for (int i = 0; i < Tile.numOfHorizontalTiles; i++) {
            if (startingY / Tile.tileHeight >= 0) {
                for (int j = startingY / Tile.tileHeight; j < ((startingY / Tile.tileHeight) + (Tile.numOfVerticalTiles / Map.numOfVerticalScreens)); j++) {
                    int tileImg = tiles[i][j].getImgNum();
                    g2d.drawImage(mapImages.get(tileImg), i * Tile.tileWidth, screenHeight - (j - startingY / Tile.tileHeight) * Tile.tileHeight, null);
                }
            }
        }

        for (int i = 0; i < imagesOnMap.size();i++) {
            imagesOnMap.get(i).draw(g2d, state);
        }

        for (int i = 0; i < prizes.size();i++) {
            prizes.get(i).draw(g2d,state);
            prizes.get(i).checkCollisionWithTank(state,this);
        }

        for (int i = 0; i < pregnableWalls.size();i++) {
            pregnableWalls.get(i).draw(g2d, state);
        }


    }

    public static Tile[][] getTiles() {
        return tiles;
    }

    public static Rectangle tileRectangle (int x, int y) {
        int locX = (int) (x * Tile.tileWidth) - (Tile.tileWidth);
        int locY = (int) (Map.screenHeight - (y - Enemy.startTile) * Tile.tileHeight) + (Tile.tileHeight);
        return (new Rectangle(locX, locY, Tile.tileWidth, Tile.tileHeight));
    }

    public static boolean tileIsVisible (int y) {
         if (y >= Enemy.startTile && y <= (Enemy.startTile + Tile.numOfVerticalTilesInOneScreen))
             return true;
         return false;
    }

    public void serializeAndSave (Map map) {
        try {
            fos = new FileOutputStream(new File("map.jtank"));
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(map);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deserializeAndUpdate () {
        try {
            fis = new FileInputStream(new File("map.jtank"));
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
            Map temp = (Map) ois.readObject();
            if (temp.pregnableWalls.size() != pregnableWalls.size())
                System.out.println("tpreg = " + temp.pregnableWalls.size() + " p = " + pregnableWalls.size());
            if (temp.prizes.size() != prizes.size())
                System.out.println("tprize = " + temp.prizes.size() + " p = " + prizes.size());

            for (int i = 0; i < temp.pregnableWalls.size(); i++) {
                pregnableWalls.get(i).numOfBulletCollisions = temp.pregnableWalls.get(i).numOfBulletCollisions;
            }
            for (int i = 0; i < temp.prizes.size(); i++) {
                prizes.get(i).usable = temp.prizes.get(i).usable;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
