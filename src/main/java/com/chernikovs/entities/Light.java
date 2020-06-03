package com.chernikovs.entities;

import org.joml.Vector3f;

/**A class describing light*/
public class Light {
    
    private Vector3f position;
    private Vector3f colour;
    
    public Light(Vector3f position, Vector3f colour) {
        this.position = position;
        this.colour = colour;
    }

    /**
     * Get position of the light
     * @return position of the light
     * */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Set position of the light
     * @param position new position of the light
     * */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * Set colour
     * @param colour new colour of the light
     * */
    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    /**
     * Get colour
     * @return colour of the light
     * */
    public Vector3f getColour() {
        return colour;
    }
}
