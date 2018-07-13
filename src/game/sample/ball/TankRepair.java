package game.sample.ball;

public class TankRepair extends Prize {

    public TankRepair(double centerTileX, double centerTileY) {
        super("tankRepair.png", centerTileX, centerTileY);
    }

    @Override
    public void checkCollisionWithTank(GameState state,Map map) {
        super.checkCollisionWithTank(state,map);
        if (state.mainTankRectangle.intersects(this.imgRectangle) && usable) {
            state.mainTankHealth += 100;
            usable = false;
        }
    }
}
