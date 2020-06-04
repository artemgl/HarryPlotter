<<<<<<< HEAD
package com.chernikovs.controllers;

import com.chernikovs.entities.Curve;
import com.chernikovs.entities.Surface;
import com.chernikovs.functions.*;
import com.chernikovs.gui.*;
import com.chernikovs.renderEngine.Display;
import com.chernikovs.renderEngine.MasterRenderer;
import lwjgui.paint.Color;
import lwjgui.scene.Node;
import lwjgui.scene.Window;
import lwjgui.scene.control.*;
import lwjgui.scene.layout.*;
import org.joml.Vector3f;

import static com.chernikovs.controllers.Attribs.*;

/**A class describing tools that handle input signals*/
public class GuiController {

    private static VBox box = new VBox();

    private static MasterRenderer renderer;
    private static Window window;

    private static Options currentOptions;
    private static SurfaceOptions surfaceOptions;
    private static CurveOptions curveOptions;

    private static Analysis currentAnalysis;
    private static SurfaceAnalysis surfaceAnalysis;
    private static CurveAnalysis curveAnalysis;

    /**
     * Set renderer to handle
     * @param renderer_ new renderer to handle
     * */
    public static void setRenderer(MasterRenderer renderer_) {
        renderer = renderer_;
    }

    /**
     * Set window to handle
     * @param window_ new window to handle
     * */
    public static void setWindow(Window window_) {
        window = window_;
        surfaceOptions = new SurfaceOptions(window);
        curveOptions = new CurveOptions(window);
        currentOptions = surfaceOptions;
        surfaceAnalysis = new SurfaceAnalysis(window);
        curveAnalysis = new CurveAnalysis(window);
        currentAnalysis = surfaceAnalysis;
    }

    /**
     * Hide current analysis menu in graphical user interface
     * */
    public static void hideCurrentAnalysis() {
        StackPane root = (StackPane)window.getScene().getRoot();
        for (Node node : currentAnalysis.getBase()) {
            root.getChildren().remove(node);
        }
    }

    /**
     * Show current analysis menu in graphical user interface
     * */
    public static void showCurrentAnalysis() {
        StackPane root = (StackPane)window.getScene().getRoot();
        for (Node node : currentAnalysis.getBase()) {
            root.getChildren().add(node);
        }
    }

    /**
     * Hide current options menu in graphical user interface
     * */
    public static void hideCurrentOptions() {
        StackPane root = (StackPane)window.getScene().getRoot();
        root.getChildren().remove(currentOptions.getBase());
    }

    /**
     * Show current options menu in graphical user interface
     * */
    public static void showCurrentOptions() {
        StackPane root = (StackPane)window.getScene().getRoot();
        root.getChildren().add(currentOptions.getBase());
    }

    /**
     * Bind surface or curve to current analysis
     * @param index primary key of the geometric object
     * */
    public static void bindCurrentAnalysis(int index) {
        currentAnalysis.bind(index);
    }

    /**
     * Bind surface or curve to current options
     * @param index primary key of the geometric object
     * */
    public static void bindCurrentOptions(int index) {
        currentOptions.bind(index);
    }

    /**
     * Give instructions that current options must refer to surface
     * */
    public static void setSurfaceOptions() {
        currentOptions = surfaceOptions;
    }

    /**
     * Give instructions that current options must refer to curve
     * */
    public static void setCurveOptions() {
        currentOptions = curveOptions;
    }

    /**
     * Give instructions that current analysis must refer to surface
     * */
    public static void setSurfaceAnalysis() {
        currentAnalysis = surfaceAnalysis;
    }

    /**
     * Give instructions that current analysis must refer to curve
     * */
    public static void setCurveAnalysis() {
        currentAnalysis = curveAnalysis;
    }

