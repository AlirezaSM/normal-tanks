package game.sample.ball;

public class TankRepair extends Prize {

    /**
     * this is another one of prizes which fill
     * your health
     * @param centerTileX
     * @param centerTileY
     */
    public TankRepair(double centerTileX, double centerTileY) {
        super("tankRepair.png", centerTileX, centerTileY);
    }

    /**
     * collison of tank
     * @param state
     * @param map
     */
    @Override
    public void checkCollisionWithTank(GameState state,Map map) {
        super.checkCollisionWithTank(state,map);
        if (state.mainTankRectangle.intersects(this.imgRectangle) && usable) {
            state.mainTankHealth += 100;
            usable = false;

            SoundPlayer soundPlayer = new SoundPlayer();
            soundPlayer.tankRepair();
        }
    }
}
