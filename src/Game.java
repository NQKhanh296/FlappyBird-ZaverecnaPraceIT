import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends JPanel {
    private int width;
    private int height;
    private Image background;
    private Image bottomPipe;
    private Image topPipe;
    private ArrayList<Pipe> pipes;
    private Bird bird;
    public Game() {
        width = 360;
        height = 640;
        setPreferredSize(new Dimension(width, height));
        pipes = new ArrayList<>();
        background = new ImageIcon(getClass().getResource("flappybirdbg.png")).getImage();
        bottomPipe = new ImageIcon(getClass().getResource("bottompipe.png")).getImage();
        topPipe = new ImageIcon(getClass().getResource("toppipe.png")).getImage();
        bird = new Bird();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,0,0,width,height,null);
        g.drawImage(bird.getBirdImage(),bird.getX(), bird.getY(),bird.getBirdWidth(),bird.getBirdHeight(),null);
    }
}
