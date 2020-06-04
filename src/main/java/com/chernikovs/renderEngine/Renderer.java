<<<<<<< HEAD
package com.chernikovs.renderEngine;

import com.chernikovs.models.RawModel;
import com.chernikovs.models.TexturedModel;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.chernikovs.shaders.StaticShader;
import com.chernikovs.textures.ModelTexture;
import com.chernikovs.toolbox.Maths;
import com.chernikovs.entities.Curve;
import com.chernikovs.entities.Entity;
import com.chernikovs.entities.Surface;
import com.chernikovs.entities.Vector;

import static org.lwjgl.opengl.GL11.*;

/**A class describing renderer*/
public class Renderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private Matrix4f projectionMatrix;

	public Renderer(StaticShader shader){
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	/**Prepare to rendering*/
	public void prepare() {
		GL11.glEnable(GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Render surfaces with shader
	 * @param surfaces surfaces to render
	 * @param shader shader to render with
	 * */
	public void renderSurfaces(ArrayList<Surface> surfaces, StaticShader shader) {
	    for (Surface surface : surfaces) {
	        render(surface, shader);
	    }
	}

	/**
	 * Render curves with shader
	 * @param curves curves to render
	 * @param shader shader to render with
	 * */
	public void renderCurves(ArrayList<Curve> curves, StaticShader shader) {
        for (Curve curve : curves) {
            render(curve, shader);
        }
    }

	/**
	 * Render vectors with shader
	 * @param vectors vectors to render
	 * @param shader shader to render with
	 * */
	public void renderVectors(ArrayList<Vector> vectors, StaticShader shader) {
        for (Vector vector : vectors) {
            render(vector, shader);
        }
    }

    /**
	 * Render a vector with shader
	 * @param vector vector to render
	 * @param shader shader to render with
	 * */
	public void render(Vector vector, StaticShader shader) {
        TexturedModel model = vector.getModel();
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        //GL20.glEnableVertexAttribArray(1);
        //GL20.glEnableVertexAttribArray(2);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(vector.getPosition(),
                vector.getRotX(), vector.getRotY(), vector.getRotZ(), vector.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadColour(vector.getColour());
        //ModelTexture texture = model.getTexture();
        //shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        //GL13.glActiveTexture(GL13.GL_TEXTURE0);
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
        //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glDrawArrays(GL11.GL_LINES, 0, rawModel.getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        //GL20.glDisableVertexAttribArray(1);
        //GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

	/**
	 * Render a curve with shader
	 * @param curve curve to render
	 * @param shader shader to render with
	 * */
	public void render(Curve curve, StaticShader shader) {
	    TexturedModel model = curve.getModel();
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        //GL20.glEnableVertexAttribArray(1);
        //GL20.glEnableVertexAttribArray(2);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(curve.getPosition(),
                curve.getRotX(), curve.getRotY(), curve.getRotZ(), curve.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadColour(curve.getColour());
        //ModelTexture texture = model.getTexture();
        //shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        //GL13.glActiveTexture(GL13.GL_TEXTURE0);
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
        //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		//GL11.glLineWidth(5);
        GL11.glDrawArrays(GL11.GL_LINE_STRIP, 0, rawModel.getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        //GL20.glDisableVertexAttribArray(1);
        //GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
	}

	/**
	 * Render an entity with shader
	 * @param entity entity to render
	 * @param shader shader to render with
	 * */
	public void render(Entity entity, StaticShader shader) {
		TexturedModel model = entity.getModel();
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadColour(entity.getColour());
		ModelTexture texture = model.getTexture();
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL_TEXTURE_2D, model.getTexture().getID());
		GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void createProjectionMatrix(){
		float aspectRatio = (float)Display.getWidth() * (float)Display.getRenderPart() / (float)Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.set(0, 0, x_scale);
		projectionMatrix.set(1, 1, y_scale);
		projectionMatrix.set(2, 2, -((FAR_PLANE + NEAR_PLANE) / frustum_length));
		projectionMatrix.set(2, 3, -1);
		projectionMatrix.set(3, 2, -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
		projectionMatrix.set(3, 3, 0);
	}

}
=======
package com.chernikovs.renderEngine;

import com.chernikovs.models.RawModel;
import com.chernikovs.models.TexturedModel;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.chernikovs.shaders.StaticShader;
import com.chernikovs.textures.ModelTexture;
import com.chernikovs.toolbox.Maths;
import com.chernikovs.entities.Curve;
import com.chernikovs.entities.Entity;
import com.chernikovs.entities.Surface;
import com.chernikovs.entities.Vector;

import static org.lwjgl.opengl.GL11.*;

/**A class describing renderer*/
public class Renderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private Matrix4f projectionMatrix;

	public Renderer(StaticShader shader){
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	/**Prepare to rendering*/
	public void prepare() {
		GL11.glEnable(GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Render surfaces with shader
	 * @param surfaces surfaces to render
	 * @param shader shader to render with
	 * */
	public void renderSurfaces(ArrayList<Surface> surfaces, StaticShader shader) {
	    for (Surface surface : surfaces) {
	        render(surface, shader);
	    }
	}

	/**
	 * Render curves with shader
	 * @param curves curves to render
	 * @param shader shader to render with
	 * */
	public void renderCurves(ArrayList<Curve> curves, StaticShader shader) {
        for (Curve curve : curves) {
            render(curve, shader);
        }
    }

	/**
	 * Render vectors with shader
	 * @param vectors vectors to render
	 * @param shader shader to render with
	 * */
	public void renderVectors(ArrayList<Vector> vectors, StaticShader shader) {
        for (Vector vector : vectors) {
            render(vector, shader);
        }
    }

    /**
	 * Render a vector with shader
	 * @param vector vector to render
	 * @param shader shader to render with
	 * */
	public void render(Vector vector, StaticShader shader) {
        TexturedModel model = vector.getModel();
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        //GL20.glEnableVertexAttribArray(1);
        //GL20.glEnableVertexAttribArray(2);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(vector.getPosition(),
                vector.getRotX(), vector.getRotY(), vector.getRotZ(), vector.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadColour(vector.getColour());
        //ModelTexture texture = model.getTexture();
        //shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        //GL13.glActiveTexture(GL13.GL_TEXTURE0);
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
        //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glDrawArrays(GL11.GL_LINES, 0, rawModel.getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        //GL20.glDisableVertexAttribArray(1);
        //GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

	/**
	 * Render a curve with shader
	 * @param curve curve to render
	 * @param shader shader to render with
	 * */
	public void render(Curve curve, StaticShader shader) {
	    TexturedModel model = curve.getModel();
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        //GL20.glEnableVertexAttribArray(1);
        //GL20.glEnableVertexAttribArray(2);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(curve.getPosition(),
                curve.getRotX(), curve.getRotY(), curve.getRotZ(), curve.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadColour(curve.getColour());
        //ModelTexture texture = model.getTexture();
        //shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        //GL13.glActiveTexture(GL13.GL_TEXTURE0);
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
        //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		//GL11.glLineWidth(5);
        GL11.glDrawArrays(GL11.GL_LINE_STRIP, 0, rawModel.getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        //GL20.glDisableVertexAttribArray(1);
        //GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
	}

	/**
	 * Render an entity with shader
	 * @param entity entity to render
	 * @param shader shader to render with
	 * */
	public void render(Entity entity, StaticShader shader) {
		TexturedModel model = entity.getModel();
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
				entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadColour(entity.getColour());
		ModelTexture texture = model.getTexture();
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL_TEXTURE_2D, model.getTexture().getID());
		GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void createProjectionMatrix(){
		float aspectRatio = (float)Display.getWidth() * (float)Display.getRenderPart() / (float)Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.set(0, 0, x_scale);
		projectionMatrix.set(1, 1, y_scale);
		projectionMatrix.set(2, 2, -((FAR_PLANE + NEAR_PLANE) / frustum_length));
		projectionMatrix.set(2, 3, -1);
		projectionMatrix.set(3, 2, -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
		projectionMatrix.set(3, 3, 0);
	}

}
>>>>>>> 4f3c886f3ff1ae2c84328c3abe8a64dfc85d4b78
