import javax.swing.*;


public class Buttons {
    private final JButton startButton;
    private final JButton OKButton;
    private final JButton easyButton;
    private final JButton normalButton;
    private final JButton hardButton;

    public Buttons() {
        ImageIcon startButtonImage = Resources.startButtonImg;
        ImageIcon OKButtonImage = Resources.OKButtonImg;
        ImageIcon easyButtonImage = Resources.easyButtonImg;
        ImageIcon normalButtonImage = Resources.normalButtonImg;
        ImageIcon hardButtonImage = Resources.hardButtonImg;
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
