package com.quinn.xmpp;

/**
 * @author Quinn
 * @date 2015-3-24
 * 
 * Request codes
 */
public interface RequestCodes {

	/**
	 * Request to signup
	 */
	int SIGNUP_SUCCESS = 1;
	
	/**
	 * Request to open camera
	 */
	int CAMERA_REQUEST = 2;
	
	/**
	 * Request to open Gallery
	 */
	int GALLERY_REQUEST = 3;
	
	/**
	 * Request to select a file
	 */
	int CHOOSE_FILE_REQUEST = 4;
	
	/**
	 * Request to select a photo
	 */
	int CHOOSE_PHOTO_REQUEST = 5;
	
}


