import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class SFXImporter {
    private Clip sfx;
    private AudioInputStream audioInputStream;
    public SFXImporter(String fileName){
        try {
            audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream(fileName)));
            sfx = AudioSystem.getClip();
            sfx.open(audioInputStream);
            sfx.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    sfx.setMicrosecondPosition(0);
                }
            });
        }catch (Exception e){
            System.out.println("Failed to open .wav file!");
        }
    }
    public void play(){
        if(sfx!=null) {
            sfx.start();
        }
    }
    public Clip getSfx() {
        return sfx;
    }
    public void stop(){
        if(sfx!=null) {
            sfx.stop();
        }
    }
    public void close(){
        if(sfx!=null) {
            sfx.close();
        }
    }
    public void open(){
        if(sfx!=null) {
            try {
                sfx.open(audioInputStream);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setMicrosecondPosition(int microsecondPosition) {
        if(sfx!=null) {
            sfx.setMicrosecondPosition(microsecondPosition);
        }
    }
}
