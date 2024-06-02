import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class Resources {
    public  static final Image birdDownImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/birdDown.png"))).getImage();
    public  static final Image birdNormalImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/birdnormal.png"))).getImage();
    public  static final Image birdUpImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/birdUp.png"))).getImage();
    public  static final Image bottomPipeImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/bottompipe.png"))).getImage();
    public  static final Image deadBird = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/deadBird.png"))).getImage();
    public  static final ImageIcon easyButtonImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/easyButton.png")));
    public  static final Image flappyBirdBgImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/flappybirdbg.png"))).getImage();
    public  static final Image logoImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/flappyBirdGameLogo.png"))).getImage();
    public  static final Image gameOverImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/gameOverImg.png"))).getImage();
    public  static final Image getReadyImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/getReadyImg.png"))).getImage();
    public  static final Image goldMedalImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/goldMedal.png"))).getImage();
    public  static final Image grassImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/grass.png"))).getImage();
    public  static final Image groundImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/ground.png"))).getImage();
    public  static final Image guideImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/guideImg.png"))).getImage();
    public  static final ImageIcon hardButtonImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/hardButton.png")));
    public  static final ImageIcon normalButtonImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/normalButton.png")));
    public  static final ImageIcon OKButtonImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/OKButton.png")));
    public  static final Image silverMedalImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/silverMedal.png"))).getImage();
    public  static final ImageIcon startButtonImg = new ImageIcon(Objects.requireNonNull(Resources.class.getResource("Images/startButton.png")));
    public  static final Image topPipeImg = new ImageIcon(Objects.requireNonNull(   Resources.class.getResource("Images/toppipe.png"))).getImage();
    public static final File click = new File("src/Sfx/clickSFX.wav");
    public static final File music = new File("src/Sfx/FlappyBirdSoundTrack.wav");
    public static final File flap = new File("src/Sfx/flapSFX.wav");
    public static final File hit = new File("src/Sfx/hitSFX.wav");
    public static final File point = new File("src/Sfx/pointSFX.wav");

}
