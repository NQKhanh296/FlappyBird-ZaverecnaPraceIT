import javax.sound.sampled.*;
import java.io.File;
import java.util.Objects;

public class SFXImporter {
    private Clip sfx;
    public SFXImporter(String fileName){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream(fileName)));
            sfx = AudioSystem.getClip();
            sfx.open(audioInputStream);
        }catch (Exception e){
            System.out.println("Failed to open file!");
        }
    }
    public void play(){
        sfx.start();
        sfx.setMicrosecondPosition(0);
    }

    public Clip getSfx() {
        return sfx;
    }
}
