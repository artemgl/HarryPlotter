<<<<<<< HEAD
package com.chernikovs.controllers;

import com.chernikovs.entities.Curve;
import com.chernikovs.entities.Surface;
import lwjgui.scene.control.TextField;
import lwjgui.scene.layout.Pane;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**A class that stores attributes of surfaces and curves*/
public class Attribs {

    private static Boolean needsUpdate = true;
    private static Map<Integer, Boolean> needsRedrawing = new HashMap<>();
    private static Map<Integer, Boolean> needsChanging = new HashMap<>();

    private static Map<Integer, Surface> surfaces = new HashMap<>();
    private static Map<Integer, Curve> curves = new HashMap<>();

    private static Map<Integer, Boolean> whetherSurfaces = new HashMap<>();
    private static Map<Integer, Boolean> whetherParametric = new HashMap<>();

    private static Map<Integer, TextField> fields = new HashMap<>();
    private static Map<Integer, Pane> frames = new HashMap<>();

    private static Map<Integer, String> functions = new HashMap<>();
    private static Map<Integer, String> modes = new HashMap<>();
    private static Map<Integer, Vector3f> colours = new HashMap<>();
    private static Map<Integer, Boolean> planes = new HashMap<>();
    private static Map<Integer, Boolean> paraboloids = new HashMap<>();
    private static Map<Integer, Boolean> mainDirs = new HashMap<>();
    private static Map<Integer, Boolean> frenets = new HashMap<>();
    private static Map<Integer, String> fvars = new HashMap<>();
    private static Map<Integer, Float> ffroms = new HashMap<>();
    private static Map<Integer, Float> ftos = new HashMap<>();
    private static Map<Integer, Float> fsteps = new HashMap<>();
    private static Map<Integer, Boolean> fpi = new HashMap<>();
    private static Map<Integer, String> svars = new HashMap<>();
    private static Map<Integer, Float> sfroms = new HashMap<>();
    private static Map<Integer, Float> stos = new HashMap<>();
    private static Map<Integer, Float> ssteps = new HashMap<>();
    private static Map<Integer, Boolean> spi = new HashMap<>();

    private static Map<Integer, Float> t0 = new HashMap<>();
    private static Map<Integer, Vector2f> u0v0Planes = new HashMap<>();
    private static Map<Integer, Vector2f> u0v0Paraboloids = new HashMap<>();
    private static Map<Integer, Vector2f> u0v0MainDirs = new HashMap<>();

    private static final ArrayList<Vector3f> colourArchive = new ArrayList<Vector3f>() {{
        add(new Vector3f(0.9f, 0.1f, 0.1f));
        add(new Vector3f(0.0f, 0.6f, 0.9f));
        add(new Vector3f(0.1f, 0.7f, 0.3f));
        add(new Vector3f(0.7f, 0.9f, 0.1f));
        add(new Vector3f(1.0f, 0.5f, 0.0f));
        add(new Vector3f(0.6f, 0.0f, 0.6f));
    }};

    /**
     * Put surface in storage
     * @param index the primary key of the surface to put
     * @param surface the surface to put
     * */
    public static void putSurface(int index, Surface surface) {
        surfaces.put(index, surface);
    }

