package proj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
public class TesterFrame extends JFrame{
	public static final int WIDTH = 900, HEIGHT = 500; // the width and height of the frame

	/* 
	 * myShapes is an array of all the shapes (circles, rectangles ) that we have
	 * In this version we will handle only a maximum of 20 shapes.
	 */
	MyList myShapes;
	Shape currentShape;
	int currentPhase;
	Color currentColor = Color.black;
	/*  
	 * numShapes is the number of shapes the frame is dealing with.
	 * currentPhase depicts the current situation as follows:
	 *   currentPhase = 0, means the user may move any of the shapes
	 *                = 1, a new compound shape is being defined that contains a number of other shapes 
	 *                     (circles, rectangles or other compound shapes- when you implement compound shapes). 
	 *                     Most of the code  for this phase is not included in the current class definition. It is your 
	 *                     responsibility to develop that code. The code supplied now simply allows the users to specify the 
	 *                     shapes (circles, rectangles for now) to be included in the compound shape.
	 */
	int  numShapes;

	/* 
	 * The frame includes the following components:
	 * 		myPanel - the panel where all the shapes (circles, rectangles for now) are displayed. Later on, squares and compound shapes
	 *                will be included) 
	 *                
	 *      shapeButtonPanel -  contains the buttons to specify what type of new shape to create
	 *                (circles, rectangles, squares and compound shapes,
	 *      colorChooserPanel - contains the radiobuttons - blackbutton, redButton, greenButton, blueButton
	 *      
	 *      messageArea - an area for messages to the user 
	 *      inputArea - place for users to supply information about the shapes (e.g., height and width for rectangle, diamter for circles).
	 *      	
	 */
	EditorPanel myPanel; 
	JPanel shapeButtonPanel, colorChooserPanel;
	JTextField messageArea;
	ButtonGroup radioButtonGroupForChoosingColor;
	JRadioButton redButton, greenButton, blueButton, blackButton;
	JTextField inputArea;

	//Defining the Observers and delegated observable object
	DelegatedObservable obs;
	BigCirclesObserver circleObs;
	SquareAndRectangleObserver squareAndRectObs;

	/*
	 * redisplay has the following responsibilities:
	 * 		a) display in showSquaresAndRectangles details about rectangles and squares
	 *      b) display in showBigCircle details about circles that have an area > 5000
	 */

	public void redisplay(){
		myPanel.repaint();
		obs.setChanged();
		obs.notifyObservers(myShapes);
	}

	/*
	 * paintComponent is called when repaint is invoked.
	 * paintComponents simply displays all shapes. Hint : use polymorphism to simplify the code 
	 * 
	 */

