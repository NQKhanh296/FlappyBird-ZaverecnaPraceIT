import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
public class Game extends JPanel {
    private int width;
    private int height;
    private int birdVelocity;
    private int pipeAndGroundVelocity;
    private int highScore;
    private double score;
    private boolean gameOver;
    private Image background;
    private Image bottomPipe;
    private Image topPipe;
    private Image guideImg;
    private Image getReadyImg;
    private Image gameOverImg;
    private Bird bird;
    private Ground ground1;
    private Ground ground2;
    private Timer birdTimer;
    private Timer placePipesTimer;
    private Timer groundTimer;
    private ArrayList<Pipe> pipes;
    private JButton startButton;
    private JButton OKButton;
    private Font flappyBirdFont;
    public Game() {
        background = new ImageIcon(Objects.requireNonNull(getClass().getResource("flappybirdbg.png"))).getImage();
        bottomPipe = new ImageIcon(Objects.requireNonNull(getClass().getResource("bottompipe.png"))).getImage();
        topPipe = new ImageIcon(Objects.requireNonNull(getClass().getResource("toppipe.png"))).getImage();
        guideImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("guideImg.png"))).getImage();
        getReadyImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("getReadyImg.png"))).getImage();
        gameOverImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("gameOverImg.png"))).getImage();
        width = background.getWidth(null);
        height = background.getHeight(null);
        score = 0;
        highScore = 0;
        pipeAndGroundVelocity = -4;
        gameOver = false;
        setPreferredSize(new Dimension(width, height));
        pipes = new ArrayList<>();
        bird = new Bird();
        bird.setTimer(true);
        ground1 = new Ground(0);
        ground2 = new Ground(width);
        addTimer();
        addButtons();
        try {
            InputStream fontStream = getClass().getResourceAsStream("flappyBirdFont.ttf");
            assert fontStream != null;
            flappyBirdFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        setFocusable(true);
    }
    public void addTimer(){
        int delay = 1000/55;
        birdTimer = new Timer(delay, e -> {
            birdVelocity += 1;
            bird.setY(Math.max(bird.getY(),0));
            bird.setY(Math.min(bird.getY(),height - ground1.getGroundHeight() - bird.getBirdHeight()));
            int targetY = height - ground1.getGroundHeight() - bird.getBirdHeight();
            if(!gameOver && Math.abs(bird.getY() - targetY) < 3){
                setGameOver();
            }
            if(!gameOver && bird.getY() + bird.getBirdHeight() < height - ground1.getGroundHeight()){
                bird.setY(bird.getY() + birdVelocity);
                for(Pipe pipe : pipes){
                    pipe.setX(pipe.getX() + pipeAndGroundVelocity);
                    if(!pipe.isPassed() && bird.getX() > pipe.getX() + pipe.getPipeWidth()){
                        score += 0.5;
                        pipe.setPassed(true);
                    }
                    if(collision(bird,pipe)){
                        setGameOver();
                    }
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
        groundTimer = new Timer(delay, e -> {
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
        groundTimer.stop();
        bird.setTimer(false);
        birdTimer.stop();
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
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMouseAndKeyListener();
                startButton.setVisible(false);
            }
        });
        OKButton = new JButton(OKButtonImage);
        OKButton.setBounds(x,500,startButtonImage.getIconWidth(),startButtonImage.getIconHeight());
        add(OKButton);
        OKButton.setVisible(false);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gameOver){
                    gameOver = false;
                    OKButton.setVisible(false);
                    bird.setY(640/3);
                    birdVelocity = 0;
                    score = 0;
                    pipes.clear();
                    bird.setTimer(true);
                    groundTimer.start();
                    startButton.setVisible(true);
                }
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
            g.setFont(flappyBirdFont.deriveFont(Font.PLAIN,35));
            g.setColor(Color.white);
            g.drawString(String.valueOf((int)score),width/2 - 12,30);
        }
        if(gameOver){
            g.drawImage(gameOverImg,(width - gameOverImg.getWidth(null))/2,200,gameOverImg.getWidth(null),gameOverImg.getHeight(null),null);
            g.setFont(flappyBirdFont.deriveFont(Font.PLAIN,38));
            g.setColor(Color.white);
            g.drawString("Best: " + highScore,247, 330);
            g.drawString("Score: " + (int)score,35,330);
        }
        if(!startButton.isVisible() && !birdTimer.isRunning() && !gameOver){
            g.drawImage(getReadyImg,(width - getReadyImg.getWidth(null))/2,120,getReadyImg.getWidth(null),getReadyImg.getHeight(null),null);
            g.drawImage(guideImg,(width - guideImg.getWidth(null))/2,640/3+50,guideImg.getWidth(null),guideImg.getHeight(null),null);
        }
    }
}
