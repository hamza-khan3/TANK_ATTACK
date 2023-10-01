import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame {
    public static void main(String[] args) {
        DifficultyDialog dialog = new DifficultyDialog();
        int difficulty = dialog.showDialog();

        if (difficulty != -1) {
            new Game(difficulty);
        }
    }

    public Game(int difficulty) {
        this.add(new GamePanel(difficulty));
        this.setTitle("Tank vs Airplanes");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    static class DifficultyDialog extends JDialog {
        private int selectedDifficulty = -1;

        public DifficultyDialog() {
            setModal(true);
            setTitle("Choose Difficulty");
            setSize(300, 150);
            setLocationRelativeTo(null);
            setLayout(new FlowLayout());

            JButton easyButton = createButton("Easy", 0);
            JButton mediumButton = createButton("Medium", 1);
            JButton hardButton = createButton("Hard", 2);

            add(easyButton);
            add(mediumButton);
            add(hardButton);
        }

        private JButton createButton(String text, int difficulty) {
            JButton button = new JButton(text);
            button.setPreferredSize(new Dimension(80, 40));
            button.setBackground(new Color(135, 206, 235));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedDifficulty = difficulty;
                    setVisible(false);
                }
            });
            return button;
        }

        public int showDialog() {
            setVisible(true);
            return selectedDifficulty;
        }
    }
}

