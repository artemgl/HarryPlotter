package com.chernikovs.entities;

import com.chernikovs.functions.FunctionHandler;
import com.chernikovs.functions.VectorFunction;
import com.chernikovs.models.TexturedModel;
import com.chernikovs.renderEngine.MasterRenderer;
import org.joml.Vector3f;

import java.util.ArrayList;

/**A class describing curve*/
public class Curve extends Entity {

    private VectorFunction function = new VectorFunction();
    
    public Curve(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }
    
    public Curve(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Vector3f colour) {
        super(model, position, rotX, rotY, rotZ, scale, colour);
    }

    public Curve(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, VectorFunction f) {
        super(model, position, rotX, rotY, rotZ, scale);
        function = f;
    }

    public Curve(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Vector3f colour, VectorFunction f) {
        super(model, position, rotX, rotY, rotZ, scale, colour);
        function = f;
    }

    /**
     * Get the torsion of the curve
     * @param point point in which torsion is needed to be calculated
     * @return torsion at the received point
     */
    public double getTorsion(float point) {
        VectorFunction d1Phi = function.diff();
        VectorFunction d2Phi = d1Phi.diff();
        VectorFunction d3Phi = d2Phi.diff();
        double nLength = d1Phi.cross(d2Phi).abs().calc(point);
        return d1Phi.cross(d2Phi).dot(d3Phi).calc(point) / nLength / nLength;
    }

    /**
     * Get the curvature of the curve
     * @param point point in which curvature is needed to be calculated
     * @return curvature at the received point
     */
    public double getCurvature(float point) {
        VectorFunction d1Phi = function.diff();
        VectorFunction d2Phi = d1Phi.diff();
        double d1PhiLength = d1Phi.abs().calc(point);
        return d1Phi.cross(d2Phi).abs().calc(point) / d1PhiLength / d1PhiLength / d1PhiLength;
    }

    /**
     * Get the length of the curve
     * @param from point from which is needed to begin length calculating
     * @param to point with which is needed to end length calculating
     * @return length of the curve
     */
    public double length(float from, float to) {
        return function.diff().abs().integrate(from, to);
    }

    /**
     * Get Frenet frame
     * @param point point in which Frenet frame is needed to be calculated
     * @return three vectors that are Frenet frame
     * */
    public ArrayList<Vector> getFrenetFrame(float point) {
        ArrayList<Vector> frenetFrame = new ArrayList<>();
        Vector3f position = new Vector3f((float)function.calc(point)[0], (float)function.calc(point)[1], (float)function.calc(point)[2]);
        VectorFunction tangent = function.diff();
        Vector3f tDirection = new Vector3f((float)tangent.calc(point)[0], (float)tangent.calc(point)[1], (float)tangent.calc(point)[2]);
        Vector t = FunctionHandler.createVector(position, tDirection, new Vector3f(1,0,0), MasterRenderer.getLoader());

        VectorFunction normal = tangent.normalize().diff();
        Vector3f nDirection = new Vector3f((float)normal.calc(point)[0], (float)normal.calc(point)[1], (float)normal.calc(point)[2]);
        Vector n = FunctionHandler.createVector(position, nDirection, new Vector3f(0,1,0), MasterRenderer.getLoader());

        VectorFunction binormal = tangent.cross(normal);
        Vector3f bDirection = new Vector3f((float)binormal.calc(point)[0], (float)binormal.calc(point)[1], (float)binormal.calc(point)[2]);
        Vector b = FunctionHandler.createVector(position, bDirection, new Vector3f(0,0,1), MasterRenderer.getLoader());

        frenetFrame.add(t);
        frenetFrame.add(n);
        frenetFrame.add(b);
        return frenetFrame;
    }
}
