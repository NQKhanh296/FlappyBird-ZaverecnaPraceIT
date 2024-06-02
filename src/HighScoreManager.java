import java.io.*;
import java.util.Objects;

public class HighScoreManager {
    private static final String highScoreFile = "highscore.txt";

    public static void saveHighScore(int highScore) {
        try (FileWriter writer = new FileWriter(getFile())) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            System.out.println("Error writing");
        }
    }

    public static int loadHighScore() {
        try (InputStream inputStream = HighScoreManager.class.getClassLoader().getResourceAsStream(highScoreFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("No high score available!");
        }
        return 0;
    }

    private static File getFile() {
        return new File(Objects.requireNonNull(HighScoreManager.class.getClassLoader().getResource(HighScoreManager.highScoreFile)).getFile());
    }
}
