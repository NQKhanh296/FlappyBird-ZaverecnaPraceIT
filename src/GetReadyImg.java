import javax.swing.*;
import java.awt.*;

public class GetReadyImg {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image getReadyImage;

    public GetReadyImg() {
        x = 30;
        y = 200;
        width = 276;
        height = 75;
        getReadyImage = new ImageIcon(getClass().getResource("getReadyImage.png")).getImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getGetReadyImage() {
        return getReadyImage;
    }
}
