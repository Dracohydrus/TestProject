package proj;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class SquareAndRectangleObserver extends JFrame implements Observer{
	final int WIDTH = 500, HEIGHT = 300;
	private JTextArea outputArea;
	MyList shapes;
	Shape currentShape;
	AbstractList.AbstractIterator iterator;
	int numShapes;
	
	
	public SquareAndRectangleObserver(){
		super("Rectangles and Squares");
		outputArea = new JTextArea(20, 30);
		add(outputArea);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		numShapes = 0;
	}
	
	public void update(Observable o, Object arg) {
		if(arg instanceof MyList){
			shapes = (MyList)arg;
		}
		if(arg instanceof AbstractList.AbstractIterator){
			iterator = (AbstractList.AbstractIterator)arg;
		}
		iterator = shapes.getIterator();
		outputArea.setText(createString());
	}
	
	/*
	 * This method will create the String if the Shape is a rectangle or a square and print it in the appropriate 
	 * frame.
	 */
	private String createString(){
		String outputString = "";
		iterator.first();
		while(iterator.doesCurrentExist()){
			currentShape = iterator.getShape();
			if (currentShape instanceof Rectangle || currentShape instanceof Square)
				outputString += currentShape.toString();
			iterator.next();
		}
		return outputString;
	}
}
