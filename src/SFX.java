import javax.sound.sampled.*;
import java.util.Objects;

public class SFX {
    private Clip clickSFX;
    private Clip flapSFX;
    private Clip hitSFX;
    private Clip pointSFX;
    private Clip soundTrack;

    public SFX(){
        try {
            AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream("Sfx/clickSFX.wav")));
            clickSFX = AudioSystem.getClip();
            clickSFX.open(audioInputStream1);
            clickSFX.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    clickSFX.setMicrosecondPosition(0);
                }
            });

            AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream("Sfx/flapSFX.wav")));
            flapSFX = AudioSystem.getClip();
            flapSFX.open(audioInputStream2);
            flapSFX.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    flapSFX.setMicrosecondPosition(0);
                }
            });

            AudioInputStream audioInputStream3 = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream("Sfx/hitSFX.wav")));
            hitSFX = AudioSystem.getClip();
            hitSFX.open(audioInputStream3);
            hitSFX.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    hitSFX.setMicrosecondPosition(0);
                }
            });

            AudioInputStream audioInputStream4 = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream("Sfx/pointSFX.wav")));
            pointSFX = AudioSystem.getClip();
            pointSFX.open(audioInputStream4);
            pointSFX.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    pointSFX.setMicrosecondPosition(0);
                }
            });

            AudioInputStream audioInputStream5 = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream("Sfx/FlappyBirdSoundTrack.wav")));
            soundTrack = AudioSystem.getClip();
            soundTrack.open(audioInputStream5);
            soundTrack.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    soundTrack.setMicrosecondPosition(0);
                }
            });
        }catch (Exception e){
            System.out.println("Failed to open .wav file!");
        }
    }


    public Clip getClickSFX() {
        return clickSFX;
    }

    public Clip getFlapSFX() {
        return flapSFX;
    }

    public Clip getHitSFX() {
        return hitSFX;
    }

    public Clip getPointSFX() {
        return pointSFX;
    }

    public Clip getSoundTrack() {
        return soundTrack;
    }
}