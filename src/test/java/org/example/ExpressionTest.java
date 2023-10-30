package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ExpressionTest {

    @Test
    void solve() throws Exception {
        Expression expression = new Expression();
        List<Double> results = new ArrayList<>();
        results.add(expression.solve("3 + 4"));
        results.add(expression.solve("12 - 5 + 4 - 3"));
        results.add(expression.solve("(6 + 1)*(13-4)*(3+4)"));
        results.add(expression.solve("(3 + 9)/(1 + 5)"));

        Assertions.assertEquals(List.of(7.0, 8.0, 441.0, 2.0), results);
    }

    @Test
    void divideByZero() {
        Expression expression = new Expression();
        Assertions.assertThrows(ArithmeticException.class, () -> expression.solve("12 / (4 - 2*2)"));
    }

    @Test
    void bracketsError() {
        Expression expression = new Expression();
        Assertions.assertThrows(Exception.class, () -> expression.solve("((4 - 1) * (2 - 3)"));
    }

    @Test
    void wrongSymbol() {
        Expression expression = new Expression();
        Assertions.assertThrows(Exception.class, () -> expression.solve("2 & 4"));
    }
}
