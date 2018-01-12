package proj;

import java.awt.Color;
import java.awt.Graphics;

/*
 * Written by Subir Bandyopadhyay Sept 1, 2012
 * An object of class Circle represents a circle. 
 * 
 * Properties of class Circle
 * referencePoint - the left top corner of enclosing square - the reference point for the circle.
 * diameter  - the diameter of the circle
 * lastMousePosition - the last position where the mouse was pressed, 
 * 						useful when moving the rectangle
 * colorCircle - color of the circle
 * shapeSelected - a boolean variable which is true if the circle is 
 * 					selected for moving 
 * 
 * capabilities of class Circle (See below for more details)
 * onThePerimeter - returns true if mouse is pressed very close to the circumference of the circle
 * changeColor - modify the color of the circle
 * changeColorTemporarily - modify the color of the circle to yellow - useful when selecting a circle
 * changeColorBack - revert back to original color
 * changeShape - modify the width or the height of the circle
 * moveShape - move the circle by dragging the mouse 
 * shapeIsSelected - mark a circle as "selected" by pressing the mouse button inside the trctangle
 * resetShapeSelected - reset the shapeSelected flag
 * calculateArea - determine the area of the circle
 * showMe - display the circle inside the frame
 * toString - return a string describing the circle.
 * savePositionWhereUserPressedMouse - useful when dragging a shape
 * 
 */
public class Circle extends Shape{
	public final double PI = 3.14;
	private int diameter;

	public Circle(Color currentColor){
		super(currentColor);
		diameter = 50;
	}

	public void changeDiameter(int newDiameter){
		diameter = newDiameter;
	}

	/*
	 * Method onThePerimeter checks if the user pressed the mouse button on the perimeter. 
	 * If so, it returns true; otherwise it return false.
	 */

	public boolean onThePerimeter(Coordinates mousePosition)
	{// the perimeter is selected if the distance from the point (x, y) to the center  is 
		// between radius - 5 and radius + 5
		int distanceFromCenter;
		Coordinates centreOfCircle;

		centreOfCircle = new Coordinates(getReferencePoint().getX() + diameter/2,
				getReferencePoint().getY() + diameter/2);
		distanceFromCenter = (int) distance(mousePosition, centreOfCircle);
		if ((distanceFromCenter >= diameter/2 - 5) && 
				(distanceFromCenter <= diameter/2 + 5)) {
			return true;
		} else{
			return false;
		}
	}

	/*
	 * Method shapeIsSelected(positionOfMouse) checks if the position of the mouse where the user
	 * pressed the left mouse button is within the shape (at least 5 pixels away from the perimeter.
	 * If so, the flag shapeSelected is set and the method returns true.
	 * Otherwise, the flag shapeSelected is reset and the method returns false.
	 */

	public boolean shapeIsSelected(Coordinates positionOfMouse){ 
		// Check if the user pressed the mouse button inside the shape
		double distance;

		Coordinates centreOfCircle;
		centreOfCircle = computeCenterOfCircle(getReferencePoint(), diameter);
		distance = distance(positionOfMouse, centreOfCircle);
		if (distance < diameter/2 - 5){
			setShapeSelected(true);
			setLastMousePosition(positionOfMouse);
			return true; 
		} else {
			setShapeSelected(false);
			return false;
		}
	}

	/*
	 * changeShape changes the diameter of the circle, if
	 * currentPhase is 3 .
	 * currentPhase = 0, means the user is moving the shape
	 *              = 1, a new shape is being created
	 *              = 2, the the color of the new shape is being modified,
	 *              = 3, the diameter of the circle is being modified,
	 */

	public void changeShape(int currentPhase, int size){
		int newReferencePointX, newReferencePointY;
		if (currentPhase == 3){
			newReferencePointX = getReferencePoint().getX() + (diameter - size)/2;
			newReferencePointY = getReferencePoint().getY() + (diameter - size)/2;
			setReferencePoint(new Coordinates(newReferencePointX, newReferencePointY));
			diameter = size; 
		} 

	}

	private double distance(Coordinates point1, Coordinates point2){
		double dist; // dist is the distance from point1 to point2
		dist = Math.sqrt((double) ((point1.getX() - point2.getX())*
				(point1.getX() - point2.getX()) +
				(point1.getY() - point2.getY())*(point1.getY() - point2.getY())));
		return dist ;
	} 
	public Coordinates computeCenterOfCircle(Coordinates referencePoint, int diameter){
		int xValueOfCenter,yValueOfCenter;
		xValueOfCenter = referencePoint.getX() + diameter/2;;
		yValueOfCenter = referencePoint.getY() + diameter/2;
		return new Coordinates(xValueOfCenter, yValueOfCenter);
	}

	/*
	 * calculateArea() returns the area of the rectangle
	 */

	public double calculateArea(){ 
		return (PI * diameter * diameter/4);
	}

	/*
	 * showMe(g) displays the rectangle using the Graphic object g.
	 * It sets the color to be used, and draws the rectangle using the specified 
	 * reference point, the width and the height.
	 */

	public void showMe(Graphics g){
		g.setColor(getColorShape());
		g.drawOval(getReferencePoint().getX(), // Draw a circle with the specified 
				// corner point and diameter
				getReferencePoint().getY(),   
				diameter,
				diameter);
	}

	/* 
	 * toString() returns the description of the rectangle - the color, 
	 * the reference point and the size.
	 */

	public String toString(){
		return ("Circle with reference point " + getReferencePoint() + " having diameter " + diameter + "\n");
	}
}
