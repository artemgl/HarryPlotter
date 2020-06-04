package com.chernikovs.entities;

import com.chernikovs.models.TexturedModel;
import org.joml.Vector3f;

/**A class describing vector*/
public class Vector extends Entity {

    private Vector3f direction;
    
    public Vector(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Vector3f direction) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.direction = direction;
    }
    
    public Vector(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Vector3f direction, Vector3f colour) {
        super(model, position, rotX, rotY, rotZ, scale, colour);
        this.direction = direction;
    }

    /**
     * Get direction
     * @return direction of this vector
     * */
    public Vector3f getDirection() {
        return direction;
    }
}
