package game.sample.ball;

/**
 * a kind of enemy named alien
 */

public class AlienEnemy extends Enemy {
    public AlienEnemy(int tileX, int tileY) {
        super("AlienEnemy.png", tileX, tileY, 0.1
                , 0, 100, 0,0,0, true);

        if(Menu.gameLevel.equals("Easy")){
            health = 100;
            speed = 0.05;
        }else if(Menu.gameLevel.equals("Normal")) {
            health = 300;
            speed = 0.1;
        }else if(Menu.gameLevel.equals("Hard")){
            health = 500;
            speed = 0.15;
        }

    }
}
