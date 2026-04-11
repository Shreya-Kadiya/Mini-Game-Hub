import javax.swing.*;
import java.awt.*;

public class WordGuessScreen {

    JFrame frame;
    String difficulty;

    int totalScore = 0;
    int round = 1;

    int wrongAttempts = 0;
    int maxAttempts;

    int revealUsed = 0;
    int highScore = 0;

    String word;
    char[] display;

    java.util.Set<Character> guessedLetters = new java.util.HashSet<>();


    JLabel wordLabel, scoreLabel, attemptsLabel, roundLabel,resultLabel,highScoreLabel;
    JTextField inputField;
    RoundedButton submitButton, revealButton;

    String[] easyWords = {"CAT", "DOG", "BALL", "TREE", "BOOK"};
    String[] mediumWords = {"APPLE", "ORANGE", "CHAIR", "TABLE", "PHONE"};
    String[] hardWords = {"COMPUTER", "KEYBOARD", "LANGUAGE", "SOFTWARE", "ELEPHANT"};



    public WordGuessScreen(String difficulty) 
    {


        this.difficulty = difficulty;

        loadHighScore();

        if (difficulty.equals("Easy")) {
        maxAttempts = 2;
        } else if (difficulty.equals("Medium")) {
            maxAttempts = 3;
        } else {
            maxAttempts = 3; // Hard
        }
        
        // Frame setup
        frame = new JFrame("Word Guess - " + difficulty);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Background panel
        BackgroundPanel panel = new BackgroundPanel("src/GTW_bg.jpg");
        panel.setLayout(null);
        frame.add(panel);


        word = getRandomWord();
        word = word.toUpperCase();
        // Convert to char array
       display = word.toCharArray();
        applyMasking();
        

        // Back Button
        RoundedButton backBtn = new RoundedButton("Back",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));

        backBtn.setBounds(20, 20, 100, 40);
        backBtn.addActionListener(e -> {
            frame.dispose();
            new WordGuessGUI();
        });
        // Labels
        
       
        scoreLabel = new JLabel("Score: " + totalScore);
        scoreLabel.setBounds(730, 150, 200, 30);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));

        roundLabel = new JLabel("Round: " + round + "/10");
        roundLabel.setBounds(500, 150, 200, 30);
        roundLabel.setFont(new Font("Arial", Font.BOLD, 20));

        highScoreLabel = new JLabel("High Score: " + highScore);
        highScoreLabel.setBounds(950, 150, 250, 30);
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 20));

        attemptsLabel = new JLabel("Attempts Left: " + (maxAttempts - wrongAttempts));
        attemptsLabel.setBounds(680, 250, 200, 30);
        attemptsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        attemptsLabel.setForeground(new Color(1,27,59));

        wordLabel = new JLabel(getDisplayWord());
        wordLabel.setBounds(680, 300, 600, 50);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 40));
        wordLabel.setForeground(new Color(1,27,59));

        inputField = new JTextField();
        inputField.setBounds(680, 370, 150, 40);
        inputField.setFont(new Font("Arial", Font.PLAIN, 20));
        inputField.addActionListener(e -> handleGuess());

        resultLabel = new JLabel("");
        resultLabel.setBounds(680, 410, 500, 30);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
       

        

        submitButton = new RoundedButton("Submit",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186)  );
        submitButton.setBounds(630, 450, 120, 40);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.addActionListener(e -> handleGuess());



        revealButton = new RoundedButton("Reveal",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186)  );
        revealButton.setBounds(780, 450, 120, 40);
        revealButton.setFont(new Font("Arial", Font.BOLD, 16));
        revealButton.addActionListener(e -> handleReveal());
        
        RoundedButton FinishBtn = new RoundedButton("Finish",
                new Color(5,72,149),
                new Color(26,3,85),
                new Color(48,14,186));
        FinishBtn.setBounds(1200, 20, 100, 40);
        FinishBtn.addActionListener(e -> {
            showFinalResult();
        });

        frame.setVisible(true);
        panel.add(wordLabel);
        panel.add(scoreLabel);
        panel.add(roundLabel);
        panel.add(attemptsLabel);
        panel.add(inputField);
        panel.add(resultLabel);
        panel.add(highScoreLabel);
        panel.add(submitButton);
        panel.add(revealButton);
        panel.add(backBtn);
        panel.add(FinishBtn);



    }


