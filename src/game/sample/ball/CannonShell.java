package game.sample.ball;

public class CannonShell extends Prize {

    public CannonShell(double centerTileX, double centerTileY) {
        super("cannonShell.png", centerTileX, centerTileY);
    }

    @Override
    public void checkCollisionWithTank(GameState state,Map map) {
        super.checkCollisionWithTank(state,map);
        if (state.mainTankRectangle.intersects(this.imgRectangle) && usable) {
            state.numOfHeavyBullets += 15;
            usable = false;
        }
    }
}
