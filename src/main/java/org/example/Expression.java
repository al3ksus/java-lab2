package org.example;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class Expression {

    Stack<String> operators = new Stack<>();
    Stack<Double> operands = new Stack<>();

    public double solve(String exp) throws Exception {
        exp = exp.replaceAll("\\s", "");
        List<String> tokens = getTokens(exp);
        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                operands.add(Double.valueOf(token));
            } else if (token.matches("[+\\-*/]")) {
                handleOperator(token);
            } else if (token.equals(")")) {
                handleClosingBracket();
            } else if (token.equals("(")) {
                operators.push(token);
            }
        }
        while (!operators.isEmpty()) {
            double operand2 = operands.pop();
            double operand1 = operands.pop();
            operands.push(calculate(operators.pop(), operand1, operand2));
        }
        return operands.pop();
    }

    private void handleOperator(String token) throws Exception {
        while (!operators.isEmpty() && hasLowerPriority(token, operators.peek())) {
            double operand2 = operands.pop();
            double operand1 = operands.pop();
            operands.push(calculate(operators.pop(), operand1, operand2));
        }
        operators.push(token);
    }

    private void handleClosingBracket() throws Exception {
        while (!operators.peek().equals("(")) {
            double operand2 = operands.pop();
            double operand1 = operands.pop();
            operands.push(calculate(operators.pop(), operand1, operand2));
        }
        operators.pop();
    }

    private List<String> getTokens(String exp) throws Exception {
        List<String> tokens = new ArrayList<>();
        Stack<Character> brackets = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < exp.length(); i++) {
            if (String.valueOf(exp.charAt(i)).matches("[+\\-*/]") &&
                    ((!tokens.isEmpty() && tokens.getLast().equals(")")) || !sb.isEmpty())) {
                if (!sb.isEmpty()) {
                    tokens.add(sb.toString());
                    sb.delete(0, sb.length());
                }
                tokens.add(String.valueOf(exp.charAt(i)));
            } else if (exp.charAt(i) == '(' && sb.isEmpty() && (tokens.isEmpty() || tokens.getLast().matches("[+\\-*/(]"))) {
                tokens.add(String.valueOf(exp.charAt(i)));
                brackets.add('(');
            } else if (exp.charAt(i) == ')' && (!sb.isEmpty() || tokens.getLast().equals(")"))) {
                tokens.add(sb.toString());
                sb.delete(0, sb.length());
                tokens.add(String.valueOf(exp.charAt(i)));
                try {
                    brackets.pop();
                } catch (EmptyStackException e) {
                    throw new Exception("Brackets error");
                }
            } else if (String.valueOf(exp.charAt(i)).matches("\\d") ||
                    (exp.charAt(i) == '.' && !sb.isEmpty() && sb.charAt(sb.length() - 1) != '-') ||
                    (exp.charAt(i) == '-' && sb.isEmpty() && (tokens.isEmpty() || tokens.getLast().equals("(")))) {
                sb.append(exp.charAt(i));
            } else {
                throw new Exception("Wrong symbol " + exp.charAt(i));
            }
        }
        if (!sb.isEmpty()) {
            tokens.add(sb.toString());
        }
        if (!brackets.isEmpty()) {
            throw new Exception("Brackets error");
        }
        if (tokens.getLast().matches("[+\\-*/(]")) {
            throw new Exception("Wrong symbol " + tokens.getLast());
        }
        return tokens;
    }

    private int getPriority(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }

    private boolean hasLowerPriority(String op1, String op2) {
        if (op1.equals("+") && op2.equals("-")) {
            return true;
        }
        return getPriority(op1) < getPriority(op2);
    }

    private double calculate(String operator, double var1, double var2) throws Exception {
        switch (operator) {
            case "+":
                return var1 + var2;
            case "-":
                return var1 - var2;
            case "*":
                return var1 * var2;
            case "/":
                if (var2 != 0) {
                    return var1 / var2;
                } else {
                    throw new ArithmeticException("Zero division");
                }
            default: return 0;
        }
    }
}
