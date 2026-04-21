package MATH.generator;

import java.util.Random;

/*
 * OOP CONCEPT: ENCAPSULATION + POLYMORPHISM
 * Hard level uses expression logic.
 */
public class HardGenerator implements QuestionGenerator {

    private int answer;
    private String question;

    @Override
    public String generateQuestion() {

        Random rand = new Random();

        int a = rand.nextInt(20) + 1;
        int b = rand.nextInt(12) + 1;
        int c = rand.nextInt(7) + 1;

        answer = (a + b) * c;

        question = "(" + a + " + " + b + ") × " + c + " = ?";

        return question;
    }

    @Override
    public int generateAnswer() {
        return answer;
    }
}