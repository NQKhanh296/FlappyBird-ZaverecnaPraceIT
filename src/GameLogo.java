import javax.swing.*;
import java.awt.*;

public class GameLogo {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image logoImage;

    public GameLogo() {
        logoImage = new ImageIcon(getClass().getResource("flappyBirdGameLogo.png")).getImage();
        width = logoImage.getWidth(null);
        height = logoImage.getHeight(null);
        x = (Game.WIDTH - width - Bird.WIDTH)/2;
        y = Game.HEIGHT / 3;
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
