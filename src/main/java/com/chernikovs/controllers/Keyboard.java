package com.chernikovs.controllers;

import lwjgui.scene.layout.OpenGLPane;

import java.util.HashMap;
import java.util.Map;

/**A class simulating keyboard*/
public class Keyboard {

    private static Map<Integer, Boolean> keyboard = new HashMap<>();

    /**
     * Initialize with certain pane
     * @param pane the pane in which initialization is required
     * */
    public static void initialize(OpenGLPane pane) {
        pane.setOnKeyPressed(event->keyboard.put(event.getKey(), true));
        pane.setOnKeyReleased(event->keyboard.put(event.getKey(), false));
    }

    /**
     * Get the information whether the key is pressed
     * @param key the key to check
     * @return true if the key if pressed and false otherwise
     * */
    public static boolean isKeyDown(int key) {
        if (!keyboard.containsKey(key)) {
            return false;
        }

        return keyboard.get(key);
    }

}
