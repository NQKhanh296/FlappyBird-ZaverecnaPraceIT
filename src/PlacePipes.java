import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class PlacePipes {
    private ArrayList<Pipe> pipes;
    private Image bottomPipe;
    private Image topPipe;

    public PlacePipes() {
        pipes = new ArrayList<>();
        bottomPipe = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/bottompipe.png"))).getImage();
        topPipe = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/toppipe.png"))).getImage();
    }
    public void placePipes(){
        Pipe toppipe = new Pipe(topPipe);
        Random random = new Random();
        int openingSpace = 180;
        int randomPipeY = random.nextInt(151)-400;
        toppipe.setX(Game.WIDTH);
        toppipe.setY(randomPipeY);
        pipes.add(toppipe);
        Pipe bottompipe = new Pipe(bottomPipe);
        bottompipe.setX(Game.WIDTH);
        bottompipe.setY(toppipe.getY() + toppipe.getPipeHeight() + openingSpace);
        pipes.add(bottompipe);
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }
}
