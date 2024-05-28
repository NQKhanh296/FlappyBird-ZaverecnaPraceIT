import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontImporter {
    private Font font;
    public FontImporter(String fileName) {
        try {
            InputStream fontStream = getClass().getResourceAsStream(fileName);
            assert fontStream != null;
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        } catch (FontFormatException | IOException e) {
            System.out.println("Error");
        }
    }
    public Font getFont() {
        return font;
    }
}
