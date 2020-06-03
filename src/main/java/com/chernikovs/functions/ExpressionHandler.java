package com.chernikovs.functions;

import java.util.*;
import java.util.function.Function;

/**A class that processes expressions*/
public class ExpressionHandler {

    /**Supported constants*/
    private static final Map<String, Double> constants = new HashMap<String, Double>() {
        {
            put("pi", Math.PI);
            put("e", Math.E);
            put("phi", 1.618033988749895);
            put("gamma", 0.5772156649015329);
        }
    };
    
    /**
     * Operator attributes
     * The first attribute is priority of the operator. Can be from 0 to 2 inclusive
     * The second attribute is associativity of the operator. Can be either "L" or "R"
     * */
    private static final Map<String, List<String>> opAttribs = new HashMap<String, List<String>>() {
        {
            put("+", new ArrayList<String>() {{add("0"); add("L");}});
            put("-", new ArrayList<String>() {{add("0"); add("L");}});
            put("*", new ArrayList<String>() {{add("1"); add("L");}});
            put("/", new ArrayList<String>() {{add("1"); add("L");}});
            put("^", new ArrayList<String>() {{add("2"); add("R");}});
        }
    };
    
    /**
     * Function attributes
     * The first attribute is number of parameters of the function
     * The second attribute is derivative of the function in infix form
     * */
    private static final Map<String, List<String>> fnAttribs = new HashMap<String, List<String>>() {
        {
            put("neg", new ArrayList<String>() {{add("1"); add("-1");}});
            put("abs", new ArrayList<String>() {{add("1"); add("sgn(x)");}}); //x != 0
            put("sgn", new ArrayList<String>() {{add("1"); add("0");}});
            put("sqrt", new ArrayList<String>() {{add("1"); add("0.5/sqrt(x)");}});
            put("cbrt", new ArrayList<String>() {{add("1"); add("1/3/cbrt(x*x)");}});
            put("exp", new ArrayList<String>() {{add("1"); add("exp(x)");}});
            put("ln", new ArrayList<String>() {{add("1"); add("1/x");}});
            put("log", new ArrayList<String>() {{add("1"); add("1/ln(10)/x");}});
            put("lb", new ArrayList<String>() {{add("1"); add("1/ln(2)/x");}});
            put("min", new ArrayList<String>() {{add("2"); add("0");}});
            put("max", new ArrayList<String>() {{add("2"); add("0");}});
            put("fact", new ArrayList<String>() {{add("1"); add("0");}});
            put("sin", new ArrayList<String>() {{add("1"); add("cos(x)");}});
            put("cos", new ArrayList<String>() {{add("1"); add("-sin(x)");}});
            put("tan", new ArrayList<String>() {{add("1"); add("sec(x)^2");}});
            put("cot", new ArrayList<String>() {{add("1"); add("-csc(x)^2");}});
            put("asin", new ArrayList<String>() {{add("1"); add("1/sqrt(1-x^2)");}});
            put("acos", new ArrayList<String>() {{add("1"); add("-1/sqrt(1-x^2)");}});
            put("atan", new ArrayList<String>() {{add("1"); add("1/(1+x^2)");}});
            put("acot", new ArrayList<String>() {{add("1"); add("-1/(1+x^2)");}});
            put("sind", new ArrayList<String>() {{add("1"); add("cosd(x)*pi/180");}});
            put("cosd", new ArrayList<String>() {{add("1"); add("sind(x)*(-pi/180)");}});
            put("tand", new ArrayList<String>() {{add("1"); add("(1+tand(x)^2)*pi/180)");}});
            put("cotd", new ArrayList<String>() {{add("1"); add("(1+cotd(x)^2)*(-pi/180)");}});
            put("asind", new ArrayList<String>() {{add("1"); add("180/pi/sqrt(1-x^2)");}});
            put("acosd", new ArrayList<String>() {{add("1"); add("-180/pi/sqrt(1-x^2)");}});
            put("atand", new ArrayList<String>() {{add("1"); add("180/pi/(1+x^2)");}});
            put("acotd", new ArrayList<String>() {{add("1"); add("-180/pi/(1+x^2)");}});
            put("sec", new ArrayList<String>() {{add("1"); add("tan(x)*sec(x)");}});
            put("csc", new ArrayList<String>() {{add("1"); add("-cot(x)*csc(x)");}});
            put("asec", new ArrayList<String>() {{add("1"); add("1/(abs(x)*sqrt(x^2-1))");}});
            put("acsc", new ArrayList<String>() {{add("1"); add("-1/(abs(x)*sqrt(x^2-1))");}});
            put("sinh", new ArrayList<String>() {{add("1"); add("cosh(x)");}});
            put("cosh", new ArrayList<String>() {{add("1"); add("sinh(x)");}});
            put("tanh", new ArrayList<String>() {{add("1"); add("sech(x)^2");}});
            put("coth", new ArrayList<String>() {{add("1"); add("-csch(x)^2");}});
            put("sech", new ArrayList<String>() {{add("1"); add("-tanh(x)*sech(x)");}});
            put("csch", new ArrayList<String>() {{add("1"); add("-coth(x)*csch(x)");}});
            put("asinh", new ArrayList<String>() {{add("1"); add("1/sqrt(1+x^2)");}});
            put("acosh", new ArrayList<String>() {{add("1"); add("1/sqrt(x^2-1)");}});
            put("atanh", new ArrayList<String>() {{add("1"); add("1/(1-x^2)");}}); //x <- (-1, 1)
            put("acoth", new ArrayList<String>() {{add("1"); add("1/(1-x^2)");}}); //x <- (-1, 1)
            put("asech", new ArrayList<String>() {{add("1"); add("-1/(x*sqrt(1-x^2))");}}); //x <- (0, 1)
            put("acsch", new ArrayList<String>() {{add("1"); add("-1/(x*sqrt(1+x^2))");}}); //x > 0
        }
    };

