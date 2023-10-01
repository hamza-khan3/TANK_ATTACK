import java.awt.*;

class Bullet {
    private int x, y;
    private final int SPEED = 5;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y -= SPEED;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 5, 10);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 10);
    }
}
