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
        groundImage = new ImageIcon(getClass().getResource("ground.png")).getImage();
        groundHeight = groundImage.getHeight(null);
        groundWidth = groundImage.getWidth(null);
        y = 648 - groundHeight;
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
