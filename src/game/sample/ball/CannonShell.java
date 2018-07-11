package game.sample.ball;

public class CannonShell extends Prize {

    public CannonShell(double centerTileX, double centerTileY) {
        super("cannonShell.png", centerTileX, centerTileY);
    }

    @Override
    public void checkCollisionWithTank() {
        super.checkCollisionWithTank();
        if (GameState.mainTankRectangle().intersects(this.imgRectangle)) {
            GameState.numOfHeavyBullets += 15;
            Map.prizes.remove(this);
        }
    }
}
