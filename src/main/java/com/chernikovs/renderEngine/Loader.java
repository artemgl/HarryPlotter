package com.chernikovs.renderEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.chernikovs.models.RawModel;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.PNGDecoder;

import static org.lwjgl.opengl.GL11.*;

/**A class describing loader*/
public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();

	/**
	 * Load array to VAO
	 * @param positions array of positions to load
	 * @return loaded raw model
	 * */
	public RawModel loadToVAO(float[] positions) {
	    int vaoID = createVAO();
        storeDataInAttributeList(0, 3, positions);
        unbindVAO();
        return new RawModel(vaoID, positions.length / 3);
	}

	/**
	 * Load arrays to VAO
	 * @param positions array of positions to load
	 * @param textureCoords array of texture coordinates to load
	 * @param normals array of normals to load
	 * @param indices array of indices to load
	 * @return loaded raw model
	 * */
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}

	/**
	 * Load texture
	 * @param fileName path to the texture
	 * @return ID of the texture
	 * */
	public int loadTexture(String fileName) {
		int id = 0;
		try {
			//load png file
			//PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream("src/main/resources/" + fileName + ".png"));
			PNGDecoder decoder = new PNGDecoder(new FileInputStream("src/main/resources/" + fileName + ".png"));

			ByteBuffer buf = ByteBuffer.allocateDirect(
					4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.RGBA);
			buf.flip();

			//create a texture
			id = glGenTextures();

			//bind the texture
			glBindTexture(GL_TEXTURE_2D, id);

			//tell opengl how to unpack bytes
			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

			//set the texture parameters, can be GL_LINEAR or GL_NEAREST
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

			//upload texture
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);

			// Generate Mip Map
			GL30.glGenerateMipmap(GL_TEXTURE_2D);
		} catch (IOException exc) {
			exc.printStackTrace();
		}


		return id;
//		Texture texture = null;
//		try {
//			texture = TextureLoader.getTexture("PNG",
//					new FileInputStream("src/main/resources/" + fileName + ".png"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.err.println("Tried to load texture " + fileName + ".png , didn't work");
//			System.exit(-1);
//		}
//		textures.add(texture.getTextureID());
//		return texture.getTextureID();
	}

	/**
	 * Remove data from memory
	 * */
	public void cleanUp(){
		for(int vao:vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture:textures){
			GL11.glDeleteTextures(texture);
		}
	}

	/**
	 * Create VAO
	 * @return created VAO ID
	 * */
	private int createVAO(){
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize,float[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber,coordinateSize,GL11.GL_FLOAT,false,0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int[] indices){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

}
