<<<<<<< HEAD
package com.chernikovs.gui;

import com.chernikovs.controllers.GuiController;
import lwjgui.geometry.Insets;
import lwjgui.paint.Color;
import lwjgui.scene.Node;
import lwjgui.scene.Window;
import lwjgui.scene.control.*;
import lwjgui.scene.layout.GridPane;
import lwjgui.scene.layout.StackPane;
import org.joml.Vector3f;

import static com.chernikovs.controllers.Attribs.*;

/**A class describing elements of graphical user interface that represent curve options menu*/
public class CurveOptions extends Options {

    private CheckBox frenet = new CheckBox("Frenet frame");
    private TextField var = createTextField(tfWidth, tfHeight);
    private TextField from = createTextField(tfWidth, tfHeight);
    private TextField to = createTextField(tfWidth, tfHeight);
    private TextField step = createTextField(tfWidth, tfHeight);
    private CheckBox pi = new CheckBox("*pi");

    /**
     * Create content attached to certain window
     * @param window window to attach content to
     * */
    public CurveOptions(Window window) {
        this.window = window;

        base.setMinSize(width, height);
        base.setBackgroundLegacy(Color.WHITE_SMOKE);

        GridPane functionDescribing = new GridPane();
        functionDescribing.setMinSize(width, height * 0.65625 + indent * 2);

        Label function = new Label("Function:");
        function.setPadding(new Insets(10, 0, 0, 10));

        functionText.setMinSize(width - indent * 2, height * 0.59375);
        functionText.setPrompt("x+y");
        functionText.setFontSize(30);

        functionDescribing.add(createWrapper(function, "L", width, height * 0.0625), 1, 1);
        functionDescribing.add(createWrapper(functionText, "C", width, height * 0.59375 + indent * 2), 1, 2);

        GridPane otherOptions = new GridPane();
        otherOptions.setMinSize(width, height * 0.1875);

        Label color = new Label("Color:");

        GridPane colorOptions = new GridPane();
        colorOptions.setMinSize(width * 0.5, height * 0.1875);
        colorOptions.add(createWrapper(color, "C", width * 0.5, height * 0.1875 / 2), 1, 1);
        colorOptions.add(createWrapper(colorPicker, "C", width * 0.5, height * 0.1875 / 2), 1, 2);

        GridPane planeAndParaboloidOptions = new GridPane();
        planeAndParaboloidOptions.setMinSize(width * 0.5, height * 0.1875);
        planeAndParaboloidOptions.add(createWrapper(frenet, "L", width * 0.5, height * 0.1875), 1, 1);

        otherOptions.add(colorOptions, 1, 1);
        otherOptions.add(planeAndParaboloidOptions, 2, 1);

        GridPane vars = new GridPane();
        vars.setMinSize(width, height * 0.21875);
        vars.add(createVariableOptions("Variable:", width, height * 0.21875 / 2, var, from, to, step, pi), 1, 1);

        analyse.setFontSize(20);
        analyse.setMinSize(100, 30);

        Button close = new Button("Close");
        close.setOnAction(event->{
            GuiController.hideCurrentOptions();
            GuiController.setCurveOptions();
            GuiController.hideCurrentOptions();
        });
        close.setFontSize(20);
        close.setMinSize(100, 30);

        GridPane btns = new GridPane();
        btns.add(createWrapper(analyse, "C", width / 2, 40), 1, 1);
        btns.add(createWrapper(close, "C", width / 2, 40), 2, 1);

        base.add(functionDescribing, 1, 1);
        base.add(otherOptions, 1, 2);
        base.add(vars, 1, 3);
        base.add(/*createWrapper(close, "C", width, 40)*/btns, 1, 4);
    }

