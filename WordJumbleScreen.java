import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class WordJumbleScreen {

    JFrame frame;
    BackgroundPanel panel;

    String difficulty;

    JLabel scoreLabel, streakLabel, LifeLabel, questionLabel, resultLabel, highScoreLabel, attemptsLabel;
    JTextField answerField;

    int score = 0;
    int streak = 0;
    int lives = 3;
    int attempts;
    int maxAttempts;
    int hintsUsed = 0;

    static int highScoreEasy = 0;
    static int highScoreMedium = 0;
    static int highScoreHard = 0;

    String currentWord;
    String jumbledWord;

    Random random = new Random();
    ArrayList<String> usedWords = new ArrayList<>();

    String[] easyWords = {"apple", "ball", "cat", "dog", "book"};
    String[] mediumWords = {"orange", "laptop", "garden", "school", "market"};
    String[] hardWords = {"computer", "elephant", "developer", "keyboard", "internet"};

    String fileName = "wordjumble_highscore.txt";


    WordJumbleScreen(String difficulty) {

        this.difficulty = difficulty;

        frame = new JFrame("Word Jumble - " + difficulty);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new BackgroundPanel("src/WordJ_bg.jpg");
        panel.setLayout(null);

        loadHighScore();

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

        // Attempts setup
        resetAttempts();

        int highScore = getHighScore();

        Font font = new Font("Arial", Font.BOLD, 20);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(400, 60, 200, 30);
        scoreLabel.setFont(font);

        streakLabel = new JLabel("Streak: 0");
        streakLabel.setBounds(550, 60, 200, 30);
        streakLabel.setFont(font);

        LifeLabel = new JLabel("Lives: 3");
        LifeLabel.setBounds(700, 60, 200, 30);
        LifeLabel.setFont(font);

        highScoreLabel = new JLabel("High Score: " + highScore);
        highScoreLabel.setBounds(850, 60, 250, 30);
        highScoreLabel.setFont(font);

        questionLabel = new JLabel("WORD", JLabel.CENTER);
        questionLabel.setBounds(500, 200, 500, 60);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 40));

        answerField = new JTextField();
        answerField.setBounds(550, 300, 300, 40);
        answerField.setFont(new Font("Arial", Font.PLAIN, 18));

        resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setBounds(500, 360, 500, 40);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));

        attemptsLabel = new JLabel("Attempts left: " + attempts);
        attemptsLabel.setBounds(700, 120, 200, 30);
        attemptsLabel.setFont(font);

        // Submit Button
        RoundedButton submitBtn = new RoundedButton("Submit",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        submitBtn.setBounds(600, 450, 100, 40);
        submitBtn.addActionListener(e -> {
            String userAnswer = answerField.getText().trim().toLowerCase();

            if (userAnswer.equals(currentWord)) {
                resultLabel.setText("Correct!");
                handleCorrectAnswer();
            } else {
                handleWrongAnswer();
            }

            answerField.setText("");
            
        });

        answerField.addActionListener(e -> submitBtn.doClick());

        // Skip Button
        RoundedButton skipBtn = new RoundedButton("Skip",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        skipBtn.setBounds(720, 450, 100, 40);
        skipBtn.addActionListener(e -> {
            int penalty = getSkipPenalty();
            if (score < penalty) {
            resultLabel.setText("Need at least " + penalty + " points to skip!");
            return;
    }
            applySkipPenalty();
            streak = 0;
            generateWord();
            updateUI();
        });

        // Hint Button
        RoundedButton hintBtn = new RoundedButton("Hint",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        hintBtn.setBounds(850, 450, 100, 40);
        hintBtn.addActionListener(e -> {

            int penalty = getHintPenalty();

            if (score < penalty) {
                resultLabel.setText("Not enough score for hint!");
                return;
            }

            if (hintsUsed == 0) {
                resultLabel.setText("Hint: Starts with '" + currentWord.charAt(0) + "'");
                applyHintPenalty();
                hintsUsed++;

            } else if (hintsUsed == 1) {
                resultLabel.setText("Hint: Ends with '" + currentWord.charAt(currentWord.length() - 1) + "'");
                applyHintPenalty();
                hintsUsed++;

            } else {
                resultLabel.setText("No more hints!");
            }

            updateUI();
        });

        panel.add(scoreLabel);
        panel.add(streakLabel);
        panel.add(LifeLabel);
        panel.add(highScoreLabel);
        panel.add(questionLabel);
        panel.add(answerField);
        panel.add(resultLabel);
        panel.add(attemptsLabel);
        panel.add(submitBtn);
        panel.add(skipBtn);
        panel.add(hintBtn);
        panel.add(backBtn);

        frame.setContentPane(panel);
        generateWord();
        updateUI();


        SwingUtilities.invokeLater(() -> answerField.requestFocusInWindow());
        frame.setVisible(true);
    }


    void generateWord() {

        answerField.requestFocusInWindow();
        hintsUsed = 0;
        resetAttempts();

        String[] wordList;

        resultLabel.setText("");

        if (difficulty.equals("Easy")) wordList = easyWords;
        else if (difficulty.equals("Medium")) wordList = mediumWords;
        else wordList = hardWords;

        if (usedWords.size() == wordList.length) {
            usedWords.clear();
        }

        do {
            currentWord = wordList[random.nextInt(wordList.length)];
        } while (usedWords.contains(currentWord));

        usedWords.add(currentWord);

        jumbledWord = shuffleWord(currentWord);
        questionLabel.setText(jumbledWord);
    }

    String shuffleWord(String word) {
        if (word.length() <= 2) return word;

        char[] letters;
        String shuffled;

        do {
            letters = word.toCharArray();
            for (int i = 0; i < letters.length; i++) {
                int j = random.nextInt(letters.length);
                char temp = letters[i];
                letters[i] = letters[j];
                letters[j] = temp;
            }
            shuffled = new String(letters);
        } while (shuffled.equals(word));

        return shuffled;
    }

    void handleCorrectAnswer() {

        streak++;
        updateScore();

        if (streak % 3 == 0) {
            score += 10;
            lives++;
            resultLabel.setText("Streak Bonus!");
        }

        generateWord();
        updateUI();
    }

    void handleWrongAnswer() {
        streak=0;

        attempts--;

        if (attempts > 0) {
            resultLabel.setText("Wrong! Try again");
        } else {
            lives--;
            resultLabel.setText("Lost 1 life!");
            generateWord();
        }

        updateUI();

        if (lives == 0) gameOver();
    }

    void updateScore() {

        int used = maxAttempts - attempts + 1;

        if (difficulty.equals("Easy")) {
            score += (used == 1) ? 10 : 5;
        } else if (difficulty.equals("Medium")) {
            score += (used == 1) ? 15 : 10;
        } else {
            if (used == 1) score += 20;
            else if (used == 2) score += 15;
            else score += 10;
        }
    }

    void resetAttempts() {
        if (difficulty.equals("Hard")) {
            attempts = 3;
            maxAttempts = 3;
        } else {
            attempts = 2;
            maxAttempts = 2;
        }
    }

    void applyHintPenalty() {
        if (difficulty.equals("Easy")) score -= 2;
        else if (difficulty.equals("Medium")) score -= 3;
        else score -= 5;

        if (score < 0) score = 0;
    }


    int getHintPenalty() {
    if (difficulty.equals("Easy")) return 2;
    else if (difficulty.equals("Medium")) return 3;
    else return 5;
}

    int getSkipPenalty() {
        if (difficulty.equals("Easy")) return 2;
        else if (difficulty.equals("Medium")) return 4;
        else return 6;
    }

    void applySkipPenalty() {
        if (difficulty.equals("Easy")) score -= 2;
        else if (difficulty.equals("Medium")) score -= 4;
        else score -= 6;

        if (score < 0) score = 0;
    }

    void updateUI() {
        scoreLabel.setText("Score: " + score);
        streakLabel.setText("Streak: " + streak);
        LifeLabel.setText("Lives: " + lives);
        attemptsLabel.setText("Attempts left: " + attempts);
    }

    void gameOver() {

        if (difficulty.equals("Easy") && score > highScoreEasy) highScoreEasy = score;
        else if (difficulty.equals("Medium") && score > highScoreMedium) highScoreMedium = score;
        else if (difficulty.equals("Hard") && score > highScoreHard) highScoreHard = score;

        saveHighScore();
        highScoreLabel.setText("High Score: " + getHighScore());

        JOptionPane.showMessageDialog(frame, "Game Over!\nScore: " + score);
        
        frame.dispose();
        new WordJumbleGUI();
    }

    int getHighScore() {
        if (difficulty.equals("Easy")) return highScoreEasy;
        if (difficulty.equals("Medium")) return highScoreMedium;
        return highScoreHard;
    }

    void loadHighScore() {
        try {
            File file = new File(fileName);
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            highScoreEasy = Integer.parseInt(br.readLine());
            highScoreMedium = Integer.parseInt(br.readLine());
            highScoreHard = Integer.parseInt(br.readLine());
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading high score");
        }
    }

    void saveHighScore() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(highScoreEasy + "\n");
            bw.write(highScoreMedium + "\n");
            bw.write(highScoreHard + "\n");
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving high score");
        }
    }

    public static void main(String[] args) {
        new WordJumbleScreen("Easy");
    }
}