    private static GridPane createFuncUnit(String content, double width, double height) {
        final int number = getNextIndex();
        addSurfaceUnit(number);

        GridPane res = new GridPane();
        putFrame(number, res);
        res.setMinSize(width, height);
        Vector3f colour = getColour(number);
        res.setBackgroundLegacy(new Color(colour.x, colour.y, colour.z));

        double indent = 5;
        double btnWidth = 30;
        double comboBoxWidth = 80;

        TextField text = new TextField(content);
        putField(number, text);
        text.setOnTextChange(event -> {
            String t = text.getText();
            putFunction(number, t);
            redraw(number);
        });
        text.setFontSize(25);
        text.setPrompt("x+y");
        text.setMinSize(width - 8 * indent - 2 * btnWidth - comboBoxWidth, height - 2 * indent);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("z(x,y)=", "x(u,v);y(u,v);z(u,v)=", "y(x)=", "x(t);y(t);z(t)=");
        comboBox.setMinSize(comboBoxWidth, height - 2 * indent);
        comboBox.setOnAction(event->{
            String t = comboBox.getValue();
            if (comboBox.getItems().contains(t)) {
                comboBox.setValue(t.substring(0, Math.min(7, t.length())) + "...");
                String function = getFunction(number);
                if (t.equals("z(x,y)=") || t.equals("x(u,v);y(u,v);z(u,v)=")) {
                    addSurfaceUnit(number);
                } else {
                    addCurveUnit(number);
                }
                putFunction(number, function);
                putIsParametric(number, t.equals("x(u,v);y(u,v);z(u,v)=") || t.equals("x(t);y(t);z(t)="));
                switch (t) {
                    case "z(x,y)=":
                        putFVar(number, "x");
                        putSVar(number, "y");
                        text.setPrompt("x+y");
                        break;
                    case "x(u,v);y(u,v);z(u,v)=":
                        text.setPrompt("u;v;u+v");
                        break;
                    case "y(x)=":
                        putFVar(number, "x");
                        text.setPrompt("x");
                        break;
                    case "x(t);y(t);z(t)=":
                        text.setPrompt("t;t;t");
                        break;
                }
                redraw(number);
            }
        });
        comboBox.setValue("z(x,y)=");

        Button delete = new Button("x");
        delete.setOnAction(event -> {
            hideCurrentAnalysis();
            box.getChildren().remove(res);
            if (getIsSurface(number)) {
                removeSurfaceUnit(number);
            } else {
                removeCurveUnit(number);
            }
            redraw(number);
        });
        delete.setMinSize(btnWidth, height - 2 * btnWidth);

        Button options = new Button("..");
        options.setOnAction(event -> {
            hideCurrentOptions();
            if (getIsSurface(number)) {
                setSurfaceOptions();
            } else {
                setCurveOptions();
            }
            bindCurrentOptions(number);
            showCurrentOptions();
        });
        options.setMinSize(btnWidth, height - 2 * btnWidth);

        res.add(createWrapper(comboBox, "C", comboBoxWidth + 2 * indent, height), 1, 1);
        res.add(createWrapper(text, "C", width - 6 * indent - 2 * btnWidth - comboBoxWidth, height), 2, 1);
        res.add(createWrapper(delete, "C", btnWidth + 2 * indent, height), 3, 1);
        res.add(createWrapper(options, "C", btnWidth + 2 * indent, height), 4, 1);

        return res;
    }

