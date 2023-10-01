import java.awt.*;

public class Tank {
    private int x, y;
    private final int speed = 10;
    private final int tankWidth = 60;
    private final int screenWidth;

    public Tank() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        x = screenWidth / 2 - tankWidth / 2;
        y = (int) (screenSize.getHeight() - 80);
    }

    public void moveLeft() {
        if (x > 0) {
            x -= speed;
        }
    }

    public void moveRight() {
        if (x < screenWidth - tankWidth) {
            x += speed;
        }
    }
    public int getY() {
        return y;
    }

    public Bullet shoot() {
        return new Bullet(x + tankWidth / 2, y);
    }

    public void draw(Graphics g) {

        g.setColor(Color.GREEN);
        g.fillRect(x, y, tankWidth, 30);


        g.setColor(Color.DARK_GRAY);
        g.fillRect(x + tankWidth/4, y - 10, tankWidth/2, 10);


        g.setColor(Color.BLACK);
        g.fillRect(x, y + 30, tankWidth, 5);
        g.fillRect(x, y + 35, tankWidth, 5);
    }
}


