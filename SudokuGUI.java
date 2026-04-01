
import javax.swing.*;
import java.awt.*;

public class SudokuGUI {


    public SudokuGUI() {


        
        JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        BackgroundPanel panel = new BackgroundPanel("src/Sudoku.png");
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
   

       
        
        JTextArea rulesArea = new JTextArea(
            "                    Rules:\n\n" +
            "• Fill the 4x4 grid with numbers 1 to 4\n" +
            "• Each number must appear only once \n in every row\n" +
            "• Each number must appear only once \n in every column\n" +
            "• Each 2x2 box must contain numbers \n 1 to 4 without repetition\n" +
            "• Do not change the pre-filled numbers\n" +
            "• Complete the grid correctly to win\n\n" +
            "       Use logic, not guessing!"
        );

        rulesArea.setEditable(false);
        rulesArea.setOpaque(false);
        rulesArea.setFont(new Font("Arial BOLD", Font.PLAIN, 20));
        rulesArea.setForeground(new Color(128,11,63));
        rulesArea.setBounds(1050, 200, 750, 800);
       
        

        // Add your gridPanel on top (higher layer)
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 4));
        gridPanel.setBounds(500, 170, 500, 500);
        gridPanel.setOpaque(true);
        gridPanel.setBackground(new Color(250, 250, 250));
        gridPanel.setBorder(BorderFactory.createLineBorder(new Color(18, 4, 85), 4));
       
        JTextField[][] cells = new JTextField[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                final int row = i;
                final int col = j;
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setOpaque(true);
                cell.setBackground(new Color(251,236,253));//color of cell background
                cell.setFont(new Font("Arial", Font.BOLD, 50));
                cell.setBorder(BorderFactory.createLineBorder(new Color(36,24,93), 2));//color of cell border
                if ((i / 2 + j / 2) % 2 == 0) {
                    cell.setBackground(new Color(246,207,179)); // light variation
                } 
                else {
                    cell.setBackground(new Color(247,181,134)); // dark variation
                }


                cell.addFocusListener(new java.awt.event.FocusAdapter() {
                    public void focusGained(java.awt.event.FocusEvent e) {
                        cell.setBackground(new Color(251,236,225)); // highlight
                    }

                    public void focusLost(java.awt.event.FocusEvent e) {
                        if ((row/ 2 + col/ 2) % 2 == 0) {
                            cell.setBackground(new Color(246,207,179));
                        } else {
                            cell.setBackground(new Color(247,181,134));
                        }
                    }
                }
            );
                
                cells[i][j] = cell;
                gridPanel.add(cell);
            }
        }



        //check button
        RoundedButton checkButton = new RoundedButton("CHECK",
                   new Color(5, 72, 149), // normal
                    new Color(26, 3, 85),  // hover
                    new Color(48, 14, 186) // pressed 
        );
        checkButton.setBounds(850, 700, 150, 40);
        checkButton.addActionListener(e -> {
            //check logic
         });
         //reset button
        RoundedButton resetButton = new RoundedButton("RESET",
                    new Color(5, 72, 149), // normal
                    new Color(26, 3, 85),  // hover
                    new Color(48, 14, 186) // pressed   
                );
        resetButton.setBounds(500, 700, 150, 40);
         resetButton.addActionListener(e -> {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    cells[i][j].setText("");
                }
            }
            });



      
         frame.add(backButton);
        frame.add(checkButton);
        frame.add(resetButton);
        frame.add(rulesArea);
        frame.add(gridPanel);

        frame.setContentPane(panel);
        frame.setVisible(true);


    }
    

    public static void main(String[] args) {
        new SudokuGUI();
    }
}
