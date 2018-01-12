package proj;

public interface IteratorInterface {
	public void first();
	public void next();
	public boolean doesCurrentExist();	//this might be equivalent to the isDone method
	public Shape getShape();
}