    /**Supported functions and operators*/
    private static final Map<String, Function<Double[], Double>> functions = new HashMap<String, Function<Double[], Double>>() {
        {
            put("+", (vars) -> vars[0] + vars[1]);
            put("-", (vars) -> vars[0] - vars[1]);
            put("*", (vars) -> vars[0] * vars[1]);
            put("/", (vars) -> vars[0] / vars[1]);
            put("^", (vars) -> Math.pow(vars[0], vars[1]));
            put("neg", (vars) -> -vars[0]);
            put("abs", (vars) -> Math.abs(vars[0]));
            put("sgn", (vars) -> Math.signum(vars[0]));
            put("sqrt", (vars) -> Math.sqrt(vars[0]));
            put("cbrt", (vars) -> Math.cbrt(vars[0]));
            put("exp", (vars) -> Math.exp(vars[0]));
            put("ln", (vars) -> Math.log(vars[0]));
            put("log", (vars) -> Math.log10(vars[0]));
            put("lb", (vars) -> Math.log(vars[0]) / Math.log(2));
            put("min", (vars) -> Math.min(vars[0], vars[1]));
            put("max", (vars) -> Math.max(vars[0], vars[1]));
            put("fact", (vars) -> (new com.chernikovs.functions.Function("exp(-t)*t^" + vars[0], "t").integrate(0, 575)));
            put("sin", (vars) -> Math.sin(vars[0]));
            put("cos", (vars) -> Math.cos(vars[0]));
            put("tan", (vars) -> Math.tan(vars[0]));
            put("cot", (vars) -> 1.0 / Math.tan(vars[0]));
            put("asin", (vars) -> Math.asin(vars[0]));
            put("acos", (vars) -> Math.acos(vars[0]));
            put("atan", (vars) -> Math.atan(vars[0]));
            put("acot", (vars) -> Math.PI / 2 - Math.atan(vars[0]));
            put("sind", (vars) -> Math.sin(vars[0] * Math.PI / 180.0));
            put("cosd", (vars) -> Math.cos(vars[0] * Math.PI / 180.0));
            put("tand", (vars) -> Math.tan(vars[0] * Math.PI / 180.0));
            put("cotd", (vars) -> 1.0 / Math.tan(vars[0] * Math.PI / 180.0));
            put("asind", (vars) -> Math.asin(vars[0]) * 180.0 / Math.PI);
            put("acosd", (vars) -> Math.acos(vars[0]) * 180.0 / Math.PI);
            put("atand", (vars) -> Math.atan(vars[0]) * 180.0 / Math.PI);
            put("acotd", (vars) -> 90.0 - Math.atan(vars[0]) * 180.0 / Math.PI);
            put("sec", (vars) -> 1.0 / Math.cos(vars[0]));
            put("csc", (vars) -> 1.0 / Math.sin(vars[0]));
            put("asec", (vars) -> Math.acos(1.0 / vars[0]));
            put("acsc", (vars) -> Math.asin(1.0 / vars[0]));
            put("sinh", (vars) -> Math.sinh(vars[0]));
            put("cosh", (vars) -> Math.cosh(vars[0]));
            put("tanh", (vars) -> Math.tanh(vars[0]));
            put("coth", (vars) -> 1.0 / Math.tanh(vars[0]));
            put("sech", (vars) -> 1.0 / Math.cosh(vars[0]));
            put("csch", (vars) -> 1.0 / Math.sinh(vars[0]));
            put("asinh", (vars) -> Math.log(vars[0] + Math.sqrt(vars[0] * vars[0] + 1.0)));
            put("acosh", (vars) -> Math.log(vars[0] + Math.sqrt(vars[0] * vars[0] - 1.0)));
            put("atanh", (vars) -> Math.log((1.0 + vars[0]) / (1.0 - vars[0])) / 2.0);
            put("acoth", (vars) -> Math.log((1.0 + vars[0]) / (vars[0] - 1.0)) / 2.0);
            put("asech", (vars) -> Math.log((1 + Math.sqrt(1.0 - vars[0] * vars[0])) / vars[0]));
            put("acsch", (vars) -> Math.log((1 + Math.signum(vars[0]) * Math.sqrt(1.0 + vars[0] * vars[0])) / vars[0]));
        }
    };

