import javax.swing.*;
import java.util.Objects;

public class Buttons {
    private JButton startButton;
    private JButton OKButton;
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;

    public Buttons() {
        ImageIcon startButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/startButton.png")));
        ImageIcon OKButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/OKButton.png")));
        ImageIcon easyButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/easyButton.png")));
        ImageIcon normalButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/normalButton.png")));
        ImageIcon hardButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/hardButton.png")));
        startButton = new JButton(startButtonImage);
        OKButton = new JButton(OKButtonImage);
        easyButton = new JButton(easyButtonImage);
        normalButton = new JButton(normalButtonImage);
        hardButton = new JButton(hardButtonImage);
        int x = (Game.WIDTH - startButtonImage.getIconWidth()) / 2;
        int y = 500;
        startButton.setBounds(x,y,startButtonImage.getIconWidth(),startButtonImage.getIconHeight());
        OKButton.setBounds(x,y,startButtonImage.getIconWidth(),startButtonImage.getIconHeight());
        normalButton.setBounds((Game.WIDTH - normalButtonImage.getIconWidth()) / 2,y-50,normalButtonImage.getIconWidth(),normalButtonImage.getIconHeight());
        easyButton.setBounds(normalButton.getX() - 40 - easyButtonImage.getIconWidth(),y-50,easyButtonImage.getIconWidth(),easyButtonImage.getIconHeight());
        hardButton.setBounds(normalButton.getX() + normalButtonImage.getIconWidth() + 40,y-50,hardButtonImage.getIconWidth(),hardButtonImage.getIconHeight());
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getOKButton() {
        return OKButton;
    }

    public JButton getEasyButton() {
        return easyButton;
    }

    public JButton getNormalButton() {
        return normalButton;
    }

    public JButton getHardButton() {
        return hardButton;
    }
}
