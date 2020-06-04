package com.chernikovs.functions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**A class describing one argument vector function*/
public class VectorFunction {

    private Function[] fns = new Function[3];
    private String var;
    
    public VectorFunction(List<ArrayDeque<String>> postfixes, String var) {
        for (int i = 0; i < fns.length; i++) {
            fns[i] = new Function(postfixes.get(i), var);
        }
        this.var = var;
    }
    
    public VectorFunction(String[] infixes, String var) {
        ArrayList<ArrayDeque<String>> postfixes = new ArrayList<>();
        for (String str : infixes) {
            postfixes.add(ExpressionHandler.getPostfix(str));
        }
        for (int i = 0; i < fns.length; i++) {
            fns[i] = new Function(postfixes.get(i), var);
        }
        this.var = var;
    }
    
    public VectorFunction(List<ArrayDeque<String>> postfixes) {
        this(postfixes, "t");
    }
    
    public VectorFunction(String[] infixes) {
        this(infixes, "t");
    }
    
    public VectorFunction() {
        this(new String[] {"0", "0", "0"});
    }
    
    public VectorFunction(Function[] fns, String var) {
        this.fns = fns;
        this.var = var;
    }

    /**
     * Set new variable of this vector function
     * @param newVar new variable
     * */
    public void setVar(String newVar) {
        var = newVar;
        for (int i = 0; i < fns.length; i++) {
            fns[i].setVar(newVar);
        }
    }

    /**
     * Calculate vector function value for certain argument value
     * @param variable argument value
     * @return vector function value
     * */
    public double[] calc(double variable) {
        double[] result = new double[3];
        for (int i = 0; i < result.length; i++) {
            result[i] = fns[i].calc(variable);
        }
        return result;
    }

    /**
     * Take derivative of this vector function
     * @return new vector function which is a derivative of this vector function
     * */
    public VectorFunction diff() {
        Function[] newFns = new Function[3];
        for (int i = 0; i < newFns.length; i++) {
            newFns[i] = fns[i].diff();
        }
        return new VectorFunction(newFns, var);
    }

    /**
     * Get the length of this vector function
     * @return function which returns the length of this vector function
     * */
    public Function abs() {
        ASTree resTree = new ASTree("sqrt(x^2+y^2+z^2)").substitute(new String[] {"x", "y", "z"},
                new ASTree(fns[0].getPostfix()), new ASTree(fns[1].getPostfix()), new ASTree(fns[2].getPostfix()));
        return new Function(resTree.reduce().toPostfix(), var);
    }

    /**
     * Get this vector function but with unit length
     * @return new vector function with unit length
     * */
    public VectorFunction normalize() {
        Function abs = abs();
        ArrayList<ArrayDeque<String>> list = new ArrayList<>();
        ASTree arg1Tree = new ASTree("x/n").substitute(new String[] {"x", "n"}, new ASTree(fns[0].getPostfix()), new ASTree(abs.getPostfix()));
        ASTree arg2Tree = new ASTree("y/n").substitute(new String[] {"y", "n"}, new ASTree(fns[1].getPostfix()), new ASTree(abs.getPostfix()));
        ASTree arg3Tree = new ASTree("z/n").substitute(new String[] {"z", "n"}, new ASTree(fns[2].getPostfix()), new ASTree(abs.getPostfix()));
        list.add(arg1Tree.reduce().toPostfix());
        list.add(arg2Tree.reduce().toPostfix());
        list.add(arg3Tree.reduce().toPostfix());
        return new VectorFunction(list, var);
    }

    /**
     * Split vector function into functions
     * @return array of functions that make up this vector function
     * */
    public Function[] getFns() {
        return this.fns;
    }

    /**
     * Take cross product of this vector function and received one
     * @param f the second vector function to take cross product
     * @return result of cross product
     * */
    public VectorFunction cross(VectorFunction f) {
        Function[] fns2 = f.getFns();
        ASTree x1 = new ASTree(fns[0].getPostfix());
        ASTree y1 = new ASTree(fns[1].getPostfix());
        ASTree z1 = new ASTree(fns[2].getPostfix());
        ASTree x2 = new ASTree(fns2[0].getPostfix());
        ASTree y2 = new ASTree(fns2[1].getPostfix());
        ASTree z2 = new ASTree(fns2[2].getPostfix());

        ASTree arg1 = (new ASTree("y1*z2-y2*z1")).substitute(new String[] {"y1", "y2", "z1", "z2"}, y1, y2, z1, z2);
        ASTree arg2 = (new ASTree("x2*z1-x1*z2")).substitute(new String[] {"x1", "x2", "z1", "z2"}, x1, x2, z1, z2);
        ASTree arg3 = (new ASTree("x1*y2-x2*y1")).substitute(new String[] {"x1", "x2", "y1", "y2"}, x1, x2, y1, y2);

        Function x = new Function(arg1.reduce().toPostfix(), var);
        Function y = new Function(arg2.reduce().toPostfix(), var);
        Function z = new Function(arg3.reduce().toPostfix(), var);

        return new VectorFunction(new Function[] {x, y, z}, var);
    }

    /**
     * Take dot product of this vector function and received one
     * @param f the second vector function to take dot product
     * @return result of dot product
     * */
    public Function dot(VectorFunction f) {
        Function[] fns2 = f.getFns();
        ASTree x1 = new ASTree(fns[0].getPostfix());
        ASTree y1 = new ASTree(fns[1].getPostfix());
        ASTree z1 = new ASTree(fns[2].getPostfix());
        ASTree x2 = new ASTree(fns2[0].getPostfix());
        ASTree y2 = new ASTree(fns2[1].getPostfix());
        ASTree z2 = new ASTree(fns2[2].getPostfix());

        ASTree arg = (new ASTree("x1*x2+y1*y2+z1*z2")).substitute(new String[] {"x1", "x2", "y1", "y2", "z1", "z2"}, x1, x2, y1, y2, z1, z2);

        return new Function(arg.reduce().toPostfix(), var);
    }
    
}
