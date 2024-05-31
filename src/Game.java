import javax.sound.sampled.LineEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Game extends JPanel {
    public static int WIDTH;
    public static int HEIGHT;
    private final int width;
    private final int height;
    private int birdVelocity;
    private final int pipeAndGroundVelocity;
    private final int grassVelocity;
    private int highScore;
    private int delay;
    private double score;
    private boolean gameOver;
    private boolean birdFlying;
    private final Image background;
    private final Image bottomPipe;
    private final Image topPipe;
    private final Image guideImg;
    private final Image getReadyImg;
    private final Image gameOverImg;
    private final Image groundImg;
    private final Image grassImg;
    private final Bird bird;
    private final GameLogo gameLogo;
    private final Ground ground1;
    private final Ground ground2;
    private final Ground grass1;
    private final Ground grass2;
    private final FontImporter flappyBirdFont;
    private final SFXImporter flapSFX;
    private final SFXImporter hitSFX;
    private final SFXImporter pointSFX;
    private final SFXImporter clickSFX;
    private final SFXImporter backgroundMusicSFX;
    private Buttons buttons;
    private Timer birdAndGroundTimer;
    private Timer placePipesTimer;
    private Timer grassTimer;
    private final ArrayList<Pipe> pipes;
    public Game() {
        background = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/flappybirdbg.png"))).getImage();
        bottomPipe = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/bottompipe.png"))).getImage();
        topPipe = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/toppipe.png"))).getImage();
        guideImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/guideImg.png"))).getImage();
        getReadyImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/getReadyImg.png"))).getImage();
        gameOverImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/gameOverImg.png"))).getImage();
        groundImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/ground.png"))).getImage();
        grassImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/grass.png"))).getImage();

        WIDTH = background.getWidth(null);
        HEIGHT = background.getHeight(null);
        width = WIDTH;
        height = HEIGHT;
        score = 0;
        highScore = HighScoreManager.loadHighScore();
        pipeAndGroundVelocity = -4;
        grassVelocity = -1;
        delay = 0;
        gameOver = false;
        birdFlying = false;
        setPreferredSize(new Dimension(width, height));
        pipes = new ArrayList<>();
        bird = new Bird();
        gameLogo = new GameLogo();
        bird.setTimer(1,true);
        ground1 = new Ground(0,544,groundImg);
        ground2 = new Ground(width,544,groundImg);
        grass1 = new Ground(0,342,grassImg);
        grass2 = new Ground(width,342,grassImg);
        buttons = new Buttons();
        flappyBirdFont = new FontImporter("Font/flappyBirdFont.TTF");
        flapSFX = new SFXImporter("Sfx/flapSFX.wav");
        hitSFX = new SFXImporter("Sfx/hitSFX.wav");
        pointSFX = new SFXImporter("Sfx/pointSFX.wav");
        clickSFX = new SFXImporter("Sfx/clickSFX.wav");
        backgroundMusicSFX = new SFXImporter("Sfx/FlappyBirdSoundTrack.wav");
        setDelay(0);
        addTimer();
        addButtons();
        addMouseAndKeyListener();
        backgroundMusicSFX.play();
        setFocusable(true);
    }
    public void addTimer(){
        grassTimer = new Timer(delay,e -> {
            grass1.setX(grass1.getX() + grassVelocity);
            grass2.setX(grass2.getX() + grassVelocity);
            if (grass1.getX() <= -grass1.getWidth()) {
                grass1.setX(grass2.getX() + grass2.getWidth());
            }
            if (grass2.getX() <= -grass2.getWidth()) {
                grass2.setX(grass1.getX() + grass1.getWidth());
            }
            repaint();
        });
        grassTimer.start();
        birdAndGroundTimer = new Timer(delay, e -> {
            ground1.setX(ground1.getX() + pipeAndGroundVelocity);
            ground2.setX(ground2.getX() + pipeAndGroundVelocity);
            if (ground1.getX() <= -ground1.getWidth()) {
                ground1.setX(ground2.getX() + ground2.getWidth());
            }
            if (ground2.getX() <= -ground2.getWidth()) {
                ground2.setX(ground1.getX() + ground1.getWidth());
            }
            if(buttons.getStartButton().isVisible()){
                bird.setX(gameLogo.getX() + gameLogo.getWidth() + (bird.getBirdWidth() / 3));
            }
            if(!buttons.getStartButton().isVisible()){
                bird.setX(width / 8);
            }
            if(birdFlying){
                birdVelocity += 1;
                bird.setY(Math.max(bird.getY(), 0));
                bird.setY(Math.min(bird.getY(), height - ground1.getHeight() - bird.getBirdHeight()));
                int targetY = height - ground1.getHeight() - bird.getBirdHeight();
                if (!gameOver && Math.abs(bird.getY() - targetY) < 3) {
                    setGameOver();
                }
                if (!gameOver && bird.getY() + bird.getBirdHeight() < height - ground1.getHeight()) {
                    bird.setY(bird.getY() + birdVelocity);
                    for (Pipe pipe : pipes) {
                        pipe.setX(pipe.getX() + pipeAndGroundVelocity);
                        if (!pipe.isPassed() && bird.getX() > pipe.getX() + pipe.getPipeWidth()) {
                            pointSFX.play();
                            score += 0.5;
                            pipe.setPassed(true);
                        }
                        if (collision(bird, pipe)) {
                            setGameOver();
                        }
                    }
                    if (score > highScore) {
                        highScore = (int) score;
                        HighScoreManager.saveHighScore(highScore);
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
        placePipesTimer = new Timer(1400, e -> {
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
                if(!gameOver && !buttons.getStartButton().isVisible()) {
                    addBirdVelocity();
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!gameOver && !buttons.getStartButton().isVisible()) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        addBirdVelocity();
                    }
                }
            }
        });
    }
    public void addBirdVelocity() {
        birdVelocity = -13;
        flapSFX.play();
        birdFlying = true;
        placePipesTimer.start();
        bird.setTimer(1,false);
        bird.setTimer(2,true);
    }
    public void setDelay(int number){
        switch (number){
            case 1 -> {
                delay = 1000/35;
            }
            case 2 -> {
                delay = 1000/75;
            }
            default -> {
                delay = 1000/55;
            }
        }
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
        buttons.getOKButton().setVisible(true);
        bird.setTimer(1,false);
        bird.setTimer(2,false);
        birdAndGroundTimer.stop();
        placePipesTimer.stop();
        grassTimer.stop();
        bird.switchImage(4);
        hitSFX.play();
        backgroundMusicSFX.stop();
        backgroundMusicSFX.setMicrosecondPosition(0);
    }
    public void addButtons(){
        setLayout(null);
        add(buttons.getStartButton());
        add(buttons.getOKButton());
        add(buttons.getEasyButton());
        add(buttons.getNormalButton());
        add(buttons.getHardButton());
        buttons.getStartButton().addActionListener(e -> {
            clickSFX.play();
            clickSFX.getSfx().addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    buttons.getStartButton().setVisible(false);
                    buttons.getHardButton().setVisible(false);
                    buttons.getNormalButton().setVisible(false);
                    buttons.getEasyButton().setVisible(false);
                    clickSFX.setMicrosecondPosition(0);
                }
            });
        });
        buttons.getOKButton().setVisible(false);
        buttons.getOKButton().addActionListener(e -> {
            if(gameOver){
                clickSFX.play();
                clickSFX.getSfx().addLineListener(event -> {
                    if(event.getType() == LineEvent.Type.STOP){
                        gameOver = false;
                        buttons.getOKButton().setVisible(false);
                        bird.setY(height/3);
                        birdVelocity = 0;
                        score = 0;
                        pipes.clear();
                        bird.setTimer(2,false);
                        bird.setTimer(1,true);
                        birdAndGroundTimer.start();
                        grassTimer.start();
                        birdFlying = false;
                        buttons.getStartButton().setVisible(true);
                        buttons.getEasyButton().setVisible(true);
                        buttons.getHardButton().setVisible(true);
                        buttons.getNormalButton().setVisible(true);
                        clickSFX.setMicrosecondPosition(0);
                        backgroundMusicSFX.play();
                    }
                });
            }
        });
        buttons.getEasyButton().addActionListener(e -> {
            setDelay(1);
            birdAndGroundTimer.setDelay(delay);
        });
        buttons.getNormalButton().addActionListener(e -> {
            setDelay(0);
            birdAndGroundTimer.setDelay(delay);
        });
        buttons.getHardButton().addActionListener(e -> {
            setDelay(2);
            birdAndGroundTimer.setDelay(delay);
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(background,0,0,width,height,null);
        g.drawImage(grass1.getImage(),grass1.getX(),grass1.getY(),grass1.getWidth(),grass1.getHeight(),null);
        g.drawImage(grass2.getImage(),grass2.getX(),grass2.getY(),grass2.getWidth(),grass2.getHeight(),null);
        for(Pipe pipe : pipes){
            g.drawImage(pipe.getPipeImage(),pipe.getX(),pipe.getY(),pipe.getPipeWidth(),pipe.getPipeHeight(),null);
        }
        g.drawImage(ground1.getImage(),ground1.getX(),ground1.getY(),ground1.getWidth(),ground1.getHeight(),null);
        g.drawImage(ground2.getImage(),ground2.getX(),ground2.getY(),ground2.getWidth(),ground2.getHeight(),null);
        g.drawImage(bird.getBirdImage(),bird.getX(), bird.getY(),bird.getBirdWidth(),bird.getBirdHeight(),null);
        if(!buttons.getStartButton().isVisible() && !gameOver){
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
        if(!buttons.getStartButton().isVisible() && !birdFlying && !gameOver){
            g.drawImage(getReadyImg,(width - getReadyImg.getWidth(null))/2,120,getReadyImg.getWidth(null),getReadyImg.getHeight(null),null);
            g.drawImage(guideImg,(width - guideImg.getWidth(null))/2,height/3+45,guideImg.getWidth(null),guideImg.getHeight(null),null);
        }
        if(buttons.getStartButton().isVisible()){
            g.drawImage(gameLogo.getLogoImage(),gameLogo.getX(),gameLogo.getY(),gameLogo.getWidth(),gameLogo.getHeight(),null);
        }
    }
}
