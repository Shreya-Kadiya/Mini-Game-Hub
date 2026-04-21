package MATH;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import MATH.util.BackgroundPanel;
import MATH.util.RoundedButton;

import MATH.generator.QuestionGenerator;
import MATH.generator.EasyGenerator;
import MATH.generator.MediumGenerator;
import MATH.generator.HardGenerator;



public class MathGameScreen {

    JFrame frame;
    BackgroundPanel panel;

    String difficulty;

    // ================= OOP: POLYMORPHISM =================
    QuestionGenerator generator;

    int correctAnswer;

    JLabel scoreLabel, streakLabel, wrongLabel,
            questionLabel, resultLabel, timerLabel, highScoreLabel;

    JTextField answerField;

    int score = 0;
    int streak = 0;
    int wrong = 5;

    int timeLeft = 30;
    Timer gameTimer;

    int highScoreEasy = 0;
    int highScoreMedium = 0;
    int highScoreHard = 0;

    public MathGameScreen(String difficulty) {

        this.difficulty = difficulty;

        createFileIfNotExists();
        loadHighScores();

        frame = new JFrame("Math Challenge - " + difficulty);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new BackgroundPanel("images/Math_bg.png");
        panel.setLayout(null);

        // ================= OOP: FACTORY STYLE INITIALIZATION =================
        generator = createGenerator(difficulty);

        // ================= BACK BUTTON =================
        RoundedButton backBtn = new RoundedButton(
                "Back",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186)
        );

        backBtn.setBounds(20, 20, 100, 40);

        backBtn.addActionListener(e -> {
            if (gameTimer != null) gameTimer.stop();
            frame.dispose();
            new MathChallangeGUI();
        });

        Font font = new Font("Arial", Font.BOLD, 20);

        // ================= LABELS =================
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(500, 60, 200, 30);
        scoreLabel.setFont(font);

        streakLabel = new JLabel("Streak: 0");
        streakLabel.setBounds(650, 60, 200, 30);
        streakLabel.setFont(font);

        wrongLabel = new JLabel("Life: 5");
        wrongLabel.setBounds(800, 60, 200, 30);
        wrongLabel.setFont(font);

        timerLabel = new JLabel("Time: 30");
        timerLabel.setBounds(950, 60, 200, 30);
        timerLabel.setFont(font);

        highScoreLabel = new JLabel("High Score: " + getHighScore());
        highScoreLabel.setBounds(1100, 60, 250, 30);
        highScoreLabel.setFont(font);