    private static BorderPane createWrapper(Node content, String side, double width, double height) {
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

    /**
     * Create elements of graphical user interface
     * @return pane which contains all the created elements
     * */
    public static Pane createContent() {
        GridPane root = new GridPane();
        root.setColumnConstraint(1, new ColumnConstraint((double) Display.getWidth() * Display.getRenderPart(), Priority.ALWAYS));
        root.setColumnConstraint(2, new ColumnConstraint((double)Display.getWidth() * Display.getNotRenderPart(), Priority.NEVER));

        OpenGLPane image = new OpenGLPane();
        image.setMinSize((double)Display.getWidth() * Display.getRenderPart(), Display.getHeight());
        image.setRendererCallback(renderer);
        Controller.initialize(image);

        root.add(image, 1, 1);

        GridPane pane = new GridPane();
        pane.setMinSize((double)Display.getWidth() * Display.getNotRenderPart(), Display.getHeight());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinSize(pane.getWidth() - 10, pane.getHeight() - 45);
        scrollPane.setScrollBarThickness(15);

        box = new VBox();
        box.setMinSize(scrollPane.getWidth() - 10, scrollPane.getHeight() - 10);

        scrollPane.setContent(box);

        Button btn = new Button("Add");
        btn.setOnAction(event->{
            box.getChildren().add(createFuncUnit("", box.getWidth()/* + 8.5*/, 45));
        });
        btn.setFontSize(20);
        btn.setMinSize(100, 30);

        BorderPane top = new BorderPane();
        top.setMinSize(pane.getWidth(), 40);
        top.setCenter(btn);

        BorderPane bottom = new BorderPane();
        bottom.setMinSize(pane.getWidth(), pane.getHeight() - 40);
        bottom.setCenter(scrollPane);

        pane.add(top, 1, 1);
        pane.add(bottom, 1, 2);
        root.add(pane, 2, 1);

        return root;
    }

    /**
     * Handle recent actions
     * Only changed parameters will be recalculated
     * */
    public static void update() {
        if (!getNeedsUpdate()) {
            return;
        }

        renderer.cleanUp();

        for (Integer i : getIndices()) {
            boolean isSurface = getIsSurface(i);

            if (getNeedsRedrawing(i)) {
                String text = getFunction(i);
                String[] xyz = text.split(";");

                if (getIsParametric(i)) {
                    if (xyz.length != 3 ||
                            !ExpressionHandler.isValid(ExpressionHandler.getPostfix(xyz[0])) ||
                            !ExpressionHandler.isValid(ExpressionHandler.getPostfix(xyz[1])) ||
                            !ExpressionHandler.isValid(ExpressionHandler.getPostfix(xyz[2]))) {
                        continue;
                    }
                } else {
                    String xyz0 = getFVar(i);
                    String xyz1 = text;
                    String xyz2 = "0";
                    if (isSurface) {
                        xyz1 = getSVar(i);
                        xyz2 = text;
                    }
                    if (xyz.length == 1 && ExpressionHandler.isValid(ExpressionHandler.getPostfix(text))) {
                        xyz = new String[3];
                        xyz[0] = xyz0;
                        xyz[1] = xyz1;
                        xyz[2] = xyz2;
                    } else {
                        continue;
                    }
                }
                String buff = xyz[0];
                xyz[0] = xyz[1];
                xyz[1] = xyz[2];
                xyz[2] = buff;
                if (isSurface) {
                    VectorFunction2 function = new VectorFunction2(xyz, getFVar(i), getSVar(i));
                    float k1 = getFPi(i) ? (float)Math.PI : 1.0f;
                    float k2 = getSPi(i) ? (float)Math.PI : 1.0f;
                    Surface surface = FunctionHandler.createSurface(function,
                            new float[] {getFFrom(i) * k1, getFTo(i) * k1},
                            new float[] {getSFrom(i) * k2, getSTo(i) * k2}, getFStep(i) * k1, getSStep(i) * k2,
                            getColour(i), MasterRenderer.getLoader());
                    if (getMode(i).equals("Grid")) {
                        renderer.addCurves(surface.getGrid());
                    } else {
                        renderer.add(surface);
                    }
                    if (getPlane(i)) {
                        renderer.add(surface.getTangentPlane(getu0v0Plane(i).x, getu0v0Plane(i).y));
                    }
                    if (getParaboloid(i)) {
                        renderer.add(surface.getOsculatingParaboloid(getu0v0Paraboloid(i).x, getu0v0Paraboloid(i).y));
                    }
                    if (getMainDir(i)) {
                        renderer.addVectors(surface.getMainDirections(getu0v0MainDir(i).x, getu0v0MainDir(i).y));
                    }
                    putSurface(i, surface);
                } else {

                    VectorFunction function = new VectorFunction(xyz, getFVar(i));
                    float k1 = getFPi(i) ? (float)Math.PI : 1.0f;
                    Curve curve = FunctionHandler.createCurve(function,
                            new float[] {getFFrom(i) * k1, getFTo(i) * k1},getFStep(i) * k1,
                            getColour(i), MasterRenderer.getLoader());
                    if (getFrenet(i)) {
                        renderer.addVectors(curve.getFrenetFrame(getT0(i)));
                    }
                    putCurve(i, curve);
                    renderer.add(curve);
                }
                putNeedsRedrawing(i, false);
            } else {
                if (getNeedsChanging(i)) {
                    if (isSurface) {
                        Surface surface = getSurface(i);
                        if (getMode(i).equals("Grid")) {
                            renderer.addCurves(surface.getGrid());
                        } else {
                            renderer.add(surface);
                        }
                        surface.setColour(getColour(i));
                        if (getPlane(i)) {
                            renderer.add(surface.getTangentPlane(getu0v0Plane(i).x, getu0v0Plane(i).y));
                        }
                        if (getParaboloid(i)) {
                            renderer.add(surface.getOsculatingParaboloid(getu0v0Paraboloid(i).x, getu0v0Paraboloid(i).y));
                        }
                        if (getMainDir(i)) {
                            renderer.addVectors(surface.getMainDirections(getu0v0MainDir(i).x, getu0v0MainDir(i).y));
                        }
                    } else {
                        Curve curve = getCurve(i);
                        renderer.add(curve);
                        curve.setColour(getColour(i));
                        if (getFrenet(i)) {
                            renderer.addVectors(curve.getFrenetFrame(getT0(i)));
                        }
                    }
                    putNeedsChanging(i, false);
                } else {
                    if (isSurface) {
                        Surface surface = getSurface(i);
                        if (surface != null) {
                            if (getMode(i).equals("Grid")) {
                                renderer.addCurves(surface.getGrid());
                            } else {
                                renderer.add(surface);
                            }
                            if (getPlane(i)) {
                                renderer.add(surface.getTangentPlane(getu0v0Plane(i).x, getu0v0Plane(i).y));
                            }
                            if (getParaboloid(i)) {
                                renderer.add(surface.getOsculatingParaboloid(getu0v0Paraboloid(i).x, getu0v0Paraboloid(i).y));
                            }
                            if (getMainDir(i)) {
                                renderer.addVectors(surface.getMainDirections(getu0v0MainDir(i).x, getu0v0MainDir(i).y));
                            }
                        }
                    } else {
                        Curve curve = getCurve(i);
                        if (curve != null) {
                            renderer.add(curve);
                            if (getFrenet(i)) {
                                renderer.addVectors(curve.getFrenetFrame(getT0(i)));
                            }
                        }
                    }
                }
            }
        }

        putNeedsUpdate(false);
    }

}
=======
package com.chernikovs.controllers;

import com.chernikovs.entities.Curve;
import com.chernikovs.entities.Surface;
import com.chernikovs.functions.*;
import com.chernikovs.gui.*;
import com.chernikovs.renderEngine.Display;
import com.chernikovs.renderEngine.MasterRenderer;
import lwjgui.paint.Color;
import lwjgui.scene.Node;
import lwjgui.scene.Window;
import lwjgui.scene.control.*;
import lwjgui.scene.layout.*;
import org.joml.Vector3f;

import static com.chernikovs.controllers.Attribs.*;

/**A class describing tools that handle input signals*/
public class GuiController {

