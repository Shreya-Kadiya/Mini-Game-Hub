// import javax.swing.*;
// import java.awt.*;

// public class SudokuScreen{


//     int[][] solutionBoard = new int[4][4];
//     int[][] puzzleBoard = new int[4][4];
//     boolean[][] isFixed = new boolean[4][4];
//     JTextField[][] cells = new JTextField[4][4];
    

//     // Game tracking
//     int mistakes = 0;
//     int hintsUsed = 0;
//     int timeElapsed = 0;
//     boolean isGridUpdated = false;
//     boolean solveUsed = false;
//     JFrame frame;
//     int[][] initialPuzzleBoard = new int[4][4];

//     javax.swing.Timer gameTimer;
//     int seconds = 0;
//     JLabel timerLabel;
    


//     public SudokuScreen(String difficulty) {
    
//         generateSudoku(difficulty);


                
//         frame = new JFrame("Sudoku");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

//         BackgroundPanel panel = new BackgroundPanel("src/Sudoku.png");
//         frame.setContentPane(panel);

//         //back button
//         RoundedButton backButton = new RoundedButton("Back",
//                     new Color(5, 72, 149), // normal
//                     new Color(26, 3, 85),  // hover
//                     new Color(48, 14, 186) // pressed   
//                 );
//         backButton.setBounds(20, 20, 100, 40);
//         backButton.addActionListener(e -> {
//               new Dashboard(); // Open Dashboard
//               frame.dispose(); 
//         });
        

        

//         // Add your gridPanel on top (higher layer)
//         JPanel gridPanel = new JPanel();
//         gridPanel.setLayout(new GridLayout(4, 4));
//         gridPanel.setBounds(500, 150, 500, 500);
//         gridPanel.setOpaque(true);
//         gridPanel.setBackground(new Color(250, 250, 250));
//         gridPanel.setBorder(BorderFactory.createLineBorder(new Color(18, 4, 85), 4));
       
       
//         for (int i = 0; i < 4; i++) {
//             for (int j = 0; j < 4; j++) {



//                 final int row = i;
//                 final int col = j;
//                 JTextField cell = new JTextField();
//                 cell.setHorizontalAlignment(JTextField.CENTER);
//                 cell.setOpaque(true);
//                 cell.setBackground(new Color(251,236,253));//color of cell background
//                 cell.setFont(new Font("Arial", Font.BOLD, 50));
//                 cell.setBorder(BorderFactory.createLineBorder(new Color(36,24,93), 2));//color of cell border
//                 if ((i / 2 + j / 2) % 2 == 0) {
//                     cell.setBackground(new Color(246,207,179)); // light variation
//                 } 
//                 else {
//                     cell.setBackground(new Color(247,181,134)); // dark variation
//                 }


//                 cell.setDocument(new javax.swing.text.PlainDocument() {
//                     @Override
//                     public void insertString(int offset, String str, javax.swing.text.AttributeSet attr)
//                             throws javax.swing.text.BadLocationException {

//                         if (str == null) return;

//                         if ((getLength() + str.length()) <= 1 && str.matches("[1-4]")) {
//                             super.insertString(offset, str, attr);
//                         }
//                     }
//                 });


//                 cell.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

//                         public void insertUpdate(javax.swing.event.DocumentEvent e) {
//                             update();
//                         }

//                         public void removeUpdate(javax.swing.event.DocumentEvent e) {
//                             update();
//                         }

//                         public void changedUpdate(javax.swing.event.DocumentEvent e) {
//                             update();
//                         }

//                         void update() {

//                             if(isFixed[row][col]) return;

//                             String text = cell.getText();

//                             if(text.isEmpty()) {
//                                 puzzleBoard[row][col] = 0;
//                             } else {
//                                 puzzleBoard[row][col] = Integer.parseInt(text);
//                             }

//                             isGridUpdated = true;
//                         }
//                     });


//                 cell.addFocusListener(new java.awt.event.FocusAdapter() {
//                     public void focusGained(java.awt.event.FocusEvent e) {
//                         cell.setBackground(new Color(251,236,225)); // highlight
//                     }

//                     public void focusLost(java.awt.event.FocusEvent e) {
//                         if ((row/ 2 + col/ 2) % 2 == 0) {
//                             cell.setBackground(new Color(246,207,179));
//                         } else {
//                             cell.setBackground(new Color(247,181,134));
//                         }
//                     }
//                 }
//             );
                