// Method to get a random word based on difficulty
    String getRandomWord() {
    String[] selectedList;


    if (difficulty.equals("Easy")) {
        selectedList = easyWords;
    } else if (difficulty.equals("Medium")) {
        selectedList = mediumWords;
    } else {
        selectedList = hardWords;
    }

    int index = (int)(Math.random() * selectedList.length);
    return selectedList[index];
    }


// Method to get the current display word with spaces
    String getDisplayWord() {
    StringBuilder sb = new StringBuilder();
    for (char c : display) {
        sb.append(c).append(" ");
    }
    return sb.toString();
}


// Method to handle guess submission
    void handleGuess() {

        String input = inputField.getText().toUpperCase();

        // Clear after reading
        inputField.setText("");
        inputField.requestFocusInWindow();

        // Validate input
        if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
            resultLabel.setText("Please enter a single letter!");
            inputField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            resultLabel.setForeground(Color.RED);
            return;
        }
        

        char guess = input.charAt(0);

        // Check repeated guess
        if (guessedLetters.contains(guess)) {
            resultLabel.setText("Already guessed!");
            resultLabel.setForeground(Color.ORANGE);
            inputField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            return;
        }

        guessedLetters.add(guess);

        boolean found = false;

        // Check in word
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess && display[i] == '_') {
                display[i] = guess;
                found = true;
            }
        }

 
        if (found) {
            resultLabel.setText("Correct Letter!");
            resultLabel.setForeground(new Color(0,128,0)); // green
            inputField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        } 
        else {
            resultLabel.setText("Wrong Letter!");
            resultLabel.setForeground(Color.RED);
            wrongAttempts++;
            inputField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }


        updateUI();
        checkRoundEnd();
    }





// Method to update labels    
    void updateUI() {
        int previewScore = 20 - (wrongAttempts * 3) - (revealUsed * 5);
        revealButton.setEnabled(previewScore >= 5);
        wordLabel.setText(getDisplayWord());
        attemptsLabel.setText("Attempts Left: " + Math.max(0, (maxAttempts - wrongAttempts)));
        scoreLabel.setText("Score: " + totalScore);
        roundLabel.setText("Round: " + round + "/10");
        inputField.requestFocusInWindow();
    }


// Method to handle reveal
void handleReveal() {

    // Check if any blanks exist
    boolean hasBlank = false;
    for (char c : display) {
        if (c == '_') {
            hasBlank = true;
            break;
        }
    }

    if (!hasBlank) {
        resultLabel.setText("No letters to reveal!");
        resultLabel.setForeground(Color.RED);
        return;
    }

    int previewScore = 20 - (wrongAttempts * 3) - (revealUsed * 5);

    if (previewScore < 5) {
        resultLabel.setText("Not enough score to reveal!");
        resultLabel.setForeground(Color.RED);
        return;
    }

    // Reveal one letter
   java.util.List<Integer> hiddenIndexes = new java.util.ArrayList<>();

    for (int i = 0; i < display.length; i++) {
        if (display[i] == '_') {
            hiddenIndexes.add(i);
        }
    }

    int index = hiddenIndexes.get((int)(Math.random() * hiddenIndexes.size()));
    display[index] = word.charAt(index);

    // increment only once AFTER success
    revealUsed++;

    resultLabel.setText("Letter Revealed (-5)");
    resultLabel.setForeground(Color.MAGENTA);

    updateUI();
    checkRoundEnd();
}




// Method to check if round ended (win/lose)
    void checkRoundEnd() {

    // ✅ Check WIN (no '_' left)
    boolean isComplete = true;
    for (char c : display) {
        if (c == '_') {
            isComplete = false;
            break;
        }
    }

    if (isComplete) {
        resultLabel.setText("Correct! Word: " + word);
        resultLabel.setForeground(new Color(0,128,0));
        endRound(true);
        return;
    }

    // ❌ Check LOSE (attempts over)
    if (wrongAttempts >= maxAttempts) {
        resultLabel.setText("Out of attempts! Word: " + word);
        resultLabel.setForeground(Color.RED);
        endRound(false);
    }
}


