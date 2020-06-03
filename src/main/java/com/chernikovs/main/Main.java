package com.chernikovs.main;

import java.util.ArrayList;
import java.util.List;

import com.chernikovs.controllers.GuiController;
import com.chernikovs.renderEngine.Display;
import com.chernikovs.renderEngine.MasterRenderer;
import lwjgui.LWJGUI;
import lwjgui.LWJGUIUtil;
import lwjgui.scene.Window;
import lwjgui.scene.layout.StackPane;
import org.joml.Vector3f;

import com.chernikovs.controllers.Controller;
import com.chernikovs.entities.Camera;
import com.chernikovs.entities.Light;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    private long window;
    private Window lwjguiWindow;

    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = LWJGUIUtil.createOpenGLCoreWindow("Plotter", Display.getWidth(), Display.getHeight(), false, false);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        lwjguiWindow = LWJGUI.initialize(window);

        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();

        Controller.setCamera(new Camera());
        MasterRenderer renderer = new MasterRenderer();

        List<Light> lights = new ArrayList<>();
        lights.add(new Light(new Vector3f(0, -1, 0), new Vector3f(0.1f, 0.1f, 0.1f)));
        lights.add(new Light(new Vector3f(0.3f, 0.5f, -1), new Vector3f(0.4f, 0.4f, 0.4f)));
        lights.add(new Light(new Vector3f(-2.5f, 1, 1), new Vector3f(0.7f, 0.7f, 0.7f)));
        lights.add(new Light(new Vector3f(1.5f, 1, 1), new Vector3f(0.2f, 0.2f, 0.2f)));
        renderer.addLights(lights);

        GuiController.setWindow(lwjguiWindow);
        GuiController.setRenderer(renderer);

        StackPane root = new StackPane();
        root.getChildren().add(GuiController.createContent());
        lwjguiWindow.getScene().setRoot(root);
        lwjguiWindow.show();

        while (!glfwWindowShouldClose(window) ) {
            GuiController.update();
            Controller.update();

            glfwPollEvents();
            LWJGUI.render();
        }

        renderer.finish();
    }

    public static void main(String[] args) {
        new Main().run();
    }

}