	private class EditorPanel extends JPanel{
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			for(int knt=0;knt<myShapes.getSize();knt++){
				currentShape = myShapes.getElement(knt);
				if(currentShape instanceof CompoundShape){
					((CompoundShape)currentShape).showMe(g);
				} else if (currentShape instanceof Rectangle){
					((Rectangle) currentShape).showMe(g);
				} else if(currentShape instanceof Square){
					((Square) currentShape).showMe(g);
				} else {
					((Circle) currentShape).showMe(g);
				}
			}
		}
	}

	private EditorPanel buildEditorPanel(){
		EditorPanel myPanel;
		myPanel = new EditorPanel();

		myPanel.addMouseListener(new MouseAdapter(){
			/* 
			 * mousePressed is important for selecting a shape for dragging it (currentPhase = 0) and for
			 *              selecting a perimeter of a shape to define a compound shape (currentPhase = 5).
			 * 
			 * mouseReleased is important to denote that the operation of dragging a shape is over.
			 * Hint : the code can be simplified using polymorphism - part of your task in phase I
			 */
			public void mouseReleased(MouseEvent e){
				// when the mouse button is released reset the flags indicating that shape(s) or 
				// edge(s) has been selected and restore color since the drag operation or the modify 
				// operation is over.
				for(int knt=0;knt<myShapes.getSize();knt++){
					currentShape = myShapes.getElement(knt);
					if(currentShape instanceof CompoundShape){
						((CompoundShape)currentShape).resetShapeSelected();
					} else if (currentShape instanceof Rectangle){
						((Rectangle) currentShape).resetShapeSelected();
					} else if(currentShape instanceof Square){
						((Square) currentShape).resetShapeSelected();
					} else {
						((Circle) currentShape).resetShapeSelected();
					}
				}
			}

			public void mousePressed(MouseEvent e){
				int x_value, y_value;
				Coordinates currentMousePosition;
				x_value = e.getX(); // Find the coordinates of the position where the user pressed the mouse button
				y_value = e.getY();
				currentMousePosition = new Coordinates(x_value, y_value);
				if (currentPhase == 0){

					for(int knt=0;knt<myShapes.getSize();knt++){
						currentShape = myShapes.getElement(knt);
						if(currentShape instanceof CompoundShape){
							((CompoundShape)currentShape).shapeIsSelected(currentMousePosition);
						} else if (currentShape instanceof Rectangle){
							((Rectangle) currentShape).shapeIsSelected(currentMousePosition);
						} else if(currentShape instanceof Square){
							((Square) currentShape).shapeIsSelected(currentMousePosition);
						} else {
							((Circle) currentShape).shapeIsSelected(currentMousePosition);
						}
					}
				}
				/* 
				 * If we are defining compound shape i.e., currentPhase == 1, we select a
				 * shape by pressing the mouse button very close to the perimeter.
				 * The color of the shape is temporarily changed to yellow. 
				 * Hint : use polymorphism to simplify the code in phase I.
				 */
				else {
					for(int knt=0;knt<myShapes.getSize();knt++){
						currentShape = myShapes.getElement(knt);
						if(currentShape instanceof CompoundShape){
							if(((CompoundShape)currentShape).onThePerimeter(currentMousePosition)){
								((CompoundShape)currentShape).changeColorTemporarily();
							}
						} else if (currentShape instanceof Rectangle){
							if (((Rectangle) currentShape).onThePerimeter(currentMousePosition)){
								((Rectangle) currentShape).changeColorTemporarily();
							}
						} else if(currentShape instanceof Square){
							if (((Square) currentShape).onThePerimeter(currentMousePosition)){
								((Square) currentShape).changeColorTemporarily();
							}
						} else {
							if (((Circle) currentShape).onThePerimeter(currentMousePosition)){
								((Circle) currentShape).changeColorTemporarily();
							}
						}
					}
				}		
				redisplay();
			}   	                      
		});

		myPanel.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent e){				
				/* 
				 * if the mouse is dragged when currentPhase is 0, the selected shapes move 
				 * with the mouse, using method moveShape.
				 * Hint : use polymorphism to simplify the code 
				 */
				for(int knt=0;knt<myShapes.getSize();knt++){
					currentShape = myShapes.getElement(knt);
					// If the operation is to drag shapes
					if(currentShape instanceof CompoundShape){
						if(((CompoundShape)currentShape).shapeIsSelected()){
							((CompoundShape)currentShape).moveShape(new Coordinates(e.getX(),e.getY()));
						}
					} else if (currentShape instanceof Rectangle){
						if (((Rectangle)currentShape). shapeIsSelected()){
							((Rectangle)currentShape). moveShape(new Coordinates(e.getX(), e.getY()));
						} 
					} else if (currentShape instanceof Square){
						if(((Square)currentShape). shapeIsSelected()){
							((Square)currentShape). moveShape(new Coordinates(e.getX(), e.getY()));
						}
					} else {
						if(((Circle)currentShape). shapeIsSelected()){
							((Circle)currentShape). moveShape(new Coordinates(e.getX(), e.getY()));
						}
					}			
					redisplay(); 
				}
			}
		});

		myPanel.setBackground(Color.WHITE);
		return myPanel;
	}

	private JPanel buildShapeChooserPanel(){
		JPanel buttonPanel;
		JButton squareButton, rectangleButton, circleButton, 
		compoundFigureButton, shapeChangeButton;
		/*
		 * Create each of the buttons squareButton, rectangleButton, circleButton, compoundFigureButton
		 * and define the event handler for ActionEvent.
		 */

		buttonPanel = new JPanel();
		compoundFigureButton = new JButton("COMPOUND");
		compoundFigureButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (currentPhase == 0){ // When button COMPOUND is pressed for the first time

					// currentPhase becomes 1 and the user can specify the shapes
					// (currently rectangle or circle) to be glued together
					currentPhase = 1;
					messageArea.setText("select figures to glue then press COMPOUND button again");
					redisplay();
				} else{ // When button COMPOUND is pressed for the second time, normal operation resumes again.
					// This is currently done by setting currentPhase to 0. Hint: You have to include code to 
					// create the compound shape, later on.
					CompoundShape compShape = new CompoundShape();
					for(int knt=0;knt<numShapes;knt++){
						currentShape = myShapes.getElement(knt);
						if(currentShape.getPerimeterSelected()){
							compShape.addShape(currentShape);
							currentShape.changeColorBack();
						}
						redisplay();
					}
					if(compShape.getSize() > 1){
						myShapes.append(compShape);
						System.out.println(compShape);
						numShapes++;
					}
					else
						System.out.println("The compound shape was scrapped because there was 1 or less shapes inside of it");
					currentPhase = 0;
					messageArea.setText("");
				}		
			}
		}
				);
		buttonPanel.add(compoundFigureButton);
		squareButton = new JButton("Create SQUARE");
		squareButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				messageArea.setText("Square size?");
				currentShape = new Square(currentColor);
				myShapes.append(currentShape);
				numShapes++;
				obs.setChanged();
				redisplay();
			}
		}
				);
		buttonPanel.add(squareButton);

		rectangleButton = new JButton("Create RECTANGLE");
		rectangleButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	
				messageArea.setText("Rectangle width?");
				currentShape = new Rectangle(currentColor);
				myShapes.append(currentShape);
				numShapes++;
				obs.setChanged();
				redisplay();
			}
		}
				);

		buttonPanel.add(rectangleButton);

		circleButton = new JButton("Create CIRCLE");
		circleButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentShape = new Circle(currentColor);
				myShapes.append(currentShape);
				numShapes++;
				obs.setChanged();
				messageArea.setText("Circle Diameter?");
				redisplay();
			}
		}
				);
		buttonPanel.add(circleButton);
		messageArea = new JTextField(20);
		buttonPanel.add(messageArea);

		inputArea = new JTextField(3);
		inputArea.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int width, height, diameter,size;
				if (messageArea.getText().equals("Rectangle width?")){
					if (!(inputArea.getText().equals(""))){
						width = Integer.parseInt(inputArea.getText());
						((Rectangle) currentShape).changeWidth(width);
					}
					messageArea.setText("Rectangle height?");
					inputArea.setText("");
					redisplay();
				} else if (messageArea.getText().equals("Rectangle height?")){
					if (!(inputArea.getText().equals(""))){
						height = Integer.parseInt(inputArea.getText());
						((Rectangle) currentShape).changeHeight(height);
					}
					messageArea.setText("");
					inputArea.setText("");
					redisplay();
				} else if (messageArea.getText().equals("Circle Diameter?")){
					if (!(inputArea.getText().equals(""))){
						diameter = Integer.parseInt(inputArea.getText());
						((Circle) currentShape).changeDiameter(diameter);
					}
					messageArea.setText("");
					inputArea.setText("");
					redisplay();
				} else if(messageArea.getText().equals("Square size?")){
					if(!(inputArea.getText().equals(""))){
						size = Integer.parseInt(inputArea.getText());
						((Square) currentShape).changeSize(size);
					}
					messageArea.setText("");
					inputArea.setText("");
					redisplay();
				}
			} 
		}
				);
		buttonPanel.add(inputArea);
		return buttonPanel;
	} 


	/*
	 * buildColorChooserPanel included 3 radio buttons so that users can select red, blue or green
	 * in addition to the original black color for the newly created shape. The user can select 
	 * one of these buttons to change the color for the newly created shape.
	 * We have used a  straight-forward anonymous handler for events in each radio button.
	 */

	private JPanel buildColorChooserPanel(){
		JPanel radioButtonPanel;


		radioButtonPanel = new JPanel();
		blackButton = new JRadioButton("BLACK");
		blackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.black;
			}
		}
				);
		redButton = new JRadioButton("RED");
		redButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.red;
			}
		}
				);

		greenButton = new JRadioButton("GREEN");
		greenButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.green;
			}
		}
				);

		blueButton = new JRadioButton("BLUE");
		blueButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentColor = Color.blue;
			}
		}
				);

		radioButtonGroupForChoosingColor = new ButtonGroup();
		radioButtonGroupForChoosingColor.add(blackButton);
		radioButtonGroupForChoosingColor.add(blueButton);
		radioButtonGroupForChoosingColor.add(greenButton);
		radioButtonGroupForChoosingColor.add(redButton);
		radioButtonPanel.add(blackButton);
		radioButtonPanel.add(blueButton);
		radioButtonPanel.add(greenButton);
		radioButtonPanel.add(redButton);
		return radioButtonPanel;
	}

	public TesterFrame(){
		myShapes = new MyList(100);
		numShapes = 0;
		currentPhase = 0;
		obs = new DelegatedObservable();
		circleObs = new BigCirclesObserver();
		squareAndRectObs = new SquareAndRectangleObserver();
		obs.addObserver(circleObs);
		obs.addObserver(squareAndRectObs);
		myPanel = buildEditorPanel();
		shapeButtonPanel = buildShapeChooserPanel();
		colorChooserPanel = buildColorChooserPanel();

		add(colorChooserPanel, BorderLayout.NORTH);
		add(shapeButtonPanel, BorderLayout.SOUTH);
		add(myPanel, BorderLayout.CENTER);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String a[]){
		TesterFrame aFrame = new TesterFrame();
	}
}