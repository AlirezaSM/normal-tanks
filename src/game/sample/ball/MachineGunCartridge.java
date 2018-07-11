package game.sample.ball;

public class MachineGunCartridge extends Prize {
    public MachineGunCartridge(double centerTileX, double centerTileY) {
        super("machineGunCartridge.png", centerTileX, centerTileY);
    }

    @Override
    public void checkCollisionWithTank() {
        super.checkCollisionWithTank();
        if (GameState.mainTankRectangle().intersects(this.imgRectangle)) {
            GameState.numOfMachineGunBullets += 100;
            Map.prizes.remove(this);
        }
    }
}