//                 cells[i][j] = cell;
//                 gridPanel.add(cell);
//             }
//         }



//         loadPuzzleToUI();// Load the puzzle in UI
//         isGridUpdated = false;

//         timerLabel = new JLabel("Time: 0s");
//         timerLabel.setBounds(900, 20, 200, 40);
//         timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
//         timerLabel.setForeground(new Color(18, 4, 85)); 

//         //check button
//         RoundedButton checkButton = new RoundedButton("CHECK",
//                    new Color(5, 72, 149), // normal
//                     new Color(26, 3, 85),  // hover
//                     new Color(48, 14, 186) // pressed 
//         );
//         checkButton.setBounds(500, 700, 100, 40);
//         checkButton.addActionListener(e -> {
//             handleCheck();
//          });

//          //reset button
//         RoundedButton resetButton = new RoundedButton("RESET",
//                     new Color(5, 72, 149), // normal
//                     new Color(26, 3, 85),  // hover
//                     new Color(48, 14, 186) // pressed   
//                 );
//         resetButton.setBounds(640, 700, 100, 40);
//         resetButton.addActionListener(e -> handleReset());


//          //Hint button
//         RoundedButton HintButton = new RoundedButton("HINT",
//                     new Color(5, 72, 149), // normal
//                     new Color(26, 3, 85),  // hover
//                     new Color(48, 14, 186) // pressed   
//                 );
//         HintButton.setBounds(780, 700, 100, 40);
//          HintButton.addActionListener(e -> {
//            handleHint();
//          });
            

//         //Solve button
//         RoundedButton SolveButton = new RoundedButton("SOLVE",
//                     new Color(5, 72, 149), // normal
//                     new Color(26, 3, 85),  // hover
//                     new Color(48, 14, 186) // pressed   
//                 );
//         SolveButton.setBounds(920, 700, 100, 40);
//         SolveButton.addActionListener(e -> {
//             handleSolve();
//          });

         

//         frame.add(timerLabel);
//         frame.add(backButton);
//         frame.add(checkButton);
//         frame.add(resetButton);
//         frame.add(gridPanel);
//         frame.add(HintButton);
//         frame.add(SolveButton);
//         frame.setContentPane(panel);
//         frame.setVisible(true);

//         startTimer();

//     }

    


//     // Generate the Sudoku puzzle based on difficulty
//     void generateSudoku(String difficulty) 
//     {
//     generateBaseSolution();
//     shuffleNumbers();
//     shuffleRows();
//     shuffleCols();
//     copyToPuzzle();
//     removeCells(difficulty);
//     copyToInitialPuzzle();
//     }




//     // generate a base solution for 4x4 Sudoku
//     void generateBaseSolution() {
//     int[][] base = {
//         {1,2,3,4},
//         {3,4,1,2},
//         {2,1,4,3},
//         {4,3,2,1}
//     };

//     for(int i=0;i<4;i++){
//         for(int j=0;j<4;j++){
//             solutionBoard[i][j] = base[i][j];
//         }
//         }
    
//     }



//     // Shuffle 
//     void shuffleNumbers() {
//     java.util.List<Integer> nums = java.util.Arrays.asList(1,2,3,4);
//     java.util.Collections.shuffle(nums);

//     for(int i=0;i<4;i++){
//         for(int j=0;j<4;j++){
//             solutionBoard[i][j] = nums.get(solutionBoard[i][j] - 1);
//         }
//         }

//     }

//     void copyToInitialPuzzle() {
//         for(int i=0;i<4;i++){
//             for(int j=0;j<4;j++){
//                 initialPuzzleBoard[i][j] = puzzleBoard[i][j];
//             }
//         }
//     }



//     void shuffleRows() {
//     java.util.Random rand = new java.util.Random();

//     for(int block=0; block<2; block++){
//         int r1 = block*2 + rand.nextInt(2);
//         int r2 = block*2 + rand.nextInt(2);

//         int[] temp = solutionBoard[r1];
//         solutionBoard[r1] = solutionBoard[r2];
//         solutionBoard[r2] = temp;
//         }

//     }


//     void shuffleCols() {
//     java.util.Random rand = new java.util.Random();

