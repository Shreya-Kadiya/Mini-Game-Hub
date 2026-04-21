package WORDJUMBLE.core;

import WORDJUMBLE.generator.*;
import WORDJUMBLE.model.GameState;
import WORDJUMBLE.service.*;

/*
 * OOP CONCEPTS USED:
 * -------------------
 * ✔ COMPOSITION → Game HAS-A state + generator + service
 * ✔ POLYMORPHISM → generator changes by difficulty
 * ✔ ENCAPSULATION → logic hidden from UI
 */

public class WordJumbleGame {

    private GameState state;                 // COMPOSITION
    private WordGenerator generator;         // POLYMORPHISM
    private HighScoreService service;        // ABSTRACTION
    private String currentWord;

    public WordJumbleGame(String level) {

        state = new GameState();
        service = new FileHighScoreService();

        // POLYMORPHISM
        if(level.equals("Easy"))
            generator = new EasyWordGenerator();
        else if(level.equals("Medium"))
            generator = new MediumWordGenerator();
        else
            generator = new HardWordGenerator();
    }


    public void nextWord() {
        currentWord = generator.getWord();
    }

    public String getJumbled() {
        return generator.shuffle(currentWord);
    }

    public boolean checkAnswer(String ans) {
        if(ans.equalsIgnoreCase(currentWord)) {
            state.correctAnswer(10);
            return true;
        } else {
            state.wrongAnswer();
            return false;
        }
    }

    public GameState getState() {
        return state;
    }

    public String getAnswer() {
        return currentWord;
    }
}