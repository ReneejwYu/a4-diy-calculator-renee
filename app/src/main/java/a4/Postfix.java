package a4;

import java.util.ArrayDeque;

public class Postfix {
    public static Double postfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Double> stack = new ArrayDeque<>();

        while (!tokens.isEmpty()) {
            Object token = tokens.removeFirst();

            if (token instanceof Double) {
                stack.addFirst((Double) token);
            } else if (token instanceof Character) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Malformed postfix expression");
                }
                double b = stack.removeFirst();  // Second operand (popped first)
                double a = stack.removeFirst();  // First operand (popped second)
                char operator = (Character) token;

                double result;
                switch (operator) {
                    case '+': result = a + b; break;
                    case '-': result = a - b; break;
                    case '*': result = a * b; break;
                    case '/': 
                        if (b == 0) throw new ArithmeticException("Division by zero");
                        result = a / b; 
                        break;
                    case '^': result = Math.pow(a, b); break;
                    default: throw new IllegalArgumentException("Unknown operator: " + operator);
                } stack.addFirst(result);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Malformed postfix expression");
        } return stack.removeFirst();
    }
}
