package game.sample.ball;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * this class is for playing
 * all the sounds throughout
 * this game
 */

public class SoundPlayer {
    public static Clip clip;


    /**
     * this method would play songs
     * @param sound
     */
    public static void play(File sound){
        try{
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();

            //Thread.sleep(clip.getMicrosecondLength() / 1000);
        }catch(Exception e){

        }
    }

    public void stop(){
        clip.stop();
    }

    public boolean isPlaying(){
        if(clip.getMicrosecondPosition() < clip.getMicrosecondLength())
            return true;

        return false;
    }

    public void menuClick(){
        play(new File(".\\sounds\\menuClick.wav"));
    }

    public void menuEntered(){
        play(new File(".\\sounds\\menuEntered.wav"));
    }

    public void heavyShot(){
        play(new File(".\\sounds\\menuClick.wav"));
    }

    public void machinGunShot(){
        play(new File(".\\sounds\\machineGunShot.wav"));
    }

    public void emptyGun(){
        play(new File(".\\sounds\\emptyGun.wav"));
    }

    public void crushedWall(){
        play(new File(".\\sounds\\crushedWall.wav"));
    }

    public void bulletHitWall(){
        play(new File(".\\sounds\\bulletHitWall.wav"));
    }

    public void refillBullets(){
        play(new File(".\\sounds\\refillBullets.wav"));
    }

    public void tankRepair(){
        play(new File(".\\sounds\\tankRepair.wav"));
    }

    public void bulletToMainTank(){
        play(new File(".\\sounds\\EnemyBulletToMyTank.wav"));
    }

    public void mineBoom(){
        play(new File(".\\sounds\\mineBoom.wav"));
    }

    public void tankMove(){
        play(new File(".\\sounds\\tankMove.wav"));
    }
}
