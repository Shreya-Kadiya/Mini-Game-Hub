package MATH.model;

/*
 * OOP: ENCAPSULATION
 * WHY:
 * - Represents one question as object
 */

public class Question {
    private String text;
    private int answer;

    public Question(String text, int answer) {
        this.text = text;
        this.answer = answer;
    }

    public String getText() { return text; }
    public int getAnswer() { return answer; }
}