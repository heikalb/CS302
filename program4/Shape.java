import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;


public class Shape 
{
	//DATA FIELDS
	private String type;	//Type of shape (rec, arc or poly).
	//Shape color values for red, blue and green:
	private int redVal, greenVal, blueVal;
	private Color color;	//Color of the shape	
	
	//Data for rectangles
	private int rectCornerX,	//X-coordinate of upper left corner.
				rectCornerY,	//Y-coordinate of upper left corner.
				rectWidth,		//Width of the rectangle
				rectHeight;		//Height of the rectangle
	
	//Data for arcs
	//X-coordinate of upper left corner of the arc's bounding rectangle:
	private int arcRectCornerX,	
	//Y-coordinate of the upper left corner the arc's bounding rectangle:
	arcRectCornerY,	
	arcWidth,		//Width of the bounding rectangle of the arc.
	arcHeight,		//Height of the bounding rectangle of the arc.
	arcInitialAngle,	//Initial angle of the start of an arc. 
	arcFinalAngle;		//Final angle of an arc
	
	
	//Data for polygons
	private int polyNumPoints;	//Number of points in a polygon
	//Array list of x-coordinates of the points:
	private ArrayList<Integer> polyXPoints = new ArrayList<Integer>();
	//Array list of y-coordinates of the points:
	private ArrayList<Integer> polyYPoints = new ArrayList<Integer>();
	
	
	//Constructor
	public Shape(String type)
	{
		this.type = type;
	}
	
	public void setColor(int redVal, int greenVal, int blueVal)
	{
		this.redVal = redVal;
		this.greenVal = greenVal;
		this.blueVal = blueVal;
		color = new Color(redVal, greenVal, blueVal);
		
	}
	
