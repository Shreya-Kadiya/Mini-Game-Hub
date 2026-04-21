package MATH.generator;

import java.util.Random;

/*
 * OOP CONCEPT: POLYMORPHISM + ENCAPSULATION
 
 * Easy-level logic is isolated here.
 * Stores internal state (question + answer).
 */
public class EasyGenerator implements QuestionGenerator {

    private int answer;
    private String question;

    @Override
    public String generateQuestion() {

        Random rand = new Random();

        int a = rand.nextInt(15) + 1;
        int b = rand.nextInt(15) + 1;

        // simple + / -
        if (rand.nextBoolean()) {
            answer = a + b;
            question = a + " + " + b + " = ?";
        } else {
            // avoid negative
            if (a < b) {
                int temp = a;
                a = b;
                b = temp;
            }

            answer = a - b;
            question = a + " - " + b + " = ?";
        }

        return question;
    }

    @Override
    public int generateAnswer() {
        return answer;
    }
}