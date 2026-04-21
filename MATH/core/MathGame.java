package MATH.core;

import MATH.generator.*;
import MATH.service.*;
import MATH.model.GameState;

/*
 * OOP CONCEPTS USED:
 * -------------------
 * ✔ Composition → Game HAS-A state, generator, service
 * ✔ Polymorphism → generator changes by difficulty
 * ✔ Encapsulation → state hidden inside class
 */

public class MathGame {

    private GameState state;
    private QuestionGenerator generator;
    private HighScoreService scoreService;

    private String currentQuestion;
    private int currentAnswer;

    private String difficulty;

    public MathGame(String difficulty) {

        this.difficulty = difficulty;

        // COMPOSITION
        state = new GameState();
        scoreService = new FileHighScoreService();

        // POLYMORPHISM (clean selection)
        generator = createGenerator(difficulty);
    }

    // =========================================================
    // FACTORY METHOD (CLEAN OOP IMPROVEMENT)
    // =========================================================
    private QuestionGenerator createGenerator(String difficulty) {

        switch (difficulty) {
            case "Easy":
                return new EasyGenerator();

            case "Medium":
                return new MediumGenerator();

            default:
                return new HardGenerator();
        }
    }

    // =========================================================
    // NEXT QUESTION
    // =========================================================
    public void nextQuestion() {

        currentQuestion = generator.generateQuestion();
        currentAnswer = generator.generateAnswer();
    }

    // =========================================================
    // GET QUESTION
    // =========================================================
    public String getQuestion() {
        return currentQuestion;
    }

    // =========================================================
    // CHECK ANSWER
    // =========================================================
    public boolean checkAnswer(int ans) {

        if (ans == currentAnswer) {
            state.correctAnswer();
            return true;
        } else {
            state.wrongAnswer();
            return false;
        }
    }

    // =========================================================
    // GAME STATE
    // =========================================================
    public GameState getState() {
        return state;
    }

    // =========================================================
    // HIGH SCORE SERVICE
    // =========================================================
    public int getHighScore() {
        return scoreService.getHighScore(difficulty);
    }

    public void endGame() {
        scoreService.saveHighScore(difficulty, state.getScore());
    }
}