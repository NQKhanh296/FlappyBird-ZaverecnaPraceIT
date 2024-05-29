import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SFX {
    private Clip flapSFX;
    private Clip hitSFX;
    private Clip pointSFX;

    public SFX() {
        SFXImporter(flapSFX,"SFX/flapSFX.wav");
        SFXImporter(hitSFX,"SFX/hitSFX.wav");
        SFXImporter(pointSFX,"SFX/pointSFX.wav");
    }
    public void SFXImporter(Clip clip, String fileName) {
        File file = new File(fileName);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
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
}
