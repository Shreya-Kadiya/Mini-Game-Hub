
    import javax.swing.*;
    import java.awt.*;
    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileReader;
    import java.io.FileWriter;
    

    public class SudokuScreen {

        int[][] solutionBoard = new int[4][4];
        int[][] puzzleBoard = new int[4][4];
        boolean[][] isFixed = new boolean[4][4];
        JTextField[][] cells = new JTextField[4][4];

        int mistakes = 0;
        int hintsUsed = 0;
        boolean isGridUpdated = false;
        boolean solveUsed = false;

        JFrame frame;

        int[][] initialPuzzleBoard = new int[4][4];

        javax.swing.Timer gameTimer;
        int seconds = 0;
        JLabel timerLabel;

        int highScoreEasy = 0;
        int highScoreMedium = 0;
        int highScoreHard = 0;

        int solvePenalty = 0;
        String difficultyLevel;

        public SudokuScreen(String difficulty) {

            this.difficultyLevel = difficulty;

            generateSudoku(difficulty);
            loadHighScore();

            frame = new JFrame("Sudoku");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            BackgroundPanel panel = new BackgroundPanel("src/Sudoku.png");
            frame.setContentPane(panel);

            RoundedButton backButton = new RoundedButton("Back",
                    new Color(5, 72, 149),
                    new Color(26, 3, 85),
                    new Color(48, 14, 186)
            );

            backButton.setBounds(20, 20, 100, 40);
            backButton.addActionListener(e -> {
                new SudokuGUI();
                frame.dispose();
            });

            JPanel gridPanel = new JPanel();
            gridPanel.setLayout(new GridLayout(4, 4));
            gridPanel.setBounds(500, 150, 500, 500);
            gridPanel.setOpaque(true);
            gridPanel.setBackground(new Color(250, 250, 250));
            gridPanel.setBorder(BorderFactory.createLineBorder(new Color(18, 4, 85), 4));


            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {

                    final int row = i;
                    final int col = j;

                    JTextField cell = new JTextField();
                    cell.setHorizontalAlignment(JTextField.CENTER);
                    cell.setFont(new Font("Arial", Font.BOLD, 50));
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

                            if (text.isEmpty()) puzzleBoard[row][col] = 0;
                            else puzzleBoard[row][col] = Integer.parseInt(text);

                            isGridUpdated = true;
                        }
                    });

                    cells[i][j] = cell;
                    gridPanel.add(cell);
                }
            }



            copyToInitialPuzzle();
            loadPuzzleToUI();

            timerLabel = new JLabel("Time: 0s");
            timerLabel.setBounds(900, 20, 200, 40);
            timerLabel.setFont(new Font("Arial", Font.BOLD, 20));

            RoundedButton checkButton = new RoundedButton("CHECK",
                    new Color(5, 72, 149),
                    new Color(26, 3, 85),
                    new Color(48, 14, 186)
            );

            checkButton.setBounds(500, 700, 100, 40);
            checkButton.addActionListener(e -> handleCheck());

            RoundedButton resetButton = new RoundedButton("RESET",
                    new Color(5, 72, 149),
                    new Color(26, 3, 85),
                    new Color(48, 14, 186)
            );

            resetButton.setBounds(640, 700, 100, 40);
            resetButton.addActionListener(e -> handleReset());

            RoundedButton hintButton = new RoundedButton("HINT",
                    new Color(5, 72, 149),
                    new Color(26, 3, 85),
                    new Color(48, 14, 186)
            );

            hintButton.setBounds(780, 700, 100, 40);
            hintButton.addActionListener(e -> handleHint());

            RoundedButton solveButton = new RoundedButton("SOLVE",
                    new Color(5, 72, 149),
                    new Color(26, 3, 85),
                    new Color(48, 14, 186)
            );

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

        

        void generateSudoku(String difficulty) {
            generateBaseSolution();
            shuffleNumbers();
            shuffleRows();
            shuffleCols();
            copyToPuzzle();
            removeCells(difficulty);
            copyToInitialPuzzle();
        }

        void generateBaseSolution() {
            int[][] base = {
                    {1,2,3,4},
                    {3,4,1,2},
                    {2,1,4,3},
                    {4,3,2,1}
            };

            for (int i = 0; i < 4; i++)
                System.arraycopy(base[i], 0, solutionBoard[i], 0, 4);
        }

        void shuffleNumbers() {
            java.util.List<Integer> nums = java.util.Arrays.asList(1, 2, 3, 4);
            java.util.Collections.shuffle(nums);

            int[] map = new int[5];
            map[1]=nums.get(0);
            map[2]=nums.get(1);
            map[3]=nums.get(2);
            map[4]=nums.get(3);

            for (int i=0;i<4;i++)
                for (int j=0;j<4;j++)
                    solutionBoard[i][j]=map[solutionBoard[i][j]];
        }

        void shuffleRows() {
            java.util.Random r=new java.util.Random();
            for(int b=0;b<2;b++){
                int r1=b*2+r.nextInt(2);
                int r2=b*2+r.nextInt(2);
                int[] t=solutionBoard[r1];
                solutionBoard[r1]=solutionBoard[r2];
                solutionBoard[r2]=t;
            }
        }

        void shuffleCols() {
            java.util.Random r=new java.util.Random();
            for(int b=0;b<2;b++){
                int c1=b*2+r.nextInt(2);
                int c2=b*2+r.nextInt(2);
                for(int i=0;i<4;i++){
                    int t=solutionBoard[i][c1];
                    solutionBoard[i][c1]=solutionBoard[i][c2];
                    solutionBoard[i][c2]=t;
                }
            }
        }

        void copyToPuzzle(){
            for(int i=0;i<4;i++)
                for(int j=0;j<4;j++)
                    puzzleBoard[i][j]=solutionBoard[i][j];
        }

        void removeCells(String diff) {

    int removeCount;

        if (diff.equals("Easy")) removeCount = 4;
        else if (diff.equals("Medium")) removeCount = 6;
        else removeCount = 8;

        java.util.Random rand = new java.util.Random();

        int attempts = removeCount * 5; // try multiple times

        while (removeCount > 0 && attempts > 0) {

            int i = rand.nextInt(4);
            int j = rand.nextInt(4);

            if (puzzleBoard[i][j] == 0) {
                attempts--;
                continue;
            }

            int backup = puzzleBoard[i][j];
            puzzleBoard[i][j] = 0;

            int[][] copy = new int[4][4];
            for (int r = 0; r < 4; r++)
                System.arraycopy(puzzleBoard[r], 0, copy[r], 0, 4);

            int solutions = countSolutions(copy);

            if (solutions != 1) {
                // restore if not unique
                puzzleBoard[i][j] = backup;
                attempts--;
            } else {
                removeCount--;
            }
        }
    }

        void copyToInitialPuzzle(){
            for(int i=0;i<4;i++)
                for(int j=0;j<4;j++)
                    initialPuzzleBoard[i][j]=puzzleBoard[i][j];
        }



    int countSolutions(int[][] board) {
            return solve(board, 0, 0, 0);
        }

    int solve(int[][] board, int row, int col, int count) {

        if (row == 4) return count + 1;

        int nextRow = (col == 3) ? row + 1 : row;
        int nextCol = (col + 1) % 4;

        if (board[row][col] != 0) {
            return solve(board, nextRow, nextCol, count);
        }

        for (int num = 1; num <= 4; num++) {

            if (isSafe(board, row, col, num)) {

                board[row][col] = num;

                count = solve(board, nextRow, nextCol, count);

                if (count > 1) {
                    board[row][col] = 0;
                    return count; // stop early if more than 1 solution
                }

                board[row][col] = 0;
            }
        }

        return count;
    }

    boolean isSafe(int[][] board, int row, int col, int num) {

        // row
        for (int j = 0; j < 4; j++)
            if (board[row][j] == num) return false;

        // column
        for (int i = 0; i < 4; i++)
            if (board[i][col] == num) return false;

        // box
        int startRow = (row / 2) * 2;
        int startCol = (col / 2) * 2;

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                if (board[startRow + i][startCol + j] == num) return false;

        return true;
    }

    
    
    

        void loadPuzzleToUI(){
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    if(puzzleBoard[i][j]!=0){
                        cells[i][j].setText(String.valueOf(puzzleBoard[i][j]));
                        cells[i][j].setEditable(false);
                        cells[i][j].setForeground(new Color(43,112,68));
                        isFixed[i][j]=true;
                    } else {
                        cells[i][j].setText("");
                        cells[i][j].setEditable(true);
                        cells[i][j].setForeground(new Color(23,57,35));
                        isFixed[i][j]=false;
                    }
                }
            }
        }

        //CHECK 

        void handleCheck() {

            if (solveUsed) return;

            if (!isGridUpdated) {
                JOptionPane.showMessageDialog(frame, "Make a change before checking!");
                return;
            }

            java.util.List<int[]> wrongCells = new java.util.ArrayList<>();

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {

                    if (!isFixed[i][j]) {

                        int val = puzzleBoard[i][j];

                        if (val == 0 || val != solutionBoard[i][j]) {
                            wrongCells.add(new int[]{i, j});
                        }
                    }
                }
            }

            if (wrongCells.isEmpty()) {
                endGame("Perfect Solution!");
                return;
            }

            highlightCells(wrongCells, Color.RED);

            javax.swing.Timer t = new javax.swing.Timer(500, e -> {
                resetColors();
                ((javax.swing.Timer) e.getSource()).stop();
            });
            t.setRepeats(false);
            t.start();

            mistakes++;

            if (mistakes >= 3) {
                endGame("Too many mistakes");
                return;
            }

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    cells[i][j].setEditable(!isFixed[i][j]);
                }
            }

            isGridUpdated = false;
        }



        void handleReset() {

        solveUsed = false;
        mistakes = 0;
        hintsUsed = 0;
        isGridUpdated = false;
        solvePenalty = 0;

        if (gameTimer != null) {
            gameTimer.stop();
        }

        seconds = 0;
        timerLabel.setText("Time: 0s");
        startTimer();

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

                if ((i / 2 + j / 2) % 2 == 0)
                    cells[i][j].setBackground(new Color(246, 207, 179));
                else
                    cells[i][j].setBackground(new Color(247, 181, 134));

                cells[i][j].setForeground(new Color(30, 30, 50));
            }
        }
    }




    void handleHint() {

        if (solveUsed) return;

        if (hintsUsed >= 3) {
            JOptionPane.showMessageDialog(frame, "No more hints allowed!");
            return;
        }

        java.util.List<int[]> emptyCells = new java.util.ArrayList<>();
        java.util.List<int[]> wrongCells = new java.util.ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if (puzzleBoard[i][j] == 0) {
                    emptyCells.add(new int[]{i, j});
                }
                else if (!isFixed[i][j] && puzzleBoard[i][j] != solutionBoard[i][j]) {
                    wrongCells.add(new int[]{i, j});
                }
            }
        }

        java.util.Random rand = new java.util.Random();
        int r, c;

        if (!emptyCells.isEmpty()) {
            int[] cell = emptyCells.get(rand.nextInt(emptyCells.size()));
            r = cell[0];
            c = cell[1];
            
        }
        else if (!wrongCells.isEmpty()) {
            int[] cell = wrongCells.get(rand.nextInt(wrongCells.size()));
            r = cell[0];
            c = cell[1];
        }
        else {
            JOptionPane.showMessageDialog(frame, "Nothing to hint!");
            return;
        }

        puzzleBoard[r][c] = solutionBoard[r][c];
        cells[r][c].setText(String.valueOf(solutionBoard[r][c]));
        cells[r][c].setForeground(new Color(28,58,39));
        cells[r][c].setEditable(false);
        

        isFixed[r][c] = true;

        hintsUsed++;
        isGridUpdated = true;

        JOptionPane.showMessageDialog(frame, "Hint applied!");
    }   



    void handleSolve() {

        solveUsed = true;
        solvePenalty = 1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                puzzleBoard[i][j] = solutionBoard[i][j];
                cells[i][j].setText(String.valueOf(solutionBoard[i][j]));
                cells[i][j].setEditable(false);
                isFixed[i][j] = true;
            }
        }

        if (gameTimer != null) {
            gameTimer.stop();
        }

        endGame("Puzzle Solved using SOLVE");
    }

        // FINAL SCORE + END
        int calculateFinalScore() {

            int score = 100;

            score -= seconds / 2;
            score -= mistakes * 15;
            score -= hintsUsed * 10;

            if (solveUsed) score -= 40;

            if (seconds < 60) score += 30;
            else if (seconds < 120) score += 15;

            if (mistakes == 0 && hintsUsed == 0 && !solveUsed)
                score += 50;

            return Math.max(score, 0);
        }

        void endGame(String reason) {

            if (gameTimer != null) gameTimer.stop();

            int score = calculateFinalScore();

            if (difficultyLevel.equals("Easy")) {
                highScoreEasy = Math.max(highScoreEasy, score);
            } else if (difficultyLevel.equals("Medium")) {
                highScoreMedium = Math.max(highScoreMedium, score);
            } else {
                highScoreHard = Math.max(highScoreHard, score);
            }

            saveHighScore();

            new SudokuGameOver(reason, score, getHighScore(), difficultyLevel);

            frame.dispose();
        }

        int getHighScore() {
            return difficultyLevel.equals("Easy") ? highScoreEasy :
                difficultyLevel.equals("Medium") ? highScoreMedium :
                highScoreHard;
        }

        //TIMER

        void startTimer() {
            gameTimer = new javax.swing.Timer(1000, e -> {
                seconds++;
                timerLabel.setText("Time: " + seconds + "s");
            });
            gameTimer.start();
        }

        // HIGH SCORE 

        void saveHighScore() {
            try {
                File file = new File(System.getProperty("user.dir"), "Sudoku/Sudoku_highscore.txt");
                FileWriter fw = new FileWriter(file);
                fw.write(highScoreEasy + "\n");
                fw.write(highScoreMedium + "\n");
                fw.write(highScoreHard + "\n");
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void loadHighScore() {
            try {
                File file = new File(System.getProperty("user.dir"), "Sudoku/Sudoku_highscore.txt");

                if (!file.exists()) {
                    FileWriter fw = new FileWriter(file);
                    fw.write("0\n0\n0");
                    fw.close();
                }

                BufferedReader br = new BufferedReader(new FileReader(file));
                highScoreEasy = Integer.parseInt(br.readLine());
                highScoreMedium = Integer.parseInt(br.readLine());
                highScoreHard = Integer.parseInt(br.readLine());
                br.close();

            } catch (Exception e) {
                highScoreEasy = highScoreMedium = highScoreHard = 0;
            }
        }

        //UI helpers

        void highlightCells(java.util.List<int[]> pos, Color c) {
            for (int[] p : pos)
                cells[p[0]][p[1]].setBackground(c);
        }

        void resetColors() {
            for (int i=0;i<4;i++)
                for (int j=0;j<4;j++)
                    if (cells[i][j].isEditable())
                        if ((i/2+j/2)%2==0)
                            cells[i][j].setBackground(new Color(246,207,179));
                        else
                            cells[i][j].setBackground(new Color(247,181,134));
        }

        

        public static void main(String[] args) {
            new SudokuScreen("Easy");
        }
    }