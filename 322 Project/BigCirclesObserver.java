package proj;

import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

public class BigCirclesObserver extends JFrame implements Observer{
	MyList shapes;
	Shape currentShape;

	public BigCirclesObserver(){
		super("Big Circles");
		setSize(TesterFrame.WIDTH, TesterFrame.HEIGHT); 
		setVisible(true);   
		shapes = new MyList(1);
	}

	/*
	 * The update method will change the local variables in this observer depending on type
	 * and it will always call the show Circles method after changing the values
	 */
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof MyList){
			shapes = (MyList)arg1;
		}
		repaint();
	}

	public void paint(Graphics g){
		Circle currentCircle;
		super.paint(g);
		AbstractList.AbstractIterator iterator = shapes.getIterator();

		//Make sure that the iterator has been defined before trying to execute any of the iterator code
		//This line is needed because paint is called when the frame is created and there is no iterator defined at this moment in time
		if(iterator != null){
			iterator.first();
			while(iterator.doesCurrentExist()){
				currentShape = iterator.getShape();
				if (currentShape instanceof Circle && currentShape != null){
					currentCircle = (Circle)currentShape;
					if (currentCircle.calculateArea() >= 5000.00){
						currentCircle.showMe(g);
					}
				}
				iterator.next();
			}
		}
		System.out.println("N = " + shapes.getSize());
	}
}
