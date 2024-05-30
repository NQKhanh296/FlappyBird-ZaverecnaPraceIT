import java.awt.*;
public class Ground {
    private int x;
    private final int y;
    private final int width;
    private final int height;
    private final Image image;
    public Ground(int x, int y ,Image img) {
        this.x = x;
        image = img;
        height = image.getHeight(null);
        width = 504;
        this.y = y;
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
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Image getImage() {
        return image;
    }
}