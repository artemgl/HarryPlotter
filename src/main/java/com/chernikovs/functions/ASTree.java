package com.chernikovs.functions;

import java.util.ArrayDeque;
import java.util.Stack;

/**A class that represents abstract syntax tree*/
public class ASTree implements Cloneable {
    
    private Node root;
    
    private ASTree(Node root) {
        this.root = root;
    }
    
    public ASTree(ArrayDeque<String> postfix) {
        build(postfix);
    }
    
    public ASTree(String infix) {
        this(ExpressionHandler.getPostfix(infix));
    }
    
    public ASTree() {
        this("0");
    }

    /**
     * Check whether this tree is equal to received one
     * @param tree the second tree to check equality
     * @return true if they are equal and false otherwise
     * */
    public boolean equals(ASTree tree) {
        return root.equals(tree.root);
    }

    /**
     * Build tree by postfix
     * @param postfix the postfix form of expression
     * */
    public void build(ArrayDeque<String> postfix) {
        Stack<Node> stack = new Stack<>();
        while (!postfix.isEmpty()) {
            String token = postfix.poll();
            if (ExpressionHandler.isVariable(token)) {
                stack.push(new Leaf(token));
            }
            if (ExpressionHandler.isOperator(token)) {
                Node snd = stack.pop();
                Node fst = stack.pop();
                stack.push(new Branch(token, fst, snd));
            }
            if (ExpressionHandler.isFunction(token)) {
                int params = ExpressionHandler.getNumOfParams(token);
                Node[] children = new Node[params];
                for (int i = params - 1; i >= 0; i--) {
                    children[i] = stack.pop();
                }
                stack.push(new Branch(token, children));
            }
        }
        root = stack.pop();
    }

    /**
     * Simplify this tree
     * @return the simplified tree
     * */
    public ASTree reduce() {
        return new ASTree(root.reduce());
    }

    /**
     * Substitute new tree instead of certain variable
     * @param var variable to replace
     * @param tree tree to replace with
     * @return new tree with the substituted tree instead of the variable
     * */
    public ASTree substitute(String var, ASTree tree) {
        return new ASTree(root.substitute(var, tree));
    }

    /**
     * Substitute new trees instead of certain variables
     * @param vars variables to replace
     * @param trees trees to replace with
     * @return new tree with the substituted trees instead of the variables
     * */
    public ASTree substitute(String[] vars, ASTree ... trees) {
        return new ASTree(root.substitute(vars, trees));
    }

    /**
     * Take derivative of the expression represented as tree
     * @param var variable to differentiate by
     * @return new tree that represents derivative of expression represented as this tree
     * */
    public ASTree diff(String var) {
        return (new ASTree(root.diff(var))).reduce();
    }

    /**
     * Print this tree
     * */
    public void print() {
        root.print();
    }

    /**
     * Print this tree and go to a new line
     * */
    public void println() {
        print();
        System.out.println();
    }

    /**
     * Transform this tree to postfix
     * @return postfix
     * */
    public ArrayDeque<String> toPostfix() {
        return root.toPostfix();
    }

    /**
     * Get copy of this tree
     * @return copy of this tree
     * */
    public ASTree clone() {
        return new ASTree(root.clone());
    }
    
    /**An interface describing node of the syntax tree*/
    private abstract class Node {
        /**
         * A method returns meaning of this node
         * @return meaning of this node
         * */
        public abstract String get();

        /**
         * Get string representation of this node
         * @return string representation of this node
         * */
        public abstract String toString();
        
        /**A method displays tree which root is this node*/
        public void print() {
            System.out.print(toString());
        }

        /**
         * Simplify the tree which root is this node
         * @return root of the simplified tree
         * */
        public abstract Node reduce();

        /**
         * Get copy of this node
         * @return copy of this node
         * */
        public abstract Node clone();

        /**
         * Check whether this node is equal to received one
         * @param node the second node to check equality
         * @return true if they are equal and false otherwise
         * */
        public abstract boolean equals(Node node);

        /**
         * Transform this node to postfix
         * @return postfix
         * */
        public abstract ArrayDeque<String> toPostfix();

        /**
         * Substitute new tree instead of certain variable
         * @param var variable to replace
         * @param tree tree to replace with
         * @return root of the new tree with the substituted tree instead of the variable
         * */
        public abstract Node substitute(String var, ASTree tree);

        /**
         * Substitute new trees instead of certain variables
         * @param vars variables to replace
         * @param trees trees to replace with
         * @return root of the new tree with the substituted trees instead of the variables
         * */
        public abstract Node substitute(String[] vars, ASTree ... trees);

        /**
         * Take derivative of the expression represented as tree which root is this node
         * @param var variable to differentiate by
         * @return root of the new tree that represents derivative of expression represented
         * as the tree which root is this node
         * */
        public abstract Node diff(String var);
        
    }

    /**A class describing operand of the syntax tree*/
    private class Leaf extends Node implements Cloneable {