    private static VBox box = new VBox();

    private static MasterRenderer renderer;
    private static Window window;

    private static Options currentOptions;
    private static SurfaceOptions surfaceOptions;
    private static CurveOptions curveOptions;

    private static Analysis currentAnalysis;
    private static SurfaceAnalysis surfaceAnalysis;
    private static CurveAnalysis curveAnalysis;

    /**
     * Set renderer to handle
     * @param renderer_ new renderer to handle
     * */
    public static void setRenderer(MasterRenderer renderer_) {
        renderer = renderer_;
    }

    /**
     * Set window to handle
     * @param window_ new window to handle
     * */
    public static void setWindow(Window window_) {
        window = window_;
        surfaceOptions = new SurfaceOptions(window);
        curveOptions = new CurveOptions(window);
        currentOptions = surfaceOptions;
        surfaceAnalysis = new SurfaceAnalysis(window);
        curveAnalysis = new CurveAnalysis(window);
        currentAnalysis = surfaceAnalysis;
    }

    /**
     * Hide current analysis menu in graphical user interface
     * */
    public static void hideCurrentAnalysis() {
        StackPane root = (StackPane)window.getScene().getRoot();
        for (Node node : currentAnalysis.getBase()) {
            root.getChildren().remove(node);
        }
    }

    /**
     * Show current analysis menu in graphical user interface
     * */
    public static void showCurrentAnalysis() {
        StackPane root = (StackPane)window.getScene().getRoot();
        for (Node node : currentAnalysis.getBase()) {
            root.getChildren().add(node);
        }
    }

