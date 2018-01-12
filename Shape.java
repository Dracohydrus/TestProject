package proj;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {
	private Coordinates referencePoint, lastMousePosition;
	private boolean shapeSelected = false, perimeterSelected = false;
	private Color colorShape, originalColorShape;

	public abstract boolean onThePerimeter(Coordinates mousePosition);
	public abstract double calculateArea();
	public abstract void showMe(Graphics g);
	public abstract boolean shapeIsSelected(Coordinates positionOfMouse);
	public abstract String toString();

	public Shape(Color color){
		referencePoint = new Coordinates(200,200);
		colorShape = color;
	}
	
	/* 
	 * changeColor(c) modifies the color of the shape to the new color c
	 */
	public void changeColor(Color c){
		colorShape = c;
	}
	
	public void changeColorTemporarily(){
		if(!perimeterSelected)
			originalColorShape = colorShape;
		colorShape = Color.yellow;
		perimeterSelected = true;
	}
	public void changeColorBack(){
		colorShape = originalColorShape;
		perimeterSelected = false;
	}
	public boolean shapeIsSelected(){
		return shapeSelected;
	}
	
	/*
	 * Method moveShape(currentPositionMouse) allows the user to drag the rectangle by dragging the mouse
	 * button. The shape is first selected by pressing the mouse button with the cursor inside the shape.
	 * Then, if the user drags the mouse, the shape should move with the mouse.
	 * Moving a rectangle simply means modifying the reference point (upper left corner point) so that
	 * rectangle moves with the mouse position.
	 * The idea is that if the x and the y coordinate of the position of the mouse is moved by specified
	 * amounts,the reference point must change by exactly the same amount.
	 */
	public void moveShape(Coordinates currentPositionMouse){
		if (shapeSelected) {
			// If a shape is selected for a move operation, change the reference point 
			// as the mouse is being dragged.
			referencePoint.setX(referencePoint.getX() + 
					(currentPositionMouse.getX() -
					lastMousePosition.getX()));
			referencePoint.setY(referencePoint.getY() +
					(currentPositionMouse.getY() -
					lastMousePosition.getY()));
			lastMousePosition = currentPositionMouse;
		}
	}
	
	/*
	 * savePositionWhereUserPressedMouse(int x, int y)is used to 
	 * save the position where the user Pressed the mouse button.
	 * This is useful for moving the shape.
	 */
	public void savePositionWhereUserPressedMouse(int x, int y){
		setLastMousePosition(new Coordinates(x, y));
	}
	
	public void resetShapeSelected(){
		shapeSelected = false;
	}
	
	//getter and setter methods
	public Coordinates getReferencePoint(){
		return referencePoint;
	}
	
	public void setReferencePoint(Coordinates newReferencePoint){
		referencePoint = newReferencePoint;
	}
	
	public boolean getShapeSelected(){
		return shapeSelected;
	}
	
	public void setShapeSelected(boolean boolValue){
		shapeSelected = boolValue;
	}
	
	public void setLastMousePosition(Coordinates mousePosition){
		lastMousePosition = mousePosition;
	}
	
	public Color getColorShape(){
		return colorShape;
	}
		
	public boolean getPerimeterSelected(){
		return perimeterSelected;
	}
}
