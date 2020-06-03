package com.chernikovs.functions;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.*;

class Function2Test {

    double epsilon = 0.0000001;

    @Test
    void calcTest1() {
        Function2 function = new Function2("x^y");
        assertEquals(1, function.calc(1, 999), epsilon);
        assertEquals(32, function.calc(2, 5), epsilon);
        assertEquals(1, function.calc(999, 0), epsilon);
    }

    @Test
    void calcTest2() {
        Function2 function = new Function2("sgn(x)+max(x,y)");
        assertEquals(10, function.calc(5, 9), epsilon);
        assertEquals(0, function.calc(-5, 1), epsilon);
        assertEquals(-2, function.calc(-1, -10), epsilon);
    }

    @Test
    void calcByFstTest1() {
        Function2 function = new Function2("ln(x)+cbrt(y)");
        assertArrayEquals(ExpressionHandler.getPostfix("ln(e)+cbrt(y)").toArray(), function.calcByFst(Math.E).getPostfix().toArray());
    }

    @Test
    void calcByFstTest2() {
        Function2 function = new Function2("sec(y^3)");
        assertArrayEquals(function.getPostfix().toArray(), function.calcByFst(5).getPostfix().toArray());
    }

    @Test
    void calcByFstTest3() {
        Function2 function = new Function2("fact(x)");
        assertArrayEquals(ExpressionHandler.getPostfix("fact(3.0)").toArray(), function.calcByFst(3).getPostfix().toArray());
    }

    @Test
    void calcByFstTest4() {
        Function2 function = new Function2("0");
        assertArrayEquals(function.getPostfix().toArray(), function.calcByFst(10).getPostfix().toArray());
    }

    @Test
    void calcBySndTest1() {
        Function2 function = new Function2("min(x,y)-abs(y)");
        assertArrayEquals(ExpressionHandler.getPostfix("min(x,8.0)-abs(8.0)").toArray(), function.calcBySnd(8).getPostfix().toArray());
    }

    @Test
    void calcBySndTest2() {
        Function2 function = new Function2("x^7-x^3");
        assertArrayEquals(function.getPostfix().toArray(), function.calcBySnd(0).getPostfix().toArray());
    }

    @Test
    void calcBySndTest3() {
        Function2 function = new Function2("cot(y)+9");
        assertArrayEquals(ExpressionHandler.getPostfix("cot(pi)+9").toArray(), function.calcBySnd(Math.PI).getPostfix().toArray());
    }

    @Test
    void calcBySndTest4() {
        Function2 function = new Function2("0");
        assertArrayEquals(function.getPostfix().toArray(), function.calcBySnd(10).getPostfix().toArray());
    }

    @Test
    void diffByFstTest1() {
        Function2 function = new Function2("(x+2*y)/sqrt(y)");
        ArrayDeque<String> expectedPostfix = ExpressionHandler.getPostfix("1.0/sqrt(y)");
        ArrayDeque<String> actualPostfix = function.diffByFst().getPostfix();
        assertArrayEquals(expectedPostfix.toArray(), actualPostfix.toArray());
    }

    @Test
    void diffByFstTest2() {
        Function2 function = new Function2("tan(y)");
        ArrayDeque<String> expectedPostfix = ExpressionHandler.getPostfix("0");
        ArrayDeque<String> actualPostfix = function.diffByFst().getPostfix();
        assertArrayEquals(expectedPostfix.toArray(), actualPostfix.toArray());
    }

    @Test
    void diffBySndTest1() {
        Function2 function = new Function2("cos(x+y)");
        ArrayDeque<String> expectedPostfix = ExpressionHandler.getPostfix("-sin(x+y)");
        ArrayDeque<String> actualPostfix = function.diffBySnd().getPostfix();
        assertArrayEquals(expectedPostfix.toArray(), actualPostfix.toArray());
    }

    @Test
    void diffBySndTest2() {
        Function2 function = new Function2("1/(x+7)-x^4");
        ArrayDeque<String> expectedPostfix = ExpressionHandler.getPostfix("0.0");
        ArrayDeque<String> actualPostfix = function.diffBySnd().getPostfix();
        assertArrayEquals(expectedPostfix.toArray(), actualPostfix.toArray());
    }
}