package WORDJUMBLE.model;

public class GameState {

    private int score = 0;
    private int lives = 2;
    private int streak = 0;

    public void correctAnswer(int points) {
        score += points;
        streak++;
    }

    public void wrongAnswer() {
        lives--;
        streak = 0;
    }

    public int getScore() { return score; }
    public int getLives() { return lives; }
    public int getStreak() { return streak; }
}