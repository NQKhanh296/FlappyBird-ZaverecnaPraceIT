import javax.sound.sampled.LineEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private int placePipesTimerDelay;
    private double score;
    private boolean gameOver;
    private boolean birdFlying;
    private boolean giveSilverMedal;
    private boolean giveGoldMedal;
    private final Image background;
    private final Image guideImg;
    private final Image getReadyImg;
    private final Image gameOverImg;
    private final Image silverMedal;
    private final Image goldMedal;
    private final Bird bird;
    private final GameLogo gameLogo;
    private final Ground ground1;
    private final Ground ground2;
    private final Ground grass1;
    private final Ground grass2;
    private final FontImporter flappyBirdFont;
    private final SFX sfx;
    private final PlacePipes placePipes;
    private final Buttons buttons;
    private Timer birdAndGroundTimer;
    private Timer placePipesTimer;
    private Timer grassTimer;

    public Game() {
        background = Resources.flappyBirdBgImg;
        guideImg = Resources.guideImg;
        getReadyImg = Resources.getReadyImg;
        gameOverImg = Resources.gameOverImg;
        Image groundImg = Resources.groundImg;
        Image grassImg = Resources.grassImg;
        silverMedal = Resources.silverMedalImg;
        goldMedal = Resources.goldMedalImg;

        WIDTH = background.getWidth(null);
        HEIGHT = background.getHeight(null);
        width = WIDTH;
        height = HEIGHT;
        score = 0;
        highScore = HighScoreManager.loadHighScore();
        pipeAndGroundVelocity = -4;
        grassVelocity = -1;

        setPreferredSize(new Dimension(width, height));

        gameOver = false;
        birdFlying = false;
        giveSilverMedal = false;
        giveGoldMedal = false;

        bird = new Bird();
        gameLogo = new GameLogo();
        placePipes = new PlacePipes();
        sfx = new SFX();

        bird.setTimer(1,true);
        ground1 = new Ground(0,544, groundImg);
        ground2 = new Ground(width,544, groundImg);
        grass1 = new Ground(0,342, grassImg);
        grass2 = new Ground(width,342, grassImg);
        buttons = new Buttons();
        flappyBirdFont = new FontImporter("Font/flappyBirdFont.TTF");


        setDelay(0);
        addTimer();
        addButtons();
        addMouseAndKeyListener();

        sfx.getSoundTrack().start();
        setFocusable(true);
    }
    public void addTimer(){
        // Method to add all the timers
        grassTimer = new Timer(delay,e -> {
            // Make the grass move
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

        birdAndGroundTimer = new Timer(delay, e -> {
            // Make the ground move
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
            // Add gravity and make the bird fall down if space key is not pressed or if mouse is not clicked
            if(birdFlying){
                birdVelocity += 1;
                bird.setY(Math.max(bird.getY(), 0));
                bird.setY(Math.min(bird.getY(), height - ground1.getHeight() - bird.getBirdHeight()));
                int targetY = height - ground1.getHeight() - bird.getBirdHeight();
                if (!gameOver && Math.abs(bird.getY() - targetY) < 3) {
                    setGameOver(); //  If the bird collides with the ground, then game over
                }
                if (!gameOver && bird.getY() + bird.getBirdHeight() < height - ground1.getHeight()) {
                    bird.setY(bird.getY() + birdVelocity);
                    for (Pipe pipe : placePipes.getPipes()) {
                        pipe.setX(pipe.getX() + pipeAndGroundVelocity);
                        if (!pipe.isPassed() && bird.getX() > pipe.getX() + pipe.getPipeWidth()) {
                            sfx.getPointSFX().start();
                            score += 0.5; // Add score, for top pipe is 0.5 point, for bottom pipe is 0.5 point too
                            pipe.setPassed(true);
                        }
                        updateMedals(); // chose a medal to give the player
                        if (collision(bird, pipe)) {
                            setGameOver(); // If the bird collides, then game over
                        }
                    }
                    for (int i = 0; i < placePipes.getPipes().size(); i++) {
                        if (placePipes.getPipes().get(i).getX() + placePipes.getPipes().get(i).getPipeWidth() < 0) {
                            placePipes.getPipes().remove(placePipes.getPipes().get(i));
                        }
                    }
                }
            }
            repaint();
        });

        placePipesTimer = new Timer(placePipesTimerDelay, e -> {
            // If the game is started, start placing pipes
            if(!gameOver){
                placePipes.placePipes();
            }
        });

        grassTimer.start();
        birdAndGroundTimer.start();
    }
    public void addMouseAndKeyListener(){
        // Add MouseListener and KeyListener(space)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!gameOver && !buttons.getStartButton().isVisible()) {
                    addBirdVelocity(); // When clicked, add bird velocity
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!gameOver && !buttons.getStartButton().isVisible()) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        addBirdVelocity(); // When space bar is pressed, add bird velocity
                    }
                }
            }
        });
    }
    public void addBirdVelocity() {
        birdVelocity = -13;
        sfx.getFlapSFX().start();
        birdFlying = true;
        placePipesTimer.start();
        bird.setTimer(1,false);
        bird.setTimer(2,true);
    }
    public void setDelay(int number){
        // Method to change timers delay between easy, normal, and hard difficulty
        switch (number){
            case 1 -> {
                delay = 1000/35;
                placePipesTimerDelay = 2000;
            }
            case 2 -> {
                delay = 1000/75;
                placePipesTimerDelay = 900;
            }
            default -> {
                delay = 1000/55;
                placePipesTimerDelay = 1300;
            }
        }
    }

    /**
     * check if the bird collides with the pipe or not
     * URL : https://youtu.be/Xw2MEG-FBsE?si=TI8CPLaIouoj6EhJ
     * @param a the bird
     * @param b the pipe
     * @return true if collides, false if not
     */
    public boolean collision(Bird a, Pipe b) {
        return a.getX() < b.getX() + b.getPipeWidth() &&
                a.getX() + a.getBirdWidth() > b.getX() &&
                a.getY() < b.getY() + b.getPipeHeight() &&
                a.getY() + a.getBirdHeight() > b.getY();
    }
    public void setGameOver(){
        // Stop all the timers and save the highScore to a text file
        sfx.getHitSFX().start();
        gameOver = true;
        buttons.getOKButton().setVisible(true);
        bird.setTimer(1,false);
        bird.setTimer(2,false);
        birdAndGroundTimer.stop();
        placePipesTimer.stop();
        grassTimer.stop();
        bird.switchImage(4);
        sfx.getSoundTrack().stop();
        sfx.getSoundTrack().setMicrosecondPosition(0);
        if (score > highScore) {
            highScore = (int) score;
            HighScoreManager.saveHighScore(highScore);
        }
    }
    public void updateMedals() {
        // Give player medal based on the player score
        if (score < highScore+10 && score >= highScore + 5) {
            giveSilverMedal = true;
        }
        if (score >= highScore + 10) {
            giveGoldMedal = true;
        }
    }
    public void addButtons(){
        // Add buttons to the game, start button to start the game, easy, normal, hard button to set the game difficulty and ok button to reset all the timers and so on
        setLayout(null);
        add(buttons.getStartButton());
        add(buttons.getOKButton());
        add(buttons.getEasyButton());
        add(buttons.getNormalButton());
        add(buttons.getHardButton());
        buttons.getStartButton().addActionListener(e -> {
            sfx.getClickSFX().start();
            sfx.getClickSFX().addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    buttons.getStartButton().setVisible(false);
                    buttons.getHardButton().setVisible(false);
                    buttons.getNormalButton().setVisible(false);
                    buttons.getEasyButton().setVisible(false);
                    sfx.getClickSFX().setMicrosecondPosition(0);
                }
            });
        });
        buttons.getOKButton().setVisible(false);
        buttons.getOKButton().addActionListener(e -> {
            if(gameOver){
                sfx.getClickSFX().start();
                sfx.getClickSFX().addLineListener(event -> {
                    if(event.getType() == LineEvent.Type.STOP){
                        gameOver = false;
                        buttons.getOKButton().setVisible(false);
                        bird.setY(height/3);
                        birdVelocity = 0;
                        score = 0;
                        placePipes.getPipes().clear();
                        bird.setTimer(2,false);
                        bird.setTimer(1,true);
                        birdAndGroundTimer.start();
                        grassTimer.start();
                        birdFlying = false;
                        giveSilverMedal = false;
                        giveGoldMedal = false;
                        buttons.getStartButton().setVisible(true);
                        buttons.getEasyButton().setVisible(true);
                        buttons.getHardButton().setVisible(true);
                        buttons.getNormalButton().setVisible(true);
                        sfx.getClickSFX().setMicrosecondPosition(0);
                        sfx.getSoundTrack().start();
                    }
                });
            }
        });
        buttons.getEasyButton().addActionListener(e -> {
            sfx.getClickSFX().start();
            sfx.getClickSFX().addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    setDelay(1);
                    birdAndGroundTimer.setDelay(delay);
                    placePipesTimer.setDelay(placePipesTimerDelay);
                }
            });
        });
        buttons.getNormalButton().addActionListener(e -> {
            sfx.getClickSFX().start();
            sfx.getClickSFX().addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    setDelay(0);
                    birdAndGroundTimer.setDelay(delay);
                    placePipesTimer.setDelay(placePipesTimerDelay);
                }
            });
        });
        buttons.getHardButton().addActionListener(e -> {
            sfx.getClickSFX().start();
            sfx.getClickSFX().addLineListener(event -> {
                if(event.getType() == LineEvent.Type.STOP){
                    setDelay(2);
                    birdAndGroundTimer.setDelay(delay);
                    placePipesTimer.setDelay(placePipesTimerDelay);
                }
            });
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        // Draw all the components based on the game scene
        g.drawImage(background,0,0,width,height,null);
        g.drawImage(grass1.getImage(),grass1.getX(),grass1.getY(),grass1.getWidth(),grass1.getHeight(),null);
        g.drawImage(grass2.getImage(),grass2.getX(),grass2.getY(),grass2.getWidth(),grass2.getHeight(),null);
        for(Pipe pipe : placePipes.getPipes()){
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
        if(gameOver && giveSilverMedal){
            g.drawImage(silverMedal, (width - silverMedal.getWidth(null)*3)/2,310,silverMedal.getWidth(null)*3,silverMedal.getHeight(null)*3,null);
        }
        if(gameOver && giveGoldMedal){
            g.drawImage(goldMedal, (width - goldMedal.getWidth(null)*3)/2,310,goldMedal.getWidth(null)*3,goldMedal.getHeight(null)*3,null);
        }
        if(!buttons.getStartButton().isVisible() && !birdFlying && !gameOver){
            g.drawImage(getReadyImg,(width - getReadyImg.getWidth(null))/2,120,getReadyImg.getWidth(null),getReadyImg.getHeight(null),null);
            g.drawImage(guideImg,(width - guideImg.getWidth(null))/2,height/3+45,guideImg.getWidth(null),guideImg.getHeight(null),null);
        }
        if(buttons.getStartButton().isVisible()){
            g.drawImage(gameLogo.getLogoImage(),gameLogo.getX(),gameLogo.getY(),gameLogo.getWidth(),gameLogo.getHeight(),null);
        }
    }
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }


    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isBirdFlying() {
        return birdFlying;
    }

    public void setBirdFlying(boolean birdFlying) {
        this.birdFlying = birdFlying;
    }

    public boolean isGiveSilverMedal() {
        return giveSilverMedal;
    }

    public boolean isGiveGoldMedal() {
        return giveGoldMedal;
    }

    public Bird getBird() {
        return bird;
    }

    public PlacePipes getPlacePipes() {
        return placePipes;
    }

    public Timer getBirdAndGroundTimer() {
        return birdAndGroundTimer;
    }

}