void endRound(boolean isWin) {

    int roundScore;

    if (isWin) {
        roundScore = Math.max(0, 20 - (wrongAttempts * 3) - (revealUsed * 5));
    } else {
        roundScore = 0;
    }

    totalScore += roundScore;

    resultLabel.setText("Round Score: " + roundScore);
    resultLabel.setForeground(Color.BLUE);

    // Disable buttons
    submitButton.setEnabled(false);
    revealButton.setEnabled(false);

    // Move to next round
    javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
        nextRound();
    });

    timer.setRepeats(false);
    timer.start();
}


void nextRound() {

    round++;
    resultLabel.setText("");

    inputField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

    // 🔚 Check game end
    if (round > 10) {
        showFinalResult();
        return;
    }

    // 🔄 Reset round variables
    wrongAttempts = 0;
    revealUsed = 0;
    guessedLetters.clear();

    // 🎲 Load new word
    word = getRandomWord().toUpperCase();
    display = word.toCharArray();

    // 🔁 Apply masking again
    applyMasking();

    // 🔓 Enable buttons
    submitButton.setEnabled(true);
    revealButton.setEnabled(true);

    // 🔄 Update UI
    updateUI();
}



void applyMasking() {

    double hidePercent;

    if (difficulty.equals("Easy")) {
        hidePercent = 0.3;
    } else if (difficulty.equals("Medium")) {
        hidePercent = 0.4;
    } else {
        hidePercent = 0.5;
    }

    int blanks = (int)(word.length() * hidePercent);

    if (blanks < 1) blanks = 1;
    if (blanks >= word.length() - 2) blanks = word.length() - 2;

    boolean[] hidden = new boolean[word.length()];

    // protect first & last
    hidden[0] = true;
    hidden[word.length() - 1] = true;

    int count = 0;

    while (count < blanks) {
        int index = (int)(Math.random() * word.length());

        if (!hidden[index]) {
            hidden[index] = true;
            display[index] = '_';
            count++;
        }
    }
}



void showFinalResult() {

    String result;
    

     boolean isNewHigh = totalScore > highScore;

    if (isNewHigh) {
        highScore = totalScore;
        highScoreLabel.setText("High Score: " + highScore);

        resultLabel.setText("🔥 New High Score!");
        resultLabel.setForeground(Color.ORANGE);
    }


    saveHighScore();


    if (totalScore >= 160) {
        result = "🏆 Excellent";
    } else if (totalScore >= 120) {
        result = "👍 Good";
    } else if (totalScore >= 80) {
        result = "🙂 Average";
    } else {
        result = "❌ Poor";
    }

   

   new WordGuessOvenrScreen(totalScore, highScore, difficulty);

    frame.dispose(); // close game
}


void saveHighScore() {
    try {
        int easy = 0, medium = 0, hard = 0;

        java.io.File file = new java.io.File("GTW_highscore.txt");

        // ✅ Read old scores
        if (file.exists()) {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file));

            easy = Integer.parseInt(br.readLine());
            medium = Integer.parseInt(br.readLine());
            hard = Integer.parseInt(br.readLine());

            br.close();
        }

        // ✅ Update only current difficulty
        if (difficulty.equals("Easy")) {
            easy = Math.max(totalScore, easy);
        } else if (difficulty.equals("Medium")) {
            medium = Math.max(totalScore, medium);
        } else {
            hard = Math.max(totalScore, hard);
        }

        // ✅ Write back updated values
        java.io.FileWriter fw = new java.io.FileWriter(file);

        fw.write(easy + "\n");
        fw.write(medium + "\n");
        fw.write(hard + "\n");

        fw.close();

    } catch (Exception e) {
        System.out.println("Error saving high score");
    }
}


void loadHighScore() {
    try {
        java.io.File file = new java.io.File("GTW_highscore.txt");

        if (!file.exists()) return;

        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file));

        int easy = Integer.parseInt(br.readLine());
        int medium = Integer.parseInt(br.readLine());
        int hard = Integer.parseInt(br.readLine());

        br.close();

        if (difficulty.equals("Easy")) highScore = easy;
        else if (difficulty.equals("Medium")) highScore = medium;
        else highScore = hard;

    } catch (Exception e) {
        System.out.println("Error loading high score");
    }
}

 
    public static void main(String[] args) {
        new WordGuessScreen("Medium");
    }
}