<<<<<<< HEAD
package com.chernikovs.gui;

import com.chernikovs.controllers.GuiController;
import com.chernikovs.renderEngine.Display;
import com.chernikovs.toolbox.Maths;
import lwjgui.geometry.Insets;
import lwjgui.scene.Node;
import lwjgui.scene.Window;
import lwjgui.scene.control.Label;
import lwjgui.scene.control.Slider;
import lwjgui.scene.control.TextField;
import lwjgui.scene.layout.GridPane;
import lwjgui.scene.layout.StackPane;
import lwjgui.scene.layout.floating.DraggablePane;

import static com.chernikovs.controllers.Attribs.*;
import static com.chernikovs.controllers.Attribs.change;

/**A class describing elements of graphical user interface that represent menu for curve analysis*/
public class CurveAnalysis extends Analysis {

    private Label length = createLabel("", labelColor, fontSize);
    private TextField from = createTextField(textFieldWidth, textColor, (int)fontSize);
    private TextField to = createTextField(textFieldWidth, textColor, (int)fontSize);

    private Label curvature = createLabel("", labelColor, fontSize);
    private Label torsion = createLabel("", labelColor, fontSize);

    private Slider slider = new Slider();
    private TextField sliderValue = createTextField(textFieldWidth, textColor, (int)fontSize);

    /**
     * Create content attached to certain window
     * @param window window to attach content to
     * */
    public CurveAnalysis(Window window) {
        this.window = window;

        DraggablePane lengthPane = new DraggablePane();
        lengthPane.setAbsolutePosition(0, 0);

        GridPane lengthGrid = new GridPane();

        lengthGrid.add(createLabel("Length from ", labelColor, fontSize), 1, 1);
        lengthGrid.add(from, 2, 1);
        lengthGrid.add(createLabel("to", labelColor, fontSize), 3, 1);
        lengthGrid.add(to, 4, 1);
        lengthGrid.add(createLabel(": ", labelColor, fontSize), 5, 1);
        lengthGrid.add(length, 6, 1);

        lengthPane.getChildren().add(lengthGrid);

        DraggablePane curvaturePane = new DraggablePane();
        curvaturePane.setAbsolutePosition(0, lineHeight);

        GridPane curvatureGrid = new GridPane();
        curvatureGrid.add(createLabel("Curvature: ", labelColor, fontSize), 1, 1);
        curvatureGrid.add(curvature, 2, 1);

        curvaturePane.getChildren().add(curvatureGrid);

        DraggablePane torsionPane = new DraggablePane();
        torsionPane.setAbsolutePosition(0, lineHeight * 2);

        GridPane torsionGrid = new GridPane();
        torsionGrid.add(createLabel("Torsion: ", labelColor, fontSize), 1, 1);
        torsionGrid.add(torsion, 2, 1);

        torsionPane.getChildren().add(torsionGrid);

        DraggablePane sliderPane = new DraggablePane();
        sliderPane.setAbsolutePosition(0, Display.getHeight() - lineHeight);

        slider.setPadding(new Insets(lineHeight / 6, 0, 0, 0));
        slider.setPrefWidth(250);

        GridPane sliderGrid = new GridPane();
        sliderGrid.add(slider, 1, 1);
        sliderGrid.add(sliderValue, 2, 1);

        sliderPane.getChildren().add(sliderGrid);

        DraggablePane finishPane = new DraggablePane();
        finishPane.setAbsolutePosition(Display.getWidth() * Display.getRenderPart() - 100, Display.getHeight() - lineHeight);

        finish.setPrefSize(100, lineHeight);
        finish.setFontSize(20);

        GridPane finishGrid = new GridPane();
        finishGrid.add(finish, 1, 1);

        finishPane.getChildren().add(finishGrid);

        finish.setOnAction(event->{
            GuiController.setCurveAnalysis();
            GuiController.hideCurrentAnalysis();
        });

        base.add(lengthPane);
        base.add(curvaturePane);
        base.add(torsionPane);
        base.add(sliderPane);
        base.add(finishPane);
    }

