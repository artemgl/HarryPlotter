package com.chernikovs.functions;

import java.util.ArrayDeque;
import java.util.Stack;

/**A class describing one argument function*/
public class Function {
    
    private ArrayDeque<String> postfix = new ArrayDeque<>();
    private ArrayDeque<String> reduced = new ArrayDeque<>();
    private String var;
    
    public Function(ArrayDeque<String> postfix, String var) {
        this.postfix = postfix;
        setVar(var);
    }
    
    public Function(String infix, String var) {
        this(ExpressionHandler.getPostfix(infix), var);
    }
    
    public Function(ArrayDeque<String> postfix) {
        this(postfix, "x");
    }
    
    public Function(String infix) {
        this(infix, "x");
    }
    
    public Function() {
        this("0");
    }

    /**
     * Set new variable of this function
     * @param newVar new variable
     * */
    public void setVar(String newVar) {
        var = newVar;
        reduced.clear();
        for (String token : postfix) {
            if (ExpressionHandler.isVariable(token) && !ExpressionHandler.isNumber(token) &&
                    !token.equals(newVar)) {
                reduced.add("0");
            } else {
                reduced.add(token);
            }
        }
        reduced = (new ASTree(reduced)).reduce().toPostfix();
    }

    /**
     * Calculate function value for certain argument value
     * @param variable argument value
     * @return function value
     * */
    public double calc(double variable) {
        Stack<Double> stack = new Stack<>();
        ArrayDeque<String> reduced = this.reduced.clone();
        
        while (!reduced.isEmpty()) {
            String token = reduced.poll();
            if (ExpressionHandler.isVariable(token)) {
                if (token.equals(var)) {
                    stack.push(variable);
                } else {
                    stack.push(Double.valueOf(token));
                }
            }
            if (ExpressionHandler.isOperator(token)) {
                Double snd = stack.pop();
                Double fst = stack.pop();
                stack.push(ExpressionHandler.calc(token, fst, snd));
            }
            if (ExpressionHandler.isFunction(token)) {
                int params = ExpressionHandler.getNumOfParams(token);
                Double[] children = new Double[params];
                for (int i = params - 1; i >= 0; i--) {
                    children[i] = stack.pop();
                }
                stack.push(ExpressionHandler.calc(token, children));
            }
        }
        return stack.pop();
    }

    /**
     * Take derivative of this function
     * @return new function which is a derivative of this function
     * */
    public Function diff() {
        return new Function((new ASTree(postfix.clone())).diff(var).toPostfix(), var);
    }

    /**
     * Take integral of this function
     * @param a left border of integration
     * @param b right border of integration
     * @return result of integration
     * */
    public double integrate(double a, double b) {
        int n = 50000;
        double h = (b - a) / (2 * n);

        double result = 0.0;
        
        for (int i = 1; i <= n; i++) {
          result += calc(a + h * (2 * i - 1));
        }
        result *= 2;
        
        for (int i = 1; i < n; i++) {
            result += calc(a + h * 2 * i);
        }
        result *= 2; 
        
        result += calc(a) + calc(b);
        result *= h / 3.0;
        
        return result;
    }

    /**
     * Get postfix representation of this function
     * @return postfix
     * */
    public ArrayDeque<String> getPostfix() {
        return postfix.clone();
    }
    
}
