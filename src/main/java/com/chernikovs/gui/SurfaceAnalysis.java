package com.chernikovs.gui;

import com.chernikovs.controllers.GuiController;
import com.chernikovs.renderEngine.Display;
import com.chernikovs.toolbox.Maths;
import lwjgui.geometry.Insets;
import lwjgui.geometry.Orientation;
import lwjgui.scene.Node;
import lwjgui.scene.Window;
import lwjgui.scene.control.*;
import lwjgui.scene.layout.GridPane;
import lwjgui.scene.layout.StackPane;
import lwjgui.scene.layout.floating.DraggablePane;
import org.joml.Vector2f;

import static com.chernikovs.controllers.Attribs.*;
import static com.chernikovs.controllers.Attribs.getu0v0MainDir;

/**A class describing elements of graphical user interface that represent menu for surface analysis*/
public class SurfaceAnalysis extends Analysis {

    private Label square = createLabel("", labelColor, fontSize);
    private TextField from1 = createTextField(textFieldWidth, textColor, (int)fontSize);
    private TextField from2 = createTextField(textFieldWidth, textColor, (int)fontSize);
    private TextField to1 = createTextField(textFieldWidth, textColor, (int)fontSize);
    private TextField to2 = createTextField(textFieldWidth, textColor, (int)fontSize);

    private Label gaussianCurvature = createLabel("", labelColor, fontSize);
    private Label averageCurvature = createLabel("", labelColor, fontSize);
    private Label mainCurvature1 = createLabel("", labelColor, fontSize);
    private Label mainCurvature2 = createLabel("", labelColor, fontSize);

    private Slider sliderU = new Slider();
    private Slider sliderV = new Slider();
    private TextField sliderUValue = createTextField(textFieldWidth, textColor, (int)fontSize);
    private TextField sliderVValue = createTextField(textFieldWidth, textColor, (int)fontSize);
    private ComboBox<String> comboBox = new ComboBox<>("Tangent plane");

