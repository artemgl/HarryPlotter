<<<<<<< HEAD
package com.chernikovs.gui;

import lwjgui.geometry.Insets;
import lwjgui.paint.Color;
import lwjgui.scene.Node;
import lwjgui.scene.Window;
import lwjgui.scene.control.Button;
import lwjgui.scene.control.Label;
import lwjgui.scene.control.TextField;

import java.util.ArrayList;

/**An abstract class describing elements of graphical user interface that represent menu for analysis*/
public abstract class Analysis {

    protected Window window;

    protected ArrayList<Node> base = new ArrayList<>();

    protected double lineHeight = 30;
    protected double textFieldWidth = 40;
    protected float fontSize = 20;
    protected Color labelColor = Color.WHITE;
    protected Color textColor = Color.BLACK;

    protected Button finish = new Button("Finish");

    /**
     * Get menu for analysis
     * @return menu for analysis
     * */
    public ArrayList<Node> getBase() {
        return base;
    }

    /**
     * Bind certain geometric object to this menu
     * @param index primary key of the geometric object
     * */
    public abstract void bind(int index);

    protected static Label createLabel(String text, Color color, float size) {
        Label result = new Label(text);
        result.setTextFill(color);
        result.setFontSize(size);
        result.setPadding(new Insets(5, 0, 0, 0));
        return result;
    }

    protected static TextField createTextField(double width, Color fontColor, int fontSize) {
        TextField result = new TextField();
        result.setFontSize(fontSize);
        result.setFontFill(fontColor);
        result.setPrefWidth(width);
        return result;
    }

}
=======
package com.chernikovs.gui;

import lwjgui.geometry.Insets;
import lwjgui.paint.Color;
import lwjgui.scene.Node;
import lwjgui.scene.Window;
import lwjgui.scene.control.Button;
import lwjgui.scene.control.Label;
import lwjgui.scene.control.TextField;

import java.util.ArrayList;

/**An abstract class describing elements of graphical user interface that represent menu for analysis*/
public abstract class Analysis {

    protected Window window;

    protected ArrayList<Node> base = new ArrayList<>();

    protected double lineHeight = 30;
    protected double textFieldWidth = 40;
    protected float fontSize = 20;
    protected Color labelColor = Color.WHITE;
    protected Color textColor = Color.BLACK;

    protected Button finish = new Button("Finish");

    /**
     * Get menu for analysis
     * @return menu for analysis
     * */
    public ArrayList<Node> getBase() {
        return base;
    }

    /**
     * Bind certain geometric object to this menu
     * @param index primary key of the geometric object
     * */
    public abstract void bind(int index);

    protected static Label createLabel(String text, Color color, float size) {
        Label result = new Label(text);
        result.setTextFill(color);
        result.setFontSize(size);
        result.setPadding(new Insets(5, 0, 0, 0));
        return result;
    }

    protected static TextField createTextField(double width, Color fontColor, int fontSize) {
        TextField result = new TextField();
        result.setFontSize(fontSize);
        result.setFontFill(fontColor);
        result.setPrefWidth(width);
        return result;
    }

}
>>>>>>> 4f3c886f3ff1ae2c84328c3abe8a64dfc85d4b78
