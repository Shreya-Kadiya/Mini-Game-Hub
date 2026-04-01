import javax.swing.*;
import java.awt.*;

public class MathGameScreen {

    JFrame frame;
    BackgroundPanel panel;

    public MathGameScreen(String difficulty) {

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


        JLabel scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(500, 60, 200, 30);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel streakLabel = new JLabel("Streak: 0");
        streakLabel.setBounds(650, 60, 200, 30);
        streakLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel wrongLabel = new JLabel("Wrong: 0");
        wrongLabel.setBounds(800, 60, 200, 30);
        wrongLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel timerLabel = new JLabel("Time: 30");
        timerLabel.setBounds(950, 60, 200, 30);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));


        JLabel questionLabel = new JLabel("5 + 3 = ?");
        questionLabel.setBounds(600, 250, 500, 50);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 40));
        questionLabel.setForeground(new Color(1,27,59));

        JTextField answerField = new JTextField();
        answerField.setBounds(600, 350, 200, 40);
        answerField.setFont(new Font("Arial", Font.BOLD, 20));

        RoundedButton submitBtn = new RoundedButton("Submit",
        new Color(5,72,149),
        new Color(26,3,85),
        new Color(48,14,186));

        submitBtn.setBounds(620, 450, 150, 40);


        JLabel resultLabel = new JLabel("");
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
    }

    public static void main(String[] args) {
        new MathGameScreen("Easy");
    }
}