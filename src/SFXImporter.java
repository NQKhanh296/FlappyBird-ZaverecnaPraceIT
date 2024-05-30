import javax.sound.sampled.*;
import java.util.Objects;

public class SFXImporter {
    private Clip sfx;
    public SFXImporter(String fileName){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream(fileName)));
            sfx = AudioSystem.getClip();
            sfx.open(audioInputStream);
            sfx.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    sfx.close();
                }
            });
        }catch (Exception e){
            System.out.println("Failed to open file!");
        }
    }
    public void play(){
        if(sfx!=null){
            sfx.setFramePosition(0);
            sfx.start();
        }
    }

    public Clip getSfx() {
        return sfx;
    }
}
