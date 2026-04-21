package WORDJUMBLE.service;

public interface HighScoreService {
    int getHighScore(String level);
    void saveHighScore(String level, int score);
}