package com.example.bot.spring.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TextAnnotation {
	
	/** This is the text annotation for the OCR.
	 * 
	 */
	
	/** This is the locale of the text annotation.
	 * 
	 */
	private String locale;
	
	/** This is the description for the text annotation.
	 * 
	 */
	private String description;
	
	/** This is the bounding polygon for the text annotation.
	 * 
	 */
	private BoundingPolygon boundingPoly;
	
	/** This is the constructor for the text annotation.
	 * 
	 */
	public TextAnnotation() {}
	
	/** This method is the getter for the locale.
	 * 
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}
	
	/** This method is the setter for the locale.
	 * 
	 * @param locale the locale
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	/** This method is the getter for the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/** This method is the setter for the description.
	 * 
	 * @param description the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** This method is the getter for the bounding polygon.
	 * 
	 * @return the bounding polygon
	 */
	public BoundingPolygon getBoundingPoly() {
		return boundingPoly;
	}
	
	/** This method is the setter for the bounding polygon.
	 * 
	 * @param boundingPoly the bounding polygon
	 */
	public void setBoundingPoly(BoundingPolygon boundingPoly) {
		this.boundingPoly = boundingPoly;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class BoundingPolygon {
	
	/** This is the bounding polygon for the OCR.
	 * 
	 */
	
	/** This is an array of the vertices of the bounding polygon.
	 * 
	 */
	private Point[] vertices;
	
	/** This is the constructor of the bounding polygon.
	 * 
	 */
	public BoundingPolygon() {}
	
	/** This is the getter of the vertices of the bounding polygon.
	 * 
	 * @return the vertices of the bounding polygon
	 */
	public Point[] getVertices() {
		return vertices;
	}
	
	/** This is the setter of the vertices of the bounding polygon.
	 * 
	 * @param vertices the vertices of the bounding polygon
	 */
	public void setVertices(Point[] vertices) {
		this.vertices = vertices;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Point {
	
	/** This is the point object for the OCR.
	 * 
	 */
	
	/** The x-value of the point.
	 * 
	 */
	private int x;
	
	/** The y-value of the point.
	 * 
	 */
	private int y;
	
	/** The constructor of the point object.
	 * 
	 */
	public Point() {}
	
	/** This method is the getter for the x-value.
	 * 
	 * @return the x-value of the point
	 */
	public int getX() {
		return x;
	}
	
	/** This method is the setter for the x-value.
	 * 
	 * @param x the x-value of the point
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/** This method is the getter for the y-value.
	 * 
	 * @return the y-value of the point
	 */
	public int getY() {
		return y;
	}
	
	/** This method is the setter for the y-value.
	 * 
	 * @param y the y-value of the point
	 */
	public void setY(int y) {
		this.y = y;
	}
}