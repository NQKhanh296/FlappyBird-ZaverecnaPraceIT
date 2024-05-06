import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


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
    private double score;
    private int highScore;
    public Game() {
        width = 360;
        height = 640;
        score = 0;
        highScore = 0;
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
                if(birdVelocity >= -13){
                    bird.switchImage(false);
                }if(birdVelocity < 0){
                    bird.switchImage(true);
                }
                bird.setY(bird.getY() + birdVelocity);
                bird.setY(Math.max(bird.getY(),0));
                pipeVelocity = -5;
                for(Pipe pipe : pipes){
                    pipe.setX(pipe.getX() + pipeVelocity);
                    if(!pipe.isPassed() && bird.getX() > pipe.getX() + pipe.getPipeWidth()){
                        score += 0.5;
                        pipe.setPassed(true);

                    }
                    if(collision(bird,pipe)){
                        gameOver = true;
                    }
                }
                if(bird.getY() + bird.getBirdHeight() >= height){
                    gameOver = true;
                }
                if(score>highScore){
                    highScore = (int) score;
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
                Bird b = new Bird();
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    birdVelocity = -13;
                    gameTimer.start();
                    if(gameOver){
                        bird.setY(b.getY());
                        birdVelocity = 0;
                        score = 0;
                        pipes.clear();
                        gameOver = false;
                        gameTimer.start();
                        placePipesTimer.start();
                    }
                }
            }
        });
        placePipesTimer.start();
        setFocusable(true);
        if(gameOver){
            gameTimer.stop();
            placePipesTimer.stop();
        }
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
    public boolean collision(Bird a, Pipe b) {
        return a.getX() < b.getX() + b.getPipeWidth() &&
                a.getX() + a.getBirdWidth() > b.getX() &&
                a.getY() < b.getY() + b.getPipeHeight() &&
                a.getY() + a.getBirdHeight() > b.getY();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,0,0,width,height,null);
        g.drawImage(bird.getBirdImage(),bird.getX(), bird.getY(),bird.getBirdWidth(),bird.getBirdHeight(),null);
        for(Pipe pipe : pipes){
            g.drawImage(pipe.getPipeImage(),pipe.getX(),pipe.getY(),pipe.getPipeWidth(),pipe.getPipeHeight(),null);
        }
        if(!gameOver){
            g.setFont(new Font("Arial", Font.BOLD,30));
            g.setColor(Color.white);
            g.drawString("Score: " + (int)score,5,30);
            g.drawString("High score: " + highScore,5,55);
        }
        if(gameOver){
            g.setFont(new Font("Arial", Font.BOLD,60));
            g.setColor(Color.white);
            g.drawString("Game over", 20,320);
        }
    }
}
