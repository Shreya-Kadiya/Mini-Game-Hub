package MATH.generator;

import java.util.Random;

/*
 * OOP CONCEPT: ABSTRACTION + POLYMORPHISM
 * Medium difficulty introduces multiple operations.
 */
public class MediumGenerator implements QuestionGenerator {

    private int answer;
    private String question;

    @Override
    public String generateQuestion() {

        Random rand = new Random();

        int a = rand.nextInt(10) + 1;
        int b = rand.nextInt(15) + 1;
        int c = rand.nextInt(10) + 1;

        char op1 = randomOp();
        char op2 = randomOp();

        int temp = calculate(a, b, op1);
        answer = calculate(temp, c, op2);

        question = a + " " + op1 + " " + b + " " + op2 + " " + c + " = ?";

        return question;
    }

    @Override
    public int generateAnswer() {
        return answer;
    }

    private char randomOp() {
        char[] ops = {'+', '-', '*'};
        return ops[new Random().nextInt(3)];
    }

    private int calculate(int a, int b, char op) {
        if (op == '+') return a + b;
        if (op == '-') return a - b;
        return a * b;
    }
}