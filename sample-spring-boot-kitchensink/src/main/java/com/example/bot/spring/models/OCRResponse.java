package com.example.bot.spring.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OCRResponse {
	
	/** This handles the OCR Response for image to text conversion.
	 * 
	 */
	
	/**
	 * This is the responses array.
	 */
	private Response[] responses;
	
	/** 
	 * This is the constructor.
	 */
	public OCRResponse() {}
	
	/** This method is the getter of the responses.
	 * 
	 * @return the responses
	 */
	public Response[] getResponses() {
		return responses;
	}
	
	/** This method is the setter of the responses.
	 * 
	 * @param responses the responses
	 */
	public void setResponses(Response[] responses) {
		this.responses = responses;
	}
}