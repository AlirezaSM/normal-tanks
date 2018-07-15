package game.sample.ball;

/**
 * this is another one of prizes which
 * you can find them if you have patience
 *
 */

public class GunUpdatePrize extends Prize {
    public GunUpdatePrize( double centerTileX, double centerTileY) {
        super("redstar.png", centerTileX, centerTileY);
    }

    @Override
    public void checkCollisionWithTank(GameState state, Map map) {
        super.checkCollisionWithTank(state,map);
        if (state.mainTankRectangle.intersects(this.imgRectangle) && usable) {
            HeavyBullet.damagingPower += 50;
            System.out.println(HeavyBullet.damagingPower);
            usable = false;
            SoundPlayer soundPlayer = new SoundPlayer();
            soundPlayer.refillBullets();
        }
    }
}
