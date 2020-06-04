package com.chernikovs.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorFunction2Test {

    @Test
    void abs() {
        VectorFunction2 function = new VectorFunction2(new String[] {"u*cos(v)", "u*sin(v)", "u"});
        assertArrayEquals(ExpressionHandler.getPostfix("sqrt((u*cos(v))^2+(u*sin(v))^2+u^2)").toArray(), function.abs().getPostfix().toArray());
    }

    @Test
    void normalize() {
        VectorFunction2 function = new VectorFunction2(new String[] {"u", "v", "u+v"});
        VectorFunction2 normalized = function.normalize();
        assertArrayEquals(ExpressionHandler.getPostfix("u/sqrt(u^2+v^2+(u+v)^2)").toArray(), normalized.getFns()[0].getPostfix().toArray());
        assertArrayEquals(ExpressionHandler.getPostfix("v/sqrt(u^2+v^2+(u+v)^2)").toArray(), normalized.getFns()[1].getPostfix().toArray());
        assertArrayEquals(ExpressionHandler.getPostfix("(u+v)/sqrt(u^2+v^2+(u+v)^2)").toArray(), normalized.getFns()[2].getPostfix().toArray());
    }

    @Test
    void cross() {
        VectorFunction2 first = new VectorFunction2(new String[] {"u^2+v^2", "u^2-v^2", "u"});
        VectorFunction2 second = new VectorFunction2(new String[] {"0", "u", "v-u"});
        VectorFunction2 crossed = first.cross(second);
        assertArrayEquals(ExpressionHandler.getPostfix("(u^2-v^2)*(v-u)-u*u").toArray(), crossed.getFns()[0].getPostfix().toArray());
        assertArrayEquals(ExpressionHandler.getPostfix("-(u^2+v^2)*(v-u)").toArray(), crossed.getFns()[1].getPostfix().toArray());
        assertArrayEquals(ExpressionHandler.getPostfix("(u^2+v^2)*u)").toArray(), crossed.getFns()[2].getPostfix().toArray());
    }

    @Test
    void dot() {
        VectorFunction2 first = new VectorFunction2(new String[] {"u", "u^2", "u^v"});
        VectorFunction2 second = new VectorFunction2(new String[] {"v", "1-v", "u"});
        Function2 doted = first.dot(second);
        assertArrayEquals(ExpressionHandler.getPostfix("u*v+u^2*(1-v)+u^v*u").toArray(), doted.getPostfix().toArray());
    }
}