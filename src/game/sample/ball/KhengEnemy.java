package game.sample.ball;

/**
 * one type of enemies which are in this
 * game
 */
public class KhengEnemy extends Enemy {
    public KhengEnemy(int tileX, int tileY) {
        super("constantEnemy.png", tileX, tileY,
                0.1,1,100,20,0,0, true);

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
