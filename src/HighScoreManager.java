import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HighScoreManager {
    public static void saveHighScore(int highScore) {
        try (FileWriter writer = new FileWriter("highscore.txt")) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("No highScore available!");
        }
        return 0;
    }
}