//     for(int block=0; block<2; block++){
//         int c1 = block*2 + rand.nextInt(2);
//         int c2 = block*2 + rand.nextInt(2);

//         for(int i=0;i<4;i++){
//             int temp = solutionBoard[i][c1];
//             solutionBoard[i][c1] = solutionBoard[i][c2];
//             solutionBoard[i][c2] = temp;
//             }
//         }

//     }



//     void copyToPuzzle() {
//     for(int i=0;i<4;i++){
//         for(int j=0;j<4;j++){
//             puzzleBoard[i][j] = solutionBoard[i][j];
//         }
//         }   
//     }



//     void removeCells(String difficulty) {

//         int removePerBox;

//         if(difficulty.equals("Easy")) removePerBox = 1;
//         else if(difficulty.equals("Medium")) removePerBox = 2;
//         else removePerBox = 3;

//         java.util.Random rand = new java.util.Random();

//         // loop through each 2x2 box
//         for(int boxRow = 0; boxRow < 4; boxRow += 2){
//             for(int boxCol = 0; boxCol < 4; boxCol += 2){

//                 int count = removePerBox;

//                 while(count > 0){
//                     int i = boxRow + rand.nextInt(2);
//                     int j = boxCol + rand.nextInt(2);

//                     if(puzzleBoard[i][j] != 0){
//                         puzzleBoard[i][j] = 0;
//                         count--;
//                     }
//                 }
//             }
//         }
//     }


// // Load the generated puzzle into the UI
//     void loadPuzzleToUI() {
//         for(int i=0;i<4;i++){
//             for(int j=0;j<4;j++){

//                 if(puzzleBoard[i][j] != 0){
//                     cells[i][j].setText(String.valueOf(puzzleBoard[i][j]));
//                     cells[i][j].setEditable(false);
//                     isFixed[i][j] = true;

//                     cells[i][j].setForeground(new Color(90, 70, 120)); // color for fixed numbers
//                     } 
//                 else {
//                     cells[i][j].setText("");
//                     cells[i][j].setEditable(true);
//                     isFixed[i][j] = false;

//                     cells[i][j].setForeground(new Color(30, 30, 50));
            

                    
//                 }
//             }
//             }
//         }



//     boolean isRowValid(int row) {
//         boolean[] seen = new boolean[5]; // index 1–4

//         for(int j=0;j<4;j++){
//             int val = puzzleBoard[row][j];

//             if(val == 0 || seen[val]) return false;

//             seen[val] = true;
//         }

//         return true;
//     }


//     boolean isColValid(int col) {
//         boolean[] seen = new boolean[5];

//         for(int i=0;i<4;i++){
//             int val = puzzleBoard[i][col];

//             if(val == 0 || seen[val]) return false;

//             seen[val] = true;
//         }

//         return true;
// }



//     boolean isBoxValid(int startRow, int startCol) {
//         boolean[] seen = new boolean[5];

//         for(int i=0;i<2;i++){
//             for(int j=0;j<2;j++){
//                 int val = puzzleBoard[startRow + i][startCol + j];

//                 if(val == 0 || seen[val]) return false;

//                 seen[val] = true;
//             }
//         }

//         return true;
//     }


//     boolean isBoardCorrect() {

//         // check rows
//         for(int i=0;i<4;i++){
//             if(!isRowValid(i)) return false;
//         }

//         // check columns
//         for(int j=0;j<4;j++){
//             if(!isColValid(j)) return false;
//         }

//         // check boxes
//         for(int i=0;i<4;i+=2){
//             for(int j=0;j<4;j+=2){
//                 if(!isBoxValid(i,j)) return false;
//             }
//         }

//         return true;
//     }




//     //check
//     void handleCheck() {
//         if(solveUsed) return;

//         if(!isGridUpdated){
//             JOptionPane.showMessageDialog(frame, "Make a change before checking!");
//             return;
//         }

//         if(isBoardCorrect()){
//             gameTimer.stop();
//             showFinalScore();

//             JOptionPane.showMessageDialog(frame, "🎉 Correct! You solved it!");

//             for(int i=0;i<4;i++){
//                 for(int j=0;j<4;j++){
//                     cells[i][j].setEditable(false);
//                 }
//             }

//             isGridUpdated = false;
//             return;
//         }

