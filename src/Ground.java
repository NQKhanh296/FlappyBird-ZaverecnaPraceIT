import javax.swing.*;
import java.awt.*;

public class Ground {
    private int x;
    private final int y;
    private final int groundWidth;
    private final int groundHeight;
    private final Image groundImage;

    public Ground(int x) {
        this.x = x;
        y = 572;
        groundHeight = 68;
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
