import javax.swing.*;

public class Frame {
    public Frame(){
        JFrame jFrame = new JFrame("Flappy Bird");
        Game game = new Game();
        jFrame.setSize(game.getWidth(), game.getHeight());
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.add(game);
        jFrame.pack();
        game.requestFocus();
        jFrame.setVisible(true);



    }


}
