import javax.swing.*;
import java.awt.*;

public class Ground {
    private int x;
    private int y;
    private int groundWidth;
    private int groundHeight;
    private Image groundImage;

    public Ground(int x) {
        this.x = x;
        y = 579;
        groundHeight = 61;
        groundWidth = 360;
        groundImage = new ImageIcon(getClass().getResource("ground.png")).getImage();
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getGroundWidth() {
        return groundWidth;
    }

    public int getGroundHeight() {
        return groundHeight;
    }

    public Image getGroundImage() {
        return groundImage;
    }
}
