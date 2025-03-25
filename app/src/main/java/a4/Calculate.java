package main.java.a4;

import java.util.ArrayDeque;
import java.util.Map;

public class Calculate {
    
    /**
     * Converts an infix expression to postfix using the shunting-yard algorithm
     * and computes the result using Postfix.postfix().
     * 
     * @param expression The infix expression as a string
     * @return The computed result as a Double
     */
    public static Double evaluate(String expression) {
        ArrayDeque<Object> tokens = Tokenizer.readTokens(expression);
        ArrayDeque<Object> postfixQueue = infixToPostfix(tokens);
        return Postfix.postfix(postfixQueue);
    }
    
    /**
     * Converts an infix expression queue into a postfix expression queue
     * using the shunting-yard algorithm.
     * 
     * @param tokens The queue of tokens representing the infix expression
     * @return A queue of tokens in postfix notation
     */
    private static ArrayDeque<Object> infixToPostfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Object> outputQueue = new ArrayDeque<>();
        ArrayDeque<Character> operatorStack = new ArrayDeque<>();
        
        Map<Character, Integer> precedence = Map.of(
            '+', 1, '-', 1,
            '*', 2, '/', 2
        );
        
        for (Object token : tokens) {
            if (token instanceof Double) {
                outputQueue.add(token);
            } else if (token instanceof Character) {
                char op = (Character) token;
                if (op == '(') {
                    operatorStack.push(op);
                } else if (op == ')') {
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.pop(); // Remove '('
                } else {
                    while (!operatorStack.isEmpty() && precedence.getOrDefault(operatorStack.peek(), 0) >= precedence.get(op)) {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.push(op);
                }
            }
        }
        
        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop());
        }
        
        return outputQueue;
    }
}
