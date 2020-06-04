package com.chernikovs.textures;

/**A class describing model texture*/
public class ModelTexture {
	
	private int textureID;
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	public ModelTexture(int texture){
		this.textureID = texture;
	}

	/**
     * Get ID of the texture
     * @return ID of the texture
     * */
	public int getID(){
		return textureID;
	}

    /**
     * Get shine damper of the material
     * @return shine damper of the material
     * */
    public float getShineDamper() {
        return shineDamper;
    }

    /**
     * Set shine damper of the material
     * @param shineDamper shine damper of the material
     * */
    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    /**
     * Get reflectivity of the material
     * @return reflectivity of the material
     * */
    public float getReflectivity() {
        return reflectivity;
    }

    /**
     * Set reflectivity of the material
     * @param reflectivity reflectivity of the material
     * */
    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
