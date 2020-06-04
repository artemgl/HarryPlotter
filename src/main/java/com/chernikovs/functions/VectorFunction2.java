package com.chernikovs.functions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**A class describing two argument vector function*/
public class VectorFunction2 {
    
    private Function2[] fns = new Function2[3];
    private String fstVar;
    private String sndVar;
    
    public VectorFunction2(List<ArrayDeque<String>> postfixes, String fstVar, String sndVar) {
        for (int i = 0; i < fns.length; i++) {
            fns[i] = new Function2(postfixes.get(i), fstVar, sndVar);
        }
        this.fstVar = fstVar;
        this.sndVar = sndVar;
    }
    
    public VectorFunction2(String[] infixes, String fstVar, String sndVar) {
        ArrayList<ArrayDeque<String>> postfixes = new ArrayList<>();
        for (String str : infixes) {
            postfixes.add(ExpressionHandler.getPostfix(str));
        }
        for (int i = 0; i < fns.length; i++) {
            fns[i] = new Function2(postfixes.get(i), fstVar, sndVar);
        }
        this.fstVar = fstVar;
        this.sndVar = sndVar;
    }
    
    public VectorFunction2(List<ArrayDeque<String>> postfixes) {
        this(postfixes, "u", "v");
    }
    
    public VectorFunction2(String[] infixes) {
        this(infixes, "u", "v");
    }
    
    public VectorFunction2() {
        this(new String[] {"0", "0", "0"});
    }
    
    public VectorFunction2(Function2[] fns, String fstVar, String sndVar) {
        this.fns = fns;
        this.fstVar = fstVar;
        this.sndVar = sndVar;
    }

    /**
     * Set new first variable of this vector function
     * @param newVar new first variable
     * */
    public void setFstVar(String newVar) {
        fstVar = newVar;
        for (int i = 0; i < fns.length; i++) {
            fns[i].setFstVar(newVar);
        }
    }

    /**
     * Set new second variable of this vector function
     * @param newVar new second variable
     * */
    public void setSndVar(String newVar) {
        sndVar = newVar;
        for (int i = 0; i < fns.length; i++) {
            fns[i].setSndVar(newVar);
        }
    }

    /**
     * Set new variables of this vector function
     * @param newFstVar new first variable
     * @param newSndVar new second variable
     * */
    public void setVars(String newFstVar, String newSndVar) {
        fstVar = newFstVar;
        sndVar = newSndVar;
        for (int i = 0; i < fns.length; i++) {
            fns[i].setVars(newFstVar, newSndVar);
        }
    }

    /**
     * Calculate vector function value for certain argument values
     * @param fst first argument value
     * @param snd second argument value
     * @return vector function value
     * */
    public double[] calc(double fst, double snd) {
        double[] result = new double[3];
        for (int i = 0; i < result.length; i++) {
            result[i] = fns[i].calc(fst, snd);
        }
        return result;
    }

    /**
     * Calculate vector function value for certain first argument value
     * @param fst first argument value
     * @return one argument vector function
     * */
    public VectorFunction calcByFst(double fst) {
        Function[] result = new Function[3];
        for (int i = 0; i < result.length; i++) {
            result[i] = fns[i].calcByFst(fst);
        }
        return new VectorFunction(result, sndVar);
    }

    /**
     * Calculate vector function value for certain second argument value
     * @param snd second argument value
     * @return one argument vector function
     * */
    public VectorFunction calcBySnd(double snd) {
        Function[] result = new Function[3];
        for (int i = 0; i < result.length; i++) {
            result[i] = fns[i].calcBySnd(snd);
        }
        return new VectorFunction(result, fstVar);
    }

    /**
     * Take derivative of this vector function by the first argument
     * @return new vector function which is a derivative of this vector function by the first argument
     * */
    public VectorFunction2 diffByFst() {
        Function2[] newFns = new Function2[3];
        for (int i = 0; i < fns.length; i++) {
            newFns[i] = fns[i].diffByFst();
        }
        return new VectorFunction2(newFns, fstVar, sndVar);
    }

    /**
     * Take derivative of this vector function by the second argument
     * @return new vector function which is a derivative of this vector function by the second argument
     * */
    public VectorFunction2 diffBySnd() {
        Function2[] newFns = new Function2[3];
        for (int i = 0; i < fns.length; i++) {
            newFns[i] = fns[i].diffBySnd();
        }
        return new VectorFunction2(newFns, fstVar, sndVar);
    }

