import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;

public class MathChallangeGUI {
    
    public MathChallangeGUI() {
        JFrame frame = new JFrame("Math challange");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

         BackgroundPanel panel = new BackgroundPanel("src/MathSolve.png");
        frame.setContentPane(panel);

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



        // Rules
         JTextArea rulesArea = new JTextArea(
            "                          Rules\n\n" +
            "• You have 60 seconds to solve questions\n" +
            "• Answer as many questions as you can\n" +
            "• Each correct answer gives +1 point\n" +
            "• No negative marking\n" +
            "• Enter your answer and click Submit\n" +
            "• Next question appears instantly\n" +
            "• Game ends when time is over\n\n" +
            "    Try to score as high as possible!"
        );


        rulesArea.setEditable(false);
        rulesArea.setOpaque(false);
        rulesArea.setFont(new Font("Arial", Font.BOLD,  25));
        rulesArea.setForeground(new Color(73,110,6));

        rulesArea.setBounds(550, 300, 700, 400);
       

        //start button
        RoundedButton startButton = new RoundedButton("START",
                    new Color(5, 72, 149), // normal
                    new Color(26, 3, 85),  // hover
                    new Color(48, 14, 186) // pressed   
                );
        startButton.setBounds(700, 650, 150, 50);
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
        new MathChallangeGUI();
}
}