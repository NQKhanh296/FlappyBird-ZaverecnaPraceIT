import javax.swing.*;
import java.awt.*;
import java.util.Objects;
public class Bird {
    public static int WIDTH;
    public static int HEIGHT;
    public static int Y;
    private int x;
    private int y;
    private final int birdHeight;
    private final int birdWidth;
    private int index;
    private int delay;
    private Image birdImage;
    private final Image birdDownImg;
    private final Image birdUpImg;
    private final Image birdNormal;
    private final Image deadBird;
    private final Timer timer;
    public Bird() {
        Y = Game.HEIGHT / 3;
        birdDownImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("birdDown.png"))).getImage();
        birdUpImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("birdUp.png"))).getImage();
        birdNormal = new ImageIcon(Objects.requireNonNull(getClass().getResource("birdnormal.png"))).getImage();
        deadBird = new ImageIcon(Objects.requireNonNull(getClass().getResource("deadBird.png"))).getImage();
        birdImage = birdNormal;
        WIDTH = birdImage.getWidth(null);
        HEIGHT = birdImage.getHeight(null);
        birdWidth = WIDTH;
        birdHeight = HEIGHT;
        y = Y;
        index = 0;
        timer = new Timer(100, e -> {
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
    public void setTimer(boolean b) {
        if(b){
            timer.start();
        }
        if(!b){
            timer.stop();
        }
    }
    public void setDelay(int number) {
        switch(number){
            case 1 -> delay = 100;
            case 2 -> delay = 40;
        }
    }
}