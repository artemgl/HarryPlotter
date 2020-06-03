package com.chernikovs.renderEngine;

public class Display {

    private static int WIDTH = 1280;
    private static int HEIGHT = 720;

    private static double renderPart = 0.75;

    /**
     * Get width
     * @return width
     * */
    public static int getWidth() {
        return WIDTH;
    }

    /**
     * Get height
     * @return height
     * */
    public static int getHeight() {
        return HEIGHT;
    }

    /**
     * Set width
     * @param width width to set
     * */
    public static void setWidth(int width) {
        WIDTH = width;
    }

    /**
     * Set height
     * @param height height to set
     * */
    public static void setHeight(int height) {
        HEIGHT = height;
    }

    /**
     * Set size
     * @param width width to set
     * @param height height to set
     * */
    public static void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    /**
     * Get render part
     * @return width of render part
     */
    public static double getRenderPart() {
        return renderPart;
    }

    /**
     * Get not render part
     * @return width of not render part
     */
    public static double getNotRenderPart() {
        return 1 - renderPart;
    }
}
