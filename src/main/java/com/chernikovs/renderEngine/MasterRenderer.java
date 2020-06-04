package com.chernikovs.renderEngine;

import com.chernikovs.controllers.Controller;
import com.chernikovs.entities.*;
import com.chernikovs.functions.FunctionHandler;
import com.chernikovs.shaders.StaticShader;
import lwjgui.scene.Context;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**A class describing smart renderer*/
public class MasterRenderer implements lwjgui.gl.Renderer {

    private Renderer renderer;
    private StaticShader shader;
    private static Loader loader = new Loader();

    private ArrayList<Surface> surfaces = new ArrayList<>();
    private ArrayList<Curve> curves = new ArrayList<>();
    private ArrayList<Vector> vectors = new ArrayList<>();
    private ArrayList<Vector> axis = new ArrayList<Vector>() {{
        add(FunctionHandler.createVector(new Vector3f(0,0,0), new Vector3f(1,0,0), new Vector3f(1,1,1), loader));
        add(FunctionHandler.createVector(new Vector3f(0,0,0), new Vector3f(0,1,0), new Vector3f(1,1,1), loader));
        add(FunctionHandler.createVector(new Vector3f(0,0,0), new Vector3f(0,0,1), new Vector3f(1,1,1), loader));
    }};
    private List<Light> lights = new ArrayList<>();

    public MasterRenderer() {
        shader = new StaticShader();
        renderer = new Renderer(shader);
    }

    /**
     * Get loader
     * @return loader
     * */
    public static Loader getLoader() {
        return loader;
    }

    /**
     * Put surfaces to render
     * @param surfaces surfaces to render
     * */
    public void putSurfaces(ArrayList<Surface> surfaces) {
        this.surfaces = surfaces;
    }

    /**
     * Put curves to render
     * @param curves curves to render
     * */
    public void putCurves(ArrayList<Curve> curves) {
        this.curves = curves;
    }

    /**
     * Put vectors to render
     * @param vectors vectors to render
     * */
    public void putVectors(ArrayList<Vector> vectors) {
        this.vectors = vectors;
    }

    /**
     * Put lights to render
     * @param lights lights to render
     * */
    public void putLights(List<Light> lights) {
        this.lights = lights;
    }

    /**
     * Add a surface to render
     * @param surface surface to render
     * */
    public void add(Surface surface) {
        surfaces.add(surface);
    }

    /**
     * Add a curve to render
     * @param curve curve to render
     * */
    public void add(Curve curve) {
        curves.add(curve);
    }

    /**
     * Add a vector to render
     * @param vector vector to render
     * */
    public void add(Vector vector) {
        vectors.add(vector);
    }

    /**
     * Add a light to render
     * @param light light to render
     * */
    public void add(Light light) {
        lights.add(light);
    }

    /**
     * Add surfaces to render
     * @param surfaces surfaces to render
     * */
    public void addSurfaces(ArrayList<Surface> surfaces) {
        this.surfaces.addAll(surfaces);
    }

    /**
     * Add curves to render
     * @param curves curves to render
     * */
    public void addCurves(ArrayList<Curve> curves) {
        this.curves.addAll(curves);
    }

    /**
     * Add vectors to render
     * @param vectors vectors to render
     * */
    public void addVectors(ArrayList<Vector> vectors) {
        this.vectors.addAll(vectors);
    }

    /**
     * Add lights to render
     * @param lights lights to render
     * */
    public void addLights(List<Light> lights) {
        this.lights.addAll(lights);
    }

    /**
     * Remove surface from rendering
     * @param surface surface to remove
     * */
    public void remove(Surface surface) {
        surfaces.remove(surface);
    }

    /**
     * Remove curve from rendering
     * @param curve curve to remove
     * */
    public void remove(Curve curve) {
        curves.remove(curve);
    }

    /**
     * Remove vector from rendering
     * @param vector vector to remove
     * */
    public void remove(Vector vector) {
        vectors.remove(vector);
    }

    /**
     * Remove light from rendering
     * @param light light to remove
     * */
    public void remove(Light light) {
        lights.remove(light);
    }

    /**
     * Remove surfaces from rendering
     * @param surfaces surfaces to remove
     * */
    public void removeSurfaces(ArrayList<Surface> surfaces) {
        this.surfaces.removeAll(surfaces);
    }

    /**
     * Remove curves from rendering
     * @param curves curves to remove
     * */
    public void removeCurves(ArrayList<Curve> curves) {
        this.curves.removeAll(curves);
    }

    /**
     * Remove vectors from rendering
     * @param vectors vectors to remove
     * */
    public void removeVectors(ArrayList<Vector> vectors) {
        this.vectors.removeAll(vectors);
    }

    /**
     * Remove lights from rendering
     * @param lights lights to remove
     * */
    public void removeLights(ArrayList<Light> lights) {
        this.lights.removeAll(lights);
    }

    /**Finish working*/
    public void finish() {
        loader.cleanUp();
        shader.cleanUp();
    }

    /**Remove all from rendering*/
    public void cleanUp() {
        surfaces = new ArrayList<>();
        curves = new ArrayList<>();
        vectors = new ArrayList<>();
    }

    private ArrayList<Vector> updateVectors(ArrayList<Vector> vectors) {
        ArrayList<Vector> result = new ArrayList<>();
        for (Vector vector : vectors) {
            result.add(FunctionHandler.createVector(vector.getPosition(), vector.getDirection(), vector.getColour(), loader));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void render(Context context) {
        vectors = updateVectors(vectors);
        axis = updateVectors(axis);

        renderer.prepare();
        shader.start();
        shader.loadLights(lights);
        shader.loadViewMatrix(Controller.getCamera());
        renderer.renderSurfaces(surfaces, shader);
        renderer.renderCurves(curves, shader);
        renderer.renderVectors(vectors, shader);
        renderer.renderVectors(axis, shader);
        shader.stop();
    }
}
