<<<<<<< HEAD
package com.chernikovs.functions;

import java.util.ArrayDeque;
import java.util.Stack;

/**A class describing two argument function*/
public class Function2 {

    private ArrayDeque<String> postfix = new ArrayDeque<>();
    private ArrayDeque<String> reduced = new ArrayDeque<>();
    private String fstVar;
    private String sndVar;
    
    public Function2(ArrayDeque<String> postfix, String fstVar, String sndVar) {
        this.postfix = postfix;
        setVars(fstVar, sndVar);
    }
    
    public Function2(String infix, String fstVar, String sndVar) {
        this(ExpressionHandler.getPostfix(infix), fstVar, sndVar);
    }
    
    public Function2(ArrayDeque<String> postfix) {
        this(postfix, "x", "y");
    }
    
    public Function2(String infix) {
        this(infix, "x", "y");
    }
    
    public Function2() {
        this("0");
    }

    /**
     * Set new first variable of this function
     * @param newVar new first variable
     * */
    public void setFstVar(String newVar) {
        setVars(newVar, sndVar);
    }

    /**
     * Set new second variable of this function
     * @param newVar new second variable
     * */
    public void setSndVar(String newVar) {
        setVars(fstVar, newVar);
    }

    /**
     * Set new variables of this function
     * @param newFstVar new first variable
     * @param newSndVar new second variable
     * */
    public void setVars(String newFstVar, String newSndVar) {
        fstVar = newFstVar;
        sndVar = newSndVar;
        reduced.clear();
        for (String token : postfix) {
            if (ExpressionHandler.isVariable(token) && !ExpressionHandler.isNumber(token) &&
                    !token.equals(newFstVar) && !token.equals(newSndVar)) {
                reduced.add("0");
            } else {
                reduced.add(token);
            }
        }
        reduced = (new ASTree(reduced)).reduce().toPostfix();
    }