//         mistakes++;

//         if(mistakes >= 3){
//             gameTimer.stop();
//             showFinalScore();
//             JOptionPane.showMessageDialog(frame, "💀 Game Over!");
            
//             for(int i=0;i<4;i++){
//                 for(int j=0;j<4;j++){
//                     cells[i][j].setEditable(false);
//                 }
//             }

//             isGridUpdated = false;
//             return;
//         }

//         java.util.List<int[]> wrongCells = new java.util.ArrayList<>();

//         // -------------------------
//         // ROW CHECK (region-based)
//         // -------------------------
//         for(int i=0;i<4;i++){
//             boolean wrong = false;

//             for(int j=0;j<4;j++){
//                 if(!isFixed[i][j] && puzzleBoard[i][j] != solutionBoard[i][j]){
//                     wrong = true;
//                     break;
//                 }
//             }

//             if(wrong){
//                 for(int j=0;j<4;j++){
//                     wrongCells.add(new int[]{i,j});
//                 }
//             }
//         }

//         // -------------------------
//         // COLUMN CHECK (region-based)
//         // -------------------------
//         for(int j=0;j<4;j++){
//             boolean wrong = false;

//             for(int i=0;i<4;i++){
//                 if(!isFixed[i][j] && puzzleBoard[i][j] != solutionBoard[i][j]){
//                     wrong = true;
//                     break;
//                 }
//             }

//             if(wrong){
//                 for(int i=0;i<4;i++){
//                     wrongCells.add(new int[]{i,j});
//                 }
//             }
//         }

//         // -------------------------
//         // BOX CHECK (region-based)
//         // -------------------------
//         for(int i=0;i<4;i+=2){
//             for(int j=0;j<4;j+=2){

//                 boolean wrong = false;

//                 for(int r=0;r<2;r++){
//                     for(int c=0;c<2;c++){
//                         int x = i + r;
//                         int y = j + c;

//                         if(!isFixed[x][y] && puzzleBoard[x][y] != solutionBoard[x][y]){
//                             wrong = true;
//                         }
//                     }
//                 }

//                 if(wrong){
//                     wrongCells.addAll(getBoxCells(i,j));
//                 }
//             }
//         }

//         highlightCells(wrongCells, Color.RED);

//         javax.swing.Timer t = new javax.swing.Timer(500, e -> {
//             resetColors();
//             ((javax.swing.Timer)e.getSource()).stop();
//         });
//         t.setRepeats(false);
//         t.start();

//         isGridUpdated = false;
//     }



//     void highlightCells(java.util.List<int[]> positions, Color color) {

//         for(int[] pos : positions){
//             int r = pos[0];
//             int c = pos[1];

//             cells[r][c].setBackground(color);
//         }

//     }


//      void resetColors() {

//         for(int i=0;i<4;i++){
//             for(int j=0;j<4;j++){

//                 if(cells[i][j].isEditable()){

//                     if ((i / 2 + j / 2) % 2 == 0) {
//                         cells[i][j].setBackground(new Color(246,207,179));
//                     } else {
//                         cells[i][j].setBackground(new Color(247,181,134));
//                     }
//                 }
//             }
//         }
//     }
    


//    java.util.List<int[]> getBoxCells(int startRow, int startCol) {

//         java.util.List<int[]> list = new java.util.ArrayList<>();

//         for(int i=0;i<2;i++){
//             for(int j=0;j<2;j++){
//                 list.add(new int[]{startRow+i, startCol+j});
//             }
//         }

//         return list;
//     }



//    void handleHint() {

//         if(solveUsed) return;

//         // limit hints (optional rule)
//         if(hintsUsed >= 3){
//             JOptionPane.showMessageDialog(frame, "No more hints allowed!");
//             return;
//         }

//         java.util.List<int[]> emptyCells = new java.util.ArrayList<>();

//         // collect all empty cells
//         for(int i=0;i<4;i++){
//             for(int j=0;j<4;j++){
//                 if(puzzleBoard[i][j] == 0){
//                     emptyCells.add(new int[]{i,j});
//                 }
//             }
//         }

//         // no empty cells left
//         if(emptyCells.isEmpty()){
//             JOptionPane.showMessageDialog(frame, "No empty cells left!");
//             return;
//         }