    /**
     * Hide current options menu in graphical user interface
     * */
    public static void hideCurrentOptions() {
        StackPane root = (StackPane)window.getScene().getRoot();
        root.getChildren().remove(currentOptions.getBase());
    }

    /**
     * Show current options menu in graphical user interface
     * */
    public static void showCurrentOptions() {
        StackPane root = (StackPane)window.getScene().getRoot();
        root.getChildren().add(currentOptions.getBase());
    }

    /**
     * Bind surface or curve to current analysis
     * @param index primary key of the geometric object
     * */
    public static void bindCurrentAnalysis(int index) {
        currentAnalysis.bind(index);
    }

    /**
     * Bind surface or curve to current options
     * @param index primary key of the geometric object
     * */
    public static void bindCurrentOptions(int index) {
        currentOptions.bind(index);
    }

    /**
     * Give instructions that current options must refer to surface
     * */
    public static void setSurfaceOptions() {
        currentOptions = surfaceOptions;
    }

    /**
     * Give instructions that current options must refer to curve
     * */
    public static void setCurveOptions() {
        currentOptions = curveOptions;
    }

    /**
     * Give instructions that current analysis must refer to surface
     * */
    public static void setSurfaceAnalysis() {
        currentAnalysis = surfaceAnalysis;
    }

    /**
     * Give instructions that current analysis must refer to curve
     * */
    public static void setCurveAnalysis() {
        currentAnalysis = curveAnalysis;
    }

    private static GridPane createFuncUnit(String content, double width, double height) {
        final int number = getNextIndex();
        addSurfaceUnit(number);

        GridPane res = new GridPane();
        putFrame(number, res);
        res.setMinSize(width, height);
        Vector3f colour = getColour(number);
        res.setBackgroundLegacy(new Color(colour.x, colour.y, colour.z));

        double indent = 5;
        double btnWidth = 30;
        double comboBoxWidth = 80;

        TextField text = new TextField(content);
        putField(number, text);
        text.setOnTextChange(event -> {
            String t = text.getText();
            putFunction(number, t);
            redraw(number);
        });
        text.setFontSize(25);
        text.setPrompt("x+y");
        text.setMinSize(width - 8 * indent - 2 * btnWidth - comboBoxWidth, height - 2 * indent);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("z(x,y)=", "x(u,v);y(u,v);z(u,v)=", "y(x)=", "x(t);y(t);z(t)=");
        comboBox.setMinSize(comboBoxWidth, height - 2 * indent);
        comboBox.setOnAction(event->{
            String t = comboBox.getValue();
            if (comboBox.getItems().contains(t)) {
                comboBox.setValue(t.substring(0, Math.min(7, t.length())) + "...");
                String function = getFunction(number);
                if (t.equals("z(x,y)=") || t.equals("x(u,v);y(u,v);z(u,v)=")) {
                    addSurfaceUnit(number);
                } else {
                    addCurveUnit(number);
                }
                putFunction(number, function);
                putIsParametric(number, t.equals("x(u,v);y(u,v);z(u,v)=") || t.equals("x(t);y(t);z(t)="));
                switch (t) {
                    case "z(x,y)=":
                        putFVar(number, "x");
                        putSVar(number, "y");
                        text.setPrompt("x+y");
                        break;
                    case "x(u,v);y(u,v);z(u,v)=":
                        text.setPrompt("u;v;u+v");
                        break;
                    case "y(x)=":
                        putFVar(number, "x");
                        text.setPrompt("x");
                        break;
                    case "x(t);y(t);z(t)=":
                        text.setPrompt("t;t;t");
                        break;
                }
                redraw(number);
            }
        });
        comboBox.setValue("z(x,y)=");

        Button delete = new Button("x");
        delete.setOnAction(event -> {
            hideCurrentAnalysis();
            box.getChildren().remove(res);
            if (getIsSurface(number)) {
                removeSurfaceUnit(number);
            } else {
                removeCurveUnit(number);
            }
            redraw(number);
        });
        delete.setMinSize(btnWidth, height - 2 * btnWidth);

        Button options = new Button("..");
        options.setOnAction(event -> {
            hideCurrentOptions();
            if (getIsSurface(number)) {
                setSurfaceOptions();
            } else {
                setCurveOptions();
            }
            bindCurrentOptions(number);
            showCurrentOptions();
        });
        options.setMinSize(btnWidth, height - 2 * btnWidth);

        res.add(createWrapper(comboBox, "C", comboBoxWidth + 2 * indent, height), 1, 1);
        res.add(createWrapper(text, "C", width - 6 * indent - 2 * btnWidth - comboBoxWidth, height), 2, 1);
        res.add(createWrapper(delete, "C", btnWidth + 2 * indent, height), 3, 1);
        res.add(createWrapper(options, "C", btnWidth + 2 * indent, height), 4, 1);

        return res;
    }

