import javax.swing.*;
import java.awt.*;
import java.io.*;


public class SudokuGUI {

    int highScoreEasy = 0;
    int highScoreMedium = 0;
    int highScoreHard = 0;

    
    public SudokuGUI() {

        File file = new File(System.getProperty("user.dir"), "Sudoku/Sudoku_highscore.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write("0\n0\n0\n"); 
                bw.close();
            } catch (IOException e) {
                System.out.println("Error initializing high score file");
            }
        }
        loadHighScores();


        JFrame frame = new JFrame("Math challange");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        BackgroundPanel panel = new BackgroundPanel("src/Sudoku.png");
        

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
       
       HTP.setBounds(700, 600, 150, 40);
         HTP.addActionListener(e -> {
        String rules = """
            HOW TO PLAY (4x4 Sudoku)

            • Fill the 4×4 grid so each row, column, and 2×2 box contains numbers 1 to 4 without repetition.
            • Pre-filled numbers cannot be changed.
            • Enter only digits 1–4 in empty cells.
            • Use CHECK to verify your current entries. Wrong cells will be highlighted.
            • You get 3 chances — after 3 mistakes, the game ends.
            • Use HINT (max 3) to reveal a correct cell.
            • Use SOLVE to auto-complete the puzzle (but it affects your score).
            • Complete the puzzle correctly to finish the game and get your score.
            """;;

        JOptionPane.showMessageDialog(frame, rules, "How to Play", JOptionPane.INFORMATION_MESSAGE);
        }); 

            

        // Difficulty level text
        JTextArea level=new JTextArea();
        level.setText("Select Difficulty level");
        level.setBounds(600, 250, 500, 50);
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
        level_E.setBounds(400, 350, 150, 40);
        level_E.addActionListener(e -> {
            new SudokuScreen("Easy");
            frame.dispose();
        });

    
         //High score for easy level
        JLabel HSE = new JLabel("High Score" + highScoreEasy);
        HSE.setText("High Score: "+ highScoreEasy);
        HSE.setBounds(415, 400, 500, 50);
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
        level_M.setBounds(700, 350, 150, 40);
        level_M.addActionListener(e -> {
             new SudokuScreen("Medium");
            frame.dispose();
        });

        //High score for medium level
        JLabel HSM = new JLabel("High Score" + highScoreMedium);
        HSM.setText("High Score: "+ highScoreMedium);
        HSM.setBounds(715, 400, 500, 50);
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
        level_H.setBounds(1000, 350, 150, 40); 
        level_H.addActionListener(e -> {
             new SudokuScreen("Hard");
            frame.dispose();
        });       

        
        //High score for hard level
        JLabel HSH = new JLabel("High Score " + highScoreHard);
        HSH.setText("High Score: "+ highScoreHard);
        HSH.setBounds(1015, 400, 500, 50);
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


    private void loadHighScores() {
    try {
        File file = new File(System.getProperty("user.dir"), "Sudoku/Sudoku_highscore.txt");
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
        new SudokuGUI();
}
}