        // ================= QUESTION =================
        questionLabel = new JLabel("");
        questionLabel.setBounds(600, 250, 600, 50);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 40));

        // ================= INPUT =================
        answerField = new JTextField();
        answerField.setBounds(600, 350, 200, 40);
        answerField.setFont(new Font("Arial", Font.BOLD, 20));

        answerField.addActionListener(e -> checkAnswer());

        // ================= SUBMIT =================
        RoundedButton submitBtn = new RoundedButton(
                "Submit",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186)
        );

        submitBtn.setBounds(620, 450, 150, 40);
        submitBtn.addActionListener(e -> checkAnswer());

        // ================= RESULT =================
        resultLabel = new JLabel("Enter answer");
        resultLabel.setBounds(600, 500, 400, 40);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 22));

        // ================= ADD =================
        panel.add(backBtn);
        panel.add(scoreLabel);
        panel.add(streakLabel);
        panel.add(wrongLabel);
        panel.add(timerLabel);
        panel.add(highScoreLabel);
        panel.add(questionLabel);
        panel.add(answerField);
        panel.add(submitBtn);
        panel.add(resultLabel);

        frame.setContentPane(panel);
        frame.setVisible(true);

        SwingUtilities.invokeLater(() -> answerField.requestFocusInWindow());

        generateQuestion();
        startTimer();
    }

    // =========================================================
    // OOP FACTORY METHOD (IMPORTANT)
    // =========================================================
    private QuestionGenerator createGenerator(String difficulty) {

        if (difficulty.equals("Easy")) return new EasyGenerator();
        if (difficulty.equals("Medium")) return new MediumGenerator();
        return new HardGenerator();
    }

    // =========================================================
    // FIXED GENERATION LOGIC (YOUR ERROR FIXED HERE)
    // =========================================================
    private void generateQuestion() {

        // IMPORTANT ORDER FIX
        String q = generator.generateQuestion();
        correctAnswer = generator.generateAnswer();

        questionLabel.setText(q);

        resultLabel.setText("Enter answer");
        resultLabel.setForeground(Color.BLACK);
    }

    // =========================================================
    // ANSWER CHECK
    // =========================================================
    private void checkAnswer() {

        String input = answerField.getText();

        if (input.isEmpty()) {
            resultLabel.setText("Enter answer!");
            return;
        }

        int userAnswer;

        try {
            userAnswer = Integer.parseInt(input);
        } catch (Exception e) {
            resultLabel.setText("Invalid input!");
            return;
        }

        if (userAnswer == correctAnswer) {

            streak++;
            score++;

            if (streak % 3 == 0) {
                score++;
                resultLabel.setText("Correct + Bonus!");
            } else {
                resultLabel.setText("Correct");
            }

            resultLabel.setForeground(Color.GREEN);

        } else {
            wrong--;
            streak = 0;

            resultLabel.setText("Wrong! Ans: " + correctAnswer);
            resultLabel.setForeground(Color.RED);
        }

        scoreLabel.setText("Score: " + score);
        streakLabel.setText("Streak: " + streak);
        wrongLabel.setText("Life: " + wrong);

        answerField.setText("");

        if (wrong <= 0) {
            endGame("0 Lives Left");
            return;
        }

        new Timer(400, e -> generateQuestion()) {{
            setRepeats(false);
            start();
        }};
    }

    // =========================================================
    // TIMER
    // =========================================================
    private void startTimer() {

        gameTimer = new Timer(1000, e -> {

            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);

            if (timeLeft <= 5) {
                timerLabel.setForeground(Color.RED);
            }

            if (timeLeft <= 0) {
                gameTimer.stop();
                endGame("Time's up");
            }
        });

        gameTimer.start();
    }

    // =========================================================
    // GAME END
    // =========================================================
    private void endGame(String reason) {

        if (gameTimer != null) gameTimer.stop();

        if (difficulty.equals("Easy") && score > highScoreEasy)
            highScoreEasy = score;
        else if (difficulty.equals("Medium") && score > highScoreMedium)
            highScoreMedium = score;
        else if (difficulty.equals("Hard") && score > highScoreHard)
            highScoreHard = score;

        saveHighScores();

        new MathGameOver(reason, score, getHighScore(), difficulty);
        frame.dispose();
    }

    // =========================================================
    // HIGH SCORE
    // =========================================================
    private int getHighScore() {
        if (difficulty.equals("Easy")) return highScoreEasy;
        else if (difficulty.equals("Medium")) return highScoreMedium;
        else return highScoreHard;
    }

    // =========================================================
    // FILE SYSTEM
    // =========================================================
    private void createFileIfNotExists() {
        try {
            File file = new File("MATH/highscore.txt");
            if (!file.exists()) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write("0\n0\n0\n");
                bw.close();
            }
        } catch (Exception e) {
            System.out.println("File error");
        }
    }

    private void loadHighScores() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("MATH/highscore.txt"));
            highScoreEasy = Integer.parseInt(br.readLine());
            highScoreMedium = Integer.parseInt(br.readLine());
            highScoreHard = Integer.parseInt(br.readLine());
            br.close();
        } catch (Exception e) {
            System.out.println("Load error");
        }
    }

    private void saveHighScores() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("MATH/highscore.txt"));
            bw.write(highScoreEasy + "\n");
            bw.write(highScoreMedium + "\n");
            bw.write(highScoreHard + "\n");
            bw.flush();
            bw.close();
            System.out.println("✅ High scores saved - Easy: " + highScoreEasy + ", Medium: " + highScoreMedium + ", Hard: " + highScoreHard);
        } catch (Exception e) {
            System.out.println("❌ Save error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MathGameScreen("Easy");
    }
}