package game.sample.ball;

public class EndingImage extends ImageOnMap {
    public EndingImage(double centerTileX, double centerTileY) {
        super("endImage.png", centerTileX, centerTileY, false);
    }

    @Override
    public void checkForCollisions(GameState state) {
        if (state.mainTankRectangle.intersects(imgRectangle)) {
            state.gameOver = true;
        }
    }
}