//         // pick random empty cell
//         java.util.Random rand = new java.util.Random();
//         int[] cell = emptyCells.get(rand.nextInt(emptyCells.size()));

//         int r = cell[0];
//         int c = cell[1];

//         // fill correct value
//         puzzleBoard[r][c] = solutionBoard[r][c];

//         // update UI
//         cells[r][c].setText(String.valueOf(solutionBoard[r][c]));
//         cells[r][c].setEditable(false);
//         cells[r][c].setForeground(new Color(0, 102, 0));

//         // mark as fixed
//         isFixed[r][c] = true;

//         hintsUsed++;

//         isGridUpdated = true;

//         JOptionPane.showMessageDialog(frame, "Hint used! (-1 hint)");
//     }



//     void startTimer() {

//         gameTimer = new javax.swing.Timer(1000, e -> {
//             seconds++;
//             timerLabel.setText("Time: " + seconds + "s");
//         });

//         gameTimer.start();
//     }

// void showFinalScore() {

//     int score = 1000;

//     // -------------------------
//     // TIME PENALTY (balanced)
//     // -------------------------
//     score -= (seconds * 2);

//     // -------------------------
//     // MISTAKE PENALTY (high impact)
//     // -------------------------
//     score -= (mistakes * 120);

//     // -------------------------
//     // HINT PENALTY
//     // -------------------------
//     score -= (hintsUsed * 80);

//     // -------------------------
//     // SOLVE PENALTY (VERY IMPORTANT)
//     // -------------------------
//     if(solveUsed){
//         score -= 300;
//     }

//     // -------------------------
//     // SPEED BONUS (reward fast play)
//     // -------------------------
//     if(seconds < 60){
//         score += 150;
//     } else if(seconds < 120){
//         score += 75;
//     }

//     // -------------------------
//     // PERFECT GAME BONUS
//     // -------------------------
//     if(mistakes == 0 && hintsUsed == 0 && !solveUsed){
//         score += 200;
//     }

//     // -------------------------
//     // MIN LIMIT
//     // -------------------------
//     if(score < 0) score = 0;

//     JOptionPane.showMessageDialog(frame,
//         "🏁 GAME RESULT\n\n" +
//         "Time: " + seconds + "s\n" +
//         "Mistakes: " + mistakes + "\n" +
//         "Hints: " + hintsUsed + "\n" +
//         "Solve Used: " + solveUsed + "\n\n" +
//         "⭐ FINAL SCORE: " + score
//     );
// }


//     void handleReset() {

//         gameTimer.stop();
//         seconds = 0;
//         timerLabel.setText("Time: 0s");
//         startTimer();

//         // reset game state
//         mistakes = 0;
//         hintsUsed = 0;
//         seconds = 0;
//         isGridUpdated = false;

//         if(gameTimer != null){
//             gameTimer.stop();
//         }

//         // restore puzzle
//         for(int i=0;i<4;i++){
//             for(int j=0;j<4;j++){

//                 puzzleBoard[i][j] = initialPuzzleBoard[i][j];

//                 if(initialPuzzleBoard[i][j] != 0){
//                     cells[i][j].setText(String.valueOf(initialPuzzleBoard[i][j]));
//                     cells[i][j].setEditable(false);
//                     isFixed[i][j] = true;
//                 } else {
//                     cells[i][j].setText("");
//                     cells[i][j].setEditable(true);
//                     isFixed[i][j] = false;
//                 }

//                 // restore original color
//                 if ((i / 2 + j / 2) % 2 == 0) {
//                     cells[i][j].setBackground(new Color(246,207,179));
//                 } else {
//                     cells[i][j].setBackground(new Color(247,181,134));
//                 }
//             }
//         }
//     }



//     void handleSolve() {

//         solveUsed = true;

//         // fill board completely
//         for(int i=0;i<4;i++){
//             for(int j=0;j<4;j++){

//                 puzzleBoard[i][j] = solutionBoard[i][j];
//                 cells[i][j].setText(String.valueOf(solutionBoard[i][j]));
//                 cells[i][j].setEditable(false);

//                 isFixed[i][j] = true;
//             }
//         }

//         // stop timer
//         if(gameTimer != null){
//             gameTimer.stop();
//         }

//         // optional penalty (important for scoring fairness)
//         hintsUsed += 2;   // treat solve as heavy penalty

