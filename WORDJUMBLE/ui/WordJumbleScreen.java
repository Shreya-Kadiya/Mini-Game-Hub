package WORDJUMBLE.ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import WORDJUMBLE.util.BackgroundPanel;
import WORDJUMBLE.util.RoundedButton;
import javax.swing.Timer;

public class WordJumbleScreen {

    JFrame frame;
    BackgroundPanel panel;

    String difficulty;

    JLabel scoreLabel, streakLabel, LifeLabel, questionLabel,
            resultLabel, highScoreLabel, attemptsLabel, roundLabel;

    JTextField answerField;
    Color normalColor = Color.BLACK;
    Color correctColor = new Color(0, 160, 0); // green
    Color wrongColor = Color.RED;

    int score = 0;
    int streak = 0;
    int lives = 2;
    int attempts;
    int maxAttempts;

    int hintsUsed = 0;
    int totalRounds = 12;
    int currentRound = 0;

    String currentWord;
    String jumbledWord;

    boolean isGameOver = false;

    static int highScoreEasy = 0;
    static int highScoreMedium = 0;
    static int highScoreHard = 0;

    Random random = new Random();
    ArrayList<String> usedWords = new ArrayList<>();

    String[] easyWords = {"apple","ball","cat","dog","book","tree","fish","milk","pen","cup"};
    String[] mediumWords = {"table","chair","water","bread","light","plant","clock","paper","money"};
    String[] hardWords = {"people","number","system","family","school","friend","doctor","market"};

