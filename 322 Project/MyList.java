package proj;

public class MyList extends AbstractList{
	Shape[] myShapes;
	int numShapes;

	public MyList(int sizeOfArray){
		numShapes = 0;
		myShapes = new Shape[sizeOfArray];
	}

	public AbstractIterator getIterator() {
		return new Iterator();
	}

	public void append(Shape newShape) {
		myShapes[numShapes] = newShape;
		numShapes++;
	}

	public class Iterator extends AbstractIterator implements IteratorInterface{
		int index;

		public Iterator(){
			index = 0;
		}

		public void first() {
			index = 0;
		}

		public void next() {
			index++;
		}

		public boolean doesCurrentExist(){
			if(myShapes[index] == null){
				return false;
			}
			return true;
		}

		public Shape getShape() {
			if(doesCurrentExist())
				return myShapes[index];
			return null;
		}

	}

	public Shape getElement(int index){
		return myShapes[index];
	}

	public int getSize(){
		return numShapes;
	}

}
