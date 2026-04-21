import javax.swing.*;
import java.awt.*;

public class SudokuGameOver {
    private static String reason;

    public SudokuGameOver(String reason, int score, int highScore, String difficulty) {
        
        JFrame frame = new JFrame("Game Over");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel panel = new BackgroundPanel("src/Sudoku.png");

        // Reason
        JLabel reasonLabel = new JLabel(reason);
        reasonLabel.setBounds(700, 250, 500, 40);
        reasonLabel.setFont(new Font("Arial", Font.BOLD, 30));
        reasonLabel.setForeground(Color.BLUE);

        // Title
        JLabel title = new JLabel("GAME OVER");
        title.setBounds(650, 300, 400, 60);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.BLUE);


         // Score
        JLabel scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(700, 400, 400, 40);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 35));
        scoreLabel.setForeground(Color.BLUE);

        if(score > highScore) {

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

        // Play Again Button
        RoundedButton playAgain = new RoundedButton("Play Again",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186)  );
        playAgain.setBounds(670, 520, 200, 50);
        playAgain.setFont(new Font("Arial", Font.BOLD, 18));

        playAgain.addActionListener(e -> {
            new SudokuScreen(difficulty);
            frame.dispose();
        });
    

        //Back button
        RoundedButton backBtn = new RoundedButton("Back",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186)  );
        backBtn.setBounds(920, 520, 200, 50);
        backBtn.setFont(new Font("Arial", Font.BOLD, 18));
        backBtn.addActionListener(e -> {
            new SudokuGUI();
            frame.dispose();
        });

        //Home Button
        RoundedButton HomeBtn = new RoundedButton("Home",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186)  );
        HomeBtn.setBounds(420, 520, 200, 50);
        HomeBtn.setFont(new Font("Arial", Font.BOLD, 18));

        HomeBtn.addActionListener(e -> {
            new Dashboard();
            frame.dispose();
        });

        // Add components
        panel.add(title);
        panel.add(reasonLabel);
        panel.add(playAgain);
        panel.add(backBtn);
        panel.add(HomeBtn);
        panel.add(scoreLabel);
        frame.add(panel);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new SudokuGameOver(reason, 20, 15, "Medium");
    }
} 