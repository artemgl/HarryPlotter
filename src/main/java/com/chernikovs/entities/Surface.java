package com.chernikovs.entities;

import com.chernikovs.functions.*;
import com.chernikovs.models.TexturedModel;
import com.chernikovs.renderEngine.MasterRenderer;
import com.chernikovs.toolbox.Maths;
import org.joml.Vector3f;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**A class describing surface*/
public class Surface extends Entity {

    private ArrayList<Curve> grid;
    private VectorFunction2 function = new VectorFunction2();

    private VectorFunction2 n;
    private Function2 E;
    private Function2 F;
    private Function2 G;
    private Function2 L;
    private Function2 M;
    private Function2 N;

    private Function2 k1;
    private Function2 k2;
    private Function2 aPhi;
    private Function2 bPhi;
    private Function2 cPhi;

    public Surface(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }
    
    public Surface(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Vector3f colour) {
        super(model, position, rotX, rotY, rotZ, scale, colour);
    }

    public Surface(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, VectorFunction2 f) {
        super(model, position, rotX, rotY, rotZ, scale);
        function = f;
    }

    public Surface(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Vector3f colour, VectorFunction2 f) {
        super(model, position, rotX, rotY, rotZ, scale, colour);
        function = f;
    }

    /**
     * Set colour of the surface
     * @param colour new colour of the surface
     * */
    @Override
    public void setColour(Vector3f colour) {
        this.colour = colour;
        for (Curve curve : grid) {
            curve.setColour(colour);
        }
    }

    /**
     * Get this surface in grid representation
     * @return curves that form this surface
     * */
    public ArrayList<Curve> getGrid() {
        return grid;
    }

    /**
     * Set grid representation of this surface
     * @param grid curves that form the surface
     * */
    public void setGrid(ArrayList<Curve> grid) {
        this.grid = grid;
    }

