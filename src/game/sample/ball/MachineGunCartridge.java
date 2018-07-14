package game.sample.ball;

public class MachineGunCartridge extends Prize {
    public MachineGunCartridge(double centerTileX, double centerTileY) {
        super("machineGunCartridge.png", centerTileX, centerTileY);
    }

    @Override
    public void checkCollisionWithTank(GameState state, Map map) {
        super.checkCollisionWithTank(state,map);
        if (state.mainTankRectangle.intersects(this.imgRectangle) && usable) {
            state.numOfMachineGunBullets += 100;
            usable = false;

            SoundPlayer soundPlayer = new SoundPlayer();
            soundPlayer.refillBullets();
        }
    }
}