    WordJumbleScreen(String difficulty) {

        this.difficulty = difficulty;

        frame = new JFrame("Word Jumble - " + difficulty);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new BackgroundPanel("images/WordJ_bg.jpg");
        panel.setLayout(null);

        loadHighScore();
        resetAttempts();

        Font font = new Font("Arial", Font.BOLD, 20);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(450, 200, 200, 30);
        scoreLabel.setFont(font);

        streakLabel = new JLabel("Streak: 0");
        streakLabel.setBounds(600, 200, 200, 30);
        streakLabel.setFont(font);

        LifeLabel = new JLabel("Lives:" + lives);
        LifeLabel.setBounds(750, 200, 200, 30);
        LifeLabel.setFont(font);

        highScoreLabel = new JLabel("High Score: " + getHighScore());
        highScoreLabel.setBounds(900, 200, 250, 30);
        highScoreLabel.setFont(font);

        questionLabel = new JLabel("WORD", JLabel.CENTER);
        questionLabel.setBounds(500, 290, 500, 60);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 40));
        

        answerField = new JTextField();
        answerField.setBounds(600, 350, 300, 40);

        resultLabel = new JLabel("Enter answer", JLabel.CENTER);
        resultLabel.setBounds(500, 400, 500, 40);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));

        attemptsLabel = new JLabel("Attempts left: " + attempts);
        attemptsLabel.setBounds(790, 250, 200, 30);
        attemptsLabel.setFont(font);

        roundLabel = new JLabel();
        roundLabel.setBounds(590, 250, 200, 30);
        roundLabel.setFont(font);

        RoundedButton submitBtn = createSubmitButton();
        RoundedButton skipBtn = createSkipButton();
        RoundedButton hintBtn = createHintButton();
        RoundedButton backBtn = createBackButton();
        RoundedButton finishBtn = createFinishButton();
        RoundedButton rulesBtn = createRulesButton();

        panel.add(scoreLabel);
        panel.add(streakLabel);
        panel.add(LifeLabel);
        panel.add(highScoreLabel);
        panel.add(questionLabel);
        panel.add(answerField);
        panel.add(resultLabel);
        panel.add(attemptsLabel);
        panel.add(roundLabel);
        panel.add(submitBtn);
        panel.add(skipBtn);
        panel.add(hintBtn);
        panel.add(backBtn);
        panel.add(finishBtn);
        panel.add(rulesBtn);

        frame.setContentPane(panel);

        generateWord();
        updateUI();

        SwingUtilities.invokeLater(() -> answerField.requestFocusInWindow());
        frame.setVisible(true);
    }

    // ================= BUTTONS =================

    private RoundedButton createSubmitButton() {
        RoundedButton btn = new RoundedButton("Submit",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        btn.setBounds(600, 450, 100, 40);

        btn.addActionListener(e -> handleSubmit());
        answerField.addActionListener(e -> btn.doClick());

        return btn;
    }

    private RoundedButton createSkipButton() {
        RoundedButton btn = new RoundedButton("Skip",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        btn.setBounds(720, 450, 100, 40);

        btn.addActionListener(e -> {
            if (isGameOver) return;

            if (score < getSkipPenalty()) {
                resultLabel.setText("Need " + getSkipPenalty() + " points!");
                return;
            }

            applySkipPenalty();
            streak = 0;
            generateWord();
            updateUI();
        });

        return btn;
    }

    private RoundedButton createHintButton() {
        RoundedButton btn = new RoundedButton("Hint",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        btn.setBounds(850, 450, 100, 40);

        btn.addActionListener(e -> {
            if (isGameOver) return;

            if (score < getHintPenalty()) {
                resultLabel.setText("Need " + getHintPenalty() + " points!");
                return;
            }

            if (hintsUsed == 0)
                resultLabel.setText("Starts: " + currentWord.charAt(0));
            else
                resultLabel.setText("Ends: " + currentWord.charAt(currentWord.length() - 1));

            hintsUsed++;
            applyHintPenalty();
            updateUI();
        });

        return btn;
    }

    private RoundedButton createBackButton() {
        RoundedButton btn = new RoundedButton("Back",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        btn.setBounds(20, 20, 100, 40);

        btn.addActionListener(e -> {
            isGameOver = true;
            frame.dispose();
            new WordJumbleGUI();
        });

        return btn;
    }

    private RoundedButton createFinishButton() {
        RoundedButton btn = new RoundedButton("Finish",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        btn.setBounds(1200, 20, 100, 40);

        btn.addActionListener(e -> gameOver());

        return btn;
    }

    private RoundedButton createRulesButton() {
        RoundedButton btn = new RoundedButton("Rules",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        btn.setBounds(250, 20, 100, 40);

        btn.addActionListener(e -> showRules());

        return btn;
    }

    // ================= GAME LOGIC =================

    void handleSubmit() {

        if (isGameOver) return;

        String userAnswer = answerField.getText().trim().toLowerCase();

        if (userAnswer.isEmpty()) {
            resultLabel.setText("Enter a word!");
            return;
        }

        if (userAnswer.equals(currentWord)) {
            handleCorrectAnswer();
        } else {
            handleWrongAnswer();
        }

        answerField.setText("");
    }

void handleCorrectAnswer() {

    if (isGameOver) return;

    streak++;

    int used = maxAttempts - attempts + 1;

    int points = (difficulty.equals("Easy")) ? (used == 1 ? 10 : 5)
            : (difficulty.equals("Medium")) ? (used == 1 ? 15 : 10)
            : (used == 1 ? 20 : 15);

    score += points;

    if (streak % 5 == 0) score += 10;

    resultLabel.setForeground(correctColor);
    resultLabel.setText("Correct!");

    // small delay so user sees green
    Timer t = new Timer(400, e -> {
        generateWord();
        updateUI();
    });

    t.setRepeats(false);
    t.start();
}

    void handleWrongAnswer() {

    if (isGameOver) return;

    streak = 0;
    attempts--;

    resultLabel.setForeground(wrongColor);
    resultLabel.setText("Wrong Answer!");

    // show wrong for 0.5 sec
    Timer t = new Timer(500, e -> {

        if (attempts > 0) {
            resultLabel.setForeground(normalColor);
            resultLabel.setText("Try again");
        } 
        else {
            lives--;
            resultLabel.setForeground(normalColor);
            resultLabel.setText("Correct Answer: " + currentWord);

            generateWord();
        }

        if (lives <= 0) {
            gameOver();
            return;
        }

        updateUI();
    });

    t.setRepeats(false);
    t.start();
}

    // ================= WORD GENERATION =================

    void generateWord() {

        if (isGameOver) return;
        
        hintsUsed = 0;
        resetAttempts();

        String[] list = getWordList();

        if (usedWords.size() >= list.length) usedWords.clear();

        do {
            currentWord = list[random.nextInt(list.length)];
        } while (usedWords.contains(currentWord));

        usedWords.add(currentWord);

        jumbledWord = shuffleWord(currentWord);
        questionLabel.setText(jumbledWord);
        resultLabel.setForeground(normalColor);
        resultLabel.setText("Enter answer");

        currentRound++;
    }

    String shuffleWord(String word) {

        if (word.length() <= 2) return word;

        String shuffled;

        do {
            char[] letters = word.toCharArray();

            int swaps = difficulty.equals("Easy") ? letters.length :
                        difficulty.equals("Medium") ? letters.length - 2 :
                        letters.length - 3;

            for (int i = 0; i < swaps; i++) {
                int j = random.nextInt(letters.length);
                char temp = letters[i];
                letters[i] = letters[j];
                letters[j] = temp;
            }

            shuffled = new String(letters);

        } while (shuffled.equals(word));

        return shuffled;
    }


    void showRules() {

    String message = "WORD JUMBLE GAME RULES\n\n";

    if (difficulty.equals("Easy")) {
        message += "EASY MODE:\n" +
                "- 1 attempt per word\n" +
                "- +10 points (first try)\n" +
                "- +5 points (second try)\n" +
                "- Hint penalty: -2 points\n" +
                "- Skip penalty: -2 points\n" +
                "- Lives: 2\n";
    } 
    else if (difficulty.equals("Medium")) {
        message += "MEDIUM MODE:\n" +
                "- 2 attempts per word\n" +
                "- +15 points (first try)\n" +
                "- +10 points (second try)\n" +
                "- Hint penalty: -3 points\n" +
                "- Skip penalty: -4 points\n" +
                "- Lives: 2\n";
    } 
    else {
        message += "HARD MODE:\n" +
                "- 2 attempts per word\n" +
                "- +20 points (first try)\n" +
                "- +15 points (second try)\n" +
                "- Hint penalty: -5 points\n" +
                "- Skip penalty: -6 points\n" +
                "- Lives: 2\n";
    }

    message += "\nBONUS RULE:\n+10 points every 5 correct answers";

    JOptionPane.showMessageDialog(
            frame,
            message,
            "Game Rules",
            JOptionPane.INFORMATION_MESSAGE
    );
}

    // ================= UTIL =================

    String[] getWordList() {
        if (difficulty.equals("Easy")) return easyWords;
        if (difficulty.equals("Medium")) return mediumWords;
        return hardWords;
    }

    void resetAttempts() {
        attempts = difficulty.equals("Easy") ? 1 : 2;
        maxAttempts = attempts;
    }

    void updateUI() {

    if (isGameOver) return;

    scoreLabel.setText("Score: " + score);
    streakLabel.setText("Streak: " + streak);
    LifeLabel.setText("Lives: " + lives);

    roundLabel.setText("Round: " + currentRound + "/" + totalRounds);

    //  ADD THIS LINE 
    if (difficulty.equals("Easy")) {
        attemptsLabel.setText("Attempts left: " + attempts );
    } else {
        attemptsLabel.setText("Attempts left: " + attempts );
    }
}

    // ================= SCORE =================

    int getHintPenalty() {
        return difficulty.equals("Easy") ? 2 :
               difficulty.equals("Medium") ? 3 : 5;
    }

    int getSkipPenalty() {
        return difficulty.equals("Easy") ? 2 :
               difficulty.equals("Medium") ? 4 : 6;
    }

    void applyHintPenalty() {
        score = Math.max(0, score - getHintPenalty());
    }

    void applySkipPenalty() {
        score = Math.max(0, score - getSkipPenalty());
    }

    // ================= HIGH SCORE =================

    int getHighScore() {
        return difficulty.equals("Easy") ? highScoreEasy :
               difficulty.equals("Medium") ? highScoreMedium :
               highScoreHard;
    }

    void loadHighScore() {
        try (BufferedReader br = new BufferedReader(
                new FileReader("Word_Jumble/wordjumble_highscore.txt"))) {

            highScoreEasy = Integer.parseInt(br.readLine());
            highScoreMedium = Integer.parseInt(br.readLine());
            highScoreHard = Integer.parseInt(br.readLine());

        } catch (Exception ignored) {}
    }

    void saveHighScore() {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("Word_Jumble/wordjumble_highscore.txt"))) {

            bw.write(highScoreEasy + "\n");
            bw.write(highScoreMedium + "\n");
            bw.write(highScoreHard + "\n");

        } catch (Exception e) {
            System.out.println("Save error");
        }
    }

    // ================= GAME OVER =================

    void gameOver() {

        if (isGameOver) return;
        isGameOver = true;

        answerField.setEnabled(false);

        saveHighScore();

        new WordJumbleOvenrScreen(score, getHighScore(), difficulty);
        frame.dispose();
    }

    public static void main(String[] args) {
        new WordJumbleScreen("Easy");
    }
}