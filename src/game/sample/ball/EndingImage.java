package game.sample.ball;

/**
 * this is the finishing image of the game
 * you need to pass it bro!
 */
public class EndingImage extends ImageOnMap {
    public EndingImage(double centerTileX, double centerTileY) {
        super("endImage.png", centerTileX, centerTileY, false);
    }

    @Override
    public void checkForCollisions(GameState state) {
        if (state.mainTankRectangle.intersects(imgRectangle)) {
            GameState.won = true;
            GameState.gameOver = true;
        }
    }
}
