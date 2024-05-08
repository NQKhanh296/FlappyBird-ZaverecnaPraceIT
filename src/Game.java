import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;


public class Game extends JPanel {
    private int width;
    private int height;
    private Image background;
    private Image bottomPipe;
    private Image topPipe;
    private ArrayList<Pipe> pipes;
    private ArrayList<Ground> grounds;
    private Bird bird;
    private Timer gameTimer;
    private Timer placePipesTimer;
    private Timer groundMovingTimer;
    private int birdVelocity;
    private int pipeAndGroundVelocity;
    private boolean gameOver;
    private double score;
    private int highScore;
    public Game() {
        width = 360;
        height = 640;
        score = 0;
        highScore = 0;
        pipeAndGroundVelocity = -5;
        gameOver = false;
        setPreferredSize(new Dimension(width, height));
        pipes = new ArrayList<>();
        grounds = new ArrayList<>();
        background = new ImageIcon(getClass().getResource("flappybirdbg.png")).getImage();
        bottomPipe = new ImageIcon(getClass().getResource("bottompipe.png")).getImage();
        topPipe = new ImageIcon(getClass().getResource("toppipe.png")).getImage();
        bird = new Bird();
        gameTimer = new Timer(1000/60, e -> {
            birdVelocity += 1;
            if(!gameOver){
                if(birdVelocity > 0){
                    bird.switchImage(2);
                }if(birdVelocity < 0){
                    bird.switchImage(1);
                }
                bird.setY(bird.getY() + birdVelocity);
                bird.setY(Math.max(bird.getY(),0));
                for(Pipe pipe : pipes){
                    pipe.setX(pipe.getX() + pipeAndGroundVelocity);
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
            movingGround();
        });
        groundMovingTimer = new Timer(1000/60, e -> {
            for(Ground ground : grounds) {
                ground.setX(ground.getX() + pipeAndGroundVelocity);
            }
            repaint();
        });
        groundMovingTimer.start();
        placePipesTimer.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                birdVelocity = -13;
                gameTimer.start();
                if(gameOver){
                    bird.setY(640/3);
                    birdVelocity = 0;
                    score = 0;
                    pipes.clear();
                    gameOver = false;
                    gameTimer.start();
                    placePipesTimer.start();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    birdVelocity = -13;
                    gameTimer.start();
                    if(gameOver){
                        bird.setY(640/3);
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
        setFocusable(true);
        if(gameOver){
            gameTimer.stop();
            placePipesTimer.stop();
            groundMovingTimer.stop();
        }
    }
    public void placePipes(){
        Pipe toppipe = new Pipe(topPipe);
        Random random = new Random();
        int openingSpace = 150;
        int randomPipeY = random.nextInt(101)-300;
        toppipe.setX(width);
        toppipe.setY(randomPipeY);
        pipes.add(toppipe);

        Pipe bottompipe = new Pipe(bottomPipe);
        bottompipe.setX(width);
        bottompipe.setY(toppipe.getY() + toppipe.getPipeHeight() + openingSpace);
        pipes.add(bottompipe);
    }
    public void movingGround(){
        Ground ground = new Ground();
        ground.setX(width);
        grounds.add(ground);
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
        for(Ground ground : grounds){
            g.drawImage(ground.getGroundImage(),ground.getX(),ground.getY(),ground.getGroundWidth(),ground.getGroundHeight(),null);
        }
        for(Pipe pipe : pipes){
            g.drawImage(pipe.getPipeImage(),pipe.getX(),pipe.getY(),pipe.getPipeWidth(),pipe.getPipeHeight(),null);
        }
        g.drawImage(bird.getBirdImage(),bird.getX(), bird.getY(),bird.getBirdWidth(),bird.getBirdHeight(),null);
        if(!gameOver){
            g.setFont(new Font("Arial", Font.BOLD,25));
            g.setColor(Color.white);
            g.drawString(String.valueOf((int)score),width/2,30);
        }
        if(gameOver){
            g.setFont(new Font("Arial", Font.BOLD,60));
            g.setColor(Color.white);
            g.drawString("Game over", 20,320);
            g.setFont(new Font("Arial", Font.BOLD,40));
            g.setColor(Color.white);
            g.drawString("Best: " + highScore,30, 365);
        }
    }
}
