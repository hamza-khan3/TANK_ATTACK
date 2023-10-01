import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class GamePanel extends JPanel implements ActionListener {
    private Timer timer;
    private Tank tank;
    private ArrayList<Cloud> clouds;
    private ArrayList<Airplane> airplanes;
    private ArrayList<Bullet> bullets;

    private boolean isPaused = false;
    private double spawnProbability;

    private int score = 0;
    private int health = 100;
    private boolean gameOver = false;

    private Set<Integer> keysPressed = new HashSet<>();

    private long lastBulletTime = 0;
    private final long BULLET_COOLDOWN = 500;

    private JButton exitButton;

    public GamePanel(int difficulty) {
        this.tank = new Tank();
        this.airplanes = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.timer = new Timer(10, this);
        clouds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight() / 2);
            clouds.add(new Cloud(x, y));
        }


        switch (difficulty) {
            case 0: // Easy
                spawnProbability = 0.01;
                break;
            case 1: // Medium
                spawnProbability = 0.02;
                break;
            case 2: // Hard
                spawnProbability = 0.03;
                break;
        }

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                keysPressed.add(e.getKeyCode());
            }

            public void keyReleased(KeyEvent e) {
                keysPressed.remove(e.getKeyCode());
            }
        });
        this.setFocusable(true);


        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.add(exitButton);

        this.timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (!isPaused && !gameOver) {
            if (keysPressed.contains(KeyEvent.VK_LEFT)) {
                tank.moveLeft();
            }
            if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
                tank.moveRight();
            }

            long currentTime = System.currentTimeMillis();
            if (keysPressed.contains(KeyEvent.VK_SPACE) && currentTime - lastBulletTime >= BULLET_COOLDOWN) {
                bullets.add(tank.shoot());
                lastBulletTime = currentTime;
            }


            for (Bullet bullet : bullets) {
                bullet.move();
            }


            if (Math.random() < spawnProbability) {
                airplanes.add(new Airplane((int) (Math.random() * this.getWidth())));
            }

            for (Airplane airplane : airplanes) {
                airplane.move();
            }


            Iterator<Airplane> airplaneIterator = airplanes.iterator();
            while (airplaneIterator.hasNext()) {
                Airplane airplane = airplaneIterator.next();
                if (airplane.getY() > tank.getY()) {
                    health -= 10;
                    airplaneIterator.remove();
                    if (health <= 0) {
                        gameOver = true;
                    }
                }
            }


            Iterator<Bullet> bulletIterator = bullets.iterator();
            while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                airplaneIterator = airplanes.iterator();
                while (airplaneIterator.hasNext()) {
                    Airplane airplane = airplaneIterator.next();
                    if (bullet.getBounds().intersects(airplane.getBounds())) {
                        score += 10;
                        airplaneIterator.remove();
                        bulletIterator.remove();
                        break;
                    }
                }
            }


            repaint();
        } else if (gameOver && keysPressed.contains(KeyEvent.VK_R)) {

            airplanes.clear();
            bullets.clear();
            score = 0;
            health = 100;
            gameOver = false;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);




            Graphics2D g2d = (Graphics2D) g;
            GradientPaint skyGradient = new GradientPaint(0, 0, new Color(135, 206, 235), 0, getHeight(), new Color(176, 224, 230));
            g2d.setPaint(skyGradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());


        g.setColor(Color.WHITE);
        for (Cloud cloud : clouds) {
            g.fillOval(cloud.x, cloud.y, 60, 40);
            g.fillOval(cloud.x + 20, cloud.y - 20, 60, 40);
            g.fillOval(cloud.x - 20, cloud.y - 20, 60, 40);
        }



            g.setColor(new Color(139, 69, 19)); // Brown color
            int[] xPoints = {0, 50, 100, 150, 200, 250, 300, 350, 400, getWidth(), getWidth(), 0};
            int[] yPoints = {getHeight() - 50, getHeight() - 60, getHeight() - 55, getHeight() - 65, getHeight() - 58, getHeight() - 63, getHeight() - 52, getHeight() - 60, getHeight() - 55, getHeight() - 50, getHeight(), getHeight()};
            g.fillPolygon(xPoints, yPoints, xPoints.length);




        tank.draw(g);
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        for (Airplane airplane : airplanes) {
            airplane.draw(g);
        }
        if (isPaused) {
            g.setColor(Color.RED);
            g.drawString("PAUSED", 175, 200);
        }


        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 10, 10);
        g.drawString("Health: " + health, 10, 25);


        if (gameOver) {

            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());


            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.setColor(Color.RED);


            FontMetrics fmGameOver = g.getFontMetrics();
            int textWidth = fmGameOver.stringWidth("GAME OVER");
            int textX = (this.getWidth() - textWidth) / 2;
            int textY = this.getHeight() / 2 - 20;

            g.drawString("GAME OVER", textX, textY);


            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.setColor(Color.WHITE);

            FontMetrics fmRestart = g.getFontMetrics();
            int restartTextWidth = fmRestart.stringWidth("Press 'R' to Restart");
            int restartTextX = (this.getWidth() - restartTextWidth) / 2;
            int restartTextY = textY + 40;

            g.drawString("Press 'R' to Restart", restartTextX, restartTextY);
        }
    }
}
