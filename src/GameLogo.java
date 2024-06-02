import java.awt.*;


public class GameLogo {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Image logoImage;
    public GameLogo() {
        logoImage = Resources.logoImg;
        width = logoImage.getWidth(null);
        height = logoImage.getHeight(null);
        x = (int) ((Game.WIDTH - width - Bird.WIDTH)/2.5);
        y = Bird.Y - 12;
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
}