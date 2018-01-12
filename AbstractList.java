package proj;

public abstract class AbstractList {
	//these are the methods that the observers will be using
	public abstract class AbstractIterator implements IteratorInterface{
		
	}
	public abstract AbstractIterator getIterator();
	public abstract void append(Shape newShape);
}
