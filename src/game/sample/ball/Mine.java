package game.sample.ball;

public class Mine extends Enemy {
    public Mine(int tileX, int tileY) {
        super("mine.png", tileX, tileY, 0
                , 0, 100, 0,0,0, true);

        if(Menu.gameLevel.equals("Easy")){
            health = 250;
        }else if(Menu.gameLevel.equals("Normal")) {
            health = 350;
        }else if(Menu.gameLevel.equals("Hard")){
            health = 450;
        }

    }
}
