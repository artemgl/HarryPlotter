package com.chernikovs.controllers;

import lwjgui.scene.layout.OpenGLPane;
import org.joml.Vector2f;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**A class simulating mouse*/
public class Mouse {

    private static Map<Integer, Boolean> buttons = new HashMap<>();
    private static double dxwheel = 0;
    private static double dywheel = 0;
    private static Vector2f position = new Vector2f(0, 0);
    private static Vector2f dposition = new Vector2f(0, 0);

    private static AtomicBoolean in = new AtomicBoolean(true);

    /**
     * Drop data about mouse movement
     * */
    public static void cleanUp() {
        dxwheel = 0;
        dywheel = 0;
        dposition.x = 0;
        dposition.y = 0;
    }

    /**
     * Initialize with certain pane
     * @param pane the pane in which initialization is required
     * */
    public static void initialize(OpenGLPane pane) {
        pane.setOnMousePressed(event->{
            if (in.get()) {
                buttons.put(event.getButton(), true);
                if (isButtonDown(0) || isButtonDown(1)) {
                    position.x = (float)event.mouseX;
                    position.y = (float)event.mouseY;
                }
            }
        });
        pane.setOnMouseReleased(event->{
            if (in.get()) {
                buttons.put(event.getButton(), false);
            }
        });
        pane.setOnMouseScrolled(event->{
            if (in.get()) {
                dxwheel = event.x;
                dywheel = event.y;
            }});
        pane.setOnMouseDragged(event -> {
            if (in.get()) {
                double xpos = event.mouseX;
                double ypos = event.mouseY;
                dposition.x = (float)(xpos - position.x);
                dposition.y = (float)(ypos - position.y);
                position.x = (float)xpos;
                position.y = (float)ypos;
            }});
        pane.setOnMouseEntered(event->in.set(true));
        pane.setOnMouseExited(event->in.set(false));
    }

    /**
     * Get recent horizontal rotation of wheel
     * @return difference in rotation
     * */
    public static double getDXWheel() {
        return dxwheel;
    }

    /**
     * Get recent vertical rotation of wheel
     * @return difference in rotation
     * */
    public static double getDYWheel() {
        return dywheel;
    }

    /**
     * Get recent horizontal movement of mouse
     * @return difference in movement
     * */
    public static float getDX() {
        return dposition.x;
    }

    /**
     * Get recent vertical movement of mouse
     * @return difference in movement
     * */
    public static float getDY() {
        return -dposition.y;
    }

    /**
     * Get the information whether the button is pressed
     * @param key the button to check
     * @return true if the button if pressed and false otherwise
     * */
    public static boolean isButtonDown(int key) {
        if (!buttons.containsKey(key)) {
            return false;
        }

        return buttons.get(key);
    }

}