    /**
     * Create content attached to certain window
     * @param window window to attach content to
     * */
    public SurfaceAnalysis(Window window) {
        this.window = window;

        DraggablePane squarePane = new DraggablePane();
        squarePane.setAbsolutePosition(0, 0);

        GridPane squareGrid = new GridPane();

        squareGrid.add(createLabel("Square from (", labelColor, fontSize), 1, 1);
        squareGrid.add(from1, 2, 1);
        squareGrid.add(createLabel(", ", labelColor, fontSize), 3, 1);
        squareGrid.add(from2, 4, 1);
        squareGrid.add(createLabel(") to (", labelColor, fontSize), 5, 1);
        squareGrid.add(to1, 6, 1);
        squareGrid.add(createLabel(", ", labelColor, fontSize), 7, 1);
        squareGrid.add(to2, 8, 1);
        squareGrid.add(createLabel("): ", labelColor, fontSize), 9, 1);
        squareGrid.add(square, 10, 1);

        squarePane.getChildren().add(squareGrid);

        DraggablePane gaussianCurvaturePane = new DraggablePane();
        gaussianCurvaturePane.setAbsolutePosition(0, lineHeight);

        GridPane gaussianCurvatureGrid = new GridPane();
        gaussianCurvatureGrid.add(createLabel("Gaussian curvature: ", labelColor, fontSize), 1, 1);
        gaussianCurvatureGrid.add(gaussianCurvature, 2, 1);

        gaussianCurvaturePane.getChildren().add(gaussianCurvatureGrid);

        DraggablePane averageCurvaturePane = new DraggablePane();
        averageCurvaturePane.setAbsolutePosition(0, lineHeight * 2);

        GridPane averageCurvatureGrid = new GridPane();
        averageCurvatureGrid.add(createLabel("Average curvature: ", labelColor, fontSize), 1, 1);
        averageCurvatureGrid.add(averageCurvature, 2, 1);

        averageCurvaturePane.getChildren().add(averageCurvatureGrid);

        DraggablePane mainCurvaturesPane = new DraggablePane();
        mainCurvaturesPane.setAbsolutePosition(0, lineHeight * 3);

        GridPane mainCurvaturesGrid = new GridPane();
        mainCurvaturesGrid.add(createLabel("Main curvatures: ", labelColor, fontSize), 1, 1);
        mainCurvaturesGrid.add(mainCurvature1, 2, 1);
        mainCurvaturesGrid.add(createLabel(" and ", labelColor, fontSize), 3, 1);
        mainCurvaturesGrid.add(mainCurvature2, 4, 1);

        mainCurvaturesPane.getChildren().add(mainCurvaturesGrid);

        DraggablePane sliderUPane = new DraggablePane();
        sliderUPane.setAbsolutePosition(lineHeight, Display.getHeight() - lineHeight);

        DraggablePane sliderVPane = new DraggablePane();
        sliderVPane.setAbsolutePosition(0, Display.getHeight() - lineHeight - 250 - 28);

        sliderU.setPadding(new Insets(lineHeight / 6, 0, 0, 0));
        sliderU.setPrefWidth(250);

        sliderV.setOrientation(Orientation.VERTICAL);
        sliderV.setPadding(new Insets(0, 0, 0, lineHeight / 6));
        sliderV.setPrefHeight(250);

        comboBox.getItems().addAll("Tangent plane", "Osculating paraboloid", "Main directions");

        GridPane sliderUGrid = new GridPane();
        sliderUGrid.add(sliderU, 1, 1);
        sliderUGrid.add(sliderUValue, 2, 1);
        sliderUGrid.add(comboBox, 3, 1);

        GridPane sliderVGrid = new GridPane();
        sliderVGrid.add(sliderVValue, 1, 1);
        sliderVGrid.add(sliderV, 1, 2);

        sliderUPane.getChildren().add(sliderUGrid);
        sliderVPane.getChildren().add(sliderVGrid);

        DraggablePane finishPane = new DraggablePane();
        finishPane.setAbsolutePosition(Display.getWidth() * Display.getRenderPart() - 100, Display.getHeight() - lineHeight);

        Button finish = new Button("Finish");
        finish.setPrefSize(100, lineHeight);
        finish.setFontSize(20);

        GridPane finishGrid = new GridPane();
        finishGrid.add(finish, 1, 1);

        finishPane.getChildren().add(finishGrid);

        finish.setOnAction(event->{
            GuiController.setSurfaceAnalysis();
            GuiController.hideCurrentAnalysis();
        });

        base.add(squarePane);
        base.add(gaussianCurvaturePane);
        base.add(averageCurvaturePane);
        base.add(mainCurvaturesPane);
        base.add(sliderUPane);
        base.add(sliderVPane);
        base.add(finishPane);
    }

    /**
     * Bind certain surface to this menu
     * @param index primary key of the surface
     * */
    @Override
    public void bind(int index) {
        float from1_ = getFFrom(index) * (getFPi(index) ? (float)Math.PI : 1);
        float from2_ = getSFrom(index) * (getSPi(index) ? (float)Math.PI : 1);
        float to1_ = getFTo(index) * (getFPi(index) ? (float)Math.PI : 1);
        float to2_ = getSTo(index) * (getSPi(index) ? (float)Math.PI : 1);

        from1.setOnTextChange(event->squareChange(index, square, from1, from2, to2, to2));
        from2.setOnTextChange(event->squareChange(index, square, from1, from2, to2, to2));
        to1.setOnTextChange(event->squareChange(index, square, from1, from2, to2, to2));
        to2.setOnTextChange(event->squareChange(index, square, from1, from2, to2, to2));

        square.setText(String.valueOf((float)getSurface(index).square(from1_, from2_, to1_, to2_)));
        from1.setText(String.valueOf(from1_));
        from2.setText(String.valueOf(from2_));
        to1.setText(String.valueOf(to1_));
        to2.setText(String.valueOf(to2_));

        Vector2f u0v0Plane = getu0v0Plane(index);
        gaussianCurvature.setText(String.valueOf((float)getSurface(index).getGaussianCurvature(u0v0Plane.x, u0v0Plane.y)));
        averageCurvature.setText(String.valueOf((float)getSurface(index).getAverageCurvature(u0v0Plane.x, u0v0Plane.y)));

        double[] mainCurvatures = getSurface(index).getMainCurvatures(u0v0Plane.x, u0v0Plane.y);
        mainCurvature1.setText(String.valueOf((float)mainCurvatures[0]));
        mainCurvature2.setText(String.valueOf((float)mainCurvatures[1]));

        sliderU.setOnValueChangedEvent(event->{
            float u0_ = from1_ + (float)sliderU.getValue() * (to1_ - from1_) / 100;
            sliderUValue.setText(String.valueOf(u0_));
        });
        sliderV.setOnValueChangedEvent(event->{
            float v0_ = to2_ + (float)sliderV.getValue() * (from2_ - to2_) / 100;
            sliderVValue.setText(String.valueOf(v0_));
        });
        sliderU.setValue(100 * (u0v0Plane.x - from1_) / (to1_ - from1_));
        sliderV.setValue(100 * (u0v0Plane.y - from2_) / (to2_ - from2_));

        sliderUValue.setOnTextChange(event->pointChange(index, from1_, from2_, to1_, to2_));
        sliderUValue.setText(String.valueOf(u0v0Plane.x));

        sliderVValue.setOnTextChange(event->pointChange(index, from1_, from2_, to1_, to2_));
        sliderVValue.setText(String.valueOf(u0v0Plane.y));

        comboBox.setOnAction(event->{
            switch (comboBox.getValue()) {
                case "Tangent plane":
                    sliderU.setValue(100 * (getu0v0Plane(index).x - from1_) / (to1_ - from1_));
                    sliderV.setValue(100 * (getu0v0Plane(index).y - from2_) / (to2_ - from2_));
                    break;
                case "Osculating paraboloid":
                    sliderU.setValue(100 * (getu0v0Paraboloid(index).x - from1_) / (to1_ - from1_));
                    sliderV.setValue(100 * (getu0v0Paraboloid(index).y - from2_) / (to2_ - from2_));
                    break;
                case "Main directions":
                    sliderU.setValue(100 * (getu0v0MainDir(index).x - from1_) / (to1_ - from1_));
                    sliderV.setValue(100 * (getu0v0MainDir(index).y - from2_) / (to2_ - from2_));
                    break;
            }
        });
    }