//         JOptionPane.showMessageDialog(frame, "Puzzle Solved!");

//         showFinalScore();
//     }



//     public static void main(String[] args) {
//         new SudokuScreen("Easy");
//     }
// }



import javax.swing.*;
import java.awt.*;

public class SudokuScreen {

    int[][] solutionBoard = new int[4][4];
    int[][] puzzleBoard = new int[4][4];
    int[][] initialPuzzleBoard = new int[4][4];

    boolean[][] isFixed = new boolean[4][4];
    JTextField[][] cells = new JTextField[4][4];

    JFrame frame;

    int mistakes = 0;
    int hintsUsed = 0;
    boolean isGridUpdated = false;
    boolean solveUsed = false;

    javax.swing.Timer gameTimer;
    int seconds = 0;
    JLabel timerLabel;

    public SudokuScreen(String difficulty) {

        generateSudoku(difficulty);

        frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        BackgroundPanel panel = new BackgroundPanel("src/Sudoku.png");
        frame.setContentPane(panel);

        // BACK BUTTON
        RoundedButton backButton = new RoundedButton("Back",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186));

        backButton.setBounds(20, 20, 100, 40);
        backButton.addActionListener(e -> {
            new Dashboard();
            frame.dispose();
        });

        // TIMER
        timerLabel = new JLabel("Time: 0s");
        timerLabel.setBounds(900, 20, 200, 40);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // GRID
        JPanel gridPanel = new JPanel(new GridLayout(4, 4));
        gridPanel.setBounds(500, 150, 500, 500);
        gridPanel.setBorder(BorderFactory.createLineBorder(new Color(18, 4, 85), 4));

        buildGrid(gridPanel);

        loadPuzzleToUI();
        copyToInitialPuzzle(); 

