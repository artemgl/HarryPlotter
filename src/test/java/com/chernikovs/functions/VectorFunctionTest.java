package com.chernikovs.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class VectorFunctionTest {

    @Test
    void abs() {
        VectorFunction function = new VectorFunction(new String[] {"cos(t)", "sin(t)", "t"});
        assertArrayEquals(ExpressionHandler.getPostfix("sqrt(cos(t)^2+sin(t)^2+t^2)").toArray(), function.abs().getPostfix().toArray());
    }

    @Test
    void normalize() {
        VectorFunction function = new VectorFunction(new String[] {"t", "1-t", "1+t"});
        VectorFunction normalized = function.normalize();
        assertArrayEquals(ExpressionHandler.getPostfix("t/sqrt(t^2+(1-t)^2+(1+t)^2)").toArray(), normalized.getFns()[0].getPostfix().toArray());
        assertArrayEquals(ExpressionHandler.getPostfix("(1-t)/sqrt(t^2+(1-t)^2+(1+t)^2)").toArray(), normalized.getFns()[1].getPostfix().toArray());
        assertArrayEquals(ExpressionHandler.getPostfix("(1+t)/sqrt(t^2+(1-t)^2+(1+t)^2)").toArray(), normalized.getFns()[2].getPostfix().toArray());
    }

    @Test
    void cross() {
        VectorFunction first = new VectorFunction(new String[] {"1", "2", "t"});
        VectorFunction second = new VectorFunction(new String[] {"t", "1", "2"});
        VectorFunction crossed = first.cross(second);
        assertArrayEquals(ExpressionHandler.getPostfix("4.0-t").toArray(), crossed.getFns()[0].getPostfix().toArray());
        assertArrayEquals(ExpressionHandler.getPostfix("t*t-2.0").toArray(), crossed.getFns()[1].getPostfix().toArray());
        assertArrayEquals(ExpressionHandler.getPostfix("1.0-t*2").toArray(), crossed.getFns()[2].getPostfix().toArray());
    }

    @Test
    void dot() {
        VectorFunction first = new VectorFunction(new String[] {"t", "5", "0"});
        VectorFunction second = new VectorFunction(new String[] {"t", "2", "1"});
        Function doted = first.dot(second);
        assertArrayEquals(ExpressionHandler.getPostfix("t*t+10.0").toArray(), doted.getPostfix().toArray());
    }
}