package com.chernikovs.models;

import com.chernikovs.textures.ModelTexture;

/**A class describing textured model*/
public class TexturedModel {
	
	private RawModel rawModel;
	private ModelTexture texture;
	
	public TexturedModel(RawModel model, ModelTexture texture){
		this.rawModel = model;
		this.texture = texture;
	}

	/**
	 * Get raw model
	 * @return raw model
	 * */
	public RawModel getRawModel() {
		return rawModel;
	}

	/**
	 * Get texture
	 * @return texture
	 * */
	public ModelTexture getTexture() {
		return texture;
	}

}