    /**
     * Bind certain curve to this menu
     * @param index primary key of the curve
     * */
    @Override
    public void bind(int index) {
        float from_ = getFFrom(index) * (getFPi(index) ? (float)Math.PI : 1);
        float to_ = getFTo(index) * (getFPi(index) ? (float)Math.PI : 1);

        from.setOnTextChange(event->{
            float from__ = 0.0f;
            float to__ = 0.0f;
            try {
                from__ = Float.parseFloat(from.getText());
                to__ = Float.parseFloat(to.getText());
            } catch (Exception exc) {
                from__ = 0.0f;
                to__ = 0.0f;
            }
            length.setText(String.valueOf((float)getCurve(index).length(from__, to__)));
        });
        to.setOnTextChange(event->{
            float from__ = 0.0f;
            float to__ = 0.0f;
            try {
                from__ = Float.parseFloat(from.getText());
                to__ = Float.parseFloat(to.getText());
            } catch (Exception exc) {
                from__ = 0.0f;
                to__ = 0.0f;
            }
            length.setText(String.valueOf((float)getCurve(index).length(from__, to__)));
        });

        length.setText(String.valueOf((float)getCurve(index).length(from_, to_)));
        from.setText(String.valueOf(from_));
        to.setText(String.valueOf(to_));

        curvature.setText(String.valueOf((float)getCurve(index).getCurvature(getT0(index))));
        torsion.setText(String.valueOf((float)getCurve(index).getTorsion(getT0(index))));

        slider.setOnValueChangedEvent(event->{
            float t0_ = from_ + (float)slider.getValue() * (to_ - from_) / 100;
            sliderValue.setText(String.valueOf(t0_));
        });
        slider.setValue(100 * (getT0(index) - from_) / (to_ - from_));

        sliderValue.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(sliderValue.getText());
            } catch (Exception exc) {
                value = from_;
            }
            curvature.setText(String.valueOf((float)getCurve(index).getCurvature(value)));
            torsion.setText(String.valueOf((float)getCurve(index).getTorsion(value)));
            float sliderVal = 100 * (value - from_) / (to_ - from_);
            if (Math.abs(sliderVal - slider.getValue()) > Maths.epsilon) {
                slider.setValue(sliderVal);
            }
            putT0(index, value);
            change(index);
        });
        sliderValue.setText(String.valueOf(getT0(index)));

    }

}
=======
package com.chernikovs.gui;

import com.chernikovs.controllers.GuiController;
import com.chernikovs.renderEngine.Display;
import com.chernikovs.toolbox.Maths;
import lwjgui.geometry.Insets;
import lwjgui.scene.Node;
import lwjgui.scene.Window;
import lwjgui.scene.control.Label;
import lwjgui.scene.control.Slider;
import lwjgui.scene.control.TextField;
import lwjgui.scene.layout.GridPane;
import lwjgui.scene.layout.StackPane;
import lwjgui.scene.layout.floating.DraggablePane;

import static com.chernikovs.controllers.Attribs.*;
import static com.chernikovs.controllers.Attribs.change;

/**A class describing elements of graphical user interface that represent menu for curve analysis*/
public class CurveAnalysis extends Analysis {

    private Label length = createLabel("", labelColor, fontSize);
    private TextField from = createTextField(textFieldWidth, textColor, (int)fontSize);
    private TextField to = createTextField(textFieldWidth, textColor, (int)fontSize);

    private Label curvature = createLabel("", labelColor, fontSize);
    private Label torsion = createLabel("", labelColor, fontSize);

    private Slider slider = new Slider();
    private TextField sliderValue = createTextField(textFieldWidth, textColor, (int)fontSize);