        private String value;
        
        private Leaf(String value) {
            this.value = value;
        }

        /**
         * A method returns meaning of this leaf
         * @return meaning of this leaf
         * */
        @Override
        public String get() {
            return value;
        }

        /**
         * Simplify the tree which root is this leaf
         * @return root of the simplified tree
         * */
        @Override
        public Node reduce() {
            return this.clone();
        }

        /**
         * Get copy of this leaf
         * @return copy of this leaf
         * */
        @Override
        public Leaf clone() {
            return new Leaf(value);
        }

        /**
         * Get string representation of this leaf
         * @return string representation of this leaf
         * */
        @Override
        public String toString() {
            return value;
        }

        /**
         * Check whether this leaf is equal to received node
         * @param node the second node to check equality
         * @return true if they are equal and false otherwise
         * */
        @Override
        public boolean equals(Node node) {
            Leaf leaf = null;
            try {
                leaf = (Leaf)node;                
            } catch (ClassCastException exc) {
                return false;
            }
            
            return value.equals(leaf.value);
        }

        /**
         * Transform this leaf to postfix
         * @return postfix
         * */
        public ArrayDeque<String> toPostfix() {
            return new ArrayDeque<String>() {
                {
                    add(value);
                }
            };
        }

        /**
         * {@inheritDoc}
         * */
        @Override
        public Node substitute(String var, ASTree tree) {
            if (value.equals(var)) {
                return tree.root.clone();
            }
            return this.clone();
        }

        /**
         * {@inheritDoc}
         * */
        @Override
        public Node substitute(String[] vars, ASTree ... trees) {
            for (int i = 0; i < vars.length; i++) {
                if (value.equals(vars[i])) {
                    return trees[i].root.clone();
                }
            }
            return this.clone();
        }

        /**
         * Take derivative of the expression represented as tree which root is this leaf
         * @param var variable to differentiate by
         * @return root of the new tree that represents derivative of expression represented
         * as the tree which root is this leaf
         * */
        public Node diff(String var) {
            if (value.equals(var)) { 
                return new Leaf("1");
            }
            return new Leaf("0");
        }
        
    }

    /**A class describing operator of the syntax tree*/
    private class Branch extends Node implements Cloneable {

        private String operation;
        private Node[] children;
        
        private Branch(String operation, Node ... children) {
            this.operation = operation;
            this.children = children;
        }

        /**
         * A method returns meaning of this branch
         * @return meaning of this branch
         * */
        @Override
        public String get() {
            return operation;
        }

        /**
         * Simplify the tree which root is this branch
         * @return root of the simplified tree
         * */
        @Override
        public Node reduce() {
            Node[] children = new Node[this.children.length];
            
            for (int i = 0; i < children.length; i++) {
                children[i] = this.children[i].reduce();
            }
            
            boolean areNumbers = true;
            for (Node child : children) {
                if (!ExpressionHandler.isNumber(child.get())) {
                    areNumbers = false;
                    break;
                }
            }            
            
            if (areNumbers) {
                Double[] vars = new Double[children.length];
                for (int i = 0; i < children.length; i++) {
                    vars[i] = Double.valueOf(children[i].get());
                }
                return new Leaf(String.valueOf(ExpressionHandler.calc(operation, vars)));                
            }
            
            switch (operation) {
                case "+":
                    if (ExpressionHandler.isNumber(children[0].get()) && Double.parseDouble(children[0].get()) == 0) {
                        return children[1];
                    }
                    if (ExpressionHandler.isNumber(children[1].get()) && Double.parseDouble(children[1].get()) == 0) {
                        return children[0];
                    }
                    break;
                case "-":
                    if (ExpressionHandler.isNumber(children[0].get()) && Double.parseDouble(children[0].get()) == 0) {
                        return new Branch("neg", children[1]);
                    }
                    if (ExpressionHandler.isNumber(children[1].get()) && Double.parseDouble(children[1].get()) == 0) {
                        return children[0];
                    }
                    break;
                case "*":
                    if (ExpressionHandler.isNumber(children[0].get()) && Double.parseDouble(children[0].get()) == 1) {
                        return children[1];
                    }
                    if (ExpressionHandler.isNumber(children[1].get()) && Double.parseDouble(children[1].get()) == 1) {
                        return children[0];
                    }
                    if (ExpressionHandler.isNumber(children[0].get()) && Double.parseDouble(children[0].get()) == 0) {
                        return new Leaf(String.valueOf(0));
                    }
                    if (ExpressionHandler.isNumber(children[1].get()) && Double.parseDouble(children[1].get()) == 0) {
                        return new Leaf(String.valueOf(0));
                    }
                    break;
                case "/":
                    if (ExpressionHandler.isNumber(children[0].get()) && Double.parseDouble(children[0].get()) == 0) {
                        return new Leaf(String.valueOf(0));
                    }
                    if (ExpressionHandler.isNumber(children[1].get()) && Double.parseDouble(children[1].get()) == 1) {
                        return children[0];
                    }
                    break;
                case "^":
                    if (ExpressionHandler.isNumber(children[0].get()) && Double.parseDouble(children[0].get()) == 1) {
                        return new Leaf("1");
                    }
                    if (ExpressionHandler.isNumber(children[1].get()) && Double.parseDouble(children[1].get()) == 0) {
                        return new Leaf("1");
                    }
                    if (ExpressionHandler.isNumber(children[1].get()) && Double.parseDouble(children[1].get()) == 1) {
                        return children[0];
                    }
                    if (ExpressionHandler.isNumber(children[1].get()) && Double.parseDouble(children[1].get()) % 2 == 1
                            && children[0].get().equals("neg")) {
                        return new Branch("^", ((Branch)children[0]).children[0], children[1]);
                    }
                    break;
            }
            
            return new Branch(operation, children);
        }

