package com.chernikovs.models;

/**A class describing raw model*/
public class RawModel {
	
	private int vaoID;
	private int vertexCount;
	
	public RawModel(int vaoID, int vertexCount){
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	/**
	 * Get VAO ID
	 * @return VAO ID
	 * */
	public int getVaoID() {
		return vaoID;
	}

	/**
	 * Get vertex count
	 * @return vertex count
	 * */
	public int getVertexCount() {
		return vertexCount;
	}

}
