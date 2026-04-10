import javax.swing.*;
import java.awt.*;


public class WordJumbleGUI {

    int highScoreEasy = 0;
    int highScoreMedium = 0;
    int highScoreHard = 0;

    
    public WordJumbleGUI() {

        JFrame frame = new JFrame("Math challange");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        BackgroundPanel panel = new BackgroundPanel("src/Word Jumble.png");
        

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
       
       HTP.setBounds(700, 700, 150, 40);
         HTP.addActionListener(e -> {
            String rules = "Word Jumble Rules:\n\n"+
            
                    "• You start with 3 lives.\n" +
                    "• Guess the correct word from jumbled letters.\n\n" +
                    
                    "• Attempts per word:\n" +
                    "  - Easy/Medium: 2 attempts\n" +
                    "  - Hard: 3 attempts\n\n" +

                    "• If all attempts are used, you lose 1 life.\n\n" +

                    "• Skip:\n" +
                    "  - You can skip a word anytime (score penalty applies).\n\n" +

                    "• Hint System:\n" +
                    "  - You can use up to 2 hints per word\n" +
                    "  - Hint 1 reveals first letter\n" +
                    "  - Hint 2 reveals last letter\n\n" +

                    "• Try to guess in fewer attempts for a better score.\n" +
                    "• Game ends when all lives are lost.";

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
           new WordJumbleScreen("Easy");
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
           new WordJumbleScreen("Medium");
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
            new WordJumbleScreen("Hard2");
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






public static void main(String[] args) {
        new WordJumbleGUI();
}
}