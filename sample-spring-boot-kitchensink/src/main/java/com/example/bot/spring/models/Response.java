package com.example.bot.spring.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
	
	/** This class is the response for the OCR.
	 * 
	 */
	
	/**
	 * This is the text annotations from the image.
	 */
	private TextAnnotation[] textAnnotations;
	
	/** This is the constructor.
	 * 
	 */
	public Response() {}
	
	/** This method is the getter for the text annotations.
	 * 
	 * @return the text annotations
	 */
	public TextAnnotation[] getTextAnnotations() {
		return textAnnotations;
	}
	
	/** This method is the setter for the text annotations.
	 * 
	 * @param textAnnotations the text annotations
	 */
	public void setTextAnnotations(TextAnnotation[] textAnnotations) {
		this.textAnnotations = textAnnotations;
	}
}
