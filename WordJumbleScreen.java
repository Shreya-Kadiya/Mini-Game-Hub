import javax.swing.*;
import java.awt.*;

//make word jumble screen
public class WordJumbleScreen {

    JFrame frame;
    BackgroundPanel panel;

    String difficulty;

    JLabel scoreLabel, streakLabel, wrongLabel, questionLabel, resultLabel, highScoreLabel;
    JTextField answerField;

    int score = 0;
    int streak = 0;
    int lives = 3;
    int attempts;

    static int highScoreEasy = 0;
    static int highScoreMedium = 0;
    static int highScoreHard = 0;

    String currentWord;
    String jumbledWord;

    public WordJumbleScreen(String difficulty) {
        this.difficulty = difficulty;

        frame = new JFrame("Word Jumble - " + difficulty);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new BackgroundPanel("src/WordJ_bg.jpg");
        panel.setLayout(null);

        // Back Button
        RoundedButton backBtn = new RoundedButton("Back",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        backBtn.setBounds(20, 20, 100, 40);
        backBtn.addActionListener(e -> {
            frame.dispose();
            new WordJumbleGUI();
        });

        // Attempts based on difficulty
        if (difficulty.equals("Hard")) {
            attempts = 3;
        } else {
            attempts = 2;
        }

        // High score selection
        int highScore = 0;
        if (difficulty.equals("Easy")) highScore = highScoreEasy;
        else if (difficulty.equals("Medium")) highScore = highScoreMedium;
        else highScore = highScoreHard;

        Font font = new Font("Arial", Font.BOLD, 20);

        // Labels
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(400, 60, 200, 30);
        scoreLabel.setFont(font);

        streakLabel = new JLabel("Streak: 0");
        streakLabel.setBounds(550, 60, 200, 30);
        streakLabel.setFont(font);

        wrongLabel = new JLabel("Lives: 3");
        wrongLabel.setBounds(700, 60, 200, 30);
        wrongLabel.setFont(font);

        highScoreLabel = new JLabel("High Score: " + highScore);
        highScoreLabel.setBounds(850, 60, 250, 30);
        highScoreLabel.setFont(font);

        // Word display
        questionLabel = new JLabel("WORD", JLabel.CENTER);
        questionLabel.setBounds(500, 200, 500, 60);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 40));

        // Input field
        answerField = new JTextField();
        answerField.setBounds(550, 300, 300, 40);
        answerField.setFont(new Font("Arial", Font.PLAIN, 18));

        // Result label
        resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setBounds(500, 360, 500, 40);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Attempts label
        JLabel attemptsLabel = new JLabel("Attempts: " + attempts);
        attemptsLabel.setBounds(700, 120, 200, 30);
        attemptsLabel.setFont(font);

        // Submit Button
        RoundedButton submitBtn = new RoundedButton("Submit",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        submitBtn.setBounds(600, 450, 100, 40);
        submitBtn.addActionListener(e -> {
            // logic later
        });

        // Skip Button
        RoundedButton skipBtn = new RoundedButton("Skip",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        skipBtn.setBounds(750, 450, 100, 40);
        skipBtn.addActionListener(e -> {
            // logic later
        });

        // Add components
        panel.add(scoreLabel);
        panel.add(streakLabel);
        panel.add(wrongLabel);
        panel.add(highScoreLabel);
        panel.add(questionLabel);
        panel.add(answerField);
        panel.add(resultLabel);
        panel.add(attemptsLabel);
        panel.add(submitBtn);
        panel.add(skipBtn);
        panel.add(backBtn);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new WordJumbleScreen("Easy");
    }
}