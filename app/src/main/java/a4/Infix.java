package a4;

import java.util.ArrayDeque;
import java.util.Map;

public class Infix {
    private static final Map<Character, Integer> PRECEDENCE = Map.of(
        '+', 1, '-', 1, '*', 2, '/', 2, '^', 3
    );

    private static final Map<Character, Boolean> RIGHT_ASSOCIATIVE = Map.of(
        '^', true
    );

    public static Double infixToPostfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Object> outputQueue = new ArrayDeque<>();
        ArrayDeque<Character> operatorStack = new ArrayDeque<>();

        while (!tokens.isEmpty()) {
            Object token = tokens.removeFirst();

            if (token instanceof Double) {
                outputQueue.addLast(token);
            } else if (token instanceof Character) {
                char operator = (Character) token;

                if (operator == '(') {
                    operatorStack.addFirst(operator);
                } else if (operator == ')') {
                    while (!operatorStack.isEmpty() && operatorStack.peekFirst() != '(') {
                        outputQueue.addLast(operatorStack.removeFirst());
                    } if (operatorStack.isEmpty()) {
                        throw new IllegalArgumentException("Mismatched parentheses");
                    } operatorStack.removeFirst(); // Remove '('
                } else { // Operator (+, -, *, /, ^)
                    while (!operatorStack.isEmpty() && operatorStack.peekFirst() != '(') {
                        char top = operatorStack.peekFirst();
                        if ((PRECEDENCE.get(top) > PRECEDENCE.get(operator)) ||
                            (PRECEDENCE.get(top).equals(PRECEDENCE.get(operator)) && !RIGHT_ASSOCIATIVE.getOrDefault(operator, false))) {
                            outputQueue.addLast(operatorStack.removeFirst());
                        } else {
                            break;
                        }
                    } operatorStack.addFirst(operator);
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            char top = operatorStack.removeFirst();
            if (top == '(') {
                throw new IllegalArgumentException("Mismatched parentheses");
            } outputQueue.addLast(top);
        } return Postfix.postfix(outputQueue);
    }
}
