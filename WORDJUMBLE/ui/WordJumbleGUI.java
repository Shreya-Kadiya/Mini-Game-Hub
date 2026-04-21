package WORDJUMBLE.ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import WORDJUMBLE.util.BackgroundPanel;
import WORDJUMBLE.util.RoundedButton;
public class WordJumbleGUI {

    // ================= HIGH SCORE STATE (ENCAPSULATION) =================
    int highScoreEasy = 0;
    int highScoreMedium = 0;
    int highScoreHard = 0;

    JFrame frame;
    BackgroundPanel panel;

    // =========================================================
    // CONSTRUCTOR (UI INITIALIZATION ONLY)
    // =========================================================
    public WordJumbleGUI() {

        frame = new JFrame("Word Jumble");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        panel = new BackgroundPanel("images/Word Jumble.png");
        panel.setLayout(null);

        loadHighScore(); // FILE IO (persistence layer)

        // ================= BACK BUTTON =================
        RoundedButton backButton = new RoundedButton(
                "Back",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186)
        );

        backButton.setBounds(20, 20, 100, 40);
        backButton.addActionListener(e -> {
            // new Dashboard();
            frame.dispose();
        });

        // ================= HOW TO PLAY =================
        RoundedButton HTP = new RoundedButton(
                "How to play",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186)
        );

        HTP.setBounds(700, 700, 150, 40);

        HTP.addActionListener(e -> showRules());

        // ================= TITLE =================
        JTextArea level = new JTextArea("Select Difficulty level");
        level.setBounds(600, 300, 500, 50);
        level.setFont(new Font("Arial", Font.BOLD, 35));
        level.setForeground(new Color(1, 27, 59));
        level.setOpaque(false);
        level.setEditable(false);
        level.setFocusable(false);

        // ================= EASY =================
        RoundedButton level_E = createLevelButton("Easy", 400, 400);
        JLabel HSE = createHighScoreLabel("High Score: " + highScoreEasy, 415, 450);

        level_E.addActionListener(e -> openGame("Easy"));

        // ================= MEDIUM =================
        RoundedButton level_M = createLevelButton("Medium", 700, 400);
        JLabel HSM = createHighScoreLabel("High Score: " + highScoreMedium, 715, 450);

        level_M.addActionListener(e -> openGame("Medium"));

        // ================= HARD =================
        RoundedButton level_H = createLevelButton("Hard", 1000, 400);
        JLabel HSH = createHighScoreLabel("High Score: " + highScoreHard, 1015, 450);

        level_H.addActionListener(e -> openGame("Hard"));

        // ================= ADD TO PANEL =================
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

    // =========================================================
    // OOP: FACTORY METHOD (BUTTON CREATION REUSE)
    // =========================================================
    private RoundedButton createLevelButton(String text, int x, int y) {
        RoundedButton btn = new RoundedButton(
                text,
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186)
        );
        btn.setBounds(x, y, 150, 40);
        return btn;
    }

    private JLabel createHighScoreLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 500, 50);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(new Color(1, 27, 59));
        return label;
    }

    // =========================================================
    // NAVIGATION LOGIC
    // =========================================================
    private void openGame(String difficulty) {
        new WordJumbleScreen(difficulty);
        frame.dispose();
    }

    // =========================================================
    // RULES (UI LOGIC SEPARATION)
    // =========================================================
    private void showRules() {

        String rules = "Word Jumble Rules:\n\n" +
                "- Unscramble the jumbled word\n" +
                "- Type answer → click Submit\n" +
                "- Correct = points\n" +
                "- Wrong = lose attempt/life\n" +
                "- Hint & Skip cost points\n" +
                "- Game ends if lives = 0\n" +
                "- Play all rounds, score high";

        JOptionPane.showMessageDialog(frame, rules, "How to Play", JOptionPane.INFORMATION_MESSAGE);
    }

    // =========================================================
    // FILE HANDLING (PERSISTENCE)
    // =========================================================
    void loadHighScore() {

        try {
            File file = new File("Word_Jumble/wordjumble_highscore.txt");
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

    // =========================================================
    public static void main(String[] args) {
        new WordJumbleGUI();
    }
}