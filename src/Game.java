import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game extends JPanel {
    private int width;
    private int height;
    private Image bottomPipe;
    private Image topPipe;
    private ArrayList<Pipe> pipes;
    public Game() {
        width = 360;
        height = 640;
        pipes = new ArrayList<>();
        setPreferredSize(new Dimension(width, height));
        bottomPipe = new ImageIcon(getClass().getResource("bottompipe.png")).getImage();
        topPipe = new ImageIcon(getClass().getResource("toppipe.png")).getImage();
    }





}