        // BUTTONS
        RoundedButton checkButton = new RoundedButton("CHECK",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186));

        checkButton.setBounds(500, 700, 100, 40);
        checkButton.addActionListener(e -> handleCheck());

        RoundedButton resetButton = new RoundedButton("RESET",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186));

        resetButton.setBounds(640, 700, 100, 40);
        resetButton.addActionListener(e -> handleReset());

        RoundedButton hintButton = new RoundedButton("HINT",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186));

        hintButton.setBounds(780, 700, 100, 40);
        hintButton.addActionListener(e -> handleHint());

        RoundedButton solveButton = new RoundedButton("SOLVE",
                new Color(5, 72, 149),
                new Color(26, 3, 85),
                new Color(48, 14, 186));

        solveButton.setBounds(920, 700, 100, 40);
        solveButton.addActionListener(e -> handleSolve());

        frame.add(timerLabel);
        frame.add(backButton);
        frame.add(checkButton);
        frame.add(resetButton);
        frame.add(gridPanel);
        frame.add(hintButton);
        frame.add(solveButton);
        frame.setContentPane(panel);
        frame.setVisible(true);

        startTimer();
    }

    // ================= GRID BUILD =================
    void buildGrid(JPanel gridPanel) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                final int row = i;
                final int col = j;

                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(new Font("Arial", Font.BOLD, 40));

                cell.setBorder(BorderFactory.createLineBorder(new Color(36, 24, 93), 2));

                if ((i / 2 + j / 2) % 2 == 0)
                    cell.setBackground(new Color(246, 207, 179));
                else
                    cell.setBackground(new Color(247, 181, 134));

                cell.setDocument(new javax.swing.text.PlainDocument() {
                    @Override
                    public void insertString(int offset, String str, javax.swing.text.AttributeSet attr)
                            throws javax.swing.text.BadLocationException {
                        if (str == null) return;
                        if ((getLength() + str.length()) <= 1 && str.matches("[1-4]")) {
                            super.insertString(offset, str, attr);
                        }
                    }
                });

                cell.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                    public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
                    public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
                    public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }

                    void update() {
                        if (isFixed[row][col]) return;

                        String text = cell.getText();
                        puzzleBoard[row][col] = text.isEmpty() ? 0 : Integer.parseInt(text);
                        isGridUpdated = true;
                    }
                });

                cells[i][j] = cell;
                gridPanel.add(cell);
            }
        }
    }

    // ================= SUDOKU GENERATION =================
    void generateSudoku(String difficulty) {
        generateBaseSolution();
        shuffleNumbers();
        shuffleRows();
        shuffleCols();

        copyToPuzzle();
        removeCells(difficulty);

        // ❌ DO NOT MOVE THIS ANYWHERE ELSE
    }

    void generateBaseSolution() {
        int[][] base = {
                {1, 2, 3, 4},
                {3, 4, 1, 2},
                {2, 1, 4, 3},
                {4, 3, 2, 1}
        };

        solutionBoard = base;
    }

    void shuffleNumbers() {
        java.util.List<Integer> nums = java.util.Arrays.asList(1, 2, 3, 4);
        java.util.Collections.shuffle(nums);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                solutionBoard[i][j] = nums.get(solutionBoard[i][j] - 1);
            }
        }
    }

    void shuffleRows() {
        java.util.Random r = new java.util.Random();
        for (int b = 0; b < 2; b++) {
            int r1 = b * 2 + r.nextInt(2);
            int r2 = b * 2 + r.nextInt(2);

            int[] temp = solutionBoard[r1];
            solutionBoard[r1] = solutionBoard[r2];
            solutionBoard[r2] = temp;
        }
    }

    void shuffleCols() {
        java.util.Random r = new java.util.Random();
        for (int b = 0; b < 2; b++) {
            int c1 = b * 2 + r.nextInt(2);
            int c2 = b * 2 + r.nextInt(2);

            for (int i = 0; i < 4; i++) {
                int temp = solutionBoard[i][c1];
                solutionBoard[i][c1] = solutionBoard[i][c2];
                solutionBoard[i][c2] = temp;
            }
        }
    }

    void copyToPuzzle() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                puzzleBoard[i][j] = solutionBoard[i][j];
    }

    void removeCells(String difficulty) {

        int remove = difficulty.equals("Easy") ? 4 :
                     difficulty.equals("Medium") ? 6 : 8;

        java.util.Random r = new java.util.Random();

        while (remove > 0) {
            int i = r.nextInt(4);
            int j = r.nextInt(4);

            if (puzzleBoard[i][j] != 0) {
                puzzleBoard[i][j] = 0;
                remove--;
            }
        }
    }

    // ================= LOAD =================
    void loadPuzzleToUI() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if (puzzleBoard[i][j] != 0) {
                    cells[i][j].setText(String.valueOf(puzzleBoard[i][j]));
                    cells[i][j].setEditable(false);
                    isFixed[i][j] = true;
                } else {
                    cells[i][j].setText("");
                    cells[i][j].setEditable(true);
                    isFixed[i][j] = false;
                }
            }
        }
    }

    // ================= SNAPSHOT FIX =================
    void copyToInitialPuzzle() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                initialPuzzleBoard[i][j] = puzzleBoard[i][j];
    }

    // ================= TIMER =================
    void startTimer() {
        gameTimer = new javax.swing.Timer(1000, e -> {
            seconds++;
            timerLabel.setText("Time: " + seconds + "s");
        });
        gameTimer.start();
    }

    // ================= CHECK / RESET / SOLVE =================
    void handleCheck() {
        // keep your existing validated version
    }

    void handleReset() {
        mistakes = 0;
        hintsUsed = 0;
        seconds = 0;
        isGridUpdated = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                puzzleBoard[i][j] = initialPuzzleBoard[i][j];

                if (initialPuzzleBoard[i][j] != 0) {
                    cells[i][j].setText(String.valueOf(initialPuzzleBoard[i][j]));
                    cells[i][j].setEditable(false);
                    isFixed[i][j] = true;
                } else {
                    cells[i][j].setText("");
                    cells[i][j].setEditable(true);
                    isFixed[i][j] = false;
                }
            }
        }
    }

    void handleSolve() {
        solveUsed = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                puzzleBoard[i][j] = solutionBoard[i][j];
                cells[i][j].setText(String.valueOf(solutionBoard[i][j]));
                cells[i][j].setEditable(false);
            }
        }

        if (gameTimer != null) gameTimer.stop();
    }

    void handleHint() {
        // keep your existing logic
    }

    public static void main(String[] args) {
        new SudokuScreen("Easy");
    }
}


//last improvemtnt
//gameover screen
//high score system
//check button improvements
//not repetaation of puzzle 
//check another difficulty level