    /**
     * Get tangent plane
     * @param fst the first coordinate of the point on u-v plane
     * @param snd the second coordinate of the point on u-v plane
     * @return tangent plane at this point
     * */
    public Surface getTangentPlane(float fst, float snd) {
        VectorFunction2 dFst = function.diffByFst();
        VectorFunction2 dSnd = function.diffBySnd();
        Function2[] fns = new Function2[3];
        Vector3f fstVector = new Vector3f((float)dFst.calc(fst, snd)[0], (float)dFst.calc(fst, snd)[1], (float)dFst.calc(fst, snd)[2]);
        Vector3f sndVector = new Vector3f((float)dSnd.calc(fst, snd)[0], (float)dSnd.calc(fst, snd)[1], (float)dSnd.calc(fst, snd)[2]);
        if (fstVector.x != 0 || fstVector.y != 0 || fstVector.z != 0) {
            fstVector.normalize();
        }
        float dot = fstVector.dot(sndVector);
        if (dot != 0) {
            float alpha = - 1 / fstVector.dot(sndVector);
            sndVector.x = fstVector.x + alpha * sndVector.x;
            sndVector.y = fstVector.y + alpha * sndVector.y;
            sndVector.z = fstVector.z + alpha * sndVector.z;
        }
        if (sndVector.x != 0 || sndVector.y != 0 || sndVector.z != 0) {
            sndVector.normalize();
        }
        fns[0] = new Function2(new ASTree("u*a1+v*a2").substitute(new String[] {"u", "a1", "v", "a2"}, new ASTree(function.getFstVar()), new ASTree(String.valueOf(fstVector.x)), new ASTree(function.getSndVar()), new ASTree(String.valueOf(sndVector.x))).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
        fns[1] = new Function2(new ASTree("u*b1+v*b2").substitute(new String[] {"u", "b1", "v", "b2"}, new ASTree(function.getFstVar()), new ASTree(String.valueOf(fstVector.y)), new ASTree(function.getSndVar()), new ASTree(String.valueOf(sndVector.y))).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
        fns[2] = new Function2(new ASTree("u*c1+v*c2").substitute(new String[] {"u", "c1", "v", "c2"}, new ASTree(function.getFstVar()), new ASTree(String.valueOf(fstVector.z)), new ASTree(function.getSndVar()), new ASTree(String.valueOf(sndVector.z))).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
        VectorFunction2 f = new VectorFunction2(fns, function.getFstVar(), function.getSndVar());
        Surface plane = FunctionHandler.createSurface(f, new float[] {-1, 1}, new float[] {-1, 1}, 1.0f, 1.0f, new Vector3f(0.6f,0.0f,0.6f), MasterRenderer.getLoader());
        double[] position = function.calc(fst, snd);
        plane.increasePosition((float)position[0], (float)position[1], (float)position[2]);
        return plane;
    }

    /**
     * Get main directions
     * @param fst the first coordinate of the point on u-v plane
     * @param snd the second coordinate of the point on u-v plane
     * @return main directions at this point
     * */
    public ArrayList<Vector> getMainDirections(float fst, float snd) {
        ArrayList<Vector> mainDirections = new ArrayList<>();
        double[] position = function.calc(fst, snd);
        Vector3f vecPosition = new Vector3f((float)position[0], (float)position[1], (float)position[2]);
        Vector3f[] directions = getVectorMainDirections(fst, snd);
        Vector3f colourMin = new Vector3f(0, 1, 0);
        Vector3f colourMax = new Vector3f(1, 0, 0);
        mainDirections.add(FunctionHandler.createVector(vecPosition, directions[0], colourMin, MasterRenderer.getLoader()));
        mainDirections.add(FunctionHandler.createVector(vecPosition, directions[1], colourMax, MasterRenderer.getLoader()));
        mainDirections.add(FunctionHandler.createVector(vecPosition, directions[2], colourMin, MasterRenderer.getLoader()));
        mainDirections.add(FunctionHandler.createVector(vecPosition, directions[3], colourMax, MasterRenderer.getLoader()));
        return mainDirections;
    }

    private Vector3f[] getVectorMainDirections(float fst, float snd) {
        Vector3f[] result = new Vector3f[5];
        if (E == null || F == null || G == null || L == null || M == null || N == null) {
            countQuadraticForms();
            countCurvatures();
        }
        Function2 d = null;
        if (Math.abs(aPhi.calc(fst, snd)) <= Maths.epsilon) {
            if (Math.abs(bPhi.calc(fst, snd)) <= Maths.epsilon) {
                d = new Function2(new ASTree("0").toPostfix(), function.getFstVar(), function.getSndVar());
            } else {
                d = new Function2(new ASTree("-c/b").substitute(new String[] {"b", "c"}, new ASTree(bPhi.getPostfix()), new ASTree(cPhi.getPostfix())).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
            }
        } else {
            d = new Function2(new ASTree("(-b+sqrt(b^2-4*a*c))/(2*a)").substitute(new String[] {"a", "b", "c"}, new ASTree(aPhi.getPostfix()), new ASTree(bPhi.getPostfix()), new ASTree(cPhi.getPostfix())).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
        }
        Function arg1 = new Function(new ASTree("t").toPostfix(), "t");
        Function arg2 = new Function(new ASTree("d*(t-fst)+snd").substitute(new String[] {"d", "fst", "snd"}, new ASTree(String.valueOf(d.calc(fst, snd))), new ASTree(String.valueOf(fst)), new ASTree(String.valueOf(snd))).reduce().toPostfix(), "t");

        List<ArrayDeque<String>> list = new ArrayList<>();
        list.add(new ASTree(function.getFns()[0].getPostfix()).substitute(new String[] {function.getFstVar(), function.getSndVar()}, new ASTree(arg1.getPostfix()), new ASTree(arg2.getPostfix())).reduce().toPostfix());
        list.add(new ASTree(function.getFns()[1].getPostfix()).substitute(new String[] {function.getFstVar(), function.getSndVar()}, new ASTree(arg1.getPostfix()), new ASTree(arg2.getPostfix())).reduce().toPostfix());
        list.add(new ASTree(function.getFns()[2].getPostfix()).substitute(new String[] {function.getFstVar(), function.getSndVar()}, new ASTree(arg1.getPostfix()), new ASTree(arg2.getPostfix())).reduce().toPostfix());
        VectorFunction phi = new VectorFunction(list, "t");
        double[] dPhi = phi.diff().calc(fst);
        Vector3f direction1 = new Vector3f((float)dPhi[0], (float)dPhi[1], (float)dPhi[2]);
        double[] fixN = n.calc(fst, snd);
        Vector3f direction3 = new Vector3f((float)fixN[0], (float)fixN[1], (float)fixN[2]);
        Vector3f direction2 = new Vector3f();
        direction3.cross(direction1, direction2);

        float dv = (float)d.calc(fst, snd);
        float k = (float) ((float)(L.calc(fst, snd) + 2 * M.calc(fst, snd) * dv + N.calc(fst, snd) * dv * dv) / (E.calc(fst, snd) + 2 * F.calc(fst, snd) * dv + G.calc(fst, snd) * dv * dv));

        if (Math.abs(k - k1.calc(fst, snd)) <= Maths.epsilon && k <= k2.calc(fst, snd) ||
                Math.abs(k - k1.calc(fst, snd)) > Maths.epsilon && k <= k1.calc(fst, snd)) {
            result[0] = direction1;
            result[1] = direction2;
            result[2] = new Vector3f(-direction1.x, -direction1.y, -direction1.z);
            result[3] = new Vector3f(-direction2.x, -direction2.y, -direction2.z);
        } else {
            result[0] = direction2;
            result[1] = new Vector3f(-direction1.x, -direction1.y, -direction1.z);
            result[2] = new Vector3f(-direction2.x, -direction2.y, -direction2.z);
            result[3] = direction1;
        }
        result[4] = direction3;
        return result;
    }

    private void countQuadraticForms() {
        VectorFunction2 fu = function.diffByFst();
        VectorFunction2 fv = function.diffBySnd();
        VectorFunction2 fuu = fu.diffByFst();
        VectorFunction2 fuv = fu.diffBySnd();
        VectorFunction2 fvv = fv.diffBySnd();
        n = fu.cross(fv).normalize();

        E = fu.dot(fu);
        F = fu.dot(fv);
        G = fv.dot(fv);
        L = fuu.dot(n);
        M = fuv.dot(n);
        N = fvv.dot(n);

        aPhi = new Function2(new ASTree("F*N-M*G").substitute(new String[] {"F", "G", "M", "N"}, new ASTree(F.getPostfix()), new ASTree(G.getPostfix()), new ASTree(M.getPostfix()), new ASTree(N.getPostfix())).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
        bPhi = new Function2(new ASTree("E*N-L*G").substitute(new String[] {"E", "G", "L", "N"}, new ASTree(E.getPostfix()), new ASTree(G.getPostfix()), new ASTree(L.getPostfix()), new ASTree(N.getPostfix())).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
        cPhi = new Function2(new ASTree("E*M-L*F").substitute(new String[] {"E", "F", "L", "M"}, new ASTree(E.getPostfix()), new ASTree(F.getPostfix()), new ASTree(L.getPostfix()), new ASTree(M.getPostfix())).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
    }

    private void countCurvatures() {
        Function2 aK = new Function2(new ASTree("E*G-F^2").substitute(new String[] {"E", "F", "G"}, new ASTree(E.getPostfix()), new ASTree(F.getPostfix()), new ASTree(G.getPostfix())).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
        Function2 bK = new Function2(new ASTree("2*M*F-G*L-N*E").substitute(new String[] {"E", "F", "G", "L", "M", "N"}, new ASTree(E.getPostfix()), new ASTree(F.getPostfix()), new ASTree(G.getPostfix()), new ASTree(L.getPostfix()), new ASTree(M.getPostfix()), new ASTree(N.getPostfix())).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
        Function2 cK = new Function2(new ASTree("N*L-M^2").substitute(new String[] {"L", "M", "N"}, new ASTree(L.getPostfix()), new ASTree(M.getPostfix()), new ASTree(N.getPostfix())).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
        k1 = new Function2(new ASTree("(-b+sqrt(b^2-4*a*c))/(2*a)").substitute(new String[] {"a", "b", "c"}, new ASTree(aK.getPostfix()), new ASTree(bK.getPostfix()), new ASTree(cK.getPostfix())).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
        k2 = new Function2(new ASTree("(-b-sqrt(b^2-4*a*c))/(2*a)").substitute(new String[] {"a", "b", "c"}, new ASTree(aK.getPostfix()), new ASTree(bK.getPostfix()), new ASTree(cK.getPostfix())).reduce().toPostfix(), function.getFstVar(), function.getSndVar());
    }

    /**
     * Get osculating paraboloid
     * @param fst the first coordinate of the point on u-v plane
     * @param snd the second coordinate of the point on u-v plane
     * @return osculating paraboloid at this point
     * */
    public Surface getOsculatingParaboloid(float fst, float snd) {
        if (E == null || F == null || G == null || L == null || M == null || N == null) {
            countQuadraticForms();
            countCurvatures();
        }

        Vector3f[] directions = getVectorMainDirections(fst, snd);
        Vector3f direction1 = directions[0];
        Vector3f direction2 = directions[1];
        Vector3f direction3 = directions[4];

        double scalarK1 = k1.calc(fst, snd);
        double scalarK2 = k2.calc(fst, snd);
        String expression = null;
        if (scalarK1 <= scalarK2) {
            expression = "((" + String.valueOf(scalarK1) + ")*x^2+(" + String.valueOf(scalarK2) + ")*y^2)/2";
        } else {
            expression = "((" + String.valueOf(scalarK2) + ")*x^2+(" + String.valueOf(scalarK1) + ")*y^2)/2";
        }

        float theta1 = (float)Math.toDegrees(new Vector3f(0, 0, 1).angle(new Vector3f(0, direction3.y, direction3.z)));
        if (new Vector3f(0, 1, 0).dot(direction3) > 0) {
            theta1 = 360 - theta1;
        }
        float theta2 = (float)Math.toDegrees(direction3.angle(new Vector3f(0, direction3.y, direction3.z)));
        if (new Vector3f(1, 0, 0).dot(direction3) < 0) {
            theta2 = 360 - theta2;
        }
        Vector3f yAfterRot = new Vector3f(0, (float)Math.cos(Math.toRadians(theta1)), (float)Math.sin(Math.toRadians(theta1)));
        float theta3 = (float)Math.toDegrees(direction2.angle(yAfterRot));
        if (yAfterRot.dot(direction1) < 0) {
            theta3 = 360 - theta3;
        }

        Surface paraboloid = FunctionHandler.createSurface(new Function2(expression, "x", "y"), new float[] {-1, 1}, new float[] {-1, 1}, 0.1f, 0.1f, new Vector3f(1.0f, 0.5f, 0.0f), MasterRenderer.getLoader());
        double[] position = function.calc(fst, snd);
        paraboloid.increasePosition((float)position[0], (float)position[1], (float)position[2]);
        paraboloid.increaseRotation(theta1, theta2, theta3);
        return paraboloid;
    }

    /**
     * Get Gaussian curvature
     * @param fst the first coordinate of the point on u-v plane
     * @param snd the second coordinate of the point on u-v plane
     * @return Gaussian curvature at this point
     * */
    public double getGaussianCurvature(float fst, float snd) {
        if (E == null || F == null || G == null || L == null || M == null || N == null) {
            countQuadraticForms();
            countCurvatures();
        }
        return k1.calc(fst, snd) * k2.calc(fst, snd);
    }

    /**
     * Get average curvature
     * @param fst the first coordinate of the point on u-v plane
     * @param snd the second coordinate of the point on u-v plane
     * @return average curvature at this point
     * */
    public double getAverageCurvature(float fst, float snd) {
        if (E == null || F == null || G == null || L == null || M == null || N == null) {
            countQuadraticForms();
            countCurvatures();
        }
        return (k1.calc(fst, snd) + k2.calc(fst, snd)) / 2;
    }

//    /**
//     * Get square
//     * @param u1 the first coordinate of the first point on u-v plane
//     * @param v1 the second coordinate of the first point on u-v plane
//     * @param u2 the first coordinate of the second point on u-v plane
//     * @param v2 the second coordinate of the second point on u-v plane
//     * @return square of the surface between the first and the second points
//     * */
//    public double square(float u1, float v1, float u2, float v2) {
//        return function.diffByFst().cross(function.diffBySnd()).abs().doubleIntegrate(u1, u2, v1, v2);
//    }

    /**
     * Get square
     * @param u1 the first coordinate of the first point on u-v plane
     * @param v1 the second coordinate of the first point on u-v plane
     * @param u2 the first coordinate of the second point on u-v plane
     * @param v2 the second coordinate of the second point on u-v plane
     * @return square of the surface between the first and the second points
     * */
    public double square(float u1, float v1, float u2, float v2) {
        double result = 0;
        int xSteps = 400;
        int ySteps = 400;
        double step1 = (u2 - u1) / xSteps;
        double step2 = (v2 - v1) / ySteps;
        float x = u1;
        float y = v1;
        for (int j = 0; j < ySteps; j++) {
            for (int i = 0; i < xSteps; i++) {
                result += triangleSquare(function.calc(x, y + step2), function.calc(x + step1, y), function.calc(x, y)) + triangleSquare(function.calc(x, y + step2), function.calc(x + step1, y), function.calc(x + step1, y + step2));
                x += step1;
            }
            x = u1;
            y += step2;
        }
        return result;
    }

    private double triangleSquare(double[] fst, double[] snd, double[] trd) {
        Vector3f one = new Vector3f((float)(snd[0] - fst[0]), (float)(snd[1] - fst[1]), (float)(snd[2] - fst[2]));
        Vector3f two = new Vector3f((float)(trd[0] - fst[0]), (float)(trd[1] - fst[1]), (float)(trd[2] - fst[2]));
        return one.length() * two.length() * Math.sin(one.angle(two)) / 2;
    }

    /**
     * Get main curvatures
     * @param fst the first coordinate of the point on u-v plane
     * @param snd the second coordinate of the point on u-v plane
     * @return main curvatures at this point
     * */
    public double[] getMainCurvatures(float fst, float snd) {
        return new double[] {k1.calc(fst, snd), k2.calc(fst, snd)};
    }
    
}
