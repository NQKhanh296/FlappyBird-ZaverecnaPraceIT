import javax.swing.*;
import java.awt.*;

public class Bird {
    private int x;
    private int y;
    public static int WIDTH;
    public static int HEIGHT;
    private final int birdHeight;
    private final int birdWidth;
    private int index;
    private Image birdImage;
    private final Image birdDownImg = new ImageIcon(getClass().getResource("birdDown.png")).getImage();
    private final Image birdUpImg = new ImageIcon(getClass().getResource("birdUp.png")).getImage();
    private final Image birdNormal = new ImageIcon(getClass().getResource("birdnormal.png")).getImage();
    private final Image deadBird = new ImageIcon(getClass().getResource("deadBird.png")).getImage();
    private final Timer timer;


    public Bird() {
        x = Game.WIDTH / 8;
        y = Game.HEIGHT / 3;
        birdImage = birdNormal;
        WIDTH = birdImage.getWidth(null);
        HEIGHT = birdImage.getHeight(null);
        birdWidth = WIDTH;
        birdHeight = HEIGHT;
        index = 0;
        timer = new Timer(110, e -> {
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
}
