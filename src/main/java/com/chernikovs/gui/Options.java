package com.chernikovs.gui;

import lwjgui.geometry.Insets;
import lwjgui.scene.Node;
import lwjgui.scene.Window;
import lwjgui.scene.control.*;
import lwjgui.scene.layout.BorderPane;
import lwjgui.scene.layout.GridPane;
import lwjgui.scene.layout.Pane;

/**An abstract class describing elements of graphical user interface that represent options menu*/
public abstract class Options {

    protected Window window;

    protected GridPane base = new GridPane();

    protected double width = 450;
    protected double height = 320;
    protected double indent = 10;
    protected double tfWidth = 35;
    protected double tfHeight = 10;

    protected Button analyse = new Button("Analyse");

    protected TextArea functionText = new TextArea();
    protected ColorPicker colorPicker = new ColorPicker();

    /**
     * Get options menu
     * @return options menu
     * */
    public Pane getBase() {
        return base;
    }

    /**
     * Bind certain geometric object to this menu
     * @param index primary key of the geometric object
     * */
    public abstract void bind(int index);

    protected static BorderPane createWrapper(Node content, String side, double width, double height) {
        BorderPane result = new BorderPane();
        result.setMinSize(width, height);
        switch (side) {
            case "L":
                result.setLeft(content);
                break;
            case "R":
                result.setRight(content);
                break;
            case "T":
                result.setTop(content);
                break;
            case "B":
                result.setBottom(content);
                break;
            default:
                result.setCenter(content);
                break;
        }
        return result;
    }

    protected static Pane createVariableOptions(String text, double width, double height,
                                                TextField varText, TextField from, TextField to, TextField step, CheckBox pi) {
        GridPane res = new GridPane();
        res.setMinSize(width, height);

        Label var = new Label(text);
        var.setPadding(new Insets(0, 0, 0, 10));

        res.add(createWrapper(var, "L", width * 0.28, height), 1, 1);
        res.add(createWrapper(varText, "L", width * 0.1, height), 2, 1);
        res.add(createWrapper(new Label("from:"), "L", width * 0.09, height), 3, 1);
        res.add(createWrapper(from, "L", width * 0.1, height), 4, 1);
        res.add(createWrapper(new Label("to:"), "L", width * 0.05, height), 5, 1);
        res.add(createWrapper(to, "L", width * 0.1, height), 6, 1);
        res.add(createWrapper(new Label("step:"), "L", width * 0.08, height), 7, 1);
        res.add(createWrapper(step, "L", width * 0.1, height), 8, 1);
        res.add(createWrapper(pi, "L", width * 0.1, height), 9, 1);

        return res;
    }

    protected static TextField createTextField(double width, double height) {
        TextField result = new TextField();
        result.setPrefSize(width, height);
        return result;
    }


}
