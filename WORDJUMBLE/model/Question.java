package WORDJUMBLE.model;

/*
 * MODEL CLASS
 * OOP CONCEPT: ENCAPSULATION
 * Stores question + answer pair
 */

public class Question {

    private String question;
    private String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}