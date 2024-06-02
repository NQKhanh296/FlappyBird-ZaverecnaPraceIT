import javax.swing.*;
import java.awt.*;
public class Bird {
    public static int WIDTH;
    public static int HEIGHT;
    public static int Y;
    private int x;
    private int y;
    private final int birdHeight;
    private final int birdWidth;
    private int index;
    private Image birdImage;
    private final Image birdDownImg;
    private final Image birdUpImg;
    private final Image birdNormal;
    private final Image deadBird;
    private final Timer idleTimer;
    private final Timer flyingTimer;
    public Bird() {
        Y = Game.HEIGHT / 3;
        birdDownImg = Resources.birdDownImg;
        birdUpImg = Resources.birdUpImg;
        birdNormal = Resources.birdNormalImg;
        deadBird = Resources.deadBird;
        birdImage = birdNormal;
        WIDTH = birdImage.getWidth(null);
        HEIGHT = birdImage.getHeight(null);
        birdWidth = WIDTH;
        birdHeight = HEIGHT;
        y = Y;
        index = 0;
        idleTimer = new Timer(105, e -> {
            index++;
            if (index > 3) {
                index = 1;
            }
            switchImage(index);
        });
        flyingTimer = new Timer(1000/45, e -> {
            index++;
            if (index > 3) {
                index = 1;
            }
            switchImage(index);
        });
    }
    public void switchImage(int swap){
        switch (swap){
            case 1 -> birdImage = birdDownImg;
            case 2 -> birdImage = birdNormal;
            case 3 -> birdImage = birdUpImg;
            case 4 -> birdImage = deadBird;
        }
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getBirdHeight() {
        return birdHeight;
    }
    public int getBirdWidth() {
        return birdWidth;
    }
    public Image getBirdImage() {
        return birdImage;
    }
    public void setTimer(int timer, boolean start) {
        if((timer == 1 && start)){
            idleTimer.start();
        }
        if((timer == 1 && !start)){
            idleTimer.stop();
        }
        if((timer == 2 && start)){
            flyingTimer.start();
        }
        if((timer == 2 && !start)){
            flyingTimer.stop();
        }
    }
}