    private void squareChange(int index, Label square, TextField from1, TextField from2, TextField to1, TextField to2) {
        float from1__ = 0.0f;
        float from2__ = 0.0f;
        float to1__ = 0.0f;
        float to2__ = 0.0f;
        try {
            from1__ = Float.parseFloat(from1.getText());
            from2__ = Float.parseFloat(from2.getText());
            to1__ = Float.parseFloat(to1.getText());
            to2__ = Float.parseFloat(to2.getText());
        } catch (Exception exc) {
            from1__ = 0.0f;
            from2__ = 0.0f;
            to1__ = 0.0f;
            to2__ = 0.0f;
        }
        square.setText(String.valueOf((float)getSurface(index).square(from1__, from2__, to1__, to2__)));
    }

    private void pointChange(int index, float from1_, float from2_, float to1_, float to2_) {
        float u = 0.0f;
        float v = 0.0f;
        try {
            u = Float.parseFloat(sliderUValue.getText());
            v = Float.parseFloat(sliderVValue.getText());
        } catch (Exception exc) {
            u = from1_;
            v = from2_;
        }
        gaussianCurvature.setText(String.valueOf((float)getSurface(index).getGaussianCurvature(u, v)));
        averageCurvature.setText(String.valueOf((float)getSurface(index).getAverageCurvature(u, v)));
        mainCurvature1.setText(String.valueOf((float)getSurface(index).getMainCurvatures(u, v)[0]));
        mainCurvature2.setText(String.valueOf((float)getSurface(index).getMainCurvatures(u, v)[1]));
        float sliderUVal = 100 * (u - from1_) / (to1_ - from1_);
        float sliderVVal = 100 * (v - to2_) / (from2_ - to2_);
        if (Math.abs(sliderUVal - sliderU.getValue()) > Maths.epsilon) {
            sliderU.setValue(sliderUVal);
        }
        if (Math.abs(sliderVVal - sliderV.getValue()) > Maths.epsilon) {
            sliderV.setValue(sliderVVal);
        }
        String comboBoxVal = comboBox.getValue();
        switch (comboBoxVal) {
            case "Tangent plane":
                putu0v0Plane(index, new Vector2f(u, v));
                break;
            case "Osculating paraboloid":
                putu0v0Paraboloid(index, new Vector2f(u, v));
                break;
            case "Main directions":
                putu0v0MainDir(index, new Vector2f(u, v));
                break;
        }
        change(index);
    }

}
