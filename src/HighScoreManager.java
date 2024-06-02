import java.io.*;

public class HighScoreManager {
    private static final File file = new File("src/highscore");
    public static void saveHighScore(int highScore) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            System.out.println("Error writing");
        }
    }
    public static int loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
