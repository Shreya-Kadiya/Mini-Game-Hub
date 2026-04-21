package WORDJUMBLE.ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import WORDJUMBLE.util.BackgroundPanel;
import WORDJUMBLE.util.RoundedButton;

public class WordJumbleScreen {

    JFrame frame;
    BackgroundPanel panel;

    String difficulty;

    // ================= UI COMPONENTS =================
    JLabel scoreLabel, streakLabel, LifeLabel, questionLabel,
            resultLabel, highScoreLabel, attemptsLabel, roundLabel;

    JTextField answerField;

    // ================= GAME STATE (ENCAPSULATION) =================
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

    // ================= HIGH SCORE (STATIC = SHARED STATE) =================
    static int highScoreEasy = 0;
    static int highScoreMedium = 0;
    static int highScoreHard = 0;

    // ================= UTIL =================
    Random random = new Random();
    ArrayList<String> usedWords = new ArrayList<>();

    // ================= WORD BANK =================
    String[] easyWords = {"apple","ball","cat","dog","book","tree","fish","milk","pen","cup"};
    String[] mediumWords = {"table","chair","water","bread","light","plant","clock","paper","money"};
    String[] hardWords = {"people","number","system","family","school","friend","doctor","market"};

    // =========================================================
    // CONSTRUCTOR (UI + INITIALIZATION)
    // =========================================================
    WordJumbleScreen(String difficulty) {

        this.difficulty = difficulty;

        frame = new JFrame("Word Jumble - " + difficulty);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new BackgroundPanel("images/WordJ_bg.jpg");
        panel.setLayout(null);

        loadHighScore(); // file I/O (persistence)

        resetAttempts(); // game setup

        Font font = new Font("Arial", Font.BOLD, 20);

        // ================= LABEL INIT (UI ONLY) =================
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

        resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setBounds(500, 400, 500, 40);

        attemptsLabel = new JLabel();
        attemptsLabel.setBounds(790, 250, 200, 30);
        attemptsLabel.setFont(font);

        roundLabel = new JLabel();
        roundLabel.setBounds(590, 250, 200, 30);
        roundLabel.setFont(font);

        // ================= BUTTONS =================
        RoundedButton submitBtn = createSubmitButton();
        RoundedButton skipBtn = createSkipButton();
        RoundedButton hintBtn = createHintButton();
        RoundedButton backBtn = createBackButton();
        RoundedButton finishBtn = createFinishButton();
        RoundedButton rulesBtn = createRulesButton();

        // ================= ADD UI =================
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

    // =========================================================
    // OOP: BUTTON FACTORY METHODS (CLEAN STRUCTURE)
    // =========================================================

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

        btn.addActionListener(e -> skipWord());
        return btn;
    }

