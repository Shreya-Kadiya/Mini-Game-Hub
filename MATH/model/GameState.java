package MATH.model;

/*
 * OOP CONCEPT: ENCAPSULATION
 * WHY:
 * - Game data (score, lives, streak) is hidden
 * - Only controlled methods can modify it
 */

public class GameState {
    private int score = 0;
    private int lives = 5;
    private int streak = 0;

    public void correctAnswer() {
        score++;
        streak++;

        // bonus logic
        if (streak % 3 == 0) score++;
    }

    public void wrongAnswer() {
        lives--;
        streak = 0;
    }

    public int getScore() { return score; }
    public int getLives() { return lives; }
    public int getStreak() { return streak; }
}