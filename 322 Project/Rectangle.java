package proj;

import java.awt.*;
/*
 * Written by Subir Bandyopadhyay Sept 1, 2012
 * An object of class Rectangle represents a rectangle. 
 * 
 * Properties of class Rectangle
 * referencePoint - where is the rectangle located
 * size  - the height and the width of the rectangle
 * lastMousePosition - the last position where the mouse was pressed, 
 * 						useful when moving the rectangle
 * colorRectangle - color of the rectangle
 * shapeSelected - a boolean variable which is true if the rectangle is 
 * 					selected for moving 
 * 
 * Properties of class Rectangle (See below for more details)
 * onAnEdge - returns true if mouse is pressed very close to one of the edges
 * changeColor - modify the color of the rectangle
 * changeShape - modify the width or the height of the rectangle
 * moveShape - move the rectangle by dragging the mouse 
 * shapeIsSelected - mark a rectangle as "selected" by pressing the mouse button inside the rectangle
 * resetShapeSelected - reset the shapeSelected flag
 * calculateArea - determine the area of the rectangle
 * showMe - display the rectangle inside the frame
 * toString - return a string describing the rectangle.
 * savePositionWhereUserPressedMouse - useful when dragging the shape
 * 
 */


public class Rectangle extends Shape{
	private int height, width;  // height and width of rectangle

	/*
	 * shapeSelected = false means that the  rectangle has  been selected for 
	 * moving by pressing mouse button inside the shape.
	 */


	// Constructor creates a black rectangle with size 50 X 50 with upper left point at (200, 200)
	public Rectangle(Color c){
		super(c);
		height = 50;
		width = 50;
	}
	/*
	 * Method onThePerimeter checks if the user pressed the mouse button on the perimeter. 
	 * If so, it returns true; otherwise it return false.
	 */

	public boolean onThePerimeter(Coordinates mousePosition){
		int xWhereMousePressed, yWhereMousePressed;
		//I created a variable that grabbed the reference point from 
		//the super class so the method would not have to be called 
		//multiple times
		Coordinates referencePoint = getReferencePoint();

		xWhereMousePressed = mousePosition.getX();
		yWhereMousePressed = mousePosition.getY();

		/*
		 * If the position where the user pressed the mouse button is within 5 pixels of 
		 * any side of the rectangle, the method will return true;
		 * Otherwise, it will return false.
		 */
		if (((xWhereMousePressed >= referencePoint.getX() - 5) &&
				(xWhereMousePressed <= (referencePoint.getX() + width + 5)) &&
				(yWhereMousePressed >= referencePoint.getY() - 5) &&
				(yWhereMousePressed <= (referencePoint.getY() + 5)))) return true;// top edge is edge # 0

		if (((xWhereMousePressed >= referencePoint.getX() - 5) &&
				(xWhereMousePressed <= (referencePoint.getX() + 5)) &&
				(yWhereMousePressed >= referencePoint.getY() - 5) &&
				(yWhereMousePressed <= (referencePoint.getY() + height + 5)))) return true;// left edge is edge # 1

		if (((xWhereMousePressed >= referencePoint.getX() - 5) &&
				(xWhereMousePressed <= (referencePoint.getX() + width + 5)) &&
				(yWhereMousePressed >= referencePoint.getY() + height - 5) &&
				(yWhereMousePressed <= (referencePoint.getY()+ height + 5)))) return true;//bottom edge-edge # 2

		if (((xWhereMousePressed >= referencePoint.getX() + width - 5) &&
				(xWhereMousePressed <= (referencePoint.getX() + width + 5)) &&
				(yWhereMousePressed >= referencePoint.getY() - 5) &&
				(yWhereMousePressed <= (referencePoint.getY() + height + 5)))) return true;// right edge-edge # 3

		return false;
	}

	public void changeWidth(int newWidth){
		width = newWidth;
	}
	
	public void changeHeight(int newHeight){
		height = newHeight;
	}
	
	/*
	 * Method shapeIsSelected(positionOfMouse) checks if the position of the mouse where the user
	 * pressed the left mouse button is within the shape (at least 5 pixels away from the perimeter.
	 * If so, the flag shapeSelected is set and the method returns true.
	 * Otherwise, the flag shapeSelected is reset and the method returns false.
	 */

	public boolean shapeIsSelected(Coordinates positionOfMouse){ 
		// Check if the user pressed the mouse button inside the shape
		int x, y;
		Coordinates referencePoint = getReferencePoint();
		x = positionOfMouse.getX();
		y = positionOfMouse.getY();
		if ((x >= referencePoint.getX() + 5) &&
				(x <= referencePoint.getX() + width - 5) &&
				(y >= referencePoint.getY() + 5) &&
				(y <= referencePoint.getY() + height - 5)) {
			setShapeSelected(true);
			setLastMousePosition(positionOfMouse);
			return true;
		}else {
			setShapeSelected(false);
			return false;
		}
	} 

	/*
	 * calculateArea() returns the area of the rectangle
	 */

	public double calculateArea(){ 
		return (width * height);
	}

	/*
	 * showMe(g) displays the rectangle using the Graphic object g.
	 * It sets the color to be used, and draws the rectangle using the specified 
	 * reference point, the width and the height.
	 */

	public void showMe(Graphics g){
		g.setColor(getColorShape());
		g.drawRect(getReferencePoint().getX(), // Draw a rectangle with the specified 
				// corner point
				getReferencePoint().getY(),  // width and height
				width,
				height);
	}

	/* 
	 * toString() returns the description of the rectangle - the color, 
	 * the reference point and the size.
	 */

	public String toString(){
		if(width == height){
			return ("Square with reference point " + getReferencePoint() + " having width "
					+ width + " and height " + height + "\n");
		}
		return ("Rectangle with reference point " + getReferencePoint() + " having width "
				+ width + " and height " + height + "\n");
	}
}


