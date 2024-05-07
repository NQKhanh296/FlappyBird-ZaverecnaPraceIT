import javax.swing.*;

public class Frame {
    public Frame(){
        JFrame jFrame = new JFrame("Flappy Bird");
        jFrame.setSize(360, 640);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        Game game = new Game();
        jFrame.add(game);
        jFrame.pack();
        game.requestFocus();
        jFrame.setIconImage(new ImageIcon(getClass().getResource("birdDown.png")).getImage());
        jFrame.setVisible(true);
    }


}
