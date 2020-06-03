package com.chernikovs.shaders;

import java.util.List;

import com.chernikovs.toolbox.Maths;

import com.chernikovs.entities.Camera;
import com.chernikovs.entities.Light;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**A class describing static shader*/
public class StaticShader extends ShaderProgram {
    
    private static final int MAX_LIGHTS = 5;
	
	private static final String VERTEX_FILE = "src/main/java/com/chernikovs/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/main/java/com/chernikovs/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_colour;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_colour = super.getUniformLocation("colour");
		
		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColour = new int[MAX_LIGHTS];
		for (int i = 0; i < MAX_LIGHTS; i++) {
		    location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
		    location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
		}
	}

	/**
	 * Load shine data
	 * @param damper damper of material
	 * @param reflectivity reflectivity of material
	 * */
	public void loadShineVariables(float damper, float reflectivity) {
	    super.loadFloat(location_shineDamper, damper);
	    super.loadFloat(location_reflectivity, reflectivity);
	}

	/**
	 * Load transformation matrix
	 * @param matrix transformation matrix to load
	 * */
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	/**
	 * Load lights
	 * @param lights lights to load
	 * */
	public void loadLights(List<Light> lights) {
	    for (int i = 0; i < MAX_LIGHTS; i++) {
	        if (i < lights.size()) {
	            super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
	            super.loadVector(location_lightColour[i], lights.get(i).getColour());
	        } else {
	            super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
                super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
	        }
	    }
	}

	/**
	 * Load view matrix
	 * @param camera camera to build a view matrix to load
	 * */
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}

	/**
	 * Load projection matrix
	 * @param projection projection matrix to load
	 * */
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}

	/**
	 * Load colour
	 * @param colour colour to load
	 * */
	public void loadColour(Vector3f colour) {
	    super.loadVector(location_colour, colour);
	}

}
