import javax.swing.*;
import java.awt.*;

public class GameLogo {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image logoImage;

    public GameLogo(int x, int y) {
        this.x = x;
        this.y = y;
        width = 445;
        height = 120;
        logoImage = new ImageIcon(getClass().getResource("flappyBirdGameLogo.png")).getImage();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getLogoImage() {
        return logoImage;
    }

    public void setY(int y) {
        this.y = y;
    }
}
