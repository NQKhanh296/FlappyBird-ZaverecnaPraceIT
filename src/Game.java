import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
            birdVelocity += 1;
            if(!gameOver){
                bird.setY(bird.getY() + birdVelocity);
                bird.setY(Math.max(bird.getY(),0));
                bird.setY(Math.min(bird.getY(), height - bird.getBirdHeight()));
                pipeVelocity = -5;
                for(Pipe pipe : pipes){
                    pipe.setX(pipe.getX() + pipeVelocity);
                }
            }
            repaint();
        });
        placePipesTimer = new Timer(1400, e -> {
            if(!gameOver){
                placePipes();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    birdVelocity = -13;
                    gameTimer.start();
                }
            }
        });
        placePipesTimer.start();
        setFocusable(true);
    }
    public void placePipes(){
        Pipe toppipe = new Pipe(topPipe);
        int openingSpace = 160;
        int randomPipeY = (int) (toppipe.getY() - toppipe.getPipeHeight()/4 - Math.random()*(toppipe.getPipeHeight()/2));
        toppipe.setX(width);
        toppipe.setY(randomPipeY);
        pipes.add(toppipe);

        Pipe bottompipe = new Pipe(bottomPipe);
        bottompipe.setX(width);
        bottompipe.setY(toppipe.getY() + toppipe.getPipeHeight() + openingSpace);
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
