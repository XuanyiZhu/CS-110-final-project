
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/*
 *  this class include all the methods related to paly music
 */
/**
 *
 * @author Xuanyi Zhu & Tariye Peter
 */
public class Sound {

    private Clip clip;
    public static Sound sound1 = new Sound("/HitBorderAndBoard.wav");
    public static Sound sound2 = new Sound("/Sound.wav");
    public static Sound sound3 = new Sound("/Music.wav");
    public static Sound sound4 = new Sound("/GameOver.wav");

    public Sound(String fileName) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //play music 
    public void play() {
        try {
            if (clip != null) {
                new Thread() {
                    public void run() {
                        synchronized (clip) {
                            clip.stop();
                            clip.setFramePosition(0);
                            clip.start();
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //stop the music
    public void stop() {
        if (clip == null) {
            return;
        }
        clip.stop();
    }

    //loop the music
    public void loop() {
        try {
            if (clip != null) {
                new Thread() {
                    public void run() {
                        synchronized (clip) {
                            clip.stop();
                            clip.setFramePosition(0);
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //check if the music is playing
    public boolean isActive() {
        return clip.isActive();
    }


}
