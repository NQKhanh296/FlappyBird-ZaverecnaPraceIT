import javax.swing.*;
import java.awt.*;

public class Pipe {
    private int x;
    private int y;
    private int pipeHeight;
    private int pipeWidth;
    private Image pipeImage;
    private boolean passed;

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
    public int getPipeWidth() {
        return pipeWidth;
    }
    public Image getPipeImage() {
        return pipeImage;
    }
    public boolean isPassed() {
        return passed;
    }
    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
