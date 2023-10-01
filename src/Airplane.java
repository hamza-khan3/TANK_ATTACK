import java.awt.*;

class Airplane {
    private int x, y;
    private final int SPEED = 2;

    public Airplane(int x) {
        this.x = x;
        this.y = 0;
    }

    public void move() {
        y += SPEED;
    }

    public void draw(Graphics g) {

        g.setColor(Color.GRAY);
        int[] xPoints = {x + 20, x + 40, x, x + 20};
        int[] yPoints = {y, y + 10, y + 10, y};
        g.fillPolygon(xPoints, yPoints, 4);

       
        g.setColor(Color.DARK_GRAY);
        g.fillPolygon(new int[]{x + 10, x + 30, x + 20}, new int[]{y + 10, y + 10, y + 20}, 3);
        g.fillPolygon(new int[]{x + 10, x + 30, x + 20}, new int[]{y, y, y - 10}, 3);


        g.setColor(Color.DARK_GRAY);
        g.fillPolygon(new int[]{x, x + 10, x}, new int[]{y + 5, y + 10, y + 15}, 3);
    }



    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 20);
    }
    public int getY() {
        return y;
    }
}