    /**
     * Bind certain curve to this menu
     * @param index primary key of the curve
     * */
    @Override
    public void bind(int index) {
        functionText.setOnTextChange(event->{
            String t = functionText.getText();
            putFunction(index, t);
            getField(index).setText(t);
            redraw(index);
        });
        functionText.setText(getFunction(index));

        colorPicker.setOnAction(event->{
            Color c = colorPicker.getColor();
            putColour(index, new Vector3f(c.getRedF(), c.getGreenF(), c.getBlueF()));
            getFrame(index).setBackgroundLegacy(c);
            change(index);
        });
        Vector3f colour = getColour(index);
        colorPicker.setColor(new Color(colour.x, colour.y, colour.z));

        frenet.setOnAction(event->{
            putFrenet(index, frenet.isChecked());
            change(index);
        });
        frenet.setChecked(getFrenet(index));

        var.setOnTextChange(event->{
            putFVar(index, var.getText());
            redraw(index);
        });
        var.setText(getFVar(index));

        from.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(from.getText());
            } catch (NumberFormatException exc) {
                value = 0.0f;
            }
            putFFrom(index, value);
            redraw(index);
        });
        from.setText(String.valueOf(getFFrom(index)));

        to.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(to.getText());
            } catch (NumberFormatException exc) {
                value = 0.0f;
            }
            putFTo(index, value);
            redraw(index);
        });
        to.setText(String.valueOf(getFTo(index)));

        step.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(step.getText());
            } catch (NumberFormatException exc) {
                value = 0.1f;
            }
            putFStep(index, value);
            redraw(index);
        });
        step.setText(String.valueOf(getFStep(index)));

        pi.setOnAction(event->{
            putFPi(index, pi.isChecked());
            redraw(index);
        });
        pi.setChecked(getFPi(index));

        analyse.setOnAction(event->{
            if (functionText.getText().isEmpty()) {
                return;
            }
            GuiController.hideCurrentAnalysis();
            GuiController.setCurveAnalysis();
            GuiController.bindCurrentAnalysis(index);
            GuiController.showCurrentAnalysis();

            GuiController.hideCurrentOptions();
            GuiController.setCurveOptions();
            GuiController.hideCurrentOptions();
        });
    }

}
=======
package com.chernikovs.gui;

import com.chernikovs.controllers.GuiController;
import lwjgui.geometry.Insets;
import lwjgui.paint.Color;
import lwjgui.scene.Node;
import lwjgui.scene.Window;
import lwjgui.scene.control.*;
import lwjgui.scene.layout.GridPane;
import lwjgui.scene.layout.StackPane;
import org.joml.Vector3f;

import static com.chernikovs.controllers.Attribs.*;

/**A class describing elements of graphical user interface that represent curve options menu*/
public class CurveOptions extends Options {

    private CheckBox frenet = new CheckBox("Frenet frame");
    private TextField var = createTextField(tfWidth, tfHeight);
    private TextField from = createTextField(tfWidth, tfHeight);
    private TextField to = createTextField(tfWidth, tfHeight);
    private TextField step = createTextField(tfWidth, tfHeight);
    private CheckBox pi = new CheckBox("*pi");

