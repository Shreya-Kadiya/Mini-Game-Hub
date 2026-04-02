import javax.swing.*;
import java.awt.*;  
import java.util.Random;



public class MathGameScreen {

  
    JFrame frame;
    BackgroundPanel panel;

    String difficulty;
    int correctAnswer;

    JLabel scoreLabel, streakLabel, wrongLabel, questionLabel, resultLabel;
    JTextField answerField;

    int score = 0;
    int streak = 0;
    int wrong = 0;

    public MathGameScreen(String difficulty) {
        this.difficulty = difficulty;


        scoreLabel = new JLabel("Score: 0");
        streakLabel = new JLabel("Streak: 0");
        wrongLabel = new JLabel("Wrong: 0");
        
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
            frame.dispose();
            new MathChallangeGUI(); // go back to difficulty screen
        });


        

        //lables
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(500, 60, 200, 30);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));

        streakLabel = new JLabel("Streak: 0");
        streakLabel.setBounds(650, 60, 200, 30);
        streakLabel.setFont(new Font("Arial", Font.BOLD, 20));

        wrongLabel = new JLabel("Wrong: 0");
        wrongLabel.setBounds(800, 60, 200, 30);
        wrongLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel timerLabel = new JLabel("Time: 30");
        timerLabel.setBounds(950, 60, 200, 30);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));

       //Question label
        questionLabel = new JLabel("");
        questionLabel.setBounds(600, 250, 500, 50);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 40));
        questionLabel.setForeground(new Color(1,27,59));

        answerField = new JTextField();
        answerField.setBounds(600, 350, 200, 40);
        answerField.setFont(new Font("Arial", Font.BOLD, 20));

        RoundedButton submitBtn = new RoundedButton("Submit",
        new Color(5,72,149),
        new Color(26,3,85),
        new Color(48,14,186));

        submitBtn.setBounds(620, 450, 150, 40);
        submitBtn.addActionListener(e -> checkAnswer());


        resultLabel = new JLabel("");
        resultLabel.setBounds(650, 480, 400, 40);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(resultLabel);
        panel.add(submitBtn);
        panel.add(answerField);
        panel.add(questionLabel);
        panel.add(scoreLabel);
        panel.add(streakLabel);
        panel.add(wrongLabel);
        panel.add(timerLabel);
        panel.add(backBtn);
        frame.setContentPane(panel);
        frame.setVisible(true);

        generateQuestion();
    }


    // ✅ Proper method (OUTSIDE constructor)
    private void generateQuestion() {
        Random rand = new Random();

        int num1 = 0, num2 = 0, num3 = 0;
        char op1 = '+', op2 = '+';

        if (difficulty.equals("Easy")) {

            num1 = rand.nextInt(15) + 1;
            num2 = rand.nextInt(15) + 1;
            op1 = rand.nextBoolean() ? '+' : '-';

            if (op1 == '-' && num2 > num1) {
                int temp = num1;
                num1 = num2;
                num2 = temp;
            }

            correctAnswer = (op1 == '+') ? num1 + num2 : num1 - num2;

            questionLabel.setText(num1 + " " + op1 + " " + num2 + " = ?");
        }

        else if (difficulty.equals("Medium")) {

            num1 = rand.nextInt(25) + 1;
            num2 = rand.nextInt(25) + 1;
            num3 = rand.nextInt(25) + 1;

            op1 = getOperator(rand);
            op2 = getOperator(rand);

            int temp = calculate(num1, num2, op1);
            correctAnswer = calculate(temp, num3, op2);

            questionLabel.setText(num1 + " " + op1 + " " + num2 + " " + op2 + " " + num3 + " = ?");
        }

        else if (difficulty.equals("Hard")) {

            num1 = rand.nextInt(50) + 1;
            num2 = rand.nextInt(50) + 1;
            num3 = rand.nextInt(50) + 1;

            correctAnswer = (num1 + num2) * num3;

            questionLabel.setText( + num1 + " + " + num2 + "× " + num3 + " = ?");
        }
    }

    private char getOperator(Random rand) {
        int op = rand.nextInt(3);
        if (op == 0) return '+';
        if (op == 1) return '-';
        return '*';
    }

    private int calculate(int a, int b, char op) {
        if (op == '+') return a + b;
        if (op == '-') return a - b;
        return a * b;
    }



    //Answer checking logic
    private void checkAnswer() {


        String input = answerField.getText();

        // empty input check
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
            resultLabel.setText("Correct ✅");
            resultLabel.setForeground(Color.GREEN);

            score++;
            streak++;

            //  bonus every 3 streak
            if (streak % 3 == 0) {
                score += 2;
                resultLabel.setText("Bonus! 🔥");
            }

        } else {
            resultLabel.setText("Wrong ❌ Correct: " + correctAnswer);
            resultLabel.setForeground(Color.RED);

            wrong++;
            streak = 0;
        }

        // update UI
        scoreLabel.setText("Score: " + score);
        streakLabel.setText("Streak: " + streak);
        wrongLabel.setText("Wrong: " + wrong);

        answerField.setText("");

        // ❌ game over condition
        if (wrong >= 5) {
            endGame();
            return;
        }

        // ⏱️ next question after delay
        Timer delay = new Timer(800, e -> generateQuestion());
        delay.setRepeats(false);
        delay.start();
}



private void endGame() {
    JOptionPane.showMessageDialog(frame,
            "Game Over!\nScore: " + score,
            "Result",
            JOptionPane.INFORMATION_MESSAGE);

    frame.dispose();
    new MathChallangeGUI();
}

    public static void main(String[] args) {
        new MathGameScreen("Easy");
    }
}