    /**
     * Create content attached to certain window
     * @param window window to attach content to
     * */
    public CurveAnalysis(Window window) {
        this.window = window;

        DraggablePane lengthPane = new DraggablePane();
        lengthPane.setAbsolutePosition(0, 0);

        GridPane lengthGrid = new GridPane();

        lengthGrid.add(createLabel("Length from ", labelColor, fontSize), 1, 1);
        lengthGrid.add(from, 2, 1);
        lengthGrid.add(createLabel("to", labelColor, fontSize), 3, 1);
        lengthGrid.add(to, 4, 1);
        lengthGrid.add(createLabel(": ", labelColor, fontSize), 5, 1);
        lengthGrid.add(length, 6, 1);

        lengthPane.getChildren().add(lengthGrid);

        DraggablePane curvaturePane = new DraggablePane();
        curvaturePane.setAbsolutePosition(0, lineHeight);

        GridPane curvatureGrid = new GridPane();
        curvatureGrid.add(createLabel("Curvature: ", labelColor, fontSize), 1, 1);
        curvatureGrid.add(curvature, 2, 1);

        curvaturePane.getChildren().add(curvatureGrid);

        DraggablePane torsionPane = new DraggablePane();
        torsionPane.setAbsolutePosition(0, lineHeight * 2);

        GridPane torsionGrid = new GridPane();
        torsionGrid.add(createLabel("Torsion: ", labelColor, fontSize), 1, 1);
        torsionGrid.add(torsion, 2, 1);

        torsionPane.getChildren().add(torsionGrid);

        DraggablePane sliderPane = new DraggablePane();
        sliderPane.setAbsolutePosition(0, Display.getHeight() - lineHeight);

        slider.setPadding(new Insets(lineHeight / 6, 0, 0, 0));
        slider.setPrefWidth(250);

        GridPane sliderGrid = new GridPane();
        sliderGrid.add(slider, 1, 1);
        sliderGrid.add(sliderValue, 2, 1);

        sliderPane.getChildren().add(sliderGrid);

        DraggablePane finishPane = new DraggablePane();
        finishPane.setAbsolutePosition(Display.getWidth() * Display.getRenderPart() - 100, Display.getHeight() - lineHeight);

        finish.setPrefSize(100, lineHeight);
        finish.setFontSize(20);

        GridPane finishGrid = new GridPane();
        finishGrid.add(finish, 1, 1);

        finishPane.getChildren().add(finishGrid);

        finish.setOnAction(event->{
            GuiController.setCurveAnalysis();
            GuiController.hideCurrentAnalysis();
        });

        base.add(lengthPane);
        base.add(curvaturePane);
        base.add(torsionPane);
        base.add(sliderPane);
        base.add(finishPane);
    }

    /**
     * Bind certain curve to this menu
     * @param index primary key of the curve
     * */
    @Override
    public void bind(int index) {
        float from_ = getFFrom(index) * (getFPi(index) ? (float)Math.PI : 1);
        float to_ = getFTo(index) * (getFPi(index) ? (float)Math.PI : 1);

        from.setOnTextChange(event->{
            float from__ = 0.0f;
            float to__ = 0.0f;
            try {
                from__ = Float.parseFloat(from.getText());
                to__ = Float.parseFloat(to.getText());
            } catch (Exception exc) {
                from__ = 0.0f;
                to__ = 0.0f;
            }
            length.setText(String.valueOf((float)getCurve(index).length(from__, to__)));
        });
        to.setOnTextChange(event->{
            float from__ = 0.0f;
            float to__ = 0.0f;
            try {
                from__ = Float.parseFloat(from.getText());
                to__ = Float.parseFloat(to.getText());
            } catch (Exception exc) {
                from__ = 0.0f;
                to__ = 0.0f;
            }
            length.setText(String.valueOf((float)getCurve(index).length(from__, to__)));
        });

        length.setText(String.valueOf((float)getCurve(index).length(from_, to_)));
        from.setText(String.valueOf(from_));
        to.setText(String.valueOf(to_));

        curvature.setText(String.valueOf((float)getCurve(index).getCurvature(getT0(index))));
        torsion.setText(String.valueOf((float)getCurve(index).getTorsion(getT0(index))));

        slider.setOnValueChangedEvent(event->{
            float t0_ = from_ + (float)slider.getValue() * (to_ - from_) / 100;
            sliderValue.setText(String.valueOf(t0_));
        });
        slider.setValue(100 * (getT0(index) - from_) / (to_ - from_));

        sliderValue.setOnTextChange(event->{
            float value = 0.0f;
            try {
                value = Float.parseFloat(sliderValue.getText());
            } catch (Exception exc) {
                value = from_;
            }
            curvature.setText(String.valueOf((float)getCurve(index).getCurvature(value)));
            torsion.setText(String.valueOf((float)getCurve(index).getTorsion(value)));
            float sliderVal = 100 * (value - from_) / (to_ - from_);
            if (Math.abs(sliderVal - slider.getValue()) > Maths.epsilon) {
                slider.setValue(sliderVal);
            }
            putT0(index, value);
            change(index);
        });
        sliderValue.setText(String.valueOf(getT0(index)));

    }

}
>>>>>>> 4f3c886f3ff1ae2c84328c3abe8a64dfc85d4b78