        /**
         * Get copy of this branch
         * @return copy of this branch
         * */
        @Override
        public Branch clone() {
            Node[] resChildren = new Node[children.length];
            for (int i = 0; i < children.length; i++) {
                resChildren[i] = children[i].clone();
            }
            return new Branch(operation, resChildren);
        }

        /**
         * Get string representation of this branch
         * @return string representation of this branch
         * */
        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append('(').append(operation).append(' ');
            
            for (int i = 0; i < children.length - 1; i++) {
                result.append(children[i].toString()).append(' ');
            }
            result.append(children[children.length - 1].toString()).append(')');
            return result.toString();
        }

        /**
         * Check whether this branch is equal to received node
         * @param node the second node to check equality
         * @return true if they are equal and false otherwise
         * */
        @Override
        public boolean equals(Node node) {
            Branch branch = null;
            try {
                branch = (Branch)node;
            } catch (ClassCastException exc) {
                return false;
            }
            if (children.length != branch.children.length) {
                return false;
            }
            for (int i = 0; i < children.length; i++) { 
                if (!children[i].equals(branch.children[i])) {
                    return false;
                }
            }
            return operation.equals(branch.operation);
        }

        /**
         * Transform this branch to postfix
         * @return postfix
         * */
        @Override
        public ArrayDeque<String> toPostfix() {
            ArrayDeque<String> result = new ArrayDeque<>();
            
            for (Node child : children) {
                result.addAll(child.toPostfix());
            }
            result.add(operation);
            
            return result;
        }

        /**
         * {@inheritDoc}
         * */
        @Override
        public Node substitute(String var, ASTree tree) {
            Node[] newChildren = new Node[children.length];
            for (int i = 0; i < children.length; i++) {
                newChildren[i] = children[i].substitute(var, tree);
            }
            return new Branch(operation, newChildren);
        }

        /**
         * {@inheritDoc}
         * */
        @Override
        public Node substitute(String[] vars, ASTree ... trees) {
            Node[] newChildren = new Node[children.length];
            for (int i = 0; i < vars.length; i++) {
                for (int j = 0; j < children.length; j++) {
                    newChildren[j] = children[j].substitute(vars, trees);
                }
            }
            return new Branch(operation, newChildren);
        }

        /**
         * Take derivative of the expression represented as tree which root is this branch
         * @param var variable to differentiate by
         * @return root of the new tree that represents derivative of expression represented
         * as the tree which root is this branch
         * */
        @Override
        public Node diff(String var) {
            if (ExpressionHandler.isOperator(operation)) {
                Node fst = children[0];
                Node snd = children[1];
                switch (operation) {
                    case "+":
                    case "-":
                        return new Branch(operation, fst.diff(var), snd.diff(var));
                    case "*":
                        return new Branch("+", new Branch("*", fst.diff(var), snd.clone()),
                                new Branch("*", fst.clone(), snd.diff(var)));
                    case "/":
                        return new Branch("-", new Branch("/", fst.diff(var), snd.clone()),
                                new Branch("/", new Branch("*", fst.clone(), snd.diff(var)),
                                        new Branch("^", snd.clone(), new Leaf("2"))));
                    case "^":
                        return new Branch("+", new Branch("*", snd.diff(var),
                                new Branch("*", new Branch("^", fst.clone(), snd.clone()),
                                        new Branch("ln", fst.clone()))), new Branch("*", snd.clone(),
                                                new Branch("*", new Branch("^", fst.clone(),
                                                        new Branch("-", snd.clone(), new Leaf("1"))), fst.diff(var))));
                }
            }
            if (ExpressionHandler.isFunction(operation)) {
                if (ExpressionHandler.getNumOfParams(operation) == 1) {
                    Node child = children[0].clone();
                    
                    ASTree der = new ASTree(ExpressionHandler.getInfixDerivative(operation)).substitute("x", new ASTree(child));
                    
                    return new Branch("*", der.root, child.diff(var));
                }
            }
            return this.clone();
        }
        
    }

}
