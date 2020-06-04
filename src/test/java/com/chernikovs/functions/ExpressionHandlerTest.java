package com.chernikovs.functions;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionHandlerTest {

    private void assertArrayDequesEqual(ArrayDeque<String> expected, ArrayDeque<String> actual) {
        assertEquals(expected.size(), actual.size());

        int size = actual.size();
        for (int i = 0; i < size; i++) {
            assertEquals(expected.poll(), actual.poll());
        }
    }

    @Test
    void getTokensTest1() {
        ArrayDeque<String> actual = ExpressionHandler.getTokens("tan(x)");
        ArrayDeque<String> expected = new ArrayDeque<String>() {{
            add("tan");
            add("(");
            add("x");
            add(")");
        }};

        assertArrayDequesEqual(expected, actual);
    }

    @Test
    void getTokensTest2() {
        ArrayDeque<String> actual = ExpressionHandler.getTokens("10+x-6/a1^3*b0");
        ArrayDeque<String> expected = new ArrayDeque<String>() {{
            add("10");
            add("+");
            add("x");
            add("-");
            add("6");
            add("/");
            add("a1");
            add("^");
            add("3");
            add("*");
            add("b0");
        }};

        assertArrayDequesEqual(expected, actual);
    }

    @Test
    void getTokensTest3() {
        ArrayDeque<String> actual = ExpressionHandler.getTokens("E+e-max(2.5E+10,4.2E-20)*E");
        ArrayDeque<String> expected = new ArrayDeque<String>() {{
            add("E");
            add("+");
            add("e");
            add("-");
            add("max");
            add("(");
            add("2.5E+10");
            add(",");
            add("4.2E-20");
            add(")");
            add("*");
            add("E");
        }};

        assertArrayDequesEqual(expected, actual);
    }

    @Test
    void getTokensTest4() {
        ArrayDeque<String> actual = ExpressionHandler.getTokens("7.4350004E+a");
        ArrayDeque<String> expected = new ArrayDeque<String>() {{
            add("7.4350004E");
            add("+");
            add("a");
        }};

        assertArrayDequesEqual(expected, actual);
    }

    @Test
    void substituteConstantsTest() {
        ArrayDeque<String> actual = ExpressionHandler.substituteConstants(new ArrayDeque<String>() {{
            add("e");
            add("+");
            add("pi");
            add("+");
            add("gamma");
            add("+");
            add("phi");
        }});
        ArrayDeque<String> expected = new ArrayDeque<String>() {{
            add("2.718281828459045");
            add("+");
            add("3.141592653589793");
            add("+");
            add("0.5772156649015329");
            add("+");
            add("1.618033988749895");
        }};

        assertArrayDequesEqual(expected, actual);
    }

    @Test
    void removeMinusesTest1() {
        ArrayDeque<String> actual = ExpressionHandler.removeMinuses(new ArrayDeque<String>() {{
            add("h");
            add("+");
            add("-");
            add("5");
            add("*");
            add("k");
            add("/");
            add("s");
        }});
        ArrayDeque<String> expected = new ArrayDeque<String>() {{
            add("h");
            add("+");
            add("neg");
            add("(");
            add("5");
            add("*");
            add("k");
            add("/");
            add("s");
            add(")");
        }};

        assertArrayDequesEqual(expected, actual);
    }

    @Test
    void removeMinusesTest2() {
        ArrayDeque<String> actual = ExpressionHandler.removeMinuses(new ArrayDeque<String>() {{
            add("-");
            add("-");
            add("-");
            add("t");
        }});
        ArrayDeque<String> expected = new ArrayDeque<String>() {{
            add("neg");
            add("(");
            add("neg");
            add("(");
            add("neg");
            add("(");
            add("t");
            add(")");
            add(")");
            add(")");
        }};

        assertArrayDequesEqual(expected, actual);
    }

    @Test
    void getPostfixTest1() {
        ArrayDeque<String> actual = ExpressionHandler.getPostfix("-log(x-sin(x)^2)^3");
        ArrayDeque<String> expected = new ArrayDeque<String>() {{
            add("x");
            add("x");
            add("sin");
            add("2");
            add("^");
            add("-");
            add("log");
            add("3");
            add("^");
            add("neg");
        }};

        assertArrayDequesEqual(expected, actual);
    }

    @Test
    void getPostfixTest2() {
        ArrayDeque<String> actual = ExpressionHandler.getPostfix("(1+cos(u/2)*sin(v)-sin(u/2)*sin(2*v))*cos(u)");
        ArrayDeque<String> expected = new ArrayDeque<String>() {{
            add("1");
            add("u");
            add("2");
            add("/");
            add("cos");
            add("v");
            add("sin");
            add("*");
            add("+");
            add("u");
            add("2");
            add("/");
            add("sin");
            add("2");
            add("v");
            add("*");
            add("sin");
            add("*");
            add("-");
            add("u");
            add("cos");
            add("*");
        }};

        assertArrayDequesEqual(expected, actual);
    }

    @Test
    void getPostfixTest3() {
        ArrayDeque<String> actual = ExpressionHandler.getPostfix("max(cbrt(x),y^2^3)");
        ArrayDeque<String> expected = new ArrayDeque<String>() {{
            add("x");
            add("cbrt");
            add("y");
            add("2");
            add("3");
            add("^");
            add("^");
            add("max");
        }};

        assertArrayDequesEqual(expected, actual);
    }

    @Test
    void getPostfixTest4() {
        ArrayDeque<String> actual = ExpressionHandler.getPostfix("9-5+3");
        ArrayDeque<String> expected = new ArrayDeque<String>() {{
            add("9");
            add("5");
            add("-");
            add("3");
            add("+");
        }};

        assertArrayDequesEqual(expected, actual);
    }

}