    private RoundedButton createHintButton() {
        RoundedButton btn = new RoundedButton("Hint",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        btn.setBounds(850, 450, 100, 40);

        btn.addActionListener(e -> useHint());
        return btn;
    }

    private RoundedButton createBackButton() {
        RoundedButton btn = new RoundedButton("Back",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        btn.setBounds(20, 20, 100, 40);

        btn.addActionListener(e -> {
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

    // =========================================================
    // CORE GAME LOGIC METHODS (CLEAN OOP SEPARATION)
    // =========================================================

    void handleSubmit() {

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

        streak++;

        int used = maxAttempts - attempts + 1;
        int points = (difficulty.equals("Easy")) ? (used == 1 ? 10 : 5)
                : (difficulty.equals("Medium")) ? (used == 1 ? 15 : 10)
                : (used == 1 ? 20 : 15);

        score += points;

        if (streak % 5 == 0) {
            score += 10;
            resultLabel.setText("Streak Bonus +10!");
        } else {
            resultLabel.setText("Correct +" + points);
        }

        generateWord();
        updateUI();
    }

    void handleWrongAnswer() {

        streak = 0;
        attempts--;

        if (attempts > 0) {
            resultLabel.setText("Wrong! Try again");
        } else {
            lives--;
            resultLabel.setText("Answer: " + currentWord);
            generateWord();
        }

        if (lives == 0) gameOver();

        updateUI();
    }

    void skipWord() {

        if (score < getSkipPenalty()) {
            resultLabel.setText("Need " + getSkipPenalty() + " points!");
            return;
        }

        applySkipPenalty();
        streak = 0;

        generateWord();
        updateUI();
    }

    void useHint() {

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
    }



    // =========================================================
// RULES POPUP (UI LOGIC SEPARATION)
// =========================================================
void showRules() {

    String message = "SCORE SYSTEM\n\n";

    if (difficulty.equals("Easy")) {
        message += "Easy Mode:\n" +
                "+10 correct\n" +
                "-2 hint / skip\n" +
                "Attempts: 1\n" +
                "Lives: 2";
    } 
    else if (difficulty.equals("Medium")) {
        message += "Medium Mode:\n" +
                "+15 (1st try)\n" +
                "+10 (2nd try)\n" +
                "-3 hint | -4 skip\n" +
                "Attempts: 2\n" +
                "Lives: 2";
    } 
    else {
        message += "Hard Mode:\n" +
                "+20 (1st try)\n" +
                "+15 (2nd try)\n" +
                "-5 hint | -6 skip\n" +
                "Attempts: 2\n" +
                "Lives: 2";
    }

    message += "\n\nBonus:\n+10 every 5 correct";

    JOptionPane.showMessageDialog(
            frame,
            message,
            "Score Info",
            JOptionPane.INFORMATION_MESSAGE
    );
}


    // =========================================================
    // WORD GENERATION
    // =========================================================

    void generateWord() {

        hintsUsed = 0;
        resetAttempts();

        currentRound++;
        if (currentRound > totalRounds) {
            gameOver();
            return;
        }

        String[] list = getWordList();

        do {
            currentWord = list[random.nextInt(list.length)];
        } while (usedWords.contains(currentWord));

        usedWords.add(currentWord);
        jumbledWord = shuffle(currentWord);

        questionLabel.setText(jumbledWord);
    }

    String shuffle(String word) {

        char[] arr = word.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            int j = random.nextInt(arr.length);
            char t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }

        return new String(arr);
    }

    // =========================================================
    // UTIL METHODS
    // =========================================================

    String[] getWordList() {
        if (difficulty.equals("Easy")) return easyWords;
        if (difficulty.equals("Medium")) return mediumWords;
        return hardWords;
    }

    void resetAttempts() {
        attempts = (difficulty.equals("Easy")) ? 1 : 2;
        maxAttempts = attempts;
    }

    void updateUI() {
        scoreLabel.setText("Score: " + score);
        streakLabel.setText("Streak: " + streak);
        LifeLabel.setText("Lives: " + lives);
        roundLabel.setText("Round: " + currentRound + "/" + totalRounds);

        attemptsLabel.setText(
                difficulty.equals("Easy") ? "Only 1 attempt"
                        : "Attempts: " + attempts
        );
    }

    // penalties
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

    // =========================================================
    // HIGH SCORE (FILE IO)
    // =========================================================

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

    // =========================================================
    // GAME OVER
    // =========================================================

    void gameOver() {

        if (isGameOver) return;
        isGameOver = true;

        if (difficulty.equals("Easy") && score > highScoreEasy)
            highScoreEasy = score;
        else if (difficulty.equals("Medium") && score > highScoreMedium)
            highScoreMedium = score;
        else if (difficulty.equals("Hard") && score > highScoreHard)
            highScoreHard = score;

        saveHighScore();

        new WordJumbleOvenrScreen(score, getHighScore(), difficulty);
        frame.dispose();
    }

    // =========================================================
    public static void main(String[] args) {
        new WordJumbleScreen("Easy");
    }
}