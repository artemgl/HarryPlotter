package com.chernikovs.toolbox;

import com.chernikovs.entities.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**A class implementing math operations*/
public class Maths {

	/**Constant to compare float variables*/
	public static final float epsilon = 0.000001f;

	/**
	 * Create transformation matrix
	 * @param translation vector to move
	 * @param rx number of degrees to rotate by x axis
	 * @param ry number of degrees to rotate by y axis
	 * @param rz number of degrees to rotate by z axis
	 * @param scale scale to increase or decrease size
	 * @return created transformation matrix
	 * */
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry,
													  float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(translation);
		matrix.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0));
		matrix.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0));
		matrix.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1));
		matrix.scale(scale);
		return matrix;
	}

	/**
	 * Create view matrix by camera
	 * @param camera camera to create view matrix
	 * */
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.identity();
		viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0));
		viewMatrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0));
		viewMatrix.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1));
		Vector3f cameraPos = new Vector3f(camera.getPosition());
		cameraPos.negate();
		viewMatrix.translate(cameraPos);

		return viewMatrix;
	}

}
