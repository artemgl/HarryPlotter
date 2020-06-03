package com.chernikovs.entities;

import com.chernikovs.controllers.Mouse;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

/**A class simulating camera*/
public class Camera {
    
    private float distance = 2.0f;
	
	private Vector3f position = new Vector3f(0,0,2);
	private Vector3f lookAt = new Vector3f(0,0,0);
	private float pitch = 0;
	private float yaw = 0;
	private float roll = 0;
	
	public Camera() {}

	/**
	 * Move the camera according to input data
	 * */
	public void move() {
	    //yaw+=0.3f;
	    
	    //calcZoom();
	    calcPitch();
	    calcYaw();
	    calcLookAt();
	    calcPos();
	}
	
	private void calcLookAt() {	    
	    if (Mouse.isButtonDown(GLFW_MOUSE_BUTTON_RIGHT)) {
	        float u = yaw;
	        float v = pitch;

	        Vector3f x = new Vector3f(
	        (float)Math.cos(Math.toRadians(u)),
	        0,
	        (float)Math.sin(Math.toRadians(u)));
	        Vector3f y = new Vector3f(
	        (float)Math.sin(Math.toRadians(u)) * (float)Math.sin(Math.toRadians(v)),
	        (float)Math.cos(Math.toRadians(v)),
	        -(float)Math.sin(Math.toRadians(v)) * (float)Math.cos(Math.toRadians(u)));

	        float dx = -Mouse.getDX() * 0.001105f * distance;
	        float dy = -Mouse.getDY() * 0.001105f * distance;
	        lookAt.x += dx * x.x + dy * y.x;
	        lookAt.y += dx * x.y + dy * y.y;
	        lookAt.z += dx * x.z + dy * y.z;
	    }
	}
	
	private void calcPos() {
//	    position.x = lookAt.x - distance * (float)Math.sin(Math.toRadians(yaw)) * (float)Math.cos(Math.toRadians(pitch));
//        position.y = lookAt.y - distance * (float)Math.cos(Math.toRadians(yaw)) * (float)Math.cos(Math.toRadians(pitch));
//        position.z = lookAt.z + distance * (float)Math.sin(Math.toRadians(pitch));
	    position.x = lookAt.x - distance * (float)Math.sin(Math.toRadians(yaw)) * (float)Math.cos(Math.toRadians(pitch));
        position.y = lookAt.y + distance * (float)Math.sin(Math.toRadians(pitch));
        position.z = lookAt.z + distance * (float)Math.cos(Math.toRadians(yaw)) * (float)Math.cos(Math.toRadians(pitch));
	}

	/**
	 * Calculate distance to the point around which the camera is rotating
	 * @param speed the speed of movement
	 * @param zoom the difference to move
	 * */
	public void calcZoom(float speed, float zoom) {
//	    float speed = 1;
//	    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
//	        speed = 5;
//	    }
//	    float zoomLevel = Mouse.getDWheel() * 0.005f * speed;
//	    distance -= zoomLevel;
//	    if (distance < 0) {
//	        distance = 0;
//	    }
	    
//	    float speed = 1;
//	    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
//          speed = 1.5f;
//        }
//	    float zoom = Mouse.getDWheel();
	    float red = 0.9f;
	    if (zoom > 0) {
	        distance *= red / speed;
	    }
	    if (zoom < 0) {
	        distance *= speed / red;
	    }
	}
	
	private void calcPitch() {
	    if (Mouse.isButtonDown(0)) {
	        float pitchChange = Mouse.getDY() * 0.3f;
	        pitch -= pitchChange;
	        if (pitch > 90) {
	            pitch = 90;
	        }
	        if (pitch < -90) {
	            pitch = -90;
	        }
	    }
	}
	
	private void calcYaw() {
	    if (Mouse.isButtonDown(0)) {
            float yawChange = Mouse.getDX() * 0.9f;
            yaw += yawChange;
        }
	}

	/**
	 * Set the point around which camera is rotating to the origin
	 * */
	public void resetLookAt() {
		lookAt = new Vector3f(0,0,0);
	}

	/**
	 * Get position of the camera
	 * @return position
	 * */
	public Vector3f getPosition() {
		return position;
	}

	/**
	 * Get pitch of the camera
	 * @return pitch
	 * */
	public float getPitch() {
		return pitch;
	}

	/**
	 * Get yaw of the camera
	 * @return yaw
	 * */
	public float getYaw() {
		return yaw;
	}

	/**
	 * Get roll of the camera
	 * @return roll
	 * */
	public float getRoll() {
		return roll;
	}

	/**
	 * Get distance from camera to the point around which the camera is rotating
	 * @return distance
	 * */
	public float getDistance() {
	    return distance;
	}
}
