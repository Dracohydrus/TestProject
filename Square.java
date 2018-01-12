package proj;

import java.awt.Color;
import java.awt.Graphics;

public class Square extends Shape{
	//creating a square from the rectangle class
	private Rectangle square;
	
	/*
	 * The square constructor needs have a delegated object to square. So we initialize a new
	 * square in the constructor
	 */
	public Square(Color c){
		super(c);
		square = new Rectangle(c);
	}
	
	public void moveShape(Coordinates currentPositionMouse){
		square.moveShape(currentPositionMouse);
	}
	
	public void changeColorTemporarily(){
		square.changeColorTemporarily();
	}
	
	public void savePositionWhereUserPressedMouse(int x, int y){
		square.savePositionWhereUserPressedMouse(x, y);
	}

	public void changeColor(Color c){
		square.changeColor(c);
	}
	
	public void setReferencePoint(Coordinates newReferencePoint){
		square.setReferencePoint(newReferencePoint);
	}
	
	public Coordinates getReferencePoint(){
		return square.getReferencePoint();
	}
	
	public boolean getShapeSelected(){
		return square.getShapeSelected();
	}
	
	public void setShapeSelected(boolean boolValue){
		square.setShapeSelected(boolValue);
	}
	
	public void setLastMousePosition(Coordinates mousePosition){
		square.setLastMousePosition(mousePosition);
	}
	
	public void resetShapeSelected(){
		square.resetShapeSelected();
	}
	
	public boolean onThePerimeter(Coordinates mousePosition) {
		return square.onThePerimeter(mousePosition);
	}
	
	public double calculateArea() {
		return square.calculateArea();
	}
	
	public void showMe(Graphics g) {
		square.showMe(g);
	}
	
	public boolean shapeIsSelected(Coordinates positionOfMouse) {
		return square.shapeIsSelected(positionOfMouse);
	}
	
	public boolean shapeIsSelected(){
		return square.shapeIsSelected();
	}
	
	public Color getColorShape(){
		return square.getColorShape();
	}
	
	public void changeColorBack(){
		square.changeColorBack();
	}
	
	public boolean getPerimeterSelected(){
		return square.getPerimeterSelected();
	}
	
	/*
	 * The square toString method is just calling the square toString because there
	 * is a square implementation in the square toString
	 */
	public String toString() {
		return square.toString();
	}
	
	/*
	 * When changing either the width or the height of the square it will have to change 
	 * both values because that is the definition of a square
	 */
	public void changeSize(int newSize){
		square.changeWidth(newSize);
		square.changeHeight(newSize);
	}
}
