package GuessTheWord;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import GuessTheWord.util.BackgroundPanel;
import GuessTheWord.util.RoundedButton;
import Dashboard.Dashboard;



public class WordGuessGUI {

    int highScoreEasy = 0;
    int highScoreMedium = 0;
    int highScoreHard = 0;

    
    public WordGuessGUI() {

        JFrame frame = new JFrame("Guess the word");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        BackgroundPanel panel = new BackgroundPanel("images/GTW.jpg");

        loadHighScore();
        

        //back button
       RoundedButton backButton = new RoundedButton("Back",
                    new Color(5, 72, 149), // normal
                    new Color(26, 3, 85),  // hover
                    new Color(48, 14, 186) // pressed   
                );
       
       backButton.setBounds(20, 20, 100, 40);
         backButton.addActionListener(e -> {
              new Dashboard(); 
              frame.dispose();
        });     
    

        
       //How to play button
       RoundedButton HTP = new RoundedButton("How to play",
                    new Color(5, 72, 149), // normal
                    new Color(26, 3, 85),  // hover
                    new Color(48, 14, 186) // pressed   
                );
       
       HTP.setBounds(700, 650, 150, 40);
         HTP.addActionListener(e -> {
           String rules="HOW TO PLAY:\n" + //
                        "1. Guess one letter at a time.\\n\"\n" + //
                        "2. If the letter is correct, it will be revealed in the word.\n" + //
                        "3. If the letter is wrong, score will be reduced.\n" + //
                        "4. You have limited wrong attempts per word.\n" + //
                        "5. Use the Reveal button to uncover a letter (-5 points).\n" + //
                        "6. Reveal is allowed only if score is at least 5.\n" + //
                        "7. Each game has 10 rounds (10 words).\n" + //
                        "8. Score per word is based on your mistakes and reveals.\n" + //
                        "9. Final score after 10 rounds decides your performance.\n";
        JOptionPane.showMessageDialog(frame, rules, "How to Play", JOptionPane.INFORMATION_MESSAGE);
        }); 

            
        
        // Difficulty level text
        JTextArea level=new JTextArea();
        level.setText("Select Difficulty level");
        level.setBounds(600, 300, 500, 50);
        level.setFont(new Font("Arial", Font.BOLD, 35));
        level.setForeground(new Color(1,27,59));
        level.setOpaque(false);
        level.setEditable(false);
        level.setFocusable(false);

        RoundedButton level_E = new RoundedButton("Easy",
                    new Color(5, 72, 149), // normal
                    new Color(26, 3, 85),  // hover
                    new Color(48, 14, 186) // pressed   
        );
        level_E.setBounds(400, 400, 150, 40);
        level_E.addActionListener(e -> {
           new WordGuessScreen("Easy");
           frame.dispose();            
            
        });
         //High score for easy level
        JLabel HSE = new JLabel("High Score" + highScoreEasy);
        HSE.setText("High Score: "+ highScoreEasy);
        HSE.setBounds(415, 450, 500, 50);
        HSE.setFont(new Font("Arial", Font.BOLD, 20));
        HSE.setForeground(new Color(1,27,59));
        HSE.setOpaque(false);
        HSE.setFocusable(false);

        
        // Medium level button
        RoundedButton level_M = new RoundedButton("Medium",
            new Color(5, 72, 149), // normal
            new Color(26, 3, 85),  // hover
            new Color(48, 14, 186) // pressed   
        );
        level_M.setBounds(700, 400, 150, 40);
        level_M.addActionListener(e -> {
           new WordGuessScreen("Medium");
           frame.dispose();
            
        });

        //High score for medium level
        JLabel HSM = new JLabel("High Score" + highScoreMedium);
        HSM.setText("High Score: "+ highScoreMedium);
        HSM.setBounds(715, 450, 500, 50);
        HSM.setFont(new Font("Arial", Font.BOLD, 20));
        HSM.setForeground(new Color(1,27,59));
        HSM.setOpaque(false);
        HSM.setFocusable(false);


        // Hard level button
        RoundedButton level_H = new RoundedButton("Hard",
            new Color(5, 72, 149), // normal
            new Color(26, 3, 85),  // hover
            new Color(48, 14, 186) // pressed   
        );
        level_H.setBounds(1000, 400, 150, 40); 
        level_H.addActionListener(e -> {    
            new WordGuessScreen("Hard");
           frame.dispose();      
        });       

        
        //High score for hard level
        JLabel HSH = new JLabel("High Score " + highScoreHard);
        HSH.setText("High Score: "+ highScoreHard);
        HSH.setBounds(1015, 450, 500, 50);
        HSH.setFont(new Font("Arial", Font.BOLD, 20));
        HSH.setForeground(new Color(1,27,59));
        HSH.setOpaque(false);
        HSH.setFocusable(false);


        
    
        frame.setContentPane(panel);
        panel.setLayout(null);
        panel.add(backButton);
        panel.add(HTP);
        panel.add(level);
        panel.add(level_E);
        panel.add(HSE);
        panel.add(level_M);
        panel.add(HSM);
        panel.add(level_H);
        panel.add(HSH);
        frame.setVisible(true);
       
    }


    void loadHighScore() {
    try {
        File file = new File("Guess_The_Word/GTW_highscore.txt");
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



public static void main(String[] args) {
        new WordGuessGUI();
}
}