    /**
     * Create content attached to certain window
     * @param window window to attach content to
     * */
    public CurveOptions(Window window) {
        this.window = window;

        base.setMinSize(width, height);
        base.setBackgroundLegacy(Color.WHITE_SMOKE);

        GridPane functionDescribing = new GridPane();
        functionDescribing.setMinSize(width, height * 0.65625 + indent * 2);

        Label function = new Label("Function:");
        function.setPadding(new Insets(10, 0, 0, 10));

        functionText.setMinSize(width - indent * 2, height * 0.59375);
        functionText.setPrompt("x+y");
        functionText.setFontSize(30);

        functionDescribing.add(createWrapper(function, "L", width, height * 0.0625), 1, 1);
        functionDescribing.add(createWrapper(functionText, "C", width, height * 0.59375 + indent * 2), 1, 2);

        GridPane otherOptions = new GridPane();
        otherOptions.setMinSize(width, height * 0.1875);

        Label color = new Label("Color:");

        GridPane colorOptions = new GridPane();
        colorOptions.setMinSize(width * 0.5, height * 0.1875);
        colorOptions.add(createWrapper(color, "C", width * 0.5, height * 0.1875 / 2), 1, 1);
        colorOptions.add(createWrapper(colorPicker, "C", width * 0.5, height * 0.1875 / 2), 1, 2);

        GridPane planeAndParaboloidOptions = new GridPane();
        planeAndParaboloidOptions.setMinSize(width * 0.5, height * 0.1875);
        planeAndParaboloidOptions.add(createWrapper(frenet, "L", width * 0.5, height * 0.1875), 1, 1);

        otherOptions.add(colorOptions, 1, 1);
        otherOptions.add(planeAndParaboloidOptions, 2, 1);

        GridPane vars = new GridPane();
        vars.setMinSize(width, height * 0.21875);
        vars.add(createVariableOptions("Variable:", width, height * 0.21875 / 2, var, from, to, step, pi), 1, 1);

        analyse.setFontSize(20);
        analyse.setMinSize(100, 30);

        Button close = new Button("Close");
        close.setOnAction(event->{
            GuiController.hideCurrentOptions();
            GuiController.setCurveOptions();
            GuiController.hideCurrentOptions();
        });
        close.setFontSize(20);
        close.setMinSize(100, 30);

        GridPane btns = new GridPane();
        btns.add(createWrapper(analyse, "C", width / 2, 40), 1, 1);
        btns.add(createWrapper(close, "C", width / 2, 40), 2, 1);

        base.add(functionDescribing, 1, 1);
        base.add(otherOptions, 1, 2);
        base.add(vars, 1, 3);
        base.add(/*createWrapper(close, "C", width, 40)*/btns, 1, 4);
    }

    /**
     * Bind certain curve to this menu
     * @param index primary key of the curve
     * */
    @Override
    public void bind(int index) {
        functionText.setOnTextChange(event->{
            String t = functionText.getText();
            putFunction(index, t);
            getField(index).setText(t);
            redraw(index);
        });
        functionText.setText(getFunction(index));

        colorPicker.setOnAction(event->{
            Color c = colorPicker.getColor();
            putColour(index, new Vector3f(c.getRedF(), c.getGreenF(), c.getBlueF()));
            getFrame(index).setBackgroundLegacy(c);
            change(index);
        });
        Vector3f colour = getColour(index);
        colorPicker.setColor(new Color(colour.x, colour.y, colour.z));

        frenet.setOnAction(event->{
            putFrenet(index, frenet.isChecked());
            change(index);
        });
        frenet.setChecked(getFrenet(index));

        var.setOnTextChange(event->{
            putFVar(index, var.getText());
            redraw(index);
        });
        var.setText(getFVar(index));

        from.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(from.getText());
            } catch (NumberFormatException exc) {
                value = 0.0f;
            }
            putFFrom(index, value);
            redraw(index);
        });
        from.setText(String.valueOf(getFFrom(index)));

        to.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(to.getText());
            } catch (NumberFormatException exc) {
                value = 0.0f;
            }
            putFTo(index, value);
            redraw(index);
        });
        to.setText(String.valueOf(getFTo(index)));

        step.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(step.getText());
            } catch (NumberFormatException exc) {
                value = 0.1f;
            }
            putFStep(index, value);
            redraw(index);
        });
        step.setText(String.valueOf(getFStep(index)));

        pi.setOnAction(event->{
            putFPi(index, pi.isChecked());
            redraw(index);
        });
        pi.setChecked(getFPi(index));

        analyse.setOnAction(event->{
            if (functionText.getText().isEmpty()) {
                return;
            }
            GuiController.hideCurrentAnalysis();
            GuiController.setCurveAnalysis();
            GuiController.bindCurrentAnalysis(index);
            GuiController.showCurrentAnalysis();

            GuiController.hideCurrentOptions();
            GuiController.setCurveOptions();
            GuiController.hideCurrentOptions();
        });
    }

}
>>>>>>> 4f3c886f3ff1ae2c84328c3abe8a64dfc85d4b78
