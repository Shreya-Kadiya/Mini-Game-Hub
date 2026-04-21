package MATH;
import java.awt.*;
import javax.swing.*;
import MATH.service.FileHighScoreService;
import MATH.util.BackgroundPanel;
import MATH.util.RoundedButton;
import Home_Screen.Dashboard;

public class MathChallangeGUI {

    public MathChallangeGUI() {

        // ================= SERVICE (OOP) =================
        FileHighScoreService hs = new FileHighScoreService();

        int highScoreEasy = hs.getHighScore("Easy");
        int highScoreMedium = hs.getHighScore("Medium");
        int highScoreHard = hs.getHighScore("Hard");

        // ================= FRAME =================
        JFrame frame = new JFrame("Math Challenge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // ================= BACKGROUND (FIXED PATH) =================
        BackgroundPanel panel= new BackgroundPanel("images/MathSolve.png");
        panel.setLayout(null);

        // ================= BACK BUTTON =================
        RoundedButton backButton = new RoundedButton(
                "Back",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186)
        );

        backButton.setBounds(20, 20, 100, 40);

        backButton.addActionListener(e -> {
            new Dashboard();
            frame.dispose();
        });

        // ================= HOW TO PLAY =================
        RoundedButton HTP = new RoundedButton(
                "How to play",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186)
        );

        HTP.setBounds(700, 600, 150, 40);

        HTP.addActionListener(e -> {
            String rules =
                    "Math Challenge Rules:\n\n"
                  + "1. Solve as many questions as possible in given time.\n"
                  + "2. You get points for correct answers.\n"
                  + "3. Every 3 correct answers gives bonus points.\n"
                  + "4. Game ends if time is over.\n"
                  + "5. You have 5 lives, wrong answer = -1 life\n"
                  + "6. Game ends if 0 lives left\n"
                  + "7. Try to beat the high score!";

            JOptionPane.showMessageDialog(frame, rules);
        });

        // ================= TITLE =================
        JTextArea level = new JTextArea("Select Difficulty Level");
        level.setBounds(600, 250, 500, 50);
        level.setFont(new Font("Arial", Font.BOLD, 35));
        level.setForeground(new Color(1, 27, 59));
        level.setOpaque(false);
        level.setEditable(false);
        level.setFocusable(false);

        // ================= EASY =================
        RoundedButton level_E = new RoundedButton("Easy",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186));

        level_E.setBounds(400, 350, 150, 40);

        level_E.addActionListener(e -> {
            new MathGameScreen("Easy");
            frame.dispose();
        });

        JLabel HSE = new JLabel("High Score: " + highScoreEasy);
        HSE.setBounds(415, 400, 500, 50);
        HSE.setFont(new Font("Arial", Font.BOLD, 20));
        HSE.setForeground(new Color(1, 27, 59));

        // ================= MEDIUM =================
        RoundedButton level_M = new RoundedButton("Medium",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186));

        level_M.setBounds(700, 350, 150, 40);

        level_M.addActionListener(e -> {
            new MathGameScreen("Medium");
            frame.dispose();
        });

        JLabel HSM = new JLabel("High Score: " + highScoreMedium);
        HSM.setBounds(715, 400, 500, 50);
        HSM.setFont(new Font("Arial", Font.BOLD, 20));
        HSM.setForeground(new Color(1, 27, 59));

        // ================= HARD =================
        RoundedButton level_H = new RoundedButton("Hard",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186));

        level_H.setBounds(1000, 350, 150, 40);

        level_H.addActionListener(e -> {
            new MathGameScreen("Hard");
            frame.dispose();
        });

        JLabel HSH = new JLabel("High Score: " + highScoreHard);
        HSH.setBounds(1015, 400, 500, 50);
        HSH.setFont(new Font("Arial", Font.BOLD, 20));
        HSH.setForeground(new Color(1, 27, 59));

        // ================= ADD =================
        panel.add(backButton);
        panel.add(HTP);
        panel.add(level);

        panel.add(level_E);
        panel.add(HSE);

        panel.add(level_M);
        panel.add(HSM);

        panel.add(level_H);
        panel.add(HSH);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MathChallangeGUI();
    }
}