import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

public class MathGameScreen {

    JFrame frame;
    BackgroundPanel panel;

    String difficulty;
    int correctAnswer;

    JLabel scoreLabel, streakLabel, wrongLabel, questionLabel, resultLabel, timerLabel, highScoreLabel;
    JTextField answerField;

    int score = 0;
    int streak = 0;
    int wrong = 5; //life

    int timeLeft = 30;
    Timer gameTimer;

    static int highScoreEasy = 0;
    static int highScoreMedium = 0;
    static int highScoreHard = 0;

    public MathGameScreen(String difficulty) {
        this.difficulty = difficulty;

        createFileIfNotExists();
        loadHighScores();

        frame = new JFrame("Math Challenge - " + difficulty);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new BackgroundPanel("src/Math_bg.png");
        panel.setLayout(null);

        // Back Button
        RoundedButton backBtn = new RoundedButton("Back",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        backBtn.setBounds(20, 20, 100, 40);
        backBtn.addActionListener(e -> {
            if (gameTimer != null) gameTimer.stop();
            frame.dispose();
            new MathChallangeGUI();
        });

        // Labels
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(500, 60, 200, 30);

        streakLabel = new JLabel("Streak: 0");
        streakLabel.setBounds(650, 60, 200, 30);

        wrongLabel = new JLabel("Life: 5");
        wrongLabel.setBounds(800, 60, 200, 30);

        timerLabel = new JLabel("Time: 30");
        timerLabel.setBounds(950, 60, 200, 30);

        highScoreLabel = new JLabel("High Score: " + getHighScore());
        highScoreLabel.setBounds(1100, 60, 250, 30);

        Font font = new Font("Arial", Font.BOLD, 20);
        scoreLabel.setFont(font);
        streakLabel.setFont(font);
        wrongLabel.setFont(font);
        timerLabel.setFont(font);
        highScoreLabel.setFont(font);

        // Question
        questionLabel = new JLabel("");
        questionLabel.setBounds(600, 250, 500, 50);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 40));

        // Input
        answerField = new JTextField();
        answerField.setBounds(600, 350, 200, 40);
        answerField.setFont(new Font("Arial", Font.BOLD, 20));
        answerField.addActionListener(e -> checkAnswer());

        // Submit
        RoundedButton submitBtn = new RoundedButton("Submit",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        submitBtn.setBounds(620, 450, 150, 40);
        submitBtn.addActionListener(e -> checkAnswer());

        // Result
        resultLabel = new JLabel("Enter answer");
        resultLabel.setBounds(600, 500, 400, 40);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 22));

        // Add components
        panel.add(scoreLabel);
        panel.add(streakLabel);
        panel.add(wrongLabel);
        panel.add(timerLabel);
        panel.add(highScoreLabel);
        panel.add(questionLabel);
        panel.add(answerField);
        panel.add(submitBtn);
        panel.add(resultLabel);
        panel.add(backBtn);

        frame.setContentPane(panel);
        frame.setVisible(true);

        SwingUtilities.invokeLater(() -> answerField.requestFocusInWindow());

        generateQuestion();
        startTimer();
    }

    private void generateQuestion() {
        Random rand = new Random();

        int num1, num2, num3;
        char op1, op2;

        if (difficulty.equals("Easy")) {
            num1 = rand.nextInt(15) + 1;
            num2 = rand.nextInt(15) + 1;
            op1 = rand.nextBoolean() ? '+' : '-';

            if (op1 == '-' && num2 > num1) {
                int temp = num1; num1 = num2; num2 = temp;
            }

            correctAnswer = (op1 == '+') ? num1 + num2 : num1 - num2;
            questionLabel.setText(num1 + " " + op1 + " " + num2 + " = ?");
        }

        else if (difficulty.equals("Medium")) {
            num1 = rand.nextInt(20) + 1;
            num2 = rand.nextInt(15) + 1;
            num3 = rand.nextInt(10) + 1;

            op1 = getOperator(rand);
            op2 = getOperator(rand);

            int temp = calculate(num1, num2, op1);
            correctAnswer = calculate(temp, num3, op2);

            questionLabel.setText(num1 + " " + op1 + " " + num2 + " " + op2 + " " + num3 + " = ?");
        }

        else {
            num1 = rand.nextInt(20) + 1;
            num2 = rand.nextInt(10) + 1;
            num3 = rand.nextInt(8) + 1;

            correctAnswer = (num1 + num2) * num3;
            questionLabel.setText("(" + num1 + " + " + num2 + ") × " + num3 + " = ?");
        }

        resultLabel.setText("Enter answer");
        resultLabel.setForeground(Color.BLACK);
    }

    private char getOperator(Random rand) {
        int op = rand.nextInt(3);
        return (op == 0) ? '+' : (op == 1) ? '-' : '*';
    }

    private int calculate(int a, int b, char op) {
        return (op == '+') ? a + b : (op == '-') ? a - b : a * b;
    }

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
                score += 1;
                resultLabel.setText("Correct + Bonus point!");
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

        new Timer(800, e -> generateQuestion()) {{
            setRepeats(false);
            start();
        }};
    }

    private void startTimer() {
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time: " + timeLeft);

            if (timeLeft <= 5) {
                timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
                timerLabel.setForeground(Color.RED);
            }

            if (timeLeft <= 0) {
                gameTimer.stop();
                endGame("Time's up");
            }
        });
        gameTimer.start();
    }
    
    private void endGame(String reason) {
        if (gameTimer != null) gameTimer.stop();

        boolean newHigh = false;

        if (difficulty.equals("Easy") && score > highScoreEasy) {
            highScoreEasy = score;
            newHigh = true;
        } else if (difficulty.equals("Medium") && score > highScoreMedium) {
            highScoreMedium = score;
            newHigh = true;
        } else if (difficulty.equals("Hard") && score > highScoreHard) {
            highScoreHard = score;
            newHigh = true;
        }

        saveHighScores();

        String message =  reason +
                "\nGame Over \nScore: " + score +
                "\nHigh Score: " + getHighScore();

        if (newHigh) message += "\n<h1> New High Score!<h1>";

        frame.dispose();
        new MathGameOver(reason, score, getHighScore(), difficulty);

       
    }

    private int getHighScore() {
        if (difficulty.equals("Easy")) return highScoreEasy;
        if (difficulty.equals("Medium")) return highScoreMedium;
        return highScoreHard;
    }

    private void createFileIfNotExists() {
        try {
            File file = new File("highscore.txt");
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
            BufferedReader br = new BufferedReader(new FileReader("highscore.txt"));
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
            BufferedWriter bw = new BufferedWriter(new FileWriter("highscore.txt"));
            bw.write(highScoreEasy + "\n");
            bw.write(highScoreMedium + "\n");
            bw.write(highScoreHard + "\n");
            bw.close();
        } catch (Exception e) {
            System.out.println("Save error");
        }
    }

    public static void main(String[] args) {
        new MathGameScreen("Easy");
    }
}