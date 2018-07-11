package game.sample.ball;

public class TankRepair extends Prize {

    public TankRepair(double centerTileX, double centerTileY) {
        super("tankRepair.png", centerTileX, centerTileY);
    }

    @Override
    public void checkCollisionWithTank() {
        super.checkCollisionWithTank();
        if (GameState.mainTankRectangle().intersects(this.imgRectangle)) {
            GameState.mainTankHealth += 100;
            Map.prizes.remove(this);
        }
    }
}
