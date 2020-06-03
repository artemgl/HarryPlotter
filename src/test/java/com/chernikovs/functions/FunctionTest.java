package com.chernikovs.functions;

import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.*;

class FunctionTest {

    double epsilon = 0.0000001;

    @Test
    void calcTest1() {
        Function function = new Function("3/(x^2-1)");
        assertEquals(1, function.calc(2), epsilon);
        assertEquals(-4, function.calc(0.5), epsilon);
        assertEquals(Double.POSITIVE_INFINITY, function.calc(1), epsilon);
    }

    @Test
    void calcTest2() {
        Function function = new Function("cos(x)-1");
        assertEquals(-2, function.calc(Math.PI), epsilon);
        assertEquals(0, function.calc(0), epsilon);
        assertEquals(-1, function.calc(-Math.PI / 2), epsilon);
    }

    @Test
    void calcTest3() {
        Function function = new Function("sqrt(x^2+4)");
        assertEquals(2, function.calc(0), epsilon);
        assertEquals(3, function.calc(Math.sqrt(5)), epsilon);
        assertEquals(4, function.calc(-2 * Math.sqrt(3)), epsilon);
    }

    @Test
    void diffTest1() {
        Function function = new Function("3*x^5");
        ArrayDeque<String> expectedPostfix = ExpressionHandler.getPostfix("3*(5*x^4.0)");
        ArrayDeque<String> actualPostfix = function.diff().getPostfix();
        assertArrayEquals(expectedPostfix.toArray(), actualPostfix.toArray());
    }

    @Test
    void diffTest2() {
        Function function2 = new Function("sin(x)+1/x");
        ArrayDeque<String> expectedPostfix = ExpressionHandler.getPostfix("cos(x)+neg(1.0/x^2)");
        ArrayDeque<String> actualPostfix = function2.diff().getPostfix();
        assertArrayEquals(expectedPostfix.toArray(), actualPostfix.toArray());
    }

    @Test
    void integrateTest1() {
        Function function = new Function("2*x");
        assertEquals(1, function.integrate(0, 1), epsilon);
    }

    @Test
    void integrateTest2() {
        Function function = new Function("sqrt(1-x^2)");
        assertEquals(Math.PI/2, function.integrate(-1, 1), epsilon);
    }
}