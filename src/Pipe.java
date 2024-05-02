import javax.swing.*;
import java.awt.*;

public class Pipe {
    private int x;
    private int y;
    private int pipeHeight;
    private int pipeWidth;
    private Image pipeImage;

    public Pipe(Image img) {
        y = 0;
        pipeHeight = 512;
        pipeWidth = 64;
        pipeImage = img;
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

    public int getPipeHeight() {
        return pipeHeight;
    }

    public void setPipeHeight(int pipeHeight) {
        this.pipeHeight = pipeHeight;
    }

    public int getPipeWidth() {
        return pipeWidth;
    }

    public void setPipeWidth(int pipeWidth) {
        this.pipeWidth = pipeWidth;
    }

    public Image getPipeImage() {
        return pipeImage;
    }

    public void setPipeImage(Image pipeImage) {
        this.pipeImage = pipeImage;
    }
}