    /**
     * Put curve in storage
     * @param index the primary key of the curve to put
     * @param curve the curve to put
     * */
    public static void putCurve(int index, Curve curve) {
        curves.put(index, curve);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param isSurface information whether the object is surface
     * */
    public static void putIsSurface(int index, boolean isSurface) {
        whetherSurfaces.put(index, isSurface);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param isParametric information whether the object is parametric
     * */
    public static void putIsParametric(int index, boolean isParametric) {
        whetherParametric.put(index, isParametric);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param field the text field that gives string representation of the object
     * */
    public static void putField(int index, TextField field) {
        fields.put(index, field);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param frame the frame bound with the object
     * */
    public static void putFrame(int index, Pane frame) {
        frames.put(index, frame);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param function the string representation of the object
     * */
    public static void putFunction(int index, String function) {
        functions.put(index, function);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param mode the mode of the object's rendering
     * */
    public static void putMode(int index, String mode) {
        modes.put(index, mode);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param colour the colour of the object
     * */
    public static void putColour(int index, Vector3f colour) {
        colours.put(index, colour);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param plane the information whether the tangent plane is needed to be rendered
     * */
    public static void putPlane(int index, boolean plane) {
        planes.put(index, plane);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param paraboloid the information whether the osculating paraboloid is needed to be rendered
     * */
    public static void putParaboloid(int index, boolean paraboloid) {
        paraboloids.put(index, paraboloid);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param mainDir the information whether the main directions are needed to be rendered
     * */
    public static void putMainDir(int index, boolean mainDir) {
        mainDirs.put(index, mainDir);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param frenet the information whether the Frenet frame is needed to be rendered
     * */
    public static void putFrenet(int index, boolean frenet) {
        frenets.put(index, frenet);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param var the first variable of the object's function
     * */
    public static void putFVar(int index, String var) {
        fvars.put(index, var);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param from the left border of object's function definition by the first variable
     * */
    public static void putFFrom(int index, float from) {
        ffroms.put(index, from);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param to the right border of object's function definition by the first variable
     * */
    public static void putFTo(int index, float to) {
        ftos.put(index, to);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param step the step for calculating the object's function by the first variable
     * */
    public static void putFStep(int index, float step) {
        fsteps.put(index, step);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param pi the information whether the data about the first variable is needed to be multiplied by pi
     * */
    public static void putFPi(int index, boolean pi) {
        fpi.put(index, pi);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param var the second variable of the object's function
     * */
    public static void putSVar(int index, String var) {
        svars.put(index, var);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param from the left border of object's function definition by the second variable
     * */
    public static void putSFrom(int index, float from) {
        sfroms.put(index, from);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param to the right border of object's function definition by the second variable
     * */
    public static void putSTo(int index, float to) {
        stos.put(index, to);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param step the step for calculating the object's function by the second variable
     * */
    public static void putSStep(int index, float step) {
        ssteps.put(index, step);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param pi the information whether the data about the second variable is needed to be multiplied by pi
     * */
    public static void putSPi(int index, boolean pi) {
        spi.put(index, pi);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param t0_ the point that needs to be analysed
     * */
    public static void putT0(int index, float t0_) {
        t0.put(index, t0_);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param u0v0 the point where the tangent plane is that needs to be analysed
     * */
    public static void putu0v0Plane(int index, Vector2f u0v0) {
        u0v0Planes.put(index, u0v0);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param u0v0 the point where the osculating paraboloid is that needs to be analysed
     * */
    public static void putu0v0Paraboloid(int index, Vector2f u0v0) {
        u0v0Paraboloids.put(index, u0v0);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param u0v0 the point where the main directions are that needs to be analysed
     * */
    public static void putu0v0MainDir(int index, Vector2f u0v0) {
        u0v0MainDirs.put(index, u0v0);
    }

    /**
     * Put whether surfaces and curves are needed to be rendered
     * @param needsUpdate_ true if they are needed to be rendered and false otherwise
     * */
    public static void putNeedsUpdate(boolean needsUpdate_) {
        needsUpdate = needsUpdate_;
    }

    /**
     * Put whether the certain geometric object is needed to be rendered
     * @param needsRedrawing_ true if it is needed to be rendered and false otherwise
     * */
    public static void putNeedsRedrawing(int index, boolean needsRedrawing_) {
        needsRedrawing.put(index, needsRedrawing_);
    }

    /**
     * Put whether the certain geometric object is needed to be changed
     * @param needsChanging_ true if it is needed to be changed and false otherwise
     * */
    public static void putNeedsChanging(int index, boolean needsChanging_) {
        needsChanging.put(index, needsChanging_);
    }

    /**
     * Give instructions to redraw the geometric object
     * @param index the primary key of the object
     * */
    public static void redraw(int index) {
        needsRedrawing.put(index, true);
        needsUpdate = true;
    }

    /**
     * Give instructions to change the geometric object
     * @param index the primary key of the object
     * */
    public static void change(int index) {
        needsChanging.put(index, true);
        needsUpdate = true;
    }

    /**
     * Get surface by its primary key
     * @param index the primary key of the surface
     * @return the surface with received primary key
     * */
    public static Surface getSurface(int index) {
        return surfaces.get(index);
    }

    /**
     * Get curve by its primary key
     * @param index the primary key of the curve
     * @return the curve with received primary key
     * */
    public static Curve getCurve(int index) {
        return curves.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether this object is surface
     * */
    public static boolean getIsSurface(int index) {
        return whetherSurfaces.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether this object is parametric
     * */
    public static boolean getIsParametric(int index) {
        return whetherParametric.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the text field that bound to this object
     * */
    public static TextField getField(int index) {
        return fields.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the frame that bound to this object
     * */
    public static Pane getFrame(int index) {
        return frames.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the string representation of the object's function
     * */
    public static String getFunction(int index) {
        return functions.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return mode of object's rendering
     * */
    public static String getMode(int index) {
        return modes.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the colour of this object
     * */
    public static Vector3f getColour(int index) {
        return colours.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether tangent plane is needed to be rendered
     * */
    public static boolean getPlane(int index) {
        return planes.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether osculating paraboloid is needed to be rendered
     * */
    public static boolean getParaboloid(int index) {
        return paraboloids.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether main directions are needed to be rendered
     * */
    public static boolean getMainDir(int index) {
        return mainDirs.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether Frenet frame is needed to be rendered
     * */
    public static boolean getFrenet(int index) {
        return frenets.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the first variable of the object's function
     * */
    public static String getFVar(int index) {
        return fvars.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the left border of the object's function definition by the first variable
     * */
    public static float getFFrom(int index) {
        return ffroms.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the right border of the object's function definition by the first variable
     * */
    public static float getFTo(int index) {
        return ftos.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the step for calculating the object's function by the first variable
     * */
    public static float getFStep(int index) {
        return fsteps.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether the data about the first variable is needed to be multiplied by pi
     * */
    public static boolean getFPi(int index) {
        return fpi.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the second variable of the object's function
     * */
    public static String getSVar(int index) {
        return svars.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the left border of the object's function definition by the second variable
     * */
    public static float getSFrom(int index) {
        return sfroms.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the right border of the object's function definition by the second variable
     * */
    public static float getSTo(int index) {
        return stos.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the step for calculating the object's function by the second variable
     * */
    public static float getSStep(int index) {
        return ssteps.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether the data about the second variable is needed to be multiplied by pi
     * */
    public static boolean getSPi(int index) {
        return spi.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the point that needs to be analysed
     * */
    public static float getT0(int index) {
        return t0.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the point where the tangent plane is that needs to be analysed
     * */
    public static Vector2f getu0v0Plane(int index) {
        return u0v0Planes.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the point where the osculating paraboloid is that needs to be analysed
     * */
    public static Vector2f getu0v0Paraboloid(int index) {
        return u0v0Paraboloids.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the point where the main directions are that needs to be analysed
     * */
    public static Vector2f getu0v0MainDir(int index) {
        return u0v0MainDirs.get(index);
    }

    /**
     * Get the information whether surfaces and curves are needed to be rendered
     * @return true if they are needed to be rendered and false otherwise
     * */
    public static boolean getNeedsUpdate() {
        return needsUpdate;
    }

    /**
     * Get the information whether the certain geometric object is needed to be rendered
     * @return true if it is needed to be rendered and false otherwise
     * */
    public static boolean getNeedsRedrawing(int index) {
        return needsRedrawing.get(index);
    }

    /**
     * Get the information whether the certain geometric object is needed to be changed
     * @return true if it is needed to be changed and false otherwise
     * */
    public static boolean getNeedsChanging(int index) {
        return needsChanging.get(index);
    }

    /**
     * Get the set of primary keys
     * @return set of primary keys
     * */
    public static Set<Integer> getIndices() {
        return whetherSurfaces.keySet();
    }

    /**
     * Get the next colour to generate
     * @return generated colour
     * */
    public static Vector3f getNextColour(int index) {
        return colourArchive.get(index % colourArchive.size());
    }

    /**
     * Get next primary key
     * @return the next primary key
     * */
    public static int getNextIndex() {
        int result = 0;
        while (whetherSurfaces.containsKey(result)) {
            result++;
        }
        return result;
    }

    /**
     * Fill storage with default information about the surface
     * @param index the primary key of the surface
     * */
    public static void addSurfaceUnit(int index) {
        whetherSurfaces.put(index, true);
        whetherParametric.put(index, false);
        functions.put(index, "");
        modes.put(index, "Solid");
        colours.put(index, getNextColour(index));
        planes.put(index, false);
        paraboloids.put(index, false);
        mainDirs.put(index, false);
        fvars.put(index, "u");
        ffroms.put(index, -5.0f);
        ftos.put(index, 5.0f);
        fsteps.put(index, 0.1f);
        fpi.put(index, false);
        svars.put(index, "v");
        sfroms.put(index, -5.0f);
        stos.put(index, 5.0f);
        ssteps.put(index, 0.1f);
        spi.put(index, false);
        u0v0Planes.put(index, new Vector2f(0.0f, 0.0f));
        u0v0Paraboloids.put(index, new Vector2f(0.0f, 0.0f));
        u0v0MainDirs.put(index, new Vector2f(0.0f, 0.0f));
        needsRedrawing.put(index, false);
        needsChanging.put(index, false);
    }

    /**
     * Fill storage with default information about the new surface
     * */
    public static void addStandardSurfaceUnit() {
        addSurfaceUnit(getNextIndex());
    }

    /**
     * Fill storage with default information about the new curve
     * */
    public static void addStandardCurveUnit() {
        addCurveUnit(getNextIndex());
    }

    /**
     * Fill storage with default information about the curve
     * @param index the primary key of the curve
     * */
    public static void addCurveUnit(int index) {
        whetherSurfaces.put(index, false);
        whetherParametric.put(index, false);
        functions.put(index, "");
        colours.put(index, getNextColour(index));
        frenets.put(index, false);
        fvars.put(index, "t");
        ffroms.put(index, -5.0f);
        ftos.put(index, 5.0f);
        fsteps.put(index, 0.1f);
        fpi.put(index, false);
        t0.put(index, 0.0f);
        needsRedrawing.put(index, false);
        needsChanging.put(index, false);
    }

    /**
     * Clear the information from storage about the surface
     * @param index the primary key of the surface
     * */
    public static void removeSurfaceUnit(int index) {
        whetherSurfaces.remove(index);
        whetherParametric.remove(index);
        functions.remove(index);
        modes.remove(index);
        colours.remove(index);
        planes.remove(index);
        paraboloids.remove(index);
        mainDirs.remove(index);
        fvars.remove(index);
        ffroms.remove(index);
        ftos.remove(index);
        fsteps.remove(index);
        fpi.remove(index);
        svars.remove(index);
        sfroms.remove(index);
        stos.remove(index);
        ssteps.remove(index);
        spi.remove(index);
        u0v0Planes.remove(index);
        u0v0Paraboloids.remove(index);
        u0v0MainDirs.remove(index);
    }

    /**
     * Clear the information from storage about the curve
     * @param index the primary key of the curve
     * */
    public static void removeCurveUnit(int index) {
        whetherSurfaces.remove(index);
        whetherParametric.remove(index);
        functions.remove(index);
        colours.remove(index);
        frenets.remove(index);
        fvars.remove(index);
        ffroms.remove(index);
        ftos.remove(index);
        fsteps.remove(index);
        fpi.remove(index);
        t0.remove(index);
    }

}
=======
package com.chernikovs.controllers;

import com.chernikovs.entities.Curve;
import com.chernikovs.entities.Surface;
import lwjgui.scene.control.TextField;
import lwjgui.scene.layout.Pane;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**A class that stores attributes of surfaces and curves*/
public class Attribs {

    private static Boolean needsUpdate = true;
    private static Map<Integer, Boolean> needsRedrawing = new HashMap<>();
    private static Map<Integer, Boolean> needsChanging = new HashMap<>();

    private static Map<Integer, Surface> surfaces = new HashMap<>();
    private static Map<Integer, Curve> curves = new HashMap<>();

    private static Map<Integer, Boolean> whetherSurfaces = new HashMap<>();
    private static Map<Integer, Boolean> whetherParametric = new HashMap<>();

    private static Map<Integer, TextField> fields = new HashMap<>();
    private static Map<Integer, Pane> frames = new HashMap<>();

    private static Map<Integer, String> functions = new HashMap<>();
    private static Map<Integer, String> modes = new HashMap<>();
    private static Map<Integer, Vector3f> colours = new HashMap<>();
    private static Map<Integer, Boolean> planes = new HashMap<>();
    private static Map<Integer, Boolean> paraboloids = new HashMap<>();
    private static Map<Integer, Boolean> mainDirs = new HashMap<>();
    private static Map<Integer, Boolean> frenets = new HashMap<>();
    private static Map<Integer, String> fvars = new HashMap<>();
    private static Map<Integer, Float> ffroms = new HashMap<>();
    private static Map<Integer, Float> ftos = new HashMap<>();
    private static Map<Integer, Float> fsteps = new HashMap<>();
    private static Map<Integer, Boolean> fpi = new HashMap<>();
    private static Map<Integer, String> svars = new HashMap<>();
    private static Map<Integer, Float> sfroms = new HashMap<>();
    private static Map<Integer, Float> stos = new HashMap<>();
    private static Map<Integer, Float> ssteps = new HashMap<>();
    private static Map<Integer, Boolean> spi = new HashMap<>();

    private static Map<Integer, Float> t0 = new HashMap<>();
    private static Map<Integer, Vector2f> u0v0Planes = new HashMap<>();
    private static Map<Integer, Vector2f> u0v0Paraboloids = new HashMap<>();
    private static Map<Integer, Vector2f> u0v0MainDirs = new HashMap<>();

    private static final ArrayList<Vector3f> colourArchive = new ArrayList<Vector3f>() {{
        add(new Vector3f(0.9f, 0.1f, 0.1f));
        add(new Vector3f(0.0f, 0.6f, 0.9f));
        add(new Vector3f(0.1f, 0.7f, 0.3f));
        add(new Vector3f(0.7f, 0.9f, 0.1f));
        add(new Vector3f(1.0f, 0.5f, 0.0f));
        add(new Vector3f(0.6f, 0.0f, 0.6f));
    }};

    /**
     * Put surface in storage
     * @param index the primary key of the surface to put
     * @param surface the surface to put
     * */
    public static void putSurface(int index, Surface surface) {
        surfaces.put(index, surface);
    }

    /**
     * Put curve in storage
     * @param index the primary key of the curve to put
     * @param curve the curve to put
     * */
    public static void putCurve(int index, Curve curve) {
        curves.put(index, curve);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param isSurface information whether the object is surface
     * */
    public static void putIsSurface(int index, boolean isSurface) {
        whetherSurfaces.put(index, isSurface);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param isParametric information whether the object is parametric
     * */
    public static void putIsParametric(int index, boolean isParametric) {
        whetherParametric.put(index, isParametric);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param field the text field that gives string representation of the object
     * */
    public static void putField(int index, TextField field) {
        fields.put(index, field);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param frame the frame bound with the object
     * */
    public static void putFrame(int index, Pane frame) {
        frames.put(index, frame);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param function the string representation of the object
     * */
    public static void putFunction(int index, String function) {
        functions.put(index, function);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param mode the mode of the object's rendering
     * */
    public static void putMode(int index, String mode) {
        modes.put(index, mode);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param colour the colour of the object
     * */
    public static void putColour(int index, Vector3f colour) {
        colours.put(index, colour);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param plane the information whether the tangent plane is needed to be rendered
     * */
    public static void putPlane(int index, boolean plane) {
        planes.put(index, plane);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param paraboloid the information whether the osculating paraboloid is needed to be rendered
     * */
    public static void putParaboloid(int index, boolean paraboloid) {
        paraboloids.put(index, paraboloid);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param mainDir the information whether the main directions are needed to be rendered
     * */
    public static void putMainDir(int index, boolean mainDir) {
        mainDirs.put(index, mainDir);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param frenet the information whether the Frenet frame is needed to be rendered
     * */
    public static void putFrenet(int index, boolean frenet) {
        frenets.put(index, frenet);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param var the first variable of the object's function
     * */
    public static void putFVar(int index, String var) {
        fvars.put(index, var);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param from the left border of object's function definition by the first variable
     * */
    public static void putFFrom(int index, float from) {
        ffroms.put(index, from);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param to the right border of object's function definition by the first variable
     * */
    public static void putFTo(int index, float to) {
        ftos.put(index, to);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param step the step for calculating the object's function by the first variable
     * */
    public static void putFStep(int index, float step) {
        fsteps.put(index, step);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param pi the information whether the data about the first variable is needed to be multiplied by pi
     * */
    public static void putFPi(int index, boolean pi) {
        fpi.put(index, pi);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param var the second variable of the object's function
     * */
    public static void putSVar(int index, String var) {
        svars.put(index, var);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param from the left border of object's function definition by the second variable
     * */
    public static void putSFrom(int index, float from) {
        sfroms.put(index, from);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param to the right border of object's function definition by the second variable
     * */
    public static void putSTo(int index, float to) {
        stos.put(index, to);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param step the step for calculating the object's function by the second variable
     * */
    public static void putSStep(int index, float step) {
        ssteps.put(index, step);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param pi the information whether the data about the second variable is needed to be multiplied by pi
     * */
    public static void putSPi(int index, boolean pi) {
        spi.put(index, pi);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param t0_ the point that needs to be analysed
     * */
    public static void putT0(int index, float t0_) {
        t0.put(index, t0_);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param u0v0 the point where the tangent plane is that needs to be analysed
     * */
    public static void putu0v0Plane(int index, Vector2f u0v0) {
        u0v0Planes.put(index, u0v0);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param u0v0 the point where the osculating paraboloid is that needs to be analysed
     * */
    public static void putu0v0Paraboloid(int index, Vector2f u0v0) {
        u0v0Paraboloids.put(index, u0v0);
    }

    /**
     * Put the information about geometric object in storage
     * @param index the primary key of the object
     * @param u0v0 the point where the main directions are that needs to be analysed
     * */
    public static void putu0v0MainDir(int index, Vector2f u0v0) {
        u0v0MainDirs.put(index, u0v0);
    }

    /**
     * Put whether surfaces and curves are needed to be rendered
     * @param needsUpdate_ true if they are needed to be rendered and false otherwise
     * */
    public static void putNeedsUpdate(boolean needsUpdate_) {
        needsUpdate = needsUpdate_;
    }

    /**
     * Put whether the certain geometric object is needed to be rendered
     * @param needsRedrawing_ true if it is needed to be rendered and false otherwise
     * */
    public static void putNeedsRedrawing(int index, boolean needsRedrawing_) {
        needsRedrawing.put(index, needsRedrawing_);
    }

    /**
     * Put whether the certain geometric object is needed to be changed
     * @param needsChanging_ true if it is needed to be changed and false otherwise
     * */
    public static void putNeedsChanging(int index, boolean needsChanging_) {
        needsChanging.put(index, needsChanging_);
    }

    /**
     * Give instructions to redraw the geometric object
     * @param index the primary key of the object
     * */
    public static void redraw(int index) {
        needsRedrawing.put(index, true);
        needsUpdate = true;
    }

    /**
     * Give instructions to change the geometric object
     * @param index the primary key of the object
     * */
    public static void change(int index) {
        needsChanging.put(index, true);
        needsUpdate = true;
    }

    /**
     * Get surface by its primary key
     * @param index the primary key of the surface
     * @return the surface with received primary key
     * */
    public static Surface getSurface(int index) {
        return surfaces.get(index);
    }

    /**
     * Get curve by its primary key
     * @param index the primary key of the curve
     * @return the curve with received primary key
     * */
    public static Curve getCurve(int index) {
        return curves.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether this object is surface
     * */
    public static boolean getIsSurface(int index) {
        return whetherSurfaces.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether this object is parametric
     * */
    public static boolean getIsParametric(int index) {
        return whetherParametric.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the text field that bound to this object
     * */
    public static TextField getField(int index) {
        return fields.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the frame that bound to this object
     * */
    public static Pane getFrame(int index) {
        return frames.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the string representation of the object's function
     * */
    public static String getFunction(int index) {
        return functions.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return mode of object's rendering
     * */
    public static String getMode(int index) {
        return modes.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the colour of this object
     * */
    public static Vector3f getColour(int index) {
        return colours.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether tangent plane is needed to be rendered
     * */
    public static boolean getPlane(int index) {
        return planes.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether osculating paraboloid is needed to be rendered
     * */
    public static boolean getParaboloid(int index) {
        return paraboloids.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether main directions are needed to be rendered
     * */
    public static boolean getMainDir(int index) {
        return mainDirs.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether Frenet frame is needed to be rendered
     * */
    public static boolean getFrenet(int index) {
        return frenets.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the first variable of the object's function
     * */
    public static String getFVar(int index) {
        return fvars.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the left border of the object's function definition by the first variable
     * */
    public static float getFFrom(int index) {
        return ffroms.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the right border of the object's function definition by the first variable
     * */
    public static float getFTo(int index) {
        return ftos.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the step for calculating the object's function by the first variable
     * */
    public static float getFStep(int index) {
        return fsteps.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether the data about the first variable is needed to be multiplied by pi
     * */
    public static boolean getFPi(int index) {
        return fpi.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the second variable of the object's function
     * */
    public static String getSVar(int index) {
        return svars.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the left border of the object's function definition by the second variable
     * */
    public static float getSFrom(int index) {
        return sfroms.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the right border of the object's function definition by the second variable
     * */
    public static float getSTo(int index) {
        return stos.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the step for calculating the object's function by the second variable
     * */
    public static float getSStep(int index) {
        return ssteps.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return whether the data about the second variable is needed to be multiplied by pi
     * */
    public static boolean getSPi(int index) {
        return spi.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the point that needs to be analysed
     * */
    public static float getT0(int index) {
        return t0.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the point where the tangent plane is that needs to be analysed
     * */
    public static Vector2f getu0v0Plane(int index) {
        return u0v0Planes.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the point where the osculating paraboloid is that needs to be analysed
     * */
    public static Vector2f getu0v0Paraboloid(int index) {
        return u0v0Paraboloids.get(index);
    }

    /**
     * Get the information about geometric object in storage
     * @param index the primary key of the object
     * @return the point where the main directions are that needs to be analysed
     * */
    public static Vector2f getu0v0MainDir(int index) {
        return u0v0MainDirs.get(index);
    }

    /**
     * Get the information whether surfaces and curves are needed to be rendered
     * @return true if they are needed to be rendered and false otherwise
     * */
    public static boolean getNeedsUpdate() {
        return needsUpdate;
    }

    /**
     * Get the information whether the certain geometric object is needed to be rendered
     * @return true if it is needed to be rendered and false otherwise
     * */
    public static boolean getNeedsRedrawing(int index) {
        return needsRedrawing.get(index);
    }

    /**
     * Get the information whether the certain geometric object is needed to be changed
     * @return true if it is needed to be changed and false otherwise
     * */
    public static boolean getNeedsChanging(int index) {
        return needsChanging.get(index);
    }

    /**
     * Get the set of primary keys
     * @return set of primary keys
     * */
    public static Set<Integer> getIndices() {
        return whetherSurfaces.keySet();
    }

    /**
     * Get the next colour to generate
     * @return generated colour
     * */
    public static Vector3f getNextColour(int index) {
        return colourArchive.get(index % colourArchive.size());
    }

    /**
     * Get next primary key
     * @return the next primary key
     * */
    public static int getNextIndex() {
        int result = 0;
        while (whetherSurfaces.containsKey(result)) {
            result++;
        }
        return result;
    }

    /**
     * Fill storage with default information about the surface
     * @param index the primary key of the surface
     * */
    public static void addSurfaceUnit(int index) {
        whetherSurfaces.put(index, true);
        whetherParametric.put(index, false);
        functions.put(index, "");
        modes.put(index, "Solid");
        colours.put(index, getNextColour(index));
        planes.put(index, false);
        paraboloids.put(index, false);
        mainDirs.put(index, false);
        fvars.put(index, "u");
        ffroms.put(index, -5.0f);
        ftos.put(index, 5.0f);
        fsteps.put(index, 0.1f);
        fpi.put(index, false);
        svars.put(index, "v");
        sfroms.put(index, -5.0f);
        stos.put(index, 5.0f);
        ssteps.put(index, 0.1f);
        spi.put(index, false);
        u0v0Planes.put(index, new Vector2f(0.0f, 0.0f));
        u0v0Paraboloids.put(index, new Vector2f(0.0f, 0.0f));
        u0v0MainDirs.put(index, new Vector2f(0.0f, 0.0f));
        needsRedrawing.put(index, false);
        needsChanging.put(index, false);
    }

    /**
     * Fill storage with default information about the new surface
     * */
    public static void addStandardSurfaceUnit() {
        addSurfaceUnit(getNextIndex());
    }

    /**
     * Fill storage with default information about the new curve
     * */
    public static void addStandardCurveUnit() {
        addCurveUnit(getNextIndex());
    }

    /**
     * Fill storage with default information about the curve
     * @param index the primary key of the curve
     * */
    public static void addCurveUnit(int index) {
        whetherSurfaces.put(index, false);
        whetherParametric.put(index, false);
        functions.put(index, "");
        colours.put(index, getNextColour(index));
        frenets.put(index, false);
        fvars.put(index, "t");
        ffroms.put(index, -5.0f);
        ftos.put(index, 5.0f);
        fsteps.put(index, 0.1f);
        fpi.put(index, false);
        t0.put(index, 0.0f);
        needsRedrawing.put(index, false);
        needsChanging.put(index, false);
    }

    /**
     * Clear the information from storage about the surface
     * @param index the primary key of the surface
     * */
    public static void removeSurfaceUnit(int index) {
        whetherSurfaces.remove(index);
        whetherParametric.remove(index);
        functions.remove(index);
        modes.remove(index);
        colours.remove(index);
        planes.remove(index);
        paraboloids.remove(index);
        mainDirs.remove(index);
        fvars.remove(index);
        ffroms.remove(index);
        ftos.remove(index);
        fsteps.remove(index);
        fpi.remove(index);
        svars.remove(index);
        sfroms.remove(index);
        stos.remove(index);
        ssteps.remove(index);
        spi.remove(index);
        u0v0Planes.remove(index);
        u0v0Paraboloids.remove(index);
        u0v0MainDirs.remove(index);
    }

    /**
     * Clear the information from storage about the curve
     * @param index the primary key of the curve
     * */
    public static void removeCurveUnit(int index) {
        whetherSurfaces.remove(index);
        whetherParametric.remove(index);
        functions.remove(index);
        colours.remove(index);
        frenets.remove(index);
        fvars.remove(index);
        ffroms.remove(index);
        ftos.remove(index);
        fsteps.remove(index);
        fpi.remove(index);
        t0.remove(index);
    }

}
>>>>>>> 4f3c886f3ff1ae2c84328c3abe8a64dfc85d4b78
