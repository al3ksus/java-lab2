package org.example;

import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Expression expression = new Expression();
        StringBuilder sb = new StringBuilder();
        System.out.println("Enter expression");
        String exp = scanner.nextLine();
        double var = 0;
        boolean f = false;

        for (int i = 0; i < exp.length(); i++) {
            if (String.valueOf(exp.charAt(i)).matches("[a-zA-Z]")) {
                sb.append(exp.charAt(i));
            } else if (!sb.isEmpty()) {
                exp = exp.replaceAll(sb.toString(), String.valueOf(getValue(sb.toString())));
                sb.delete(0, sb.length());
            }
        }
        if (!sb.isEmpty()) {
            exp = exp.replaceAll(sb.toString(), String.valueOf(getValue(sb.toString())));
        }
        try {
            System.out.println("Answer: " + expression.solve(exp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double getValue(String var) {
            String val;
            while (true) {
                System.out.println("Enter the value of the variable " + var);
                val = scanner.nextLine();
                if (val.matches("-?\\d+(\\.\\d+)?")) {
                    break;
                }
                System.out.println("Wrong value");
            }
            return Double.parseDouble(val);
    }
}