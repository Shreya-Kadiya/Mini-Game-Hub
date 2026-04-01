import javax.swing.*;
import java.awt.*;

public class WordJumbleGUI {
    
    public WordJumbleGUI() {
        JFrame frame = new JFrame("Word Jumble");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);


        BackgroundPanel panel = new BackgroundPanel("src/WordJumble.png");
        frame.setContentPane(panel);



        //back button
       RoundedButton backButton = new RoundedButton("Back",
                    new Color(5, 72, 149), // normal
                    new Color(26, 3, 85),  // hover
                    new Color(48, 14, 186) // pressed   
                );
       backButton.setBounds(20, 20, 100, 40);
         backButton.addActionListener(e -> {
              new Dashboard(); // Open Dashboard
              frame.dispose(); 
        });
        

        // Rules
         JTextArea rulesArea = new JTextArea(
            "                    Rules:\n\n" +
            "• A jumbled (shuffled) word will be shown\n" +
            "• Unscramble the letters to form a correct word\n" +
            "• Type your answer in the input box\n" +
            "• Click Submit to check your answer\n" +
            "• Each correct answer gives +1 point\n" +
            "• No negative marking\n" +
            "• Try to guess as many words as possible\n\n" +
            "   Think fast and arrange wisely!"
            );


        rulesArea.setEditable(false);
        rulesArea.setOpaque(false);
        rulesArea.setFont(new Font("Arial", Font.BOLD,  25));
        rulesArea.setForeground(new Color(1,27,59));

        rulesArea.setBounds(550, 300, 700, 400);
        

        //start button
        RoundedButton startButton = new RoundedButton("START");
        startButton.setBounds(650, 650, 150, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.addActionListener(e -> {
            // Start game logic here
        });
            

        frame.add(backButton);
        frame.add(startButton);
        frame.add(rulesArea);
        frame.setContentPane(panel);
        frame.setVisible(true);
       
    }

    public static void main(String[] args) {
        new WordJumbleGUI();
}
}