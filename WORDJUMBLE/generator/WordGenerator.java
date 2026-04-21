package WORDJUMBLE.generator;

/*
 * OOP CONCEPT: ABSTRACTION
 * ------------------------
 * - Defines blueprint for all generators
 */

public interface WordGenerator {
    String getWord();
    String shuffle(String word);
}