    /**
     * Calculate function value for certain argument values
     * @param fnName string representation of the function
     * @param vars argument values
     * @return function value
     * */
    public static double calc(String fnName, Double ... vars) {
        if (!functions.containsKey(fnName)) {
            return 0.0;
        }
        
        return functions.get(fnName).apply(vars);
    }

    /**
     * Get number of parameters of the function
     * @param function function number of parameters of which is needed to be known
     * @return number of parameters of the received function
     * */
    public static int getNumOfParams(String function) {
        return Integer.parseInt(fnAttribs.get(function).get(0));
    }
    
    private static int getPriority(String operator) {
        return Integer.parseInt(opAttribs.get(operator).get(0));
    }

    /**
     * Get derivative in infix form of the function
     * @param function function derivative of which is needed to be known
     * @return string representation of function derivative in infix form
     * */
    public static String getInfixDerivative(String function) {
        return fnAttribs.get(function).get(1);
    }
    
    private static ArrayDeque<String> removeExponents(ArrayDeque<String> infix) {
        ArrayDeque<String> result = new ArrayDeque<>();
        
        String prev = "_";
        while (!infix.isEmpty()) {
            String token = infix.poll();
            if ((token.equals("-") || token.equals("+")) && prev.charAt(prev.length() - 1) == 'E'
                    && isNumber(prev.substring(0, prev.length() - 1))) {
                String next = infix.poll();
                if (isNumber(next)) {
                    token = prev + token + next;
                    result.pollLast();
                } else {
                    result.add(token);
                    token = next;
                }
            }
            result.add(token);
            prev = token;
        }
        
        return result;
    }

