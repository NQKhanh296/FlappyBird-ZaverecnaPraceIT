import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PlacePipes {
    private final ArrayList<Pipe> pipes;
    private final Image bottomPipe;
    private final Image topPipe;

    public PlacePipes() {
        pipes = new ArrayList<>();
        bottomPipe = Resources.bottomPipeImg;
        topPipe = Resources.topPipeImg;
    }
    public void placePipes(){
        // Place top and bottom pipes with a random coordinates for the opening space
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
