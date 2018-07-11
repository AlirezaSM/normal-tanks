package game.sample.ball;

public class PregnableWall extends ImageOnMap {
    int numOfBulletCollisions = 0;

    public PregnableWall(double centerTileX, double centerTileY) {
        super("softWall.png", centerTileX, centerTileY,true);
    }

    public void updateWall () {
        switch (numOfBulletCollisions) {
            case 0:
                this.setImg("softWall.png");
                break;
            case 1:
                this.setImg("softWall1.png");
                break;
            case 2:
                this.setImg("softWall2.png");
                break;
            case 3:
                this.setImg("softWall3.png");
                break;
            case 4:
                Map.pregnableWalls.remove(this);
                break;
        }
    }
}
