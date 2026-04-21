package GuessTheWord.service;

/*
 * OOP: ABSTRACTION
 * - Defines high score behavior
 */

public interface HighScoreService {
    int getHighScore(String difficulty);
    void saveHighScore(String difficulty, int score);
}
