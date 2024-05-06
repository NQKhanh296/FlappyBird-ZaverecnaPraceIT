import javax.swing.*;
import java.awt.*;

public class Bird {
    private int x;
    private int y;
    private int birdHeight;
    private int birdWidth;
    private Image birdImage;

    public Bird() {
        x = 360/8;
        y = 640/2;
        birdHeight = 31;
        birdWidth = 44;
        birdImage = new ImageIcon(getClass().getResource("birdUp.png")).getImage();
    }
    public void switchImage(boolean swap){
        if(swap){
            birdImage = new ImageIcon(getClass().getResource("birdDown.png")).getImage();
        }else{
            birdImage = new ImageIcon(getClass().getResource("birdUp,.png")).getImage();
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

    public void setBirdHeight(int birdHeight) {
        this.birdHeight = birdHeight;
    }

    public int getBirdWidth() {
        return birdWidth;
    }

    public void setBirdWidth(int birdWidth) {
        this.birdWidth = birdWidth;
    }

    public Image getBirdImage() {
        return birdImage;
    }

    public void setBirdImage(Image birdImage) {
        this.birdImage = birdImage;
    }
}
