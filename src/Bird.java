import javax.swing.*;
import java.awt.*;

public class Bird {
    private int x;
    private int y;
    private int birdHeight;
    private int birdWidth;
    private int index;
    private Image birdImage;
    private Image birdDownImg = new ImageIcon(getClass().getResource("birdDown.png")).getImage();
    private Image birdUpImg = new ImageIcon(getClass().getResource("birdUp.png")).getImage();
    private Image birdNormal = new ImageIcon(getClass().getResource("birdnormal.png")).getImage();
    private Timer timer;


    public Bird() {
        x = 360/8;
        y = 640/3;
        birdWidth = 43;
        birdHeight = 30;
        birdImage = birdNormal;
        index = 0;
        timer = new Timer(120, e -> {
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
