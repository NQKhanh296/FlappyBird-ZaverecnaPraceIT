import java.util.Random;

public class RandomNumber extends Thread{
    private int randomNumber;

    @Override
    public void run() {
        super.run();
        Random random = new Random();
        while (true){
        randomNumber = random.nextInt(201)-300;
            try {
                Thread.sleep(1300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public int getRandomNumber() {
        return randomNumber;
    }
}