    /**
     * Remove unary minuses from the expression represented by infix form
     * @param infix expression represented by infix form
     * @return expression represented by infix form without unary minuses
     * */
    public static ArrayDeque<String> removeMinuses(ArrayDeque<String> infix) {
        ArrayDeque<String> result = new ArrayDeque<>();
        
        String prev = "(";
        while (!infix.isEmpty()) {
            String token = infix.poll();
            if (token.equals("-") && (prev.equals("(") || prev.equals(",") || isOperator(prev))) {
                result.add("neg");
                result.add("(");
                int bracketsFactor = 0;
                ArrayDeque<String> interior = new ArrayDeque<>();
                while (!infix.isEmpty()) {
                    interior.add(token = infix.poll());
                    if (token.equals("(")) {
                        bracketsFactor++;
                    }
                    if (token.equals(")")) {
                        bracketsFactor--;
                    }
                    if (bracketsFactor == 0) {
                        if (!isOperator(token) && !isFunction(token)) {
                            if (!infix.isEmpty()) {
                                token = infix.poll();
                                if (isOperator(token) && getPriority(token) > getPriority("-")) {
                                    interior.add(token);
                                    continue;
                                } else {
                                    infix.addFirst(token);
                                }
                            }

                            break;
                        }
                    }
                }
                interior = removeMinuses(interior);
                result.addAll(interior);
                result.add(")");
                token = result.peekLast();
            } else {
                result.add(token);
            }
            prev = token;
        }
        
        return result;
    }

    /**
     * Check whether postfix is valid
     * @param postfix postfix to check
     * @return true if received postfix is valid and false otherwise
     * */
    public static boolean isValid(ArrayDeque<String> postfix) {
        if (postfix.isEmpty()) {
            return false;
        }

        try {
            ASTree tree = new ASTree(postfix);
        } catch (Exception exc) {
            return false;
        }

        return true;
    }

    /**
     * Transform infix to postfix
     * @param infix infix to transform
     * @return postfix
     * */
    public static ArrayDeque<String> getPostfix(ArrayDeque<String> infix) {
        ArrayDeque<String> result = new ArrayDeque<>();
        Stack<String> stack = new Stack<>();
        
        infix = removeMinuses(infix);
        infix = substituteConstants(infix);
        
        while (!infix.isEmpty()) {
            String token = infix.poll();
            if (isVariable(token)) {
                result.add(token);
            }
            if (isFunction(token)) {
                stack.push(token);
            }
            if (token.equals(",")) {
                String currToken = "";
                boolean wasEmpty = false;
                while (!(wasEmpty = stack.isEmpty()) && !(currToken = stack.pop()).equals("(")) {
                    result.add(currToken);
                }
                if (!wasEmpty) {
                    stack.push(currToken);
                }
            }
            if (isOperator(token)) {
                String op = "";
                boolean wasEmpty = false;
                while (!(wasEmpty = stack.isEmpty()) && isOperator(op = stack.pop()) &&
                        Integer.parseInt(opAttribs.get(op).get(0)) >= Integer.parseInt(opAttribs.get(token).get(0))) {
                    if (opAttribs.get(token).get(1).equals("R") &&
                            Integer.parseInt(opAttribs.get(op).get(0)) == Integer.parseInt(opAttribs.get(token).get(0))) {
                        break;
                    }
                    result.add(op);
                }
                if (!wasEmpty) {
                    stack.push(op);
                }
                stack.push(token);
            }
            if (token.equals("(")) {
                stack.push(token);
            }
            if (token.equals(")")) {
                String currToken = "";
                while (!stack.isEmpty() && !(currToken = stack.pop()).equals("(")) {
                    result.add(currToken);
                }
                if (!stack.isEmpty()) {
                    if (!isFunction(currToken = stack.pop())) {
                        stack.push(currToken);
                    } else {
                        result.add(currToken);
                    }
                }
            }
        }
        
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        
        return result;
    }

