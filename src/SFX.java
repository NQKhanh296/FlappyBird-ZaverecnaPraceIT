import javax.sound.sampled.*;

public class SFX {
    private Clip clickSFX;
    private Clip flapSFX;
    private Clip hitSFX;
    private Clip pointSFX;
    private Clip soundTrack;

    public SFX(){
        try {
            clickSFX = AudioSystem.getClip();
            assert Resources.click != null;
            clickSFX.open(AudioSystem.getAudioInputStream(Resources.click));
            clickSFX.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    clickSFX.setMicrosecondPosition(0);
                }
            });

            flapSFX = AudioSystem.getClip();
            assert Resources.flap != null;
            flapSFX.open(AudioSystem.getAudioInputStream(Resources.flap));
            flapSFX.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    flapSFX.setMicrosecondPosition(0);
                }
            });

            hitSFX = AudioSystem.getClip();
            assert Resources.hit != null;
            hitSFX.open(AudioSystem.getAudioInputStream(Resources.hit));
            hitSFX.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    hitSFX.setMicrosecondPosition(0);
                }
            });

            pointSFX = AudioSystem.getClip();
            assert Resources.point != null;
            pointSFX.open(AudioSystem.getAudioInputStream(Resources.point));
            pointSFX.addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    pointSFX.setMicrosecondPosition(0);
                }
            });

            soundTrack = AudioSystem.getClip();
            assert Resources.music != null;
            soundTrack.open(AudioSystem.getAudioInputStream(Resources.music));
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
