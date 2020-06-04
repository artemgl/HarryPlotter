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

/**A class describing elements of graphical user interface that represent surface options menu*/
public class SurfaceOptions extends Options {

    private RadioButton gridMode = new RadioButton();
    private RadioButton solidMode = new RadioButton();
    private CheckBox tangentPlane = new CheckBox("Tangent plane");
    private CheckBox osculatingParaboloid = new CheckBox("Osculating paraboloid");
    private CheckBox mainDirections = new CheckBox("Main directions");
    private TextField fvar = createTextField(tfWidth, tfHeight);
    private TextField ffrom = createTextField(tfWidth, tfHeight);
    private TextField fto = createTextField(tfWidth, tfHeight);
    private TextField fstep = createTextField(tfWidth, tfHeight);
    private TextField svar = createTextField(tfWidth, tfHeight);
    private TextField sfrom = createTextField(tfWidth, tfHeight);
    private TextField sto = createTextField(tfWidth, tfHeight);
    private TextField sstep = createTextField(tfWidth, tfHeight);
    private CheckBox fpi = new CheckBox("*pi");
    private CheckBox spi = new CheckBox("*pi");

    /**
     * Create content attached to certain window
     * @param window window to attach content to
     * */
    public SurfaceOptions(Window window) {
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

        Label mode = new Label("Mode:");
        mode.setPadding(new Insets(0, 0, 0, 10));

        Label gridLabel = new Label("  Grid", gridMode);
        gridLabel.setContentDisplay(ContentDisplay.LEFT);
        gridLabel.setPadding(new Insets(0, 0, 0, 10));

        Label solidLabel = new Label("  Solid", solidMode);
        solidLabel.setContentDisplay(ContentDisplay.LEFT);
        solidLabel.setPadding(new Insets(0, 0, 0, 10));

        GridPane modeOptions = new GridPane();
        modeOptions.setMinSize(width * 0.2, height * 0.1875);
        modeOptions.add(createWrapper(mode, "L", width * 0.2, height * 0.1875 / 3), 1, 1);
        modeOptions.add(createWrapper(gridLabel, "L", width * 0.2, height * 0.1875 / 3), 1, 2);
        modeOptions.add(createWrapper(solidLabel, "L", width * 0.2, height * 0.1875 / 3), 1, 3);

        Label color = new Label("Color:");

        GridPane colorOptions = new GridPane();
        colorOptions.setMinSize(width * 0.3, height * 0.1875);
        colorOptions.add(createWrapper(color, "C", width * 0.3, height * 0.1875 / 2), 1, 1);
        colorOptions.add(createWrapper(colorPicker, "C", width * 0.3, height * 0.1875 / 2), 1, 2);

        GridPane planeAndParaboloidOptions = new GridPane();
        planeAndParaboloidOptions.setMinSize(width * 0.5, height * 0.1875);
        planeAndParaboloidOptions.add(createWrapper(tangentPlane, "L", width * 0.5, height * 0.1875 / 3), 1, 1);
        planeAndParaboloidOptions.add(createWrapper(osculatingParaboloid, "L", width * 0.5, height * 0.1875 / 3), 1, 2);
        planeAndParaboloidOptions.add(createWrapper(mainDirections, "L", width * 0.5, height * 0.1875 / 3), 1, 3);

        otherOptions.add(modeOptions, 1, 1);
        otherOptions.add(colorOptions, 2, 1);
        otherOptions.add(planeAndParaboloidOptions, 3, 1);

        GridPane vars = new GridPane();
        vars.setMinSize(width, height * 0.21875);
        vars.add(createVariableOptions("First variable:", width, height * 0.21875 / 2, fvar, ffrom, fto, fstep, fpi), 1, 1);
        vars.add(createVariableOptions("Second variable:", width, height * 0.21875 / 2, svar, sfrom, sto, sstep, spi), 1, 2);

        analyse.setFontSize(20);
        analyse.setMinSize(100, 30);

        Button close = new Button("Close");
        close.setOnAction(event->{
            GuiController.hideCurrentOptions();
            GuiController.setSurfaceOptions();
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
        base.add(btns, 1, 4);
    }

    /**
     * Bind certain surface to this menu
     * @param index primary key of the surface
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

        gridMode.setOnSelectedEvent(event->{
            putMode(index, "Grid");
            solidMode.setSelected(false);
            change(index);
        });
        solidMode.setOnSelectedEvent(event->{
            putMode(index, "Solid");
            gridMode.setSelected(false);
            change(index);
        });
        if (getMode(index).equals("Grid")) {
            gridMode.setSelected(true);
            solidMode.setSelected(false);
        }
        if (getMode(index).equals("Solid")) {
            gridMode.setSelected(false);
            solidMode.setSelected(true);
        }

        colorPicker.setOnAction(event->{
            Color c = colorPicker.getColor();
            putColour(index, new Vector3f(c.getRedF(), c.getGreenF(), c.getBlueF()));
            getFrame(index).setBackgroundLegacy(c);
            change(index);
        });
        Vector3f colour = getColour(index);
        colorPicker.setColor(new Color(colour.x, colour.y, colour.z));

        tangentPlane.setOnAction(event->{
            putPlane(index, tangentPlane.isChecked());
            change(index);
        });
        tangentPlane.setChecked(getPlane(index));

        osculatingParaboloid.setOnAction(event->{
            putParaboloid(index, osculatingParaboloid.isChecked());
            change(index);
        });
        osculatingParaboloid.setChecked(getParaboloid(index));

        mainDirections.setOnAction(event->{
            putMainDir(index, mainDirections.isChecked());
            change(index);
        });
        mainDirections.setChecked(getMainDir(index));

        fvar.setOnTextChange(event->{
            putFVar(index, fvar.getText());
            redraw(index);
        });
        fvar.setText(getFVar(index));

        ffrom.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(ffrom.getText());
            } catch (NumberFormatException exc) {
                value = 0.0f;
            }
            putFFrom(index, value);
            redraw(index);
        });
        ffrom.setText(String.valueOf(getFFrom(index)));

        fto.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(fto.getText());
            } catch (NumberFormatException exc) {
                value = 0.0f;
            }
            putFTo(index, value);
            redraw(index);
        });
        fto.setText(String.valueOf(getFTo(index)));

        fstep.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(fstep.getText());
            } catch (NumberFormatException exc) {
                value = 0.0f;
            }
            if (value == 0.0f) {
                value = 0.1f;
            }
            putFStep(index, value);
            redraw(index);
        });
        fstep.setText(String.valueOf(getFStep(index)));

        svar.setOnTextChange(event->{
            putSVar(index, svar.getText());
            redraw(index);
        });
        svar.setText(getSVar(index));

        sfrom.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(sfrom.getText());
            } catch (NumberFormatException exc) {
                value = 0.0f;;
            }
            putSFrom(index, value);
            redraw(index);
        });
        sfrom.setText(String.valueOf(getSFrom(index)));

        sto.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(sto.getText());
            } catch (NumberFormatException exc) {
                value = 0.0f;;
            }
            putSTo(index, value);
            redraw(index);
        });
        sto.setText(String.valueOf(getSTo(index)));

        sstep.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(sstep.getText());
            } catch (NumberFormatException exc) {
                value = 0.0f;
            }
            if (value == 0.0f) {
                value = 0.1f;
            }
            putSStep(index, value);
            redraw(index);
        });
        sstep.setText(String.valueOf(getSStep(index)));

        fpi.setOnAction(event->{
            putFPi(index, fpi.isChecked());
            redraw(index);
        });
        fpi.setChecked(getFPi(index));

        spi.setOnAction(event->{
            putSPi(index, spi.isChecked());
            redraw(index);
        });
        spi.setChecked(getSPi(index));

        analyse.setOnAction(event->{
            if (functionText.getText().isEmpty()) {
                return;
            }
            GuiController.hideCurrentAnalysis();
            GuiController.setSurfaceAnalysis();
            GuiController.bindCurrentAnalysis(index);
            GuiController.showCurrentAnalysis();

            GuiController.hideCurrentOptions();
            GuiController.setSurfaceOptions();
            GuiController.hideCurrentOptions();
        });
    }

}
