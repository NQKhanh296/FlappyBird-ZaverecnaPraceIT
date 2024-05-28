import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
public class Game extends JPanel {
    public static int WIDTH;
    public static int HEIGHT;
    private int width;
    private int height;
    private int birdVelocity;
    private int pipeAndGroundVelocity;
    private int highScore;
    private double score;
    private boolean gameOver;
    private boolean birdFlying;
    private Image background;
    private Image bottomPipe;
    private Image topPipe;
    private Image guideImg;
    private Image getReadyImg;
    private Image gameOverImg;
    private Bird bird;
    private GameLogo gameLogo;
    private Ground ground1;
    private Ground ground2;
    private Timer birdAndGroundTimer;
    private Timer placePipesTimer;
    private ArrayList<Pipe> pipes;
    private JButton startButton;
    private JButton OKButton;
    private FontImporter flappyBirdFont;
    public Game() {
        background = new ImageIcon(Objects.requireNonNull(getClass().getResource("flappybirdbg.png"))).getImage();
        bottomPipe = new ImageIcon(Objects.requireNonNull(getClass().getResource("bottompipe.png"))).getImage();
        topPipe = new ImageIcon(Objects.requireNonNull(getClass().getResource("toppipe.png"))).getImage();
        guideImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("guideImg.png"))).getImage();
        getReadyImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("getReadyImg.png"))).getImage();
        gameOverImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("gameOverImg.png"))).getImage();
        WIDTH = background.getWidth(null);
        HEIGHT = background.getHeight(null);
        width = WIDTH;
        height = HEIGHT;
        score = 0;
        highScore = 0;
        pipeAndGroundVelocity = -4;
        gameOver = false;
        birdFlying = false;
        setPreferredSize(new Dimension(width, height));
        pipes = new ArrayList<>();
        bird = new Bird();
        gameLogo = new GameLogo();
        bird.setTimer(1,true);
        ground1 = new Ground(0);
        ground2 = new Ground(width);
        flappyBirdFont = new FontImporter("flappyBirdFont.TTF");
        addTimer();
        addButtons();
        addMouseAndKeyListener();
        setFocusable(true);
    }
    public void addTimer(){
        birdAndGroundTimer = new Timer(1000/55, e -> {
            ground1.setX(ground1.getX() + pipeAndGroundVelocity);
            ground2.setX(ground2.getX() + pipeAndGroundVelocity);
            if (ground1.getX() <= -ground1.getGroundWidth()) {
                ground1.setX(ground2.getX() + ground2.getGroundWidth());
            }
            if (ground2.getX() <= -ground2.getGroundWidth()) {
                ground2.setX(ground1.getX() + ground1.getGroundWidth());
            }
            if(startButton.isVisible()){
                bird.setX(gameLogo.getX() + gameLogo.getWidth() + (bird.getBirdWidth() / 3));

            }
            if(!startButton.isVisible()){
                bird.setX(width / 8);
            }
            if(birdFlying){
                birdVelocity += 1;
                bird.setY(Math.max(bird.getY(), 0));
                bird.setY(Math.min(bird.getY(), height - ground1.getGroundHeight() - bird.getBirdHeight()));
                int targetY = height - ground1.getGroundHeight() - bird.getBirdHeight();
                if (!gameOver && Math.abs(bird.getY() - targetY) < 3) {
                    setGameOver();
                }
                if (!gameOver && bird.getY() + bird.getBirdHeight() < height - ground1.getGroundHeight()) {
                    bird.setY(bird.getY() + birdVelocity);
                    for (Pipe pipe : pipes) {
                        pipe.setX(pipe.getX() + pipeAndGroundVelocity);
                        if (!pipe.isPassed() && bird.getX() > pipe.getX() + pipe.getPipeWidth()) {
                            score += 0.5;
                            pipe.setPassed(true);
                        }
                        if (collision(bird, pipe)) {
                            setGameOver();
                        }
                    }
                    if (score > highScore) {
                        highScore = (int) score;
                    }
                    for (int i = 0; i < pipes.size(); i++) {
                        if (pipes.get(i).getX() + pipes.get(i).getPipeWidth() < 0) {
                            pipes.remove(pipes.get(i));
                        }
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
        birdAndGroundTimer.start();
    }
    public void addMouseAndKeyListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!gameOver && !startButton.isVisible()) {
                    addBirdVelocity();
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!gameOver && !startButton.isVisible()) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        addBirdVelocity();
                    }
                }
            }
        });
    }
    public void addBirdVelocity() {
        birdVelocity = -13;
        birdFlying = true;
        placePipesTimer.start();
        bird.setTimer(1,false);
        bird.setTimer(2,true);
    }
    public void placePipes(){
        Pipe toppipe = new Pipe(topPipe);
        Random random = new Random();
        int openingSpace = 180;
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
    public void setGameOver(){
        gameOver = true;
        OKButton.setVisible(true);
        bird.setTimer(1,false);
        bird.setTimer(2,false);
        birdAndGroundTimer.stop();
        placePipesTimer.stop();
        bird.switchImage(4);
    }
    public void addButtons(){
        setLayout(null);
        ImageIcon startButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("startButton.png")));
        ImageIcon OKButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("OKButton.png")));
        startButton = new JButton(startButtonImage);
        int x = (width - startButtonImage.getIconWidth()) / 2;
        startButton.setBounds(x,500,startButtonImage.getIconWidth(),startButtonImage.getIconHeight());
        add(startButton);
        startButton.addActionListener(e -> {
            startButton.setVisible(false);
        });
        OKButton = new JButton(OKButtonImage);
        OKButton.setBounds(x,500,startButtonImage.getIconWidth(),startButtonImage.getIconHeight());
        add(OKButton);
        OKButton.setVisible(false);
        OKButton.addActionListener(e -> {
            if(gameOver){
                gameOver = false;
                OKButton.setVisible(false);
                bird.setY(height/3);
                birdVelocity = 0;
                score = 0;
                pipes.clear();
                bird.setTimer(2,false);
                bird.setTimer(1,true);
                birdAndGroundTimer.start();
                birdFlying = false;
                startButton.setVisible(true);
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(background,0,0,width,height,null);
        for(Pipe pipe : pipes){
            g.drawImage(pipe.getPipeImage(),pipe.getX(),pipe.getY(),pipe.getPipeWidth(),pipe.getPipeHeight(),null);
        }
        g.drawImage(ground1.getGroundImage(),ground1.getX(),ground1.getY(),ground1.getGroundWidth(),ground1.getGroundHeight(),null);
        g.drawImage(ground2.getGroundImage(),ground2.getX(),ground2.getY(),ground2.getGroundWidth(),ground2.getGroundHeight(),null);
        g.drawImage(bird.getBirdImage(),bird.getX(), bird.getY(),bird.getBirdWidth(),bird.getBirdHeight(),null);
        if(!startButton.isVisible() && !gameOver){
            g.setFont(flappyBirdFont.getFont().deriveFont(Font.PLAIN,35));
            g.setColor(Color.white);
            g.drawString(String.valueOf((int)score),width/2 - 12,40);
        }
        if(gameOver){
            g.drawImage(gameOverImg,(width - gameOverImg.getWidth(null))/2,120,gameOverImg.getWidth(null),gameOverImg.getHeight(null),null);
            g.setFont(flappyBirdFont.getFont().deriveFont(Font.PLAIN,38));
            g.setColor(Color.white);
            g.drawString("Best: " + highScore,247, 250);
            g.drawString("Score: " + (int)score,35,250);
        }
        if(!startButton.isVisible() && !birdFlying && !gameOver){
            g.drawImage(getReadyImg,(width - getReadyImg.getWidth(null))/2,120,getReadyImg.getWidth(null),getReadyImg.getHeight(null),null);
            g.drawImage(guideImg,(width - guideImg.getWidth(null))/2,height/3+45,guideImg.getWidth(null),guideImg.getHeight(null),null);
        }
        if(startButton.isVisible()){
            g.drawImage(gameLogo.getLogoImage(),gameLogo.getX(),gameLogo.getY(),gameLogo.getWidth(),gameLogo.getHeight(),null);
        }
    }
}
