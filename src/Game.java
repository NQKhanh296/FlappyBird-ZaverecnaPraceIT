import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel {
    private int width;
    private int height;
    private Image background;
    private Image bottomPipe;
    private Image topPipe;
    private ArrayList<Pipe> pipes;
    private Bird bird;
    private Timer gameTimer;
    private Timer placePipesTimer;
    private int birdVelocity;
    private int pipeVelocity;
    private boolean gameOver;
    public Game() {
        width = 360;
        height = 640;
        gameOver = false;
        setPreferredSize(new Dimension(width, height));
        pipes = new ArrayList<>();
        background = new ImageIcon(getClass().getResource("flappybirdbg.png")).getImage();
        bottomPipe = new ImageIcon(getClass().getResource("bottompipe.png")).getImage();
        topPipe = new ImageIcon(getClass().getResource("toppipe.png")).getImage();
        bird = new Bird();
        gameTimer = new Timer(1000/60, e -> {
            if(!gameOver){
                birdVelocity += 1;
                bird.setY(bird.getY() + birdVelocity);
                pipeVelocity = -1;
                for(Pipe pipe : pipes){
                    pipe.setX(pipe.getX() + pipeVelocity);
                }
            }
            repaint();
        });
        placePipesTimer = new Timer(1500, e -> {
            if(!gameOver){
                placePipes();

            }
        });
        gameTimer.start();
        placePipesTimer.start();
        setFocusable(true);
    }
    public void placePipes(){
        Pipe toppipe = new Pipe(topPipe);
        Random random = new Random();
        int openingSpace = 160;
        int randomPipeY = random.nextInt(300)+30;
        toppipe.setX(width);
        toppipe.setY(randomPipeY);

        Pipe bottompipe = new Pipe(bottomPipe);
        bottompipe.setX(width);
        bottompipe.setY(toppipe.getY() + toppipe.getPipeHeight() + openingSpace);
        pipes.add(toppipe);
        pipes.add(bottompipe);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,0,0,width,height,null);
        g.drawImage(bird.getBirdImage(),bird.getX(), bird.getY(),bird.getBirdWidth(),bird.getBirdHeight(),null);
        for(Pipe pipe : pipes){
            g.drawImage(pipe.getPipeImage(),pipe.getX(),pipe.getY(),pipe.getPipeWidth(),pipe.getPipeHeight(),null);
        }
    }
}
