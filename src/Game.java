import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
public class Game extends JPanel {
    private int width;
    private int height;
    private Image background;
    private Image bottomPipe;
    private Image topPipe;
    private ArrayList<Pipe> pipes;
    private Bird bird;
    private Ground ground1;
    private Ground ground2;
    private GetReadyImg getReadyImg;
    private Timer birdTimer;
    private Timer placePipesTimer;
    private Timer groundTimer;
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
        pipeAndGroundVelocity = -4;
        gameOver = false;
        setPreferredSize(new Dimension(width, height));
        pipes = new ArrayList<>();
        background = new ImageIcon(Objects.requireNonNull(getClass().getResource("flappybirdbg.png"))).getImage();
        bottomPipe = new ImageIcon(Objects.requireNonNull(getClass().getResource("bottompipe.png"))).getImage();
        topPipe = new ImageIcon(Objects.requireNonNull(getClass().getResource("toppipe.png"))).getImage();
        bird = new Bird();
        bird.setTimer(true);
        ground1 = new Ground(0);
        ground2 = new Ground(width);
        getReadyImg = new GetReadyImg();
        addTimer();
        addMouseAndKeyListener();
        setFocusable(true);
        if(gameOver){
            birdTimer.stop();
            placePipesTimer.stop();
        }
    }
    public void addTimer(){
        birdTimer = new Timer(1000/50, e -> {
            birdVelocity += 1;
            if(!gameOver){
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
                        groundTimer.stop();
                        bird.setTimer(false);
                        bird.switchImage(4);
                    }
                }
                if(bird.getY() >= height - ground1.getGroundHeight()-bird.getBirdHeight()){
                    gameOver = true;
                    groundTimer.stop();
                    bird.setTimer(false);
                    bird.switchImage(4);
                }
                if(score>highScore){
                    highScore = (int) score;
                }
                for(int i = 0; i < pipes.size(); i++){
                    if(pipes.get(i).getX() + pipes.get(i).getPipeWidth() < 0){
                        pipes.remove(pipes.get(i));
                    }
                }
            }
            repaint();
        });
        placePipesTimer = new Timer(1200, e -> {
            if(!gameOver){
                placePipes();
            }
        });
        groundTimer = new Timer(1000/50, e -> {
            ground1.setX(ground1.getX() + pipeAndGroundVelocity);
            ground2.setX(ground2.getX() + pipeAndGroundVelocity);
            if (ground1.getX() <= -ground1.getGroundWidth()) {
                ground1.setX(ground2.getX() + ground2.getGroundWidth());
            }
            if (ground2.getX() <= -ground2.getGroundWidth()) {
                ground2.setX(ground1.getX() + ground1.getGroundWidth());
            }
            repaint();
        });
        groundTimer.start();
    }
    public void addMouseAndKeyListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addBirdVelocity();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    addBirdVelocity();
                }
            }
        });
    }

    public void addBirdVelocity() {
        birdVelocity = -13;
        birdTimer.start();
        placePipesTimer.start();
        if(gameOver){
            bird.setY(640/3);
            birdVelocity = 0;
            score = 0;
            pipes.clear();
            gameOver = false;
            birdTimer.start();
            placePipesTimer.start();
            groundTimer.start();
            bird.setTimer(true);
        }
    }

    public void placePipes(){
        Pipe toppipe = new Pipe(topPipe);
        Random random = new Random();
        int openingSpace = 150;
        int randomPipeY = random.nextInt(151)-400;
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
        g.drawImage(background,0,0,width,height,null);
        for(Pipe pipe : pipes){
            g.drawImage(pipe.getPipeImage(),pipe.getX(),pipe.getY(),pipe.getPipeWidth(),pipe.getPipeHeight(),null);
        }
        g.drawImage(ground1.getGroundImage(),ground1.getX(),ground1.getY(),ground1.getGroundWidth(),ground1.getGroundHeight(),null);
        g.drawImage(ground2.getGroundImage(),ground2.getX(),ground2.getY(),ground2.getGroundWidth(),ground2.getGroundHeight(),null);
        g.setColor(Color.BLACK);
        g.drawLine(0,578,width,578);
        g.drawLine(0,577,width,577);
        g.drawImage(bird.getBirdImage(),bird.getX(), bird.getY(),bird.getBirdWidth(),bird.getBirdHeight(),null);
        if(!gameOver){
            g.setFont(new Font("Arial", Font.BOLD,30));
            g.setColor(Color.white);
            g.drawString(String.valueOf((int)score),width/2 - 10,30);
        }
        if(gameOver){
            g.setFont(new Font("Arial", Font.BOLD,60));
            g.setColor(Color.white);
            g.drawString("Game over", 20,320);
            g.setFont(new Font("Arial", Font.BOLD,40));
            g.setColor(Color.white);
            g.drawString("Best: " + highScore,20, 365);
        }
    }
}