    private static BorderPane createWrapper(Node content, String side, double width, double height) {
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

    /**
     * Create elements of graphical user interface
     * @return pane which contains all the created elements
     * */
    public static Pane createContent() {
        GridPane root = new GridPane();
        root.setColumnConstraint(1, new ColumnConstraint((double) Display.getWidth() * Display.getRenderPart(), Priority.ALWAYS));
        root.setColumnConstraint(2, new ColumnConstraint((double)Display.getWidth() * Display.getNotRenderPart(), Priority.NEVER));

        OpenGLPane image = new OpenGLPane();
        image.setMinSize((double)Display.getWidth() * Display.getRenderPart(), Display.getHeight());
        image.setRendererCallback(renderer);
        Controller.initialize(image);

        root.add(image, 1, 1);

        GridPane pane = new GridPane();
        pane.setMinSize((double)Display.getWidth() * Display.getNotRenderPart(), Display.getHeight());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinSize(pane.getWidth() - 10, pane.getHeight() - 45);
        scrollPane.setScrollBarThickness(15);

        box = new VBox();
        box.setMinSize(scrollPane.getWidth() - 10, scrollPane.getHeight() - 10);

        scrollPane.setContent(box);

        Button btn = new Button("Add");
        btn.setOnAction(event->{
            box.getChildren().add(createFuncUnit("", box.getWidth()/* + 8.5*/, 45));
        });
        btn.setFontSize(20);
        btn.setMinSize(100, 30);

        BorderPane top = new BorderPane();
        top.setMinSize(pane.getWidth(), 40);
        top.setCenter(btn);

        BorderPane bottom = new BorderPane();
        bottom.setMinSize(pane.getWidth(), pane.getHeight() - 40);
        bottom.setCenter(scrollPane);

        pane.add(top, 1, 1);
        pane.add(bottom, 1, 2);
        root.add(pane, 2, 1);

        return root;
    }

    /**
     * Handle recent actions
     * Only changed parameters will be recalculated
     * */
    public static void update() {
        if (!getNeedsUpdate()) {
            return;
        }

        renderer.cleanUp();

        for (Integer i : getIndices()) {
            boolean isSurface = getIsSurface(i);

            if (getNeedsRedrawing(i)) {
                String text = getFunction(i);
                String[] xyz = text.split(";");

                if (getIsParametric(i)) {
                    if (xyz.length != 3 ||
                            !ExpressionHandler.isValid(ExpressionHandler.getPostfix(xyz[0])) ||
                            !ExpressionHandler.isValid(ExpressionHandler.getPostfix(xyz[1])) ||
                            !ExpressionHandler.isValid(ExpressionHandler.getPostfix(xyz[2]))) {
                        continue;
                    }
                } else {
                    String xyz0 = getFVar(i);
                    String xyz1 = text;
                    String xyz2 = "0";
                    if (isSurface) {
                        xyz1 = getSVar(i);
                        xyz2 = text;
                    }
                    if (xyz.length == 1 && ExpressionHandler.isValid(ExpressionHandler.getPostfix(text))) {
                        xyz = new String[3];
                        xyz[0] = xyz0;
                        xyz[1] = xyz1;
                        xyz[2] = xyz2;
                    } else {
                        continue;
                    }
                }
                String buff = xyz[0];
                xyz[0] = xyz[1];
                xyz[1] = xyz[2];
                xyz[2] = buff;
                if (isSurface) {
                    VectorFunction2 function = new VectorFunction2(xyz, getFVar(i), getSVar(i));
                    float k1 = getFPi(i) ? (float)Math.PI : 1.0f;
                    float k2 = getSPi(i) ? (float)Math.PI : 1.0f;
                    Surface surface = FunctionHandler.createSurface(function,
                            new float[] {getFFrom(i) * k1, getFTo(i) * k1},
                            new float[] {getSFrom(i) * k2, getSTo(i) * k2}, getFStep(i) * k1, getSStep(i) * k2,
                            getColour(i), MasterRenderer.getLoader());
                    if (getMode(i).equals("Grid")) {
                        renderer.addCurves(surface.getGrid());
                    } else {
                        renderer.add(surface);
                    }
                    if (getPlane(i)) {
                        renderer.add(surface.getTangentPlane(getu0v0Plane(i).x, getu0v0Plane(i).y));
                    }
                    if (getParaboloid(i)) {
                        renderer.add(surface.getOsculatingParaboloid(getu0v0Paraboloid(i).x, getu0v0Paraboloid(i).y));
                    }
                    if (getMainDir(i)) {
                        renderer.addVectors(surface.getMainDirections(getu0v0MainDir(i).x, getu0v0MainDir(i).y));
                    }
                    putSurface(i, surface);
                } else {

                    VectorFunction function = new VectorFunction(xyz, getFVar(i));
                    float k1 = getFPi(i) ? (float)Math.PI : 1.0f;
                    Curve curve = FunctionHandler.createCurve(function,
                            new float[] {getFFrom(i) * k1, getFTo(i) * k1},getFStep(i) * k1,
                            getColour(i), MasterRenderer.getLoader());
                    if (getFrenet(i)) {
                        renderer.addVectors(curve.getFrenetFrame(getT0(i)));
                    }
                    putCurve(i, curve);
                    renderer.add(curve);
                }
                putNeedsRedrawing(i, false);
            } else {
                if (getNeedsChanging(i)) {
                    if (isSurface) {
                        Surface surface = getSurface(i);
                        if (getMode(i).equals("Grid")) {
                            renderer.addCurves(surface.getGrid());
                        } else {
                            renderer.add(surface);
                        }
                        surface.setColour(getColour(i));
                        if (getPlane(i)) {
                            renderer.add(surface.getTangentPlane(getu0v0Plane(i).x, getu0v0Plane(i).y));
                        }
                        if (getParaboloid(i)) {
                            renderer.add(surface.getOsculatingParaboloid(getu0v0Paraboloid(i).x, getu0v0Paraboloid(i).y));
                        }
                        if (getMainDir(i)) {
                            renderer.addVectors(surface.getMainDirections(getu0v0MainDir(i).x, getu0v0MainDir(i).y));
                        }
                    } else {
                        Curve curve = getCurve(i);
                        renderer.add(curve);
                        curve.setColour(getColour(i));
                        if (getFrenet(i)) {
                            renderer.addVectors(curve.getFrenetFrame(getT0(i)));
                        }
                    }
                    putNeedsChanging(i, false);
                } else {
                    if (isSurface) {
                        Surface surface = getSurface(i);
                        if (surface != null) {
                            if (getMode(i).equals("Grid")) {
                                renderer.addCurves(surface.getGrid());
                            } else {
                                renderer.add(surface);
                            }
                            if (getPlane(i)) {
                                renderer.add(surface.getTangentPlane(getu0v0Plane(i).x, getu0v0Plane(i).y));
                            }
                            if (getParaboloid(i)) {
                                renderer.add(surface.getOsculatingParaboloid(getu0v0Paraboloid(i).x, getu0v0Paraboloid(i).y));
                            }
                            if (getMainDir(i)) {
                                renderer.addVectors(surface.getMainDirections(getu0v0MainDir(i).x, getu0v0MainDir(i).y));
                            }
                        }
                    } else {
                        Curve curve = getCurve(i);
                        if (curve != null) {
                            renderer.add(curve);
                            if (getFrenet(i)) {
                                renderer.addVectors(curve.getFrenetFrame(getT0(i)));
                            }
                        }
                    }
                }
            }
        }

        putNeedsUpdate(false);
    }

}
>>>>>>> 4f3c886f3ff1ae2c84328c3abe8a64dfc85d4b78
