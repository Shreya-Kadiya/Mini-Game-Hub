import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class WordDecodeGUI {
    
    public WordDecodeGUI() {
        JFrame frame = new JFrame("Word Decode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //back button
       RoundedButton backButton = new RoundedButton("Back",
                    new Color(5, 72, 149), // normal
                    new Color(26, 3, 85),  // hover
                    new Color(48, 14, 186) // pressed   
                );
       backButton.setBounds(20, 20, 100, 40);
         backButton.addActionListener(e -> {
              frame.dispose(); // Close Sudoku window
              new Dashboard(); // Open Dashboard
    });


      BackgroundPanel panel = new BackgroundPanel("src/WordDecode.png");
      frame.setContentPane(panel);


        // Rules
         JTextArea rulesArea = new JTextArea(
        "                     Rules:\n\n" +
            "• A word will be shown with some missing letters\n" +
            "• Example: A_p_e\n" +
            "• Guess the complete word correctly\n" +
            "• Type your answer in the input box\n" +
            "• Click Check to verify your answer\n" +
            "• Each correct answer gives +1 point\n" +
            "• No negative marking\n" +
            "• Try to guess as many words as possible\n\n" +
            "  Think carefully and complete the word!"
            );


        rulesArea.setEditable(false);
        rulesArea.setOpaque(false);
        rulesArea.setFont(new Font("Arial", Font.BOLD,  25));
        rulesArea.setForeground(new Color(1,27,59));

        rulesArea.setBounds(550, 300, 700, 400);

        //start button
        RoundedButton startButton = new RoundedButton("START",
                    new Color(5, 72, 149), // normal
                    new Color(26, 3, 85),  // hover
                    new Color(48, 14, 186) // pressed   
                );        
        startButton.setBounds(700, 700, 150, 50);
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
        new WordDecodeGUI();
}
}