    /**
     * Calculate function value for certain argument values
     * @param fst first argument value
     * @param snd second argument value
     * @return function value
     * */
    public double calc(double fst, double snd) {
        Stack<Double> stack = new Stack<>();
        ArrayDeque<String> reduced = this.reduced.clone();
        
        while (!reduced.isEmpty()) {
            String token = reduced.poll();
            if (ExpressionHandler.isVariable(token)) {
                if (token.equals(fstVar)) {
                    stack.push(fst);
                } else {
                    if (token.equals(sndVar)) {
                        stack.push(snd);
                    } else {
                        stack.push(Double.valueOf(token));                        
                    }
                }
            }
            if (ExpressionHandler.isOperator(token)) {
                Double snd_ = stack.pop();
                Double fst_ = stack.pop();
                stack.push(ExpressionHandler.calc(token, fst_, snd_));
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
     * Calculate function value for certain first argument value
     * @param fst first argument value
     * @return one argument function
     * */
    public Function calcByFst(double fst) {
        ArrayDeque<String> newPostfix = new ArrayDeque<>();
        for (String token : postfix) {
            if (token.equals(fstVar)) {
                newPostfix.add(String.valueOf(fst));
            } else {
                newPostfix.add(token);
            }
        }
        return new Function(newPostfix, sndVar);
    }

    /**
     * Calculate function value for certain second argument value
     * @param snd second argument value
     * @return one argument function
     * */
    public Function calcBySnd(double snd) {
        ArrayDeque<String> newPostfix = new ArrayDeque<>();
        for (String token : postfix) {
            if (token.equals(sndVar)) {
                newPostfix.add(String.valueOf(snd));
            } else {
                newPostfix.add(token);
            }
        }
        return new Function(newPostfix, fstVar);
    }

    /**
     * Take derivative of this function by the first argument
     * @return new function which is a derivative of this function by the first argument
     * */
    public Function2 diffByFst() {
        return new Function2((new Function(postfix.clone(), fstVar)).diff().getPostfix(), fstVar, sndVar);
    }

    /**
     * Take derivative of this function by the second argument
     * @return new function which is a derivative of this function by the second argument
     * */
    public Function2 diffBySnd() {
        return new Function2((new Function(postfix.clone(), sndVar)).diff().getPostfix(), fstVar, sndVar);
    }

    /**
     * Get postfix representation of this function
     * @return postfix
     * */
    public ArrayDeque<String> getPostfix() {
        return postfix.clone();
    }

    /**
     * Take double integral of this function
     * @param a1 left border of integration by the first argument
     * @param b1 right border of integration by the first argument
     * @param a2 left border of integration by the second argument
     * @param b2 right border of integration by the second argument
     * @return result of double integration
     * */
    public double doubleIntegrate(double a1, double b1, double a2, double b2) {
        int n = 500;
        double h = (b1 - a1) / (2 * n);

        ArrayDeque<String> resultEq = new ArrayDeque<>();
        resultEq.add("0");

        for (int i = 1; i <= n; i++) {
            resultEq.addAll(calcByFst(a1 + h * (2 * i - 1)).getPostfix());
            resultEq.add("+");
        }
        resultEq.add("2");
        resultEq.add("*");

        for (int i = 1; i < n; i++) {
            resultEq.addAll(calcByFst(a1 + h * 2 * i).getPostfix());
            resultEq.add("+");
        }
        resultEq.add("2");
        resultEq.add("*");

        resultEq.addAll(calcByFst(a1).getPostfix());
        resultEq.add("+");
        resultEq.addAll(calcByFst(b1).getPostfix());
        resultEq.add("+");
        resultEq.add(String.valueOf(h));
        resultEq.add("*");
        resultEq.add(String.valueOf(3.0f));
        resultEq.add("/");

        return new Function(resultEq, sndVar).integrate(a2, b2);
    }
    
}
=======
package com.chernikovs.functions;

import java.util.ArrayDeque;
import java.util.Stack;

/**A class describing two argument function*/
public class Function2 {

    private ArrayDeque<String> postfix = new ArrayDeque<>();
    private ArrayDeque<String> reduced = new ArrayDeque<>();
    private String fstVar;
    private String sndVar;
    
    public Function2(ArrayDeque<String> postfix, String fstVar, String sndVar) {
        this.postfix = postfix;
        setVars(fstVar, sndVar);
    }
    
    public Function2(String infix, String fstVar, String sndVar) {
        this(ExpressionHandler.getPostfix(infix), fstVar, sndVar);
    }
    
    public Function2(ArrayDeque<String> postfix) {
        this(postfix, "x", "y");
    }
    
    public Function2(String infix) {
        this(infix, "x", "y");
    }
    
    public Function2() {
        this("0");
    }

    /**
     * Set new first variable of this function
     * @param newVar new first variable
     * */
    public void setFstVar(String newVar) {
        setVars(newVar, sndVar);
    }

    /**
     * Set new second variable of this function
     * @param newVar new second variable
     * */
    public void setSndVar(String newVar) {
        setVars(fstVar, newVar);
    }

    /**
     * Set new variables of this function
     * @param newFstVar new first variable
     * @param newSndVar new second variable
     * */
    public void setVars(String newFstVar, String newSndVar) {
        fstVar = newFstVar;
        sndVar = newSndVar;
        reduced.clear();
        for (String token : postfix) {
            if (ExpressionHandler.isVariable(token) && !ExpressionHandler.isNumber(token) &&
                    !token.equals(newFstVar) && !token.equals(newSndVar)) {
                reduced.add("0");
            } else {
                reduced.add(token);
            }
        }
        reduced = (new ASTree(reduced)).reduce().toPostfix();
    }

    /**
     * Calculate function value for certain argument values
     * @param fst first argument value
     * @param snd second argument value
     * @return function value
     * */
    public double calc(double fst, double snd) {
        Stack<Double> stack = new Stack<>();
        ArrayDeque<String> reduced = this.reduced.clone();
        
        while (!reduced.isEmpty()) {
            String token = reduced.poll();
            if (ExpressionHandler.isVariable(token)) {
                if (token.equals(fstVar)) {
                    stack.push(fst);
                } else {
                    if (token.equals(sndVar)) {
                        stack.push(snd);
                    } else {
                        stack.push(Double.valueOf(token));                        
                    }
                }
            }
            if (ExpressionHandler.isOperator(token)) {
                Double snd_ = stack.pop();
                Double fst_ = stack.pop();
                stack.push(ExpressionHandler.calc(token, fst_, snd_));
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
     * Calculate function value for certain first argument value
     * @param fst first argument value
     * @return one argument function
     * */
    public Function calcByFst(double fst) {
        ArrayDeque<String> newPostfix = new ArrayDeque<>();
        for (String token : postfix) {
            if (token.equals(fstVar)) {
                newPostfix.add(String.valueOf(fst));
            } else {
                newPostfix.add(token);
            }
        }
        return new Function(newPostfix, sndVar);
    }

    /**
     * Calculate function value for certain second argument value
     * @param snd second argument value
     * @return one argument function
     * */
    public Function calcBySnd(double snd) {
        ArrayDeque<String> newPostfix = new ArrayDeque<>();
        for (String token : postfix) {
            if (token.equals(sndVar)) {
                newPostfix.add(String.valueOf(snd));
            } else {
                newPostfix.add(token);
            }
        }
        return new Function(newPostfix, fstVar);
    }

    /**
     * Take derivative of this function by the first argument
     * @return new function which is a derivative of this function by the first argument
     * */
    public Function2 diffByFst() {
        return new Function2((new Function(postfix.clone(), fstVar)).diff().getPostfix(), fstVar, sndVar);
    }

    /**
     * Take derivative of this function by the second argument
     * @return new function which is a derivative of this function by the second argument
     * */
    public Function2 diffBySnd() {
        return new Function2((new Function(postfix.clone(), sndVar)).diff().getPostfix(), fstVar, sndVar);
    }

    /**
     * Get postfix representation of this function
     * @return postfix
     * */
    public ArrayDeque<String> getPostfix() {
        return postfix.clone();
    }

    /**
     * Take double integral of this function
     * @param a1 left border of integration by the first argument
     * @param b1 right border of integration by the first argument
     * @param a2 left border of integration by the second argument
     * @param b2 right border of integration by the second argument
     * @return result of double integration
     * */
    public double doubleIntegrate(double a1, double b1, double a2, double b2) {
        int n = 500;
        double h = (b1 - a1) / (2 * n);

        ArrayDeque<String> resultEq = new ArrayDeque<>();
        resultEq.add("0");

        for (int i = 1; i <= n; i++) {
            resultEq.addAll(calcByFst(a1 + h * (2 * i - 1)).getPostfix());
            resultEq.add("+");
        }
        resultEq.add("2");
        resultEq.add("*");

        for (int i = 1; i < n; i++) {
            resultEq.addAll(calcByFst(a1 + h * 2 * i).getPostfix());
            resultEq.add("+");
        }
        resultEq.add("2");
        resultEq.add("*");

        resultEq.addAll(calcByFst(a1).getPostfix());
        resultEq.add("+");
        resultEq.addAll(calcByFst(b1).getPostfix());
        resultEq.add("+");
        resultEq.add(String.valueOf(h));
        resultEq.add("*");
        resultEq.add(String.valueOf(3.0f));
        resultEq.add("/");

        return new Function(resultEq, sndVar).integrate(a2, b2);
    }
    
}
>>>>>>> 4f3c886f3ff1ae2c84328c3abe8a64dfc85d4b78
