package MATH;
import javax.swing.*;
import java.awt.*;
import MATH.util.BackgroundPanel;
import MATH.util.RoundedButton;
import Home_Screen.Dashboard;



public class MathGameOver {

    public MathGameOver(String reason, int score, int highScore, String difficulty) {

        JFrame frame = new JFrame("Game Over");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //  FIXED IMAGE PATH (images folder)
        BackgroundPanel panel = new BackgroundPanel("images/MathSolve.png");

        // ================= REASON =================
        JLabel reasonLabel = new JLabel(reason);
        reasonLabel.setBounds(700, 250, 400, 40);
        reasonLabel.setFont(new Font("Arial", Font.BOLD, 30));
        reasonLabel.setForeground(Color.RED);

        // ================= TITLE =================
        JLabel title = new JLabel("GAME OVER");
        title.setBounds(650, 300, 400, 60);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.BLUE);

        JLabel scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(700, 400, 400, 40);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 35));
        scoreLabel.setForeground(Color.BLUE);

        if(score >= highScore) {

            JLabel newHigh = new JLabel("");
            newHigh.setText("New High Score!");
            newHigh.setBounds(650, 450, 400, 40);
            newHigh.setForeground(Color.GREEN.darker());
            newHigh.setFont(new Font("Arial", Font.BOLD, 35));
            panel.add(newHigh);
        }
        else{          

            JLabel highScoreLabel = new JLabel("High Score: " + highScore);
            highScoreLabel.setBounds(700, 440, 400, 40);
            highScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
            highScoreLabel.setForeground(Color.BLUE);
            panel.add(highScoreLabel);
        }


        // ================= PLAY AGAIN =================
        RoundedButton playAgain = new RoundedButton(
                "Play Again",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186)
        );

        playAgain.setBounds(670, 520, 200, 50);

        playAgain.addActionListener(e -> {
            new MathGameScreen(difficulty);
            frame.dispose();
        });

        // ================= BACK =================
        RoundedButton backBtn = new RoundedButton(
                "Back",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186)
        );

        backBtn.setBounds(920, 520, 200, 50);

        backBtn.addActionListener(e -> {
            new MathChallangeGUI();
            frame.dispose();
        });

        // ================= HOME =================
        RoundedButton HomeBtn = new RoundedButton(
                "Home",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186)
        );

        HomeBtn.setBounds(420, 520, 200, 50);

        HomeBtn.addActionListener(e -> {
            new Dashboard();
            frame.dispose();
        });

        // ================= ADD COMPONENTS =================
        panel.add(title);
        panel.add(reasonLabel);
        panel.add(playAgain);
        panel.add(backBtn);
        panel.add(HomeBtn);
        panel.add(scoreLabel);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MathGameOver("Time's Up", 20, 15, "Medium");
    }
}