	public String getType()
	{
		return type;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setRect(int rectCornerX, int rectCornerY, int rectWidth, int rectHeight)
	{
		this.rectCornerX = rectCornerX;
		this.rectCornerY = rectCornerY;
		this.rectWidth = rectWidth;
		this.rectHeight = rectHeight;
	}
	
	public void setArc(int arcRectCornerX, int arcRectCornerY, int arcWidth, 
					int arcHeight, int arcInitialAngle, int arcFinalAngle)
	{
		this.arcRectCornerX = arcRectCornerX;
		this.arcRectCornerY = arcRectCornerY;
		this.arcWidth = arcWidth;
		this.arcHeight = arcHeight;
		this.arcInitialAngle = arcInitialAngle;
		this.arcFinalAngle = arcFinalAngle;
	}
	
	public void setPoly(int polyNumPoints, ArrayList<Integer> polyXPoints, 
						ArrayList<Integer> polyYPoints )
	{
		this.polyNumPoints = polyNumPoints;
		this.polyXPoints = polyXPoints;
		this.polyYPoints = polyYPoints;
	}
	
	public void askColor(Scanner scan)
	{
		int redVal, greenVal, blueVal;
		
		System.out.print("Enter amount of red (0-255): ");
			redVal = ImageMaker.getInput(0, 255, "Must be 0-255: ", scan);
			//setRed(redVal);
		System.out.print("Enter amount of green (0-255): ");
			greenVal = ImageMaker.getInput(0, 255, "Must be 0-255: ", scan);
			//setGreen(greenVal);
		System.out.print("Enter amount of blue (0-255): ");
			blueVal = ImageMaker.getInput(0, 255, "Must be 0-255: ", scan);
			//setBlue(blueVal);
		this.setColor(redVal, greenVal, blueVal);
	}
	
	public void askRect(Scanner scan)
	{
		int rectCornerX, rectCornerY, rectWidth, rectHeight;
		
		System.out.print("Enter x-coord of upper left corner of bounding rectangle (int): ");	
			rectCornerX = ImageMaker.getInput(-2147483648, 2147483647, 
					"Must be -2,147,483,648-2,147,483,647: ", scan);
		System.out.print("Enter y-coord of upper left corner of bounding rectangle (int): ");
			rectCornerY = ImageMaker.getInput(- 2147483648, 2147483647,
					"Must be -2,147,483,648-2,147,483,647: ", scan);
		System.out.print("Enter width of bounding rectangle (int): ");
			rectWidth = ImageMaker.getInput(- 2147483648, 2147483647, 
					"Must be -2,147,483,648-2,147,483,647: ", scan);
		System.out.print("Enter height of bounding rectangle (int): ");
			rectHeight = ImageMaker.getInput(- 2147483648, 2147483647, 
					"Must be -2,147,483,648-2,147,483,647: ", scan);
		
		setRect(rectCornerX, rectCornerY, rectWidth, rectHeight);
		
		System.out.println("Created: rect " + this.redVal + 
				" " + this.greenVal + " " + this.blueVal + " " + rectCornerX + 
				" " + rectCornerY + " " + rectWidth + " " + rectHeight);
							
	}
	
	public void askArc(Scanner scan) 
	{
		int arcRectCornerX, arcRectCornerY, arcWidth, arcHeight, 
			arcInitialAngle, arcFinalAngle;  
		
		System.out.print("Enter x-coord of upper left corner of bounding rectangle (int): ");
			arcRectCornerX = ImageMaker.getInput(-2147483648, 2147483647, 
					"Must be -2,147,483,648-2,147,483,647: ", scan);
		System.out.print("Enter y-coord of upper left corner of bounding rectangle (int): ");
			arcRectCornerY = ImageMaker.getInput(-2147483648, 2147483647, 
					"Must be -2,147,483,648-2,147,483,647: ", scan);
		System.out.print("Enter width of bounding rectangle (int): ");
		 	arcWidth = ImageMaker.getInput(-2147483648, 2147483647, 
					"Must be -2,147,483,648-2,147,483,647: ", scan);
		System.out.print("Enter height of bounding rectangle (int): ");
			arcHeight = ImageMaker.getInput(-2147483648, 2147483647, 
					"Must be -2,147,483,648-2,147,483,647: ", scan);
		System.out.print("Enter initial angle of arc (int degrees from horizontal): ");
			arcInitialAngle = ImageMaker.getInput(-2147483648, 2147483647, 
					"Must be -2,147,483,648-2,147,483,647: ", scan);
		System.out.print("Enter length of angle (int degrees from start): ");
			arcFinalAngle = ImageMaker.getInput(-2147483648, 2147483647, 
					"Must be -2,147,483,648-2,147,483,647: ", scan);
		
		setArc(arcRectCornerX, arcRectCornerY, arcWidth, arcHeight, 
				arcInitialAngle, arcFinalAngle);
		
		System.out.println("Created: arc " + this.redVal + 
			" " + this.greenVal + " " + this.blueVal + " " + arcRectCornerX + 
			" " + arcRectCornerY + " " + arcWidth + " " + arcHeight + 
			" " + arcInitialAngle + " " + arcFinalAngle);
	}
	
	public void askPoly(Scanner scan)
	{
		ArrayList<Integer> polyXPoints = new ArrayList<Integer>();
		ArrayList<Integer> polyYPoints = new ArrayList<Integer>();
		int polyNumPoints;
		
		System.out.print("Enter number of points (1-100): ");
			polyNumPoints = ImageMaker.getInput(1, 100, "Must be 1-100: ", scan);
		
		for(int i = 0; i < polyNumPoints; i++)
		{
			System.out.print("Enter x and y coords of next point (int): ");
			polyXPoints.add(scan.nextInt());
			polyYPoints.add(scan.nextInt());
		}
		
		setPoly(polyNumPoints, polyXPoints, polyYPoints);
		
		//String to be displayed after a polygon is created
		String createString = "Created: poly " + this.redVal + 
				" " + this.greenVal + " " + this.blueVal + " " + polyNumPoints;
		
		for(int i = 0; i < polyXPoints.size(); i++)
			createString += " " + polyXPoints.get(i) + " " + polyYPoints.get(i);
		
		System.out.println(createString);
	}

	public void drawShape(Graphics graphics)
	{
		//Set color
		graphics.setColor(this.color);
		
		//Draw according to type of Shape
		if(this.type.equals("rect"))
			graphics.fillRect(rectCornerX, rectCornerY, rectWidth, rectHeight);
		else if(this.type.equals("arc"))
			graphics.fillArc(arcRectCornerX, arcRectCornerY, arcWidth, 
								arcHeight, arcInitialAngle, arcFinalAngle);
		else if(this.type.equals("poly"))
		{
			//Create int arrays version of the Integer ArrayLists of coordinates
			//so the data can be passed to the fillPolygon method below
			int[] XArray = new int[polyXPoints.size()];	//For x-coordinates.
			int[] YArray = new int[polyYPoints.size()];	//For y-coordinates.
			for(int i = 0; i < XArray.length; i++)
			{
				XArray[i] = polyXPoints.get(i);
				YArray[i] = polyYPoints.get(i);
			}
			
			//Draw a filled polygon
			graphics.fillPolygon(XArray, YArray, polyNumPoints);
		}
			
	}
}