    /**
     * Get the length of this vector function
     * @return function which returns the length of this vector function
     * */
    public Function2 abs() {
        ASTree resTree = new ASTree("sqrt(x^2+y^2+z^2)").substitute(new String[] {"x", "y", "z"},
                new ASTree(fns[0].getPostfix()), new ASTree(fns[1].getPostfix()), new ASTree(fns[2].getPostfix()));
        return new Function2(resTree.reduce().toPostfix(), fstVar, sndVar);
    }

    /**
     * Get this vector function but with unit length
     * @return new vector function with unit length
     * */
    public VectorFunction2 normalize() {
        Function2 abs = abs();
        ArrayList<ArrayDeque<String>> list = new ArrayList<>();
        ASTree arg1Tree = new ASTree("x/n").substitute(new String[] {"x", "n"}, new ASTree(fns[0].getPostfix()), new ASTree(abs.getPostfix()));
        ASTree arg2Tree = new ASTree("y/n").substitute(new String[] {"y", "n"}, new ASTree(fns[1].getPostfix()), new ASTree(abs.getPostfix()));
        ASTree arg3Tree = new ASTree("z/n").substitute(new String[] {"z", "n"}, new ASTree(fns[2].getPostfix()), new ASTree(abs.getPostfix()));
        list.add(arg1Tree.reduce().toPostfix());
        list.add(arg2Tree.reduce().toPostfix());
        list.add(arg3Tree.reduce().toPostfix());
        return new VectorFunction2(list, fstVar, sndVar);
    }

    /**
     * Get normal to ths vector function
     * @return function which returns normal to this vector function
     * */
    public VectorFunction2 getNormal() {
        return diffByFst().cross(diffBySnd());
    }

    /**
     * Take cross product of this vector function and received one
     * @param f the second vector function to take cross product
     * @return result of cross product
     * */
    public VectorFunction2 cross(VectorFunction2 f) {
        Function2[] fns2 = f.getFns();
        ASTree x1 = new ASTree(fns[0].getPostfix());
        ASTree y1 = new ASTree(fns[1].getPostfix());
        ASTree z1 = new ASTree(fns[2].getPostfix());
        ASTree x2 = new ASTree(fns2[0].getPostfix());
        ASTree y2 = new ASTree(fns2[1].getPostfix());
        ASTree z2 = new ASTree(fns2[2].getPostfix());

        ASTree arg1 = new ASTree("y1*z2-y2*z1").substitute(new String[] {"y1", "y2", "z1", "z2"}, y1, y2, z1, z2);
        ASTree arg2 = new ASTree("x2*z1-x1*z2").substitute(new String[] {"x1", "x2", "z1", "z2"}, x1, x2, z1, z2);
        ASTree arg3 = new ASTree("x1*y2-x2*y1").substitute(new String[] {"x1", "x2", "y1", "y2"}, x1, x2, y1, y2);

        Function2 x = new Function2(arg1.reduce().toPostfix(), fstVar, sndVar);
        Function2 y = new Function2(arg2.reduce().toPostfix(), fstVar, sndVar);
        Function2 z = new Function2(arg3.reduce().toPostfix(), fstVar, sndVar);

        return new VectorFunction2(new Function2[] {x, y, z}, fstVar, sndVar);
    }

    /**
     * Take dot product of this vector function and received one
     * @param f the second vector function to take dot product
     * @return result of dot product
     * */
    public Function2 dot(VectorFunction2 f) {
        Function2[] fns2 = f.getFns();
        ASTree x1 = new ASTree(fns[0].getPostfix());
        ASTree y1 = new ASTree(fns[1].getPostfix());
        ASTree z1 = new ASTree(fns[2].getPostfix());
        ASTree x2 = new ASTree(fns2[0].getPostfix());
        ASTree y2 = new ASTree(fns2[1].getPostfix());
        ASTree z2 = new ASTree(fns2[2].getPostfix());

        ASTree arg = (new ASTree("x1*x2+y1*y2+z1*z2")).substitute(new String[] {"x1", "x2", "y1", "y2", "z1", "z2"}, x1, x2, y1, y2, z1, z2);

        return new Function2(arg.reduce().toPostfix(), fstVar, sndVar);
    }

    /**
     * Split vector function into functions
     * @return array of functions that make up this vector function
     * */
    public Function2[] getFns() {
        return this.fns;
    }

    /**
     * Get the first variable of this vector function
     * @return the first variable
     * */
    public String getFstVar() {
        return fstVar;
    }

    /**
     * Get the second variable of this vector function
     * @return the second variable
     * */
    public String getSndVar() {
        return sndVar;
    }
    
}