    /**
     * Transform infix to postfix
     * @param infix string representation of infix to transform
     * @return postfix
     * */
    public static ArrayDeque<String> getPostfix(String infix) {
        return getPostfix(getTokens(infix));
    }

    /**
     * Split string representation of infix into tokens
     * @param infix string representation of infix
     * @return infix that consists of tokens
     * */
    public static ArrayDeque<String> getTokens(String infix) {
        ArrayDeque<String> result = new ArrayDeque<>();
        
        infix = infix.replaceAll(" ", "");
        infix = infix.replaceAll("\t", "");
        infix = infix.replaceAll("\n", "");
        infix = infix.replaceAll("\r", "");
        infix = correctBrackets(infix);
        
        while (infix.length() > 0) {
            String token = parseToken(infix);
            infix = infix.substring(token.length());
            result.add(token);
        }

        result = removeExponents(result);

        return result;
    }

    /**
     * Substitue supported constants
     * @param expression expression that contains constants
     * @return new expressions with values instead of string representation of constants
     * */
    public static ArrayDeque<String> substituteConstants(ArrayDeque<String> expression) {
        ArrayDeque<String> result = new ArrayDeque<>();
        for (String token : expression) {
            if (constants.containsKey(token)) {
                token = String.valueOf(constants.get(token));
            }
            result.add(token);
        }
        return result;
    }

    private static String parseToken(String infix) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < infix.length(); i++) {
            String symbol = String.valueOf(infix.charAt(i));

            if (symbol.equals("(") || symbol.equals(",") || symbol.equals(")")) {
                if (result.length() == 0) {
                    return symbol;
                }
                return result.toString();
            }

            result.append(symbol);

            if (isOperator(result.toString())) {
                return result.toString();
            }

            String nextToken = parseToken(infix.substring(result.length()));

            if (isFunction(result.toString()) && nextToken.equals("(")) {
                return result.toString();
            }

            if (!isVariable(nextToken) && !nextToken.equals("(")) {
                return result.toString();
            }
        }

        return result.toString();
    }
    
    private static String correctBrackets(String infix) {
        StringBuilder correctInfix = new StringBuilder(infix);
        int brFactor = bracketsFactor(infix);
        if (brFactor > 0) {
            for (int i = 0; i < brFactor; i++) {
                correctInfix.append(')');
            }
        }
        if (brFactor < 0) {
            for (int i = 0; i > brFactor; i--) {
                correctInfix.insert(0, '(');
            }
        }
        return correctInfix.toString();
    }
    
    private static int bracketsFactor(String infix) {
        int res = 0;
        
        for (int i = 0; i < infix.length(); i++) {
            if (infix.charAt(i) == '(') {
                res++;
            }
            if (infix.charAt(i) == ')') {
                res--;
            }
        }
        
        return res;
    }

    /**
     * Check whether token is an operator
     * @param token token to check
     * @return true if received token is operator and false otherwise
     * */
    public static boolean isOperator(String token) {
        return opAttribs.containsKey(token);
    }

    /**
     * Check whether token is a function
     * @param token token to check
     * @return true if received token is function and false otherwise
     * */
    public static boolean isFunction(String token) {
        return fnAttribs.containsKey(token);
    }

    /**
     * Check whether token is a variable
     * @param token token to check
     * @return true if received token is variable and false otherwise
     * */
    public static boolean isVariable(String token) {
        return !isOperator(token) && !isFunction(token) &&
                !token.equals("(") && !token.equals(",") && !token.equals(")");
    }

    /**
     * Check whether token is a number
     * @param token token to check
     * @return true if received token is number and false otherwise
     * */
    public static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
        } catch (NumberFormatException exc) {
            return false;
        }
        return true;
    }

}
