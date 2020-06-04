<<<<<<< HEAD
package com.chernikovs.entities;

import com.chernikovs.models.TexturedModel;
import org.joml.Vector3f;

/**A class describing entity to render*/
public class Entity {

	private TexturedModel model;
	private Vector3f position;
	protected Vector3f colour;
	private float rotX, rotY, rotZ;
	private float scale;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Vector3f colour) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.colour = colour;
    }
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
	    this(model, position, rotX, rotY, rotZ, scale, new Vector3f(0,0,0));
	}

	/**
	 * Increase position of the entity
	 * @param dx difference by x axis
	 * @param dy difference by y axis
	 * @param dz difference by z axis
	 * */
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	/**
	 * Increase rotation of the entity
	 * @param dx difference by x axis
	 * @param dy difference by y axis
	 * @param dz difference by z axis
	 * */
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	/**
	 * Get model of the entity
	 * */
	public TexturedModel getModel() {
		return model;
	}

	/**
	 * Set new model to the entity
	 * @param model new model to set
	 * */
	public void setModel(TexturedModel model) {
		this.model = model;
	}

	/**
	 * Get position of the entity
	 * @return position of the entity
	 * */
	public Vector3f getPosition() {
		return position;
	}

	/**
	 * Set position of the entity
	 * @param position new position of the entity
	 * */
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	/**
	 * Get rotation by x axis
	 * @return rotation by x axis
	 * */
	public float getRotX() {
		return rotX;
	}

	/**
	 * Set rotation by x axis
	 * @param rotX new rotation by x axis
	 * */
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	/**
	 * Get rotation by y axis
	 * @return rotation by y axis
	 * */
	public float getRotY() {
		return rotY;
	}

	/**
	 * Set rotation by y axis
	 * @param rotY new rotation by y axis
	 * */
	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	/**
	 * Get rotation by z axis
	 * @return rotation by z axis
	 * */
	public float getRotZ() {
		return rotZ;
	}

	/**
	 * Set rotation by z axis
	 * @param rotZ new rotation by z axis
	 * */
	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	/**
	 * Get scale
	 * @return scale of the entity
	 * */
	public float getScale() {
		return scale;
	}

	/**
	 * Set scale
	 * @param scale new scale of the entity
	 * */
	public void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * Set colour
	 * @param colour new colour of the entity
	 * */
	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	/**
	 * Get colour
	 * @return colour of the entity
	 * */
	public Vector3f getColour() {
	    return colour;
	}

}
=======
package com.chernikovs.entities;

import com.chernikovs.models.TexturedModel;
import org.joml.Vector3f;

/**A class describing entity to render*/
public class Entity {

	private TexturedModel model;
	private Vector3f position;
	protected Vector3f colour;
	private float rotX, rotY, rotZ;
	private float scale;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Vector3f colour) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.colour = colour;
    }
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
	    this(model, position, rotX, rotY, rotZ, scale, new Vector3f(0,0,0));
	}

	/**
	 * Increase position of the entity
	 * @param dx difference by x axis
	 * @param dy difference by y axis
	 * @param dz difference by z axis
	 * */
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	/**
	 * Increase rotation of the entity
	 * @param dx difference by x axis
	 * @param dy difference by y axis
	 * @param dz difference by z axis
	 * */
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	/**
	 * Get model of the entity
	 * */
	public TexturedModel getModel() {
		return model;
	}

	/**
	 * Set new model to the entity
	 * @param model new model to set
	 * */
	public void setModel(TexturedModel model) {
		this.model = model;
	}

	/**
	 * Get position of the entity
	 * @return position of the entity
	 * */
	public Vector3f getPosition() {
		return position;
	}

	/**
	 * Set position of the entity
	 * @param position new position of the entity
	 * */
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	/**
	 * Get rotation by x axis
	 * @return rotation by x axis
	 * */
	public float getRotX() {
		return rotX;
	}

	/**
	 * Set rotation by x axis
	 * @param rotX new rotation by x axis
	 * */
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	/**
	 * Get rotation by y axis
	 * @return rotation by y axis
	 * */
	public float getRotY() {
		return rotY;
	}

	/**
	 * Set rotation by y axis
	 * @param rotY new rotation by y axis
	 * */
	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	/**
	 * Get rotation by z axis
	 * @return rotation by z axis
	 * */
	public float getRotZ() {
		return rotZ;
	}

	/**
	 * Set rotation by z axis
	 * @param rotZ new rotation by z axis
	 * */
	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	/**
	 * Get scale
	 * @return scale of the entity
	 * */
	public float getScale() {
		return scale;
	}

	/**
	 * Set scale
	 * @param scale new scale of the entity
	 * */
	public void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * Set colour
	 * @param colour new colour of the entity
	 * */
	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	/**
	 * Get colour
	 * @return colour of the entity
	 * */
	public Vector3f getColour() {
	    return colour;
	}

}
>>>>>>> 4f3c886f3ff1ae2c84328c3abe8a64dfc85d4b78
