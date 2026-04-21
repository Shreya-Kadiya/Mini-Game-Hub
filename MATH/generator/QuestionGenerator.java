package MATH.generator;

/*
 * OOP CONCEPT: ABSTRACTION
 * -------------------------
 * Defines contract for all difficulty levels.
 * Ensures all generators behave consistently.
 */
public interface QuestionGenerator {

    // returns question string for UI
    String generateQuestion();

    // returns correct answer
    int generateAnswer();
}