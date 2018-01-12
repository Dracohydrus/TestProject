package proj;

import java.awt.Color;
import java.awt.Graphics;

public class CompoundShape extends Shape{
	private Shape[] shapes;
	private int numShapes;

	public CompoundShape(){
		super(Color.black);
		numShapes = 0;
		shapes = new Shape[100];	//making the size of the array a default size
	}

	/*
	 * This will check if any of the shapes part of the compound shape is selected on the Perimeter. This will be used for
	 * gluing two compound shapes together
	 */
	public boolean onThePerimeter(Coordinates mousePosition) {
		boolean result = false;
		for(Shape currentShape: shapes){
			if (currentShape instanceof Rectangle){
				result = result || ((Rectangle) currentShape).onThePerimeter(mousePosition);
			} else if(currentShape instanceof Square){
				result = result || ((Square) currentShape).onThePerimeter(mousePosition);
			} else if(currentShape instanceof Circle){
				result = result || ((Circle) currentShape).onThePerimeter(mousePosition);
			}
		}
		return result;
	}

	/*
	 * This will return the area of all of the shapes that are part of this complex shape
	 */
	public double calculateArea() {
		double result = 0;
		for(Shape currentShape:shapes){
			if (currentShape instanceof Rectangle){
				result += ((Rectangle) currentShape).calculateArea();
			} else if(currentShape instanceof Square){
				result += ((Square) currentShape).calculateArea();
			} else if(currentShape instanceof Circle){
				result += ((Circle) currentShape).calculateArea();
			}
		}
		return result;
	}

	public void showMe(Graphics g) {
		for(Shape currentShape: shapes){
			if (currentShape instanceof Rectangle){
				((Rectangle) currentShape).showMe(g);
			} else if(currentShape instanceof Square){
				((Square) currentShape).showMe(g);
			} else if(currentShape instanceof Circle){
				((Circle) currentShape).showMe(g);
			}
		}
	}

	/*
	 * If any of the shapes within the compound shape are selected, than they should all be 
	 * considered selected
	 */
	public boolean shapeIsSelected(Coordinates positionOfMouse) {
		boolean result = false;
		for(Shape currentShape:shapes){
			if (currentShape instanceof Rectangle){
				result = result || ((Rectangle)currentShape).shapeIsSelected(positionOfMouse);
			} else if(currentShape instanceof Square){
				result = result || ((Square)currentShape).shapeIsSelected(positionOfMouse);
			} else if(currentShape instanceof Circle){
				result = result || ((Circle)currentShape).shapeIsSelected(positionOfMouse);
			}
		}
		//sets the compound shape as selected so that it will call be able to call move
		this.setShapeSelected(result);
		//sets all of the shapes in the compound to selected
		//and set the last mouse position to the current mouse position
		if(result){
			for(int knt=0;knt<numShapes;knt++){
				if(shapes[knt] != null){
					shapes[knt].setShapeSelected(true);
					shapes[knt].setLastMousePosition(positionOfMouse);
				}
			}
		}
		return result;
	}

	public void moveShape(Coordinates currentPositionMouse){
		for(int knt=0;knt<numShapes;knt++){
			shapes[knt].moveShape(currentPositionMouse);
		}
	}
	
	public String toString() {
		//not sure what to print out in this method yet, maybe a list of the shapes that it contains
		int numRect=0,numSqr=0,numCir=0;
		for(Shape currentShape:shapes){
			if(currentShape instanceof Rectangle){
				numRect++;
			}
			else if (currentShape instanceof Square){
				numSqr++;
			}
			else if(currentShape instanceof Circle){
				numCir++;
			}
		}
		return ("This is a complex shape with " 
				+ numRect + " Rectangles, " + numSqr + " Squares and " 
				+ numCir + " Circles with an area of " + calculateArea());
	}

	public int getSize(){
		return numShapes;
	}

	public void addShape(Shape shape){
		shapes[numShapes] = shape;
		numShapes++;
	}
	
	public void resetShapeSelected(){
		for(Shape currentShape:shapes){
			if(currentShape != null) 
				currentShape.resetShapeSelected();
		}
		this.setShapeSelected(false);
	}
	
	public void changeColorTemporarily(){
		for(Shape currentShape:shapes){
			if(currentShape != null)
				currentShape.changeColorTemporarily();
		}
	}
	
	public void changeColorBack(){
		for(Shape currentShape:shapes){
			if(currentShape != null)
				System.out.println("Changing color back for shape " + currentShape);
				currentShape.changeColorBack();
		}
	}
		
	public boolean getPerimeterSelected(){
		boolean result = false;
		for(Shape currentShape:shapes){
			if(currentShape != null){
				result = result || currentShape.getPerimeterSelected();
			}
		}
		return result;
	}
	
}
