import javax.swing.*;
import java.awt.*;

public class Bird {
    private int x;
    private int y;
    private int birdHeight;
    private int birdWidth;
    private Image birdImage;
    private static int HEIGHT = 31;
    private static int WIDTH = 44;
    private Image birdDownImg = new ImageIcon(getClass().getResource("birdDown.png")).getImage();
    private Image birdUpImg = new ImageIcon(getClass().getResource("birdUp.png")).getImage();
    private Image deadBirdImg = new ImageIcon(getClass().getResource("deadBird.png")).getImage();


    public Bird() {
        x = 360/8;
        y = 640/3;
        birdWidth = WIDTH;
        birdHeight = HEIGHT;
        birdImage = birdDownImg;
    }
    public void switchImage(int swap){
        switch (swap){
            case 1 -> birdImage = birdDownImg;
            case 2 -> birdImage = birdUpImg;
            case 3 -> birdImage = deadBirdImg;
        }

    }
    public void switchWidthHeight(int swap){
        switch (swap){
            case 1 -> {
                birdWidth = WIDTH;
                birdHeight = HEIGHT;
            }
            case 2 -> {
                birdWidth = HEIGHT;
                birdHeight = WIDTH;
            }
        }
    }

    public int getX() {
        return x;
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

}
